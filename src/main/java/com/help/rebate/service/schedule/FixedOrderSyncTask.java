package com.help.rebate.service.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DtkConfig;
import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfo;
import com.help.rebate.dao.entity.V2TaobaoSyncOrderOffsetInfo;
import com.help.rebate.service.V2TaobaoOrderBindService;
import com.help.rebate.service.V2TaobaoOrderService;
import com.help.rebate.service.V2TaobaoSyncOrderOffsetInfoService;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.PropertyValueResolver;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.utils.dtk.ApiClient;
import com.help.rebate.vo.OrderBindResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int secondStep;
    private int syncOrderType;
    private int syncTimeType;
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
    private V2TaobaoOrderService v2TaobaoOrderService;

    /**
     * 订单绑定服务
     */
    @Resource
    private V2TaobaoOrderBindService v2TaobaoOrderBindService;

    /**
     * 时间游标
     */
    @Resource
    private V2TaobaoSyncOrderOffsetInfoService v2TaobaoSyncOrderOffsetInfoService;

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
            selectSyncTimeOffset();
        }

        //二次判定
        if (startTime == null || endTime == null) {
            logger.info("[fix-sync-task] find not any sync time config, skip sync");
            return;
        }

        //时间判定
        if (endTime.isBefore(LocalDateTime.now())) {
            logger.info("[fix-sync-task] 当前时间小于结束时间[{}], 跳过本次同步", TimeUtil.formatLocalDate(endTime));
            return;
        }

        //执行
        try {
            //同步订单
            syncOrder();

            //订单绑定
            syncBindOrder();

            //更新时间游标
            updateSyncOrderTimeOffset();
        } catch(Exception e) {
            logger.info("[fix-sync-task] error to sync order", e);
        }
    }

    /**
     * 执行订单同步
     * sync_time_type - 查询时间类型，1-订单创建时间，2-订单支付时间，3-订单结算时间，4-订单更新时间
     * syncOrderType - 场景订单类型，1-常规订单，2-渠道订单，3-会员运营订单，0-都查
     * syncTimeType - 查询时间场景 - 查询时间类型，1-订单创建时间，2-订单支付时间，3-订单结算时间，4-订单更新时间，0-都查
     */
    private void syncOrder() {
        logger.info("[fix-sync-task] sync order - time range[{}, {}], syncOrderType[{}], syncTimeType[{}]",
                TimeUtil.formatLocalDate(startTime), TimeUtil.formatLocalDate(endTime), syncOrderType, syncTimeType);

        //重构订单场景
        int[] syncOrderTypes = new int[]{syncOrderType};
        if (syncOrderType == 0) {
            syncOrderTypes = new int[]{1, 3};
        }

        //重构查询场景
        int[] syncTimeTypes = new int[]{syncTimeType};
        if (syncTimeType == 0) {
            syncTimeTypes = new int[]{1, 2, 3, 4};
        }

        //循环
        for (int i = 0; i < syncOrderTypes.length; i++) {
            for (int j = 0; j < syncTimeTypes.length; j++) {
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
                    params.put("startTime", TimeUtil.formatLocalDate(startTime));
                    params.put("endTime", TimeUtil.formatLocalDate(endTime));
                    params.put("queryType", syncTimeTypes[j]);
                    params.put("orderScene", syncOrderTypes[i]);

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
                            TimeUtil.formatLocalDate(startTime), TimeUtil.formatLocalDate(endTime), positionIndex, syncOrderTypes[i], syncTimeTypes[j]);

                    //看下是否还有数据
                    hasNext = jsonObject.getJSONObject("data").getBoolean("has_next");
                    if (hasNext) {
                        positionIndex = jsonObject.getJSONObject("data").getString("position_index");
                    }
                }
            }
        }
    }

    /**
     * 顺便同步绑定订单
     */
    private void syncBindOrder() {
        //为了防止覆盖不全，这里自动自起始日期，往前多扫描1个周期
        LocalDateTime localStartTime = startTime.minusSeconds(secondStep);
        long localSecondStep = 2 * secondStep;

        logger.info("[fix-order-sync-task] sync bind order - time range[{}, {}]",
                TimeUtil.formatLocalDate(localStartTime), TimeUtil.formatLocalDate(endTime));

        //调用服务，按时间范围查找并绑定
        List<OrderBindResultVO> orderBindResultVOS = v2TaobaoOrderBindService.bindByTimeRange(TimeUtil.formatLocalDate(localStartTime), localSecondStep / 60);

        //log输出
        if (EmptyUtils.isEmpty(orderBindResultVOS)) {
            logger.info("[fix-order-sync-task] sync bind order - time range[{}, {}], no any bind order",
                    TimeUtil.formatLocalDate(localStartTime), TimeUtil.formatLocalDate(endTime));
            return;
        }

        //循环输出
        for (OrderBindResultVO orderBindResultVO : orderBindResultVOS) {
            String openId = orderBindResultVO.getOpenId();
            String specialId = orderBindResultVO.getSpecialId();
            String tradeParentId = orderBindResultVO.getTradeParentId();
            List<String> tradeIdItemIdList = orderBindResultVO.getTradeIdItemIdList();
            String tradeIds = tradeIdItemIdList.stream().collect(Collectors.joining(","));
            logger.info("[fix-order-sync-task] sync bind order - time range[{}, {}], openId:{}, specialId:{}, tradeParentId:{}, tradeIds:{}",
                    TimeUtil.formatLocalDate(localStartTime), TimeUtil.formatLocalDate(endTime), openId, specialId, tradeParentId, tradeIds);
        }
    }

    /**
     * 更新时间游标
     */
    private void updateSyncOrderTimeOffset() {
        //更改游标，并更新数据库
        startTime = startTime.plusSeconds(secondStep);
        endTime = startTime.plusSeconds(secondStep);
        positionIndex = null;
        v2TaobaoSyncOrderOffsetInfoService.upsertSyncStartTimeOffset(startTime, secondStep, syncOrderType, syncTimeType);
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
            List<V2TaobaoOrderDetailInfo> orderDetails = v2TaobaoOrderService.selectByTradeId(parentTradeId, tradeId);

            //插入
            V2TaobaoOrderDetailInfo newOrderDetail = buildOrderDetail(orderItem, tradeId, parentTradeId);
            if (EmptyUtils.isEmpty(orderDetails)) {
                v2TaobaoOrderService.save(newOrderDetail);
                saveCnt++;
                continue;
            }

            //更新 - 决定是否更新 - 12-付款，13-关闭，14-确认收货，3-结算成功
            V2TaobaoOrderDetailInfo orderDetail = orderDetails.get(0);
            Integer oldTkStatus = orderDetail.getTkStatus();
            Integer newTkStatus = newOrderDetail.getTkStatus();

            //如果已经结算成功了，那么就不不更新了
            boolean updateFlag = true;
            if (oldTkStatus != null && (oldTkStatus == 3 || oldTkStatus == 13)) {
                updateFlag = false;
            }

            //执行更新
            if (updateFlag) {
                newOrderDetail.setId(orderDetail.getId());
                newOrderDetail.setGmtModified(LocalDateTime.now());
                v2TaobaoOrderService.update(newOrderDetail);
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
    private V2TaobaoOrderDetailInfo buildOrderDetail(JSONObject orderItem, String tradeId, String parentTradeId) {
        V2TaobaoOrderDetailInfo orderDetail = new V2TaobaoOrderDetailInfo();
        orderDetail.setGmtCreated(LocalDateTime.now());
        orderDetail.setGmtModified(LocalDateTime.now());
        orderDetail.setTradeId(tradeId);
        orderDetail.setTradeParentId(parentTradeId);
        orderDetail.setClickTime(TimeUtil.parseLocalDate(orderItem.getString("click_time")));
        orderDetail.setTkCreateTime(TimeUtil.parseLocalDate(orderItem.getString("tk_create_time")));
        orderDetail.setTbPaidTime(TimeUtil.parseLocalDate(orderItem.getString("tb_paid_time")));
        orderDetail.setTkPaidTime(TimeUtil.parseLocalDate(orderItem.getString("tk_paid_time")));
        orderDetail.setAlipayTotalPrice(orderItem.getString("alipay_total_price"));
        orderDetail.setPayPrice(orderItem.getString("pay_price"));
        orderDetail.setModifiedTime(orderItem.getString("modified_time"));
        orderDetail.setTkStatus(orderItem.getInteger("tk_status"));
        orderDetail.setOrderType(orderItem.getString("order_type"));
        orderDetail.setRefundTag(orderItem.getInteger("refund_tag"));
        orderDetail.setFlowSource(orderItem.getString("flow_source"));
        orderDetail.setTerminalType(orderItem.getString("terminal_type"));
        orderDetail.setTkEarningTime(TimeUtil.parseLocalDate(orderItem.getString("tk_earning_time")));
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
        orderDetail.setTkDepositTime(TimeUtil.parseLocalDate(orderItem.getString("tk_deposit_time")));
        orderDetail.setTbDepositTime(TimeUtil.parseLocalDate(orderItem.getString("tb_deposit_time")));
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
        orderDetail.setStatus((byte) 0);
        return orderDetail;
    }

    /**
     * 获取时间点位
     */
    private void selectSyncTimeOffset() {
        V2TaobaoSyncOrderOffsetInfo v2TaobaoSyncOrderOffsetInfo = v2TaobaoSyncOrderOffsetInfoService.selectOrderSyncTimeOffset();
        if (v2TaobaoSyncOrderOffsetInfo == null) {
            return;
        }

        this.startTime = v2TaobaoSyncOrderOffsetInfo.getStartTime();
        this.endTime = v2TaobaoSyncOrderOffsetInfo.getEndTime();
        this.secondStep = v2TaobaoSyncOrderOffsetInfo.getStep();
        this.syncOrderType = v2TaobaoSyncOrderOffsetInfo.getSyncOrderType();
        this.syncTimeType = v2TaobaoSyncOrderOffsetInfo.getSyncTimeType();
    }

    /**
     * 重置点位
     * @param running
     */
    public void resetScheduleContext(Boolean running) {
        this.startTime = null;
        this.endTime = null;
        this.syncOrderType = 0;
        this.syncTimeType = 0;
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
