package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * commission_ratio
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.CommissionRatio用户佣金比率表，一个用户同时只能使用一个费率（或者使用最新的）")
@Data
public class CommissionRatio implements Serializable {
    private Integer id;

    private Date gmtCreated;

    private Date gmtModified;

    /**
     * 外部ID
     */
    @ApiModelProperty(value="外部ID")
    private String openId;

    /**
     * 外部关联的ID - 默认与open_id相等
     */
    @ApiModelProperty(value="外部关联的ID - 默认与open_id相等")
    private String externalId;

    /**
     * 佣金比率，默认是1，就是全返
     */
    @ApiModelProperty(value="佣金比率，默认是1，就是全返")
    private Integer commissionRatio;

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
        CommissionRatio other = (CommissionRatio) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getExternalId() == null ? other.getExternalId() == null : this.getExternalId().equals(other.getExternalId()))
            && (this.getCommissionRatio() == null ? other.getCommissionRatio() == null : this.getCommissionRatio().equals(other.getCommissionRatio()))
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
        result = prime * result + ((getCommissionRatio() == null) ? 0 : getCommissionRatio().hashCode());
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
        sb.append(", commissionRatio=").append(commissionRatio);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}