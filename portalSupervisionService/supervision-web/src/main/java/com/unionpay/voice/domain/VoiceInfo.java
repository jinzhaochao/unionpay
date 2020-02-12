package com.unionpay.voice.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "voice_info")
@NamedQuery(name="VoiceInfo.findAll", query="SELECT v FROM VoiceInfo v")
public class VoiceInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "unid")
    private String unid;
    @Column(name = "title")
    private String title;
    @Column(name = "info_content")
    private String infoContent;
    @Column(name = "status")
    private Integer status;
    @Column(name = "home_show")
    private Integer homeShow;
    @Column(name = "case_tag")
    private Integer caseTag;
    @Column(name = "client_identity")
    private Integer clientIdentity;
    @Column(name = "case_type")
    private Integer caseType;
    @Column(name = "business_type")
    private Integer businessType;
    @Column(name = "call_time")
    private String callTime;
    @Column(name = "transfer_time")
    private String transferTime;
    @Column(name = "handle_orgid")
    private Integer handleOrgid;
    @Column(name = "handle_time")
    private String handleTime;
    @Column(name = "handle_content")
    private String handleContent;
    @Column(name = "create_userid")
    private String createUserid;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "read_number")
    private int readNumber;


    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHomeShow() {
        return homeShow;
    }

    public void setHomeShow(Integer homeShow) {
        this.homeShow = homeShow;
    }

    public Integer getCaseTag() {
        return caseTag;
    }

    public void setCaseTag(Integer caseTag) {
        this.caseTag = caseTag;
    }

    public Integer getClientIdentity() {
        return clientIdentity;
    }

    public void setClientIdentity(Integer clientIdentity) {
        this.clientIdentity = clientIdentity;
    }

    public Integer getCaseType() {
        return caseType;
    }

    public void setCaseType(Integer caseType) {
        this.caseType = caseType;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public Integer getHandleOrgid() {
        return handleOrgid;
    }

    public void setHandleOrgid(Integer handleOrgid) {
        this.handleOrgid = handleOrgid;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getHandleContent() {
        return handleContent;
    }

    public void setHandleContent(String handleContent) {
        this.handleContent = handleContent;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(int readNumber) {
        this.readNumber = readNumber;
    }

    @Override
    public String toString() {
        return "VoiceInfo{" +
                "unid=" + unid +
                ", title=" + title +
                ", infoContent=" + infoContent +
                ", status=" + status +
                ", homeShow=" + homeShow +
                ", caseTag=" + caseTag +
                ", clientIdentity=" + clientIdentity +
                ", caseType=" + caseType +
                ", businessType=" + businessType +
                ", callTime=" + callTime +
                ", transferTime=" + transferTime +
                ", handleOrgid=" + handleOrgid +
                ", handleTime=" + handleTime +
                ", handleContent=" + handleContent +
                ", createUserid=" + createUserid +
                ", createTime=" + createTime +
                ", readNumber=" + readNumber +
                '}';
    }
}
