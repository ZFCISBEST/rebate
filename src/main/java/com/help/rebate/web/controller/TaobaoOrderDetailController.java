package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfo;
import com.help.rebate.service.V2TaobaoOrderService;
import com.help.rebate.utils.Checks;
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
import java.util.List;

/**
 * 淘宝订单详情操作API
 * @author zfcisbest
 * @date 21/11/14
 */
@Api("淘宝订单详情操作API")
@RestController
@RequestMapping("/tbk/order")
public class TaobaoOrderDetailController {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoOrderDetailController.class);

    /**
     * 订单服务
     */
    @Resource
    private V2TaobaoOrderService v2TaobaoOrderService;

    /**
     * 调度订单同步
     * @param orderUpdateTime 订单同步起始时间 yyyy-MM-dd HH:mm:ss，为空时直接取数据库的数据
     * @param minuteStep 取数据时长 范围[1-180]，为空取默认180
     * @param orderType 订单类型 1-常规订单、2-渠道订单、3-会员运营订单、0-都查，默认0都查
     * @param queryTimeType 时间的属性 1-创建时间、2-付款时间、3-结算时间、4-更新时间、0-都使用，默认2付款时间
     * @param running 运行标识 true/false
     * @return
     */
    @ApiOperation("订单同步")
    @RequestMapping("/scheduleSyncOrder")
    public SafeServiceResponse scheduleSyncOrder(
                                     @ApiParam(name = "syncStartTime", value = "订单同步起始时间 yyyy-MM-dd HH:mm:ss，为空时直接取数据库的数据") @RequestParam(required = false) String orderUpdateTime,
                                     @ApiParam(name = "minuteStep", value = "取数据时长 范围[1-180]，为空取默认180") @RequestParam(required = false) Long minuteStep,
                                     @ApiParam(name = "orderType", value = "订单类型 1-常规订单、2-渠道订单、3-会员运营订单、0-都查，默认0都查") @RequestParam(required = false) Integer orderType,
                                     @ApiParam(name = "queryTimeType", value = "时间的属性1-创建时间、2-付款时间、3-结算时间、4-更新时间、0-都使用，默认2付款时间") @RequestParam(required = false) Integer queryTimeType,
                                     @ApiParam(name = "running", value = "运行标识 true/false，false时，表示只将同步设置写到数据库") @RequestParam(required = false) Boolean running){
        try {
            SafeServiceResponse.startBiz();

            //默认是false，关闭运行
            if (running == null) {
                running = false;
            }
            if (orderType == null) {
                orderType = 0;
            }
            if (queryTimeType == null) {
                queryTimeType = 2;
            }

            //校验
            Checks.isTrue(minuteStep == null || minuteStep > 0 && minuteStep <= 180, "时间范围只能在[0-180之间]");
            Checks.isIn(orderType, new int[]{0, 1, 2, 3}, "可选数组[0-3]");
            Checks.isIn(queryTimeType, new int[]{0, 1, 2, 3, 4}, "可选数组[0-4]");

            //插入
            int affectedCnt = v2TaobaoOrderService.scheduleSyncOrder(orderUpdateTime, minuteStep, orderType, queryTimeType, running);

            //返回
            return SafeServiceResponse.success("启动成功 - " + affectedCnt);
        } catch (Exception e) {
            logger.error("fail to sync order[/tbk/order/scheduleSyncOrder]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    /**
     * 列出所有符合条件的订单
     * @param tradeParentId
     * @param tradeId
     * @param specialId
     * @param relationId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation("列出所有符合条件的订单")
    @RequestMapping("/listAll")
    public SafeServiceResponse listAll(@ApiParam(name = "tradeParentId", value = "指定父单号") @RequestParam(required = false) String tradeParentId,
                                       @ApiParam(name = "tradeId", value = "指定子单号") @RequestParam(required = false) String tradeId,
                                       @ApiParam(name = "specialId", value = "会员ID") @RequestParam(required = false) String specialId,
                                       @ApiParam(name = "relationId", value = "渠道ID") @RequestParam(required = false) String relationId,
                                       @ApiParam(name = "pageNo", value = "页码 默认为1") @RequestParam(required = false) Integer pageNo,
                                       @ApiParam(name = "pageSize", value = "每页行数 默认为10") @RequestParam(required = false) Integer pageSize){
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
            List<V2TaobaoOrderDetailInfo> orderDetailList = v2TaobaoOrderService.listAll(tradeParentId, tradeId, specialId, relationId, pageNo, pageSize);

            //返回
            return SafeServiceResponse.success(orderDetailList);
        }catch(Exception e){
            logger.error("fail to list all orders[/tbk/order/listAll]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
