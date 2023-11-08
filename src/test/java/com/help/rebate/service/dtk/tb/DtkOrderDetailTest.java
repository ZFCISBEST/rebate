package com.help.rebate.service.dtk.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

    @Test
    void comLinkTest() throws Exception {
        //1、获取商品详情
        //String itemLink = "https://detail.tmall.com/item.htm?id=587584692337";
        //String itemLink = "【淘宝】https://m.tb.cn/h.58YCFYr?tk=iZUkWdm6Fqi CZ0001 「【进口】安佳金装高钙儿童牛奶3.6g蛋白质草饲奶源0蔗糖190ml*27 1件装」\n" + "点击链接直接打开 或者 淘宝搜索直接打开";
        //String itemLink = "【淘宝】https://m.tb.cn/h.5j1hJot?tk=M5rNWdnBIKf CZ3457 「【限时3件9折】致知 采莲子 小香风外套女2023秋新款休闲高级澳毛」\n" + "点击链接直接打开 或者 淘宝搜索直接打开";
        String itemLink = "16:06【淘宝】https://m.tb.cn/h.58QQm4s?tk=q68LWdnBIKy CZ3457 「【自营】皇家美素佳儿荷兰进口婴儿配方奶粉3段(1-3岁) 800g*6罐」\n" + "点击链接直接打开 或者 淘宝搜索直接打开";
        JSONObject orderDetailResponse = getOrderDetail(itemLink);

        //2、提取商品title
        JSONObject orderDetail = orderDetailResponse.getObject("data", JSONObject.class);
        JSONObject originInfo = orderDetail.getObject("originInfo", JSONObject.class);
        String itemTitle = originInfo.getString("title");
        String goodsId = orderDetail.getString("goodsId");
        String sellerId = orderDetail.getString("sellerId");

        //3、模糊搜索
        JSONObject fuzzyItemObject = getFuzzyItemList(itemTitle.replaceAll("[【】]", " "), 1, 50);
        JSONArray fuzzyItemList = fuzzyItemObject.getObject("data", JSONObject.class).getJSONArray("list");

        //4、匹配新商品ID
        String targetGoodsId = null;
        for (int i = 0; i < fuzzyItemList.size(); i++) {
            JSONObject jsonObject = fuzzyItemList.getJSONObject(i);

            String tempItemTitle = jsonObject.getString("title");
            //这是联盟新ID
            String tempGoodsId = jsonObject.getString("goodsId");
            String tempSellerId = jsonObject.getString("sellerId");

            if (tempItemTitle.equals(itemTitle) && tempSellerId.equals(sellerId)) {
                targetGoodsId = tempGoodsId;
                break;
            }
        }

        //5、根据新商品ID，转为淘口令
        JSONObject tklResponse = getTkl(targetGoodsId);
        JSONObject tkl = tklResponse.getObject("data", JSONObject.class);
        System.out.println(JSON.toJSONString(tkl, true));

        //6、输出结果
        System.out.println("原始链接:" + itemLink);
        System.out.println("新淘口令:" + tkl.getString("tpwd"));
    }

    JSONObject getOrderDetail(String content) throws Exception {
        String url = "https://openapi.dataoke.com/api/tb-service/parse-taokouling";

        //dtk - my app
        String appKey = "6195f0595b3a8";
        String appSecret = "d70d242a16ee75c0e9509f1ea58add2a";

        TreeMap<String, Object> paraMap = new TreeMap<>();
        paraMap.put("version", "v1.0.0");
        paraMap.put("appKey", appKey);

        paraMap.put("content", content);

        String data = ApiClient.sendReqNew(url, appSecret, paraMap);
        return JSON.parseObject(data);
    }

    JSONObject getFuzzyItemList(String title, int pageNo, int pageSize) throws Exception {
        //联盟搜索
        //String url = "https://openapi.dataoke.com/api/tb-service/get-tb-service";
        //超级搜索
        String url = "https://openapi.dataoke.com/api/goods/list-super-goods";

        //dtk - my app
        String appKey = "6195f0595b3a8";
        String appSecret = "d70d242a16ee75c0e9509f1ea58add2a";

        TreeMap<String, Object> paraMap = new TreeMap<>();
        paraMap.put("version", "v1.3.0");
        paraMap.put("appKey", appKey);

        paraMap.put("keyWords", title);
        paraMap.put("pageId", pageNo + "");
        paraMap.put("pageSize", pageSize + "");
        paraMap.put("type", "2");

        String data = ApiClient.sendReqNew(url, appSecret, paraMap);
        return JSON.parseObject(data);
    }

    JSONObject getTkl(String goodsId) throws Exception {
        //高效转链
        String url = "https://openapi.dataoke.com/api/tb-service/get-privilege-link";

        //dtk - my app
        String appKey = "6195f0595b3a8";
        String appSecret = "d70d242a16ee75c0e9509f1ea58add2a";

        TreeMap<String, Object> paraMap = new TreeMap<>();
        paraMap.put("version", "v1.3.1");
        paraMap.put("appKey", appKey);

        paraMap.put("goodsId", goodsId);

        String data = ApiClient.sendReqNew(url, appSecret, paraMap);
        return JSON.parseObject(data);
    }

}
