package com.help.rebate.service;

import com.help.rebate.dao.OrderDetailDao;
import com.help.rebate.dao.OrderOpenidMapDao;
import com.help.rebate.dao.TklConvertHistoryDao;
import com.help.rebate.dao.entity.OrderDetail;
import com.help.rebate.dao.entity.OrderDetailExample;
import com.help.rebate.dao.entity.OrderOpenidMap;
import com.help.rebate.dao.entity.TklConvertHistory;
import com.help.rebate.service.schedule.FixedOrderSyncTask;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

    /**
     * 订单服务
     */
    @Resource
    private OrderService orderService;

    /**
     * 转链历史记录表
     */
    @Resource
    private TklConvertHistoryDao tklConvertHistoryDao;

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
     * 通过交易单号，进行绑定操作
     * @param parentTradeId 必传字段
     * @param tradeIds 可传可不传
     * @return
     */
    public String bindByTradeId(String parentTradeId, String... tradeIds) {
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
        if (!EmptyUtils.isEmpty(orderOpenidMapList)) {
            createOrderOpenidMapBy(orderOpenidMapList.get(0), tradeId2OrderDetailMap);
            return null;
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

        return null;
    }

    /**
     * 根据给定的绑定关系，将已知的其他订单绑定到相同的用户信息上
     * @param orderOpenidMap
     * @param tradeId2OrderDetailMap
     */
    private void createOrderOpenidMapBy(OrderOpenidMap orderOpenidMap, Map<String, OrderDetail> tradeId2OrderDetailMap) {

    }

    /**
     * 会员用户，通过specialId进行绑定
     * @param orderDetailList
     */
    private void bindBySpecialId(List<OrderDetail> orderDetailList) {
        //第一种，这里的所有商品，至少有被转链过，那么查出来，那么这种情况，是可以建立openid和specialid的关系并存入用户表的

        //第二种，这里所有的商品，都没有被转链过，那么只能存入specialid字段，其他openid这些数据不填写，mapType就是specialid，表示只是会员

    }

    /**
     * 普通用户，通过推广位和商品的ID去查询转链记录表，看是否转过
     * @param orderDetailList
     */
    private void bindByPubSite(List<OrderDetail> orderDetailList) {

    }

    /**
     * 根据需要，做绑定的更新
     * 如果订单状态还没有结束，那么需要前向走，更新订单状态
     * 如果我们平台给用户的状态已经结算，或者关闭，那么不用更新
     * 每次更新的时候，主要更新一下其他字段（如预期返利）
     * 应该多个状态，就是如果实际已经返利给用户了，但是订单状态发生了商家维权，造成我们自己资损，需要记录下来实际给用户反了多少，后面要补回来
     * @param orderOpenidMap
     * @param orderDetail
     */
    private void updateOrderOpenidMapIfNeeded(OrderOpenidMap orderOpenidMap, OrderDetail orderDetail) {

    }
}
