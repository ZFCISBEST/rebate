package com.help.rebate.web.controller;

import com.alibaba.fastjson.JSON;
import com.help.rebate.dao.entity.V2TaobaoUserInfo;
import com.help.rebate.dao.entity.V2YljDetailInfo;
import com.help.rebate.model.GenericRowListDTO;
import com.help.rebate.model.LoginResultDTO;
import com.help.rebate.model.V2YljDetailInfoDTO;
import com.help.rebate.web.response.SafeServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Api("yllgAPI")
@RestController
@CrossOrigin
@RequestMapping("/yanglaojin")
public class YanglaojinController {
    private static final Logger logger = LoggerFactory.getLogger(YanglaojinController.class);

    public static List<V2YljDetailInfo> allData = null;
    public static int allSize = 200;


    @ApiOperation("查询和获取数据")
    @GetMapping("/queryYangLaoJinList")
    public SafeServiceResponse<GenericRowListDTO<V2YljDetailInfo>> queryYangLaoJinList(@ApiParam(name = "v2YljDetailInfoDTO", value = "查询参数") V2YljDetailInfoDTO v2YljDetailInfoDTO) {
        try{
            SafeServiceResponse.startBiz();

            List<V2YljDetailInfo> list = new ArrayList<V2YljDetailInfo>();
            if (allData == null) {
                allData = new ArrayList<V2YljDetailInfo>();
                for (int i = 0; i < allSize; i++) {
                    V2YljDetailInfo v2YljDetailInfo = new V2YljDetailInfo();
                    v2YljDetailInfo.setId(i + 0L);
                    v2YljDetailInfo.setGmtCreated(LocalDateTime.now());
                    v2YljDetailInfo.setGmtModified(LocalDateTime.now());
                    v2YljDetailInfo.setOpenId("oo-" + i);
                    v2YljDetailInfo.setMediaPicUrl("url--" + i + ".gif");
                    v2YljDetailInfo.setYljStubFlowId("stub_" + i);
                    v2YljDetailInfo.setVerifyStatus((byte)0);
                    v2YljDetailInfo.setVerifyStatusMsg("成功");
                    v2YljDetailInfo.setConponStatus((byte)0);
                    v2YljDetailInfo.setConponStatusMsg("成功");
                    v2YljDetailInfo.setConponAmount(0);
                    v2YljDetailInfo.setStatus((byte)0);
                    allData.add(v2YljDetailInfo);
                }
            }

            //获取页数
            list = allData;

            //返回指定页数
            logger.info("【查询】参数:{}", JSON.toJSONString(v2YljDetailInfoDTO, true));

            //返回
            return SafeServiceResponse.success(new GenericRowListDTO<V2YljDetailInfo>(list, allSize, true));
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

            V2YljDetailInfo v2YljDetailInfo = allData.stream().filter(a -> a.getId() == id).findFirst().get();

            v2YljDetailInfo.setVerifyStatus(verifyStatus);
            v2YljDetailInfo.setVerifyStatusMsg(verifyStatusMsg);

            //返回
            return SafeServiceResponse.success(true);
        }catch(Exception e){
            logger.error("fail to execute[/yanglaojin/updateVerifyStatus]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}