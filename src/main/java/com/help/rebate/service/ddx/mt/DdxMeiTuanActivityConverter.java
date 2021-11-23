package com.help.rebate.service.ddx.mt;

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
 * 订单侠的美团转链
 * 参考文档 - https://www.dingdanxia.com/doc/221/173
 * @author zfcisbest
 * @date 21/11/20
 */
@Service
public class DdxMeiTuanActivityConverter {
    private static final Logger logger = LoggerFactory.getLogger(DdxMeiTuanActivityConverter.class);

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
     * 美团外卖/闪购/酒店/优选红包活动转链接口，支持自定义参数，跳转小程序
     *
     * 美团团购小程序ID：wxde8ac0a21135c07d
     *
     * 美团优选小程序ID：wx77af438b3505c00e
     * 接口地址： http://api.tbk.dingdanxia.com/waimai/meituan_generateLink
     * @param keyword
     * @return
     */
    public String generateReturnPriceInfo(String keyword, String sid) {
        //33-外卖 4-闪购 22-优选(不支持h5，仅支持app端唤起，小程序路径，小程序二维码) 23-优选1.99促销页 24-优选秒杀 29-优选新人活动页 31-外卖品质商家活动
        long actId = 33l;

        // 1-h5链接 2-deeplink(唤起)链接 3-中间页唤起链接 4-微信小程序唤起路径
        int linkType = 4;
        if (keyword.contains("外卖品质商家活动")) {
            actId = 31;
        } else if (keyword.contains("闪购")) {
            actId = 4;
        } else if (keyword.contains("优选秒杀")) {
            actId = 24;
        } else if (keyword.contains("优选新人")) {
            actId = 29;
        } else if (keyword.contains("优选h5")) {
            linkType = 4;
            actId = 23;
        } else if (keyword.contains("优选")) {
            linkType = 4;
            actId = 22;
        }

        //获取
        JSONObject jsonObject = parseLink(actId, linkType, sid);
        String wxMiniprogramPath = jsonObject.getString("data");

        //直接生成
        String content = "【小程序】<a href=\"http://www.qq.com\" data-miniprogram-appid=\"$chengxuId\" data-miniprogram-path=\"" + wxMiniprogramPath + "\">点击跳转小程序</a>";

        if (actId == 33 || actId == 31 || actId == 4) {
            content = content.replace("$chengxuId", "wxde8ac0a21135c07d");
        }
        else {
            content = content.replace("$chengxuId", "wx77af438b3505c00e");
        }

        return content;
    }

    /**
     * 链接转链接
     *
     * @param actId
     * @param linkType
     * @param sid
     * @return
     */
    public JSONObject parseLink(long actId, int linkType, String sid) {
        //基础参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", DdxConfig.ddxApiKey);
        params.put("actId", actId);
        params.put("sid", sid.toLowerCase());
        params.put("linkType", linkType);
        params.put("shortLink", 1);

        String result = prettyHttpService.get("http://api.tbk.dingdanxia.com/waimai/meituan_generateLink", params);
        return JSON.parseObject(result);
    }
}
