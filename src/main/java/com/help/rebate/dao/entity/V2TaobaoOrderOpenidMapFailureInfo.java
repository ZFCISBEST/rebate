package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * v2_taobao_order_openid_map_failure_info
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.V2TaobaoOrderOpenidMapFailureInfo订单关联映射失败表")
@Data
public class V2TaobaoOrderOpenidMapFailureInfo implements Serializable {
    private Integer id;

    private LocalDateTime gmtCreated;

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
     * 商品的ID
     */
    @ApiModelProperty(value="商品的ID")
    private String itemId;

    /**
     * 失败原因
     */
    @ApiModelProperty(value="失败原因")
    private String failReason;

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
        V2TaobaoOrderOpenidMapFailureInfo other = (V2TaobaoOrderOpenidMapFailureInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getTradeId() == null ? other.getTradeId() == null : this.getTradeId().equals(other.getTradeId()))
            && (this.getTradeParentId() == null ? other.getTradeParentId() == null : this.getTradeParentId().equals(other.getTradeParentId()))
            && (this.getItemId() == null ? other.getItemId() == null : this.getItemId().equals(other.getItemId()))
            && (this.getFailReason() == null ? other.getFailReason() == null : this.getFailReason().equals(other.getFailReason()))
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
        result = prime * result + ((getTradeParentId() == null) ? 0 : getTradeParentId().hashCode());
        result = prime * result + ((getItemId() == null) ? 0 : getItemId().hashCode());
        result = prime * result + ((getFailReason() == null) ? 0 : getFailReason().hashCode());
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
        sb.append(", tradeParentId=").append(tradeParentId);
        sb.append(", itemId=").append(itemId);
        sb.append(", failReason=").append(failReason);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}