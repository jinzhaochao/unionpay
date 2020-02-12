package com.unionpay.sms.domain;
import java.io.Serializable;

import javax.persistence.*;

/**
 * @author wangyue
 * @date 2018/12/7
 * The persistent class for the sms_matter database table.
 */
@Entity
@Table(name="sms_matter")

@NamedQuery(name="smsMatter.findAll", query="SELECT s FROM smsMatter s")
public class smsMatter implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String unid;
	private String  title;
	@Column(name="org_id")
	private Integer orgId;
	@Column(name="org_name")
	private String orgName;
	@Column(name="user_id")
	private String userId;
	@Column(name="user_name")
	private String userName;
	private String reason;
	private String content;
	private String mode;
	@Column(name="timing_time")
	private String timingTime;
	@Column(name="approver_id")
	private String approverId;
	@Column(name="approver_name")
	private String approverName;
	@Column(name="approval_result")
	private String approvalResult;
	@Column(name="approval_opinion")
	private String approvalOpinion;
	@Column(name="create_time")
	private String createTime;
	@Column(name="submit_time")
	private String submitTime;
	@Column(name="end_time")
	private String endTime;
	private String status;
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

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getTimingTime() {
		return timingTime;
	}

	public void setTimingTime(String timingTime) {
		this.timingTime = timingTime;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}

	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "smsMatter{" +
				"unid=" + unid +
				", title=" + title +
				", orgId=" + orgId +
				", orgName=" + orgName +
				", userId=" + userId +
				", userName=" + userName +
				", reason=" + reason +
				", content=" + content +
				", mode=" + mode +
				", timingTime=" + timingTime +
				", approverId=" + approverId +
				", approverName=" + approverName +
				", approvalResult=" + approvalResult +
				", approvalOpinion=" + approvalOpinion +
				", createTime=" + createTime +
				", submitTime=" + submitTime +
				", endTime=" + endTime +
				", status=" + status +
				", note=" + note +
				'}';
	}
}
