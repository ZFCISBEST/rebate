package com.help.rebate.service.dtk.tb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DtkReturnPriceServiceTest {
    @Autowired
    private DtkReturnPriceService dtkReturnPriceService;

    @Test
    void test_generateReturnPriceInfo() {
        String tkl = "【淘宝】https://m.tb.cn/h.fmA2OfL?tk=Vhm72fOLbQo「植护大包抽纸整箱卫生纸巾大号餐巾纸家用实惠装婴儿面巾纸抽批发」\n" +
                "点击链接直接打开";
        DtkReturnPriceService.TklDO tklDO = dtkReturnPriceService.generateReturnPriceInfo(tkl, null, null, null, "mm_120037479_18710025_65896653", 1.0);
        System.out.println(JSON.toJSONString(tklDO, true));
    }
}