package com.help.rebate.service.wx;

import com.alibaba.fastjson.JSON;
import com.help.rebate.service.WxKeyWordHandlerService;
import com.help.rebate.utils.MsgUtil;
import com.help.rebate.vo.TextMessage;
import org.json.JSONException;
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
                replyMessage = wrapReturnMsg(fromUserName, toUserName, "感谢您的关注，本服务号当前提供淘宝/京东/拼多多链接返利功能，将商品链接发送给服务号，使用服务号返回的链接购买商品，确认收货后可以通过平台提取优惠现金。", "text");

                logger.info("returnOther=" + replyMessage);
            } else if (map.get("Event").equals("CLICK")) {
                String eventKey = map.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                if (eventKey.equals("V001_CHECK_MONEY")) {
                    //查看余额
                    TextMessage text = new TextMessage();
                    //String tips = "待开发查看余额功能";
                    String tips = WxKeyWordHandlerService.tips;
                    text.setContent(wxKeyWordHandlerService.handleKeyWord(fromUserName, toUserName, "余额") + "\n"+tips);
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime());
                    text.setMsgType("text");

                    replyMessage = MsgUtil.textMessageToXML(text);

                    logger.info("returnOther="+replyMessage);
                } else if (eventKey.equals("V001_GET_MONEY")) {
                    boolean sendRedPackFlag = detectSendRedPack(fromUserName);
                    if (!sendRedPackFlag) {
                        //默认的回复
                        replyMessage = wrapReturnMsg(fromUserName, toUserName, "待开发领取红包功能。", "text");
                        return replyMessage;
                    }

                    //发个红包试试
                    try {
                        SendRedPackageService.SendPackReturnMsgWrapper returnMsgWrapper = sendRedPackageService.sendRedPack2(fromUserName, 200);
                        if (returnMsgWrapper.judgeSuccessful()) {
                            replyMessage = wrapReturnMsg(fromUserName, toUserName, "红包已发出，请领取(24小时有效)", "text");
                            logger.info("发送红包给[{}]成功: {}", fromUserName, JSON.toJSONString(returnMsgWrapper));
                        }
                        else {
                            replyMessage = wrapReturnMsg(fromUserName, toUserName, "红包发送失败，请稍后重试", "text");
                            logger.info("发送红包给[{}]失败: {}", fromUserName, JSON.toJSONString(returnMsgWrapper));
                        }
                    } catch (Exception e) {
                        logger.info("发送红包给[{}]失败", fromUserName, e);
                        replyMessage = wrapReturnMsg(fromUserName, toUserName, "红包发送失败，请稍后重试", "text");
                    }
                } else if (eventKey.equals("V001_VIP")) {
                    // 绑定会员
                    replyMessage = wrapReturnMsg(fromUserName, toUserName, wxKeyWordHandlerService.handleKeyWord(fromUserName, toUserName, "会员"), "text");

                    logger.info("returnOther="+replyMessage);
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
        } else {
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
        TextMessage text = new TextMessage();
        text.setContent(content);
        text.setToUserName(fromUserName);
        text.setFromUserName(toUserName);
        text.setCreateTime(new Date().getTime());
        text.setMsgType(msgType);
        replyMessage = MsgUtil.textMessageToXML(text);
        return replyMessage;
    }
}
