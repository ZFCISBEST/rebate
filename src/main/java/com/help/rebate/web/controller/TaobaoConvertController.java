package com.help.rebate.web.controller;

import com.help.rebate.service.V2TaobaoTklConvertService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.web.response.SafeServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 淘口令转链接接口
 * @author zfcisbest
 * @date 21/11/14
 */
@Api("淘口令转链")
@RestController
@RequestMapping("/tbk/tkl")
public class TaobaoConvertController {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoConvertController.class);

    /**
     * 口令转链服务
     */
    @Autowired
    private V2TaobaoTklConvertService v2TaobaoTklConvertService;

    /**
     * 转链接
     * @param tkl
     * @param openId
     * @param pubSiteType
     * @return
     */
    @ApiOperation("转链接")
    @RequestMapping("/convert")
    public SafeServiceResponse convert(@ApiParam(name = "openId", value = "微信的OpenId") @RequestParam String openId,
                                       @ApiParam(name = "tkl", value = "原始淘口令") @RequestParam String tkl,
                                       @ApiParam(name = "pubSiteType", value = "淘口令类型 渠道(relation)、虚拟(virtual)，默认为virtual") @RequestParam(required = false) String pubSiteType) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            Checks.isNotEmpty(tkl, "淘口令不能为空");
            Checks.isTrue(!EmptyUtils.isEmpty(openId), "openId不能为空");

            //为空，默认为淘宝
            if (EmptyUtils.isEmpty(pubSiteType)) {
                pubSiteType = "virtual";
            }

            //转换
            String newTkl = v2TaobaoTklConvertService.convert(tkl, openId, pubSiteType);

            //返回
            return SafeServiceResponse.success(newTkl);
        }catch(Exception e){
            logger.error("fail to convert[/tbk/tkl/convert]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
