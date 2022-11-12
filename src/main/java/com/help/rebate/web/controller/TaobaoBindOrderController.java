package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.OrderOpenidMap;
import com.help.rebate.service.OrderBindService;
import com.help.rebate.service.OrderOpenidMapService;
import com.help.rebate.service.V2TaobaoUserInfoService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.vo.CommissionVO;
import com.help.rebate.vo.OrderBindResultVO;
import com.help.rebate.vo.PickCommissionVO;
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
 * 1、测试 手动绑定 订单 finish
 * 2、测试 查询 绑定的订单详情 finish
 * 3、测试 查询 返利信息，已付款、待结算、已结算（待提现）、已提现 finish
 * 4、启停 订单绑定服务，类似于订单同步服务，从什么时候开始执行绑定 part
 * 5、测试 指定一段时间，执行订单绑定 part
 * 6、测试 提现（虚拟提现，目的测试流程），查询余额
 * 7、测试 查询一个订单是否已经绑定成功 finish
 * @author zfcisbest
 * @date 22/03/27
 */
@Api("绑定订单相关服务API")
@RestController
@RequestMapping("/tbk/order/bind")
public class TaobaoBindOrderController {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoBindOrderController.class);

    @Autowired
    private V2TaobaoUserInfoService v2TaobaoUserInfoService;

    @Autowired
    private OrderBindService orderBindService;

    @Autowired
    private OrderOpenidMapService orderOpenidMapService;

    @ApiOperation("根据给定的父订单号，以及用户信息（openId或者指定specialId），执行直接绑定，注意：幂等性")
    @RequestMapping("/by_trade_id")
    public SafeServiceResponse bindByTradeParentId(@ApiParam(name = "openId", value = "微信openId") @RequestParam(required = false) String openId,
                                      @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID") @RequestParam(required = false) String specialId,
                                      @ApiParam(name = "tradeParentId", value = "交易父单号") @RequestParam(required = true) String tradeParentId) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            Checks.isTrue(!EmptyUtils.isEmpty(openId) || !EmptyUtils.isEmpty(specialId), "openId和specialId不能同时为空");
            //Checks.isTrue(openId != null || specialId != null, "openId和specialID不能同时为空");

            //插入
            OrderBindResultVO orderBindResultVO = orderBindService.bindByTradeId(tradeParentId, openId, specialId, null);

            //返回
            return SafeServiceResponse.success(orderBindResultVO);
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/by_trade_id]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("根据给定的父订单号，以及用户信息（openId或者指定specialId），查询绑定情况")
    @RequestMapping("/query_bind_info")
    public SafeServiceResponse queryBindInfoByTradeParentId(@ApiParam(name = "openId", value = "微信openId") @RequestParam(required = false) String openId,
                                                   @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID") @RequestParam(required = false) String specialId,
                                                   @ApiParam(name = "tradeParentId", value = "交易父单号") @RequestParam(required = true) String tradeParentId) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            Checks.isTrue(!EmptyUtils.isEmpty(openId) || !EmptyUtils.isEmpty(specialId), "openId和specialId不能同时为空");
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
    public SafeServiceResponse queryCommissionByStatus(@ApiParam(name = "openId", value = "微信openId") @RequestParam(required = false) String openId,
                                                        @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID") @RequestParam(required = false) String specialId,
                                                        @ApiParam(name = "orderStatuss", value = "订单状态 - 12-付款，13-关闭，14-确认收货，3-结算成功，可多个，逗号隔开") @RequestParam(required = true) String orderStatuss,
                                                        @ApiParam(name = "commissionStatuss", value = "给用户的结算状态,可填多个，逗号隔开 - 待提取、提取中，提取成功，提取失败,提取超时") @RequestParam(required = true) String commissionStatuss,
                                                       @ApiParam(name = "payStartTime", value = "支付起始日期，可不填，用在触发提取中") @RequestParam(required = false) String payStartTime,
                                                       @ApiParam(name = "payEndTime", value = "支付结束日期，可不填，用在触发提取中") @RequestParam(required = false) String payEndTime) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            Checks.isTrue(!EmptyUtils.isEmpty(openId) || !EmptyUtils.isEmpty(specialId), "openId和specialId不能同时为空");
            //Checks.isTrue(orderStatus >= 12 && orderStatus <= 14 || orderStatus == 3, "订单状态只能是12、13、14、3");
            //Checks.isTrue(commissionStatus.equals("待结算") || commissionStatus.equals("已结算") || commissionStatus.equals("结算中"), "返利状态只能是[待结算、已结算、结算中]");

            //查询
            CommissionVO commissionVO = orderOpenidMapService.selectCommissionBy(openId, specialId, orderStatuss, commissionStatuss, payStartTime, payEndTime);

            //返回
            return SafeServiceResponse.success(commissionVO);
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/query_commission]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("测试提取返利，各种状态的流转。tip-返利状态只能是[待结算、已结算、结算中]")
    @RequestMapping("/mock_pick_money")
    public SafeServiceResponse mockPickMoney(@ApiParam(name = "openId", value = "微信openId") @RequestParam(required = false) String openId,
                                             @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID") @RequestParam(required = false) String specialId,
                                             @ApiParam(name = "mockStatus", value = "当前模拟的是哪种状态 - 触发提取、提取成功，提取失败, 提取超时") @RequestParam(required = false) String mockStatus,
                                             @ApiParam(name = "payStartTime", value = "支付起始日期，可不填，用在触发提取中") @RequestParam(required = false) String payStartTime,
                                             @ApiParam(name = "payEndTime", value = "支付结束日期，可不填，用在触发提取中") @RequestParam(required = false) String payEndTime) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            Checks.isTrue(!EmptyUtils.isEmpty(openId) || !EmptyUtils.isEmpty(specialId), "openId和specialId不能同时为空");
            Checks.isTrue("触发提取、提取成功，提取失败, 提取超时".contains(mockStatus), "当前模拟的状态只能是 - 触发提取、提取成功，提取失败, 提取超时");

            //查询
            PickCommissionVO pickCommissionVO = orderBindService.mockPickMoney(openId, specialId, mockStatus, payStartTime, payEndTime);

            //返回
            return SafeServiceResponse.success(pickCommissionVO);
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/mock_pick_money]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("指定起始时间，周期性调度，执行订单绑定")
    @RequestMapping("/by_time_range_sync")
    public SafeServiceResponse syncBindByTimeRange(@ApiParam(name = "orderBindTime", value = "订单绑定的起始时间 yyyy-MM-dd HH:mm:ss，为空时直接取数据库的数据") @RequestParam(required = false) String orderBindTime,
                                               @ApiParam(name = "minuteStep", value = "取数据时长 范围[1-180]，为空取默认5") @RequestParam(required = false) Long minuteStep,
                                               @ApiParam(name = "running", value = "运行标识 true/false") @RequestParam(required = false) Boolean running) {
        try{
            SafeServiceResponse.startBiz();

            //默认是false，关闭运行
            if (running == null) {
                running = false;
            }

            //校验
            //Checks.isTrue(minuteStep == null || minuteStep > 0 && minuteStep <= 180, "时间范围只能在[0-180之间]");

            //插入
            boolean success = orderBindService.syncBindOrderByTimeStart(orderBindTime, minuteStep, running);

            //返回
            return SafeServiceResponse.success(success);
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/by_time_range_sync]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("指定一段时间，执行订单绑定")
    @RequestMapping("/by_time_range")
    public SafeServiceResponse bindByTimeRange(@ApiParam(name = "openId", value = "微信openId，用于圈定范围，可不指定") @RequestParam(required = false) String openId,
                                               @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID，用于圈定范围，可不指定") @RequestParam(required = false) String specialId,
                                               @ApiParam(name = "orderBindTime", value = "订单绑定的起始时间 yyyy-MM-dd HH:mm:ss，为空时直接取数据库的数据") @RequestParam(required = false) String orderBindTime,
                                               @ApiParam(name = "minuteStep", value = "取数据时长 范围[1-180]，为空取默认5") @RequestParam(required = false) Long minuteStep) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            //Checks.isTrue(minuteStep == null || minuteStep > 0 && minuteStep <= 180, "时间范围只能在[0-180之间]");

            //绑定
            List<OrderBindResultVO> orderBindResultVOS = orderBindService.bindByTimeRange(openId, specialId, orderBindTime, minuteStep);

            //返回
            return SafeServiceResponse.success(orderBindResultVOS);
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/by_time_range]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("更新订单绑定表，记录维权退回的金额")
    @RequestMapping("/bind_refund_fee")
    public SafeServiceResponse bindRefundFee(@ApiParam(name = "openId", value = "微信openId") @RequestParam(required = false) String openId,
                                             @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID") @RequestParam(required = false) String specialId,
                                             @ApiParam(name = "tradeId", value = "交易子单号") @RequestParam(required = true) String tradeId,
                                             @ApiParam(name = "itemId", value = "交易的商品ID") @RequestParam(required = false) String itemId,
                                             @ApiParam(name = "refundFee", value = "维权退回返利") @RequestParam(required = true) String refundFee) {
        try{
            SafeServiceResponse.startBiz();

            //插入或者更新
            orderBindService.bindRefundFee(openId, specialId, tradeId, itemId, refundFee);

            //返回
            return SafeServiceResponse.success("更新成功");
        }catch(Exception e){
            logger.error("fail to bind refund fee by [/tbk/order/bind/bind_refund_fee]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
