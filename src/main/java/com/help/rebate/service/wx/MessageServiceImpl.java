package com.help.rebate.service.wx;

import com.help.rebate.service.TklConvertService;
import com.help.rebate.utils.MsgUtil;
import com.help.rebate.vo.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    private final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    /**
     * 淘口令转链服务
     */
    @Autowired
    private TklConvertService tklConvertService;

    @Override
    public String newMessageRequest(HttpServletRequest request) {
        String ReplyMessage = null;

        Map<String,String> map = new HashMap<>();
        logger.info("request="+request.toString());


        try {
            map = MsgUtil.xmlToMap(request);
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

        logger.info(fromUserName+";"+toUserName+";"+msgType+";"+content);

        if (msgType.equals("text")) {
            //这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
            /*if(content.equals("xxx")){
             }*/

            //淘口令转链接
            String tkl = content;
            String openId = fromUserName;
            String tklType = "virtual";
            String newTkl = tklConvertService.convert(tkl, openId, null, tklType);

            //自动回复
            TextMessage text = new TextMessage();
            text.setContent(newTkl);
            text.setToUserName(fromUserName);
            text.setFromUserName(toUserName);
            text.setCreateTime(new Date().getTime());
            text.setMsgType(msgType);
            ReplyMessage = MsgUtil.textMessageToXML(text);

            logger.info("returnText="+ReplyMessage);
        } else {
            TextMessage text = new TextMessage();
            text.setContent("回复：该消息非文本格式；暂无自动回复能力。");
            text.setToUserName(fromUserName);
            text.setFromUserName(toUserName);
            text.setCreateTime(new Date().getTime());
            text.setMsgType("text");
            ReplyMessage = MsgUtil.textMessageToXML(text);

            logger.info("returnOther="+ReplyMessage);
        }

        return ReplyMessage;
    }
}
