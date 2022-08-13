package com.help.rebate.commons;

/**
 * 订单侠信息
 *
 * @author zfcisbest
 * @date 21/11/14
 */
public class DtkConfig {
    /**
     * 基础信息
     */
    public final static String dtkAppkey = "61961877e8403";
    public final static String dtkAppsecret = "30511c332ab42746a0fd0f43b948975f";

    /**
     * 淘系万能解析,根据淘口令生成商品id
     */
    public final static String DTK_PARSE_CONTENT ="https://openapi.dataoke.com/api/tb-service/parse-content";

    /**
     * 高效转链,根据商品id获取返利tkl
     */
    public final static String DTK_GET_PRIVILEGE_TKL ="https://openapi.dataoke.com/api/tb-service/get-privilege-link";
    public final static String DTK_GET_TWD_2_TWD ="https://openapi.dataoke.com/api/tb-service/twd-to-twd";


    /**
     * 订单同步接口
     */
    public final static String DTK_TB_ORDER_DETAILS_URL = "https://openapi.dataoke.com/api/tb-service/get-order-details";
}
