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
        String tkl = "9安慕斯儿童牙刷小孩软毛牙刷牙膏乳牙宝宝婴幼儿牙刷3-6-12岁以上\n" +
                "【推荐理由】30天热卖9千+, 回头客超1百, 满2件打7.5折, 赠送运费险退货无忧!\n" +
                "【券后价】 7.9 元\n" +
                "【优惠券】 12 元\n" +
                "\n" +
                "5fu\uD83D\uDE04質3$FbOzd8kUAFb$:// ZH9102,打開/";
        tkl = "8PRICH牛仔裤22年新款直筒高腰设计感女小众商务通勤裤子女\n" +
                "【推荐理由】满1件打9折, 赠送运费险退货无忧!\n" +
                "【折后价】 275.4 元\n" +
                "\n" +
                "4輹\uD83D\uDC4BZhi6$hHhhd8P3leL$:// HU7177,打開/";
        DtkReturnPriceService.TklDO tklDO = dtkReturnPriceService.generateReturnPriceInfo(tkl, null, null, null, "mm_120037479_18710025_65896653");
        System.out.println(JSON.toJSONString(tklDO, true));
    }
}