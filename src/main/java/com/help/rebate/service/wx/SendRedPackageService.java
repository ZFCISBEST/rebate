package com.help.rebate.service.wx;

import com.alibaba.fastjson.JSON;
import com.help.rebate.commons.DdxConfig;
import com.help.rebate.commons.WxHttpService;
import com.help.rebate.utils.DecryptMD5;
import com.help.rebate.utils.MsgUtil;
import com.help.rebate.web.controller.wx.SendRedPack;
import com.help.rebate.web.controller.wx.SignatureController;
import com.help.rebate.web.controller.wx.Tool;
import lombok.Data;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
     * <xml>
     *  <return_code><![CDATA[SUCCESS]]></return_code>
     *  <return_msg><![CDATA[发放成功]]></return_msg>
     *  <result_code><![CDATA[SUCCESS]]></result_code>
     *  <err_code><![CDATA[SUCCESS]]></err_code>
     *  <err_code_des><![CDATA[发放成功]]></err_code_des>
     *  <mch_billno><![CDATA[16610964586166018]]></mch_billno>
     *  <mch_id><![CDATA[1617446954]]></mch_id>
     *  <wxappid><![CDATA[wx9f4ab53be3e5e226]]></wxappid>
     *  <re_openid><![CDATA[odgJP5w-s6pRav3pIcIeB1urmqX8]]></re_openid>
     *  <total_amount>200</total_amount>
     * <send_listid><![CDATA[1000041701202208213033416589419]]></send_listid>
     * </xml>
     * @param openID
     * @param moneyFen
     * @return
     * @throws JSONException
     */
    public String sendRedPack(String openID, Integer moneyFen) throws JSONException {
        try{
            SendPackReturnMsgWrapper returnMsgWrapper = sendRedPack2(openID, moneyFen);

            //返回判定结果
            if (returnMsgWrapper.judgeSuccessful()) {
                return "红包发送成功";
            }

            //返回错误结果
            return returnMsgWrapper.getMsg();
        }
        catch(Exception ex){
            return "红包发送异常:" + ex;
        }
    }

    /**
     * 发送红包，返回原始信息
     * @param openID
     * @param moneyFen
     * @return
     * @throws JSONException
     */
    public SendPackReturnMsgWrapper sendRedPack2(String openID, Integer moneyFen) throws JSONException {
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
            SendPackReturnMsgWrapper returnMsgWrapper = wrapReturnMsg(returnMsg);
            return returnMsgWrapper;
        } catch (Exception ex) {
            throw new RuntimeException("红包发送异常", ex);
        }
    }

    /**
     * 封装返回数据
     * @param returnMsg
     * @return
     * @throws IOException
     */
    private SendPackReturnMsgWrapper wrapReturnMsg(String returnMsg) throws IOException {
        Map<String, String> resultMsg = MsgUtil.xmlToMap(returnMsg);

        //序列化并反序列化
        SendPackReturnMsgWrapper returnMsgWrapper = JSON.parseObject(JSON.toJSONString(resultMsg), SendPackReturnMsgWrapper.class);

        return returnMsgWrapper;
    }

    @Data
    public static class SendPackReturnMsgWrapper {
        private String return_code;
        private String return_msg;

        private String result_code;
        private String error_code;
        private String error_code_des;

        private String mch_billno;
        private String mch_id;

        private String wxappid;
        private String re_openid;

        private String send_list_id;

        private Integer total_amount;

        /**
         * 判定是否成功
         * @return
         */
        public boolean judgeSuccessful() {
            return "SUCCESS".equalsIgnoreCase(return_code) && "SUCCESS".equalsIgnoreCase(result_code);
        }

        /**
         * 获取信息
         * @return
         */
        public String getMsg() {
            return error_code + "[" + error_code_des + "]";
        }
    }
}
