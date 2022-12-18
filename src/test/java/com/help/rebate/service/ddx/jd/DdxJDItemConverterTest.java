package com.help.rebate.service.ddx.jd;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DdxJDItemConverterTest {

    @Resource
    private DdxJDItemConverter ddxJDItemConverter;

    @Test
    void generateReturnPriceInfo() {
        //String materialId = "https://u.jd.com/2KRkeJt";
        String materialId = "https://item.jd.com/10037910217458.html";
        Long positionId = 88L;
        String subUnionId = "wx_zhoufachao";
        int tempReturnRate = 900;

        //转链接
        DdxJDItemConverter.JDLinkDO jdLinkDO = ddxJDItemConverter.generateReturnPriceInfo(materialId, positionId, subUnionId, tempReturnRate);
        System.out.println(jdLinkDO.getLinkInfo());
    }
}