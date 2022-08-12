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
        String tkl = "78￥58he2tdmEMs￥ https://m.tb.cn/h.fBHZtDv  CZ3457 青蛙王子婴儿牛奶沐浴露洗发水二合一宝宝沐浴乳婴幼儿童洗发水";
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
}