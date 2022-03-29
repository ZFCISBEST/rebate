package com.help.rebate.vo;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单绑定结果视图
 */
@Data
public class OrderBindResultVO {
    private static final Logger logger = LoggerFactory.getLogger(OrderBindResultVO.class);

    /**
     * 绑定到的openId
     */
    private String openId;

    /**
     * 绑定到的会员ID
     */
    private String specialId;

    /**
     * 绑定的 父订单ID
     */
    private String tradeParentId;

    /**
     * 详细订单
     */
    private List<String> tradeIdItemIdList = new ArrayList<>();
}
