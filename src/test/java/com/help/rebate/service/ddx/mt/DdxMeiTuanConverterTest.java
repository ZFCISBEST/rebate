package com.help.rebate.service.ddx.mt;

import com.help.rebate.service.ddx.tb.DdxElemeActivityConverter;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class DdxMeiTuanConverterTest {
    @Resource
    private DdxMeiTuanActivityConverter ddxMeiTuanActivityConverter;


    @Test
    void generateReturnPriceInfo() {
        String keyword = "美团外卖";
        String sid = "zhoufachao";
        String link = ddxMeiTuanActivityConverter.generateReturnPriceInfo(keyword, sid);
        System.out.println(link);
    }
}