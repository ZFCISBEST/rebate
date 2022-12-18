package com.help.rebate.service.ddx.jd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DdxConfig;
import com.help.rebate.commons.PrettyHttpService;
import com.help.rebate.service.ddx.tb.DdxReturnPriceService;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.PropertyValueResolver;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单侠商品转链接
 * 参考文档 - https://www.dingdanxia.com/doc/97/94
 * @author zfcisbest
 * @date 21/11/20
 */
@Deprecated
@Service
public class DdxJDItemConverter {
    private static final Logger logger = LoggerFactory.getLogger(DdxJDItemConverter.class);

    /**
     * 金额格式化
     */
    private static final DecimalFormat decimal = new DecimalFormat("##.##");

    /**
     * 生成的口令的模板
     */
    private static final String template_simple = "$title $jcmd\n" +
            "【最低价】$finalPrice元\n" +
            "【预计返】$returnPrice (plus返 $returnPlusPrice)\n" +
            "【领券抢购】$url\n" +
            "【小程序】<a href=\"$url\" data-miniprogram-appid=\"wx91d27dbf599dff74\" data-miniprogram-path=\"pages/union/proxy/proxy?spreadUrl=$url\">点击跳转小程序</a>";

    /**
     * http服务
     */
    @Autowired
    private PrettyHttpService prettyHttpService;

    /**
     * 生成京东的转链
     * @param materialId
     * @param positionId
     * @param subUnionId
     * @param tempReturnRate 千分位的返利比例
     * @return
     */
    public JDLinkDO generateReturnPriceInfo(String materialId, Long positionId, String subUnionId, int tempReturnRate){
        if (tempReturnRate <= 0) {
            tempReturnRate = 900;
        }

        //获取
        JSONObject jsonObject = parseLink(materialId, positionId, subUnionId);
        JSONObject data = jsonObject.getJSONObject("data");

        //解析参数
        Long skuId = data.getLong("skuId");
        String skuName = data.getString("skuName");
        BigDecimal lowestPrice = new BigDecimal(PropertyValueResolver.getProperty(data, "priceInfo.lowestPrice", true) + "");
        BigDecimal commissionShare = new BigDecimal(PropertyValueResolver.getProperty(data, "commissionInfo.commissionShare", true) + "");
        BigDecimal plusCommissionShare = new BigDecimal(PropertyValueResolver.getProperty(data, "commissionInfo.plusCommissionShare", true) + "");
        String owner = PropertyValueResolver.getProperty(data, "owner");
        BigDecimal fenchengPercent = new BigDecimal("1");
        if (!"g".equals(owner)) {
            fenchengPercent = new BigDecimal("0.9");
        }

        //京东口令 - 京口令（匹配到红包活动有效配置才会返回京口令）
        String jCommand = PropertyValueResolver.getProperty(data, "jCommand", false);
        String link = PropertyValueResolver.getProperty(data, "shortURL");

        //返利计算
        commissionShare = commissionShare.multiply(fenchengPercent).multiply(new BigDecimal(tempReturnRate)).multiply(new BigDecimal("0.001"));
        plusCommissionShare = plusCommissionShare.multiply(fenchengPercent).multiply(new BigDecimal(tempReturnRate)).multiply(new BigDecimal("0.001"));

        //根据模板生成新的淘口令
        String content = template_simple.replace("$finalPrice", decimal.format(lowestPrice))
                .replace("$returnPrice", decimal.format(commissionShare) + "%")
                .replace("$returnPlusPrice", decimal.format(plusCommissionShare) + "%")
                .replaceAll("\\$url", link)
                .replace("$title", skuName)
                .replace("$jcmd", jCommand == null ? "" : jCommand);

        return new JDLinkDO(content, skuId);
    }

    /**
     * 链接转链接
     * @param materialId
     * @param positionId
     * @param subUnionId
     * @return
     */
    public JSONObject parseLink(String materialId, Long positionId, String subUnionId) {
        //基础参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", DdxConfig.ddxApiKey);
        params.put("materialId", materialId);
        params.put("unionId", DdxConfig.JD_UNIONID);
        params.put("autoSearch", true);

        //关于ID的参数
        if (positionId != null) {
            params.put("positionId", positionId);
        }
        if (!EmptyUtils.isEmpty(subUnionId)) {
            params.put("subUnionId", subUnionId);
        }

        //转链类型，1：长链， 2 ：短链 ，3： 长链+短链，默认短链
        params.put("chainType", 3);

        String result = prettyHttpService.get(DdxConfig.JD_BY_UNIONID_PROMOTION_URL, params);
        return JSON.parseObject(result);
    }

    /**
     * 转链后存储信息
     */
    @Data
    public static class JDLinkDO {
        private String linkInfo;
        private Long itemId;

        public JDLinkDO(String linkInfo, Long itemId) {
            this.linkInfo = linkInfo;
            this.itemId = itemId;
        }

        public String getLinkInfo() {
            return linkInfo;
        }

        public Long getItemId() {
            return itemId;
        }

        @Override
        public String toString() {
            return "JDLinkDO{" +
                    "linkInfo='" + linkInfo + '\'' +
                    ", itemId='" + itemId + '\'' +
                    '}';
        }
    }
}
