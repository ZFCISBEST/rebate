package com.help.rebate.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * http服务
 *
 * @author zfcisbest
 * @date 21/11/13
 */
@Service
public class PrettyHttpService {
    private static final Logger logger = LoggerFactory.getLogger(PrettyHttpService.class);

    /**
     * http服务
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * get请求
     * @param url
     * @param paramMap
     * @return
     */
    public String get(String url, Map<String, Object> paramMap) {
        //请求
        String param = convertUrlParam(paramMap);
        String newUrl = url + "?" + param;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(newUrl, String.class, paramMap);

        //判定是否正确
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if (statusCodeValue == 200) {
            return responseEntity.getBody();
        }

        //否则抛出异常
        throw new RuntimeException(responseEntity.getStatusCode().getReasonPhrase());
    }

    /**
     * post请求
     * @param url
     * @param paramMap
     * @return
     */
    public String post(String url, Map<String, Object> paramMap) {
        //请求
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);

        //判定是否正确
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if (statusCodeValue == 200) {
            return responseEntity.getBody();
        }

        //否则抛出异常
        throw new RuntimeException(responseEntity.getStatusCode().getReasonPhrase());
    }

    /**
     * 转换参数
     * @param paramMap
     * @return
     */
    private String convertUrlParam(Map<String, Object> paramMap) {
        List<String> params = new ArrayList<String>();
        for (Map.Entry<String, Object> keyValue : paramMap.entrySet()) {
            params.add(keyValue.getKey() + "=" + "{" + keyValue.getKey() + "}");
        }

        String param = params.stream().collect(Collectors.joining("&"));
        return param;
    }
}
