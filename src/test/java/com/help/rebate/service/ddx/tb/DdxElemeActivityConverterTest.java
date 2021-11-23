package com.help.rebate.service.ddx.tb;

import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class DdxElemeActivityConverterTest {
    @Resource
    private DdxElemeActivityConverter ddxElemeActivityConverter;


    @Test
    void generateReturnPriceInfo() {
        String keyword = "饿了么外卖";
        String link = ddxElemeActivityConverter.generateReturnPriceInfo(keyword);
        System.out.println(link);
    }
}