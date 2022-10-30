package com.help.rebate.service.dtk.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.service.ddx.tb.DdxItemConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DtkItemConverterTest {
    @Autowired
    private DtkItemConverter dtkItemConverter;

    @Autowired
    private DdxItemConverter ddxItemConverter;

    @Test
    void test_getPrivilegeTkl() {
        String tkl = "【淘宝】https://m.tb.cn/h.UezyqYW?tk=U9O7daf5RMT CZ3457 「【旗舰店】Orijen渴望美国狗粮中小型犬无谷鸡肉幼犬粮11.4kg」\n" +
                "点击链接直接打开 或者 淘宝搜索直接打开";
        JSONObject a =dtkItemConverter.getPrivilegeTkl(tkl,null,null,null,"mm_120037479_18710025_65896653");
        System.out.println(JSON.toJSONString(a, true));
    }

    @Test
    void test_ddx_converter() {
        String tkl = "【淘宝】https://m.tb.cn/h.fmA2OfL?tk=Vhm72fOLbQo「植护大包抽纸整箱卫生纸巾大号餐巾纸家用实惠装婴儿面巾纸抽批发」\n" +
                "点击链接直接打开";
        JSONObject a =ddxItemConverter.parseTkl(tkl,null,null,null,"mm_120037479_18710025_65896653");
        System.out.println(JSON.toJSONString(a, true));
    }

    @Test
    void test_getTwd2Twd() {
        String tkl = "0英氏婴儿洗衣液宝宝专用儿童清洗液新生婴幼儿内衣裤清洁剂bb皂液【包邮】\n" +
                "【推荐理由】30天热卖3万+, 回头客超1千, 赠送运费险退货无忧!\n" +
                "【券后价】 49 元\n" +
                "【优惠券】 10 元\n" +
                //"【下单链接】https://m.tb.cn/h.U0Jcu8a\n" +
                "\n" +
                "2覆\uD83D\uDC4BZHI7$sMTt2EYX2CX$:// ZH9114,打開/";
        JSONObject a =dtkItemConverter.parseTkl(tkl);

        System.out.println(JSON.toJSONString(a, true));
    }
}