package com.help.rebate.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单绑定结果视图
 */
@Data
public class WideOrderDetailListDTO {
    private Integer current;
    private Integer pageSize;

    /**
     * 父订单号
     */
    @ApiModelProperty(value="父订单号")
    private String tradeParentId;

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
     * 维权标签，0 含义为非维权 1 含义为维权订单
     */
    @ApiModelProperty(value="维权标签，0 含义为非维权 1 含义为维权订单")
    private Integer refundTag;

    /**
     * 会员运营ID（需要申请到私域会员权限才返回此ID）
     */
    @ApiModelProperty(value="会员运营ID（需要申请到私域会员权限才返回此ID）")
    private String specialId;

    /**
     * 商品标题
     */
    @ApiModelProperty(value="商品标题")
    private String itemTitle;

    /**
     * 状态字段，0表示不删除，1表示逻辑删除
     */
    @ApiModelProperty(value="状态字段，0表示不删除，1表示逻辑删除")
    private Byte orderStatus;
}
