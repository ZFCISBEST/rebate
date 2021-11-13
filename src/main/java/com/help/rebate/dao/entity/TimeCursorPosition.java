package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * time_cursor_position
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.TimeCursorPosition同步时间点位表")
@Data
public class TimeCursorPosition implements Serializable {
    /**
     * 主键，自增，唯一
     */
    @ApiModelProperty(value="主键，自增，唯一")
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
     * 起始点位
     */
    @ApiModelProperty(value="起始点位")
    private Date startTime;

    /**
     * 结束点位
     */
    @ApiModelProperty(value="结束点位")
    private Date endTime;

    /**
     * 步长，秒
     */
    @ApiModelProperty(value="步长，秒")
    private Integer step;

    /**
     * 如果是订单同步 - 1：创建时间,2:付款时间,3:结算时间,4:更新时间
     */
    @ApiModelProperty(value="如果是订单同步 - 1：创建时间,2:付款时间,3:结算时间,4:更新时间")
    private Integer queryType;

    /**
     * 如果是订单同步，场景订单场景类型，1:常规订单，2:渠道订单，3:会员运营订单，0:都查
     */
    @ApiModelProperty(value="如果是订单同步，场景订单场景类型，1:常规订单，2:渠道订单，3:会员运营订单，0:都查")
    private Integer subType;

    /**
     * 时间类型- 0订单同步、1订单关联、2订单结算、3其他。。。
     */
    @ApiModelProperty(value="时间类型- 0订单同步、1订单关联、2订单结算、3其他。。。")
    private Integer timeType;

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
        TimeCursorPosition other = (TimeCursorPosition) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getStep() == null ? other.getStep() == null : this.getStep().equals(other.getStep()))
            && (this.getQueryType() == null ? other.getQueryType() == null : this.getQueryType().equals(other.getQueryType()))
            && (this.getSubType() == null ? other.getSubType() == null : this.getSubType().equals(other.getSubType()))
            && (this.getTimeType() == null ? other.getTimeType() == null : this.getTimeType().equals(other.getTimeType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGmtCreated() == null) ? 0 : getGmtCreated().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getStep() == null) ? 0 : getStep().hashCode());
        result = prime * result + ((getQueryType() == null) ? 0 : getQueryType().hashCode());
        result = prime * result + ((getSubType() == null) ? 0 : getSubType().hashCode());
        result = prime * result + ((getTimeType() == null) ? 0 : getTimeType().hashCode());
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
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", step=").append(step);
        sb.append(", queryType=").append(queryType);
        sb.append(", subType=").append(subType);
        sb.append(", timeType=").append(timeType);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}