package com.help.rebate.service.wx;

import com.help.rebate.utils.ShaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @Author: lst
 * @Date 2020-08-18
 */
@Service
public class WxCheckSignatureServiceImpl implements WxCheckSignatureService {

    @Value("${wx.token}")
    public String token;

    /**
     * @Description  进行签名认证
     * @author lst
     * @date 2020-8-20 10:49
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return java.lang.String
     */
    @Override
    public String checkSignature(String signature, String timestamp, String nonce, String echostr) {
        // 1.将token、timestamp、nonce三个参数进行字典序排序
       String tmpStr = ShaUtil.getSHA1(token,  timestamp,  nonce);
        //TODO 进行对比
        if (tmpStr.equals(signature.toUpperCase())) {
            return echostr;
        }

        String xml = "<xml>\n" +
                "  <ToUserName><![CDATA[crayzy1233211]]></ToUserName>\n" +
                "  <FromUserName></FromUserName>\n" +
                "  <CreateTime>12345678</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[你好]]></Content>\n" +
                "</xml>";
        return xml;
    }


}