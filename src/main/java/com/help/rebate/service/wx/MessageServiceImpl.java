package com.help.rebate.service.wx;

import com.alibaba.fastjson.JSON;
import com.help.rebate.service.V2TaobaoCommissionAccountService;
import com.help.rebate.service.WxKeyWordHandlerService;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.MsgUtil;
import com.help.rebate.vo.WeChartTextMessage;
import com.help.rebate.vo.WeChatImageMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    private final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    /**
     * 微信关键字处理
     */
    @Resource
    private WxKeyWordHandlerService wxKeyWordHandlerService;

    /**
     * 发红包服务
     */
    @Resource
    private SendRedPackageService sendRedPackageService;

    /**
     * 账户服务
     */
    @Resource
    private V2TaobaoCommissionAccountService v2TaobaoCommissionAccountService;

    @Override
    public String newMessageRequest(HttpServletRequest request) {
        String replyMessage = null;

        Map<String,String> map = new HashMap<>();
        //logger.info("request="+request.toString());

        try {
            map = MsgUtil.xmlToMap(request);
            logger.info("request = {}", JSON.toJSONString(map, true));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 发送方帐号（open_id）
        String fromUserName = map.get("FromUserName");
        // 公众帐号
        String toUserName = map.get("ToUserName");
        // 消息类型
        String msgType = map.get("MsgType");
        // 消息内容
        String content = map.get("Content");

        if(msgType.equals("event")) {
            if(map.get("Event").equals("subscribe")) {
                // 订阅
                replyMessage = wrapReturnMsg(fromUserName, toUserName, "本公众号致力于给大家薅各种羊毛，参与福利活动，做你的贴心省钱/赚钱小管家。目前有开户即送微信红包活动，点击“个人养老开户”即可参与，不仅送现金红包，还能参与抽奖送最新款iPhone！！！", "text");

                logger.info("returnOther=" + replyMessage);
            } else if (map.get("Event").equals("CLICK")) {
                String eventKey = map.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                if (eventKey.equals("V001_CHECK_MONEY")) {
                    //查看余额
                    WeChartTextMessage text = new WeChartTextMessage();
                    text.setContent(wxKeyWordHandlerService.handleKeyWord(fromUserName, toUserName, "余额"));
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime());
                    text.setMsgType("text");

                    replyMessage = MsgUtil.textMessageToXML(text);
                    logger.info("returnOther="+replyMessage);
                }else if (eventKey.equals("V001_VIP")) {
                    //回复图片
                    WeChatImageMessage image = new WeChatImageMessage();
                    image.setToUserName(fromUserName);
                    image.setFromUserName(toUserName);
                    image.setCreateTime(new Date().getTime());
                    image.setMsgType("image");
                    image.setMediaId("AKG7FG_cwz3uqX_DwlPk_xrxyMtvkLlFXRor99JEY4DLUzSHLuADW653UXQR0ODP");

                    replyMessage = String.format(
                            "<xml>" +
                                    "<ToUserName><![CDATA[%s]]></ToUserName>" +
                                    "<FromUserName><![CDATA[%s]]></FromUserName>" +
                                    "<CreateTime>%s</CreateTime>" +
                                    "<MsgType><![CDATA[image]]></MsgType>" +
                                    "<Image>" +
                                    " <MediaId><![CDATA[%s]]></MediaId>" +
                                    "</Image>" +
                                    "</xml>",
                            fromUserName,toUserName, new Date().getTime(),"AKG7FG_cwz3uqX_DwlPk_xrxyMtvkLlFXRor99JEY4DLUzSHLuADW653UXQR0ODP"
                    );
                    logger.info("returnOther="+replyMessage);
                } else if (eventKey.equals("V001_GET_MONEY")) {
                    /*boolean sendRedPackFlag = detectSendRedPack(fromUserName);
                    if (!sendRedPackFlag) {
                        //默认的回复
                        replyMessage = wrapReturnMsg(fromUserName, toUserName, "待开发领取红包功能。", "text");
                        return replyMessage;
                    }*/

                    //触发发红包
                    replyMessage = triggerSendConpon(fromUserName, toUserName);
                }
            }
        } else if (msgType.equals("text")) {
            String returnContent;
            // 是关键字
            if (wxKeyWordHandlerService.acceptKeyWord(content)) {
                returnContent = wxKeyWordHandlerService.handleKeyWord(fromUserName, toUserName, content);
            }
            else {
                //这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
                returnContent = wxKeyWordHandlerService.handleConvert(fromUserName, toUserName, content);
            }

            //自动回复
            replyMessage = wrapReturnMsg(fromUserName, toUserName, returnContent, msgType);
            logger.info("returnText = {}", replyMessage);
        } else if (msgType.equals("image")) {
            // 图片信息
            String PicUrl = map.get("PicUrl");
            replyMessage = wrapReturnMsg(fromUserName, toUserName, "您发送的图片若为个人养老开户成功信息，公众号将在2-3个工作日内核实，核实成功自动发送现金红包！！！其它信息目前公众号无法提供服务。", "text");
            logger.info("returnOther=" + replyMessage);
        }else {
            replyMessage = wrapReturnMsg(fromUserName, toUserName, "公众号无法提供相关服务。", "text");
            logger.info("returnOther=" + replyMessage);
        }

        return replyMessage;
    }

    /**
     * 内测用户可以提取
     * @param fromUserName
     * @return
     */
    private boolean detectSendRedPack(String fromUserName) {
        return fromUserName.toLowerCase().startsWith("odgjp5w-s6prav3p");
    }

    /**
     * 封装返回消息
     * @param fromUserName
     * @param toUserName
     * @param content
     * @param msgType
     * @return
     */
    private String wrapReturnMsg(String fromUserName, String toUserName, String content, String msgType) {
        String replyMessage;
        WeChartTextMessage text = new WeChartTextMessage();
        text.setContent(content);
        text.setToUserName(fromUserName);
        text.setFromUserName(toUserName);
        text.setCreateTime(new Date().getTime());
        text.setMsgType(msgType);
        replyMessage = MsgUtil.textMessageToXML(text);
        return replyMessage;
    }

    /**
     * 触发发红包
     * 发个红包，每次只能1000分
     * @param fromUserName
     * @param toUserName
     * @return
     */
    private String triggerSendConpon(String fromUserName, String toUserName) {
        String replyMessage;

        try {
            //触发提现, 返回提现的钱（分）
            int withdrawalAmount = v2TaobaoCommissionAccountService.getWithdrawalAmount();
            Checks.isTrue(withdrawalAmount > 0, "提现金额不能为0");

            //先扣款
            long stubId = v2TaobaoCommissionAccountService.triggerWithdrawal(fromUserName, withdrawalAmount);

            //实际发红包
            SendRedPackageService.SendPackReturnMsgWrapper returnMsgWrapper = sendRedPackageService.sendRedPack2(fromUserName, withdrawalAmount);
            String msg = JSON.toJSONString(returnMsgWrapper);
            if (returnMsgWrapper.judgeSuccessful()) {
                replyMessage = wrapReturnMsg(fromUserName, toUserName, "红包已发出，请领取(24小时有效)", "text");
                logger.info("发送红包给[{}]成功: {}", fromUserName, msg);

                //准备触发提现回退操作
                v2TaobaoCommissionAccountService.postTriggerWithdrawal(fromUserName, withdrawalAmount, true, returnMsgWrapper.getReturn_msg(), stubId);
            }
            else {
                replyMessage = wrapReturnMsg(fromUserName, toUserName, "红包发送失败，请稍后重试", "text");
                logger.info("发送红包给[{}]失败: {}", fromUserName, msg);

                //准备触发提现回退操作
                v2TaobaoCommissionAccountService.postTriggerWithdrawal(fromUserName, withdrawalAmount, false, returnMsgWrapper.getReturn_msg(), stubId);
            }
        } catch (Exception e) {
            logger.info("发送红包给[{}]失败", fromUserName, e);
            replyMessage = wrapReturnMsg(fromUserName, toUserName, "红包发送失败[" + e.getMessage() + "]", "text");
        }
        return replyMessage;
    }
}
