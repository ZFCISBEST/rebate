package com.help.rebate.service;


import com.help.rebate.dao.V2TaobaoOrderDetailInfoDao;
import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfo;
import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfoExample;
import com.help.rebate.service.schedule.FixedOrderSyncTask;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单数据服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class V2TaobaoOrderService {
    private static final Logger logger = LoggerFactory.getLogger(V2TaobaoOrderService.class);

    /**
     * 订单详情接口
     */
    @Resource
    private V2TaobaoOrderDetailInfoDao v2TaobaoOrderDetailInfoDao;

    /**
     * 时间游标记录
     */
    @Resource
    private V2TaobaoSyncOrderOffsetInfoService v2TaobaoSyncOrderOffsetInfoService;

    /**
     * 定时订单同步器
     */
    @Resource
    private FixedOrderSyncTask fixedOrderSyncTask;

    /**
     * 订单同步
     *
     * @param orderSyncStartTime
     * @param minuteStep
     * @param orderType
     * @param queryType
     * @param running
     * @return
     */
    public int scheduleSyncOrder(String orderSyncStartTime, Long minuteStep, int orderType, Integer queryType, Boolean running) {
        //如果不运行
        if (!running) {
            fixedOrderSyncTask.setRunning(false);
            return 0;
        }

        //运行的话，更新时间游标数据库
        if (orderSyncStartTime != null && minuteStep != null) {
            v2TaobaoSyncOrderOffsetInfoService.upsertSyncStartTimeOffset(TimeUtil.parseLocalDate(orderSyncStartTime),
                    new Long(minuteStep * 60).intValue(), orderType, queryType);
        }

        //调用执行
        fixedOrderSyncTask.resetScheduleContext(running);
        return 0;
    }

    /**
     *
     * 列出所有符合条件的订单
     * @param parentTradeId
     * @param tradeId
     * @param specialId
     * @param relationId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<V2TaobaoOrderDetailInfo> listAll(String parentTradeId, String tradeId, String specialId, String relationId, int pageNo, int pageSize) {
        //查出所有没有被删除的
        V2TaobaoOrderDetailInfoExample orderDetailExample = new V2TaobaoOrderDetailInfoExample();
        V2TaobaoOrderDetailInfoExample.Criteria criteria = orderDetailExample.createCriteria();
        if (!EmptyUtils.isEmpty(parentTradeId)) {
            criteria.andTradeParentIdEqualTo(parentTradeId);
        }
        if (!EmptyUtils.isEmpty(tradeId)) {
            criteria.andTradeIdEqualTo(tradeId);
            orderDetailExample.setLimit(1);
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
        List<V2TaobaoOrderDetailInfo> orderDetails = v2TaobaoOrderDetailInfoDao.selectByExample(orderDetailExample);
        return orderDetails;
    }

    /**
     *
     * 通过交易单号查询
     * @param tradeParentId
     * @param tradeId
     * @return
     */
    public List<V2TaobaoOrderDetailInfo> selectByTradeId(String tradeParentId, String tradeId) {
        V2TaobaoOrderDetailInfoExample orderDetailExample = new V2TaobaoOrderDetailInfoExample();
        orderDetailExample.setLimit(200);
        V2TaobaoOrderDetailInfoExample.Criteria criteria = orderDetailExample.createCriteria();

        if (!EmptyUtils.isEmpty(tradeParentId)) {
            criteria.andTradeParentIdEqualTo(tradeParentId);
        }
        if (!EmptyUtils.isEmpty(tradeId)) {
            criteria.andTradeIdEqualTo(tradeId);
            orderDetailExample.setLimit(1);
        }

        //查询
        List<V2TaobaoOrderDetailInfo> orderDetails = v2TaobaoOrderDetailInfoDao.selectByExample(orderDetailExample);
        return orderDetails;
    }

    /**
     * 保存一个新对象
     * @param v2TaobaoOrderDetailInfo
     * @return
     */
    public int save(V2TaobaoOrderDetailInfo v2TaobaoOrderDetailInfo) {
        int affectedCnt = v2TaobaoOrderDetailInfoDao.insertSelective(v2TaobaoOrderDetailInfo);
        return affectedCnt;
    }

    /**
     * 更新一个新对象
     * @param orderDetail
     * @return
     */
    public int update(V2TaobaoOrderDetailInfo orderDetail) {
        int affectedCnt = v2TaobaoOrderDetailInfoDao.updateByPrimaryKeySelective(orderDetail);
        return affectedCnt;
    }

    /**
     * 筛选符合条件的订单
     * @param tradeParentIdList
     * @param payStartTime
     * @param payEndTime
     * @return
     */
    public List<V2TaobaoOrderDetailInfo> selectByTradeParentIds(List<String> tradeParentIdList, String payStartTime, String payEndTime) {
        V2TaobaoOrderDetailInfoExample orderDetailExample = new V2TaobaoOrderDetailInfoExample();
        V2TaobaoOrderDetailInfoExample.Criteria criteria = orderDetailExample.createCriteria();

        if (!EmptyUtils.isEmpty(tradeParentIdList)) {
            criteria.andTradeParentIdIn(tradeParentIdList);
        }
        if (!EmptyUtils.isEmpty(payStartTime)) {
            criteria.andTbPaidTimeGreaterThanOrEqualTo(TimeUtil.parseLocalDate(payStartTime));
        }
        if (!EmptyUtils.isEmpty(payEndTime)) {
            criteria.andTbPaidTimeLessThanOrEqualTo(TimeUtil.parseLocalDate(payEndTime));
        }

        //查询
        List<V2TaobaoOrderDetailInfo> orderDetails = v2TaobaoOrderDetailInfoDao.selectByExample(orderDetailExample);
        return orderDetails;
    }

    /**
     * 筛选符合条件的订单
     * @param payStartTime
     * @param payEndTime
     * @return
     */
    public List<V2TaobaoOrderDetailInfo> selectByPayTimeRange(LocalDateTime payStartTime, LocalDateTime payEndTime) {
        V2TaobaoOrderDetailInfoExample orderDetailExample = new V2TaobaoOrderDetailInfoExample();
        V2TaobaoOrderDetailInfoExample.Criteria criteria = orderDetailExample.createCriteria();

        criteria.andTbPaidTimeGreaterThanOrEqualTo(payStartTime);
        criteria.andTbPaidTimeLessThanOrEqualTo(payEndTime);

        //订单的付款时间，也必须不能晚于当前的时间
        criteria.andTbPaidTimeGreaterThanOrEqualTo(TimeUtil.parseLocalDate("2021-12-25 00:00:00"));

        //查询
        List<V2TaobaoOrderDetailInfo> orderDetails = v2TaobaoOrderDetailInfoDao.selectByExample(orderDetailExample);
        return orderDetails;
    }

    /**
     * 筛选符合条件的订单
     * @param payModifiedStartTime
     * @param payModifiedEndTime
     * @return
     */
    public List<V2TaobaoOrderDetailInfo> selectByModifiedTimeRange(String payModifiedStartTime, String payModifiedEndTime) {
        V2TaobaoOrderDetailInfoExample orderDetailExample = new V2TaobaoOrderDetailInfoExample();
        V2TaobaoOrderDetailInfoExample.Criteria criteria = orderDetailExample.createCriteria();

        criteria.andModifiedTimeGreaterThanOrEqualTo(payModifiedStartTime);
        criteria.andModifiedTimeLessThanOrEqualTo(payModifiedEndTime);

        //订单的付款时间，也必须不能晚于当前的时间
        criteria.andTbPaidTimeGreaterThanOrEqualTo(TimeUtil.parseLocalDate("2021-12-25 00:00:00"));

        //查询
        List<V2TaobaoOrderDetailInfo> orderDetails = v2TaobaoOrderDetailInfoDao.selectByExample(orderDetailExample);
        return orderDetails;
    }
}
