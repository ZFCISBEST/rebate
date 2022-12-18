package com.help.rebate.web.controller;

import com.help.rebate.model.DashboardVO;
import com.help.rebate.model.GenericRowListDTO;
import com.help.rebate.model.WideOrderDetailListDTO;
import com.help.rebate.model.WideOrderDetailListVO;
import com.help.rebate.service.V2TaobaoDashboardService;
import com.help.rebate.web.response.SafeServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api("大盘数据")
@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class TaobaoDashboardController {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoDashboardController.class);

    @Resource
    private V2TaobaoDashboardService v2TaobaoDashboardService;

    @ApiOperation("订单列表")
    @GetMapping("/queryOrderList")
    public SafeServiceResponse<GenericRowListDTO<WideOrderDetailListVO>> queryOrderList(@ApiParam(name = "wideOrderDetailListDTO", value = "查询参数") WideOrderDetailListDTO wideOrderDetailListDTO) {
        try{
            SafeServiceResponse.startBiz();

            List<WideOrderDetailListVO> wideOrderDetailListVOs = v2TaobaoDashboardService.listAll(wideOrderDetailListDTO, wideOrderDetailListDTO.getCurrent(), wideOrderDetailListDTO.getPageSize());

            long all = v2TaobaoDashboardService.countAll(wideOrderDetailListDTO, wideOrderDetailListDTO.getCurrent(), wideOrderDetailListDTO.getPageSize());

            //返回
            return SafeServiceResponse.success(new GenericRowListDTO<WideOrderDetailListVO>(wideOrderDetailListVOs, (int) all, true));
        }catch(Exception e){
            logger.error("fail to execute[/dashboard/queryOrderList]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("订单统计大盘")
    @GetMapping("/queryDashboard")
    public SafeServiceResponse<DashboardVO> queryDashboard(@ApiParam(name = "lastDaysOfOrder", value = "最近几天的订单") Integer lastDaysOfOrder) {
        try{
            SafeServiceResponse.startBiz();

            DashboardVO dashboardVO = v2TaobaoDashboardService.queryDashboard(lastDaysOfOrder);

            //返回
            return SafeServiceResponse.success(dashboardVO);
        }catch(Exception e){
            logger.error("fail to execute[/dashboard/queryDashboard]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}