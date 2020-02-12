package com.unionpay.voice.model;

/**
 * 新增基本信息实体类
 * @author lishuang
 * @date 2018-05-08
 */
public class VoiceInfoDto {
    private String unid;
    private String title;
    private Integer status;
    private Integer homeShow;
    private Integer caseTag;
    private Integer clientIdentity;
    private Integer caseType;
    private String callTime;
    private String transferTime;
    private String infoContent;
    private Integer handleOrgid;
    private String handleTime;
    private String handleContent;
    private String token;
    private String audioUnids;
    private String picUnids;
    private String docUnids;
    private Boolean add;
    private Boolean update;

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

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAudioUnids() {
        return audioUnids;
    }

    public void setAudioUnids(String audioUnids) {
        this.audioUnids = audioUnids;
    }

    public String getPicUnids() {
        return picUnids;
    }

    public void setPicUnids(String picUnids) {
        this.picUnids = picUnids;
    }

    public String getDocUnids() {
        return docUnids;
    }

    public void setDocUnids(String docUnids) {
        this.docUnids = docUnids;
    }

    public Boolean getAdd() {
        return add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }
}
