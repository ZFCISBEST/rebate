package com.help.rebate.service.ddx.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DdxConfig;
import com.help.rebate.commons.PrettyHttpService;
import com.help.rebate.utils.PropertyValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单侠的饿了么转链
 * 参考文档 - https://www.dingdanxia.com/apitest/122/173
 * @author zfcisbest
 * @date 21/11/20
 */
@Service
public class DdxElemeActivityConverter {
    private static final Logger logger = LoggerFactory.getLogger(DdxElemeActivityConverter.class);

    /**
     * 金额格式化
     */
    private static final DecimalFormat decimal = new DecimalFormat("##.##");

    /**
     * http服务
     */
    @Autowired
    private PrettyHttpService prettyHttpService;

    /**
     * 饿了么转链
     * 饿了么小程序 appid：wxece3a9a4c82f58c9
     * 1、饿了么外卖微信活动ID：20150318020002192（注意：仅支持微信小程序推广，h5链接无效）
     * 2、饿了么活动ID：20150318019998877  推广佣金6%起
     * 3、饿了么果蔬生鲜活动ID：1585018034441  推广佣金4%起
     * @param keyword
     * @return
     */
    public String generateReturnPriceInfo(String keyword) {

        String elemActivity = "20150318020002192";
        if (keyword.contains("外卖")) {
            elemActivity = "20150318020002192";
        } else if (keyword.contains("活动")) {
            elemActivity = "20150318019998877";
        } else if (keyword.contains("生鲜") || keyword.contains("果蔬")) {
            elemActivity = "1585018034441";
        }

        //获取
        JSONObject jsonObject = parseLink(elemActivity);
        JSONObject data = jsonObject.getJSONObject("data");

        //解析参数
        //短口令 -
        String shortClickUrl = PropertyValueResolver.getProperty(data, "short_click_url");
        String wxMiniprogramPath = PropertyValueResolver.getProperty(data, "wx_miniprogram_path");
        String longTpwd = PropertyValueResolver.getProperty(data, "long_tpwd", false);

        //直接生成
        StringBuilder sb = new StringBuilder();
        sb.append("【链接】").append(shortClickUrl).append("\n");
        if (longTpwd != null) {
            sb.append("【口令】").append(longTpwd).append("\n");
        }
        sb.append("【小程序】<a href=\"" + shortClickUrl + "\" data-miniprogram-appid=\"wxece3a9a4c82f58c9\" data-miniprogram-path=\"" + wxMiniprogramPath + "\">点击跳转小程序</a>");
        return sb.toString();
    }

    /**
     * 链接转链接
     * @param elemActivity
     * @return
     */
    public JSONObject parseLink(String elemActivity) {
        //基础参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", DdxConfig.ddxApiKey);
        params.put("activity_material_id", elemActivity);
        params.put("pid", "mm_120037479_18710025_65896653");
        params.put("tpwd", true);

        String result = prettyHttpService.get("http://api.tbk.dingdanxia.com/tbk/activityinfo", params);
        return JSON.parseObject(result);
    }
}
