package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * tkl_convert_history
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.TklConvertHistory转链记录表")
@Data
public class TklConvertHistory implements Serializable {
    private Integer id;

    private Date gmtCreated;

    private Date gmtModified;

    /**
     * 开放ID，从其他对接系统获取到的数据
     */
    @ApiModelProperty(value="开放ID，从其他对接系统获取到的数据")
    private String openId;

    /**
     * 外部ID
     */
    @ApiModelProperty(value="外部ID")
    private String externalId;

    /**
     * 组合[类型|类型内容|推广位]，三位一体
     */
    @ApiModelProperty(value="组合[类型|类型内容|推广位]，三位一体")
    private String pubsiteCombination;

    /**
     * 用户的淘口令
     */
    @ApiModelProperty(value="用户的淘口令")
    private String tkl;

    /**
     * 转后的淘口令
     */
    @ApiModelProperty(value="转后的淘口令")
    private String newTkl;

    /**
     * 商品的ID
     */
    @ApiModelProperty(value="商品的ID")
    private String itemId;

    /**
     * 淘口令类型 - 渠道（relation）、会员（special）、虚拟（virtual）、无（none）
     */
    @ApiModelProperty(value="淘口令类型 - 渠道（relation）、会员（special）、虚拟（virtual）、无（none）")
    private String tkltype;

    /**
     * 生成时，使用的信息（如specialID，relationID，externalID）
     */
    @ApiModelProperty(value="生成时，使用的信息（如specialID，relationID，externalID）")
    private String attachInfo;

    /**
     * 0表示未过期，-1表示过期
     */
    @ApiModelProperty(value="0表示未过期，-1表示过期")
    private Integer expired;

    /**
     * 0表示不删除，-1表示删除
     */
    @ApiModelProperty(value="0表示不删除，-1表示删除")
    private Integer status;

    private static final long serialVersionUID = 1L;

    /**
     * 重构
     * @return
     */
    public String getItemId() {
        if (itemId == null || itemId.trim().isEmpty()) {
            return itemId;
        }
        if (itemId.contains("-")) {
            return itemId.split("-")[1];
        }
        return itemId;
    }

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
        TklConvertHistory other = (TklConvertHistory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getExternalId() == null ? other.getExternalId() == null : this.getExternalId().equals(other.getExternalId()))
            && (this.getPubsiteCombination() == null ? other.getPubsiteCombination() == null : this.getPubsiteCombination().equals(other.getPubsiteCombination()))
            && (this.getTkl() == null ? other.getTkl() == null : this.getTkl().equals(other.getTkl()))
            && (this.getNewTkl() == null ? other.getNewTkl() == null : this.getNewTkl().equals(other.getNewTkl()))
            && (this.getItemId() == null ? other.getItemId() == null : this.getItemId().equals(other.getItemId()))
            && (this.getTkltype() == null ? other.getTkltype() == null : this.getTkltype().equals(other.getTkltype()))
            && (this.getAttachInfo() == null ? other.getAttachInfo() == null : this.getAttachInfo().equals(other.getAttachInfo()))
            && (this.getExpired() == null ? other.getExpired() == null : this.getExpired().equals(other.getExpired()))
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
        result = prime * result + ((getPubsiteCombination() == null) ? 0 : getPubsiteCombination().hashCode());
        result = prime * result + ((getTkl() == null) ? 0 : getTkl().hashCode());
        result = prime * result + ((getNewTkl() == null) ? 0 : getNewTkl().hashCode());
        result = prime * result + ((getItemId() == null) ? 0 : getItemId().hashCode());
        result = prime * result + ((getTkltype() == null) ? 0 : getTkltype().hashCode());
        result = prime * result + ((getAttachInfo() == null) ? 0 : getAttachInfo().hashCode());
        result = prime * result + ((getExpired() == null) ? 0 : getExpired().hashCode());
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
        sb.append(", pubsiteCombination=").append(pubsiteCombination);
        sb.append(", tkl=").append(tkl);
        sb.append(", newTkl=").append(newTkl);
        sb.append(", itemId=").append(itemId);
        sb.append(", tkltype=").append(tkltype);
        sb.append(", attachInfo=").append(attachInfo);
        sb.append(", expired=").append(expired);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}