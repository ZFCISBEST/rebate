package com.help.rebate.service;

import com.help.rebate.dao.OrderDetailDao;
import com.help.rebate.dao.entity.*;
import com.help.rebate.service.ddx.jd.DdxJDItemConverter;
import com.help.rebate.service.ddx.mt.DdxMeiTuanActivityConverter;
import com.help.rebate.service.ddx.pdd.DdxPddItemConverter;
import com.help.rebate.service.ddx.tb.DdxElemeActivityConverter;
import com.help.rebate.service.exception.ConvertException;
import com.help.rebate.utils.Checks;
import com.help.rebate.utils.EmptyUtils;
import com.help.rebate.utils.NumberUtil;
import com.help.rebate.vo.CommissionVO;
import com.help.rebate.vo.OrderBindResultVO;
import com.help.rebate.vo.PickCommissionVO;
import com.help.rebate.web.response.SafeServiceResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private UserInfosService userInfosService;

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
    private OrderBindService orderBindService;

    @Autowired
    private OrderOpenidMapService orderOpenidMapService;

    @Autowired
    private OrderService orderService;

    /**
     * keyword
     */
    private static String[] keywords = new String[]{"余额", "提现", "会员", "提示", "openId"};
    private static String[] keywordBegins = new String[]{"绑定:", "查询:", "返利:", "区间余额:", "模拟:", "会员查询:", "会员绑定:"};
    public static String tips = "如有订单疑惑，回复如下指令查询：\n";
    public static String innerTips = "完整指令：\n";
    static {
//        tips += doHandleYuE(fromUserName)+"\n";
//        tips += "2、[提现] 领取返利\n";
        tips += "1、[绑定:订单号] 执行订单手动绑定\n";
        tips += "2、[查询:订单号] 查询订单是否绑定成功\n";
        tips += "3、[返利:订单号] 查询订单返利详情\n";
//        tips += "6、[会员] 获取绑定会员链接\n";

        int index = 0;
        innerTips += ++index + "、[余额、提现、会员、提示、openId]\n" ;
        innerTips += ++index + "、绑定:openId;specialId;tradeParentId - 执行订单手动绑定\n" ;
        innerTips += ++index + "、查询:openId;specialId;tradeParentId - 查询订单是否绑定成功\n" ;
        innerTips += ++index + "、返利:genericType;tradeParentId - 查询订单返利详情\n" ;
        innerTips += ++index + "、区间余额:openId;specialId;startTime;endTime - 查询区间的余额\n" ;
        innerTips += ++index + "、模拟:openId;specialId;mockStatus;startTime;endTime - 模拟提现的状态\n" ;
        innerTips += ++index + "、会员查询:keyword(1-openid,2-specialid,3-externalid);value - 查询会员信息\n" ;
        innerTips += ++index + "、会员绑定:1-openid:val;2-specialid:val;3-externalid:val;4-force:true/false - 执行绑定，以及是否强制绑定，解除其他绑定\n" ;

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

        if ("提示".equals(content)) {
            return innerTips;
        }

        if ("openid".equals(content.toLowerCase())) {
            return "openId - " + fromUserName;
        }

        //绑定订单
        if (content.startsWith("绑定:")) {
            return doHandleBindTradeId(fromUserName, content.split(":", 2)[1]);
        }

        //查询订单
        if (content.startsWith("查询:")) {
            return doHandleQueryTradeId(fromUserName, content.split(":", 2)[1]);
        }

        //查询订单
        if (content.startsWith("返利:")) {
            return doHandleQueryCommissions(fromUserName, content.split(":", 2)[1]);
        }

        //其他状态 - 区间余额
        if (content.startsWith("区间余额:")) {
            return doHandleRangeYuE(fromUserName, content.split(":", 2)[1]);
        }

        //其他状态 - 模拟，触发提取、提取成功，提取失败, 提取超时
        if (content.startsWith("模拟:")) {
            return doHandleTriggerPickMoney(fromUserName, content.split(":", 2)[1]);
        }

        //其他状态 - 会员查询
        if (content.startsWith("会员查询:")) {
            return doHandleVipQuery(fromUserName, content.split(":", 2)[1]);
        }

        //其他状态 - 会员绑定
        if (content.startsWith("会员绑定:")) {
            return doHandleVipBind(fromUserName, content.split(":", 2)[1]);
        }

        return "暂不支持的指令";
    }

    /**
     * 会员绑定 1-openid:val;2-specialid:val;3-externalid:val;4-force:true/false - 执行绑定，以及是否强制绑定，解除其他绑定
     * @param fromUserName
     * @param content
     * @return
     */
    private String doHandleVipBind(String fromUserName, String content) {
        //管理员操作
        Map<String, String> keyValMap = Arrays.stream(content.split(";"))
                .map(a -> a.trim().split(":"))
                .filter(a -> a.length == 2 && StringUtils.isNotEmpty(a[0]) && StringUtils.isNotEmpty(a[1]))
                .collect(Collectors.toMap(a -> a[0].trim().toLowerCase(), a -> a[1].trim(), (a, b) -> a));

        //关键字
        String openId = keyValMap.get("1") == null ? keyValMap.get("openid") : keyValMap.get("1");
        String specialId = keyValMap.get("2") == null ? keyValMap.get("specialid") : keyValMap.get("2");
        String externalId = keyValMap.get("3") == null ? keyValMap.get("externalid") : keyValMap.get("3");
        String forceFlag = keyValMap.get("4") == null ? keyValMap.get("force") : keyValMap.get("4");

        //openid不能为空
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(specialId)) {
            return "绑定失败-[openId, specialId]必须提供";
        }

        //

        return "稍后支持";
    }

    /**
     * 会员查询 keyword(1-openid,2-specialid,3-externalid);value - 查询会员信息
     * @param fromUserName
     * @param content
     * @return
     */
    private String doHandleVipQuery(String fromUserName, String content) {
        //管理员操作
        String[] keywordAndVal = content.split(";");

        //值
        String value = keywordAndVal[keywordAndVal.length - 1];

        //关键字
        String openId = null;
        String specialId = null;
        String externalId = null;
        String keyword = null;
        keyword = keywordAndVal[keywordAndVal.length - 2].toLowerCase();
        if (keyword.startsWith("1") || keyword.contains("openid")) {
            openId = value;
        }
        else if (keyword.startsWith("2") || keyword.contains("specialid")) {
            specialId = value;
        }
        else {
            externalId = value;
        }

        //查询
        UserInfos userInfos = userInfosService.selectByOpenIdAndSpecialIdAndExternalId(openId, specialId, externalId);
        if (userInfos == null) {
            return "不存在该用户信息";
        }
        else {
            String result = "openId: " + userInfos.getOpenId() + "\n";
            result += "specialId: " + userInfos.getSpecialId() + "\n";
            result += "externalId: " + userInfos.getExternalId() + "\n";
            return result;
        }
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
        //表示，期限不限，全部查出来
        return doHandleYuE(fromUserName, null, null, null);
    }

    /**
     * 查询余额
     * @param fromUserName
     * @return
     */
    private String doHandleYuE(String fromUserName, String specialId, String payStartTime, String payEndTime) {
        //orderStatuss 订单状态 - 12-付款，13-关闭，14-确认收货，3-结算成功
        //commissionStatuss 给用户的结算状态 - 待提取、提取中，提取成功，提取失败, 提取超时
        String message = "当前账户余额:\n";

        //查询 - 付款
        int index = 1;
        CommissionVO commissionByFuKuan = orderOpenidMapService.selectCommissionBy(fromUserName, specialId, "12", "待提取", payStartTime, payEndTime);
        if (commissionByFuKuan != null && commissionByFuKuan.getPubFee() != null) {
            message += index + "、已付款待确认: ￥" + commissionByFuKuan.getPubFee() + "元\n";
            index++;
        }

        //查询 - 确认收货
        CommissionVO commissionByQuRen = orderOpenidMapService.selectCommissionBy(fromUserName, specialId, "14", "待提取",  payStartTime, payEndTime);
        if (commissionByQuRen != null && commissionByQuRen.getPubFee() != null) {
            message += index + "、已确认待结算: ￥" + commissionByQuRen.getPubFee() + "元\n";
            index++;
        }

        //查询 - 结算成功
        CommissionVO commissionByJieSuan = orderOpenidMapService.selectCommissionBy(fromUserName, specialId, "3", "待提取,提取失败,提取超时",  payStartTime, payEndTime);
        if (commissionByJieSuan != null && commissionByJieSuan.getPubFee() != null) {
            message += index + "、已结算可提现: ￥" + commissionByJieSuan.getPubFee() + "元\n";
            index++;
        }

        return message;
    }

    /**
     * 手动绑定订单 openId;specialId;tradeParentId
     *
     * @param fromUserName
     * @param tradeParentIdContent
     * @return
     */
    private String doHandleBindTradeId(String fromUserName, String tradeParentIdContent) {
        //管理员操作
        String[] openidAndSpecialIdAndTradeId = tradeParentIdContent.split(";");

        //单号必须要有
        String tradeParentId = openidAndSpecialIdAndTradeId[openidAndSpecialIdAndTradeId.length - 1];

        //倒数第二位是specialId
        String specialId = null;
        if (openidAndSpecialIdAndTradeId.length >= 2) {
            specialId = openidAndSpecialIdAndTradeId[openidAndSpecialIdAndTradeId.length - 2];
        }

        //倒数第三位是openId
        String openId = null;
        if (openidAndSpecialIdAndTradeId.length >= 3) {
            openId = openidAndSpecialIdAndTradeId[openidAndSpecialIdAndTradeId.length - 3];
        }

        //判定，如果长度只有1，那么说明是用户自己的操作
        if (openidAndSpecialIdAndTradeId.length == 1) {
            openId = fromUserName;
        }

        //执行绑定
        try {
            OrderBindResultVO orderBindResultVO = orderBindService.bindByTradeId(tradeParentId, openId, specialId, null);
            List<String> tradeIdItemIdList = orderBindResultVO.getTradeIdItemIdList();
            return "成功绑定商品数 - " + tradeIdItemIdList.size();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "执行绑定失败 - " + ex.getMessage();
        }
    }

    /**
     * 查询某个订单是否绑定成功了 openId;specialId;tradeParentId
     *
     * @param fromUserName
     * @param tradeParentIdContent
     * @return
     */
    private String doHandleQueryTradeId(String fromUserName, String tradeParentIdContent) {
        //管理员操作
        String[] openidAndSpecialIdAndTradeId = tradeParentIdContent.split(";");

        //单号必须要有
        String tradeParentId = openidAndSpecialIdAndTradeId[openidAndSpecialIdAndTradeId.length - 1];

        //倒数第二位是specialId
        String specialId = null;
        if (openidAndSpecialIdAndTradeId.length >= 2) {
            specialId = openidAndSpecialIdAndTradeId[openidAndSpecialIdAndTradeId.length - 2];
        }

        //倒数第三位是openId
        String openId = null;
        if (openidAndSpecialIdAndTradeId.length >= 3) {
            openId = openidAndSpecialIdAndTradeId[openidAndSpecialIdAndTradeId.length - 3];
        }

        //判定，如果长度只有1，那么说明是用户自己的操作
        if (openidAndSpecialIdAndTradeId.length == 1) {
            openId = fromUserName;
        }

        //执行绑定
        try {
            List<OrderOpenidMap> orderOpenidMapList = orderOpenidMapService.selectBy(tradeParentId, openId, specialId);
            if (!EmptyUtils.isEmpty(orderOpenidMapList)) {
                return "已绑定";
            }
            else {
                return "未绑定";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "查询失败 - " + ex.getMessage();
        }
    }

    /**
     * 查询某个订单的返利信息 genericType;tradeParentId
     *
     * @param fromUserName
     * @param tradeParentIdContent
     * @return
     */
    private String doHandleQueryCommissions(String fromUserName, String tradeParentIdContent) {
        //管理员操作
        String[] openidAndSpecialIdAndTradeId = tradeParentIdContent.split(";");
        //单号必须要有
        String tradeParentId = openidAndSpecialIdAndTradeId[openidAndSpecialIdAndTradeId.length - 1];

        //倒数第二位是 绕开查询绑定表
        String genericType = null;
        if (openidAndSpecialIdAndTradeId.length >= 2) {
            genericType = openidAndSpecialIdAndTradeId[openidAndSpecialIdAndTradeId.length - 2];
        }

        //判定
        //统计返利
        BigDecimal allFee = new BigDecimal(0.0);
        if (genericType == null) {
            List<OrderOpenidMap> orderOpenidMapList = orderOpenidMapService.selectByTradeId(tradeParentIdContent, null);
            if (EmptyUtils.isEmpty(orderOpenidMapList)) {
                return "未查询到绑定信息，不符合查询条件，请先手动执行订单绑定";
            }

            for (OrderOpenidMap orderDetail : orderOpenidMapList) {

                //默认呢，就是预返利，哪怕是关闭
                BigDecimal fee = new BigDecimal(0.0);

                //返利
                Integer orderStatus = orderDetail.getOrderStatus();
                if (orderStatus == 12 || orderStatus == 14){
                    fee = new BigDecimal(orderDetail.getPubSharePreFee());
                }
                else if(orderStatus == 3) {
                    fee = new BigDecimal(orderDetail.getPubShareFee());
                }

                //alimama - 服务费
                if (fee.doubleValue() > 0) {
                    String alimamaFee = orderDetail.getAlimamaShareFee();
                    fee = fee.subtract(new BigDecimal(alimamaFee == null ? "0.0" : alimamaFee));
                }

                //维权订单
                BigDecimal refundFee = new BigDecimal(orderDetail.getRefundFee() == null ? "0.0" : orderDetail.getRefundFee());
                fee = fee.subtract(refundFee);

                //统计费用的更新
                allFee = allFee.add(fee);
            }

            //返回
            String message = "返利共: ￥" + NumberUtil.format(allFee.multiply(new BigDecimal(0.9))) + "\n";
            message += "共包含返利计件商品数: " + orderOpenidMapList.size() + "个";
            return message;
        }

        //泛化查询 - 直接查询订单
        List<OrderDetail> orderDetailList = orderService.selectByTradeId(tradeParentId, null);
        for (OrderDetail orderDetail : orderDetailList) {
            //默认呢，就是预返利，哪怕是关闭
            BigDecimal fee = new BigDecimal(0.0);

            //返利
            Integer orderStatus = orderDetail.getTkStatus();
            if (orderStatus == 12 || orderStatus == 14){
                fee = new BigDecimal(orderDetail.getPubSharePreFee());
            }
            else if(orderStatus == 3) {
                fee = new BigDecimal(orderDetail.getPubShareFee());
            }

            //alimama - 服务费
            if (fee.doubleValue() > 0) {
                String alimamaFee = orderDetail.getAlimamaShareFee();
                fee = fee.subtract(new BigDecimal(alimamaFee == null ? "0.0" : alimamaFee));
            }

            //统计费用的更新
            allFee = allFee.add(fee);
        }

        //返回
        String message = "返利共: ￥" + NumberUtil.format(allFee.multiply(new BigDecimal(0.9))) + "\n";
        message += "共包含返利计件商品数: " + orderDetailList.size() + "个";
        return message;
    }

    /**
     * 处理查询区间余额数据 - openId;specialId;startTime;endTime
     * @param fromUserName
     * @param content
     * @return
     */
    private String doHandleRangeYuE(String fromUserName, String content) {
        //管理员操作
        String[] openidAndSpecialIdAndTimeRange = content.split(";");

        //倒数第1位是endTime
        String endTime = null;
        endTime = openidAndSpecialIdAndTimeRange[openidAndSpecialIdAndTimeRange.length - 1];

        //倒数第2位是startTime
        String startTime = null;
        startTime = openidAndSpecialIdAndTimeRange[openidAndSpecialIdAndTimeRange.length - 2];

        //倒数第2位是 specialId
        String specialId = null;
        if (openidAndSpecialIdAndTimeRange.length >= 3) {
            specialId = openidAndSpecialIdAndTimeRange[openidAndSpecialIdAndTimeRange.length - 3];
            if (EmptyUtils.isEmpty(specialId)) {
                specialId = null;
            }
        }

        //倒数第三位是openId
        String openId = null;
        if (openidAndSpecialIdAndTimeRange.length >= 4) {
            openId = openidAndSpecialIdAndTimeRange[openidAndSpecialIdAndTimeRange.length - 4];
            if (EmptyUtils.isEmpty(openId)) {
                openId = null;
            }
        }

        //都为空
        if (specialId == null && openId == null) {
            openId = fromUserName;
        }

        //返回查询信息
        return doHandleYuE(openId, specialId, startTime, endTime);
    }

    /**
     * 处理触发模拟提现相关操作 openId;specialId;mockStatus;startTime;endTime
     * @param fromUserName
     * @param content
     * @return
     */
    private String doHandleTriggerPickMoney(String fromUserName, String content) {
        //管理员操作
        String[] openidAndSpecialIdAndTimeRange = content.split(";");

        //倒数第1位是endTime
        String endTime = null;
        endTime = openidAndSpecialIdAndTimeRange[openidAndSpecialIdAndTimeRange.length - 1];

        //倒数第2位是startTime
        String startTime = null;
        startTime = openidAndSpecialIdAndTimeRange[openidAndSpecialIdAndTimeRange.length - 2];

        //倒数第3位是 mockStatus
        String mockStatus = null;
        mockStatus = openidAndSpecialIdAndTimeRange[openidAndSpecialIdAndTimeRange.length - 3];


        //倒数第2位是 specialId
        String specialId = null;
        if (openidAndSpecialIdAndTimeRange.length >= 4) {
            specialId = openidAndSpecialIdAndTimeRange[openidAndSpecialIdAndTimeRange.length - 4];
            if (EmptyUtils.isEmpty(specialId)) {
                specialId = null;
            }
        }

        //倒数第三位是openId
        String openId = null;
        if (openidAndSpecialIdAndTimeRange.length >= 5) {
            openId = openidAndSpecialIdAndTimeRange[openidAndSpecialIdAndTimeRange.length - 5];
            if (EmptyUtils.isEmpty(openId)) {
                openId = null;
            }
        }

        //都为空
        if (specialId == null && openId == null) {
            openId = fromUserName;
        }

        //查询
        PickCommissionVO pickCommissionVO = orderBindService.mockPickMoney(openId, specialId, mockStatus, startTime, endTime);
        String message = "操作: " + pickCommissionVO.getAction() + "\n";
        message += "返利: " + pickCommissionVO.getCommission() + "\n";
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
