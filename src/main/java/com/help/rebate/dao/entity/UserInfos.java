package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_infos
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.UserInfos")
@Data
public class UserInfos implements Serializable {
    /**
     * 自增
     */
    @ApiModelProperty(value="自增")
    private Integer id;

    private Date gmtCreated;

    private Date gmtModified;

    /**
     * 开放ID，从其他对接系统获取到的数据
     */
    @ApiModelProperty(value="开放ID，从其他对接系统获取到的数据")
    private String openId;

    /**
     * 获取到的对接系统的用户名称
     */
    @ApiModelProperty(value="获取到的对接系统的用户名称")
    private String openName;

    /**
     * 用户查询联盟的ID，关联到淘宝联盟内部的会员ID或者渠道ID的一个外部ID
     */
    @ApiModelProperty(value="用户查询联盟的ID，关联到淘宝联盟内部的会员ID或者渠道ID的一个外部ID")
    private String externalId;

    /**
     * 如果做了关联，那么就是会员的ID
     */
    @ApiModelProperty(value="如果做了关联，那么就是会员的ID")
    private String specialId;

    /**
     * 如果做了代理，那么是渠道ID
     */
    @ApiModelProperty(value="如果做了代理，那么是渠道ID")
    private String relationId;

    /**
     * 数据来源：淘宝联盟、京东、拼多多、饿了么、美团、唯品会等等
     */
    @ApiModelProperty(value="数据来源：淘宝联盟、京东、拼多多、饿了么、美团、唯品会等等")
    private String dataFrom;

    /**
     * 默认为0，表示不删除。-1表示删除
     */
    @ApiModelProperty(value="默认为0，表示不删除。-1表示删除")
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
        UserInfos other = (UserInfos) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getOpenName() == null ? other.getOpenName() == null : this.getOpenName().equals(other.getOpenName()))
            && (this.getExternalId() == null ? other.getExternalId() == null : this.getExternalId().equals(other.getExternalId()))
            && (this.getSpecialId() == null ? other.getSpecialId() == null : this.getSpecialId().equals(other.getSpecialId()))
            && (this.getRelationId() == null ? other.getRelationId() == null : this.getRelationId().equals(other.getRelationId()))
            && (this.getDataFrom() == null ? other.getDataFrom() == null : this.getDataFrom().equals(other.getDataFrom()))
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
        result = prime * result + ((getOpenName() == null) ? 0 : getOpenName().hashCode());
        result = prime * result + ((getExternalId() == null) ? 0 : getExternalId().hashCode());
        result = prime * result + ((getSpecialId() == null) ? 0 : getSpecialId().hashCode());
        result = prime * result + ((getRelationId() == null) ? 0 : getRelationId().hashCode());
        result = prime * result + ((getDataFrom() == null) ? 0 : getDataFrom().hashCode());
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
        sb.append(", openName=").append(openName);
        sb.append(", externalId=").append(externalId);
        sb.append(", specialId=").append(specialId);
        sb.append(", relationId=").append(relationId);
        sb.append(", dataFrom=").append(dataFrom);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}