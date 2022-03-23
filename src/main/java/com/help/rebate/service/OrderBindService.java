package com.help.rebate.service;

import com.help.rebate.dao.OrderDetailDao;
import com.help.rebate.dao.OrderOpenidMapDao;
import com.help.rebate.dao.TklConvertHistoryDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.service.schedule.FixedOrderSyncTask;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单绑定服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class OrderBindService {
    private static final Logger logger = LoggerFactory.getLogger(OrderBindService.class);

    /**
     * 订单详情接口
     */
    @Resource
    private OrderDetailDao orderDetailDao;
    @Resource
    private OrderService orderService;

    /**
     * 转链历史记录表
     */
    @Resource
    private TklConvertHistoryDao tklConvertHistoryDao;
    @Resource
    private TklConvertHistoryService tklConvertHistoryService;

    /**
     * 用户信息服务，主要用于对用户信息表的更新操作
     */
    @Resource
    private UserInfosService userInfosService;

    /**
     * 订单映射表
     */
    @Resource
    private OrderOpenidMapDao orderOpenidMapDao;
    @Resource
    private OrderOpenidMapService orderOpenidMapService;

    /**
     * 用户通过前端，直接发送过来的，期望绑定的订单
     * 如果按照订单，没有查到，那么给用户报相应提示，这里不做任何缓存，提示用户稍后再试，因为可能还没来得及订单同步，或者订单输入错误了
     * @param parentTradeId
     * @param openId
     * @param specialId 额外给出的信息，用于将openid和specialid做强制映射，这个只是给管理员使用
     * @param externalId 额外给出的信息，用于将openid和externalid做强制映射，这个只是给管理员用
     * @return 商品名称列表
     */
    public List<String> bindByTradeId(String parentTradeId, String openId, String specialId, String externalId) {
        //获取用户数据
        UserInfos userInfos = userInfosService.selectByOpenId(openId);
        Checks.isNotNull(userInfos, "openid不存在");

        //新的special和旧的不相同
        String infosSpecialId = userInfos.getSpecialId();
        String infosExternalId = userInfos.getExternalId();
        Checks.isTrue(EmptyUtils.isEmpty(specialId) || EmptyUtils.isEmpty(infosSpecialId) || specialId.equals(infosSpecialId), "special已经存在，并且与给定的不相同");
        Checks.isTrue(EmptyUtils.isEmpty(externalId) || EmptyUtils.isEmpty(infosExternalId) || specialId.equals(infosExternalId), "special已经存在，并且与给定的不相同");

        //更新数据库
        boolean needUpdate = false;
        if (!EmptyUtils.isEmpty(specialId)) {
            needUpdate = true;
            userInfos.setSpecialId(specialId);
        }
        if (!EmptyUtils.isEmpty(externalId)) {
            needUpdate = true;
            userInfos.setExternalId(externalId);
        }
        if (needUpdate) {
            int affectedCnt = userInfosService.update(userInfos);
            Checks.isTrue(affectedCnt == 1, "更新用户信息失败");
        }

        //干正事，绑定
        //首先查询订单
        List<OrderDetail> orderDetailList = orderService.selectByTradeId(parentTradeId, null);
        Checks.isNotEmpty(orderDetailList, "当前订单不存在，请稍后重试或确定是否通过本平台获取的购买链接");
        for (OrderDetail orderDetail : orderDetailList) {
            OrderOpenidMap newOrderOpenidMap = new OrderOpenidMap();
            newOrderOpenidMap.setGmtCreated(new Date());
            newOrderOpenidMap.setGmtModified(new Date());
            newOrderOpenidMap.setTradeId(orderDetail.getTradeId());
            newOrderOpenidMap.setParentTradeId(orderDetail.getParentTradeId());
            newOrderOpenidMap.setOpenId(userInfos.getOpenId());
            newOrderOpenidMap.setExternalId(userInfos.getExternalId());
            newOrderOpenidMap.setSpecialId(userInfos.getSpecialId());
            newOrderOpenidMap.setRelationId(userInfos.getRelationId());
            newOrderOpenidMap.setItemId(orderDetail.getItemId());
            newOrderOpenidMap.setPubSharePreFee(orderDetail.getPubSharePreFee());
            newOrderOpenidMap.setPubShareFee(orderDetail.getPubShareFee());
            newOrderOpenidMap.setAlimamaShareFee(orderDetail.getAlimamaShareFee());
            newOrderOpenidMap.setOrderStatus(orderDetail.getTkStatus());
            newOrderOpenidMap.setCommissionStatus("待结算");
            newOrderOpenidMap.setRefundTag(orderDetail.getRefundTag());

            newOrderOpenidMap.setMapType("by-tradeId");
            newOrderOpenidMap.setStatus(0);

            //插入数据库
            int affectedNum = orderOpenidMapService.save(newOrderOpenidMap);
            Checks.isTrue(affectedNum == 1, "插入失败 - tradeId=" + orderDetail.getTradeId());
        }

        //获取商品名称返回
        return orderDetailList.stream().map(a -> a.getItemTitle()).collect(Collectors.toList());
    }


    /**
     * 通过交易单号，进行绑定操作
     * @param parentTradeId 必传字段
     * @param tradeIds 可传可不传
     * @return
     */
    public void bindByTradeId(String parentTradeId, String... tradeIds) {
        //首先查询订单
        List<OrderDetail> orderDetailList = orderService.selectByTradeId(parentTradeId, null);
        Map<String, OrderDetail> tradeId2OrderDetailMap = orderDetailList.stream().collect(Collectors.toMap(a -> a.getTradeId(), a -> a));

        //查询哪些已经绑定过了
        List<OrderOpenidMap> orderOpenidMapList = orderOpenidMapService.selectByTradeId(parentTradeId, null);

        //如果已经存在了，那么先执行更新操作，如订单状态的演变等
        for (OrderOpenidMap orderOpenidMap : orderOpenidMapList) {
            updateOrderOpenidMapIfNeeded(orderOpenidMap, tradeId2OrderDetailMap.remove(orderOpenidMap.getTradeId()));
        }

        //如果已经存在了，那么说明已经绑定过，这样可以直接复用原来的信息将剩下的数据绑定完毕
        if (!EmptyUtils.isEmpty(orderOpenidMapList) && tradeId2OrderDetailMap.size() > 0) {
            createOrderOpenidMapBy(orderOpenidMapList.get(0), tradeId2OrderDetailMap);
            return;
        }

        //所有的都没有绑定过
        String specialId = orderDetailList.get(0).getSpecialId();
        //按照普通用户的方式来处理
        if (EmptyUtils.isEmpty(specialId)) {
            //通过商品&推广位来绑定
            bindByPubSite(orderDetailList);
        }
        else {
            bindBySpecialId(orderDetailList);
        }

        return;
    }

    /**
     * 根据需要，做绑定的更新
     * 如果订单状态还没有结束，那么需要前向走，更新订单状态
     * 如果我们平台给用户的状态已经结算，或者关闭，那么不用更新
     * 每次更新的时候，主要更新一下其他字段（如预期返利）
     * 应该多个状态，就是如果实际已经返利给用户了，但是订单状态发生了商家维权，造成我们自己资损，需要记录下来实际给用户反了多少，后面要补回来
     *
     * @param orderOpenidMap
     * @param orderDetail
     */
    private void updateOrderOpenidMapIfNeeded(OrderOpenidMap orderOpenidMap, OrderDetail orderDetail) {
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
        //判定 - 如果旧状态是付款、确认收货，或者新状态为关闭，那么更新
        else if (lastTkStatus == 12 && (tkStatus == 13 || tkStatus == 14 || tkStatus == 3)) {
            updateFlag = true;
        }
        else if (lastTkStatus == 14 && (tkStatus == 13 || tkStatus == 3)) {
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
            orderOpenidMap.setGmtModified(new Date(System.currentTimeMillis()));
            orderOpenidMapService.update(orderOpenidMap);
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
     */
    private void createOrderOpenidMapBy(OrderOpenidMap orderOpenidMap, Map<String, OrderDetail> tradeId2OrderDetailMap) {
        //循环每个订单，插入到绑定表中去
        Collection<OrderDetail> allOrderDetails = tradeId2OrderDetailMap.values();
        for (OrderDetail orderDetail : allOrderDetails) {
            OrderOpenidMap newOrderOpenidMap = new OrderOpenidMap();
            newOrderOpenidMap.setGmtCreated(new Date());
            newOrderOpenidMap.setGmtModified(new Date());
            newOrderOpenidMap.setTradeId(orderDetail.getTradeId());
            newOrderOpenidMap.setParentTradeId(orderDetail.getParentTradeId());
            newOrderOpenidMap.setOpenId(orderOpenidMap.getOpenId());
            newOrderOpenidMap.setExternalId(orderOpenidMap.getExternalId());
            newOrderOpenidMap.setSpecialId(orderOpenidMap.getSpecialId());
            newOrderOpenidMap.setRelationId(orderOpenidMap.getRelationId());
            newOrderOpenidMap.setItemId(orderDetail.getItemId());
            newOrderOpenidMap.setPubSharePreFee(orderDetail.getPubSharePreFee());
            newOrderOpenidMap.setPubShareFee(orderDetail.getPubShareFee());
            newOrderOpenidMap.setAlimamaShareFee(orderDetail.getAlimamaShareFee());
            newOrderOpenidMap.setOrderStatus(orderDetail.getTkStatus());
            newOrderOpenidMap.setCommissionStatus("待结算");
            newOrderOpenidMap.setRefundTag(orderDetail.getRefundTag());
            newOrderOpenidMap.setMapType("extend");
            newOrderOpenidMap.setStatus(0);

            //插入数据库
            int affectedNum = orderOpenidMapService.save(newOrderOpenidMap);
            Checks.isTrue(affectedNum == 1, "插入失败 - tradeId=" + orderDetail.getTradeId());
        }
    }

    /**
     * 普通用户，通过推广位和商品的ID去查询转链记录表，看是否转过
     * 通过这个方法的绑定，一定不是会员，就是那种普通的订单而已
     * @param orderDetailList
     */
    private void bindByPubSite(List<OrderDetail> orderDetailList) {
        BindOpenidInfo openidInfo = resolveBindOpenidInfo(orderDetailList);
        if (openidInfo == null) {
            return;
        }

        //根据openid查询用户信息
        UserInfos userInfos = userInfosService.selectByOpenId(openidInfo.getOpenid());
        for (OrderDetail orderDetail : orderDetailList) {
            OrderOpenidMap newOrderOpenidMap = new OrderOpenidMap();
            newOrderOpenidMap.setGmtCreated(new Date());
            newOrderOpenidMap.setGmtModified(new Date());
            newOrderOpenidMap.setTradeId(orderDetail.getTradeId());
            newOrderOpenidMap.setParentTradeId(orderDetail.getParentTradeId());
            newOrderOpenidMap.setOpenId(openidInfo.getOpenid());
            newOrderOpenidMap.setExternalId(userInfos.getExternalId());
            newOrderOpenidMap.setSpecialId(userInfos.getSpecialId());
            newOrderOpenidMap.setRelationId(userInfos.getRelationId());
            newOrderOpenidMap.setItemId(orderDetail.getItemId());
            newOrderOpenidMap.setPubSharePreFee(orderDetail.getPubSharePreFee());
            newOrderOpenidMap.setPubShareFee(orderDetail.getPubShareFee());
            newOrderOpenidMap.setAlimamaShareFee(orderDetail.getAlimamaShareFee());
            newOrderOpenidMap.setOrderStatus(orderDetail.getTkStatus());
            newOrderOpenidMap.setCommissionStatus("待结算");
            newOrderOpenidMap.setRefundTag(orderDetail.getRefundTag());

            //用作匹配的那个id
            if (openidInfo.getItemIds().contains(orderDetail.getItemId())) {
                newOrderOpenidMap.setMapType("pubsite");
            }
            else {
                newOrderOpenidMap.setMapType("extend");
            }

            newOrderOpenidMap.setStatus(0);

            //插入数据库
            int affectedNum = orderOpenidMapService.save(newOrderOpenidMap);
            Checks.isTrue(affectedNum == 1, "插入失败 - tradeId=" + orderDetail.getTradeId());
        }

    }

    /**
     * 会员用户，通过specialId进行绑定
     * @param orderDetailList
     */
    private void bindBySpecialId(List<OrderDetail> orderDetailList) {
        //第一种，这里的所有商品，至少有被转链过，那么查出来，那么这种情况，是可以建立openid和specialid的关系并存入用户表的
        BindOpenidInfo openidInfo = resolveBindOpenidInfo(orderDetailList);
        if (openidInfo != null) {
            //起始这里有个问题，如果转码是A通过微信转的，但是发给了B去买，B正好是会员，此时是不可以将openId和specialId识别为一对的
            //所以此时，就将mapType记录一下，openId-specialId
            String specialIdByOrderDetail = orderDetailList.get(0).getSpecialId();
            UserInfos userInfosBySpecialId = userInfosService.selectBySpecialId(specialIdByOrderDetail);

            //判定一下
            UserInfos userInfos = userInfosService.selectByOpenId(openidInfo.getOpenid());
            String specialIdByUserInfo = userInfos.getSpecialId();

            //存储
            for (OrderDetail orderDetail : orderDetailList) {
                OrderOpenidMap newOrderOpenidMap = new OrderOpenidMap();
                newOrderOpenidMap.setGmtCreated(new Date());
                newOrderOpenidMap.setGmtModified(new Date());
                newOrderOpenidMap.setTradeId(orderDetail.getTradeId());
                newOrderOpenidMap.setParentTradeId(orderDetail.getParentTradeId());
                newOrderOpenidMap.setOpenId(userInfos.getOpenId());
                newOrderOpenidMap.setExternalId(userInfosBySpecialId.getExternalId());
                newOrderOpenidMap.setSpecialId(userInfosBySpecialId.getSpecialId());
                newOrderOpenidMap.setRelationId(userInfosBySpecialId.getRelationId());
                newOrderOpenidMap.setItemId(orderDetail.getItemId());
                newOrderOpenidMap.setPubSharePreFee(orderDetail.getPubSharePreFee());
                newOrderOpenidMap.setPubShareFee(orderDetail.getPubShareFee());
                newOrderOpenidMap.setAlimamaShareFee(orderDetail.getAlimamaShareFee());
                newOrderOpenidMap.setOrderStatus(orderDetail.getTkStatus());
                newOrderOpenidMap.setCommissionStatus("待结算");
                newOrderOpenidMap.setRefundTag(orderDetail.getRefundTag());
                newOrderOpenidMap.setMapType("openId-specialId");

                newOrderOpenidMap.setStatus(0);

                //插入数据库
                int affectedNum = orderOpenidMapService.save(newOrderOpenidMap);
                Checks.isTrue(affectedNum == 1, "插入失败 - tradeId=" + orderDetail.getTradeId());
            }

            return;
        }

        //第二种，这里所有的商品，都没有被转链过，那么只能存入specialid字段，其他openid这些数据不填写，mapType就是specialid，表示只是会员
        UserInfos userInfos = userInfosService.selectBySpecialId(orderDetailList.get(0).getSpecialId());
        for (OrderDetail orderDetail : orderDetailList) {
            OrderOpenidMap newOrderOpenidMap = new OrderOpenidMap();
            newOrderOpenidMap.setGmtCreated(new Date());
            newOrderOpenidMap.setGmtModified(new Date());
            newOrderOpenidMap.setTradeId(orderDetail.getTradeId());
            newOrderOpenidMap.setParentTradeId(orderDetail.getParentTradeId());
            newOrderOpenidMap.setOpenId(userInfos.getOpenId());
            newOrderOpenidMap.setExternalId(userInfos.getExternalId());
            newOrderOpenidMap.setSpecialId(userInfos.getSpecialId());
            newOrderOpenidMap.setRelationId(userInfos.getRelationId());
            newOrderOpenidMap.setItemId(orderDetail.getItemId());
            newOrderOpenidMap.setPubSharePreFee(orderDetail.getPubSharePreFee());
            newOrderOpenidMap.setPubShareFee(orderDetail.getPubShareFee());
            newOrderOpenidMap.setAlimamaShareFee(orderDetail.getAlimamaShareFee());
            newOrderOpenidMap.setOrderStatus(orderDetail.getTkStatus());
            newOrderOpenidMap.setCommissionStatus("待结算");
            newOrderOpenidMap.setRefundTag(orderDetail.getRefundTag());
            newOrderOpenidMap.setMapType("specialId");

            newOrderOpenidMap.setStatus(0);

            //插入数据库
            int affectedNum = orderOpenidMapService.save(newOrderOpenidMap);
            Checks.isTrue(affectedNum == 1, "插入失败 - tradeId=" + orderDetail.getTradeId());
        }
    }

    /**
     * 根据这个用户购买的所有订单，去确定，是那个微信用户在转码和购买
     * @param orderDetailList
     * @return
     */
    private BindOpenidInfo resolveBindOpenidInfo(List<OrderDetail> orderDetailList) {
        //查询几天内的数据
        int days = 7;

        //首先需要明确，有的没有转链接，而是跟随父订单过来的，所以如果查不到，需要循环，都查找一遍
        Map<Integer, List<String>> convertNum2ItemsMap = new HashMap<Integer, List<String>>(16, 1);
        Map<String, List<TklConvertHistory>> item2ConvertHistoryMap = new HashMap<>(16, 1);
        for (OrderDetail orderDetail : orderDetailList) {
            //商品ID
            String itemId = orderDetail.getItemId();

            //推广位
            String pubId = orderDetail.getPubId();
            String siteId = orderDetail.getSiteId();
            String adzoneId = orderDetail.getAdzoneId();
            String pubSite = String.format("mm_%s_%s_%s", pubId, siteId, adzoneId);

            //起止时间
            Date clickTime = orderDetail.getClickTime();
            Date startTime = new Date(clickTime.getTime() - days * 24 * 3600000);
            Date endTime = clickTime;

            //查询看看，是否有转链接记录
            List<TklConvertHistory> tklConvertHistories = tklConvertHistoryService.selectByItemId(itemId, pubSite, startTime, endTime);
            item2ConvertHistoryMap.put(itemId, tklConvertHistories);
            if (!EmptyUtils.isEmpty(tklConvertHistories)) {
                List<String> itemIds = convertNum2ItemsMap.getOrDefault(tklConvertHistories.size(), new ArrayList<>());
                itemIds.add(itemId);
                convertNum2ItemsMap.put(tklConvertHistories.size(), itemIds);
            }
            else {
                List<String> itemIds = convertNum2ItemsMap.getOrDefault(0, new ArrayList<>());
                itemIds.add(itemId);
                convertNum2ItemsMap.put(0, itemIds);
            }
        }

        //convertNum2ItemsMap判定，看看里面有没有只有一个的
        List<String> itemIds = convertNum2ItemsMap.get(1);
        if (itemIds == null) {
            //说明，要么有歧义，要么没有记录，这里需要记录一下日志
            logger.warn("[order-bind] fail to bind by tradeParentId[{}]", orderDetailList.get(0).getParentTradeId());
            return null;
        }

        //存在，那么同一绑定到一起
        String matchItemId = itemIds.get(0);
        TklConvertHistory tklConvertHistory = item2ConvertHistoryMap.get(matchItemId).get(0);
        String openId = tklConvertHistory.getOpenId();
        BindOpenidInfo openidInfo = BindOpenidInfo.build(openId, itemIds);
        return openidInfo;
    }

    /**
     * 按照推广位，查找的绑定信息
     */
    public static class BindOpenidInfo {
        private List<String> itemIds;
        private String openid;

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
}