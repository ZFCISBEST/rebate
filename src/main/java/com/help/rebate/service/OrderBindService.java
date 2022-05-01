package com.help.rebate.service;

import com.help.rebate.dao.OrderDetailDao;
import com.help.rebate.dao.OrderOpenidMapDao;
import com.help.rebate.dao.TklConvertHistoryDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.service.schedule.FixedOrderBindSyncTask;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.NumberUtil;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.vo.CommissionVO;
import com.help.rebate.vo.OrderBindResultVO;
import com.help.rebate.vo.PickCommissionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
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
    @Resource
    private OrderOpenidMapFailureService orderOpenidMapFailureService;

    /**
     * 订单绑定的自动同步任务
     */
    @Resource
    private FixedOrderBindSyncTask fixedOrderBindSyncTask;

    /**
     * 时间游标记录
     */
    @Resource
    private TimeCursorPositionService timeCursorPositionService;

    /**
     * 提取服务
     */
    @Resource
    private PickMoneyRecordService pickMoneyRecordService;

    /**
     * 模拟提取的流程
     * @param openId
     * @param specialId
     * @param mockStatus 当前模拟的是哪种状态 - 触发提取、待提取、提取中，提取成功，提取失败, 提取超时
     * @return
     */
    @Transactional
    public PickCommissionVO mockPickMoney(String openId, String specialId, String mockStatus) {
        //触发提取操作
        if ("触发提取".equals(mockStatus)) {
            //触发
            return triggerPickMoneyAction(openId, specialId);
        }

        //触发提取成功 - 最新状态的 - 提取失败（包括，提取超时）
        if (EmptyUtils.isIn(mockStatus, new String[]{"提取成功", "提取失败", "提取超时"})) {
            return triggerEndPickMoneyAction(openId, specialId, mockStatus);
        }

        return null;
    }

    /**
     * 触发提取操作
     * 只有状态为 3 的，可以提取
     * @param openId
     * @param specialId
     * @return
     */
    @Transactional
    public PickCommissionVO triggerPickMoneyAction(String openId, String specialId) {
        //如果是触发提取操作，那么先看，之前是否有提取中的状态，或者有失败的，提取中，不允许再提，失败的，那么重试之前的就行，直到成功
        PickMoneyRecord oldPickMoneyRecord = pickMoneyRecordService.selectPickMoneyAction(openId, specialId, new String[]{"提取中"});
        if (oldPickMoneyRecord != null) {
            CommissionVO commissionVO = orderOpenidMapService.selectCommissionBy(openId, specialId, "3", new String[]{"提取中"});
            //直接返回查询的统计信息
            PickCommissionVO pickCommissionVO = new PickCommissionVO();
            pickCommissionVO.setAction("重复 - 提取中");
            pickCommissionVO.setCommission(commissionVO.getPubFee());
            pickCommissionVO.setTradeParentId2TradeIdsMap(commissionVO.getTradeParentId2ItemIdsMap());
            return pickCommissionVO;
        }

        //订单状态 - 12-付款，13-关闭，14-确认收货，3-结算成功
        CommissionVO commissionVO = orderOpenidMapService.selectCommissionBy(openId, specialId, "3", new String[]{"待提取", "提取失败", "提取超时"});
        if (commissionVO == null || commissionVO.getPubFee() == null || "0.0".equals(commissionVO.getPubFee()) || commissionVO.getTradeParentId2ItemIdsMap().isEmpty()) {
            PickCommissionVO pickCommissionVO = new PickCommissionVO();
            pickCommissionVO.setAction("提取中 - [可提取订单为0]");
            pickCommissionVO.setCommission("0.0");
            pickCommissionVO.setTradeParentId2TradeIdsMap(null);
            return pickCommissionVO;
        }

        //可提取的金额
        String pubFee = commissionVO.getPubFee();
        Map<String, List<String>> tradeParentId2ItemIdsMap = commissionVO.getTradeParentId2ItemIdsMap();

        //第一步，插入数据库，记录提取这个动作
        Integer primaryKey = pickMoneyRecordService.recordPickMoneyAction(openId, specialId, pubFee, "提取中");
        Checks.isTrue(primaryKey != null, "记录提取动作失败");

        //第二步，更新订单表，记录所有的为提取中
        int affectedCnt = orderOpenidMapService.changeCommissionStatusByTradeParentIds(openId, specialId, tradeParentId2ItemIdsMap, primaryKey, "提取中");
        int allTradeIdCnt = tradeParentId2ItemIdsMap.values().stream().mapToInt(a -> a.size()).sum();
        Checks.isTrue(affectedCnt == allTradeIdCnt, "更新的记录数，与查询出的记录数不一致");

        //第三部，更新提取记录表，记录一下附加信息，影响了多少订单数
        PickMoneyRecord pickMoneyRecord = new PickMoneyRecord();
        pickMoneyRecord.setId(primaryKey);
        pickMoneyRecord.setPickAttachInfo("all_trade_id_cnt:" + affectedCnt);
        pickMoneyRecordService.update(pickMoneyRecord);

        //直接返回查询的统计信息
        PickCommissionVO pickCommissionVO = new PickCommissionVO();
        pickCommissionVO.setAction("提取中");
        pickCommissionVO.setCommission(pubFee);
        pickCommissionVO.setTradeParentId2TradeIdsMap(tradeParentId2ItemIdsMap);
        return pickCommissionVO;
    }

    /**
     * 触发提取操作的后续操作
     * @param openId
     * @param specialId
     * @param mockStatus 待提取、提取中，提取成功，提取失败,提取超时
     * @return
     */
    private PickCommissionVO triggerEndPickMoneyAction(String openId, String specialId, String mockStatus) {
        //先查询出最新的状态，只能存在一个唯一的提取状态。只有提取中的状态，才可以触发其他操作
        PickMoneyRecord pickMoneyRecord = pickMoneyRecordService.selectByStatus(openId, specialId, "提取中");
        if (pickMoneyRecord == null) {
            PickCommissionVO pickCommissionVO = new PickCommissionVO();
            pickCommissionVO.setAction(mockStatus + "- [无'提取中'的提现记录]");
            pickCommissionVO.setCommission("0.0");
            pickCommissionVO.setTradeParentId2TradeIdsMap(null);
            return pickCommissionVO;
        }

        //获取附加信息
        String pickAttachInfo = pickMoneyRecord.getPickAttachInfo();

        //更新状态
        Integer pickMoneyRecordId = pickMoneyRecord.getId();
        pickMoneyRecord.setGmtModified(new Date());
        pickMoneyRecord.setPickStatus(mockStatus);
        pickMoneyRecord.setActPickCommission(pickMoneyRecord.getPrePickCommission());
        int affectedCnt = pickMoneyRecordService.update(pickMoneyRecord);
        logger.error("[pick-money] fail to change status to {}, pick_id:{}, openId:{}, specialId:{}", mockStatus, pickMoneyRecordId, openId, specialId);
        Checks.isTrue(affectedCnt == 1, "更新提取状态失败");

        //更新订单绑定表中的状态
        int affectedMapCnt = orderOpenidMapService.changeCommissionStatusByPickMoneyId(openId, specialId, pickMoneyRecordId, mockStatus);
        Checks.isTrue(pickAttachInfo.equals("all_trade_id_cnt:" + affectedMapCnt), "提取时的详细订单记录与当前更新个数不符");

        //订单状态 - 12-付款，13-关闭，14-确认收货，3-结算成功
        CommissionVO commissionVO = orderOpenidMapService.selectCommissionBy(openId, specialId, "3", new String[]{mockStatus});
        PickCommissionVO pickCommissionVO = new PickCommissionVO();
        pickCommissionVO.setAction(mockStatus);
        pickCommissionVO.setCommission(commissionVO.getPubFee());
        pickCommissionVO.setTradeParentId2TradeIdsMap(commissionVO.getTradeParentId2ItemIdsMap());
        return pickCommissionVO;
    }

    /**
     * 指定起始时间，周期性调度，执行订单绑定
     * @param orderBindTime 这个时间，应该是订单的更新时间
     * @param minuteStep
     * @param running
     * @return
     */
    public boolean syncBindOrderByTimeStart(String orderBindTime, Long minuteStep, Boolean running) {
        //如果不运行
        if (!running) {
            fixedOrderBindSyncTask.setRunning(false);
            return true;
        }

        //运行的话，更新时间游标数据库
        if (orderBindTime != null && minuteStep != null) {
            Date startTime = TimeUtil.parseDate(orderBindTime);
            int secondStep = new Long(minuteStep * 60).intValue();
            timeCursorPositionService.saveOrUpdateTimeCursor(startTime, secondStep, null, null, TimeCursorPositionService.TimeType.ORDER_BIND_SYNC);
        }

        //调用执行
        fixedOrderBindSyncTask.cleanContext(orderBindTime, minuteStep, running);
        return true;
    }

    /**
     * 指定一段时间，执行订单绑定
     * @param openId 微信openId，用于圈定范围，可不指定
     * @param specialId 淘宝联盟私域会员ID，用于圈定范围，可不指定
     * @param orderBindTime
     * @param minuteStep
     * @return
     */
    public List<OrderBindResultVO> bindByTimeRange(String openId, String specialId, String orderBindTime, Long minuteStep) {
        //看一下openId，暂时不用

        //确定时间范围
        Date startTime = TimeUtil.parseDate(orderBindTime);
        long endTimestamp = startTime.getTime() + minuteStep * 60 * 1000;
        long currentTimeMillis = System.currentTimeMillis();
        if (endTimestamp > currentTimeMillis) {
            endTimestamp = currentTimeMillis;
        }
        String endTime = TimeUtil.format(endTimestamp);

        //查询
        OrderDetailExample example = new OrderDetailExample();
        OrderDetailExample.Criteria criteria = example.createCriteria();
        criteria.andModifiedTimeGreaterThanOrEqualTo(orderBindTime);
        criteria.andModifiedTimeLessThanOrEqualTo(endTime);
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andSpecialIdEqualTo(specialId);
        }

        //订单的付款时间，也必须不能晚于当前的时间
        criteria.andTbPaidTimeGreaterThanOrEqualTo(TimeUtil.parseDate("2021-12-25 00:00:00"));

        //查询数量
        long orderNum = orderDetailDao.countByExample(example);
        if (orderNum == 0) {
            return Collections.EMPTY_LIST;
        }

        //确定循环次数
        Map<String, OrderBindResultVO> parentTradeId2OrderBindResultVOMap = new HashMap<String, OrderBindResultVO>(16);
        long offset = 0;
        while (offset < orderNum) {
            example.setOffset(offset);
            example.setLimit(100);

            //查询
            List<OrderDetail> orderDetailList = orderDetailDao.selectByExample(example);

            //判定
            if (EmptyUtils.isEmpty(orderDetailList)) {
                //增加offset
                offset += 100;
                continue;
            }

            //处理 - 有优化空间
            List<String> allParentTradeIdList = orderDetailList.stream().map(a -> a.getParentTradeId()).distinct().collect(Collectors.toList());
            for (String parentTradeId : allParentTradeIdList) {
                OrderBindResultVO orderBindResultVO = bindByTradeId(parentTradeId, null);
                parentTradeId2OrderBindResultVOMap.put(parentTradeId, orderBindResultVO);
            }

            //增加offset
            offset += 100;
        }

        //结果返回
        return parentTradeId2OrderBindResultVOMap.values().stream().collect(Collectors.toList());
    }

    /**
     * 用户通过前端，直接发送过来的，期望绑定的订单
     * 如果按照订单，没有查到，那么给用户报相应提示，这里不做任何缓存，提示用户稍后再试，因为可能还没来得及订单同步，或者订单输入错误了
     * @param parentTradeId
     * @param openId
     * @param specialId 额外给出的信息，用于将openid和specialid做强制映射，这个只是给管理员使用
     * @param externalId 额外给出的信息，用于将openid和externalid做强制映射，这个只是给管理员用
     * @return 商品名称列表
     */
    public OrderBindResultVO bindByTradeId(String parentTradeId, String openId, String specialId, String externalId) {
        OrderBindResultVO orderBindResultVO = new OrderBindResultVO();

        //首先看看，是不是已经绑定过了
        List<OrderOpenidMap> orderOpenidMapList = orderOpenidMapService.selectByTradeId(parentTradeId, null);
        if (!EmptyUtils.isEmpty(orderOpenidMapList)) {
            //that means: has bind already
            OrderOpenidMap orderOpenidMap = orderOpenidMapList.get(0);
            String oldOpenId = orderOpenidMap.getOpenId();
            String oldSpecialId = orderOpenidMap.getSpecialId();
            String oldExternalId = orderOpenidMap.getExternalId();
            Checks.isTrue(openId != null && openId.equals(oldOpenId), "已绑定的openId与当前提供的openId不一致");
            Checks.isTrue(specialId == null || specialId.equals(oldSpecialId), "已绑定的specialId与当前提供的specialId不一致");
            Checks.isTrue(externalId == null || externalId.equals(oldExternalId), "已绑定的externalId与当前提供的externalId不一致");

            //返回已经绑定的信息
            orderBindResultVO.setOpenId(openId);
            orderBindResultVO.setSpecialId(specialId);
            orderBindResultVO.setTradeParentId(parentTradeId);
            List<String> tradeIdList = orderOpenidMapList.stream().map(a -> a.getTradeId()).collect(Collectors.toList());
            orderBindResultVO.getTradeIdItemIdList().addAll(tradeIdList);
            return orderBindResultVO;
        }

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
            //先查询，万一存在，就得更新，防止操作错误
            insertOrUpdateOrderOpenidMap(userInfos.getOpenId(), MapType.specified_openid_tradeparentid, userInfos, orderDetail);
        }

        //获取商品名称返回
        orderBindResultVO.setOpenId(openId);
        orderBindResultVO.setSpecialId(specialId);
        orderBindResultVO.setTradeParentId(parentTradeId);
        List<String> tradeIdList = orderDetailList.stream().map(a -> a.getTradeId()).collect(Collectors.toList());
        orderBindResultVO.getTradeIdItemIdList().addAll(tradeIdList);
        return orderBindResultVO;
    }


    /**
     * 通过交易单号，进行绑定操作
     * @param parentTradeId 必传字段
     * @param tradeIds 可传可不传
     * @return
     */
    public OrderBindResultVO bindByTradeId(String parentTradeId, String... tradeIds) {
        OrderBindResultVO orderBindResultVO = new OrderBindResultVO();
        orderBindResultVO.setTradeParentId(parentTradeId);

        //首先查询订单
        List<OrderDetail> orderDetailList = orderService.selectByTradeId(parentTradeId, null);
        Map<String, OrderDetail> tradeId2OrderDetailMap = orderDetailList.stream().collect(Collectors.toMap(a -> a.getTradeId(), a -> a));

        //查询哪些已经绑定过了
        List<OrderOpenidMap> orderOpenidMapList = orderOpenidMapService.selectByTradeId(parentTradeId, null);

        //如果已经存在了，那么先执行更新操作，如订单状态的演变等
        for (OrderOpenidMap orderOpenidMap : orderOpenidMapList) {
            updateOrderOpenidMapIfNeeded(orderOpenidMap, tradeId2OrderDetailMap.remove(orderOpenidMap.getTradeId()), orderBindResultVO);
        }

        //如果已经存在了，那么说明已经绑定过，这样可以直接复用原来的信息将剩下的数据绑定完毕
        if (!EmptyUtils.isEmpty(orderOpenidMapList)) {
            if (tradeId2OrderDetailMap.size() > 0) {
                createOrderOpenidMapBy(orderOpenidMapList.get(0), tradeId2OrderDetailMap, orderBindResultVO);
            }

            //内容
            orderBindResultVO.setOpenId(orderOpenidMapList.get(0).getOpenId());
            orderBindResultVO.setSpecialId(orderOpenidMapList.get(0).getSpecialId());
            orderBindResultVO.getTradeIdItemIdList().addAll(orderDetailList.stream().map(i -> i.getTradeId()).collect(Collectors.toList()));
            return orderBindResultVO;
        }

        //所有的都没有绑定过
        String specialId = orderDetailList.get(0).getSpecialId();
        //按照普通用户的方式来处理
        if (EmptyUtils.isEmpty(specialId)) {
            //通过商品&推广位来绑定
            bindByPubSite(orderDetailList, orderBindResultVO);
        }
        else {
            bindBySpecialId(orderDetailList, orderBindResultVO);
        }

        return orderBindResultVO;
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
    private void updateOrderOpenidMapIfNeeded(OrderOpenidMap orderOpenidMap, OrderDetail orderDetail, OrderBindResultVO orderBindResultVO) {
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

        //内容
        orderBindResultVO.setOpenId(orderOpenidMap.getOpenId());
        orderBindResultVO.setSpecialId(orderOpenidMap.getSpecialId());
        orderBindResultVO.getTradeIdItemIdList().add(orderOpenidMap.getTradeId());
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
    private void createOrderOpenidMapBy(OrderOpenidMap orderOpenidMap, Map<String, OrderDetail> tradeId2OrderDetailMap, OrderBindResultVO orderBindResultVO) {
        //用户信息
        UserInfos userInfos = userInfosService.selectByOpenIdAndSpecialId(orderOpenidMap.getOpenId(), orderOpenidMap.getSpecialId());

        //循环每个订单，插入到绑定表中去
        Collection<OrderDetail> allOrderDetails = tradeId2OrderDetailMap.values();
        for (OrderDetail orderDetail : allOrderDetails) {
            //先查询，万一存在，就得更新，防止操作错误
            insertOrUpdateOrderOpenidMap(userInfos.getOpenId(), MapType.tradeparentid_extend, userInfos, orderDetail);
        }

        //内容
        orderBindResultVO.setOpenId(orderOpenidMap.getOpenId());
        orderBindResultVO.setSpecialId(orderOpenidMap.getSpecialId());
        orderBindResultVO.getTradeIdItemIdList().add(orderOpenidMap.getTradeId());
    }

    /**
     * 普通用户，通过推广位和商品的ID去查询转链记录表，看是否转过
     * 通过这个方法的绑定，一定不是会员，就是那种普通的订单而已
     * @param orderDetailList
     * @param orderBindResultVO
     */
    private void bindByPubSite(List<OrderDetail> orderDetailList, OrderBindResultVO orderBindResultVO) {
        BindOpenidInfo openidInfo = resolveBindOpenidInfoByConvertHistory(orderDetailList);
        if (openidInfo == null) {
            orderOpenidMapFailureService.insertOrDoNoneOrderInfo(orderDetailList, "无淘口令转换记录");
            return;
        }

        //根据openid查询用户信息
        UserInfos userInfos = userInfosService.selectByOpenId(openidInfo.getOpenid());
        for (OrderDetail orderDetail : orderDetailList) {
            //获取mapType
            MapType mapType;

            //用作匹配的那个id
            if (openidInfo.getItemIds().contains(orderDetail.getItemId())) {
                mapType = MapType.pubsite;
            }
            else {
                mapType = MapType.one_item_pubsite_extend;
            }

            //先查询，万一存在，就得更新，防止操作错误
            insertOrUpdateOrderOpenidMap(openidInfo.getOpenid(), mapType, userInfos, orderDetail);

            //内容
            orderBindResultVO.setOpenId(userInfos.getOpenId());
            orderBindResultVO.setSpecialId(userInfos.getSpecialId());
            orderBindResultVO.getTradeIdItemIdList().add(orderDetail.getTradeId());
        }

    }

    /**
     * 执行插入或者更新
     * @param openId
     * @param mapType
     * @param userInfos
     * @param orderDetail
     */
    private void insertOrUpdateOrderOpenidMap(String openId, MapType mapType, UserInfos userInfos, OrderDetail orderDetail) {
        //先查询，万一存在，就得更新，防止操作错误
        OrderOpenidMap newOrderOpenidMap = null;
        List<OrderOpenidMap> orderOpenidMapList = orderOpenidMapService.selectByTradeId(orderDetail.getParentTradeId(), orderDetail.getTradeId());
        if (!orderOpenidMapList.isEmpty()) {
            newOrderOpenidMap = orderOpenidMapList.get(0);

            //初步判定，绑定的用户得是一样的
        }
        else {
            newOrderOpenidMap = new OrderOpenidMap();
            newOrderOpenidMap.setGmtCreated(new Date());
            newOrderOpenidMap.setTradeId(orderDetail.getTradeId());
            newOrderOpenidMap.setParentTradeId(orderDetail.getParentTradeId());
            newOrderOpenidMap.setCommissionStatus("待提取");
        }

        //公共更新字段
        newOrderOpenidMap.setGmtModified(new Date());
        newOrderOpenidMap.setOpenId(openId);
        newOrderOpenidMap.setExternalId(userInfos.getExternalId());
        newOrderOpenidMap.setSpecialId(userInfos.getSpecialId());
        newOrderOpenidMap.setRelationId(userInfos.getRelationId());
        newOrderOpenidMap.setItemId(orderDetail.getItemId());
        newOrderOpenidMap.setPubSharePreFee(orderDetail.getPubSharePreFee());
        newOrderOpenidMap.setPubShareFee(orderDetail.getPubShareFee());
        newOrderOpenidMap.setAlimamaShareFee(orderDetail.getAlimamaShareFee());
        newOrderOpenidMap.setOrderStatus(orderDetail.getTkStatus());
        newOrderOpenidMap.setRefundTag(orderDetail.getRefundTag());

        //用作匹配的那个id
        newOrderOpenidMap.setMapType(mapType.getLabel());
        newOrderOpenidMap.setStatus(0);

        //插入数据库
        if (orderOpenidMapList.isEmpty()) {
            int affectedNum = orderOpenidMapService.save(newOrderOpenidMap);
            Checks.isTrue(affectedNum == 1, "插入失败 - tradeId=" + orderDetail.getTradeId());
        }
        else {
            int affectedCnt = orderOpenidMapService.update(newOrderOpenidMap);
            Checks.isTrue(affectedCnt == 1, "更新失败 - tradeId=" + orderDetail.getTradeId());
        }
    }

    /**
     * 会员用户，通过specialId进行绑定
     * @param orderDetailList
     * @param orderBindResultVO
     */
    private void bindBySpecialId(List<OrderDetail> orderDetailList, OrderBindResultVO orderBindResultVO) {
        //第一种，这里的所有商品，至少有被转链过，那么查出来，那么这种情况，是可以建立openid和specialid的关系并存入用户表的
        BindOpenidInfo openidInfo = resolveBindOpenidInfoByConvertHistory(orderDetailList);
        if (openidInfo != null) {
            //起始这里有个问题，如果转码是A通过微信转的，但是发给了B去买，B正好是会员，此时是不可以将openId和specialId识别为一对的
            //所以此时，就将mapType记录一下，openId-specialId
            String specialIdByOrderDetail = orderDetailList.get(0).getSpecialId();
            UserInfos userInfosBySpecialId = userInfosService.selectBySpecialId(specialIdByOrderDetail);

            //判定一下
            UserInfos userInfos = userInfosService.selectByOpenId(openidInfo.getOpenid());
            //String specialIdByUserInfo = userInfos.getSpecialId();

            //存储
            for (OrderDetail orderDetail : orderDetailList) {
                //先查询，万一存在，就得更新，防止操作错误
                insertOrUpdateOrderOpenidMap(userInfos.getOpenId(), MapType.specialid_with_pubsite, userInfosBySpecialId, orderDetail);

                //内容
                orderBindResultVO.setOpenId(userInfos.getOpenId());
                orderBindResultVO.setSpecialId(userInfosBySpecialId.getSpecialId());
                orderBindResultVO.getTradeIdItemIdList().add(orderDetail.getTradeId());
            }

            return;
        }

        //第二种，这里所有的商品，都没有被转链过，那么只能存入specialid字段，其他openid这些数据不填写，mapType就是specialid，表示只是会员
        UserInfos userInfos = userInfosService.selectBySpecialId(orderDetailList.get(0).getSpecialId());
        for (OrderDetail orderDetail : orderDetailList) {
            //先查询，万一存在，就得更新，防止操作错误
            insertOrUpdateOrderOpenidMap(userInfos.getOpenId(), MapType.specialid, userInfos, orderDetail);

            //内容
            orderBindResultVO.setOpenId(userInfos.getOpenId());
            orderBindResultVO.setSpecialId(userInfos.getSpecialId());
            orderBindResultVO.getTradeIdItemIdList().add(orderDetail.getTradeId());
        }
    }

    /**
     * 根据这个用户购买的所有订单，去确定，是那个微信用户在转码和购买
     * @param orderDetailList
     * @return
     */
    private BindOpenidInfo resolveBindOpenidInfoByConvertHistory(List<OrderDetail> orderDetailList) {
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
     * 绑定维权的退回金额
     * @param openId
     * @param specialId
     * @param tradeId
     * @param itemId
     * @param refundFeeStr
     * @return
     */
    public void bindRefundFee(String openId, String specialId, String tradeId, String itemId, String refundFeeStr) {
        List<OrderOpenidMap> orderOpenidMapList = orderOpenidMapService.selectByTradeId(null, tradeId);
        Checks.isTrue(orderOpenidMapList.size() == 1, "订单不唯一，更新失败");

        //验证
        OrderOpenidMap orderOpenidMap = orderOpenidMapList.get(0);
        Checks.isTrue(EmptyUtils.isEmpty(openId) || openId.equals(orderOpenidMap.getOpenId()), "给定的openId与订单绑定的不一致，更新失败");
        Checks.isTrue(EmptyUtils.isEmpty(specialId) || specialId.equals(orderOpenidMap.getSpecialId()), "给定的specialId与订单绑定的不一致，更新失败");
        Checks.isTrue(EmptyUtils.isEmpty(itemId) || itemId.equals(orderOpenidMap.getItemId()), "给定的itemId与订单绑定的不一致，更新失败");

        //数值判定
        BigDecimal refundFee = NumberUtil.parseBigDecimal(refundFeeStr);
        Checks.isTrue(refundFee != null && !(refundFee.doubleValue() <= 0), "给定的返利金额不正确");

        //更新
        orderOpenidMap.setRefundFee(refundFeeStr);
        int affectedCnt = orderOpenidMapService.update(orderOpenidMap);
        Checks.isTrue(affectedCnt == 1, "更新失败，影响条数:" + affectedCnt);
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

        //通过一个商品的转链用的推广为信息，将其他的都帮挡到一个用户openid上
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
