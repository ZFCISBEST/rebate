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
     * jd基础信息
     */
    public final static long JD_UNIONID = 1000399057L;
    public final static String JD_KEY = "e4209eac5db9f8e291a5aaf7d7c8c1421f47e224447b23cdfea6d6e34fce6a4bfab3eaaf36bbb55c";

    /**
     * 获取会员信息的链接
     */
    public final static String TB_PUBLISHER_URL = "http://api.tbk.dingdanxia.com/tbk/publisher_get";

    /**
     * 高佣转链接接口
     */
    public final static String TB_TKL_PRIVILEGES_URL = "http://api.tbk.dingdanxia.com/tbk/tkl_privilege";

    /**
     * 订单同步接口
     */
    public final static String TB_TKL_ORDER_DETAILS_URL = "http://api.tbk.dingdanxia.com/tbk/order_details";

    /**
     * 京东转链接口
     */
    public final static String JD_BY_UNIONID_PROMOTION_URL = "http://api.tbk.dingdanxia.com/jd/by_unionid_promotion";

    /**
     * 拼多多转链接口
     */
    public final static String PDD_URL_CONVERT_URL = "http://api.tbk.dingdanxia.com/pdd/url_convert";
}
