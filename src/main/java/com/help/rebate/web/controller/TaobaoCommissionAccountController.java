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
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
            logger.error("fail to execute[/tbk/queryCommission]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("根据OpenId，触发提现10元")
    @RequestMapping("/triggerWithdrawal")
    public SafeServiceResponse<String> triggerWithdrawal(@ApiParam(name = "openId", value = "微信openId") @RequestParam String openId) {
        try{
            SafeServiceResponse.startBiz();

            //插入
            v2TaobaoCommissionAccountService.triggerWithdrawal(openId, "1000");

            //返回
            return SafeServiceResponse.success("触发提现成功");
        }catch(Exception e){
            logger.error("fail to execute[/tbk/triggerWithdrawal]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("根据订单更新时间，计算该范围的可转为结算的订单")
    @RequestMapping("/computeOrderDetailToAccount")
    public SafeServiceResponse<String> computeOrderDetailToAccount(
            @ApiParam(name = "orderStartModifiedTime", value = "起始的订单状态更新时间") @RequestParam String orderStartModifiedTime,
            @ApiParam(name = "minuteStep", value = "向后查询多长时间的数据") @RequestParam Long minuteStep) {
        try{
            SafeServiceResponse.startBiz();

            //插入
            List<String> tradeParentIds = v2TaobaoCommissionAccountService.computeOrderDetailToAccount(orderStartModifiedTime, minuteStep);

            //返回
            return SafeServiceResponse.success("转结算成功: " + tradeParentIds.stream().collect(Collectors.joining(",")));
        }catch(Exception e){
            logger.error("fail to execute[/tbk/computeOrderDetailToAccount]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("设置银行卡总余额(内存)")
    @RequestMapping("/setBankTotalAccount")
    public SafeServiceResponse<String> setBankTotalAccount(
            @ApiParam(name = "totalAccount", value = "银行卡总余额") @RequestParam String totalAccount) {
        try{
            SafeServiceResponse.startBiz();

            //插入
            v2TaobaoCommissionAccountService.setBankTotalAccount(totalAccount);

            //返回
            return SafeServiceResponse.success("设置银行卡总余额成功");
        }catch(Exception e){
            logger.error("fail to execute[/tbk/commission/setBankTotalAccount]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("获取银行卡总余额(内存)")
    @RequestMapping("/getBankTotalAccount")
    public SafeServiceResponse<String> getBankTotalAccount() {
        try{
            SafeServiceResponse.startBiz();

            //插入
            BigDecimal bankTotalAccount = v2TaobaoCommissionAccountService.getBankTotalAccount();

            //返回
            return SafeServiceResponse.success("银行卡总余额: " + bankTotalAccount.doubleValue());
        }catch(Exception e){
            logger.error("fail to execute[/tbk/commission/getBankTotalAccount]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
