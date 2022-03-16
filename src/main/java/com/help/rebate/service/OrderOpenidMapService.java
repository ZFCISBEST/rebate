package com.help.rebate.service;

import com.help.rebate.dao.OrderOpenidMapDao;
import com.help.rebate.dao.entity.OrderDetail;
import com.help.rebate.dao.entity.OrderDetailExample;
import com.help.rebate.dao.entity.OrderOpenidMap;
import com.help.rebate.dao.entity.OrderOpenidMapExample;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单映射数据服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class OrderOpenidMapService {
    private static final Logger logger = LoggerFactory.getLogger(OrderOpenidMapService.class);

    /**
     * 订单映射详情接口
     */
    @Resource
    private OrderOpenidMapDao orderOpenidMapDao;



    /**
     * 列出符合条件的所有绑定的订单
     * @return
     */
    public List<OrderOpenidMap> listAll(String parentTradeId, String tradeId, String specialId, String relationId, int pageNo, int pageSize) {
        //查出所有没有被删除的
        OrderOpenidMapExample orderOpenidMapExample = new OrderOpenidMapExample();
        OrderOpenidMapExample.Criteria criteria = orderOpenidMapExample.createCriteria();
        if (!EmptyUtils.isEmpty(parentTradeId)) {
            criteria.andParentTradeIdEqualTo(parentTradeId);
        }
        if (!EmptyUtils.isEmpty(tradeId)) {
            criteria.andTradeIdEqualTo(tradeId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            criteria.andSpecialIdEqualTo(specialId);
        }
        if (!EmptyUtils.isEmpty(relationId)) {
            criteria.andRelationIdEqualTo(relationId);
        }

        orderOpenidMapExample.setOffset((long) (pageNo - 1) * pageSize);
        orderOpenidMapExample.setLimit(pageSize);

        //查询
        List<OrderOpenidMap> orderOpenidMaps = orderOpenidMapDao.selectByExample(orderOpenidMapExample);
        return orderOpenidMaps;
    }

    /**
     *
     * 通过交易单号查询
     * @param parentTradeId
     * @param tradeId
     * @return
     */
    public List<OrderOpenidMap> selectByTradeId(String parentTradeId, String tradeId) {
        OrderOpenidMapExample orderOpenidMapExample = new OrderOpenidMapExample();
        orderOpenidMapExample.setLimit(20);
        OrderOpenidMapExample.Criteria criteria = orderOpenidMapExample.createCriteria();

        if (!EmptyUtils.isEmpty(parentTradeId)) {
            criteria.andParentTradeIdEqualTo(parentTradeId);
        }
        if (!EmptyUtils.isEmpty(tradeId)) {
            criteria.andTradeIdEqualTo(tradeId);
            orderOpenidMapExample.setLimit(1);
        }

        //查询
        List<OrderOpenidMap> orderDetails = orderOpenidMapDao.selectByExample(orderOpenidMapExample);
        return orderDetails;
    }

    /**
     * 保存一个新对象
     * @param orderOpenidMap
     * @return
     */
    public int save(OrderOpenidMap orderOpenidMap) {
        int affectedCnt = orderOpenidMapDao.insertSelective(orderOpenidMap);
        return affectedCnt;
    }

    /**
     * 更新一个新对象
     * @param orderOpenidMap
     * @return
     */
    public int update(OrderOpenidMap orderOpenidMap) {
        int affectedCnt = orderOpenidMapDao.updateByPrimaryKeySelective(orderOpenidMap);
        return affectedCnt;
    }
}
