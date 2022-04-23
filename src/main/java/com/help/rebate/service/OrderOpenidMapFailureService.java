package com.help.rebate.service;
import java.time.LocalDateTime;

import com.help.rebate.dao.OrderDetailDao;
import com.help.rebate.dao.OrderOpenidMapDao;
import com.help.rebate.dao.OrderOpenidMapFailureDao;
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
public class OrderOpenidMapFailureService {
    private static final Logger logger = LoggerFactory.getLogger(OrderOpenidMapFailureService.class);

    /**
     * 订单映射表
     */
    @Resource
    private OrderOpenidMapDao orderOpenidMapDao;
    @Resource
    private OrderOpenidMapService orderOpenidMapService;

    /**
     * 订单失败记录表
     */
    @Resource
    private OrderOpenidMapFailureDao orderOpenidMapFailureDao;


    /**
     * 插入或者更新订单绑定失败的订单
     * @param orderDetailList
     * @param reason
     */
    public void insertOrDoNoneOrderInfo(List<OrderDetail> orderDetailList, String reason) {
        if (EmptyUtils.isEmpty(orderDetailList)) {
            return;
        }

        //循环插入
        for (OrderDetail orderDetail : orderDetailList) {
            OrderOpenidMapFailure orderOpenidMapFailure = getFailureOrder(orderDetail.getTradeId());

            //更新
            if (orderOpenidMapFailure != null) {
                continue;
            }
            //插入
            else {
                orderOpenidMapFailure = new OrderOpenidMapFailure();
                orderOpenidMapFailure.setGmtCreated(LocalDateTime.now());
                orderOpenidMapFailure.setGmtModified(LocalDateTime.now());
                orderOpenidMapFailure.setTradeId(orderDetail.getTradeId());
                orderOpenidMapFailure.setParentTradeId(orderDetail.getParentTradeId());
                orderOpenidMapFailure.setItemId(orderDetail.getItemId());
                orderOpenidMapFailure.setFailReason(reason);
                orderOpenidMapFailure.setStatus(0);
                int affectedCnt = orderOpenidMapFailureDao.insert(orderOpenidMapFailure);
                Checks.isTrue(affectedCnt == 1, "记录未绑定订单失败");
            }
        }
    }

    /**
     * 获取失败的订单
     * @param tradeId
     * @return
     */
    private OrderOpenidMapFailure getFailureOrder(String tradeId) {
        OrderOpenidMapFailureExample example = new OrderOpenidMapFailureExample();
        example.setLimit(1);
        OrderOpenidMapFailureExample.Criteria criteria = example.createCriteria();
        criteria.andTradeIdEqualTo(tradeId);
        criteria.andStatusEqualTo(0);

        List<OrderOpenidMapFailure> orderOpenidMapFailures = orderOpenidMapFailureDao.selectByExample(example);
        if (orderOpenidMapFailures.isEmpty()) {
            return null;
        }
        return orderOpenidMapFailures.get(0);
    }
}
