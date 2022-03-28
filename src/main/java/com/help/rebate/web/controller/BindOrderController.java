package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.OrderOpenidMap;
import com.help.rebate.service.OrderBindService;
import com.help.rebate.service.OrderOpenidMapService;
import com.help.rebate.service.UserInfosService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.vo.CommissionVO;
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

import java.util.List;

/**
 * 订单绑定服务接口
 * 1、测试 手动绑定 订单
 * 2、测试 查询 绑定的订单详情
 * 3、测试 查询 返利信息，已付款、待结算、已结算（待提现）、已提现
 * 4、启停 订单绑定服务，类似于订单同步服务，从什么时候开始执行绑定
 * 5、测试 指定一段时间，执行订单绑定
 * 6、测试 提现（虚拟提现，目的测试流程），查询余额
 * 7、测试 查询一个订单是否已经绑定成功
 * @author zfcisbest
 * @date 22/03/27
 */
@Api("绑定订单相关服务API")
@RestController
@RequestMapping("/tbk/order/bind")
public class BindOrderController {
    private static final Logger logger = LoggerFactory.getLogger(BindOrderController.class);

    @Autowired
    private UserInfosService userInfosService;

    @Autowired
    private OrderBindService orderBindService;

    @Autowired
    private OrderOpenidMapService orderOpenidMapService;

    @ApiOperation("根据给定的父订单号，以及用户信息（openId或者指定specialId），执行直接绑定，注意：幂等性")
    @RequestMapping("/by_trade_id")
    public SafeServiceResponse bindByTradeParentId(@ApiParam(name = "openId", value = "微信openId") @RequestParam(required = true) String openId,
                                      @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID") @RequestParam(required = false) String specialId,
                                      @ApiParam(name = "tradeParentId", value = "交易父单号") @RequestParam(required = true) String tradeParentId) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            //Checks.isTrue(openId != null || specialId != null, "openId和specialID不能同时为空");


            //插入
            List<String> itemTitleList = orderBindService.bindByTradeId(tradeParentId, openId, specialId, null);

            //返回
            return SafeServiceResponse.success(itemTitleList);
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/by_trade_id]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("根据给定的父订单号，以及用户信息（openId或者指定specialId），执行直接绑定，注意：幂等性")
    @RequestMapping("/query_bind_info")
    public SafeServiceResponse queryBindInfoByTradeParentId(@ApiParam(name = "openId", value = "微信openId") @RequestParam(required = true) String openId,
                                                   @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID") @RequestParam(required = false) String specialId,
                                                   @ApiParam(name = "tradeParentId", value = "交易父单号") @RequestParam(required = true) String tradeParentId) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            //Checks.isTrue(openId != null || specialId != null, "openId和specialID不能同时为空");


            //插入
            List<OrderOpenidMap> orderOpenidMapList = orderOpenidMapService.selectBy(tradeParentId, openId, specialId);
            if (EmptyUtils.isEmpty(orderOpenidMapList)) {
                return SafeServiceResponse.success("尚未绑定");
            }

            //返回
            return SafeServiceResponse.success("已经绑定");
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/query_bind_info]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("查询返利的汇总信息")
    @RequestMapping("/query_commission")
    public SafeServiceResponse queryCommissionByStatus(@ApiParam(name = "openId", value = "微信openId") @RequestParam(required = true) String openId,
                                                   @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID") @RequestParam(required = false) String specialId,
                                                   @ApiParam(name = "orderStatus", value = "订单状态 - 12-付款，13-关闭，14-确认收货，3-结算成功") @RequestParam(required = true) Integer orderStatus,
                                                   @ApiParam(name = "commissionStatus", value = "给用户的结算状态 - 待结算、已结算、结算中") @RequestParam(required = true) String commissionStatus) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            Checks.isTrue(orderStatus >= 12 && orderStatus <= 14 || orderStatus == 3, "订单状态只能是12、13、14、3");
            Checks.isTrue(commissionStatus.equals("待结算") || commissionStatus.equals("已结算") || commissionStatus.equals("结算中"), "返利状态只能是[待结算、已结算、结算中]");


            //查询
            CommissionVO commissionVO = orderOpenidMapService.selectCommissionBy(openId, specialId, orderStatus, commissionStatus);

            //返回
            return SafeServiceResponse.success(commissionVO);
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/query_commission]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
