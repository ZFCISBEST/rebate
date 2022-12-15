package com.help.rebate.model;

import com.help.rebate.dao.entity.V2TaobaoOrderDetailInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单绑定结果视图
 */
@Data
public class WideOrderDetailListVO /*extends V2TaobaoOrderDetailInfo*/ {

    /**
     * 关联的open id
     */
    @ApiModelProperty(value="关联的open id")
    private String openId;

    /**
     * 佣金比率，默认是1，就是全返。可以不用alimama_share_fee，整体用该字段，如0.85
     */
    @ApiModelProperty(value="佣金比率，默认是1，就是全返。可以不用alimama_share_fee，整体用该字段，如0.85")
    private Integer commissionRatio;

    /**
     * 映射类型信息，pubsite(推广位绑定), specialid(会员ID绑定), extend(扩展而来，通过parent订单扩展的其他购买商品，可能也转过码)，pubsite_specialid(既存在推广位，又有specialid是会员，此时可建立与opened的映射关系)
     */
    @ApiModelProperty(value="映射类型信息，pubsite(推广位绑定), specialid(会员ID绑定), extend(扩展而来，通过parent订单扩展的其他购买商品，可能也转过码)，pubsite_specialid(既存在推广位，又有specialid是会员，此时可建立与opened的映射关系)")
    private String mapTypeMsg;

    /**
     * 给用户的结算状态 - 待提取、提取中，提取成功，提取失败, 提取超时
     */
    @ApiModelProperty(value="给用户的结算状态 - 待提取、提取中，提取成功，提取失败, 提取超时")
    private String commissionStatusMsg;

    private Integer id;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    /**
     * 订单号
     */
    @ApiModelProperty(value="订单号")
    private String tradeId;

    /**
     * 父订单号
     */
    @ApiModelProperty(value="父订单号")
    private String tradeParentId;

    /**
     * 通过推广链接达到商品、店铺详情页的点击时间
     */
    @ApiModelProperty(value="通过推广链接达到商品、店铺详情页的点击时间")
    private LocalDateTime clickTime;

    /**
     * 订单创建的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间
     */
    @ApiModelProperty(value="订单创建的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间")
    private LocalDateTime tkCreateTime;

    /**
     * 订单在拍下付款的时间
     */
    @ApiModelProperty(value="订单在拍下付款的时间")
    private LocalDateTime tbPaidTime;

    /**
     * 订单付款的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间
     */
    @ApiModelProperty(value="订单付款的时间，该时间同步淘宝，可能会略晚于买家在淘宝的订单创建时间")
    private LocalDateTime tkPaidTime;

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
    private LocalDateTime tkEarningTime;

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
    private LocalDateTime tkDepositTime;

    /**
     * 预售时期，用户对预售商品支付定金的付款时间
     */
    @ApiModelProperty(value="预售时期，用户对预售商品支付定金的付款时间")
    private LocalDateTime tbDepositTime;

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

    /**付款金额
     *
     * 状态字段，0表示不删除，1表示逻辑删除
     */
    @ApiModelProperty(value="状态字段，0表示不删除，1表示逻辑删除")
    private Byte status;
}
