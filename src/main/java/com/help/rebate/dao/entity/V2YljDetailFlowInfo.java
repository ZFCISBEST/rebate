package com.help.rebate.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * v2_ylj_detail_flow_info
 * @author 
 */
@ApiModel(value="com.help.rebate.dao.entity.V2YljDetailFlowInfo流水信息表")
@Data
public class V2YljDetailFlowInfo implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private LocalDateTime gmtModified;

    /**
     * 依赖表‘open_detail_info’中的流水单号
     */
    @ApiModelProperty(value="依赖表‘open_detail_info’中的流水单号")
    private String yljStubFlowId;

    /**
     * 操作类型，0-审核，1-发红包
     */
    @ApiModelProperty(value="操作类型，0-审核，1-发红包")
    private Byte operationType;

    /**
     * 如果是审核，记录审核状态，0-新建，1-审核中，2-审核通过，3-审核不通过
     */
    @ApiModelProperty(value="如果是审核，记录审核状态，0-新建，1-审核中，2-审核通过，3-审核不通过")
    private Byte verifyStatus;

    /**
     * 审核状态信息，如审批不过，给出原因
     */
    @ApiModelProperty(value="审核状态信息，如审批不过，给出原因")
    private String verifyStatusMsg;

    /**
     * 红包状态，0-未发，1、发送中，2-发送成功，3、发送失败
     */
    @ApiModelProperty(value="红包状态，0-未发，1、发送中，2-发送成功，3、发送失败")
    private Byte conponStatus;

    /**
     * 红包状态，如发送失败，给出原因
     */
    @ApiModelProperty(value="红包状态，如发送失败，给出原因")
    private String conponStatusMsg;

    /**
     * 红包金额（单位: 分）
     */
    @ApiModelProperty(value="红包金额（单位: 分）")
    private Integer conponAmount;

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
        V2YljDetailFlowInfo other = (V2YljDetailFlowInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getYljStubFlowId() == null ? other.getYljStubFlowId() == null : this.getYljStubFlowId().equals(other.getYljStubFlowId()))
            && (this.getOperationType() == null ? other.getOperationType() == null : this.getOperationType().equals(other.getOperationType()))
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
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getYljStubFlowId() == null) ? 0 : getYljStubFlowId().hashCode());
        result = prime * result + ((getOperationType() == null) ? 0 : getOperationType().hashCode());
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
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", yljStubFlowId=").append(yljStubFlowId);
        sb.append(", operationType=").append(operationType);
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