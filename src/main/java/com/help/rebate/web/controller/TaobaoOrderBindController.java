package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapInfo;
import com.help.rebate.service.V2TaobaoOrderBindService;
import com.help.rebate.service.V2TaobaoOrderOpenidMapService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.vo.OrderBindResultVO;
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
public class TaobaoOrderBindController {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoOrderBindController.class);

    @Autowired
    private V2TaobaoOrderBindService v2TaobaoOrderBindService;

    @Autowired
    private V2TaobaoOrderOpenidMapService v2TaobaoOrderOpenidMapService;

    @ApiOperation("根据给定的父订单号，以及用户信息（openId或者指定specialId），执行直接绑定，注意：幂等性")
    @RequestMapping("/bindByTradeParentId")
    public SafeServiceResponse bindByTradeParentId(@ApiParam(name = "openId", value = "微信openId") @RequestParam String openId,
                                      @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID") @RequestParam(required = false) String specialId,
                                      @ApiParam(name = "tradeParentId", value = "交易父单号") @RequestParam String tradeParentId) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            Checks.isTrue(!EmptyUtils.isEmpty(openId) || !EmptyUtils.isEmpty(specialId), "openId和specialId不能同时为空");

            //插入
            OrderBindResultVO orderBindResultVO = v2TaobaoOrderBindService.bindByTradeParentId(tradeParentId, openId, specialId, null);

            //返回
            return SafeServiceResponse.success(orderBindResultVO);
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/bindByTradeParentId]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("指定一段时间，执行订单绑定")
    @RequestMapping("/by_time_range")
    public SafeServiceResponse bindByTimeRange(@ApiParam(name = "orderBindTime", value = "订单绑定的起始时间 yyyy-MM-dd HH:mm:ss，为空时直接取数据库的数据") @RequestParam(required = false) String orderBindTime,
                                               @ApiParam(name = "minuteStep", value = "取数据时长 范围[1-180]，为空取默认5") @RequestParam(required = false) Long minuteStep) {
        try{
            SafeServiceResponse.startBiz();

            //绑定
            List<OrderBindResultVO> orderBindResultVOS = v2TaobaoOrderBindService.bindByTimeRange(orderBindTime, minuteStep);

            //返回
            return SafeServiceResponse.success(orderBindResultVOS);
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/by_time_range]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("根据给定的父订单号，以及用户信息（openId或者指定specialId），查询绑定情况")
    @RequestMapping("/queryBindByTradeParentId")
    public SafeServiceResponse queryBindInfoByTradeParentId(@ApiParam(name = "openId", value = "微信openId") @RequestParam String openId,
                                                   @ApiParam(name = "specialId", value = "淘宝联盟私域会员ID") @RequestParam(required = false) String specialId,
                                                   @ApiParam(name = "tradeParentId", value = "交易父单号") @RequestParam String tradeParentId) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            Checks.isTrue(!EmptyUtils.isEmpty(openId) || !EmptyUtils.isEmpty(specialId), "openId和specialId不能同时为空");

            //插入
            List<V2TaobaoOrderOpenidMapInfo> orderOpenidMapList = v2TaobaoOrderOpenidMapService.queryBindInfoByTradeParentId(tradeParentId, openId, specialId);
            if (EmptyUtils.isEmpty(orderOpenidMapList)) {
                return SafeServiceResponse.success("尚未绑定");
            }

            //返回
            return SafeServiceResponse.success("已经绑定");
        }catch(Exception e){
            logger.error("fail to bind order by tradeId[/tbk/order/bind/queryBindByTradeParentId]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("更新订单绑定表，记录维权退回的金额")
    @RequestMapping("/recordRefundFee")
    public SafeServiceResponse recordRefundFee(@ApiParam(name = "tradeId", value = "交易子单号") @RequestParam String tradeId,
                                             @ApiParam(name = "refundFee", value = "维权退回返利") @RequestParam String refundFee) {
        try{
            SafeServiceResponse.startBiz();

            //插入或者更新
            v2TaobaoOrderBindService.bindRefundFee(tradeId, refundFee);

            //返回
            return SafeServiceResponse.success("更新成功");
        }catch(Exception e){
            logger.error("fail to bind refund fee by [/tbk/order/bind/recordRefundFee]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
