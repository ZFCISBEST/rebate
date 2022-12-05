package com.help.rebate.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * v2_ylj_detail_info
 * @author 
 */
@Data
public class V2YljDetailInfo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    /**
     * 开放ID，从其他对接系统获取到的数据
     */
    private String openId;

    /**
     * 媒体图片链接
     */
    private String mediaPicUrl;

    /**
     * 生成的流水单号
     */
    private String yljStubFlowId;

    /**
     * 审核状态，0-新建，1-审核中，2-审核通过，3-审核不通过
     */
    private Byte verifyStatus;

    /**
     * 审核状态信息，如审批不过，给出原因
     */
    private String verifyStatusMsg;

    /**
     * 红包状态，0-未发，1、发送中，2-发送成功，3、发送失败
     */
    private Byte conponStatus;

    /**
     * 红包状态，如发送失败，给出原因
     */
    private String conponStatusMsg;

    /**
     * 红包金额（单位: 分）
     */
    private Integer conponAmount;

    /**
     * 状态字段，0表示不删除，1表示逻辑删除
     */
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
        V2YljDetailInfo other = (V2YljDetailInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreated() == null ? other.getGmtCreated() == null : this.getGmtCreated().equals(other.getGmtCreated()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getMediaPicUrl() == null ? other.getMediaPicUrl() == null : this.getMediaPicUrl().equals(other.getMediaPicUrl()))
            && (this.getYljStubFlowId() == null ? other.getYljStubFlowId() == null : this.getYljStubFlowId().equals(other.getYljStubFlowId()))
            && (this.getVerifyStatus() == null ? other.getVerifyStatus() == null : this.getVerifyStatus().equals(other.getVerifyStatus()))
            && (this.getVerifyStatusMsg() == null ? other.getVerifyStatusMsg() == null : this.getVerifyStatusMsg().equals(other.getVerifyStatusMsg()))
            && (this.getConponStatus() == null ? other.getConponStatus() == null : this.getConponStatus().equals(other.getConponStatus()))
            && (this.getConponStatusMsg() == null ? other.getConponStatusMsg() == null : this.getConponStatusMsg().equals(other.getConponStatusMsg()))
            && (this.getConponAmount() == null ? other.getConponAmount() == null : this.getConponAmount().equals(other.getConponAmount()))
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
        result = prime * result + ((getMediaPicUrl() == null) ? 0 : getMediaPicUrl().hashCode());
        result = prime * result + ((getYljStubFlowId() == null) ? 0 : getYljStubFlowId().hashCode());
        result = prime * result + ((getVerifyStatus() == null) ? 0 : getVerifyStatus().hashCode());
        result = prime * result + ((getVerifyStatusMsg() == null) ? 0 : getVerifyStatusMsg().hashCode());
        result = prime * result + ((getConponStatus() == null) ? 0 : getConponStatus().hashCode());
        result = prime * result + ((getConponStatusMsg() == null) ? 0 : getConponStatusMsg().hashCode());
        result = prime * result + ((getConponAmount() == null) ? 0 : getConponAmount().hashCode());
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
        sb.append(", mediaPicUrl=").append(mediaPicUrl);
        sb.append(", yljStubFlowId=").append(yljStubFlowId);
        sb.append(", verifyStatus=").append(verifyStatus);
        sb.append(", verifyStatusMsg=").append(verifyStatusMsg);
        sb.append(", conponStatus=").append(conponStatus);
        sb.append(", conponStatusMsg=").append(conponStatusMsg);
        sb.append(", conponAmount=").append(conponAmount);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}