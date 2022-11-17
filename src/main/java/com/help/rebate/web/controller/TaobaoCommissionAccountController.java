package com.help.rebate.web.controller;

import com.help.rebate.service.V2TaobaoCommissionAccountService;
import com.help.rebate.service.V2TaobaoUserInfoService;
import com.help.rebate.vo.CommissionVO;
import com.help.rebate.web.response.SafeServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户返利账户信息操作类
 * @author zfcisbest
 * @date 21/11/14
 */
@Api("用户返利账户信息API")
@RestController
@RequestMapping("/tbk/commission")
public class TaobaoCommissionAccountController {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoCommissionAccountController.class);

    @Resource
    private V2TaobaoUserInfoService v2TaobaoUserInfoService;

    @Resource
    private V2TaobaoCommissionAccountService v2TaobaoCommissionAccountService;

    @ApiOperation("根据OpenId查询返利")
    @RequestMapping("/queryCommission")
    public SafeServiceResponse<CommissionVO> queryCommission(@ApiParam(name = "openId", value = "微信openId") @RequestParam String openId) {
        try{
            SafeServiceResponse.startBiz();

            //插入
            CommissionVO commissionVO = v2TaobaoCommissionAccountService.selectCommissionBy(openId);

            //返回
            return SafeServiceResponse.success(commissionVO);
        }catch(Exception e){
            logger.error("fail to create user[/tbk/queryCommission]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
