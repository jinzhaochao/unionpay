package com.unionpay.services.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 意见反馈列表信息实体类
 * @author lishuang
 * @date 2019-03-20
 */
public class Server0pinionFeedbackModelDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //反馈意见主键id
    private Integer id;
    //服务信息主键id
    private Integer serverId;
    //服务编码
    private String serId;
    //服务名称
    private String serName;
    //所属部门
    private String orgName;
    //反馈意见
    private String content;
    //反馈人empid
    private Integer empId;
    //反馈人
    private String empName;
    //反馈人部门
    private String feedBackOrgName;
    //反馈时间
    private String createtime;
    //处理状态
    private Integer status;
    //处理人empid
    private Integer replyId;
    //处理人姓名
    private String replyName;
    //处理人所属部门
    private String replyOrgName;
    //处理时间
    private String replyTime;
    //处理说明
    private String replyReason;

    public Server0pinionFeedbackModelDto() {
    }

    public Server0pinionFeedbackModelDto(Integer id, Integer serverId, String serId, String serName, String orgName, String content, Integer empId, String empName, String feedBackOrgName, String createtime, Integer status, Integer replyId, String replyName, String replyOrgName, String replyTime, String replyReason) {
        this.id = id;
        this.serverId = serverId;
        this.serId = serId;
        this.serName = serName;
        this.orgName = orgName;
        this.content = content;
        this.empId = empId;
        this.empName = empName;
        this.feedBackOrgName = feedBackOrgName;
        this.createtime = createtime;
        this.status = status;
        this.replyId = replyId;
        this.replyName = replyName;
        this.replyOrgName = replyOrgName;
        this.replyTime = replyTime;
        this.replyReason = replyReason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getFeedBackOrgName() {
        return feedBackOrgName;
    }

    public void setFeedBackOrgName(String feedBackOrgName) {
        this.feedBackOrgName = feedBackOrgName;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getReplyOrgName() {
        return replyOrgName;
    }

    public void setReplyOrgName(String replyOrgName) {
        this.replyOrgName = replyOrgName;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyReason() {
        return replyReason;
    }

    public void setReplyReason(String replyReason) {
        this.replyReason = replyReason;
    }

    @Override
    public String toString() {
        return "Server0pinionFeedbackModelDto{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", serId=" + serId +
                ", serName='" + serName +
                ", orgName='" + orgName +
                ", content='" + content +
                ", empId='" + empId +
                ", empName='" + empName +
                ", feedBackOrgName='" + feedBackOrgName +
                ", createtime=" + createtime +
                ", status=" + status +
                ", replyId=" + replyId +
                ", replyName=" + replyName +
                ", replyOrgName='" + replyOrgName +
                ", replyTime=" + replyTime +
                ", replyReason='" + replyReason +
                '}';
    }
}
