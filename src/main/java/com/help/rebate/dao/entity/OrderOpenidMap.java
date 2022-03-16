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
     * 预估返利费
     */
    @ApiModelProperty(value="预估返利费")
    private String preCommissionFee;

    /**
     * 实际返利费
     */
    @ApiModelProperty(value="实际返利费")
    private String actCommissionFee;

    /**
     * 订单状态 - 付款、已收货、已结算
     */
    @ApiModelProperty(value="订单状态 - 付款、已收货、已结算")
    private String orderStatus;

    /**
     * 待结算、已结算、已关闭
     */
    @ApiModelProperty(value="待结算、已结算、已关闭")
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
            && (this.getPreCommissionFee() == null ? other.getPreCommissionFee() == null : this.getPreCommissionFee().equals(other.getPreCommissionFee()))
            && (this.getActCommissionFee() == null ? other.getActCommissionFee() == null : this.getActCommissionFee().equals(other.getActCommissionFee()))
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
        result = prime * result + ((getPreCommissionFee() == null) ? 0 : getPreCommissionFee().hashCode());
        result = prime * result + ((getActCommissionFee() == null) ? 0 : getActCommissionFee().hashCode());
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
        sb.append(", preCommissionFee=").append(preCommissionFee);
        sb.append(", actCommissionFee=").append(actCommissionFee);
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