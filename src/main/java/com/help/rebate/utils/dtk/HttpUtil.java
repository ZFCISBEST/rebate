package com.help.rebate.utils.dtk;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HttpUtil {
    private static PoolingHttpClientConnectionManager cm;
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private static void init() {
        if (cm == null) {
            cm = new PoolingHttpClientConnectionManager();
            //连接池最大连接数
            cm.setMaxTotal(2000);
            //每个路由最大连接数，默认值为2
            cm.setDefaultMaxPerRoute(cm.getMaxTotal());
        }
    }

    /**
     * 通过连接池获取HttpClient
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom()
                .setRetryHandler(new HttpRequestRetryHandler() {
                    @Override
                    public boolean retryRequest(IOException e, int executionCount, HttpContext httpContext) {
                        if (executionCount >= 1) {
                            return false;
                        }
                        return true;
                    }
                })
                .setConnectionManager(cm)
                .setConnectionManagerShared(true)
                .build();
    }

    /**
     * 执行get请求
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {
        String paramsStr = convertParamsForStr(params);
        url = url.contains("?") ? url : url + "?";

        HttpGet httpGet = new HttpGet(url + paramsStr);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(false)
                .build();
        httpGet.setConfig(config);

        httpGet.setHeader("Accept", "application/json");
        httpGet.addHeader("Accept-Encoding", "gzip");
        return getResult(httpGet);
    }

    /**
     * 执行并获取结果
     * @param reqeust
     * @return
     */
    private static String getResult(HttpRequestBase reqeust) {
        CloseableHttpClient httpClient = getHttpClient();
        String result = "";

        try{
            CloseableHttpResponse response = httpClient.execute(reqeust);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
            response.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 拼接参数
     * @param params
     * @return
     */
    private static String convertParamsForStr(Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }

        params = new TreeMap<>(params);
        StringBuilder sb = new StringBuilder();
        params.entrySet().stream().forEach(entry -> {
            sb.append(entry.getKey());
            sb.append("=");
            try{
                sb.append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb.append("&");
        });

        return sb.toString();
    }
}
