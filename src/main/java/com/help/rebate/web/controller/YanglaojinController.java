package com.help.rebate.web.controller;
import java.time.LocalDateTime;

import com.help.rebate.dao.entity.V2YljDetailInfo;
import com.help.rebate.model.GenericRowListDTO;
import com.help.rebate.model.V2YljDetailInfoDTO;
import com.help.rebate.service.V2YljDetailInfoService;
import com.help.rebate.web.response.SafeServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("yllgAPI")
@RestController
@CrossOrigin
@RequestMapping("/yanglaojin")
public class YanglaojinController {
    private static final Logger logger = LoggerFactory.getLogger(YanglaojinController.class);

    @Resource
    private V2YljDetailInfoService v2YljDetailInfoService;

    @ApiOperation("查询和获取数据")
    @GetMapping("/queryYangLaoJinList")
    public SafeServiceResponse<GenericRowListDTO<V2YljDetailInfo>> queryYangLaoJinList(@ApiParam(name = "v2YljDetailInfoDTO", value = "查询参数") V2YljDetailInfoDTO v2YljDetailInfoDTO) {
        try{
            SafeServiceResponse.startBiz();

            List<V2YljDetailInfo> v2YljDetailInfos = v2YljDetailInfoService.listAll(v2YljDetailInfoDTO, v2YljDetailInfoDTO.getCurrent(), v2YljDetailInfoDTO.getPageSize());

            //http://mmbiz.qpic.cn/mmbiz_jpg/XpGLU7WoibqBvO5KyRAJOia03yjPBrwVJiad1XnrkoAt5xoA17Hf4WyRT82DWAxkFLGeMRaqjAJ7mlmZT03X8de3Q/0
            /*V2YljDetailInfo v2YljDetailInfo = new V2YljDetailInfo();
            v2YljDetailInfo.setId(0L);
            v2YljDetailInfo.setGmtCreated(LocalDateTime.now());
            v2YljDetailInfo.setGmtModified(LocalDateTime.now());
            v2YljDetailInfo.setOpenId("222222");
            v2YljDetailInfo.setMediaPicUrl("http://mmbiz.qpic.cn/mmbiz_jpg/XpGLU7WoibqBvO5KyRAJOia03yjPBrwVJiad1XnrkoAt5xoA17Hf4WyRT82DWAxkFLGeMRaqjAJ7mlmZT03X8de3Q/0");
            v2YljDetailInfo.setYljStubFlowId("5555");
            v2YljDetailInfo.setVerifyStatus((byte)0);
            v2YljDetailInfo.setVerifyStatusMsg("");
            v2YljDetailInfo.setConponStatus((byte)0);
            v2YljDetailInfo.setConponStatusMsg("");
            v2YljDetailInfo.setConponAmount(0);
            v2YljDetailInfo.setStatus((byte)0);
            v2YljDetailInfos.add(v2YljDetailInfo);*/


            long all = v2YljDetailInfoService.countAll(v2YljDetailInfoDTO, v2YljDetailInfoDTO.getCurrent(), v2YljDetailInfoDTO.getPageSize());
            //all += 1;
            //返回指定页数
            //logger.info("【查询】参数:{}", JSON.toJSONString(v2YljDetailInfoDTO, true));

            //返回
            return SafeServiceResponse.success(new GenericRowListDTO<V2YljDetailInfo>(v2YljDetailInfos, (int) all, true));
        }catch(Exception e){
            logger.error("fail to execute[/yanglaojin/queryYangLaoJinList]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("更新审核状态")
    @GetMapping("/updateVerifyStatus")
    public SafeServiceResponse<Boolean> updateVerifyStatus(@ApiParam(name = "verifyStatus", value = "验证状态") @RequestParam Byte verifyStatus,
                                                           @ApiParam(name = "verifyStatusMsg", value = "验证状态信息") @RequestParam String verifyStatusMsg,
                                                           @ApiParam(name = "id", value = "主键") @RequestParam Long id) {
        try{
            SafeServiceResponse.startBiz();

            int affectedCnt = v2YljDetailInfoService.verifyYlj(id, verifyStatus, verifyStatusMsg);

            //返回
            return SafeServiceResponse.success(affectedCnt == 1);
        }catch(Exception e){
            logger.error("fail to execute[/yanglaojin/updateVerifyStatus]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}