package com.help.rebate.service.ddx.pdd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DdxConfig;
import com.help.rebate.commons.PrettyHttpService;
import com.help.rebate.utils.PropertyValueResolver;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单侠商品转链接
 * 参考文档 - https://www.dingdanxia.com/apitest/150/86
 * @author zfcisbest
 * @date 21/11/20
 */
@Service
public class DdxPddItemConverter {
    private static final Logger logger = LoggerFactory.getLogger(DdxPddItemConverter.class);

    /**
     * 金额格式化
     */
    private static final DecimalFormat decimal = new DecimalFormat("##.##");

    /**
     * 生成的口令的模板
     */
    private static final String template_simple = "$title\n" +
            "【最低价】$finalPrice元\n" +
            "【预计返】$returnPrice\n" +
            "【领券抢购】$url\n";
    /**
     * http服务
     */
    @Autowired
    private PrettyHttpService prettyHttpService;

    /**
     * 生成京东的转链
     * @param url
     * @param openId
     * @param tempReturnRate
     * @return
     */
    public PddLinkDO generateReturnPriceInfo(String url, String openId, Double tempReturnRate){
        if (tempReturnRate == null || tempReturnRate <= 0) {
            tempReturnRate = 0.8;
        }

        //获取
        JSONObject jsonObject = parseLink(url, openId);
        JSONObject data = jsonObject.getJSONObject("data");

        //解析参数
        String goodsId = data.getString("goods_id");
        String goodsName = PropertyValueResolver.getProperty(data, "itemInfo.goods_name", true);
        String goodsSign = PropertyValueResolver.getProperty(data, "itemInfo.goods_sign", true);

        //返利优惠 - 最小拼团价(分)
        long minGroupPrice = Long.parseLong(PropertyValueResolver.getProperty(data, "itemInfo.min_group_price", true) + "");
        Object couponDiscountObj = PropertyValueResolver.getProperty(data, "itemInfo.coupon_discount", false);
        long couponDiscount = couponDiscountObj == null ? 0L : Long.parseLong(couponDiscountObj + "");

        //佣金比例，千分比
        Object minGroupPriceObj = PropertyValueResolver.getProperty(data, "itemInfo.promotion_rate", false);
        long promotionRate = minGroupPriceObj == null ? 0L : Long.parseLong(minGroupPriceObj + "");


        //比价行为预判定佣金，需要用户备案
        Object predictPromotionRateObj = PropertyValueResolver.getProperty(data, "itemInfo.predict_promotion_rate", false);
        long predictPromotionRate = predictPromotionRateObj == null ? 0L : Long.parseLong(predictPromotionRateObj + "");

        //短口令 -
        String mobileShortUrl = PropertyValueResolver.getProperty(data, "mobile_short_url");
        String multiGroupMobileShortUrl = PropertyValueResolver.getProperty(data, "multi_group_mobile_short_url");
        String multiGroupShortUrl = PropertyValueResolver.getProperty(data, "multi_group_short_url");
        String shortUrl = PropertyValueResolver.getProperty(data, "short_url");
        String allIn = "【短连接】" + mobileShortUrl +"\n";
        allIn += "【团购短连接】" + multiGroupMobileShortUrl +"\n";
        allIn += "【二人团】" + multiGroupShortUrl +"\n";
        allIn += "【一人团】" + shortUrl + "\n";
        allIn += "sign = " + goodsSign;

        //返利计算
        double commissionShare = (minGroupPrice - couponDiscount) / 100.0 * promotionRate / 1000.0 * tempReturnRate;

        //根据模板生成新的淘口令
        String content = template_simple.replace("$finalPrice", decimal.format(minGroupPrice / 100.0))
                .replace("$returnPrice", decimal.format(commissionShare) + "元")
                .replaceAll("\\$url", allIn)
                .replace("$title", goodsName);

        return new PddLinkDO(content, goodsId, goodsSign);
    }

    /**
     * 链接转链接
     * @param sourceUrl
     * @param openId
     * @return
     */
    public JSONObject parseLink(String sourceUrl, String openId) {
        //基础参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", DdxConfig.ddxApiKey);
        params.put("source_url", sourceUrl);
        params.put("pid", "9404445_226741196");
        params.put("custom_parameters", "{\"uid\":\"tangpingbujuan_001\", \"open_id\":\"" + openId + "\"}");
        params.put("itemInfo", true);

        String result = prettyHttpService.get(DdxConfig.PDD_URL_CONVERT_URL, params);
        return JSON.parseObject(result);
    }

    /**
     * 转链后存储信息
     */
    @Data
    public static class PddLinkDO {
        private String linkInfo;
        private String goodsId;
        private String goodsSign;

        public PddLinkDO(String linkInfo, String goodsId, String goodsSign) {
            this.linkInfo = linkInfo;
            this.goodsId = goodsId;
            this.goodsSign = goodsSign;
        }

        public String getLinkInfo() {
            return linkInfo;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public String getGoodsSign() {
            return goodsSign;
        }

        @Override
        public String toString() {
            return "PDDLinkDO{" +
                    "linkInfo='" + linkInfo + '\'' +
                    ", goodsId='" + goodsId + '\'' +
                    ", goodsSign'" + goodsSign + '\'' +
                    '}';
        }
    }
}
