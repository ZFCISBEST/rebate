package com.help.rebate.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页
 * @author zfcisbest
 * @date 22/12/03
 */
@Api("主页")
@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @ApiOperation("主页")
    @RequestMapping({"/", "/ylj", "/ylj/**"})
    public String homePage() {
        return "index";
    }
}
