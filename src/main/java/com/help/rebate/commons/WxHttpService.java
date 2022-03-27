package com.help.rebate.commons;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.utils.DecryptMD5;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

import static com.help.rebate.service.wx.WXPayConstants.USER_AGENT;

public class WxHttpService {

    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            System.out.println("连接超时：" + ce );
        } catch (Exception e) {
            System.out.println("https请求异常：" + e );
        }
        return jsonObject;
    }


    public static JSONObject httpsRequestPay(String requestUrl,  String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            //TrustManager[] tm = { new MyX509TrustManager() };
            // 实例化密钥库 & 初始化密钥工厂
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            //证书位置自己定义
            FileInputStream instream = new FileInputStream(new File("/usr/local/src/apiclient_cert.p12"));
            try {
                //填写证书密码，默认为商户号 1617446954
                keyStore.load(instream, DecryptMD5.IdOpen(DdxConfig.ID).toCharArray());
            } finally {
                instream.close();
            }

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore,  DecryptMD5.IdOpen(DdxConfig.ID).toCharArray());

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );

            HttpClient httpClient = HttpClientBuilder.create()
                    .setConnectionManager(connManager)
                    .build();

            HttpPost httpPost = new HttpPost(requestUrl);

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000).build();
            httpPost.setConfig(requestConfig);

            StringEntity postEntity = new StringEntity(outputStr, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.addHeader("User-Agent", USER_AGENT + " " + "1617446954");
            httpPost.setEntity(postEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            return JSON.parseObject(EntityUtils.toString(httpEntity, "UTF-8"));

        } catch (ConnectException ce) {
            System.out.println("连接超时：" + ce );
        } catch (Exception e) {
            System.out.println("https请求异常：" + e );
        }
        return jsonObject;
    }
}
