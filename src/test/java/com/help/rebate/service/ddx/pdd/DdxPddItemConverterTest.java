package com.help.rebate.service.ddx.pdd;

import com.help.rebate.service.ddx.jd.DdxJDItemConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DdxPddItemConverterTest {

    @Resource
    private DdxPddItemConverter ddxPddItemConverter;

    @Test
    void generateReturnPriceInfo() {
        String url = "https://p.pinduoduo.com/fqVaKzTD";
        String customId = "wx_zhoufachao";
        Double tempReturnRate = 0.9;

        DdxPddItemConverter.PddLinkDO pddLinkDO = ddxPddItemConverter.generateReturnPriceInfo(url, customId, tempReturnRate);
        System.out.println(pddLinkDO.getLinkInfo());
    }
}