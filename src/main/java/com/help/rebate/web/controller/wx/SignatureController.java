package com.help.rebate.web.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DdxConfig;
import com.help.rebate.commons.MyX509TrustManager;
import com.help.rebate.commons.WxHttpService;
import com.help.rebate.service.wx.MessageServiceImpl;
import com.help.rebate.service.wx.WXPayConfig;
import com.help.rebate.service.wx.WXPayUtil;
import com.help.rebate.service.wx.WxCheckSignatureService;
import com.help.rebate.utils.DecryptMD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.*;
import okhttp3.internal.platform.Platform;
import org.apache.http.ssl.SSLContexts;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Random;
import java.util.TreeMap;

@Api("微信对接接口")
@RestController
@RequestMapping("/wx")
public class SignatureController {
    private final static Logger logger = LoggerFactory.getLogger(SignatureController.class);
    // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public static String message_send_url_1 = " https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
    public static String message_send_url_2 = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
    public static String send_template_message = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    public static String send_wechat_red_envelop = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
    @Autowired
    private WxCheckSignatureService wxCheckSignatureService;
    @Autowired
    private MessageServiceImpl messageService;

    public static String accessToken() {
        String accessToken = null;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token"
                + "?grant_type=client_credential&appid=" + DdxConfig.APPID +
                "&secret=" + DdxConfig.APPSECRET;
        JSONObject jsonObject = WxHttpService.httpsRequest(requestUrl, "GET", null);
        accessToken = jsonObject.getString("access_token");
        return accessToken;
    }

    @ApiOperation("验证微信的Token")
    @RequestMapping(value = "/token.html", method = RequestMethod.GET)
    @ResponseBody
    public String verifyWXToken(HttpServletRequest request) {

        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        logger.info("get=" + msgSignature + ";" + msgTimestamp + ";" + msgNonce + ";" + echostr);

        return wxCheckSignatureService.checkSignature(msgSignature, msgTimestamp, msgNonce, echostr);

    }

    @ApiOperation("微信测试")
    @RequestMapping(value = "/test.html")
    @ResponseBody
    public String test() {
        logger.info("test");
        return "3月20日：欢迎来到躺平不卷平台，这是一个测试网页。双十二将推出淘宝返利活动。";
    }

    @ApiOperation("设置所属行业")
    @RequestMapping(value = "/setIndusty.html")
    @ResponseBody
    public String setIndusty() {
        String result = "设置所属行业成功";
        // 拼装创建菜单的url
        String url = message_send_url_1.replace("ACCESS_TOKEN", accessToken());
        // 将菜单对象转换成json字符串
        String message = "{\"industry_id1\": \"1\",\"industry_id2\": \"41\"}";
        // 调用接口创建菜单
        JSONObject jsonObject = WxHttpService.httpsRequest(url, "POST", message);

        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                return "设置所属行业失败" + jsonObject.getInteger("errcode") + jsonObject.getString("errmsg");
            }
        }
        return result;
    }

    @ApiOperation("设置所属行业")
    @RequestMapping(value = "/getIndusty.html")
    @ResponseBody
    public String getIndusty() {
        String result = "获取所属行业成功";
        // 拼装创建菜单的url
        String url = message_send_url_2.replace("ACCESS_TOKEN", accessToken());
        // 调用接口创建菜单
        JSONObject jsonObject = WxHttpService.httpsRequest(url, "POST", null);

        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                return "获取所属行业失败" + jsonObject.getInteger("errcode") + jsonObject.getString("errmsg");
            }
        }
        return result;
    }

    @ApiOperation("消息主动推送")
    @RequestMapping(value = "/sendMessage.html")
    @ResponseBody
    public String sendMessage(String type) {
        String result = "消息发送成功";
        String message = "";
        String url = send_template_message.replace("ACCESS_TOKEN", accessToken());

        if (type.equals("1")) {
            // JmMKlEul-uDdej2djWtZ9eHQA-cOCY9BorvfQJTw22E 下单成功
            message = "{\"touser\":\"odgJP50u7NosrfeTyZGpg45u_QS0\",\"template_id\":\"JmMKlEul-uDdej2djWtZ9eHQA-cOCY9BorvfQJTw22E\",\"data\":{\"first\": {\"value\":\"恭喜你下单成功！\",\"color\":\"#173177\"},\"keyword1\":{\"value\":\"2325353434\",\"color\":\"#173177\"},\"keyword2\": {\"value\":\"空中花园\",\"color\":\"#173177\"},\"keyword3\": {\"value\":\"￥100.00\",\"color\":\"#173177\"},\"keyword4\": {\"value\":\"优惠金额￥1.00\",\"color\":\"#173177\"},\"keyword5\": {\"value\":\"元亭担详公众号\",\"color\":\"#173177\"},\"remark\":{\"value\":\"收货可查看余额，满1元可提现优惠！\",\"color\":\"#173177\"}}}";
        } else if (type.equals("2")) {
            // pasGxBx_3wUQstLU2-efqjMO1rQX8gEJZdVzx3yr_As 订单收货
            message = "{\"touser\":\"odgJP50u7NosrfeTyZGpg45u_QS0\",\"template_id\":\"pasGxBx_3wUQstLU2-efqjMO1rQX8gEJZdVzx3yr_As\",\"data\": {\"first\": {\"value\":\"恭喜你已确认收货成功！\"},\"keyword1\":{\"value\":\"VIP/NotVIP\"},\"keyword2\":{\"value\": \"234235325989\"},\"keyword3\":{\"value\":\"￥300.00\"},\"keyword4\":{\"value\":\"优惠金额￥100.00\"},\"remark\":{\"value\": \"您可以查看余额获取当前总共优惠金额，满1元可提现！\"}}}";
        } else if (type.equals("3")) {
            // Es6pYTZ9k3NWDTc4BC7uIBPDq5WJwB1akDiSVJ3CVNU 交易到账
            message = "{\"touser\":\"odgJP50u7NosrfeTyZGpg45u_QS0\",\"template_id\":\"Es6pYTZ9k3NWDTc4BC7uIBPDq5WJwB1akDiSVJ3CVNU\",\"data\":{\"first\":{\"value\":\"恭喜你已领取现金红包！\",\"color\":\"#173177\"},\"keyword1\":{\"value\":\"2022-03-02\",\"color\":\"#173177\"},\"keyword2\":{\"value\":\"2022-03-02 15:02:04\",\"color\":\"#173177\"},\"keyword3\": {\"value\":\"￥100.00\",\"color\":\"#173177\"},\"keyword4\": {\"value\":\"已确认收货5笔优惠，还有2笔待收货\",\"color\": \"#173177\"},\"remark\":{\"value\":\"祝贺年年有余，一年更比一年旺！\"}}}";
        }

        JSONObject jsonObject = WxHttpService.httpsRequest(url, "POST", message);

        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                return "消息发送失败" + jsonObject.getInteger("errcode") + jsonObject.getString("errmsg");
            }
        }
        return result;
    }

    @ApiOperation("发送红包")
    @RequestMapping(value = "/sendMoney.html")
    @ResponseBody
    public String sendMoney(String openID, Integer moneyFen) throws JSONException {
        String result = "红包发送成功";

        //具体参数查看具体实体类，实体类中的的参数参考微信的红包发放接口，这里你直接用map，进行设置参数也可以。。。
        SendRedPack sendRedPack = new SendRedPack(
                WXPayUtil.generateNonceStr(),
                Tool.getOrderNum(),
                DecryptMD5.IdOpen(DdxConfig.ID),
                DdxConfig.APPID,
                DdxConfig.SENDNAME,
                openID,
                moneyFen,
                DdxConfig.TOTALNUMBER,
                DdxConfig.WISHING,
                DdxConfig.IP,
                DdxConfig.ACTNAME,
                DdxConfig.REMARK,
                DdxConfig.SCENEID
        );


        //将实体类转换为url形式
        String urlParamsByMap = Tool.getUrlParamsByMap(Tool.toMap(sendRedPack));
        //拼接我们再前期准备好的API密钥，前期准备第5条
        String key = DecryptMD5.KeyOpen(DdxConfig.KEY);
        urlParamsByMap += "&key=" + key;
        //进行签名，需要说明的是，如果内容包含中文的话，要使用utf-8进行md5签名，不然会签名错误
        String sign = Tool.parseStrToMd5L32(urlParamsByMap).toUpperCase();
        sendRedPack.setSign(sign);
        //微信要求按照参数名ASCII字典序排序，这里巧用treeMap进行字典排序
        TreeMap treeMap = new TreeMap(Tool.toMap(sendRedPack));
        //然后转换成xml格式
        String soapRequestData = Tool.getSoapRequestData(treeMap);
        JSONObject jsonObject = WxHttpService.httpsRequestPay(send_wechat_red_envelop, soapRequestData);


        if (jsonObject.getString("return_code").equals("SUCCESS")) {
            if (jsonObject.getString("result_code").equals("SUCCESS")) {
                return "红包发送成功" + jsonObject.getString("mch_billno") + jsonObject.getString("mch_id");
            } else {
                return "红包发送成功" + jsonObject.getString("err_code_des") + jsonObject.getString("err_code");
            }
        } else if (jsonObject.getString("return_code").equals("FAIL")) {
            return "红包发送成功" + jsonObject.getString("return_code") + jsonObject.getString("return_msg");
        }

        return "红包发送成功" + jsonObject.getString("return_code") + jsonObject.getString("return_msg");
    }


    @ApiOperation("微信创建菜单")
    @RequestMapping(value = "/createMenu.html")
    @ResponseBody
    public String createMenu() {
        String result = "创建菜单成功";

        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken());
        // 将菜单对象转换成json字符串
        String menu = "{\"button\": [{\"type\": \"click\",\"name\": \"绑定会员\",\"key\": \"V001_VIP\",\"sub_button\": []},{\"type\": \"click\",\"name\": \"查看余额\",\"key\": \"V001_CHECK_MONEY\",\"sub_button\": []},{\"type\": \"click\",\"name\": \"提取现金\",\"key\": \"V001_GET_MONEY\",\"sub_button\": []}]}";
        // 调用接口创建菜单
        JSONObject jsonObject = WxHttpService.httpsRequest(url, "POST", menu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInteger("errcode")) {
                return "创建菜单失败" + jsonObject.getInteger("errcode") + jsonObject.getString("errmsg");
            }
        }
        return result;
    }

    @ApiOperation("微信TEXT消息回复")
    @RequestMapping(value = "/token.html", method = RequestMethod.POST)
    @ResponseBody
    public String reply(HttpServletRequest request) {
        logger.info("relyPost=" + request.getParameter("signature") + ";");

        return messageService.newMessageRequest(request);
    }


    @ApiOperation("淘口令转链消息回复")
    @RequestMapping(value = "/tkl/token.html", method = RequestMethod.POST)
    @ResponseBody
    public String replyTkl(HttpServletRequest request) {
        logger.info("relyPost=" + request.getParameter("signature") + ";");

        return messageService.newMessageRequest(request);
    }
}

