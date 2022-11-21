package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.V2TaobaoUserInfo;
import com.help.rebate.service.V2TaobaoUserInfoService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
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
 * 用户信息操作类
 * @author zfcisbest
 * @date 21/11/14
 */
@Api("用户信息API")
@RestController
@RequestMapping("/tbk/user")
public class TaobaoUserInfoController {
    private static final Logger logger = LoggerFactory.getLogger(TaobaoUserInfoController.class);

    @Autowired
    private V2TaobaoUserInfoService v2TaobaoUserInfoService;

    @ApiOperation("添加一个新的用户映射信息")
    @RequestMapping("/addNewUser")
    public SafeServiceResponse<V2TaobaoUserInfo> create(@ApiParam(name = "openId", value = "微信openId") @RequestParam(required = true) String openId,
                                      @ApiParam(name = "relationId", value = "渠道ID 同步自淘宝联盟") @RequestParam(required = false) String relationId,
                                      @ApiParam(name = "specialId", value = "会员ID 同步自淘宝联盟") @RequestParam(required = false) String specialId,
                                      @ApiParam(name = "externalId", value = "外部关联ID") @RequestParam(required = false) String externalId) {
        try{
            SafeServiceResponse.startBiz();

            //重构
            if (EmptyUtils.isEmpty(openId)) {
                openId = null;
            }
            if (EmptyUtils.isEmpty(externalId)) {
                externalId = null;
            }

            //校验
            Checks.isTrue(openId != null, "openId不能为空");

            //插入
            V2TaobaoUserInfo userInfo = v2TaobaoUserInfoService.insertOrDoNone(openId, relationId, specialId, externalId);

            //返回
            return SafeServiceResponse.success(userInfo);
        }catch(Exception e){
            logger.error("fail to create user[/tbk/user/addNewUser]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("列出所有用户数据")
    @RequestMapping("/listAll")
    public SafeServiceResponse<List<V2TaobaoUserInfo>> listAll() {
        try{
            SafeServiceResponse.startBiz();

            //查询
            List<V2TaobaoUserInfo> userInfos = v2TaobaoUserInfoService.listAll();

            //返回
            return SafeServiceResponse.success(userInfos);
        }catch(Exception e){
            logger.error("fail to create user[/tbk/user/listAll]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
