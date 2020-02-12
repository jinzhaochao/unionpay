package com.unionpay.sms.model;

import java.io.Serializable;

/**
 * 短信发送信息实体类
 */
public class SmsInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String unid;
    private String content;
    private String userid;
    private String mode;
    private String status;
    //private Date endTime;
    private String approvalResult;
    private String timingTime;

    public SmsInfo() {
    }

    public SmsInfo(String unid, String content, String userid, String mode, String status, String approvalResult, String timingTime) {
        this.unid = unid;
        this.content = content;
        this.userid = userid;
        this.mode = mode;
        this.status = status;
        this.approvalResult = approvalResult;
        this.timingTime = timingTime;
    }

    public String getUnid() { return unid; }

    public void setUnid(String unid) { this.unid = unid; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getTimingTime() {
        return timingTime;
    }

    public void setTimingTime(String timingTime) {
        this.timingTime = timingTime;
    }

    /*public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }*/

    @Override
    public String toString() {
        return "SmsInfo{" +
                "unid='" + unid +
                ", content='" + content +
                ", userid='" + userid +
                ", mode='" + mode +
                ", status='" + status +
                ", approvalResult='" + approvalResult +
                ", timingTime='" + timingTime +
                '}';
    }
}
