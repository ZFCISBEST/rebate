package com.help.rebate.service.dtk.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.utils.TimeUtil;
import com.help.rebate.utils.dtk.ApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 * @author hokxi_gyl
 * * @date 2022/3/12
 */
@SpringBootTest
class DtkOrderDetailTest {

    @Autowired
    private DtkOrderDetail dtkOrderDetail;

    @Test
    void test_getOrder() throws ParseException {
        String date1 = "2022-03-11 20:00:00";
        String date2 = "2022-03-11 23:00:00";
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date3=format.parse(date1);
        Date date4=format.parse(date2);
//        DtkOrderDetail dtkOrderDetail = new DtkOrderDetail();
        JSONObject json = dtkOrderDetail.getOrderDetail(date3,date4,4,1,null,1,50);
        System.out.println(json);
    }

    @Test
    void test_syncOrder() throws ParseException {
        String url = "https://openapi.dataoke.com/api/tb-service/get-order-details";
        String date1 = "2022-03-10 21:00:00";
        String date2 = "2022-03-10 23:59:59";

        String dtkAppkey = "61961877e8403";
        String dtkAppsecret = "30511c332ab42746a0fd0f43b948975f";

        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("version", "v1.0.0");
        paraMap.put("appKey", dtkAppkey);

        paraMap.put("startTime", date1);
        paraMap.put("endTime", date2);
        paraMap.put("queryType", "4");
        paraMap.put("orderScene", "1");

        paraMap.put("pageNo", "1");
        paraMap.put("pageSize", "20");

        String data = ApiClient.sendReqNew(url, dtkAppsecret, paraMap);
        System.out.println(JSON.toJSONString(JSON.parseObject(data), true));
    }
}
