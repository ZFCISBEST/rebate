package com.help.rebate.service;

import com.help.rebate.dao.V2TaobaoOrderOpenidMapInfoDao;
import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapInfoExample;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * 通过交易单号、openId和specialId一起查询，是否已经绑定
     * @param tradeParentId
     * @param openId
     * @return
     */
    public List<V2TaobaoOrderOpenidMapInfo> selectBindInfoByTradeParentId(String tradeParentId, String openId) {
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
    public List<V2TaobaoOrderOpenidMapInfo> selectBindInfoByTradeId(String parentTradeId, String tradeId) {
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
    public List<V2TaobaoOrderOpenidMapInfo> selectBindInfoByTradeId(List<String> parentTradeIds) {
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
     * 更改绑定的订单状态
     * @param openId
     * @param tradeParentId2TradeIdsMap
     * @param commissionStatusMsg
     * @return
     */
    public int changeCommissionStatusMsgByTradeParentIds(String openId, Map<String, List<String>> tradeParentId2TradeIdsMap, String commissionStatusMsg) {
        //条件
        V2TaobaoOrderOpenidMapInfoExample example = new V2TaobaoOrderOpenidMapInfoExample();
        V2TaobaoOrderOpenidMapInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo((byte) 0);
        if (!EmptyUtils.isEmpty(openId)) {
            criteria.andOpenIdEqualTo(openId);
        }
        if (!EmptyUtils.isEmpty(tradeParentId2TradeIdsMap.keySet())) {
            List<String> tradeIds = tradeParentId2TradeIdsMap.values().stream().flatMap(a -> a.stream()).collect(Collectors.toList());
            if (!tradeIds.isEmpty()) {
                criteria.andTradeIdIn(tradeIds);
            }
        }
        List<V2TaobaoOrderOpenidMapInfo> orderOpenidMapList = v2TaobaoOrderOpenidMapInfoDao.selectByExample(example);
        for (V2TaobaoOrderOpenidMapInfo openidMap : orderOpenidMapList) {
            openidMap.setGmtModified(LocalDateTime.now());

            //只能正向走 - 待提取 -> 提取中 -> 提取失败, 提取超时 -> 提取成功
            if (commissionStatusMsg.equalsIgnoreCase(openidMap.getCommissionStatusMsg())) {
                continue;
            }

            //已经成功了
            if ("提取成功".equalsIgnoreCase(openidMap.getCommissionStatusMsg())) {
                continue;
            }

            openidMap.setCommissionStatusMsg(commissionStatusMsg);

            //更新 - 后面再写一个批量更新
            int affected = v2TaobaoOrderOpenidMapInfoDao.updateByPrimaryKeySelective(openidMap);
            Checks.isTrue(affected == 1, "更新失败");
        }

        //返回
        return orderOpenidMapList.size();
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
}
