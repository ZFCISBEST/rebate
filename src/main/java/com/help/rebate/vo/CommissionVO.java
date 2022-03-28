package com.help.rebate.vo;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返利信息封装
 */
@Data
public class CommissionVO {
    private static final Logger logger = LoggerFactory.getLogger(CommissionVO.class);

    /**
     * 状态提示
     */
    private String label;

    /**
     * 商家预计或者已经给的返利费用
     */
    private String pubFee;

    /**
     * 本平台给用户返利的钱
     */
    private String commission;

    /**
     * 父订单 对 详细的商品列表
     */
    private Map<String, List<String>> tradeParentId2ItemIdsMap = new HashMap<String, List<String>>();
}
