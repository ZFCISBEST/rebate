package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.V2TaobaoUserInfo;
import com.help.rebate.model.LoginResultDTO;
import com.help.rebate.model.LoginUserInfo;
import com.help.rebate.web.response.SafeServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("用户信息API")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @ApiOperation("登录")
    @RequestMapping("/login")
    public SafeServiceResponse<LoginResultDTO> login(@ApiParam(name = "username", value = "用户名") @RequestParam String username,
                                                        @ApiParam(name = "password", value = "密码") @RequestParam String password,
                                                        @ApiParam(name = "autoLogin", value = "自动登录") @RequestParam(required=false) Boolean autoLogin) {
        try{
            SafeServiceResponse.startBiz();

            LoginUserInfo userInfo = LoginUserInfo.getByUserName(username);

            LoginResultDTO result = new LoginResultDTO();
            if (password.equals("tangpingbujuan666")) {
                result.setType("account");
                result.setStatus("ok");
                result.setCurrentAuthority(userInfo.getAccess());
                result.setAccess(userInfo.getAccess());
            }
            else {
                result.setType("account");
                result.setStatus("error");
                result.setCurrentAuthority(userInfo.getAccess());
                result.setAccess(userInfo.getAccess());
            }

            //返回
            return SafeServiceResponse.success(result);
        }catch(Exception e){
            logger.error("fail to execute[/user/login]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("登出")
    @RequestMapping("/loginOut")
    public SafeServiceResponse<Boolean> loginOut(@ApiParam(name = "username", value = "用户名") @RequestParam String username) {
        try{
            SafeServiceResponse.startBiz();

            logger.info("登出，用户名:{}", username);

            //返回
            return SafeServiceResponse.success(true);
        }catch(Exception e){
            logger.error("fail to execute[/user/loginOut]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("登出")
    @RequestMapping("/currentUser")
    public SafeServiceResponse<LoginUserInfo> currentUser(@ApiParam(name = "username", value = "用户名") @RequestParam String username) {
        try{
            SafeServiceResponse.startBiz();

            logger.info("用户信息，用户名:{}", username);

            LoginUserInfo byUserName = LoginUserInfo.getByUserName(username);

            //返回
            return SafeServiceResponse.success(byUserName);
        }catch(Exception e){
            logger.error("fail to execute[/user/currentUser]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}