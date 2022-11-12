package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * v2_taobao_pubsite_combination_info
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.V2TaobaoPubsiteCombinationInfo推广位组合表")
@Data
public class V2TaobaoPubsiteCombinationInfo implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime gmtCreated;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime gmtModified;

    /**
     * 原tkl_type，推广位类型 - virtual（对应着普通推广）、relation（对应着渠道推广），目前仅支持一种virtual
     */
    @ApiModelProperty(value="原tkl_type，推广位类型 - virtual（对应着普通推广）、relation（对应着渠道推广），目前仅支持一种virtual")
    private String pubSiteType;

    /**
     * 会员的ID，表示是谁在做的推广，默认是没有或者virtualId，当前仅支持relation，因为special只能自己买，没法做推广
     */
    @ApiModelProperty(value="会员的ID，表示是谁在做的推广，默认是没有或者virtualId，当前仅支持relation，因为special只能自己买，没法做推广")
    private String vipId;

    /**
     * 推广位
     */
    @ApiModelProperty(value="推广位")
    private String pubSite;

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
        V2TaobaoPubsiteCombinationInfo other = (V2TaobaoPubsiteCombinationInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getPubSiteType() == null ? other.getPubSiteType() == null : this.getPubSiteType().equals(other.getPubSiteType()))
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
        result = prime * result + ((getPubSiteType() == null) ? 0 : getPubSiteType().hashCode());
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
        sb.append(", pubSiteType=").append(pubSiteType);
        sb.append(", vipId=").append(vipId);
        sb.append(", pubSite=").append(pubSite);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}