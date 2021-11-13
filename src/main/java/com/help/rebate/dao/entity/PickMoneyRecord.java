package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * pick_money_record
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.PickMoneyRecord提现记录表")
@Data
public class PickMoneyRecord implements Serializable {
    private Integer id;

    private Date gmtCreated;

    private Date gmtModified;

    /**
     * 外部ID
     */
    @ApiModelProperty(value="外部ID")
    private String openId;

    /**
     * 关联的外部ID
     */
    @ApiModelProperty(value="关联的外部ID")
    private String externalId;

    /**
     * 提现时总额
     */
    @ApiModelProperty(value="提现时总额")
    private String totalCommission;

    /**
     * 提现时剩余额度
     */
    @ApiModelProperty(value="提现时剩余额度")
    private String remainCommission;

    /**
     * 预提现金额
     */
    @ApiModelProperty(value="预提现金额")
    private String prePickCommission;

    /**
     * 实际提取的金额
     */
    @ApiModelProperty(value="实际提取的金额")
    private String actPickCommission;

    /**
     * 附加信息，如满10元可提，提取成功，提取失败信息
     */
    @ApiModelProperty(value="附加信息，如满10元可提，提取成功，提取失败信息")
    private String pickAttachInfo;

    /**
     * 预提取、提取中、提取失败、已提取
     */
    @ApiModelProperty(value="预提取、提取中、提取失败、已提取")
    private String pickStatus;

    /**
     * 0表示不删除，-1表示删除
     */
    @ApiModelProperty(value="0表示不删除，-1表示删除")
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
        PickMoneyRecord other = (PickMoneyRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getExternalId() == null ? other.getExternalId() == null : this.getExternalId().equals(other.getExternalId()))
            && (this.getTotalCommission() == null ? other.getTotalCommission() == null : this.getTotalCommission().equals(other.getTotalCommission()))
            && (this.getRemainCommission() == null ? other.getRemainCommission() == null : this.getRemainCommission().equals(other.getRemainCommission()))
            && (this.getPrePickCommission() == null ? other.getPrePickCommission() == null : this.getPrePickCommission().equals(other.getPrePickCommission()))
            && (this.getActPickCommission() == null ? other.getActPickCommission() == null : this.getActPickCommission().equals(other.getActPickCommission()))
            && (this.getPickAttachInfo() == null ? other.getPickAttachInfo() == null : this.getPickAttachInfo().equals(other.getPickAttachInfo()))
            && (this.getPickStatus() == null ? other.getPickStatus() == null : this.getPickStatus().equals(other.getPickStatus()))
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
        result = prime * result + ((getExternalId() == null) ? 0 : getExternalId().hashCode());
        result = prime * result + ((getTotalCommission() == null) ? 0 : getTotalCommission().hashCode());
        result = prime * result + ((getRemainCommission() == null) ? 0 : getRemainCommission().hashCode());
        result = prime * result + ((getPrePickCommission() == null) ? 0 : getPrePickCommission().hashCode());
        result = prime * result + ((getActPickCommission() == null) ? 0 : getActPickCommission().hashCode());
        result = prime * result + ((getPickAttachInfo() == null) ? 0 : getPickAttachInfo().hashCode());
        result = prime * result + ((getPickStatus() == null) ? 0 : getPickStatus().hashCode());
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
        sb.append(", externalId=").append(externalId);
        sb.append(", totalCommission=").append(totalCommission);
        sb.append(", remainCommission=").append(remainCommission);
        sb.append(", prePickCommission=").append(prePickCommission);
        sb.append(", actPickCommission=").append(actPickCommission);
        sb.append(", pickAttachInfo=").append(pickAttachInfo);
        sb.append(", pickStatus=").append(pickStatus);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}