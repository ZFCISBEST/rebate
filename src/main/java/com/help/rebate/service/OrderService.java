package com.help.rebate.service;

import com.help.rebate.dao.OrderDetailDao;
import com.help.rebate.dao.entity.OrderDetail;
import com.help.rebate.dao.entity.OrderDetailExample;
import com.help.rebate.service.schedule.FixedOrderSyncTask;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单数据服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    /**
     * 订单详情接口
     */
    @Resource
    private OrderDetailDao orderDetailDao;

    /**
     * 定时订单同步器
     */
    @Resource
    private FixedOrderSyncTask fixedOrderSyncTask;

    /**
     * 时间游标记录
     */
    @Resource
    private TimeCursorPositionService TimeCursorPositionService;



    /**
     * 列出符合条件的所有订单
     * @return
     */
    public List<OrderDetail> listAll(String parentTradeId, String tradeId, String specialId, String relationId, int pageNo, int pageSize) {
        //查出所有没有被删除的
        OrderDetailExample orderDetailExample = new OrderDetailExample();
        OrderDetailExample.Criteria criteria = orderDetailExample.createCriteria();
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

        orderDetailExample.setOffset((long) (pageNo - 1) * pageSize);
        orderDetailExample.setLimit(pageSize);

        //查询
        List<OrderDetail> orderDetails = orderDetailDao.selectByExample(orderDetailExample);
        return orderDetails;
    }

    /**
     * 订单同步
     *
     * @param orderUpdateTime
     * @param minuteStep
     * @param orderScene
     * @param queryType
     * @param running
     * @return
     */
    public int syncOrder(String orderUpdateTime, Long minuteStep, int orderScene, Integer queryType, Boolean running) {
        //如果不运行
        if (!running) {
            fixedOrderSyncTask.setRunning(false);
            return 0;
        }

        //运行的话，更新时间游标数据库
        if (orderUpdateTime != null && minuteStep != null) {
            TimeCursorPositionService.saveOrUpdateTimeCursor(TimeUtil.parseDate(orderUpdateTime), new Long(minuteStep * 60).intValue(), orderScene, queryType);
        }

        //调用执行
        fixedOrderSyncTask.cleanContext(orderUpdateTime, minuteStep, orderScene, queryType, running);
        return 0;
    }

    /**
     *
     * 通过交易单号查询
     * @param parentTradeId
     * @param tradeId
     * @return
     */
    public List<OrderDetail> selectByTradeId(String parentTradeId, String tradeId) {
        OrderDetailExample orderDetailExample = new OrderDetailExample();
        orderDetailExample.setLimit(20);
        OrderDetailExample.Criteria criteria = orderDetailExample.createCriteria();

        if (!EmptyUtils.isEmpty(parentTradeId)) {
            criteria.andParentTradeIdEqualTo(parentTradeId);
        }
        if (!EmptyUtils.isEmpty(tradeId)) {
            criteria.andTradeIdEqualTo(tradeId);
            orderDetailExample.setLimit(1);
        }

        //查询
        List<OrderDetail> orderDetails = orderDetailDao.selectByExample(orderDetailExample);
        return orderDetails;
    }

    /**
     * 保存一个新对象
     * @param orderDetail
     * @return
     */
    public int save(OrderDetail orderDetail) {
        int affectedCnt = orderDetailDao.insertSelective(orderDetail);
        return affectedCnt;
    }

    /**
     * 更新一个新对象
     * @param orderDetail
     * @return
     */
    public int update(OrderDetail orderDetail) {
        int affectedCnt = orderDetailDao.updateByPrimaryKeySelective(orderDetail);
        return affectedCnt;
    }
}
