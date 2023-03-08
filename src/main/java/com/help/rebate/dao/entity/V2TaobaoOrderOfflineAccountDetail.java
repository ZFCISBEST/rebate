package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * v2_taobao_order_offline_account_detail
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.V2TaobaoOrderOfflineAccountDetail无法关联上的那些订单的线下处理，多数是线下手动转链接的")
@Data
public class V2TaobaoOrderOfflineAccountDetail implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    /**
     * 订单号
     */
    @ApiModelProperty(value="订单号")
    private String tradeId;

    /**
     * 父单号
     */
    @ApiModelProperty(value="父单号")
    private String tradeParentId;

    /**
     * 结算的钱，单位（元）
     */
    @ApiModelProperty(value="结算的钱，单位（元）")
    private String commission;

    /**
     * 订单表的主键ID
     */
    @ApiModelProperty(value="订单表的主键ID")
    private Long taobaoOrderId;

    /**
     * 结算的备注信息
     */
    @ApiModelProperty(value="结算的备注信息")
    private String commissionMsg;

    /**
     * 状态字段，0表示不删除，1表示逻辑删除
     */
    @ApiModelProperty(value="状态字段，0表示不删除，1表示逻辑删除")
    private Byte status;

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
        V2TaobaoOrderOfflineAccountDetail other = (V2TaobaoOrderOfflineAccountDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getTradeId() == null ? other.getTradeId() == null : this.getTradeId().equals(other.getTradeId()))
            && (this.getTradeParentId() == null ? other.getTradeParentId() == null : this.getTradeParentId().equals(other.getTradeParentId()))
            && (this.getCommission() == null ? other.getCommission() == null : this.getCommission().equals(other.getCommission()))
            && (this.getTaobaoOrderId() == null ? other.getTaobaoOrderId() == null : this.getTaobaoOrderId().equals(other.getTaobaoOrderId()))
            && (this.getCommissionMsg() == null ? other.getCommissionMsg() == null : this.getCommissionMsg().equals(other.getCommissionMsg()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getTradeId() == null) ? 0 : getTradeId().hashCode());
        result = prime * result + ((getTradeParentId() == null) ? 0 : getTradeParentId().hashCode());
        result = prime * result + ((getCommission() == null) ? 0 : getCommission().hashCode());
        result = prime * result + ((getTaobaoOrderId() == null) ? 0 : getTaobaoOrderId().hashCode());
        result = prime * result + ((getCommissionMsg() == null) ? 0 : getCommissionMsg().hashCode());
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
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", tradeId=").append(tradeId);
        sb.append(", tradeParentId=").append(tradeParentId);
        sb.append(", commission=").append(commission);
        sb.append(", taobaoOrderId=").append(taobaoOrderId);
        sb.append(", commissionMsg=").append(commissionMsg);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}