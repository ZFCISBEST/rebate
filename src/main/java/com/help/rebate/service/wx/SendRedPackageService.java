package com.help.rebate.service.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.commons.DdxConfig;
import com.help.rebate.commons.WxHttpService;
import com.help.rebate.utils.DecryptMD5;
import com.help.rebate.utils.MsgUtil;
import com.help.rebate.web.controller.wx.SendRedPack;
import com.help.rebate.web.controller.wx.SignatureController;
import com.help.rebate.web.controller.wx.Tool;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * 发送红包服务
 * @author zfcisbest
 * @date 2022/8/21
 */
@Service
public class SendRedPackageService {
    private static final Logger logger = LoggerFactory.getLogger(SendRedPackageService.class);


    /**
     * 发送红包服务
     * @param openID
     * @param moneyFen
     * @return
     * @throws JSONException
     */
    public String sendRedPack(String openID, Integer moneyFen) throws JSONException {
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

        try {
            String returnMsg = WxHttpService.httpsRequestPay2(SignatureController.send_wechat_red_envelop, soapRequestData);
            Map<String, String> resultMsg = MsgUtil.xmlToMap(returnMsg);
            JSONObject jsonObject = (JSONObject) JSON.parseObject(JSON.toJSONString(resultMsg));
            if (jsonObject.getString("return_code").equals("SUCCESS")) {
                if (jsonObject.getString("result_code").equals("SUCCESS")) {
                    return "红包发送成功|" + jsonObject.getString("mch_billno") + "|" + jsonObject.getString("mch_id");
                } else {
                    return "红包发送成功|" + jsonObject.getString("err_code_des") + "|" + jsonObject.getString("err_code");
                }
            } else if (jsonObject.getString("return_code").equals("FAIL")) {
                return "红包发送失败|" + jsonObject.getString("return_code") + "|" + jsonObject.getString("return_msg");
            }

            return "红包发送成功|" + jsonObject.getString("return_code") + "|" + jsonObject.getString("return_msg");
        } catch (Exception ex) {
            logger.error("发送红包失败", ex);
            return "发送红包失败|" + ex.toString();
        }
    }
}
