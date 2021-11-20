package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.UserInfos;
import com.help.rebate.service.TklConvertService;
import com.help.rebate.service.UserInfosService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 淘口令转链接接口
 * @author zfcisbest
 * @date 21/11/14
 */
@Api("淘口令转链")
@RestController
@RequestMapping("/tbk/tkl")
public class TklConvertController {
    private static final Logger logger = LoggerFactory.getLogger(TklConvertController.class);

    /**
     * 口令转链服务
     */
    @Autowired
    private TklConvertService tklConvertService;

    /**
     * 转链接
     * @param tkl
     * @param openId
     * @param externalId
     * @param tklType
     * @param dataFrom
     * @return
     */
    @ApiOperation("转链接")
    @RequestMapping("/convert")
    public SafeServiceResponse convert(@ApiParam(name = "tkl", value = "原始淘口令") @RequestParam String tkl,
                                       @ApiParam(name = "openId", value = "微信的OpenId") @RequestParam(required = false) String openId,
                                       @ApiParam(name = "externalId", value = "淘宝联盟外部ID") @RequestParam(required = false) String externalId,
                                       @ApiParam(name = "tklType", value = "淘口令类型 渠道(relation)、会员(special)、虚拟(virtual)、无(none)，默认为virtual") @RequestParam(required = false) String tklType,
                                       @ApiParam(name = "dataFrom", value = "口令的平台来源 tb、jd、pdd、wph（唯品会）、mt（美团）、elem（饿了么）") @RequestParam(required = false) String dataFrom) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            Checks.isNotEmpty(tkl, "淘口令不能为空");
            Checks.isTrue(!EmptyUtils.isEmpty(openId) && !EmptyUtils.isEmpty(externalId), "openId和externalId不能同时为空");

            //为空，默认为淘宝
            if (EmptyUtils.isEmpty(tklType)) {
                tklType = "virtual";
            }

            //为空就是淘宝
            if (EmptyUtils.isEmpty(dataFrom)) {
                dataFrom = "tb";
            }

            //转换
            String newTkl = tklConvertService.convert(tkl, openId, externalId, tklType, dataFrom);

            //返回
            return SafeServiceResponse.success(newTkl);
        }catch(Exception e){
            logger.error("fail to convert[/tbk/tkl/convert]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
