package com.help.rebate.service;
import java.math.BigDecimal;

import com.help.rebate.dao.V2TaobaoCommissionAccountFlowInfoDao;
import com.help.rebate.dao.V2TaobaoCommissionAccountInfoDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.vo.CommissionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
     * 查询返利信息
     * 1、账户里的总值
     * 2、尚未走到结算的所有钱（剔除关闭的）
     * 3、所以，需要两者叠加给用户
     * @param openId
     * @param specialId
     * @return
     */
    public CommissionVO selectCommissionBy(String openId, String specialId) {
        return null;
    }

    /**
     * 按照这个时间范围，筛选订单，并计算已经结算成功的订单，转到总账户表中
     * @param orderStartModifiedTime
     * @param minuteStep
     */
    public void computeOrderDetailToAccount(String orderStartModifiedTime, Long minuteStep) {
        //确定时间范围
        LocalDateTime startTime = TimeUtil.parseLocalDate(orderStartModifiedTime);
        String endTime = TimeUtil.formatLocalDate(startTime.plusMinutes(minuteStep));

        //查询
        List<V2TaobaoOrderDetailInfo> orderDetailList = v2TaobaoOrderService.selectByModifiedTimeRange(orderStartModifiedTime, endTime);
        //转变为订单号
        List<String> tradeParentIds = orderDetailList.stream().map(a -> a.getTradeParentId()).distinct().collect(Collectors.toList());

        //查询订单绑定表
        List<V2TaobaoOrderOpenidMapInfo> v2TaobaoOrderOpenidMapInfos = v2TaobaoOrderOpenidMapService.selectBindInfoByTradeId(tradeParentIds);

        //只保留两种 - 结算成功-3、订单【待提取、提取中，提取成功，提取失败, 提取超时】，修改为【结算xxx】
        List<V2TaobaoOrderOpenidMapInfo> allEligiableOrderMapInfos = v2TaobaoOrderOpenidMapInfos.stream()
                .filter(a -> a.getStatus() == 0)
                .filter(a -> a.getOrderStatus() != null && a.getOrderStatus() == 3)
                .filter(a -> a.getCommissionStatusMsg() != null && "待提取,结算失败".contains(a.getCommissionStatusMsg()))
                .collect(Collectors.toList());
        if (allEligiableOrderMapInfos.isEmpty()) {
            return;
        }

        //这些订单是需要提取的
        double sumCommission = allEligiableOrderMapInfos.stream().map(a -> {
            Integer commissionRatio = a.getCommissionRatio();
            if (commissionRatio == null) {
                commissionRatio = 900;
            }
            double ratio = commissionRatio * 1.0 / 1000.0;
            double pubShareFee = Double.parseDouble(a.getPubShareFee());
            return ratio * pubShareFee;
        }).mapToDouble(a -> a).sum();

        //订单更新
        List<Integer> ids = allEligiableOrderMapInfos.stream().map(a -> a.getId()).collect(Collectors.toList());
        v2TaobaoOrderOpenidMapService.updateCommissionStatusMsgByPrimaryKey(ids, "结算成功");

        //查询账户数据
        String openId = allEligiableOrderMapInfos.get(0).getOpenId();
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

        //更新结算流水
        BigDecimal sumCommssionOfBigDecimal = new BigDecimal(sumCommission);
        for (V2TaobaoOrderOpenidMapInfo openidMapInfo : allEligiableOrderMapInfos) {
            V2TaobaoCommissionAccountFlowInfo accountFlowInfo = new V2TaobaoCommissionAccountFlowInfo();
            accountFlowInfo.setGmtCreated(LocalDateTime.now());
            accountFlowInfo.setGmtModified(LocalDateTime.now());
            accountFlowInfo.setOpenId(openidMapInfo.getOpenId());
            accountFlowInfo.setTotalCommission(currentAccountInfo.getTotalCommission());
            accountFlowInfo.setRemainCommission(currentAccountInfo.getRemainCommission());
            accountFlowInfo.setFrozenCommission(currentAccountInfo.getFrozenCommission());
            accountFlowInfo.setFlowAmount(sumCommssionOfBigDecimal);

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
        currentAccountInfo.setTotalCommission(currentAccountInfo.getTotalCommission().add(sumCommssionOfBigDecimal));
        currentAccountInfo.setRemainCommission(currentAccountInfo.getRemainCommission().add(sumCommssionOfBigDecimal));
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
}
