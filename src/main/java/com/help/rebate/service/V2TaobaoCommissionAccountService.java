package com.help.rebate.service;
import java.math.BigDecimal;

import com.help.rebate.dao.V2TaobaoCommissionAccountFlowInfoDao;
import com.help.rebate.dao.V2TaobaoCommissionAccountInfoDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.NumberUtil;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.vo.CommissionVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 提现服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2TaobaoCommissionAccountService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoCommissionAccountService.class);

    /**
     * 账户流水dao
     */
    @Resource
    private V2TaobaoCommissionAccountFlowInfoDao v2TaobaoCommissionAccountFlowInfoDao;

    /**
     * 账户dao
     */
    @Resource
    private V2TaobaoCommissionAccountInfoDao v2TaobaoCommissionAccountInfoDao;

    /**
     * 淘宝订单服务
     */
    @Resource
    private V2TaobaoOrderService v2TaobaoOrderService;

    /**
     * 淘宝订单绑定服务
     */
    @Resource
    private V2TaobaoOrderOpenidMapService v2TaobaoOrderOpenidMapService;

    /**
     * 银行卡总余额，默认为0
     */
    private BigDecimal bankTotalAccount = new BigDecimal("0.00");

    /**
     * 每个用户，每日最高的提现次数
     */
    private int maxWithdrawalTimesPerUser = 3;

    /**
     * 每次提现，固定的钱数，精确到分
     */
    private int withdrawalAmount = 100;

    /**
     * 查询返利信息
     * 1、账户里的总值
     * 2、尚未走到结算的所有钱（剔除关闭的）
     * 3、所以，需要两者叠加给用户
     * @param openId
     * @return
     */
    public CommissionVO selectCommissionBy(String openId) {
        V2TaobaoCommissionAccountInfo v2TaobaoCommissionAccountInfo = selectV2TaobaoCommissionAccountInfo(openId);

        //首先获取到账户总信息
        CommissionVO commissionVO = new CommissionVO();
        commissionVO.setRemainCommission(NumberUtil.format(v2TaobaoCommissionAccountInfo.getRemainCommission()));

        //12-付款，13-关闭，14-确认收货，3-结算成功
        List<Integer> orderStatusList = Stream.of(12, 14).collect(Collectors.toList());
        //待提取、[提取中，提取成功，提取失败, 提取超时]不用了，结算中，结算成功，结算失败
        List<String> commissionStatusMsgList = Stream.of("待提取").collect(Collectors.toList());
        List<V2TaobaoOrderOpenidMapInfo> v2TaobaoOrderOpenidMapInfos = v2TaobaoOrderOpenidMapService.selectBindInfoByOpenId(openId, orderStatusList, commissionStatusMsgList);

        //这些订单是需要提取的
        BigDecimal sumCommission = v2TaobaoOrderOpenidMapInfos.stream().map(a -> {
            Integer commissionRatio = a.getCommissionRatio();
            if (commissionRatio == null) {
                commissionRatio = 900;
            }
            BigDecimal ratio = new BigDecimal(commissionRatio).multiply(new BigDecimal("0.001"));
            BigDecimal pubShareFee = new BigDecimal((a.getOrderStatus() == 3 ? a.getPubShareFee() : a.getPubSharePreFee()));
            return ratio.multiply(pubShareFee);
        }).reduce((x, y) -> x.add(y)).get();

        //未来待结算金额
        commissionVO.setFutureCommission(NumberUtil.format(sumCommission));
        return commissionVO;
    }

    /**
     * 查询一个账户信息
     * @param openId
     * @return
     */
    public V2TaobaoCommissionAccountInfo selectV2TaobaoCommissionAccountInfo(String openId) {
        V2TaobaoCommissionAccountInfoExample accountInfoExample = new V2TaobaoCommissionAccountInfoExample();
        accountInfoExample.setLimit(1);
        accountInfoExample.setOrderByClause("id asc");
        V2TaobaoCommissionAccountInfoExample.Criteria criteria = accountInfoExample.createCriteria();
        criteria.andOpenIdEqualTo(openId);
        criteria.andStatusEqualTo((byte) 0);
        List<V2TaobaoCommissionAccountInfo> v2TaobaoCommissionAccountInfos = v2TaobaoCommissionAccountInfoDao.selectByExample(accountInfoExample);
        V2TaobaoCommissionAccountInfo currentAccountInfo = null;
        if (v2TaobaoCommissionAccountInfos.isEmpty()) {
            currentAccountInfo = insertNewOpenIdAccount(openId);
        }
        else {
            currentAccountInfo = v2TaobaoCommissionAccountInfos.get(0);
        }
        return currentAccountInfo;
    }

    /**
     * 触发提现操作
     * @param openId
     * @param withdrawalAmount 精确到分
     * @return
     */
    public synchronized long triggerWithdrawal(String openId, int withdrawalAmount) {
        V2TaobaoCommissionAccountInfo v2TaobaoCommissionAccountInfo = selectV2TaobaoCommissionAccountInfo(openId);
        BigDecimal remainCommission = v2TaobaoCommissionAccountInfo.getRemainCommission();

        //判断，是不是金额太大了
        BigDecimal withdrawalAmountDecimal = new BigDecimal(withdrawalAmount).multiply(new BigDecimal("0.01"));
        Checks.isTrue(remainCommission.compareTo(withdrawalAmountDecimal) >= 0, "提现金额高于账户余额");

        //也不能大于账户总额
        Checks.isTrue(this.bankTotalAccount.subtract(withdrawalAmountDecimal).compareTo(new BigDecimal("0")) >= 0, "今日剩余提取额度不足，明日再试哦");

        //扣减余额
        this.bankTotalAccount = this.bankTotalAccount.subtract(withdrawalAmountDecimal);

        //构建金额
        logger.info("[触发提现] openId:{}, 准备扣除账户余额:{}分", openId, withdrawalAmount);
        v2TaobaoCommissionAccountInfo.setFinishCommission(v2TaobaoCommissionAccountInfo.getFinishCommission().add(withdrawalAmountDecimal));
        v2TaobaoCommissionAccountInfo.setRemainCommission(v2TaobaoCommissionAccountInfo.getRemainCommission().subtract(withdrawalAmountDecimal));
        v2TaobaoCommissionAccountInfo.setGmtModified(LocalDateTime.now());
        v2TaobaoCommissionAccountInfoDao.updateByPrimaryKey(v2TaobaoCommissionAccountInfo);

        //产生一个流水
        long subId = System.nanoTime();
        V2TaobaoCommissionAccountFlowInfo accountFlowInfo = new V2TaobaoCommissionAccountFlowInfo();
        accountFlowInfo.setGmtCreated(LocalDateTime.now());
        accountFlowInfo.setGmtModified(LocalDateTime.now());
        accountFlowInfo.setOpenId(openId);
        accountFlowInfo.setTotalCommission(v2TaobaoCommissionAccountInfo.getTotalCommission());
        accountFlowInfo.setRemainCommission(v2TaobaoCommissionAccountInfo.getRemainCommission());
        accountFlowInfo.setFrozenCommission(v2TaobaoCommissionAccountInfo.getFrozenCommission());
        accountFlowInfo.setFlowAmount(withdrawalAmountDecimal);
        //0-结算，1-维权退回，2-提现，3-冻结金额
        accountFlowInfo.setFlowAmountType((byte)2);
        accountFlowInfo.setFlowAmountTypeMsg("账户提现");
        accountFlowInfo.setCommissionTradeId(null);
        accountFlowInfo.setRefundTradeId(null);
        //0-成功、1-失败、2-进行中
        accountFlowInfo.setAccountFlowStatus((byte)2);
        accountFlowInfo.setAccountFlowStatusMsg("[" + subId + "]进行中，已扣减余额" + withdrawalAmount + "分");
        accountFlowInfo.setStatus((byte)0);
        //插入
        v2TaobaoCommissionAccountFlowInfoDao.insertSelective(accountFlowInfo);

        //返回结果
        return subId;
    }

    /**
     * 触发提现操作，记录成功还是失败
     * @param openId
     * @param withdrawalAmount 分
     * @return
     */
    public synchronized void postTriggerWithdrawal(String openId, int withdrawalAmount, boolean success, String msg, long subId) {
        V2TaobaoCommissionAccountInfo v2TaobaoCommissionAccountInfo = selectV2TaobaoCommissionAccountInfo(openId);

        BigDecimal withdrawalAmountDecimal = new BigDecimal(new Integer(withdrawalAmount) * 1.0 / 100);

        //产生一个流水
        V2TaobaoCommissionAccountFlowInfo accountFlowInfo = new V2TaobaoCommissionAccountFlowInfo();
        accountFlowInfo.setGmtCreated(LocalDateTime.now());
        accountFlowInfo.setGmtModified(LocalDateTime.now());
        accountFlowInfo.setOpenId(openId);
        accountFlowInfo.setTotalCommission(v2TaobaoCommissionAccountInfo.getTotalCommission());
        accountFlowInfo.setRemainCommission(v2TaobaoCommissionAccountInfo.getRemainCommission());
        accountFlowInfo.setFrozenCommission(v2TaobaoCommissionAccountInfo.getFrozenCommission());
        accountFlowInfo.setFlowAmount(withdrawalAmountDecimal);
        //0-结算，1-维权退回，2-提现，3-冻结金额
        accountFlowInfo.setFlowAmountType((byte)2);
        accountFlowInfo.setFlowAmountTypeMsg("账户提现");
        accountFlowInfo.setCommissionTradeId(null);
        accountFlowInfo.setRefundTradeId(null);
        //0-成功、1-失败、2-进行中
        if (success) {
            accountFlowInfo.setAccountFlowStatus((byte)0);
            accountFlowInfo.setAccountFlowStatusMsg("[" + subId + "]" + msg);
        }
        else {
            accountFlowInfo.setAccountFlowStatus((byte)1);
            accountFlowInfo.setAccountFlowStatusMsg(StringUtils.substring("[" + subId + "]" + msg, 0, 127));
        }
        accountFlowInfo.setStatus((byte)0);
        //插入
        v2TaobaoCommissionAccountFlowInfoDao.insertSelective(accountFlowInfo);

        //准备触发回退操作
        if (!success) {
            logger.warn("[提现确认] 提现失败，准备触发提现回退, openId:{}, 回退金额:{}分, 失败信息:{}", openId, withdrawalAmount, msg);
            backingTriggerWithdrawal(openId, withdrawalAmount, msg, subId);
        }
    }

    /**
     * 逆向触发提现回退操作
     * @param openId
     * @param withdrawalAmount 精确到分，如100分，就是一元钱
     * @return
     */
    public synchronized void backingTriggerWithdrawal(String openId, Integer withdrawalAmount, String msg, long subId) {
        V2TaobaoCommissionAccountInfo v2TaobaoCommissionAccountInfo = selectV2TaobaoCommissionAccountInfo(openId);

        //金额
        BigDecimal withdrawalAmountDecimal = new BigDecimal(new Integer(withdrawalAmount) * 1.0 / 100);

        //首先产生一个流水
        V2TaobaoCommissionAccountFlowInfo accountFlowInfo = new V2TaobaoCommissionAccountFlowInfo();
        accountFlowInfo.setGmtCreated(LocalDateTime.now());
        accountFlowInfo.setGmtModified(LocalDateTime.now());
        accountFlowInfo.setOpenId(openId);
        accountFlowInfo.setTotalCommission(v2TaobaoCommissionAccountInfo.getTotalCommission());
        accountFlowInfo.setRemainCommission(v2TaobaoCommissionAccountInfo.getRemainCommission());
        accountFlowInfo.setFrozenCommission(v2TaobaoCommissionAccountInfo.getFrozenCommission());
        accountFlowInfo.setFlowAmount(withdrawalAmountDecimal);
        //0-结算，1-维权退回，2-提现，3-冻结金额，4-提现回退
        accountFlowInfo.setFlowAmountType((byte)4);
        accountFlowInfo.setFlowAmountTypeMsg("提现回退");
        accountFlowInfo.setCommissionTradeId(null);
        accountFlowInfo.setRefundTradeId(null);
        //0-成功、1-失败、2-进行中
        accountFlowInfo.setAccountFlowStatus((byte)0);
        accountFlowInfo.setAccountFlowStatusMsg(StringUtils.substring("[" + subId + "]" + msg, 0, 127));
        accountFlowInfo.setStatus((byte)0);
        //插入
        v2TaobaoCommissionAccountFlowInfoDao.insertSelective(accountFlowInfo);

        //构建金额
        v2TaobaoCommissionAccountInfo.setFinishCommission(v2TaobaoCommissionAccountInfo.getFinishCommission().subtract(withdrawalAmountDecimal));
        v2TaobaoCommissionAccountInfo.setRemainCommission(v2TaobaoCommissionAccountInfo.getRemainCommission().add(withdrawalAmountDecimal));
        v2TaobaoCommissionAccountInfo.setGmtModified(LocalDateTime.now());
        v2TaobaoCommissionAccountInfoDao.updateByPrimaryKey(v2TaobaoCommissionAccountInfo);
    }

    /**
     * 按照这个时间范围，筛选订单，并计算已经结算成功的订单，转到总账户表中
     * @param orderStartModifiedTime
     * @param minuteStep
     */
    public synchronized List<String> computeOrderDetailToAccount(String orderStartModifiedTime, Long minuteStep) {
        //确定时间范围
        LocalDateTime startTime = TimeUtil.parseLocalDate(orderStartModifiedTime);
        String endTime = TimeUtil.formatLocalDate(startTime.plusMinutes(minuteStep));

        //查询
        List<V2TaobaoOrderDetailInfo> orderDetailList = v2TaobaoOrderService.selectByModifiedTimeRange(orderStartModifiedTime, endTime);
        //转变为订单号
        List<String> tradeParentIds = orderDetailList.stream().map(a -> a.getTradeParentId()).distinct().collect(Collectors.toList());

        //查询订单绑定表
        List<V2TaobaoOrderOpenidMapInfo> v2TaobaoOrderOpenidMapInfos = v2TaobaoOrderOpenidMapService.selectBindInfoByTradeParentId(tradeParentIds);

        //只保留两种 - 结算成功-3、订单【待提取、提取中，提取成功，提取失败, 提取超时】，修改为【结算xxx】
        List<V2TaobaoOrderOpenidMapInfo> allEligiableOrderMapInfos = v2TaobaoOrderOpenidMapInfos.stream()
                .filter(a -> a.getStatus() == 0)
                .filter(a -> a.getOrderStatus() != null && a.getOrderStatus() == 3)
                .filter(a -> a.getCommissionStatusMsg() != null && "待提取,结算失败".contains(a.getCommissionStatusMsg()))
                .collect(Collectors.toList());
        if (allEligiableOrderMapInfos.isEmpty()) {
            return Collections.emptyList();
        }

        //按照openId做划分
        Map<String, List<V2TaobaoOrderOpenidMapInfo>> openId2OrderBindMap = allEligiableOrderMapInfos.stream().collect(Collectors.groupingBy(a -> a.getOpenId()));
        Iterator<Map.Entry<String, List<V2TaobaoOrderOpenidMapInfo>>> iterator = openId2OrderBindMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<V2TaobaoOrderOpenidMapInfo>> entry = iterator.next();
            String openId = entry.getKey();
            List<V2TaobaoOrderOpenidMapInfo> orderBindList = entry.getValue();

            //单个openId的统计计算
            logger.info("[统计总返利] openId:{}, 订单详情:{}", openId, orderBindList.stream().map(a -> a.getTradeId()).collect(Collectors.joining(",")));
            computeOrderDetailToAccountForOpenId(openId, orderBindList);
        }

        //订单数返回
        List<String> tradeParentIdList = allEligiableOrderMapInfos.stream().map(a -> a.getTradeParentId()).distinct().collect(Collectors.toList());
        return tradeParentIdList;
    }

    /**
     * 为专门的openId来统计
     * @param openId
     * @param orderBindList
     */
    private void computeOrderDetailToAccountForOpenId(String openId, List<V2TaobaoOrderOpenidMapInfo> orderBindList) {
        //这些订单是需要提取的
        Map<String, BigDecimal> tradeId2FeeMap = orderBindList.stream().map(a -> {
            Integer commissionRatio = a.getCommissionRatio();
            if (commissionRatio == null) {
                commissionRatio = 900;
            }

            BigDecimal ratio = new BigDecimal(commissionRatio).multiply(new BigDecimal("0.001"));
            BigDecimal pubShareFee = new BigDecimal((a.getOrderStatus() == 3 ? a.getPubShareFee() : a.getPubSharePreFee()));
            return new Object[]{a.getTradeId(), ratio.multiply(pubShareFee)};
        }).collect(Collectors.toMap(a -> (String) a[0], a -> (BigDecimal) a[1]));

        //总钱数
        BigDecimal sumCommission = tradeId2FeeMap.values().stream().reduce((x, y) -> x.add(y)).get();

        //订单更新，先更新为结算成功
        List<Integer> ids = orderBindList.stream().map(a -> a.getId()).collect(Collectors.toList());
        v2TaobaoOrderOpenidMapService.updateCommissionStatusMsgByPrimaryKey(ids, "结算成功");

        //查询账户数据
        V2TaobaoCommissionAccountInfo currentAccountInfo = selectV2TaobaoCommissionAccountInfo(openId);

        //更新结算流水
        for (V2TaobaoOrderOpenidMapInfo openidMapInfo : orderBindList) {
            V2TaobaoCommissionAccountFlowInfo accountFlowInfo = new V2TaobaoCommissionAccountFlowInfo();
            accountFlowInfo.setGmtCreated(LocalDateTime.now());
            accountFlowInfo.setGmtModified(LocalDateTime.now());
            accountFlowInfo.setOpenId(openidMapInfo.getOpenId());
            accountFlowInfo.setTotalCommission(currentAccountInfo.getTotalCommission());
            accountFlowInfo.setRemainCommission(currentAccountInfo.getRemainCommission());
            accountFlowInfo.setFrozenCommission(currentAccountInfo.getFrozenCommission());
            accountFlowInfo.setFlowAmount(tradeId2FeeMap.get(openidMapInfo.getTradeId()));

            //0-结算，1-维权退回，2-提现，3-冻结金额
            accountFlowInfo.setFlowAmountType((byte)0);
            accountFlowInfo.setFlowAmountTypeMsg("订单结算");
            accountFlowInfo.setCommissionTradeId(openidMapInfo.getTradeId());
            accountFlowInfo.setRefundTradeId(null);

            //0-成功、1-失败、2-进行中
            accountFlowInfo.setAccountFlowStatus((byte)0);
            accountFlowInfo.setAccountFlowStatusMsg("成功");
            accountFlowInfo.setStatus((byte)0);

            //插入
            v2TaobaoCommissionAccountFlowInfoDao.insertSelective(accountFlowInfo);
        }

        //增加账户余额
        currentAccountInfo.setTotalCommission(currentAccountInfo.getTotalCommission().add(sumCommission));
        currentAccountInfo.setRemainCommission(currentAccountInfo.getRemainCommission().add(sumCommission));
        int affectedCnt = v2TaobaoCommissionAccountInfoDao.updateByPrimaryKeySelective(currentAccountInfo);
    }

    /**
     * 为专门的openId来更新维权单
     * @param openId
     * @param orderBindList
     */
    public void computeOrderRefundFeeToAccountForOpenId(String openId, List<V2TaobaoOrderOpenidMapInfo> orderBindList) {
        //这些订单是需要提取的
        Map<String, BigDecimal> tradeId2FeeMap = orderBindList.stream().map(a -> {
            BigDecimal refundFee = new BigDecimal(a.getRefundFee());
            return new Object[]{a.getTradeId(), refundFee};
        }).collect(Collectors.toMap(a -> (String) a[0], a -> (BigDecimal) a[1]));

        //总维权钱数
        BigDecimal sumCommission = tradeId2FeeMap.values().stream().reduce((x, y) -> x.add(y)).get();

        //查询账户数据
        V2TaobaoCommissionAccountInfo currentAccountInfo = selectV2TaobaoCommissionAccountInfo(openId);

        //更新结算流水
        for (V2TaobaoOrderOpenidMapInfo openidMapInfo : orderBindList) {
            V2TaobaoCommissionAccountFlowInfo accountFlowInfo = new V2TaobaoCommissionAccountFlowInfo();
            accountFlowInfo.setGmtCreated(LocalDateTime.now());
            accountFlowInfo.setGmtModified(LocalDateTime.now());
            accountFlowInfo.setOpenId(openidMapInfo.getOpenId());
            accountFlowInfo.setTotalCommission(currentAccountInfo.getTotalCommission());
            accountFlowInfo.setRemainCommission(currentAccountInfo.getRemainCommission());
            accountFlowInfo.setFrozenCommission(currentAccountInfo.getFrozenCommission());
            accountFlowInfo.setFlowAmount(tradeId2FeeMap.get(openidMapInfo.getTradeId()));

            //0-结算，1-维权退回，2-提现，3-冻结金额
            accountFlowInfo.setFlowAmountType((byte)1);
            accountFlowInfo.setFlowAmountTypeMsg("维权退回");
            accountFlowInfo.setCommissionTradeId(null);
            accountFlowInfo.setRefundTradeId(openidMapInfo.getTradeId());

            //0-成功、1-失败、2-进行中
            accountFlowInfo.setAccountFlowStatus((byte)0);
            accountFlowInfo.setAccountFlowStatusMsg("成功");
            accountFlowInfo.setStatus((byte)0);

            //插入
            v2TaobaoCommissionAccountFlowInfoDao.insertSelective(accountFlowInfo);
        }

        //增加账户余额
        currentAccountInfo.setTotalCommission(currentAccountInfo.getTotalCommission().subtract(sumCommission));
        currentAccountInfo.setRemainCommission(currentAccountInfo.getRemainCommission().subtract(sumCommission));
        int affectedCnt = v2TaobaoCommissionAccountInfoDao.updateByPrimaryKeySelective(currentAccountInfo);
    }

    /**
     * 插入一个新的
     * @param openId
     * @return
     */
    private synchronized V2TaobaoCommissionAccountInfo insertNewOpenIdAccount(String openId) {
        V2TaobaoCommissionAccountInfo commissionAccountInfo = new V2TaobaoCommissionAccountInfo();
        commissionAccountInfo.setGmtCreated(LocalDateTime.now());
        commissionAccountInfo.setGmtModified(LocalDateTime.now());
        commissionAccountInfo.setOpenId(openId);
        commissionAccountInfo.setTotalCommission(new BigDecimal("0"));
        commissionAccountInfo.setRemainCommission(new BigDecimal("0"));
        commissionAccountInfo.setFrozenCommission(new BigDecimal("0"));
        commissionAccountInfo.setFinishCommission(new BigDecimal("0"));
        commissionAccountInfo.setStatus((byte)0);

        //插入
        int affectedCnt = v2TaobaoCommissionAccountInfoDao.insert(commissionAccountInfo);
        return commissionAccountInfo;
    }

    /**
     * 银行卡总余额
     * @param totalAccount
     * @param maxWithdrawalTimesPerUser
     * @param withdrawalAmount
     */
    public void setWithdrawalConfig(String totalAccount, Integer maxWithdrawalTimesPerUser, Integer withdrawalAmount) {
        if (!EmptyUtils.isEmpty(totalAccount)) {
            this.bankTotalAccount = new BigDecimal(totalAccount);
        }

        if (maxWithdrawalTimesPerUser != null && maxWithdrawalTimesPerUser >= 0) {
            this.maxWithdrawalTimesPerUser = maxWithdrawalTimesPerUser;
        }

        if (withdrawalAmount != null && withdrawalAmount >= 0) {
            this.withdrawalAmount = withdrawalAmount;
        }
    }

    /**
     * 获取提现配置
     */
    public Map<String, String> getWithdrawalConfig() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("bankTotalAccount", NumberUtil.format(bankTotalAccount));
        result.put("maxWithdrawalTimesPerUser", maxWithdrawalTimesPerUser + "");
        result.put("withdrawalAmount", withdrawalAmount + "");
        return result;
    }

    public BigDecimal getBankTotalAccount() {
        return bankTotalAccount;
    }

    public int getMaxWithdrawalTimesPerUser() {
        return maxWithdrawalTimesPerUser;
    }

    public int getWithdrawalAmount() {
        return withdrawalAmount;
    }

    /**
     * 列出所有用户的返利
     */
    public List<V2TaobaoCommissionAccountInfo> listAllUserCommissions() {
        V2TaobaoCommissionAccountInfoExample accountInfoExample = new V2TaobaoCommissionAccountInfoExample();
        accountInfoExample.setOrderByClause("id asc");
        V2TaobaoCommissionAccountInfoExample.Criteria criteria = accountInfoExample.createCriteria();
        criteria.andStatusEqualTo((byte) 0);
        List<V2TaobaoCommissionAccountInfo> v2TaobaoCommissionAccountInfos = v2TaobaoCommissionAccountInfoDao.selectByExample(accountInfoExample);
        return v2TaobaoCommissionAccountInfos;
    }
}
