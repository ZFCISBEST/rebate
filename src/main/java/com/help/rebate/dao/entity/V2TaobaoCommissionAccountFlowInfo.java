package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * v2_taobao_commission_account_flow_info
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.V2TaobaoCommissionAccountFlowInfo淘宝返利账户流水表")
@Data
public class V2TaobaoCommissionAccountFlowInfo implements Serializable {
    private Integer id;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    /**
     * 外部ID
     */
    @ApiModelProperty(value="外部ID")
    private String openId;

    /**
     * 事件发生时全部返利
     */
    @ApiModelProperty(value="事件发生时全部返利")
    private BigDecimal totalCommission;

    /**
     * 事件发生时剩余返利，相当于账户余额
     */
    @ApiModelProperty(value="事件发生时剩余返利，相当于账户余额")
    private BigDecimal remainCommission;

    /**
     * 事件发生时被冻结返利，这部分钱不能提取
     */
    @ApiModelProperty(value="事件发生时被冻结返利，这部分钱不能提取")
    private BigDecimal frozenCommission;

    /**
     * 产生的账户流水金额
     */
    @ApiModelProperty(value="产生的账户流水金额")
    private BigDecimal flowAmount;

    /**
     * 产生的账户流水金额类型：0-结算，1-维权退回，2-提现，3-冻结金额
     */
    @ApiModelProperty(value="产生的账户流水金额类型：0-结算，1-维权退回，2-提现，3-冻结金额")
    private Byte flowAmountType;

    /**
     * 产生的账户流水金额类型说明
     */
    @ApiModelProperty(value="产生的账户流水金额类型说明")
    private String flowAmountTypeMsg;

    /**
     * 如果是返利结算，结算的交易单号
     */
    @ApiModelProperty(value="如果是返利结算，结算的交易单号")
    private String commissionTradeId;

    /**
     * 如果是维权，维权的交易单号
     */
    @ApiModelProperty(value="如果是维权，维权的交易单号")
    private String refundTradeId;

    /**
     * 0-成功、1-失败、2-进行中
     */
    @ApiModelProperty(value="0-成功、1-失败、2-进行中")
    private Byte accountFlowStatus;

    /**
     * 状态信息，如提取失败，失败信息
     */
    @ApiModelProperty(value="状态信息，如提取失败，失败信息")
    private String accountFlowStatusMsg;

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
        V2TaobaoCommissionAccountFlowInfo other = (V2TaobaoCommissionAccountFlowInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getTotalCommission() == null ? other.getTotalCommission() == null : this.getTotalCommission().equals(other.getTotalCommission()))
            && (this.getRemainCommission() == null ? other.getRemainCommission() == null : this.getRemainCommission().equals(other.getRemainCommission()))
            && (this.getFrozenCommission() == null ? other.getFrozenCommission() == null : this.getFrozenCommission().equals(other.getFrozenCommission()))
            && (this.getFlowAmount() == null ? other.getFlowAmount() == null : this.getFlowAmount().equals(other.getFlowAmount()))
            && (this.getFlowAmountType() == null ? other.getFlowAmountType() == null : this.getFlowAmountType().equals(other.getFlowAmountType()))
            && (this.getFlowAmountTypeMsg() == null ? other.getFlowAmountTypeMsg() == null : this.getFlowAmountTypeMsg().equals(other.getFlowAmountTypeMsg()))
            && (this.getCommissionTradeId() == null ? other.getCommissionTradeId() == null : this.getCommissionTradeId().equals(other.getCommissionTradeId()))
            && (this.getRefundTradeId() == null ? other.getRefundTradeId() == null : this.getRefundTradeId().equals(other.getRefundTradeId()))
            && (this.getAccountFlowStatus() == null ? other.getAccountFlowStatus() == null : this.getAccountFlowStatus().equals(other.getAccountFlowStatus()))
            && (this.getAccountFlowStatusMsg() == null ? other.getAccountFlowStatusMsg() == null : this.getAccountFlowStatusMsg().equals(other.getAccountFlowStatusMsg()))
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
        result = prime * result + ((getFlowAmount() == null) ? 0 : getFlowAmount().hashCode());
        result = prime * result + ((getFlowAmountType() == null) ? 0 : getFlowAmountType().hashCode());
        result = prime * result + ((getFlowAmountTypeMsg() == null) ? 0 : getFlowAmountTypeMsg().hashCode());
        result = prime * result + ((getCommissionTradeId() == null) ? 0 : getCommissionTradeId().hashCode());
        result = prime * result + ((getRefundTradeId() == null) ? 0 : getRefundTradeId().hashCode());
        result = prime * result + ((getAccountFlowStatus() == null) ? 0 : getAccountFlowStatus().hashCode());
        result = prime * result + ((getAccountFlowStatusMsg() == null) ? 0 : getAccountFlowStatusMsg().hashCode());
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
        sb.append(", flowAmount=").append(flowAmount);
        sb.append(", flowAmountType=").append(flowAmountType);
        sb.append(", flowAmountTypeMsg=").append(flowAmountTypeMsg);
        sb.append(", commissionTradeId=").append(commissionTradeId);
        sb.append(", refundTradeId=").append(refundTradeId);
        sb.append(", accountFlowStatus=").append(accountFlowStatus);
        sb.append(", accountFlowStatusMsg=").append(accountFlowStatusMsg);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}