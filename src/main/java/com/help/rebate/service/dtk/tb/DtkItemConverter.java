package com.help.rebate.service.dtk.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DtkConfig;
import com.help.rebate.commons.PrettyHttpService;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.dtk.SignMD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 大淘客商品转链接
 * 参考文档 - https://www.dingdanxia.com/doc/3/8
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class DtkItemConverter {
    private static final Logger logger = LoggerFactory.getLogger(DtkItemConverter.class);

    /**
     * http服务
     */
    @Autowired
    private PrettyHttpService prettyHttpService;

    /**
     * 根据淘口令，转链接获取返利淘口令
     * @param tkl
     * @param relationId
     * @param specialId
     * @param externalId
     * @param pubSite
     * @return
     */
    public JSONObject getPrivilegeTkl(String tkl, String relationId, String specialId, String externalId, String pubSite){
        String goodsId = parseTkl(tkl).getJSONObject("data").getString("goodsId");
        //基础参数
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("appKey", DtkConfig.dtkAppkey);
        params.put("goodsId", goodsId);
        params.put("version", "v1.3.1");

        //推广位
        params.put("pid", pubSite);

        //关于ID的参数
        if (!EmptyUtils.isEmpty(relationId)) {
            params.put("relation_id", relationId);
        }
        if (!EmptyUtils.isEmpty(specialId)) {
            params.put("special_id", specialId);
        }
        if (!EmptyUtils.isEmpty(externalId)) {
            params.put("external_id", externalId);
        }

        params.put("sign", SignMD5Util.getSignStr(params,DtkConfig.dtkAppsecret));
        String result = prettyHttpService.get(DtkConfig.DTK_GET_PRIVILEGE_TKL, params);
        return JSON.parseObject(String.valueOf(result));
    }

    /**
     * 根据淘口令，获取商品信息，包含商品ID
     * @param tkl
     * @return
     */
    public JSONObject parseTkl(String tkl) {
        //基础参数
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("appKey", DtkConfig.dtkAppkey);
        params.put("content", tkl);
        params.put("version", "v1.0.0");
        params.put("sign", SignMD5Util.getSignStr(params,DtkConfig.dtkAppsecret));

        String result = prettyHttpService.get(DtkConfig.DTK_PARSE_CONTENT, params);
        return JSON.parseObject(String.valueOf(result));
    }

}
