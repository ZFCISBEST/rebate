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
        String tkl = "7英氏婴儿洗衣液宝宝专用儿童清洗液新生婴幼儿内衣裤清洁剂bb皂液【包邮】\n" +
                "【推荐理由】30天热卖3万+, 回头客超1千, 赠送运费险退货无忧!\n" +
                "【券后价】 49 元\n" +
                "【优惠券】 10 元\n" +
                "\n" +
                "7輹\uD83D\uDC4BZhi8$p7kr2vC0jOX$:// CZ6135,打開/";
        DtkReturnPriceService.TklDO tklDO = dtkReturnPriceService.generateReturnPriceInfo(tkl, null, null, null, "mm_120037479_18710025_65896653");
        System.out.println(JSON.toJSONString(tklDO, true));
    }
}