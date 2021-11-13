package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * commission_account
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.CommissionAccount佣金计算表")
@Data
public class CommissionAccount implements Serializable {
    private Integer id;

    private Date gmtCreated;

    private Date gmtModified;

    /**
     * 外部ID
     */
    @ApiModelProperty(value="外部ID")
    private String openId;

    /**
     * 全部返利
     */
    @ApiModelProperty(value="全部返利")
    private String totalCommission;

    /**
     * 剩余返利
     */
    @ApiModelProperty(value="剩余返利")
    private String remainCommission;

    /**
     * 已结算的
     */
    @ApiModelProperty(value="已结算的")
    private String finishCommission;

    /**
     * 账户类型 - 总、年、月、周
     */
    @ApiModelProperty(value="账户类型 - 总、年、月、周")
    private String accountType;

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
        CommissionAccount other = (CommissionAccount) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getTotalCommission() == null ? other.getTotalCommission() == null : this.getTotalCommission().equals(other.getTotalCommission()))
            && (this.getRemainCommission() == null ? other.getRemainCommission() == null : this.getRemainCommission().equals(other.getRemainCommission()))
            && (this.getFinishCommission() == null ? other.getFinishCommission() == null : this.getFinishCommission().equals(other.getFinishCommission()))
            && (this.getAccountType() == null ? other.getAccountType() == null : this.getAccountType().equals(other.getAccountType()))
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
        result = prime * result + ((getFinishCommission() == null) ? 0 : getFinishCommission().hashCode());
        result = prime * result + ((getAccountType() == null) ? 0 : getAccountType().hashCode());
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
        sb.append(", finishCommission=").append(finishCommission);
        sb.append(", accountType=").append(accountType);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}