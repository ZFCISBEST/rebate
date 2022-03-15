package com.help.rebate.utils.dtk;

import com.help.rebate.utils.EmptyUtils;

import java.net.URISyntaxException;
import java.util.Map;

/**
 * zfc
 */
public class ApiClient {
    /**
     * 发送get请求
     * @param url
     * @param secret
     * @param paramMap
     * @return
     */
    public static String sendReqNew(String url, String secret, Map<String, Object> paramMap) {
        if (EmptyUtils.isEmpty(url)) {
            throw new IllegalArgumentException("请求地址不能为空");
        }

        if (EmptyUtils.isEmpty(secret)) {
            throw new IllegalArgumentException("secret不能为空");
        }

        if (null == paramMap || paramMap.size() < 1) {
            throw new IllegalArgumentException("参数不能为空");
        }

        String timer = String.valueOf(System.currentTimeMillis());
        paramMap.put("timer", timer);
        paramMap.put("nonce", "110505");
        paramMap.put("signRan", SignMD5Util.getSignStrNew(paramMap, secret));

        String data = "";
        try{
            data = HttpUtil.httpGetRequest(url, paramMap);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return data;
    }

}
