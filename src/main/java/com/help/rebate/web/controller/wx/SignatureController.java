package com.help.rebate.web.controller.wx;

import com.help.rebate.service.wx.MessageServiceImpl;
import com.help.rebate.service.wx.WxCheckSignatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api("微信对接接口")
@RestController
@RequestMapping("/wx")
public class SignatureController {
    private final static Logger logger = LoggerFactory.getLogger(SignatureController.class);
    @Autowired
    private WxCheckSignatureService wxCheckSignatureService;
    @Autowired
    private MessageServiceImpl messageService;

    @ApiOperation("验证微信的Token")
    @RequestMapping(value = "/token.html",method = RequestMethod.GET)
    @ResponseBody
    public String verifyWXToken(HttpServletRequest request) {

        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        logger.info("get="+msgSignature+";"+msgTimestamp+";"+msgNonce+";"+echostr);

        return  wxCheckSignatureService.checkSignature(msgSignature, msgTimestamp, msgNonce,echostr);

    }

    @ApiOperation("微信测试")
    @RequestMapping(value = "/test.html")
    @ResponseBody
    public String test() {
        logger.info("test");
        return "13 欢迎来到躺平不卷平台，这是一个测试网页。双十二将推出淘宝返利活动。";
    }

    @ApiOperation("微信TEXT消息回复")
    @RequestMapping(value = "/token.html",method = RequestMethod.POST)
    @ResponseBody
    public String reply(HttpServletRequest request) {
        logger.info("relyPost="+request.getParameter("signature")+";");

        return messageService.newMessageRequest(request);
    }



    @ApiOperation("淘口令转链消息回复")
    @RequestMapping(value = "/tkl/token.html",method = RequestMethod.POST)
    @ResponseBody
    public String replyTkl(HttpServletRequest request) {
        logger.info("relyPost="+request.getParameter("signature")+";");

        return messageService.newMessageRequest(request);
    }
}
