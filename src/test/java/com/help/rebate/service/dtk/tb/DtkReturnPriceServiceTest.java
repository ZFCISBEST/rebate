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
        tkl = "1復制( CZ3457 WCRJWdrj9qo)打开tao寳抢购/";
        //tkl = "16:06【淘宝】https://m.tb.cn/h.58QQm4s?tk=q68LWdnBIKy CZ3457 「【自营】皇家美素佳儿荷兰进口婴儿配方奶粉3段(1-3岁) 800g*6罐」\n" + "点击链接直接打开 或者 淘宝搜索直接打开";
        DtkReturnPriceService.TklDO tklDO = dtkReturnPriceService.generateReturnPriceInfo(tkl, null, null, null, "mm_120037479_18710025_65896653");
        System.out.println(JSON.toJSONString(tklDO, true));
    }
}