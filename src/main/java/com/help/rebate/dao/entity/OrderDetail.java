package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order_detail
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.OrderDetail订单详情表")
@Data
public class OrderDetail implements Serializable {
    private Integer id;

    private Date gmtCreated;

    private Date gmtModified;

    /**
     * 订单号
     */
    @ApiModelProperty(value="订单号")
    private String tradeId;

    /**
     * 父订单号
     */
    @ApiModelProperty(value="父订单号")
    private String parentTradeId;

    /**
     * 通过推广链接达到商品、店铺详情页的点击时间
     */
    @ApiModelProperty(value="通过推广链接达到商品、店铺详情页的点击时间")
    private Date clickTime;

    /**
     * 订单创建的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间
     */
    @ApiModelProperty(value="订单创建的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间")
    private Date tkCreateTime;

    /**
     * 订单在拍下付款的时间
     */
    @ApiModelProperty(value="订单在拍下付款的时间")
    private Date tbPaidTime;

    /**
     * 订单付款的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间
     */
    @ApiModelProperty(value="订单付款的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间")
    private Date tkPaidTime;

    /**
     * 买家拍下付款的金额（不包含运费金额）
     */
    @ApiModelProperty(value="买家拍下付款的金额（不包含运费金额）")
    private String alipayTotalPrice;

    /**
     * 买家确认收货的付款金额（不包含运费金额）
     */
    @ApiModelProperty(value="买家确认收货的付款金额（不包含运费金额）")
    private String payPrice;

    /**
     * 订单更新时间
     */
    @ApiModelProperty(value="订单更新时间")
    private String modifiedTime;

    /**
     * 已付款：指订单已付款，但还未确认收货 已收货：指订单已确认收货，但商家佣金未支付 已结算：指订单已确认收货，且商家佣金已支付成功 已失效：指订单关闭/订单佣金小于0.01元，订单关闭主要有：1）买家超时未付款； 2）买家付款前，买家/卖家取消了订单；3）订单付款后发起售中退款成功；3：订单结算，12：订单付款， 13：订单失效，14：订单成功。淘客订单状态，12-付款，13-关闭，14-确认收货，3-结算成功;不传，表示所有状态
     */
    @ApiModelProperty(value="已付款：指订单已付款，但还未确认收货 已收货：指订单已确认收货，但商家佣金未支付 已结算：指订单已确认收货，且商家佣金已支付成功 已失效：指订单关闭/订单佣金小于0.01元，订单关闭主要有：1）买家超时未付款； 2）买家付款前，买家/卖家取消了订单；3）订单付款后发起售中退款成功；3：订单结算，12：订单付款， 13：订单失效，14：订单成功。淘客订单状态，12-付款，13-关闭，14-确认收货，3-结算成功;不传，表示所有状态")
    private Integer tkStatus;

    /**
     * 订单所属平台类型，包括天猫、淘宝、聚划算等
     */
    @ApiModelProperty(value="订单所属平台类型，包括天猫、淘宝、聚划算等")
    private String orderType;

    /**
     * 维权标签，0 含义为非维权 1 含义为维权订单
     */
    @ApiModelProperty(value="维权标签，0 含义为非维权 1 含义为维权订单")
    private Integer refundTag;

    /**
     * 产品类型
     */
    @ApiModelProperty(value="产品类型")
    private String flowSource;

    /**
     * 成交平台
     */
    @ApiModelProperty(value="成交平台")
    private String terminalType;

    /**
     * 订单确认收货后且商家完成佣金支付的时间
     */
    @ApiModelProperty(value="订单确认收货后且商家完成佣金支付的时间")
    private Date tkEarningTime;

    /**
     * 二方：佣金收益的第一归属者； 三方：从其他淘宝客佣金中进行分成的推广者
     */
    @ApiModelProperty(value="二方：佣金收益的第一归属者； 三方：从其他淘宝客佣金中进行分成的推广者")
    private Integer tkOrderRole;

    /**
     * 佣金比率 - 30
     */
    @ApiModelProperty(value="佣金比率 - 30")
    private String totalCommissionRate;

    /**
     * 收入比率。订单结算的佣金比率+平台的补贴比率
     */
    @ApiModelProperty(value="收入比率。订单结算的佣金比率+平台的补贴比率")
    private String incomeRate;

    /**
     * 分成比率。从结算佣金中分得的收益比率
     */
    @ApiModelProperty(value="分成比率。从结算佣金中分得的收益比率")
    private String pubShareRate;

    /**
     * 提成率=收入比率*分成比率。指实际获得收益的比率。total_commission_rate * pub_share_rate
     */
    @ApiModelProperty(value="提成率=收入比率*分成比率。指实际获得收益的比率。total_commission_rate * pub_share_rate")
    private String tkTotalRate;

    /**
     * 佣金金额=结算金额*佣金比率
     */
    @ApiModelProperty(value="佣金金额=结算金额*佣金比率")
    private String totalCommissionFee;

    /**
     * 付款预估收入=付款金额*提成。指买家付款金额为基数，预估您可能获得的收入。因买家退款等原因，可能与结算预估收入不一致
     */
    @ApiModelProperty(value="付款预估收入=付款金额*提成。指买家付款金额为基数，预估您可能获得的收入。因买家退款等原因，可能与结算预估收入不一致")
    private String pubSharePreFee;

    /**
     * 结算预估收入=结算金额*提成。以买家确认收货的付款金额为基数，预估您可能获得的收入。因买家退款、您违规推广等原因，可能与您最终收入不一致。最终收入以月结后您实际收到的为准
     */
    @ApiModelProperty(value="结算预估收入=结算金额*提成。以买家确认收货的付款金额为基数，预估您可能获得的收入。因买家退款、您违规推广等原因，可能与您最终收入不一致。最终收入以月结后您实际收到的为准")
    private String pubShareFee;

    /**
     * 推广者赚取佣金后支付给阿里妈妈的技术服务费用的比率
     */
    @ApiModelProperty(value="推广者赚取佣金后支付给阿里妈妈的技术服务费用的比率")
    private String alimamaRate;

    /**
     * 技术服务费=结算金额*收入比率*技术服务费率。推广者赚取佣金后支付给阿里妈妈的技术服务费用
     */
    @ApiModelProperty(value="技术服务费=结算金额*收入比率*技术服务费率。推广者赚取佣金后支付给阿里妈妈的技术服务费用")
    private String alimamaShareFee;

    /**
     * 平台给与的补贴比率，如天猫、淘宝、聚划算等
     */
    @ApiModelProperty(value="平台给与的补贴比率，如天猫、淘宝、聚划算等")
    private String subsidyRate;

    /**
     * 补贴金额=结算金额*补贴比率
     */
    @ApiModelProperty(value="补贴金额=结算金额*补贴比率")
    private String subsidyFee;

    /**
     * 平台出资方，如天猫、淘宝、或聚划算等
     */
    @ApiModelProperty(value="平台出资方，如天猫、淘宝、或聚划算等")
    private String subsidyType;

    /**
     * 预估内容专项服务费：内容场景专项技术服务费，内容推广者在内容场景进行推广需要支付给阿里妈妈专项的技术服务费用。专项服务费＝付款金额＊专项服务费率。
     */
    @ApiModelProperty(value="预估内容专项服务费：内容场景专项技术服务费，内容推广者在内容场景进行推广需要支付给阿里妈妈专项的技术服务费用。专项服务费＝付款金额＊专项服务费率。")
    private String tkCommissionPreFeeForMediaPlatform;

    /**
     * 结算内容专项服务费：内容场景专项技术服务费，内容推广者在内容场景进行推广需要支付给阿里妈妈专项的技术服务费用。专项服务费＝结算金额＊专项服务费率。
     */
    @ApiModelProperty(value="结算内容专项服务费：内容场景专项技术服务费，内容推广者在内容场景进行推广需要支付给阿里妈妈专项的技术服务费用。专项服务费＝结算金额＊专项服务费率。")
    private String tkCommissionFeeForMediaPlatform;

    /**
     * 内容专项服务费率：内容场景专项技术服务费率，内容推广者在内容场景进行推广需要按结算金额支付一定比例给阿里妈妈作为内容场景专项技术服务费，用于提供与内容平台实现产品技术对接等服务。
     */
    @ApiModelProperty(value="内容专项服务费率：内容场景专项技术服务费率，内容推广者在内容场景进行推广需要按结算金额支付一定比例给阿里妈妈作为内容场景专项技术服务费，用于提供与内容平台实现产品技术对接等服务。")
    private String tkCommissionRateForMediaPlatform;

    /**
     * 推广者的会员id
     */
    @ApiModelProperty(value="推广者的会员id")
    private String pubId;

    /**
     * 媒体管理下的ID，同时也是pid=mm_1_2_3中的“2”这段数字
     */
    @ApiModelProperty(value="媒体管理下的ID，同时也是pid=mm_1_2_3中的“2”这段数字")
    private String siteId;

    /**
     * 推广位管理下的推广位名称对应的ID，同时也是pid=mm_1_2_3中的“3”这段数字
     */
    @ApiModelProperty(value="推广位管理下的推广位名称对应的ID，同时也是pid=mm_1_2_3中的“3”这段数字")
    private String adzoneId;

    /**
     * 媒体管理下的对应ID的自定义名称
     */
    @ApiModelProperty(value="媒体管理下的对应ID的自定义名称")
    private String siteName;

    /**
     * 推广位管理下的自定义推广位名称
     */
    @ApiModelProperty(value="推广位管理下的自定义推广位名称")
    private String adzoneName;

    /**
     * 会员运营ID（需要申请到私域会员权限才返回此ID）
     */
    @ApiModelProperty(value="会员运营ID（需要申请到私域会员权限才返回此ID）")
    private String specialId;

    /**
     * 渠道关系ID会员运营ID（需要申请到渠道管理权限才返回此ID）
     */
    @ApiModelProperty(value="渠道关系ID会员运营ID（需要申请到渠道管理权限才返回此ID）")
    private String relationId;

    /**
     * 商品所属的根类目，即一级类目的名称
     */
    @ApiModelProperty(value="商品所属的根类目，即一级类目的名称")
    private String itemCategoryName;

    /**
     * 掌柜旺旺
     */
    @ApiModelProperty(value="掌柜旺旺")
    private String sellerNick;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value="店铺名称")
    private String sellerShopTitle;

    /**
     * 商品id
     */
    @ApiModelProperty(value="商品id")
    private String itemId;

    /**
     * 商品标题
     */
    @ApiModelProperty(value="商品标题")
    private String itemTitle;

    /**
     * 商品单价
     */
    @ApiModelProperty(value="商品单价")
    private String itemPrice;

    /**
     * 商品链接
     */
    @ApiModelProperty(value="商品链接")
    private String itemLink;

    /**
     * 商品数量
     */
    @ApiModelProperty(value="商品数量")
    private Integer itemNum;

    /**
     * 预售时期，用户对预售商品支付定金的付款时间，可能略晚于在淘宝付定金时间
     */
    @ApiModelProperty(value="预售时期，用户对预售商品支付定金的付款时间，可能略晚于在淘宝付定金时间")
    private Date tkDepositTime;

    /**
     * 预售时期，用户对预售商品支付定金的付款时间
     */
    @ApiModelProperty(value="预售时期，用户对预售商品支付定金的付款时间")
    private Date tbDepositTime;

    /**
     * 预售时期，用户对预售商品支付的定金金额
     */
    @ApiModelProperty(value="预售时期，用户对预售商品支付的定金金额")
    private String depositPrice;

    /**
     * 口碑子订单号
     */
    @ApiModelProperty(value="口碑子订单号")
    private String alscId;

    /**
     * 口碑父订单号
     */
    @ApiModelProperty(value="口碑父订单号")
    private String alscPid;

    /**
     * 服务费信息，本来是个数组，ServiceFeeDto[]，能存就存，不能存就截断。 share_relative_rate（专项服务费率），share_fee（结算专项服务费），share_pre_fee（预估专项服务费），tk_share_role_type（专项服务费来源，122-渠道）
     */
    @ApiModelProperty(value="服务费信息，本来是个数组，ServiceFeeDto[]，能存就存，不能存就截断。 share_relative_rate（专项服务费率），share_fee（结算专项服务费），share_pre_fee（预估专项服务费），tk_share_role_type（专项服务费来源，122-渠道）")
    private String serviceFeeDtoList;

    /**
     * 激励池对应的rid
     */
    @ApiModelProperty(value="激励池对应的rid")
    private String lxRid;

    /**
     * 订单是否为激励池订单 1: 表征是 。0: 表征否
     */
    @ApiModelProperty(value="订单是否为激励池订单 1: 表征是 。0: 表征否")
    private String isLx;

    /**
     * 0表示不删除，-1表示删除
     */
    @ApiModelProperty(value="0表示不删除，-1表示删除")
    private Integer status;

    private static final long serialVersionUID = 1L;

    /**
     * 重构
     * @return
     */
    public String getItemId() {
        if (itemId == null || itemId.trim().isEmpty()) {
            return itemId;
        }
        if (itemId.contains("-")) {
            return itemId.split("-")[1];
        }
        return itemId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OrderDetail other = (OrderDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getTradeId() == null ? other.getTradeId() == null : this.getTradeId().equals(other.getTradeId()))
            && (this.getParentTradeId() == null ? other.getParentTradeId() == null : this.getParentTradeId().equals(other.getParentTradeId()))
            && (this.getClickTime() == null ? other.getClickTime() == null : this.getClickTime().equals(other.getClickTime()))
            && (this.getTkCreateTime() == null ? other.getTkCreateTime() == null : this.getTkCreateTime().equals(other.getTkCreateTime()))
            && (this.getTbPaidTime() == null ? other.getTbPaidTime() == null : this.getTbPaidTime().equals(other.getTbPaidTime()))
            && (this.getTkPaidTime() == null ? other.getTkPaidTime() == null : this.getTkPaidTime().equals(other.getTkPaidTime()))
            && (this.getAlipayTotalPrice() == null ? other.getAlipayTotalPrice() == null : this.getAlipayTotalPrice().equals(other.getAlipayTotalPrice()))
            && (this.getPayPrice() == null ? other.getPayPrice() == null : this.getPayPrice().equals(other.getPayPrice()))
            && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
            && (this.getTkStatus() == null ? other.getTkStatus() == null : this.getTkStatus().equals(other.getTkStatus()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getRefundTag() == null ? other.getRefundTag() == null : this.getRefundTag().equals(other.getRefundTag()))
            && (this.getFlowSource() == null ? other.getFlowSource() == null : this.getFlowSource().equals(other.getFlowSource()))
            && (this.getTerminalType() == null ? other.getTerminalType() == null : this.getTerminalType().equals(other.getTerminalType()))
            && (this.getTkEarningTime() == null ? other.getTkEarningTime() == null : this.getTkEarningTime().equals(other.getTkEarningTime()))
            && (this.getTkOrderRole() == null ? other.getTkOrderRole() == null : this.getTkOrderRole().equals(other.getTkOrderRole()))
            && (this.getTotalCommissionRate() == null ? other.getTotalCommissionRate() == null : this.getTotalCommissionRate().equals(other.getTotalCommissionRate()))
            && (this.getIncomeRate() == null ? other.getIncomeRate() == null : this.getIncomeRate().equals(other.getIncomeRate()))
            && (this.getPubShareRate() == null ? other.getPubShareRate() == null : this.getPubShareRate().equals(other.getPubShareRate()))
            && (this.getTkTotalRate() == null ? other.getTkTotalRate() == null : this.getTkTotalRate().equals(other.getTkTotalRate()))
            && (this.getTotalCommissionFee() == null ? other.getTotalCommissionFee() == null : this.getTotalCommissionFee().equals(other.getTotalCommissionFee()))
            && (this.getPubSharePreFee() == null ? other.getPubSharePreFee() == null : this.getPubSharePreFee().equals(other.getPubSharePreFee()))
            && (this.getPubShareFee() == null ? other.getPubShareFee() == null : this.getPubShareFee().equals(other.getPubShareFee()))
            && (this.getAlimamaRate() == null ? other.getAlimamaRate() == null : this.getAlimamaRate().equals(other.getAlimamaRate()))
            && (this.getAlimamaShareFee() == null ? other.getAlimamaShareFee() == null : this.getAlimamaShareFee().equals(other.getAlimamaShareFee()))
            && (this.getSubsidyRate() == null ? other.getSubsidyRate() == null : this.getSubsidyRate().equals(other.getSubsidyRate()))
            && (this.getSubsidyFee() == null ? other.getSubsidyFee() == null : this.getSubsidyFee().equals(other.getSubsidyFee()))
            && (this.getSubsidyType() == null ? other.getSubsidyType() == null : this.getSubsidyType().equals(other.getSubsidyType()))
            && (this.getTkCommissionPreFeeForMediaPlatform() == null ? other.getTkCommissionPreFeeForMediaPlatform() == null : this.getTkCommissionPreFeeForMediaPlatform().equals(other.getTkCommissionPreFeeForMediaPlatform()))
            && (this.getTkCommissionFeeForMediaPlatform() == null ? other.getTkCommissionFeeForMediaPlatform() == null : this.getTkCommissionFeeForMediaPlatform().equals(other.getTkCommissionFeeForMediaPlatform()))
            && (this.getTkCommissionRateForMediaPlatform() == null ? other.getTkCommissionRateForMediaPlatform() == null : this.getTkCommissionRateForMediaPlatform().equals(other.getTkCommissionRateForMediaPlatform()))
            && (this.getPubId() == null ? other.getPubId() == null : this.getPubId().equals(other.getPubId()))
            && (this.getSiteId() == null ? other.getSiteId() == null : this.getSiteId().equals(other.getSiteId()))
            && (this.getAdzoneId() == null ? other.getAdzoneId() == null : this.getAdzoneId().equals(other.getAdzoneId()))
            && (this.getSiteName() == null ? other.getSiteName() == null : this.getSiteName().equals(other.getSiteName()))
            && (this.getAdzoneName() == null ? other.getAdzoneName() == null : this.getAdzoneName().equals(other.getAdzoneName()))
            && (this.getSpecialId() == null ? other.getSpecialId() == null : this.getSpecialId().equals(other.getSpecialId()))
            && (this.getRelationId() == null ? other.getRelationId() == null : this.getRelationId().equals(other.getRelationId()))
            && (this.getItemCategoryName() == null ? other.getItemCategoryName() == null : this.getItemCategoryName().equals(other.getItemCategoryName()))
            && (this.getSellerNick() == null ? other.getSellerNick() == null : this.getSellerNick().equals(other.getSellerNick()))
            && (this.getSellerShopTitle() == null ? other.getSellerShopTitle() == null : this.getSellerShopTitle().equals(other.getSellerShopTitle()))
            && (this.getItemId() == null ? other.getItemId() == null : this.getItemId().equals(other.getItemId()))
            && (this.getItemTitle() == null ? other.getItemTitle() == null : this.getItemTitle().equals(other.getItemTitle()))
            && (this.getItemPrice() == null ? other.getItemPrice() == null : this.getItemPrice().equals(other.getItemPrice()))
            && (this.getItemLink() == null ? other.getItemLink() == null : this.getItemLink().equals(other.getItemLink()))
            && (this.getItemNum() == null ? other.getItemNum() == null : this.getItemNum().equals(other.getItemNum()))
            && (this.getTkDepositTime() == null ? other.getTkDepositTime() == null : this.getTkDepositTime().equals(other.getTkDepositTime()))
            && (this.getTbDepositTime() == null ? other.getTbDepositTime() == null : this.getTbDepositTime().equals(other.getTbDepositTime()))
            && (this.getDepositPrice() == null ? other.getDepositPrice() == null : this.getDepositPrice().equals(other.getDepositPrice()))
            && (this.getAlscId() == null ? other.getAlscId() == null : this.getAlscId().equals(other.getAlscId()))
            && (this.getAlscPid() == null ? other.getAlscPid() == null : this.getAlscPid().equals(other.getAlscPid()))
            && (this.getServiceFeeDtoList() == null ? other.getServiceFeeDtoList() == null : this.getServiceFeeDtoList().equals(other.getServiceFeeDtoList()))
            && (this.getLxRid() == null ? other.getLxRid() == null : this.getLxRid().equals(other.getLxRid()))
            && (this.getIsLx() == null ? other.getIsLx() == null : this.getIsLx().equals(other.getIsLx()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGmtCreated() == null) ? 0 : getGmtCreated().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getTradeId() == null) ? 0 : getTradeId().hashCode());
        result = prime * result + ((getParentTradeId() == null) ? 0 : getParentTradeId().hashCode());
        result = prime * result + ((getClickTime() == null) ? 0 : getClickTime().hashCode());
        result = prime * result + ((getTkCreateTime() == null) ? 0 : getTkCreateTime().hashCode());
        result = prime * result + ((getTbPaidTime() == null) ? 0 : getTbPaidTime().hashCode());
        result = prime * result + ((getTkPaidTime() == null) ? 0 : getTkPaidTime().hashCode());
        result = prime * result + ((getAlipayTotalPrice() == null) ? 0 : getAlipayTotalPrice().hashCode());
        result = prime * result + ((getPayPrice() == null) ? 0 : getPayPrice().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getTkStatus() == null) ? 0 : getTkStatus().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getRefundTag() == null) ? 0 : getRefundTag().hashCode());
        result = prime * result + ((getFlowSource() == null) ? 0 : getFlowSource().hashCode());
        result = prime * result + ((getTerminalType() == null) ? 0 : getTerminalType().hashCode());
        result = prime * result + ((getTkEarningTime() == null) ? 0 : getTkEarningTime().hashCode());
        result = prime * result + ((getTkOrderRole() == null) ? 0 : getTkOrderRole().hashCode());
        result = prime * result + ((getTotalCommissionRate() == null) ? 0 : getTotalCommissionRate().hashCode());
        result = prime * result + ((getIncomeRate() == null) ? 0 : getIncomeRate().hashCode());
        result = prime * result + ((getPubShareRate() == null) ? 0 : getPubShareRate().hashCode());
        result = prime * result + ((getTkTotalRate() == null) ? 0 : getTkTotalRate().hashCode());
        result = prime * result + ((getTotalCommissionFee() == null) ? 0 : getTotalCommissionFee().hashCode());
        result = prime * result + ((getPubSharePreFee() == null) ? 0 : getPubSharePreFee().hashCode());
        result = prime * result + ((getPubShareFee() == null) ? 0 : getPubShareFee().hashCode());
        result = prime * result + ((getAlimamaRate() == null) ? 0 : getAlimamaRate().hashCode());
        result = prime * result + ((getAlimamaShareFee() == null) ? 0 : getAlimamaShareFee().hashCode());
        result = prime * result + ((getSubsidyRate() == null) ? 0 : getSubsidyRate().hashCode());
        result = prime * result + ((getSubsidyFee() == null) ? 0 : getSubsidyFee().hashCode());
        result = prime * result + ((getSubsidyType() == null) ? 0 : getSubsidyType().hashCode());
        result = prime * result + ((getTkCommissionPreFeeForMediaPlatform() == null) ? 0 : getTkCommissionPreFeeForMediaPlatform().hashCode());
        result = prime * result + ((getTkCommissionFeeForMediaPlatform() == null) ? 0 : getTkCommissionFeeForMediaPlatform().hashCode());
        result = prime * result + ((getTkCommissionRateForMediaPlatform() == null) ? 0 : getTkCommissionRateForMediaPlatform().hashCode());
        result = prime * result + ((getPubId() == null) ? 0 : getPubId().hashCode());
        result = prime * result + ((getSiteId() == null) ? 0 : getSiteId().hashCode());
        result = prime * result + ((getAdzoneId() == null) ? 0 : getAdzoneId().hashCode());
        result = prime * result + ((getSiteName() == null) ? 0 : getSiteName().hashCode());
        result = prime * result + ((getAdzoneName() == null) ? 0 : getAdzoneName().hashCode());
        result = prime * result + ((getSpecialId() == null) ? 0 : getSpecialId().hashCode());
        result = prime * result + ((getRelationId() == null) ? 0 : getRelationId().hashCode());
        result = prime * result + ((getItemCategoryName() == null) ? 0 : getItemCategoryName().hashCode());
        result = prime * result + ((getSellerNick() == null) ? 0 : getSellerNick().hashCode());
        result = prime * result + ((getSellerShopTitle() == null) ? 0 : getSellerShopTitle().hashCode());
        result = prime * result + ((getItemId() == null) ? 0 : getItemId().hashCode());
        result = prime * result + ((getItemTitle() == null) ? 0 : getItemTitle().hashCode());
        result = prime * result + ((getItemPrice() == null) ? 0 : getItemPrice().hashCode());
        result = prime * result + ((getItemLink() == null) ? 0 : getItemLink().hashCode());
        result = prime * result + ((getItemNum() == null) ? 0 : getItemNum().hashCode());
        result = prime * result + ((getTkDepositTime() == null) ? 0 : getTkDepositTime().hashCode());
        result = prime * result + ((getTbDepositTime() == null) ? 0 : getTbDepositTime().hashCode());
        result = prime * result + ((getDepositPrice() == null) ? 0 : getDepositPrice().hashCode());
        result = prime * result + ((getAlscId() == null) ? 0 : getAlscId().hashCode());
        result = prime * result + ((getAlscPid() == null) ? 0 : getAlscPid().hashCode());
        result = prime * result + ((getServiceFeeDtoList() == null) ? 0 : getServiceFeeDtoList().hashCode());
        result = prime * result + ((getLxRid() == null) ? 0 : getLxRid().hashCode());
        result = prime * result + ((getIsLx() == null) ? 0 : getIsLx().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", tradeId=").append(tradeId);
        sb.append(", parentTradeId=").append(parentTradeId);
        sb.append(", clickTime=").append(clickTime);
        sb.append(", tkCreateTime=").append(tkCreateTime);
        sb.append(", tbPaidTime=").append(tbPaidTime);
        sb.append(", tkPaidTime=").append(tkPaidTime);
        sb.append(", alipayTotalPrice=").append(alipayTotalPrice);
        sb.append(", payPrice=").append(payPrice);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", tkStatus=").append(tkStatus);
        sb.append(", orderType=").append(orderType);
        sb.append(", refundTag=").append(refundTag);
        sb.append(", flowSource=").append(flowSource);
        sb.append(", terminalType=").append(terminalType);
        sb.append(", tkEarningTime=").append(tkEarningTime);
        sb.append(", tkOrderRole=").append(tkOrderRole);
        sb.append(", totalCommissionRate=").append(totalCommissionRate);
        sb.append(", incomeRate=").append(incomeRate);
        sb.append(", pubShareRate=").append(pubShareRate);
        sb.append(", tkTotalRate=").append(tkTotalRate);
        sb.append(", totalCommissionFee=").append(totalCommissionFee);
        sb.append(", pubSharePreFee=").append(pubSharePreFee);
        sb.append(", pubShareFee=").append(pubShareFee);
        sb.append(", alimamaRate=").append(alimamaRate);
        sb.append(", alimamaShareFee=").append(alimamaShareFee);
        sb.append(", subsidyRate=").append(subsidyRate);
        sb.append(", subsidyFee=").append(subsidyFee);
        sb.append(", subsidyType=").append(subsidyType);
        sb.append(", tkCommissionPreFeeForMediaPlatform=").append(tkCommissionPreFeeForMediaPlatform);
        sb.append(", tkCommissionFeeForMediaPlatform=").append(tkCommissionFeeForMediaPlatform);
        sb.append(", tkCommissionRateForMediaPlatform=").append(tkCommissionRateForMediaPlatform);
        sb.append(", pubId=").append(pubId);
        sb.append(", siteId=").append(siteId);
        sb.append(", adzoneId=").append(adzoneId);
        sb.append(", siteName=").append(siteName);
        sb.append(", adzoneName=").append(adzoneName);
        sb.append(", specialId=").append(specialId);
        sb.append(", relationId=").append(relationId);
        sb.append(", itemCategoryName=").append(itemCategoryName);
        sb.append(", sellerNick=").append(sellerNick);
        sb.append(", sellerShopTitle=").append(sellerShopTitle);
        sb.append(", itemId=").append(itemId);
        sb.append(", itemTitle=").append(itemTitle);
        sb.append(", itemPrice=").append(itemPrice);
        sb.append(", itemLink=").append(itemLink);
        sb.append(", itemNum=").append(itemNum);
        sb.append(", tkDepositTime=").append(tkDepositTime);
        sb.append(", tbDepositTime=").append(tbDepositTime);
        sb.append(", depositPrice=").append(depositPrice);
        sb.append(", alscId=").append(alscId);
        sb.append(", alscPid=").append(alscPid);
        sb.append(", serviceFeeDtoList=").append(serviceFeeDtoList);
        sb.append(", lxRid=").append(lxRid);
        sb.append(", isLx=").append(isLx);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}