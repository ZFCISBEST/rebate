package com.help.rebate.service.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DdxConfig;
import com.help.rebate.commons.DtkConfig;
import com.help.rebate.commons.PrettyHttpService;
import com.help.rebate.dao.entity.OrderDetail;
import com.help.rebate.dao.entity.TimeCursorPosition;
import com.help.rebate.service.OrderService;
import com.help.rebate.service.TimeCursorPositionService;
import com.help.rebate.service.dtk.tb.DtkItemConverter;
import com.help.rebate.service.dtk.tb.DtkOrderDetail;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.PropertyValueResolver;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.utils.dtk.ApiClient;
import com.help.rebate.utils.dtk.SignMD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;

/**
 * 固定时间同步订单任务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Component
public class FixedOrderSyncTask {
    private static final Logger logger = LoggerFactory.getLogger(FixedOrderSyncTask.class);

    /**
     * 运行标志，表示是否运行
     */
    private boolean running = false;

    /**
     * 表示当前的位置缓存
     */
    private Date startTime;
    private Date endTime;
    private int secondStep;
    private int orderScene;
    private int queryType;
    private String positionIndex;

    /**
     * 需要的令牌个数
     */
    private int tokenCnt = 2;


    /**
     * 速度控制器
     */
    private RateLimiterManager rateLimiterManager = new RateLimiterManager();

    /**
     * 订单服务
     */
    @Resource
    private OrderService orderService;

    /**
     * 时间游标
     */
    @Resource
    private TimeCursorPositionService timeCursorPositionService;

    /**
     * http服务
     */
    @Resource
    private PrettyHttpService prettyHttpService;

    /**
     * 周期调度执行器
     */
    @Scheduled(cron = "*/2 * * * * ?")
    public void execute() {
        if (!running) {
            logger.debug("[fix-sync-task] running flag:{}", running);
            return;
        }

        //获取令牌
        if (!rateLimiterManager.acquire(tokenCnt)) {
            logger.info("[fix-sync-task] rate limit: token is not enough");
        }

        //获取同步开始时间
        if (startTime == null || endTime == null) {
            fetchSyncTime();
        }

        //二次判定
        if (startTime == null || endTime == null) {
            logger.info("[fix-sync-task] find not any sync time config, skip sync");
            return;
        }

        //时间判定
        long endMillis = endTime.getTime();
        if (System.currentTimeMillis() < endMillis) {
            logger.info("[fix-sync-task] 当前时间小于结束时间[{}], 跳过本次同步", TimeUtil.format(endMillis));
            return;
        }

        //执行
        try {
            syncOrder();
        } catch(Exception e) {
            logger.info("[fix-sync-task] error to sync order", e);
        }
    }

    /**
     * 执行订单同步
     * query_type - 查询时间类型，1-订单创建时间，2-订单支付时间，3-订单结算时间，4-订单更新时间
     * 场景订单类型，1-常规订单，2-渠道订单，3-会员运营订单，0-都查
     * 查询时间场景 - 查询时间类型，1-订单创建时间，2-订单支付时间，3-订单结算时间，4-订单更新时间，0-都查
     */
    private void syncOrder() {
        logger.info("[fix-sync-task] sync order - time range[{}, {}], scene[{}], queryType[{}]",
                TimeUtil.format(startTime), TimeUtil.format(endTime), orderScene, queryType);

        //重构订单场景
        int[] orderScenes = new int[]{orderScene};
        if (orderScene == 0) {
            orderScenes = new int[]{1, 3};
        }

        //重构查询场景
        int[] queryTypes = new int[]{queryType};
        if (queryType == 0) {
            queryTypes = new int[]{1, 2, 3, 4};
        }

        //循环
        for (int i = 0; i < orderScenes.length; i++) {
            for (int j = 0; j < queryTypes.length; j++) {
                boolean hasNext = true;
                int pageNo = 1;
                int pageSize = 50;
                while (hasNext) {
                    //获取令牌
                    if (!rateLimiterManager.acquire(tokenCnt)) {
                        sleepUninterruptibly(5500, TimeUnit.MICROSECONDS);
                        continue;
                    }

                    //参数构建
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("appKey", DtkConfig.dtkAppkey);
                    params.put("version", "v1.0.0");
                    params.put("startTime", TimeUtil.format(startTime));
                    params.put("endTime", TimeUtil.format(endTime));
                    params.put("queryType", queryTypes[j]);
                    params.put("orderScene", orderScenes[i]);

                    if (positionIndex != null) {
                        params.put("positionIndex", positionIndex);
                    }
                    params.put("pageNo", pageNo);
                    params.put("pageSize", pageSize);

                    //请求数据
                    String response = ApiClient.sendReqNew(DtkConfig.DTK_TB_ORDER_DETAILS_URL, DtkConfig.dtkAppsecret, params);
                    JSONObject jsonObject = JSON.parseObject(String.valueOf(response));

                    //判定
                    String code = PropertyValueResolver.getProperty(jsonObject, "code") + "";
                    if (!"0".equals(code)) {
                        throw new IllegalStateException("获取远程数据报错，详细错误：" + PropertyValueResolver.getProperty(jsonObject, "msg"));
                    }

                    //全部数据，存储到数据库
                    String affectedMsg = saveOrUpdateToOrderDetail(jsonObject);
                    logger.info("[fix-sync-task] 同步存入数据:{}, start:{}, end:{}, position:{}, orderScene:{}, queryType:{}", affectedMsg,
                            TimeUtil.format(startTime), TimeUtil.format(endTime), positionIndex, orderScenes[i], queryTypes[j]);

                    //看下是否还有数据
                    hasNext = jsonObject.getJSONObject("data").getBoolean("has_next");
                    if (hasNext) {
                        positionIndex = jsonObject.getJSONObject("data").getString("position_index");
                    }
                }
            }
        }

        //更改游标，并更新数据库
        startTime = new Date(startTime.getTime() + secondStep * 1000);
        endTime = new Date(endTime.getTime() + secondStep * 1000);
        positionIndex = null;
        timeCursorPositionService.saveOrUpdateTimeCursor(startTime, secondStep, orderScene, queryType, TimeCursorPositionService.TimeType.ORDER_SYNC);
    }

    /**
     * order信息存储
     *
     * @param responseObj
     * @return
     */
    private String saveOrUpdateToOrderDetail(JSONObject responseObj) {
        //JSONArray jsonArray = responseObj.getJSONArray("data");//订单侠
        JSONArray jsonArray = responseObj.getJSONObject("data").getJSONObject("results").getJSONArray("publisher_order_dto");//大淘客
        if (jsonArray == null || jsonArray.size() == 0) {
            return "0";
        }

        //不为空
        int size = jsonArray.size();
        int saveCnt = 0;
        int updateCnt = 0;
        int skipCnt = 0;
        for (int i = 0; i < size; i++) {
            JSONObject orderItem = jsonArray.getJSONObject(i);
            String tradeId = orderItem.getString("trade_id");
            String parentTradeId = orderItem.getString("trade_parent_id");//应该是trade_parent_id，而不是parent_trade_id
            List<OrderDetail> orderDetails = orderService.selectByTradeId(parentTradeId, tradeId);

            //插入
            OrderDetail newOrderDetail = buildOrderDetail(orderItem, tradeId, parentTradeId);
            if (EmptyUtils.isEmpty(orderDetails)) {
                orderService.save(newOrderDetail);
                saveCnt++;
                continue;
            }

            //更新 - 决定是否更新 - 12-付款，13-关闭，14-确认收货，3-结算成功
            OrderDetail orderDetail = orderDetails.get(0);
            Integer oldTkStatus = orderDetail.getTkStatus();
            Integer newTkStatus = newOrderDetail.getTkStatus();

            boolean updateFlag = false;
            if (oldTkStatus == null) {
                updateFlag = true;
            }

            //原始状态只是付款
            else if (oldTkStatus == 12 && newTkStatus != 12) {
                updateFlag = true;
            }

            //确认到结算，确认到关闭
            else if (oldTkStatus == 14 && (newTkStatus == 3 || newTkStatus == 13)) {
                updateFlag = true;
            }

            //执行更新 - 直接全部更新 - todo 测试一下
            updateFlag = true;
            if (updateFlag) {
                newOrderDetail.setId(orderDetail.getId());
                newOrderDetail.setGmtModified(new Date());
                orderService.update(newOrderDetail);
                updateCnt++;
            }
            else {
                skipCnt++;
            }
        }

        return "save-" + saveCnt + ", update-" + updateCnt + ", skip-" + skipCnt;
    }

    /**
     * 构建详情
     * @param orderItem
     * @param tradeId
     * @param parentTradeId
     * @return
     */
    private OrderDetail buildOrderDetail(JSONObject orderItem, String tradeId, String parentTradeId) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setGmtCreated(new Date());
        orderDetail.setGmtModified(new Date());
        orderDetail.setTradeId(tradeId);
        orderDetail.setParentTradeId(parentTradeId);
        orderDetail.setClickTime(orderItem.getDate("click_time"));
        orderDetail.setTkCreateTime(orderItem.getDate("tk_create_time"));
        orderDetail.setTbPaidTime(orderItem.getDate("tb_paid_time"));
        orderDetail.setTkPaidTime(orderItem.getDate("tk_paid_time"));
        orderDetail.setAlipayTotalPrice(orderItem.getString("alipay_total_price"));
        orderDetail.setPayPrice(orderItem.getString("pay_price"));
        orderDetail.setModifiedTime(orderItem.getString("modified_time"));
        orderDetail.setTkStatus(orderItem.getInteger("tk_status"));
        orderDetail.setOrderType(orderItem.getString("order_type"));
        orderDetail.setRefundTag(orderItem.getInteger("refund_tag"));
        orderDetail.setFlowSource(orderItem.getString("flow_source"));
        orderDetail.setTerminalType(orderItem.getString("terminal_type"));
        orderDetail.setTkEarningTime(orderItem.getDate("tk_earning_time"));
        orderDetail.setTkOrderRole(orderItem.getInteger("tk_order_role"));
        orderDetail.setTotalCommissionRate(orderItem.getString("total_commission_rate"));
        orderDetail.setIncomeRate(orderItem.getString("income_rate"));
        orderDetail.setPubShareRate(orderItem.getString("pub_share_rate"));
        orderDetail.setTkTotalRate(orderItem.getString("tk_total_rate"));
        orderDetail.setTotalCommissionFee(orderItem.getString("total_commission_fee"));
        orderDetail.setPubSharePreFee(orderItem.getString("pub_share_pre_fee"));
        orderDetail.setPubShareFee(orderItem.getString("pub_share_fee"));
        orderDetail.setAlimamaRate(orderItem.getString("alimama_rate"));
        orderDetail.setAlimamaShareFee(orderItem.getString("alimama_share_fee"));
        orderDetail.setSubsidyRate(orderItem.getString("subsidy_rate"));
        orderDetail.setSubsidyFee(orderItem.getString("subsidy_fee"));
        orderDetail.setSubsidyType(orderItem.getString("subsidy_type"));
        orderDetail.setTkCommissionPreFeeForMediaPlatform(orderItem.getString("tk_commission_pre_fee_for_media_platform"));
        orderDetail.setTkCommissionFeeForMediaPlatform(orderItem.getString("tk_commission_fee_for_media_platform"));
        orderDetail.setTkCommissionRateForMediaPlatform(orderItem.getString("tk_commission_rate_for_media_platform"));
        orderDetail.setPubId(orderItem.getString("pub_id"));
        orderDetail.setSiteId(orderItem.getString("site_id"));
        orderDetail.setAdzoneId(orderItem.getString("adzone_id"));
        orderDetail.setSiteName(orderItem.getString("site_name"));
        orderDetail.setAdzoneName(orderItem.getString("adzone_name"));
        orderDetail.setSpecialId(orderItem.getString("special_id"));
        orderDetail.setRelationId(orderItem.getString("relation_id"));
        orderDetail.setItemCategoryName(orderItem.getString("item_category_name"));
        orderDetail.setSellerNick(orderItem.getString("seller_nick"));
        orderDetail.setSellerShopTitle(orderItem.getString("seller_shop_title"));
        orderDetail.setItemId(orderItem.getString("item_id"));
        orderDetail.setItemTitle(orderItem.getString("item_title"));
        orderDetail.setItemPrice(orderItem.getString("item_price"));
        orderDetail.setItemLink(orderItem.getString("item_link"));
        orderDetail.setItemNum(orderItem.getInteger("item_num"));
        //orderDetail.setTkDepositTime(orderItem.getDate("tk_deposit_time"));
        //orderDetail.setTbDepositTime(orderItem.getDate("tb_deposit_time"));
        orderDetail.setTkDepositTime(TimeUtil.parseDate(orderItem.getString("tk_deposit_time")));
        orderDetail.setTbDepositTime(TimeUtil.parseDate(orderItem.getString("tb_deposit_time")));
        orderDetail.setDepositPrice(orderItem.getString("deposit_price"));
        orderDetail.setAlscId(orderItem.getString("alsc_id"));
        orderDetail.setAlscPid(orderItem.getString("alsc_pid"));
        orderDetail.setServiceFeeDtoList("");
        if (orderItem.containsKey("lx_rid")){   //大淘客接口没有返回lx_rid
            orderDetail.setLxRid(orderItem.getString("lx_rid"));
        }
        if (orderItem.containsKey("is_lx")){    //大淘客接口没有返回is_lx
            orderDetail.setIsLx(orderItem.getString("is_lx"));
        }
        orderDetail.setStatus(0);
        return orderDetail;
    }

    /**
     * 获取时间点位
     */
    private void fetchSyncTime() {
        TimeCursorPosition timeCursorPosition = timeCursorPositionService.fetchOrderSyncTimePosition(TimeCursorPositionService.TimeType.ORDER_SYNC);
        if (timeCursorPosition == null) {
            return;
        }

        this.startTime = timeCursorPosition.getStartTime();
        this.endTime = timeCursorPosition.getEndTime();
        this.secondStep = timeCursorPosition.getStep();
        this.orderScene = timeCursorPosition.getSubType();
        this.queryType = timeCursorPosition.getQueryType();
    }

    /**
     *
     * @param orderUpdateTime
     * @param minuteStep
     * @param orderScene
     * @param queryType
     * @param running
     */
    public void cleanContext(String orderUpdateTime, Long minuteStep, int orderScene, Integer queryType, Boolean running) {
        this.startTime = null;
        this.endTime = null;
        this.orderScene = 0;
        this.queryType = 0;
        this.running = running;
        this.positionIndex = null;
    }

    /**
     * 设置运行标志
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
}
