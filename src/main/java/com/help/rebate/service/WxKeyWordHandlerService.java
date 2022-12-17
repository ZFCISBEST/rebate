package com.help.rebate.service;

import com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapInfo;
import com.help.rebate.service.exception.ConvertException;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.vo.CommissionVO;
import com.help.rebate.vo.OrderBindResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private static String[] keywords = new String[]{"余额", "openId"};
    private static String[] keywordPrefixs = new String[]{"绑定订单:", "查询订单:"};

    /**
     * 内容是否是关键字
     * @param content 用户输入的内容
     */
    public boolean acceptKeyWord(String content) {
        if (EmptyUtils.isEmpty(content)) {
            return false;
        }

        //关键字
        Optional<String> first = Arrays.stream(keywords).filter(a -> a.equalsIgnoreCase(content)).findFirst();
        if (first.isPresent()) {
            return true;
        }

        //前缀部分的
        Optional<String> first1 = Arrays.stream(keywordPrefixs).filter(a -> content.startsWith(a)).findFirst();
        if (first1.isPresent()) {
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
        //确定的
        if ("余额".equals(content)) {
            return doHandleQueryYuE(fromUserName);
        }
        if ("openid".equalsIgnoreCase(content.toLowerCase())) {
            return fromUserName;
        }

        //前缀的
        if (content.startsWith("绑定订单:")) {
            content = content.replaceFirst("绑定订单:", "");

            String[] split = content.split(";");
            String tradeParentId = split[0];
            String specialId = null;
            if (split.length > 1) {
                specialId = split[1];
            }
            OrderBindResultVO orderBindResultVO = v2TaobaoOrderBindService.bindByTradeParentId(tradeParentId, fromUserName, specialId);
            if (orderBindResultVO != null && orderBindResultVO.getTradeIdItemIdList() != null) {
                return "共绑定商品数[" + orderBindResultVO.getTradeIdItemIdList().size() + "]";
            }
            else {
                return "共绑定商品数[0]";
            }
        }
        if (content.startsWith("查询订单:")) {
            content = content.replaceFirst("查询订单:", "");
            List<V2TaobaoOrderOpenidMapInfo> v2TaobaoOrderOpenidMapInfos = v2TaobaoOrderOpenidMapService.selectBindInfoByTradeParentIdAndOpenId(content, fromUserName);
            return "共查询到绑定的订单数[" + v2TaobaoOrderOpenidMapInfos.size() + "]";
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
        String message = "【当前账户余额】";
        message += "\t\n可提现:" + remainCommission;
        message += "\t\n待确认:" + futureCommission;

        //提示信息
        message += "\n========================";
        message += "\n1、手动绑定订单【绑定订单: 订单号】";
        message += "\n2、查询订单绑定【查询订单: 订单号】";
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
