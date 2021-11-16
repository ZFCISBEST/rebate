package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.OrderDetail;
import com.help.rebate.dao.entity.PubsiteCombination;
import com.help.rebate.service.OrderService;
import com.help.rebate.service.PubsiteCombinationService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.web.response.SafeServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 淘宝订单详情操作API
 * @author zfcisbest
 * @date 21/11/14
 */
@Api("淘宝订单详情操作API")
@RestController
@RequestMapping("/tbk/order")
public class OrderDetailController {
    private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);

    /**
     * 订单服务
     */
    @Resource
    private OrderService orderService;

    /**
     * 订单同步
     * @param orderUpdateTime 订单同步起始时间 yyyy-MM-dd HH:mm:ss，为空时直接取数据库的数据
     * @param minuteStep 取数据时长 范围[1-180]，为空取默认180
     * @param orderScene 订单类型 1-常规订单、2-渠道订单、3-会员运营订单、0-都查，默认0都查
     * @param queryType 时间的属性 1-创建时间、2-付款时间、3-结算时间、4-更新时间、0-都使用，默认2付款时间
     * @param running 运行标识 true/false
     * @return
     */
    @ApiOperation("订单同步")
    @RequestMapping("/build")
    public SafeServiceResponse build(@ApiParam(name = "订单同步起始时间", value = "yyyy-MM-dd HH:mm:ss，为空时直接取数据库的数据") @RequestParam(required = false) String orderUpdateTime,
                                     @ApiParam(name = "取数据时长", value = "范围[1-180]，为空取默认180") @RequestParam(required = false) Long minuteStep,
                                     @ApiParam(name = "订单类型", value = "1-常规订单、2-渠道订单、3-会员运营订单、0-都查，默认0都查") @RequestParam(required = false) Integer orderScene,
                                     @ApiParam(name = "时间的属性", value = "1-创建时间、2-付款时间、3-结算时间、4-更新时间、0-都使用，默认2付款时间") @RequestParam(required = false) Integer queryType,
                                     @ApiParam(name = "运行标识", value = "true/false") @RequestParam(required = false) Boolean running){
        try {
            SafeServiceResponse.startBiz();

            //默认是false，关闭运行
            if (running == null) {
                running = false;
            }
            if (orderScene == null) {
                orderScene = 0;
            }
            if (queryType == null) {
                queryType = 2;
            }

            //校验
            Checks.isTrue(minuteStep == null || minuteStep > 0 && minuteStep <= 180, "时间范围只能在[0-180之间]");
            Checks.isIn(orderScene, new int[]{0, 1, 2, 3}, "可选数组[0-3]");
            Checks.isIn(queryType, new int[]{0, 1, 2, 3, 4}, "可选数组[0-4]");

            //插入
            int affectedCnt = orderService.syncOrder(orderUpdateTime, minuteStep, orderScene, queryType, running);

            //返回
            return SafeServiceResponse.success("启动成功 - " + affectedCnt);
        } catch (Exception e) {
            logger.error("fail to sync order[/tbk/order/sync]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    /**
     * 列出所有符合条件的订单
     * @param parentTradeId
     * @param tradeId
     * @param specialId
     * @param relationId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation("列出所有符合条件的订单")
    @RequestMapping("/list/all")
    public SafeServiceResponse listAll(@ApiParam(name = "交易父单号", value = "指定父单号") @RequestParam(required = false) String parentTradeId,
                                       @ApiParam(name = "交易子单", value = "指定子单号") @RequestParam(required = false) String tradeId,
                                       @ApiParam(name = "会员ID") @RequestParam(required = false) String specialId,
                                       @ApiParam(name = "渠道ID") @RequestParam(required = false) String relationId,
                                       @ApiParam(name = "页码", value = "默认为1") @RequestParam(required = false) Integer pageNo,
                                       @ApiParam(name = "每页行数", value = "默认为10") @RequestParam(required = false) Integer pageSize){
        try{
            SafeServiceResponse.startBiz();

            //提前判定
            if (pageNo == null | pageNo <= 0) {
                pageNo = 1;
            }
            if (pageSize == null | pageSize <= 0) {
                pageSize = 10;
            }

            //插入
            List<OrderDetail> orderDetailList = orderService.listAll(parentTradeId, tradeId, specialId, relationId, pageNo, pageSize);

            //重新包装
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("records", orderDetailList);
            result.put("order_count", orderDetailList == null ? 0 : orderDetailList.size());

            //返回
            return SafeServiceResponse.success(result);
        }catch(Exception e){
            logger.error("fail to list all orders[/tbk/order/list/all]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
