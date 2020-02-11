package com.unionpay.supervision.domain;

/**
 * @Author jinzhao
 * @Date 2019-10-09
 * 添加进展实体
 */
public class SuperSponsorModel {

    private String unid;
    private String progress;
    private String sponsorId;
    private String sponsorName;
    private String feedbackUserid;
    private String feedbackUsername;
    private String note;
    private String feedbackDeadline;
    private String feedbackTime;
    private String workStatus;
    //1添加进展  2  修改最新进展
    private Integer type;
    private String serviceStatus;
    public SuperSponsorModel() {
    }

    public SuperSponsorModel(String unid, String progress, String sponsorId, String sponsorName, String feedbackUserid, String feedbackUsername, String note, String feedbackDeadline, String feedbackTime, String workStatus, Integer type, String serviceStatus) {
        this.unid = unid;
        this.progress = progress;
        this.sponsorId = sponsorId;
        this.sponsorName = sponsorName;
        this.feedbackUserid = feedbackUserid;
        this.feedbackUsername = feedbackUsername;
        this.note = note;
        this.feedbackDeadline = feedbackDeadline;
        this.feedbackTime = feedbackTime;
        this.workStatus = workStatus;
        this.type = type;
        this.serviceStatus = serviceStatus;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getFeedbackUserid() {
        return feedbackUserid;
    }

    public void setFeedbackUserid(String feedbackUserid) {
        this.feedbackUserid = feedbackUserid;
    }

    public String getFeedbackUsername() {
        return feedbackUsername;
    }

    public void setFeedbackUsername(String feedbackUsername) {
        this.feedbackUsername = feedbackUsername;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFeedbackDeadline() {
        return feedbackDeadline;
    }

    public void setFeedbackDeadline(String feedbackDeadline) {
        this.feedbackDeadline = feedbackDeadline;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    @Override
    public String toString() {
        return "SuperSponsorModel{" +
                "unid='" + unid + '\'' +
                ", progress='" + progress + '\'' +
                ", sponsorId='" + sponsorId + '\'' +
                ", sponsorName='" + sponsorName + '\'' +
                ", feedbackUserid='" + feedbackUserid + '\'' +
                ", feedbackUsername='" + feedbackUsername + '\'' +
                ", note='" + note + '\'' +
                ", feedbackDeadline='" + feedbackDeadline + '\'' +
                ", feedbackTime='" + feedbackTime + '\'' +
                ", workStatus='" + workStatus + '\'' +
                ", type=" + type +
                ", serviceStatus='" + serviceStatus + '\'' +
                '}';
    }
}
