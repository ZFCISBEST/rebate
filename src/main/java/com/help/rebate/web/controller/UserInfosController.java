package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.UserInfos;
import com.help.rebate.service.UserInfosService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.web.response.SafeServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息操作类
 * @author zfcisbest
 * @date 21/11/14
 */
@Api("用户信息API")
@RestController
@RequestMapping("/tbk/user")
public class UserInfosController {
    private static final Logger logger = LoggerFactory.getLogger(UserInfosController.class);

    @Autowired
    private UserInfosService userInfosService;

    @ApiOperation("创建新用户")
    @RequestMapping("/create")
    public SafeServiceResponse create(@RequestParam String openId,
                                      @RequestParam(required = false) String relationId,
                                      @RequestParam(required = false) String specialId,
                                      @RequestParam(required = false) String externalId,
                                      @RequestParam(required = false) String dataFrom) {
        try{
            SafeServiceResponse.startBiz();

            //校验
            Checks.isNotEmpty(openId, "openId不能为空");

            //为空，默认为淘宝
            if (EmptyUtils.isEmpty(dataFrom)) {
                dataFrom = "tb";
            }

            //插入
            UserInfos userInfos = userInfosService.insertOrDoNone(openId, relationId, specialId, externalId, dataFrom);

            //返回
            return SafeServiceResponse.success(userInfos);
        }catch(Exception e){
            logger.error("fail to create user[/tbk/user/create]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("自动建立开放ID和会员信息的关系")
    @RequestMapping("/automap/special")
    public SafeServiceResponse create(@RequestParam(required = false) String specialId,
                                      @RequestParam(required = false) String externalId) {
        try{
            SafeServiceResponse.startBiz();

            //插入
            int affectedCnt = userInfosService.autoMap(specialId, externalId);

            //返回
            return SafeServiceResponse.success("受影响记录数 - " + affectedCnt);
        }catch(Exception e){
            logger.error("fail to create user[/tbk/user/automap/special]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("列出所有用户")
    @RequestMapping("/list/all")
    public SafeServiceResponse listAll() {
        try{
            SafeServiceResponse.startBiz();

            //查询
            List<UserInfos> userInfos = userInfosService.listAll();

            //重新包装
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("records", userInfos);
            result.put("user_count", userInfos == null ? 0 : userInfos.size());

            //返回
            return SafeServiceResponse.success(result);
        }catch(Exception e){
            logger.error("fail to create user[/tbk/user/list/all]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
