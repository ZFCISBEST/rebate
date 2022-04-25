package com.help.rebate.service;

import com.help.rebate.dao.entity.OrderDetail;
import com.help.rebate.dao.entity.OrderOpenidMapFailure;
import com.help.rebate.dao.entity.OrderOpenidMapFailureExample;
import com.help.rebate.service.ddx.jd.DdxJDItemConverter;
import com.help.rebate.service.ddx.mt.DdxMeiTuanActivityConverter;
import com.help.rebate.service.ddx.pdd.DdxPddItemConverter;
import com.help.rebate.service.ddx.tb.DdxElemeActivityConverter;
import com.help.rebate.service.exception.ConvertException;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 微信的关键字处理服务
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Service
@Transactional
public class WxKeyWordHandlerService {
    private static final Logger logger = LoggerFactory.getLogger(WxKeyWordHandlerService.class);

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

    @Autowired
    private UserInfosService userInfosService;

    @Autowired
    private OrderBindService orderBindService;

    @Autowired
    private OrderOpenidMapService orderOpenidMapService;

    /**
     * keyword
     */
    private static String[] keywords = new String[]{"余额", "提现", "会员"};
    private static String[] keywordBegins = new String[]{"绑定:", "查询:", "返利:"};
    public static String tips = "回复如下指令：\n";
    static {
        tips += "1、[余额] 查看账户余额信息\n";
        tips += "2、[提现] 领取返利\n";
        tips += "3、[绑定:订单号] 执行订单手动绑定\n";
        tips += "4、[查询:订单号] 查询订单是否绑定成功\n";
        tips += "5、[返利:订单号] 查询订单返利详情\n";
        tips += "6、[会员] 获取绑定会员链接\n";
    }

    /**
     * 内容是否是关键字
     * @param content 用户输入的内容
     */
    public boolean acceptKeyWord(String content) {
        if (EmptyUtils.isEmpty(content)) {
            return false;
        }

        //关键字
        Optional<Boolean> first = Arrays.stream(keywords).map(a -> a.equals(content)).filter(a -> a).findFirst();
        if (first.isPresent()) {
            return true;
        }

        //起始关键字
        Optional<Boolean> optional = Arrays.stream(keywordBegins).map(a -> content.startsWith(a)).filter(a -> a).findFirst();
        return optional.isPresent();
    }

    /**
     * 处理关键字
     *
     * @param fromUserName
     * @param toUserName
     * @param content
     * @return
     */
    public String handleKeyWord(String fromUserName, String toUserName, String content) {
        if ("余额".equals(content)) {
            return doHandleYuE(fromUserName);
        }

        if ("提现".equals(content)) {
            return "暂不支持";
        }

        if ("会员".equals(content)) {
            return doHandleVip(fromUserName);
        }

        //绑定订单
        if (content.startsWith("绑定:")) {
            return doHandleBindTradeId(fromUserName, content.substring(content.indexOf(":") + 1));
        }

        //查询订单
        if (content.startsWith("查询:")) {
            return doHandleQueryTradeId(fromUserName, content.substring(content.indexOf(":") + 1));
        }

        //查询订单
        if (content.startsWith("返利:")) {
            return doHandleQueryCommissions(fromUserName, content.substring(content.indexOf(":") + 1));
        }

        return "暂不支持的指令";
    }

    /**
     * 处理返回成为会员的链接
     * @param fromUserName
     * @return
     */
    private String doHandleVip(String fromUserName) {
        return "待完成 - " + fromUserName;
    }

    /**
     * 查询余额
     * @param fromUserName
     * @return
     */
    private String doHandleYuE(String fromUserName) {
        return "待完成 - " + fromUserName;
    }

    /**
     * 手动绑定订单
     *
     * @param fromUserName
     * @param tradeParentId
     * @return
     */
    private String doHandleBindTradeId(String fromUserName, String tradeParentId) {
        return "待完成 - " + fromUserName;
    }

    /**
     * 查询某个订单是否绑定成功了
     *
     * @param fromUserName
     * @param tradeParentId
     * @return
     */
    private String doHandleQueryTradeId(String fromUserName, String tradeParentId) {
        return "待完成 - " + fromUserName;
    }

    /**
     * 查询某个订单的返利信息
     *
     * @param fromUserName
     * @param tradeParentId
     * @return
     */
    private String doHandleQueryCommissions(String fromUserName, String tradeParentId) {
        return "待完成 - " + fromUserName;
    }

    /**
     * 转链接处理
     * @param fromUserName
     * @param toUserName
     * @param content
     * @return
     */
    public String handleConvert(String fromUserName, String toUserName, String content) {
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
