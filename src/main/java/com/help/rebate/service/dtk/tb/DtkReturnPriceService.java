package com.help.rebate.service.dtk.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.service.V2TaobaoCommissionRatioInfoService;
import com.help.rebate.service.dtk.tb.DtkItemConverter;
import com.help.rebate.service.dtk.tb.DtkReturnPriceService;
import com.help.rebate.service.exception.ConvertException;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.PropertyValueResolver;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author hokxi_gyl
 * * @date 2022/3/6
 */
@Service
public class DtkReturnPriceService {

    private static final Logger logger = LoggerFactory.getLogger(DtkReturnPriceService.class);

    /**
     * 转链服务
     */
    @Resource
    private DtkItemConverter dtkItemConverter;

    @Resource
    private V2TaobaoCommissionRatioInfoService v2TaobaoCommissionRatioInfoService;

    /**
     * 金额格式化
     */
    private static final DecimalFormat decimal = new DecimalFormat("##.##");

    /**
     * 生成的口令的模板
     */
    private static final String template_simple = "$pwd\n" +
            "【券后价】$finalPrice元\n" +
            "【预计返】$returnPrice元";

    /**
     * 生成的口令的模板
     */
    private static final String template_simple_with_title = "$title\n" +
            "【券后价】$finalPrice元\n" +
            "【优惠券】$conponInfo\n" +
            "【预计返】$returnPrice元\n" +
            "-------------\n" +
            "$pwd";

    /**
     * 生成淘口令转链
     * @param tkl
     * @param relationId
     * @param specialId
     * @param externalId
     * @return
     */
    public DtkReturnPriceService.TklDO generateReturnPriceInfo(String tkl, String relationId, String specialId, String externalId){
        String pubSite = "mm_120037479_18710025_65896653";
        return generateReturnPriceInfo(tkl, relationId, specialId, externalId, pubSite);
    }


    /**
     * 生成淘口令转链
     * @param tkl
     * @param relationId
     * @param specialId
     * @param externalId
     * @param pubSite
     * @return
     */
    public DtkReturnPriceService.TklDO generateReturnPriceInfo(String tkl, String relationId, String specialId, String externalId,
                                                               String pubSite){
        JSONObject jsonObject = null;
        try{
            //解析淘口令
            jsonObject = dtkItemConverter.getPrivilegeTkl(tkl, relationId, specialId, externalId, pubSite);
            String goodTitle = PropertyValueResolver.getProperty(jsonObject, "data.title");
            String longCouponTpwd = PropertyValueResolver.getProperty(jsonObject, "data.longTpwd");
            String maxCommissionRate = PropertyValueResolver.getProperty(jsonObject, "data.maxCommissionRate", true);

            //百分位的数，如35，表示返利35%
            if (maxCommissionRate == null || maxCommissionRate.equals("null") || maxCommissionRate.trim().length()==0) {
                maxCommissionRate = PropertyValueResolver.getProperty(jsonObject, "data.minCommissionRate");
            }
            if (maxCommissionRate == null || maxCommissionRate.equals("null") || maxCommissionRate.trim().length()==0) {
                logger.error("解析淘口令的返利比例失败:{}", tkl);
                throw new RuntimeException("无返利信息");
            }

            String itemId = PropertyValueResolver.getProperty(jsonObject, "data.itemId") + "";
            String coupon = PropertyValueResolver.getProperty(jsonObject, "data.couponInfo") + "";
            String qhFinalPrice = PropertyValueResolver.getProperty(jsonObject, "data.actualPrice") + "";

            //计算动态返利比例 - 千分位的比例
            int dynamicCommissionRate = v2TaobaoCommissionRatioInfoService.queryDynamicCommissionRatio(maxCommissionRate);

            //计算预计返利
            BigDecimal returnPrice = new BigDecimal(maxCommissionRate)
                    .multiply(new BigDecimal(dynamicCommissionRate))
                    .multiply(new BigDecimal(qhFinalPrice))
                    //百分位的返利比例 /100，千分位的本平台返利抽佣后的比例 / 1000
                    .multiply(new BigDecimal("0.00001"));

            //兼容ios14及其以上 - 这里与订单侠不同，暂时没有可用字段用于判断
            /*String newCouponTpwd = longCouponTpwd;
            if (EmptyUtils.isEmpty(coupon)) {
                String tpwd = PropertyValueResolver.getProperty(jsonObject, "data.tpwd");
                String longItemTpwd = PropertyValueResolver.getProperty(jsonObject, "data.shortUrl");
                newCouponTpwd = tpwd + " " + longItemTpwd;
            }*/

            //根据模板生成新的淘口令
            String content = template_simple_with_title
                    .replace("$title", goodTitle)
                    .replace("$finalPrice", qhFinalPrice)
                    .replace("$conponInfo", EmptyUtils.isEmpty(coupon) ? "无" : coupon)
                    .replace("$returnPrice", decimal.format(returnPrice))
                    .replace("$pwd", longCouponTpwd);

            return new DtkReturnPriceService.TklDO(content, itemId);
        }catch(Throwable e){
            logger.warn("[DdxReturnPriceService] 淘宝转链失败，message:{}, 返回的内容:{}", e.getMessage(), JSON.toJSONString(jsonObject));

            if (jsonObject != null) {
                try{
                    String itemId = PropertyValueResolver.getProperty(jsonObject, "data.itemId") + "";
                    String tpwd = PropertyValueResolver.getProperty(jsonObject, "data.tpwd");
                    return new DtkReturnPriceService.TklDO(tpwd, itemId);
                }
                catch(Exception ex) {
                    logger.error("提取商品属性失败，导致转链接失败", ex);
                    throw new ConvertException("此商品转链失败 - " + e.getMessage());
                }
            }

            throw new ConvertException("此商品转链失败 - " + e.getMessage());
        }
    }

    /**
     * 转链后存储信息
     */
    @Data
    public static class TklDO {
        private String tkl;
        private String itemId;

        public TklDO(String tkl, String itemId) {
            this.tkl = tkl;
            this.itemId = itemId;
        }

        @Override
        public String toString() {
            return "TklDO{" +
                    "tkl='" + tkl + '\'' +
                    ", itemId='" + itemId + '\'' +
                    '}';
        }
    }
}
