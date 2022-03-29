package com.help.rebate.vo;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提现返利的视图
 * 主要：每个状态下，要执行的动作，或者给用户的提示
 * 状态流转：触发提现、提现成功、提现失败、提现超时
 */
@Data
public class PickCommissionVO {
    private static final Logger logger = LoggerFactory.getLogger(PickCommissionVO.class);

    /**
     * 触发提现、提现成功、提现失败、提现超时
     */
    private String action;

    /**
     * 本平台需要给用户返利的钱
     */
    private String commission;

    /**
     * 父订单 对 详细的订单列表
     */
    private Map<String, List<String>> tradeParentId2TradeIdsMap = new HashMap<String, List<String>>();
}
