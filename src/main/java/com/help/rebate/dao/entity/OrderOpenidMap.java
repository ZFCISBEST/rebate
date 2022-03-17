package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order_openid_map
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.OrderOpenidMap订单关联映射表")
@Data
public class OrderOpenidMap implements Serializable {
    private Integer id;

    private Date gmtCreated;

    private Date gmtModified;

    /**
     * 订单号
     */
    @ApiModelProperty(value="订单号")
    private String tradeId;

    /**
     * 父单号
     */
    @ApiModelProperty(value="父单号")
    private String parentTradeId;

    /**
     * 关联的open id
     */
    @ApiModelProperty(value="关联的open id")
    private String openId;

    /**
     * 关联的外部ID，如果有的话
     */
    @ApiModelProperty(value="关联的外部ID，如果有的话")
    private String externalId;

    /**
     * 关联的会员ID，如果有的话
     */
    @ApiModelProperty(value="关联的会员ID，如果有的话")
    private String specialId;

    /**
     * 关联的渠道ID，如果有的话（虚拟的也可以）
     */
    @ApiModelProperty(value="关联的渠道ID，如果有的话（虚拟的也可以）")
    private String relationId;

    /**
     * 商品的ID
     */
    @ApiModelProperty(value="商品的ID")
    private String itemId;

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
     * 技术服务费=结算金额*收入比率*技术服务费率。推广者赚取佣金后支付给阿里妈妈的技术服务费用
     */
    @ApiModelProperty(value="技术服务费=结算金额*收入比率*技术服务费率。推广者赚取佣金后支付给阿里妈妈的技术服务费用")
    private String alimamaShareFee;

    /**
     * 订单状态 - 12-付款，13-关闭，14-确认收货，3-结算成功
     */
    @ApiModelProperty(value="订单状态 - 12-付款，13-关闭，14-确认收货，3-结算成功")
    private Integer orderStatus;

    /**
     * 给用户的结算状态 - 待结算、已结算、已关闭
     */
    @ApiModelProperty(value="给用户的结算状态 - 待结算、已结算、已关闭")
    private String commissionStatus;

    /**
     * 维权标签，0 含义为非维权 1 含义为维权订单
     */
    @ApiModelProperty(value="维权标签，0 含义为非维权 1 含义为维权订单")
    private Integer refundTag;

    /**
     * 映射类型，pubsite(推广位绑定), specialid(会员ID绑定), extend(扩展而来，通过parent订单扩展的其他购买商品，可能也转过码)，pubsite_specialid(既存在推广位，又有specialid是会员，此时可建立与opened的映射关系)
     */
    @ApiModelProperty(value="映射类型，pubsite(推广位绑定), specialid(会员ID绑定), extend(扩展而来，通过parent订单扩展的其他购买商品，可能也转过码)，pubsite_specialid(既存在推广位，又有specialid是会员，此时可建立与opened的映射关系)")
    private String mapType;

    /**
     * 0表示未删除，-1表示删除
     */
    @ApiModelProperty(value="0表示未删除，-1表示删除")
    private Integer status;

    private static final long serialVersionUID = 1L;

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
        OrderOpenidMap other = (OrderOpenidMap) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getTradeId() == null ? other.getTradeId() == null : this.getTradeId().equals(other.getTradeId()))
            && (this.getParentTradeId() == null ? other.getParentTradeId() == null : this.getParentTradeId().equals(other.getParentTradeId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getExternalId() == null ? other.getExternalId() == null : this.getExternalId().equals(other.getExternalId()))
            && (this.getSpecialId() == null ? other.getSpecialId() == null : this.getSpecialId().equals(other.getSpecialId()))
            && (this.getRelationId() == null ? other.getRelationId() == null : this.getRelationId().equals(other.getRelationId()))
            && (this.getItemId() == null ? other.getItemId() == null : this.getItemId().equals(other.getItemId()))
            && (this.getPubSharePreFee() == null ? other.getPubSharePreFee() == null : this.getPubSharePreFee().equals(other.getPubSharePreFee()))
            && (this.getPubShareFee() == null ? other.getPubShareFee() == null : this.getPubShareFee().equals(other.getPubShareFee()))
            && (this.getAlimamaShareFee() == null ? other.getAlimamaShareFee() == null : this.getAlimamaShareFee().equals(other.getAlimamaShareFee()))
            && (this.getOrderStatus() == null ? other.getOrderStatus() == null : this.getOrderStatus().equals(other.getOrderStatus()))
            && (this.getCommissionStatus() == null ? other.getCommissionStatus() == null : this.getCommissionStatus().equals(other.getCommissionStatus()))
            && (this.getRefundTag() == null ? other.getRefundTag() == null : this.getRefundTag().equals(other.getRefundTag()))
            && (this.getMapType() == null ? other.getMapType() == null : this.getMapType().equals(other.getMapType()))
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
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getExternalId() == null) ? 0 : getExternalId().hashCode());
        result = prime * result + ((getSpecialId() == null) ? 0 : getSpecialId().hashCode());
        result = prime * result + ((getRelationId() == null) ? 0 : getRelationId().hashCode());
        result = prime * result + ((getItemId() == null) ? 0 : getItemId().hashCode());
        result = prime * result + ((getPubSharePreFee() == null) ? 0 : getPubSharePreFee().hashCode());
        result = prime * result + ((getPubShareFee() == null) ? 0 : getPubShareFee().hashCode());
        result = prime * result + ((getAlimamaShareFee() == null) ? 0 : getAlimamaShareFee().hashCode());
        result = prime * result + ((getOrderStatus() == null) ? 0 : getOrderStatus().hashCode());
        result = prime * result + ((getCommissionStatus() == null) ? 0 : getCommissionStatus().hashCode());
        result = prime * result + ((getRefundTag() == null) ? 0 : getRefundTag().hashCode());
        result = prime * result + ((getMapType() == null) ? 0 : getMapType().hashCode());
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
        sb.append(", openId=").append(openId);
        sb.append(", externalId=").append(externalId);
        sb.append(", specialId=").append(specialId);
        sb.append(", relationId=").append(relationId);
        sb.append(", itemId=").append(itemId);
        sb.append(", pubSharePreFee=").append(pubSharePreFee);
        sb.append(", pubShareFee=").append(pubShareFee);
        sb.append(", alimamaShareFee=").append(alimamaShareFee);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", commissionStatus=").append(commissionStatus);
        sb.append(", refundTag=").append(refundTag);
        sb.append(", mapType=").append(mapType);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}