package com.unionpay.sms.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 短信-->流程平台审批结果推回数据实体类
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "不能为空")
    private String unid;
    @NotBlank(message = "不能为空")
    private String title;
    @NotBlank(message = "不能为空")
    private String reason;
    @NotBlank(message = "不能为空")
    private String content;
    @NotBlank(message = "不能为空")
    private String mode;
    private String timing_time;
    @NotBlank(message = "不能为空")
    private String approver_id;
    @NotBlank(message = "不能为空")
    private String approver_name;
    @NotBlank(message = "不能为空")
    private String approval_result;
    @NotBlank(message = "不能为空")
    private String approval_opinion;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "不符合yyyy-MM-dd hh:mm:ss格式")
    private String approval_time;
    private String note;

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTiming_time() {
        return timing_time;
    }

    public void setTiming_time(String timing_time) {
        this.timing_time = timing_time;
    }

    public String getApprover_id() {
        return approver_id;
    }

    public void setApprover_id(String approver_id) {
        this.approver_id = approver_id;
    }

    public String getApprover_name() {
        return approver_name;
    }

    public void setApprover_name(String approver_name) {
        this.approver_name = approver_name;
    }

    public String getApproval_result() {
        return approval_result;
    }

    public void setApproval_result(String approval_result) {
        this.approval_result = approval_result;
    }

    public String getApproval_opinion() {
        return approval_opinion;
    }

    public void setApproval_opinion(String approval_opinion) {
        this.approval_opinion = approval_opinion;
    }

    public String getApproval_time() {
        return approval_time;
    }

    public void setApproval_time(String approval_time) {
        this.approval_time = approval_time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Message{" +
                "unid=" + unid +
                ", title=" + title +
                ", reason=" + reason +
                ", content=" + content +
                ", mode=" + mode +
                ", timing_time=" + timing_time +
                ", approver_id=" + approver_id +
                ", approver_name=" + approver_name +
                ", approval_result=" + approval_result +
                ", approval_opinion=" + approval_opinion +
                ", approval_time=" + approval_time +
                ", note=" + note +
                "}";
    }
}
