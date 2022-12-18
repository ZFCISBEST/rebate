package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoOrderOpenidMapInfoDao;
import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapInfoExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单映射数据服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2TaobaoOrderOpenidMapService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoOrderOpenidMapService.class);

    /**
     * 订单服务
     */
    @Resource
    private V2TaobaoOrderService v2TaobaoOrderService;

    /**
     * 订单映射详情接口
     */
    @Resource
    private V2TaobaoOrderOpenidMapInfoDao v2TaobaoOrderOpenidMapInfoDao;

    /**
     * 保存一个新对象
     * @param orderOpenidMap
     * @return
     */
    public int save(V2TaobaoOrderOpenidMapInfo orderOpenidMap) {
        int affectedCnt = v2TaobaoOrderOpenidMapInfoDao.insertSelective(orderOpenidMap);
        return affectedCnt;
    }

    /**
     * 更新一个新对象
     * @param orderOpenidMap
     * @return
     */
    public int update(V2TaobaoOrderOpenidMapInfo orderOpenidMap) {
        int affectedCnt = v2TaobaoOrderOpenidMapInfoDao.updateByPrimaryKeySelective(orderOpenidMap);
        return affectedCnt;
    }

    /**
     * 订单结算状态更新
     * @param ids
     * @param commissionStatusMsg
     * @return
     */
    public int updateCommissionStatusMsgByPrimaryKey(List<Integer> ids, String commissionStatusMsg) {
        V2TaobaoOrderOpenidMapInfoExample example = new V2TaobaoOrderOpenidMapInfoExample();
        V2TaobaoOrderOpenidMapInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);

        V2TaobaoOrderOpenidMapInfo v2TaobaoOrderOpenidMapInfo = new V2TaobaoOrderOpenidMapInfo();
        v2TaobaoOrderOpenidMapInfo.setCommissionStatusMsg(commissionStatusMsg);

        //更新 - 后面再写一个批量更新
        int affected = v2TaobaoOrderOpenidMapInfoDao.updateByExampleSelective(v2TaobaoOrderOpenidMapInfo, example);
        Checks.isTrue(affected == ids.size(), "更新失败");
        return affected;
    }

    /**
     * 通过交易单号、openId和specialId一起查询，是否已经绑定
     * @param tradeParentId
     * @param openId
     * @return
     */
    public List<V2TaobaoOrderOpenidMapInfo> selectBindInfoByTradeParentIdAndOpenId(String tradeParentId, String openId) {
        V2TaobaoOrderOpenidMapInfoExample orderOpenidMapExample = new V2TaobaoOrderOpenidMapInfoExample();
        orderOpenidMapExample.setLimit(50);
        V2TaobaoOrderOpenidMapInfoExample.Criteria criteria = orderOpenidMapExample.createCriteria();

        if (!EmptyUtils.isEmpty(tradeParentId)) {
            criteria.andTradeParentIdEqualTo(tradeParentId);
        }
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }

        //查询
        List<V2TaobaoOrderOpenidMapInfo> orderDetails = v2TaobaoOrderOpenidMapInfoDao.selectByExample(orderOpenidMapExample);
        return orderDetails;
    }

    /**
     *
     * 通过交易单号查询
     * @param parentTradeId
     * @param tradeId
     * @return
     */
    public List<V2TaobaoOrderOpenidMapInfo> selectBindInfoByTradeParentId(String parentTradeId, String tradeId) {
        V2TaobaoOrderOpenidMapInfoExample orderOpenidMapExample = new V2TaobaoOrderOpenidMapInfoExample();
        orderOpenidMapExample.setLimit(50);
        V2TaobaoOrderOpenidMapInfoExample.Criteria criteria = orderOpenidMapExample.createCriteria();

        if (!EmptyUtils.isEmpty(parentTradeId)) {
            criteria.andTradeParentIdEqualTo(parentTradeId);
        }
        if (!EmptyUtils.isEmpty(tradeId)) {
            criteria.andTradeIdEqualTo(tradeId);
            orderOpenidMapExample.setLimit(1);
        }

        //查询
        List<V2TaobaoOrderOpenidMapInfo> orderDetails = v2TaobaoOrderOpenidMapInfoDao.selectByExample(orderOpenidMapExample);
        return orderDetails;
    }

    /**
     *
     * 通过交易单号查询
     * @param parentTradeIds
     * @return
     */
    public List<V2TaobaoOrderOpenidMapInfo> selectBindInfoByTradeParentId(List<String> parentTradeIds) {
        if (EmptyUtils.isEmpty(parentTradeIds)) {
            return Collections.emptyList();
        }

        V2TaobaoOrderOpenidMapInfoExample orderOpenidMapExample = new V2TaobaoOrderOpenidMapInfoExample();
        V2TaobaoOrderOpenidMapInfoExample.Criteria criteria = orderOpenidMapExample.createCriteria();
        criteria.andTradeParentIdIn(parentTradeIds);
        criteria.andStatusEqualTo((byte) 0);

        //查询
        List<V2TaobaoOrderOpenidMapInfo> orderDetails = v2TaobaoOrderOpenidMapInfoDao.selectByExample(orderOpenidMapExample);
        return orderDetails;
    }

    /**
     *
     * 通过交易单号查询
     * @param tradeIds
     * @return
     */
    public List<V2TaobaoOrderOpenidMapInfo> selectBindInfoByTradeId(List<String> tradeIds) {
        if (EmptyUtils.isEmpty(tradeIds)) {
            return Collections.emptyList();
        }

        V2TaobaoOrderOpenidMapInfoExample orderOpenidMapExample = new V2TaobaoOrderOpenidMapInfoExample();
        V2TaobaoOrderOpenidMapInfoExample.Criteria criteria = orderOpenidMapExample.createCriteria();
        criteria.andTradeIdIn(tradeIds);
        criteria.andStatusEqualTo((byte) 0);

        //查询
        List<V2TaobaoOrderOpenidMapInfo> orderDetails = v2TaobaoOrderOpenidMapInfoDao.selectByExample(orderOpenidMapExample);
        return orderDetails;
    }

    /**
     * 查询这个用户所有的数据
     *
     * @param openId
     * @param orderStatusList         订单状态列表
     * @param commissionStatusMsgList 结算状态信息列表
     * @return
     */
    public List<V2TaobaoOrderOpenidMapInfo> selectBindInfoByOpenId(String openId, List<Integer> orderStatusList, List<String> commissionStatusMsgList) {
        if (EmptyUtils.isEmpty(orderStatusList) || EmptyUtils.isEmpty(commissionStatusMsgList)) {
            return Collections.emptyList();
        }

        //按理应该查询订单的下单时间，但是可以查询最近30天的
        LocalDateTime lastModifiedTime = LocalDateTime.now().minusDays(30);

        //先查询订单
        //List<V2TaobaoOrderDetailInfo> orderList = v2TaobaoOrderService.selectOrderListByOpenId(originalTbPayTime, orderStatusList);

        V2TaobaoOrderOpenidMapInfoExample orderOpenidMapExample = new V2TaobaoOrderOpenidMapInfoExample();
        V2TaobaoOrderOpenidMapInfoExample.Criteria criteria = orderOpenidMapExample.createCriteria();
        criteria.andOpenIdEqualTo(openId);
        criteria.andOrderStatusIn(orderStatusList);
        criteria.andCommissionStatusMsgIn(commissionStatusMsgList);
        criteria.andGmtModifiedGreaterThanOrEqualTo(lastModifiedTime);
        criteria.andStatusEqualTo((byte) 0);

        //查询
        List<V2TaobaoOrderOpenidMapInfo> orderDetails = v2TaobaoOrderOpenidMapInfoDao.selectByExample(orderOpenidMapExample);
        return orderDetails;
    }

    /**
     * 统计最近几天的订单量
     * @param lastDaysOfOrder
     * @return
     */
    public long countParentOrderCountOfLastDays(Integer lastDaysOfOrder) {
        //这么多订单，过滤出能绑定成功的
        List<V2TaobaoOrderDetailInfo> v2TaobaoOrderDetailInfos = v2TaobaoOrderService.selectOrderOfLastDays(lastDaysOfOrder);
        List<String> tradeParentIds = v2TaobaoOrderDetailInfos.stream().map(a -> a.getTradeParentId()).collect(Collectors.toList());
        if (tradeParentIds.size() == 0) {
            return 0;
        }

        //这里再查一遍
        V2TaobaoOrderOpenidMapInfoExample orderOpenidMapExample = new V2TaobaoOrderOpenidMapInfoExample();
        V2TaobaoOrderOpenidMapInfoExample.Criteria criteria = orderOpenidMapExample.createCriteria();
        criteria.andStatusEqualTo((byte) 0);
        criteria.andTradeParentIdIn(tradeParentIds);

        //最近几天的数据
        List<V2TaobaoOrderOpenidMapInfo> orderDetails = v2TaobaoOrderOpenidMapInfoDao.selectByExample(orderOpenidMapExample);
        long count = orderDetails.stream().map(a -> a.getTradeParentId()).distinct().mapToInt(a -> 1).count();
        return count;
    }

    /**
     * 理论上的总返利
     * @return
     */
    public BigDecimal sumAllTheoreticalCommission() {
        V2TaobaoOrderOpenidMapInfoExample orderOpenidMapExample = new V2TaobaoOrderOpenidMapInfoExample();
        V2TaobaoOrderOpenidMapInfoExample.Criteria criteria = orderOpenidMapExample.createCriteria();
        criteria.andStatusEqualTo((byte) 0);
        criteria.andOrderStatusEqualTo(3);
        criteria.andCommissionStatusMsgEqualTo("结算成功");

        double commission = v2TaobaoOrderOpenidMapInfoDao.sumAllTheoreticalCommissionByExample(orderOpenidMapExample);
        return new BigDecimal(NumberUtil.format(commission));
    }
}
