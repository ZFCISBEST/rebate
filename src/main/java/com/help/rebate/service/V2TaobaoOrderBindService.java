package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoOrderDetailInfoDao;
import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapInfo;
import com.help.rebate.dao.entity.V2TaobaoTklConvertHistoryInfo;
import com.help.rebate.dao.entity.V2TaobaoUserInfo;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.NumberUtil;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.vo.OrderBindResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单绑定服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
@Transactional
public class V2TaobaoOrderBindService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoOrderBindService.class);

    /**
     * 用户信息服务，主要用于对用户信息表的更新操作
     */
    @Resource
    private V2TaobaoUserInfoService v2TaobaoUserInfoService;

    /**
     * 转链历史记录表
     */
    @Resource
    private V2TaobaoTklConvertHistoryService v2TaobaoTklConvertHistoryService;

    /**
     * 订单详情接口
     */
    @Resource
    private V2TaobaoOrderDetailInfoDao v2TaobaoOrderDetailInfoDao;
    @Resource
    private V2TaobaoOrderService v2TaobaoOrderService;

    /**
     * 订单映射表
     */
    @Resource
    private V2TaobaoOrderOpenidMapService v2TaobaoOrderOpenidMapService;
    @Resource
    private V2TaobaoOrderOpenidMapFailureService v2TaobaoOrderOpenidMapFailureService;
    @Resource
    private V2TaobaoCommissionAccountService v2TaobaoCommissionAccountService;

    @Resource
    private V2TaobaoCommissionRatioInfoService v2TaobaoCommissionRatioInfoService;

    /**
     * 用户通过前端，直接发送过来的，期望绑定的订单
     * 如果按照订单，没有查到，那么给用户报相应提示，这里不做任何缓存，提示用户稍后再试，因为可能还没来得及订单同步，或者订单输入错误了
     * @param parentTradeId
     * @param openId
     * @return 商品名称列表
     */
    public OrderBindResultVO bindByTradeParentId(String parentTradeId, String openId) {
        OrderBindResultVO orderBindResultVO = new OrderBindResultVO();

        //首先看看，是不是已经绑定过了
        List<V2TaobaoOrderOpenidMapInfo> orderOpenidMapList = v2TaobaoOrderOpenidMapService.selectBindInfoByTradeParentId(parentTradeId, null);
        if (!EmptyUtils.isEmpty(orderOpenidMapList)) {
            //that means: has bind already
            V2TaobaoOrderOpenidMapInfo orderOpenidMap = orderOpenidMapList.get(0);
            String oldOpenId = orderOpenidMap.getOpenId();
            Checks.isTrue(openId != null && openId.equals(oldOpenId), "已绑定的openId与当前提供的openId不一致");

            //返回已经绑定的信息
            orderBindResultVO.setOpenId(openId);
            orderBindResultVO.setSpecialId(null);
            orderBindResultVO.setTradeParentId(parentTradeId);
            List<String> tradeIdList = orderOpenidMapList.stream().map(a -> a.getTradeId()).collect(Collectors.toList());
            orderBindResultVO.getTradeIdItemIdList().addAll(tradeIdList);
            return orderBindResultVO;
        }

        //获取用户数据
        V2TaobaoUserInfo v2TaobaoUserInfo = v2TaobaoUserInfoService.selectByOpenId(openId);
        Checks.isNotNull(v2TaobaoUserInfo, "openid不存在");

        //干正事，绑定
        //首先查询订单
        List<V2TaobaoOrderDetailInfo> v2TaobaoOrderDetailInfos = v2TaobaoOrderService.selectByTradeId(parentTradeId, null);
        Checks.isNotEmpty(v2TaobaoOrderDetailInfos, "当前订单不存在，请稍后重试或确定是否通过本平台获取的购买链接");
        for (V2TaobaoOrderDetailInfo orderDetail : v2TaobaoOrderDetailInfos) {
            //先查询，万一存在，就得更新，防止操作错误
            insertOrUpdateOrderOpenidMap(openId, MapType.specified_openid_tradeparentid, v2TaobaoUserInfo, orderDetail);
        }

        //获取商品名称返回
        orderBindResultVO.setOpenId(openId);
        orderBindResultVO.setSpecialId(null);
        orderBindResultVO.setTradeParentId(parentTradeId);
        List<String> tradeIdList = v2TaobaoOrderDetailInfos.stream().map(a -> a.getTradeId()).collect(Collectors.toList());
        orderBindResultVO.getTradeIdItemIdList().addAll(tradeIdList);
        return orderBindResultVO;
    }

    /**
     * 指定一段时间，执行订单绑定
     * @param orderStartModifiedTime 订单的修改日期
     * @param minuteStep
     * @return
     */
    public List<OrderBindResultVO> bindByTimeRange(String orderStartModifiedTime, Long minuteStep) {
        //确定时间范围
        LocalDateTime startTime = TimeUtil.parseLocalDate(orderStartModifiedTime);
        String endTime = TimeUtil.formatLocalDate(startTime.plusMinutes(minuteStep));

        //查询
        List<V2TaobaoOrderDetailInfo> orderDetailList = v2TaobaoOrderService.selectByModifiedTimeRange(orderStartModifiedTime, endTime);

        //处理 - 有优化空间
        Map<String, OrderBindResultVO> tradeParentId2OrderBindResultVOMap = new HashMap<String, OrderBindResultVO>(16);
        List<String> allTradeParentIdList = orderDetailList.stream().map(a -> a.getTradeParentId()).distinct().collect(Collectors.toList());
        for (String tradeParentId : allTradeParentIdList) {
            //绑定
            OrderBindResultVO orderBindResultVO = bindByTradeParentId(tradeParentId);
            tradeParentId2OrderBindResultVOMap.put(tradeParentId, orderBindResultVO);
        }

        //结果返回
        return tradeParentId2OrderBindResultVOMap.values().stream().collect(Collectors.toList());
    }

    /**
     * 通过交易单号，进行绑定操作
     * @param parentTradeId 必传字段
     * @return
     */
    public OrderBindResultVO bindByTradeParentId(String parentTradeId) {
        OrderBindResultVO orderBindResultVO = new OrderBindResultVO();
        orderBindResultVO.setTradeParentId(parentTradeId);

        //首先查询订单
        List<V2TaobaoOrderDetailInfo> orderDetailList = v2TaobaoOrderService.selectByTradeId(parentTradeId, null);
        Map<String, V2TaobaoOrderDetailInfo> tradeId2OrderDetailMap = orderDetailList.stream().collect(Collectors.toMap(a -> a.getTradeId(), a -> a));

        //查询哪些已经绑定过了
        List<V2TaobaoOrderOpenidMapInfo> orderOpenidMapList = v2TaobaoOrderOpenidMapService.selectBindInfoByTradeParentId(parentTradeId, null);

        //如果已经存在了，那么先执行更新操作，如订单状态的演变等
        for (V2TaobaoOrderOpenidMapInfo orderOpenidMap : orderOpenidMapList) {
            updateOrderOpenidMapIfNeeded(orderOpenidMap, tradeId2OrderDetailMap.remove(orderOpenidMap.getTradeId()), orderBindResultVO);
        }

        //如果已经存在了，那么说明已经绑定过，这样可以直接复用原来的信息将剩下的数据绑定完毕
        if (!EmptyUtils.isEmpty(orderOpenidMapList)) {
            if (tradeId2OrderDetailMap.size() > 0) {
                createOrderOpenidMapBy(orderOpenidMapList.get(0), tradeId2OrderDetailMap, orderBindResultVO);
            }

            //内容
            orderBindResultVO.setOpenId(orderOpenidMapList.get(0).getOpenId());
            orderBindResultVO.getTradeIdItemIdList().addAll(orderDetailList.stream().map(i -> i.getTradeId()).collect(Collectors.toList()));
            return orderBindResultVO;
        }

        //所有的都没有绑定过 - 探测有没有转过链接
        BindOpenidInfo openidInfo = detectBindedOpenidInfoByConvertHistory(parentTradeId, orderDetailList);
        if (openidInfo == null) {
            v2TaobaoOrderOpenidMapFailureService.insertOrDoNoneOrderInfo(orderDetailList, "有多人转链接或无淘口令转换记录");
            return orderBindResultVO;
        }

        //有转链接的记录
        //通过商品&推广位来绑定
        bindByPubSite(openidInfo, parentTradeId, orderDetailList, orderBindResultVO);

        return orderBindResultVO;
    }

    /**
     * 普通用户，通过推广位和商品的ID去查询转链记录表，看是否转过
     * 通过这个方法的绑定，一定不是会员，就是那种普通的订单而已
     * @param openidInfo
     * @param parentTradeId
     * @param orderDetailList
     * @param orderBindResultVO
     */
    private void bindByPubSite(BindOpenidInfo openidInfo, String parentTradeId, List<V2TaobaoOrderDetailInfo> orderDetailList, OrderBindResultVO orderBindResultVO) {
        //根据openid查询用户信息
        V2TaobaoUserInfo userInfos = v2TaobaoUserInfoService.selectByOpenId(openidInfo.getOpenid());
        for (V2TaobaoOrderDetailInfo orderDetail : orderDetailList) {
            //获取mapType
            MapType mapType = MapType.pubsite;

            //用作匹配的那个id - 这里只有一种匹配方式
            /*if (openidInfo.getItemIds().contains(orderDetail.getItemId())) {
                mapType = MapType.pubsite;
            }
            else {
                mapType = MapType.one_item_pubsite_extend;
            }*/

            //先查询，万一存在，就得更新，防止操作错误
            insertOrUpdateOrderOpenidMap(openidInfo.getOpenid(), mapType, userInfos, orderDetail);

            //内容
            orderBindResultVO.setOpenId(userInfos.getOpenId());
            orderBindResultVO.setSpecialId(userInfos.getSpecialId());
            orderBindResultVO.getTradeIdItemIdList().add(orderDetail.getTradeId());
        }

    }

    /**
     * 根据给定的绑定关系，将已知的其他订单绑定到相同的用户信息上
     * 已知的用户绑定条件
     * 1、绑定到openid上，这个是主流的
     * 2、绑定到special上，这个是小众的
     * 3、绑定到openid和special上，这个就是正好可以关联上
     * @param orderOpenidMap
     * @param tradeId2OrderDetailMap
     * @param orderBindResultVO
     */
    private void createOrderOpenidMapBy(V2TaobaoOrderOpenidMapInfo orderOpenidMap, Map<String, V2TaobaoOrderDetailInfo> tradeId2OrderDetailMap, OrderBindResultVO orderBindResultVO) {
        //用户信息
        V2TaobaoUserInfo v2TaobaoUserInfo = v2TaobaoUserInfoService.selectByOpenId(orderOpenidMap.getOpenId());

        //循环每个订单，插入到绑定表中去
        Collection<V2TaobaoOrderDetailInfo> allOrderDetails = tradeId2OrderDetailMap.values();
        for (V2TaobaoOrderDetailInfo orderDetail : allOrderDetails) {
            //先查询，万一存在，就得更新，防止操作错误
            insertOrUpdateOrderOpenidMap(v2TaobaoUserInfo.getOpenId(), MapType.tradeparentid_extend, v2TaobaoUserInfo, orderDetail);
        }

        //内容
        orderBindResultVO.setOpenId(orderOpenidMap.getOpenId());
        orderBindResultVO.getTradeIdItemIdList().add(orderOpenidMap.getTradeId());
    }

    /**
     * 根据需要，做绑定的更新
     * 如果订单状态还没有结束，那么需要前向走，更新订单状态
     * 如果我们平台给用户的状态已经结算，或者关闭，那么不用更新
     * 每次更新的时候，主要更新一下其他字段（如预期返利）
     * 应该多个状态，就是如果实际已经返利给用户了，但是订单状态发生了商家维权，造成我们自己资损，需要记录下来实际给用户反了多少，后面要补回来
     *  @param orderOpenidMap
     * @param orderDetail
     * @param orderBindResultVO
     */
    private void updateOrderOpenidMapIfNeeded(V2TaobaoOrderOpenidMapInfo orderOpenidMap, V2TaobaoOrderDetailInfo orderDetail, OrderBindResultVO orderBindResultVO) {
        if (orderDetail == null) {
            return;
        }

        //维权标识 0 含义为非维权 1 含义为维权订单
        Integer refundTag = orderDetail.getRefundTag();
        //付款预估收入 - 是总的哦
        String pubSharePreFee = orderDetail.getPubSharePreFee();
        //结算预估收入 - 是总的哦
        String pubShareFee = orderDetail.getPubShareFee();

        //支付给阿里妈妈的费用
        String alimamaShareFee = orderDetail.getAlimamaShareFee();

        //当前状态 - 12-付款，13-关闭，14-确认收货，3-结算成功
        Integer tkStatus = orderDetail.getTkStatus();

        //上次的状态
        int lastTkStatus = orderOpenidMap.getOrderStatus();

        //判定 - 相同状态，不更新，订单已经关闭的也不更新
        if (tkStatus == lastTkStatus || lastTkStatus == 13) {
            return;
        }

        //判定 - 如果新状态为3，旧状态不是，那么更新
        boolean updateFlag = false;
        if (lastTkStatus != 3 && tkStatus == 3) {
            updateFlag = true;
        }

        //是否更新
        if (updateFlag) {
            orderOpenidMap.setOrderStatus(tkStatus);

            //结算时，应该给用户返利多少
            orderOpenidMap.setPubSharePreFee(pubSharePreFee);
            orderOpenidMap.setPubShareFee(pubShareFee);
            orderOpenidMap.setAlimamaShareFee(alimamaShareFee);

            //修改时间
            orderOpenidMap.setGmtModified(LocalDateTime.now());
            v2TaobaoOrderOpenidMapService.update(orderOpenidMap);
        }

        //内容
        orderBindResultVO.setOpenId(orderOpenidMap.getOpenId());
        orderBindResultVO.getTradeIdItemIdList().add(orderOpenidMap.getTradeId());
    }

    /**
     * 执行插入或者更新
     * @param openId
     * @param mapType
     * @param userInfos
     * @param orderDetail
     */
    private void insertOrUpdateOrderOpenidMap(String openId, MapType mapType, V2TaobaoUserInfo userInfos, V2TaobaoOrderDetailInfo orderDetail) {
        //先查询，万一存在，就得更新，防止操作错误
        V2TaobaoOrderOpenidMapInfo newOrderOpenidMap = null;
        List<V2TaobaoOrderOpenidMapInfo> orderOpenidMapList = v2TaobaoOrderOpenidMapService.selectBindInfoByTradeParentId(orderDetail.getTradeParentId(), orderDetail.getTradeId());
        if (!orderOpenidMapList.isEmpty()) {
            newOrderOpenidMap = orderOpenidMapList.get(0);
        }
        else {
            newOrderOpenidMap = new V2TaobaoOrderOpenidMapInfo();
            newOrderOpenidMap.setGmtCreated(LocalDateTime.now());
            newOrderOpenidMap.setTradeId(orderDetail.getTradeId());
            newOrderOpenidMap.setTradeParentId(orderDetail.getTradeParentId());
            newOrderOpenidMap.setCommissionStatusMsg("待提取");

            newOrderOpenidMap.setCommissionRatio(v2TaobaoCommissionRatioInfoService.selectCommissionRatio(openId));
        }

        //公共更新字段
        newOrderOpenidMap.setGmtModified(LocalDateTime.now());
        newOrderOpenidMap.setOpenId(openId);
        newOrderOpenidMap.setItemId(orderDetail.getItemId());
        newOrderOpenidMap.setPubSharePreFee(orderDetail.getPubSharePreFee());
        newOrderOpenidMap.setPubShareFee(orderDetail.getPubShareFee());
        newOrderOpenidMap.setAlimamaShareFee(orderDetail.getAlimamaShareFee());
        newOrderOpenidMap.setOrderStatus(orderDetail.getTkStatus());
        newOrderOpenidMap.setRefundTag(orderDetail.getRefundTag());

        //用作匹配的那个id
        newOrderOpenidMap.setMapTypeMsg(mapType.getLabel());
        newOrderOpenidMap.setStatus((byte) 0);

        //插入数据库
        if (orderOpenidMapList.isEmpty()) {
            int affectedNum = v2TaobaoOrderOpenidMapService.save(newOrderOpenidMap);
            Checks.isTrue(affectedNum == 1, "插入失败 - tradeId=" + orderDetail.getTradeId());
        }
        else {
            int affectedCnt = v2TaobaoOrderOpenidMapService.update(newOrderOpenidMap);
            Checks.isTrue(affectedCnt == 1, "更新失败 - tradeId=" + orderDetail.getTradeId());
        }
    }


    /**
     * 根据这个用户购买的所有订单，去确定，是那个微信用户在转码和购买
     * 限制：一定要是，相同的parentTradeId
     * 逻辑：多个商品，可能不一定是同一个推广位，但是如果找到一个没有任何歧义项的，那么可以认为，这里所有的商品都是一个用户的
     * @param orderDetailList
     * @param parentTradeId
     * @return
     */
    private BindOpenidInfo detectBindedOpenidInfoByConvertHistory(String parentTradeId, List<V2TaobaoOrderDetailInfo> orderDetailList) {
        //查询几天内的数据
        int days = 3;

        //openId
        String targetOpenId = null;
        Set<String> allPossibleOpenIdSet = null;

        //首先需要明确，有的没有转链接，而是跟随父订单过来的，所以如果查不到，需要循环，都查找一遍
        for (V2TaobaoOrderDetailInfo orderDetail : orderDetailList) {
            //商品ID
            String itemId = orderDetail.getItemId();

            //推广位
            String pubId = orderDetail.getPubId();
            String siteId = orderDetail.getSiteId();
            String adzoneId = orderDetail.getAdzoneId();
            String pubSite = String.format("mm_%s_%s_%s", pubId, siteId, adzoneId);

            //默认的推广位，手动使用，直接过滤掉
            if (pubSite.equalsIgnoreCase("mm_120037479_18622324_114562250311")) {
                targetOpenId = null;
                break;
            }

            //起止时间
            LocalDateTime clickTime = orderDetail.getClickTime();
            LocalDateTime startTime = clickTime.minusDays(days);
            LocalDateTime endTime = clickTime;

            //查询看看，是否有转链接记录 - 更改了逻辑，这里不可能只有一个转链记录了
            List<V2TaobaoTklConvertHistoryInfo> tklConvertHistories = v2TaobaoTklConvertHistoryService.selectByItemId(itemId, pubSite, startTime, endTime);
            if (tklConvertHistories.isEmpty()) {
                //没转过
                continue;
            }

            //判断，这个里面，是不是只有一个人转过链接
            List<String> allOpenIds = tklConvertHistories.stream().map(a -> a.getOpenId()).filter(a -> !a.trim().isEmpty()).distinct().collect(Collectors.toList());
            if (allOpenIds.size() == 1) {
                targetOpenId = allOpenIds.get(0);
                break;
            }

            //加入
            if (allPossibleOpenIdSet == null) {
                allPossibleOpenIdSet = new HashSet<>();
                allPossibleOpenIdSet.addAll(allOpenIds);
            }
            else {
                allPossibleOpenIdSet.retainAll(allOpenIds);

                //加一层判定
                if (allPossibleOpenIdSet.size() == 1) {
                    targetOpenId = allPossibleOpenIdSet.stream().findFirst().get();
                    break;
                }
            }
        }

        //没有唯一可用的
        if (targetOpenId == null) {
            return null;
        }

        //存在，那么同一绑定到一起
        List<String> itemIds = orderDetailList.stream().map(a -> a.getItemId()).collect(Collectors.toList());
        BindOpenidInfo openidInfo = BindOpenidInfo.build(targetOpenId, itemIds);
        return openidInfo;
    }

    /**
     * 绑定维权的退回金额
     * @param tradeId
     * @param refundFeeStr
     * @return
     */
    public void recordRefundFee(String tradeId, String refundFeeStr) {
        List<V2TaobaoOrderOpenidMapInfo> orderOpenidMapList = v2TaobaoOrderOpenidMapService.selectBindInfoByTradeParentId(null, tradeId);
        Checks.isTrue(orderOpenidMapList.size() == 1, "订单不唯一，更新失败");

        //验证
        V2TaobaoOrderOpenidMapInfo orderOpenidMap = orderOpenidMapList.get(0);

        //数值判定
        BigDecimal refundFee = NumberUtil.parseBigDecimal(refundFeeStr);
        Checks.isTrue(refundFee != null && refundFee.compareTo(new BigDecimal("0")) > 0, "给定的维权金额不正确，必须大于0");

        //更新
        orderOpenidMap.setRefundFee(refundFeeStr);
        orderOpenidMap.setGmtModified(LocalDateTime.now());
        int affectedCnt = v2TaobaoOrderOpenidMapService.update(orderOpenidMap);
        Checks.isTrue(affectedCnt == 1, "更新失败，影响条数:" + affectedCnt);

        // 如果已经结算成功了，那么需要更新到总账户中，并产生流水
        String openId = orderOpenidMap.getOpenId();
        v2TaobaoCommissionAccountService.computeOrderRefundFeeToAccountForOpenId(openId, orderOpenidMapList);
    }

    /**
     * 按照推广位，查找的绑定信息
     */
    public static class BindOpenidInfo {
        private String openid;
        private List<String> itemIds;

        public BindOpenidInfo(String openid, List<String> itemIds) {
            this.itemIds = itemIds;
            this.openid = openid;
        }

        public static BindOpenidInfo build(String openid, List<String> itemIds) {
            return new BindOpenidInfo(openid, itemIds);
        }

        public List<String> getItemIds() {
            return itemIds;
        }

        public void setItemIds(List<String> itemIds) {
            this.itemIds = itemIds;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }
    }

    /**
     * 订单绑定的映射类型
     */
    public enum MapType {
        //显式绑定，通过指定交易单号和openid，使其绑定到一起；
        specified_openid_tradeparentid("specified_openid_tradeparentid"),

        //有商品已经绑定了，通过父单id，将其他的子单绑定到相同的用于信息身上
        tradeparentid_extend("tradeparentid_extend"),

        //单纯通过推广位，绑定openid和订单号的关系
        pubsite("pubsite"),

        //通过一个商品的转链用的推广位信息，将其他的都帮挡到一个用户openid上
        one_item_pubsite_extend("one_item_pubsite_extend"),

        //是会员，转过码，以special信息为准，查找用户信息，并存到映射表中去。
        specialid_with_pubsite("specialid_with_pubsite"),

        //无转码，但是作为会员，独立将当担绑定到specialid上
        specialid("specialid");

        private String label;

        MapType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
