package com.help.rebate.service;

import com.help.rebate.service.exception.ConvertException;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.vo.CommissionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
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
     * 用户信息服务
     */
    @Resource
    private V2TaobaoUserInfoService v2TaobaoUserInfoService;

    /**
     * 淘口令转链服务
     */
    @Resource
    private V2TaobaoTklConvertService v2TaobaoTklConvertService;

    @Resource
    private V2TaobaoOrderBindService v2TaobaoOrderBindService;

    @Resource
    private V2TaobaoOrderOpenidMapService v2TaobaoOrderOpenidMapService;

    @Resource
    private V2TaobaoOrderService v2TaobaoOrderService;

    @Resource
    private V2TaobaoCommissionAccountService v2TaobaoCommissionAccountService;

    /**
     * keyword
     */
    private static String[] keywords = new String[]{"余额", "提现", "openId"};

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
        return false;
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
            return doHandleQueryYuE(fromUserName);
        }

        if ("提现".equals(content)) {
            return "暂不支持";
        }

        if ("openid".equals(content.toLowerCase())) {
            return "openId - " + fromUserName;
        }

        return "暂不支持的指令";
    }

    /**
     * 查询余额
     * @param fromUserName
     * @return
     */
    private String doHandleQueryYuE(String fromUserName) {
        //账户数据
        CommissionVO commissionVO = v2TaobaoCommissionAccountService.selectCommissionBy(fromUserName);

        //可提现 + 尚不可提现
        String remainCommission = commissionVO.getRemainCommission();
        String futureCommission = commissionVO.getFutureCommission();

        //结果
        String message = "当前账户余额:\n";
        message += "\t可提现:" + remainCommission;
        message += "\t待确认:" + futureCommission;
        return message;
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
        //淘口令转链接
        String tkl = content;
        String openId = fromUserName;
        String pubSiteType = "virtual";
        String newTkl = v2TaobaoTklConvertService.convert(tkl, openId, pubSiteType);
        return newTkl;
    }
}
