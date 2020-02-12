package com.unionpay.services.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 服务申请已完成流程信息表实体类
 * @author lishuang
 * @date 2019/10/10
 */
@Entity
@Table(name = "server_apply_info")
@NamedQuery(name="ServerApplyInfo.findAll", query="SELECT s FROM ServerApplyInfo s")
public class ServerApplyInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Column(name = "process_id")
    private String processId;
    @Column(name = "bizcode")
    private String bizcode;
    @Column(name = "service_info_name")
    private String serviceInfoName;
    @Column(name = "launch_userid")
    private String launchUserid;
    @Column(name = "launch_username")
    private String launchUsername;
    @Column(name = "ser_orgid")
    private String serOrgid;
    @Column(name = "ser_orgname")
    private String serOrgname;
    @Column(name = "ser_userid")
    private String serUserid;
    @Column(name = "ser_username")
    private String serUsername;
    @Column(name = "launch_time")
    private String launchTime;
    @Column(name = "end_time")
    private String endTime;
    @Column(name = "server_item_id")
    private String serverItemId;
    @Column(name = "server_item_name")
    private String serverItemName;
    @Column(name = "flow_name_id")
    private String flowNameId;
    @Column(name = "flow_name")
    private String flowName;
    @Column(name = "ser_id")
    private Integer serId;
    @Column(name = "query_url")
    private String queryUrl;
    @Column(name = "status")
    private String status;
    @Column(name = "score")
    private Integer score;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "note")
    private String note;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getBizcode() {
        return bizcode;
    }

    public void setBizcode(String bizcode) {
        this.bizcode = bizcode;
    }

    public String getServiceInfoName() {
        return serviceInfoName;
    }

    public void setServiceInfoName(String serviceInfoName) {
        this.serviceInfoName = serviceInfoName;
    }

    public String getLaunchUserid() {
        return launchUserid;
    }

    public void setLaunchUserid(String launchUserid) {
        this.launchUserid = launchUserid;
    }

    public String getLaunchUsername() {
        return launchUsername;
    }

    public void setLaunchUsername(String launchUsername) {
        this.launchUsername = launchUsername;
    }

    public String getSerOrgid() {
        return serOrgid;
    }

    public void setSerOrgid(String serOrgid) {
        this.serOrgid = serOrgid;
    }

    public String getSerOrgname() {
        return serOrgname;
    }

    public void setSerOrgname(String serOrgname) {
        this.serOrgname = serOrgname;
    }

    public String getSerUserid() {
        return serUserid;
    }

    public void setSerUserid(String serUserid) {
        this.serUserid = serUserid;
    }

    public String getSerUsername() {
        return serUsername;
    }

    public void setSerUsername(String serUsername) {
        this.serUsername = serUsername;
    }

    public String getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(String launchTime) {
        this.launchTime = launchTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getServerItemId() {
        return serverItemId;
    }

    public void setServerItemId(String serverItemId) {
        this.serverItemId = serverItemId;
    }

    public String getServerItemName() {
        return serverItemName;
    }

    public void setServerItemName(String serverItemName) {
        this.serverItemName = serverItemName;
    }

    public String getFlowNameId() {
        return flowNameId;
    }

    public void setFlowNameId(String flowNameId) {
        this.flowNameId = flowNameId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public Integer getSerId() {
        return serId;
    }

    public void setSerId(Integer serId) {
        this.serId = serId;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ServerApplyInfo{" +
                "id=" + id +
                ", processId=" + processId +
                ", bizcode=" + bizcode +
                ", serviceInfoName=" + serviceInfoName +
                ", launchUserid=" + launchUserid +
                ", launchUsername=" + launchUsername +
                ", serOrgid=" + serOrgid +
                ", serOrgname=" + serOrgname +
                ", serUserid=" + serUserid +
                ", serUsername=" + serUsername +
                ", launchTime=" + launchTime +
                ", endTime=" + endTime +
                ", serverItemId=" + serverItemId +
                ", serverItemName=" + serverItemName +
                ", flowNameId=" + flowNameId +
                ", flowName=" + flowName +
                ", serId=" + serId +
                ", queryUrl=" + queryUrl +
                ", status=" + status +
                ", score=" + score +
                ", createTime=" + createTime +
                ", note=" + note +
                '}';
    }
}
