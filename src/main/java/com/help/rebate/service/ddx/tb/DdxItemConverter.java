package com.help.rebate.service.ddx.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DdxConfig;
import com.help.rebate.commons.PrettyHttpService;
import com.help.rebate.service.dtk.tb.DtkItemConverter;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单侠商品转链接
 * 参考文档 - https://www.dingdanxia.com/doc/3/8
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
public class DdxItemConverter {
    private static final Logger logger = LoggerFactory.getLogger(DdxItemConverter.class);

    /**
     * http服务
     */
    @Autowired
    private PrettyHttpService prettyHttpService;

    /**
     * 淘口令转链接
     * @param tkl
     * @param relationId
     * @param specialId
     * @param externalId
     * @param pubSite
     * @return
     */
    public JSONObject parseTkl(String tkl, String relationId, String specialId, String externalId, String pubSite) {
        //基础参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", DdxConfig.ddxApiKey);
        params.put("tkl", tkl);
        params.put("tpwd", "true");
        params.put("itemInfo", true);

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

        String result = prettyHttpService.get(DdxConfig.TB_TKL_PRIVILEGES_URL, params);
        return JSON.parseObject(result);
    }
}
