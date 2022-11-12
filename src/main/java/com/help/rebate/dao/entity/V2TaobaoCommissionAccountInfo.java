package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * v2_taobao_commission_account_info
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.V2TaobaoCommissionAccountInfo佣金账户表")
@Data
public class V2TaobaoCommissionAccountInfo implements Serializable {
    private Integer id;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    /**
     * 外部ID
     */
    @ApiModelProperty(value="外部ID")
    private String openId;

    /**
     * 全部返利
     */
    @ApiModelProperty(value="全部返利")
    private BigDecimal totalCommission;

    /**
     * 剩余返利，相当于账户余额
     */
    @ApiModelProperty(value="剩余返利，相当于账户余额")
    private BigDecimal remainCommission;

    /**
     * 被冻结返利，这部分钱不能提取
     */
    @ApiModelProperty(value="被冻结返利，这部分钱不能提取")
    private BigDecimal frozenCommission;

    /**
     * 已结算的，相当于历史提现总额
     */
    @ApiModelProperty(value="已结算的，相当于历史提现总额")
    private BigDecimal finishCommission;

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
        V2TaobaoCommissionAccountInfo other = (V2TaobaoCommissionAccountInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getTotalCommission() == null ? other.getTotalCommission() == null : this.getTotalCommission().equals(other.getTotalCommission()))
            && (this.getRemainCommission() == null ? other.getRemainCommission() == null : this.getRemainCommission().equals(other.getRemainCommission()))
            && (this.getFrozenCommission() == null ? other.getFrozenCommission() == null : this.getFrozenCommission().equals(other.getFrozenCommission()))
            && (this.getFinishCommission() == null ? other.getFinishCommission() == null : this.getFinishCommission().equals(other.getFinishCommission()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGmtCreated() == null) ? 0 : getGmtCreated().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getTotalCommission() == null) ? 0 : getTotalCommission().hashCode());
        result = prime * result + ((getRemainCommission() == null) ? 0 : getRemainCommission().hashCode());
        result = prime * result + ((getFrozenCommission() == null) ? 0 : getFrozenCommission().hashCode());
        result = prime * result + ((getFinishCommission() == null) ? 0 : getFinishCommission().hashCode());
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
        sb.append(", openId=").append(openId);
        sb.append(", totalCommission=").append(totalCommission);
        sb.append(", remainCommission=").append(remainCommission);
        sb.append(", frozenCommission=").append(frozenCommission);
        sb.append(", finishCommission=").append(finishCommission);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}