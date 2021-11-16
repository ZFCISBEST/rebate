package com.help.rebate.commons;

/**
 * 订单侠信息
 *
 * @author zfcisbest
 * @date 21/11/14
 */
public class DdxConfig {
    /**
     * 基础信息
     */
    public final static String taobaoAppKey = "33165315";
    public final static String taobaoAppSecret = "7d485e51e0589561f1700438075b1ef8";
    public final static String ddxApiKey = "TopNoEKW13PQMoG8F50uRLiZ8jwoW3A1";

    /**
     * 获取会员信息的链接
     */
    public final static String PUBLISHER_URL = "http://api.tbk.dingdanxia.com/tbk/publisher_get";

    /**
     * 高佣转链接接口
     */
    public final static String TKL_PRIVILEGES_URL = "http://api.tbk.dingdanxia.com/tbk/tkl_privilege";

    /**
     * 订单同步接口
     */
    public final static String TKL_ORDER_DETAILS_URL = "http://api.tbk.dingdanxia.com/tbk/order_details";
}
