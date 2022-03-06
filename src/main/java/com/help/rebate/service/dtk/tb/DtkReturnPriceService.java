package com.help.rebate.service.dtk.tb;

import com.alibaba.fastjson.JSONObject;
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
    @Autowired
    private DtkItemConverter dtkItemConverter;

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
     * 生成淘口令转链
     * @param tkl
     * @param relationId
     * @param specialId
     * @param externalId
     * @return
     */
    public DtkReturnPriceService.TklDO generateReturnPriceInfo(String tkl, String relationId, String specialId, String externalId,
                                                               Double tempReturnRate){
        String pubSite = "mm_120037479_18710025_65896653";
        return generateReturnPriceInfo(tkl, relationId, specialId, externalId, pubSite, tempReturnRate);
    }


    /**
     * 生成淘口令转链
     * @param tkl
     * @param relationId
     * @param specialId
     * @param externalId
     * @param pubSite
     * @param tempReturnRate
     * @return
     */
    public DtkReturnPriceService.TklDO generateReturnPriceInfo(String tkl, String relationId, String specialId, String externalId,
                                                               String pubSite, Double tempReturnRate){
        try{
            if (tempReturnRate == null || tempReturnRate <= 0) {
                tempReturnRate = 0.8;
            }

            //解析淘口令
            JSONObject jsonObject = dtkItemConverter.getPrivilegeTkl(tkl, relationId, specialId, externalId, pubSite);
            String longCouponTpwd = PropertyValueResolver.getProperty(jsonObject, "data.longTpwd");
            String maxCommissionRate = PropertyValueResolver.getProperty(jsonObject, "data.maxCommissionRate", true);
            if (maxCommissionRate == null || maxCommissionRate.equals("null")) {
                maxCommissionRate = PropertyValueResolver.getProperty(jsonObject, "data.minCommissionRate");
            }

            String itemId = PropertyValueResolver.getProperty(jsonObject, "data.itemId") + "";
            String coupon = PropertyValueResolver.getProperty(jsonObject, "data.couponInfo") + "";
            String qhFinalPrice = PropertyValueResolver.getProperty(jsonObject, "data.actualPrice") + "";

            //计算预计返利
            double returnPrice = Double.parseDouble(maxCommissionRate) * tempReturnRate * Double.parseDouble(qhFinalPrice) / 100;

            //兼容ios14及其以上 - 这里与订单侠不同，暂时没有可用字段用于判断
            String newCouponTpwd = longCouponTpwd;
            if (EmptyUtils.isEmpty(coupon)) {
                String longItemTpwd = PropertyValueResolver.getProperty(jsonObject, "data.shortUrl");
                newCouponTpwd = longItemTpwd;
            }

            //根据模板生成新的淘口令
            String content = template_simple.replace("$finalPrice", qhFinalPrice)
                    .replace("$returnPrice", decimal.format(returnPrice))
                    .replace("$pwd", newCouponTpwd);

            return new DtkReturnPriceService.TklDO(content, itemId);
        }catch(Throwable e){
            logger.warn("[DdxReturnPriceService] 淘宝转链失败，message:{}, toString:{}", e.getMessage(), e.toString());
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
