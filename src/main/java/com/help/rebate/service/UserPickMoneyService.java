package com.help.rebate.service;

import com.help.rebate.dao.OrderDetailDao;
import com.help.rebate.dao.OrderOpenidMapDao;
import com.help.rebate.dao.TklConvertHistoryDao;
import com.help.rebate.dao.entity.OrderDetail;
import com.help.rebate.dao.entity.OrderOpenidMap;
import com.help.rebate.dao.entity.TklConvertHistory;
import com.help.rebate.dao.entity.UserInfos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 订单绑定服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class UserPickMoneyService {
    private static final Logger logger = LoggerFactory.getLogger(UserPickMoneyService.class);

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
    private V2TaobaoUserInfoService v2TaobaoUserInfoService;

    /**
     * 订单映射表
     */
    @Resource
    private OrderOpenidMapDao orderOpenidMapDao;
    @Resource
    private OrderOpenidMapService orderOpenidMapService;

    /**
     * 统计 返利信息
     * 这两个参数，可以同时传入，也可以分开传入，如果同时传入，那么必须是一致的才行，否则报错
     * @param openId 用户的openid
     * @param specialId 外部传入的specialid
     *
     * @return
     */
    public Map<String, Object> countCommissions(String openId, String specialId) {
        return null;
    }


    /**
     * 通过时间段，筛选出订单，并完成绑定
     * @param startTime 起始时间
     * @param minuteStep 起始时间开始后，时间跨度，分钟为单位
     * @param timeType 时间类型，- 0-订单插入时间, 1-订单创建时间，2-订单支付时间，3-订单结算时间，4-订单更新时间
     */
    public void bindOrderByTimeRange(String startTime, int minuteStep, String timeType) {

    }
}
