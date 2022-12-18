package com.help.rebate.web.controller;

import com.help.rebate.service.V2TaobaoCommissionAccountService;
import com.help.rebate.service.V2TaobaoUserInfoService;
import com.help.rebate.utils.EmptyUtils;
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
import java.util.Map;
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
    public SafeServiceResponse<String> triggerWithdrawal(@ApiParam(name = "openId", value = "微信openId") @RequestParam String openId,
                                                         @ApiParam(name = "withdrawalAmount", value = "提现金额，单位：分") @RequestParam Integer withdrawalAmount,
                                                         @ApiParam(name = "msg", value = "提现的说明信息，如：模拟提现、多退金额追回") @RequestParam(required = false) String msg) {
        try{
            SafeServiceResponse.startBiz();

            //插入
            if (withdrawalAmount <= 0){
                withdrawalAmount = v2TaobaoCommissionAccountService.getWithdrawalAmount();
            }
            long stubId = v2TaobaoCommissionAccountService.triggerWithdrawal(openId, withdrawalAmount);

            //直接提现
            v2TaobaoCommissionAccountService.postTriggerWithdrawal(openId, withdrawalAmount, true, EmptyUtils.isEmpty(msg) ? "模拟提现成功" : msg, stubId);

            //返回
            return SafeServiceResponse.success("触发提现成功 - " + withdrawalAmount);
        }catch(Exception e){
            logger.error("fail to execute[/tbk/triggerWithdrawal]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("根据OpenId，逆向触发提现金额回退")
    @RequestMapping("/backingTriggerWithdrawal")
    public SafeServiceResponse<String> backingTriggerWithdrawal(@ApiParam(name = "openId", value = "微信openId") @RequestParam String openId,
                                                                @ApiParam(name = "withdrawalAmount", value = "单次额度（精确到分）") @RequestParam(required = false) Integer withdrawalAmount) {
        try{
            SafeServiceResponse.startBiz();

            //插入
            v2TaobaoCommissionAccountService.backingTriggerWithdrawal(openId, withdrawalAmount, "手动回退", -1);

            //返回
            return SafeServiceResponse.success("逆向触发提现金额回退成功");
        }catch(Exception e){
            logger.error("fail to execute[/tbk/backingTriggerWithdrawal]", e);
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

    @ApiOperation("设置提现配置(内存)")
    @RequestMapping("/setWithdrawalConfig")
    public SafeServiceResponse<Map<String, String>> setWithdrawalConfig(
            @ApiParam(name = "totalAccount", value = "银行卡总余额（元）") @RequestParam(required = false) String totalAccount,
            @ApiParam(name = "maxWithdrawalTimesPerUser", value = "最大提现次数") @RequestParam(required = false) Integer maxWithdrawalTimesPerUser,
            @ApiParam(name = "withdrawalAmount", value = "单次提现额度（精确到分）") @RequestParam(required = false) Integer withdrawalAmount
    ) {
        try{
            SafeServiceResponse.startBiz();

            //插入
            v2TaobaoCommissionAccountService.setWithdrawalConfig(totalAccount, maxWithdrawalTimesPerUser, withdrawalAmount);

            Map<String, String> bankTotalAccount = v2TaobaoCommissionAccountService.getWithdrawalConfig();

            //返回
            return SafeServiceResponse.success(bankTotalAccount);
        }catch(Exception e){
            logger.error("fail to execute[/tbk/commission/setWithdrawalConfig]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("获取提现配置(内存)")
    @RequestMapping("/getWithdrawalConfig")
    public SafeServiceResponse<Map<String, String>> getWithdrawalConfig() {
        try{
            SafeServiceResponse.startBiz();

            //插入
            Map<String, String> bankTotalAccount = v2TaobaoCommissionAccountService.getWithdrawalConfig();

            //返回
            return SafeServiceResponse.success(bankTotalAccount);
        }catch(Exception e){
            logger.error("fail to execute[/tbk/commission/getWithdrawalConfig]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
