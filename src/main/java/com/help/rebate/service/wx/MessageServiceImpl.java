package com.help.rebate.service.wx;

import com.alibaba.fastjson.JSON;
import com.help.rebate.service.TklConvertService;
import com.help.rebate.service.ddx.jd.DdxJDItemConverter;
import com.help.rebate.service.ddx.mt.DdxMeiTuanActivityConverter;
import com.help.rebate.service.ddx.pdd.DdxPddItemConverter;
import com.help.rebate.service.ddx.tb.DdxElemeActivityConverter;
import com.help.rebate.service.exception.ConvertException;
import com.help.rebate.utils.MsgUtil;
import com.help.rebate.vo.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 淘口令转链服务
     */
    @Autowired
    private TklConvertService tklConvertService;

    /**
     * 京东转链接
     */
    @Resource
    private DdxJDItemConverter ddxJDItemConverter;

    /**
     * 拼多多转链接
     */
    @Resource
    private DdxPddItemConverter ddxPddItemConverter;

    /**
     * 饿了么转链接
     */
    @Resource
    private DdxElemeActivityConverter ddxElemeActivityConverter;

    /**
     * 美团转链接
     */
    @Resource
    private DdxMeiTuanActivityConverter ddxMeiTuanActivityConverter;

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
                TextMessage text = new TextMessage();
                text.setContent("感谢您的关注，本服务号当前提供淘宝/京东/拼多多链接返利功能，将商品链接发送给服务号，使用服务号返回的链接购买商品，确认收货后可以通过平台提取优惠现金。");
                text.setToUserName(fromUserName);
                text.setFromUserName(toUserName);
                text.setCreateTime(new Date().getTime());
                text.setMsgType("text");
                replyMessage = MsgUtil.textMessageToXML(text);

                logger.info("returnOther=" + replyMessage);
            } else if (map.get("Event").equals("CLICK")) {
                String eventKey = map.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                if (eventKey.equals("V001_CHECK_MONEY")) {
                    TextMessage text = new TextMessage();
                    text.setContent("待开发查看余额功能");
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime());
                    text.setMsgType("text");

                    replyMessage = MsgUtil.textMessageToXML(text);

                    logger.info("returnOther="+replyMessage);
                } else if (eventKey.equals("V001_GET_MONEY")) {
                    // 订阅
                    TextMessage text = new TextMessage();
                    text.setContent("待开发领取红包功能。");
                    text.setToUserName(fromUserName);
                    text.setFromUserName(toUserName);
                    text.setCreateTime(new Date().getTime());
                    text.setMsgType("text");
                    replyMessage = MsgUtil.textMessageToXML(text);

                    logger.info("returnOther="+replyMessage);
                }
            }
        }
        else  if (msgType.equals("text")) {
            //这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
            String newContent = convert(fromUserName, toUserName, content);

            //自动回复
            TextMessage text = new TextMessage();
            text.setContent(newContent);
            text.setToUserName(fromUserName);
            text.setFromUserName(toUserName);
            text.setCreateTime(new Date().getTime());
            text.setMsgType(msgType);
            replyMessage = MsgUtil.textMessageToXML(text);

            logger.info("returnText = {}", replyMessage);
        } else {
            TextMessage text = new TextMessage();
            text.setContent("公众号无法提供相关服务。");
            text.setToUserName(fromUserName);
            text.setFromUserName(toUserName);
            text.setCreateTime(new Date().getTime());
            text.setMsgType("text");
            replyMessage = MsgUtil.textMessageToXML(text);
            logger.info("returnOther="+replyMessage);
        }

        return replyMessage;
    }

    /**
     * 转化
     * @param fromUserName
     * @param toUserName
     * @param content
     * @return
     */
    private String convert(String fromUserName, String toUserName, String content) {
        try {
            return doConvert(fromUserName, toUserName, content);
        } catch (Throwable ex) {
            //已知的错误
            if (ex instanceof ConvertException) {
                return ex.getMessage();
            }

            //未知错误
            logger.error("[MessageService] 转链失败，发生未知错误", ex);
            return "转链失败，未知错误，请稍后重试或换个商品再试哦";
        }
    }

    /**
     * 转化
     * @param fromUserName
     * @param toUserName
     * @param content
     * @return
     */
    private String doConvert(String fromUserName, String toUserName, String content) {
        //如果是京东的链接
        if (content.contains("jd.com")) {
            String materialId = content;
            String subUnionId = "wx_" + fromUserName;
            Long positionId = Long.valueOf(Math.abs(subUnionId.hashCode()));
            Double tempReturnRate = 0.9;
            DdxJDItemConverter.JDLinkDO jdLinkDO = ddxJDItemConverter.generateReturnPriceInfo(materialId, positionId, subUnionId, tempReturnRate);
            return jdLinkDO.getLinkInfo();
        }

        //解析为pdd的链接
        else if (content.contains("pdd") || content.contains("pinduoduo") || content.contains("yangkeduo")) {
            String url = content;
            String customId = "wx_" + fromUserName;
            Double tempReturnRate = 0.9;

            DdxPddItemConverter.PddLinkDO pddLinkDO = ddxPddItemConverter.generateReturnPriceInfo(url, customId, tempReturnRate);
            return pddLinkDO.getLinkInfo();
        }

        //解析为饿了么 - 外卖、活动、生鲜 - 这个暂时没法跟踪，只能订单绑定
        else if(content.contains("饿了么")) {
            String keyword = content;
            String link = ddxElemeActivityConverter.generateReturnPriceInfo(keyword);
            return link;
        }

        //解析为美团
        else if(content.contains("美团")) {
            String keyword = content;
            String link = ddxMeiTuanActivityConverter.generateReturnPriceInfo(keyword, fromUserName);
            return link;
        }

        //默认就是淘宝的链接
        else {
            //淘口令转链接
            String tkl = content;
            String openId = fromUserName;
            String tklType = "virtual";
            String newTkl = tklConvertService.convert(tkl, openId, null, tklType, "tb");
            return newTkl;
        }

    }
}
