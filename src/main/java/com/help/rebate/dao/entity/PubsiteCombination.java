package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * pubsite_combination
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.PubsiteCombination推广位组合表")
@Data
public class PubsiteCombination implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date gmtCreated;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date gmtModified;

    /**
     * 口令类型 - special/relation/virtual/none
     */
    @ApiModelProperty(value="口令类型 - special/relation/virtual/none")
    private String tklType;

    private String vipId;

    private String pubSite;

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
        PubsiteCombination other = (PubsiteCombination) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getTklType() == null ? other.getTklType() == null : this.getTklType().equals(other.getTklType()))
            && (this.getVipId() == null ? other.getVipId() == null : this.getVipId().equals(other.getVipId()))
            && (this.getPubSite() == null ? other.getPubSite() == null : this.getPubSite().equals(other.getPubSite()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGmtCreated() == null) ? 0 : getGmtCreated().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getTklType() == null) ? 0 : getTklType().hashCode());
        result = prime * result + ((getVipId() == null) ? 0 : getVipId().hashCode());
        result = prime * result + ((getPubSite() == null) ? 0 : getPubSite().hashCode());
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
        sb.append(", tklType=").append(tklType);
        sb.append(", vipId=").append(vipId);
        sb.append(", pubSite=").append(pubSite);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}