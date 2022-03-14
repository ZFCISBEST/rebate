package com.help.rebate.web.controller;

import com.help.rebate.dao.entity.UserInfos;
import com.help.rebate.service.UserInfosService;
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

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Enumeration;
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

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @ApiOperation("创建新用户")
    @RequestMapping("/create")
    public SafeServiceResponse create(@ApiParam(name = "openId", value = "微信openId 和externalId不能同时为空") @RequestParam(required = false) String openId,
                                      @ApiParam(name = "relationId", value = "渠道ID 同步自淘宝联盟") @RequestParam(required = false) String relationId,
                                      @ApiParam(name = "specialId", value = "会员ID 同步自淘宝联盟") @RequestParam(required = false) String specialId,
                                      @ApiParam(name = "externalId", value = "外部关联ID 默认和openId一致，和openId不能同时为空") @RequestParam(required = false) String externalId,
                                      @ApiParam(name = "dataFrom", value = "来源 tb、jd、pdd、wph、mt、elem") @RequestParam(required = false) String dataFrom) {
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
            Checks.isTrue(openId != null || externalId != null, "openId和externalId不能同时为空");

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

    @ApiOperation("自动建立开放ID和会员信息的关系，根据给定条件查询淘宝联盟的会员数据，并与本地数据库的数据做关联")
    @RequestMapping("/automap/special")
    public SafeServiceResponse create(@ApiParam(name = "specialId", value = "会员ID 同步自淘宝联盟") @RequestParam(required = false) String specialId,
                                      @ApiParam(name = "externalId", value = "外部ID 默认和openId一致，作为关联会员ID和openId的中间关系") @RequestParam(required = false) String externalId) {
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

    @ApiOperation("订单侠回调的接口 https://www.dingdanxia.com/doc/184/27")
    @RequestMapping("/callBackByDdx")
    public SafeServiceResponse publisherSaveExclusive(@ApiParam(name = "note", value = "生成连接时的唯一标识") @RequestParam(required = true) String note,
                                                      @ApiParam(name = "callback_data", value = "回调内容") @RequestParam(required = false) String callback_data) {
        try{
            SafeServiceResponse.startBiz();

            //直接读取内容
            logger.info("回调内容：");
            logger.info("note：" +note);
            logger.info("callback_data：" + callback_data);

            //返回
            return SafeServiceResponse.success(true);
        }catch(Exception e){
            logger.error("fail to create user[/tbk/user/callBackByDdx]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }

    @ApiOperation("订单侠回调的接口 https://www.dingdanxia.com/doc/184/27")
    @RequestMapping("/callBackByDdx2")
    public SafeServiceResponse publisherSaveExclusive2(HttpServletRequest httpServletRequest) {
        try{
            SafeServiceResponse.startBiz();

            Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                String value = httpServletRequest.getParameter(name);
                System.out.println("===>" + name + ": " + value);
            }

            //直接读取内容
            logger.info("回调内容,rushang");

            //返回
            return SafeServiceResponse.success(true);
        }catch(Exception e){
            logger.error("fail to create user[/tbk/user/callBackByDdx2]", e);
            return SafeServiceResponse.fail(e.toString());
        }
    }
}
