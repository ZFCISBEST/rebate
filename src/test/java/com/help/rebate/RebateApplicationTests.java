package com.help.rebate;

import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.PrettyHttpService;
import com.help.rebate.service.dtk.tb.DtkItemConverter;
import com.help.rebate.service.dtk.tb.DtkReturnPriceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RebateApplicationTests {
//	@Autowired
//	private PrettyHttpService prettyHttpService;
//	@Resource
//	private DtkReturnPriceService dtkReturnPriceService;
	@Autowired
	private DtkItemConverter dtkItemConverter;
	@Test
	void contextLoads() {
//		DtkItemConverter dtkItemConverter =new DtkItemConverter();
		String tkl = "【淘宝】https://m.tb.cn/h.fmA2OfL?tk=Vhm72fOLbQo「植护大包抽纸整箱卫生纸巾大号餐巾纸家用实惠装婴儿面巾纸抽批发」\n" +
				"点击链接直接打开";
		JSONObject a =dtkItemConverter.getPrivilegeTkl(tkl,null,null,null,"mm_120037479_18710025_65896653");
		System.out.println(a);
//		DtkReturnPriceService.TklDO b =dtkReturnPriceService.generateReturnPriceInfo(tkl,null,null,null,"mm_120037479_18710025_65896653",null);
//		System.out.println(b);
	}

}
