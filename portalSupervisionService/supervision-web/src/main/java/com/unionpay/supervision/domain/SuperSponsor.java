package com.unionpay.supervision.domain;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the super_sponsor database table.
 * 
 */
@Entity
@Table(name="super_sponsor")
@NamedQuery(name="SuperSponsor.findAll", query="SELECT s FROM SuperSponsor s")
public class SuperSponsor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String unid;

	@Column(name="approval_deadline")
	private String approvalDeadline;

	private String verifiers;

	@Column(name="approval_time")
	private String approvalTime;

	@Column(name="create_time")
	private String createTime;

	@Column(name="create_userid")
	private String createUserid;

	@Column(name="create_username")
	private String createUsername;

	@Column(name="feedback_deadline")
	private String feedbackDeadline;

	@Lob
	@Column(name="feedback_rule")
	private String feedbackRule;

	@Column(name="feedback_time")
	private String feedbackTime;

	@Column(name="is_read")
	private String isRead;

	@Column(name="need_verify")
	private String needVerify;

	@Column(name="next_time")
	private String nextTime;

	@Lob
	private String note;

	@Column(name="org_id")
	private String orgId;

	@Column(name="org_name")
	private String orgName;

	@Column(name="oversee_time")
	private String overseeTime;

	@Lob
	private String progress;

	@Column(name="proposed_closing_time")
	private String proposedClosingTime;

	@Lob
	@Column(name="result_form")
	private String resultForm;

	@Column(name="service_id")
	private String serviceId;
	//private String serviceId;

	@Column(name="service_status")
	private String serviceStatus;

	@Column(name="service_unid")
	private String serviceUnid;

	@Column(name="sponsor_id")
	private String sponsorId;

	@Column(name="sponsor_name")
	private String sponsorName;

	private int status;

	@Lob
	@Column(name="work_plan")
	private String workPlan;

	@Column(name="work_status")
	private String workStatus;

	//信息反馈人userid
	@Column(name = "feedback_userid")
	private String feedbackUserid;
	//信息反馈人姓名
	@Column(name = "feedback_username")
	private String feedbackUsername;

	public SuperSponsor() {
	}

	public String getUnid() {
		return this.unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getApprovalDeadline() {
		return this.approvalDeadline;
	}

	public void setApprovalDeadline(String approvalDeadline) {
		this.approvalDeadline = approvalDeadline;
	}
	

	public String getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(String approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserid() {
		return this.createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getCreateUsername() {
		return this.createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public String getFeedbackDeadline() {
		return this.feedbackDeadline;
	}

	public void setFeedbackDeadline(String feedbackDeadline) {
		this.feedbackDeadline = feedbackDeadline;
	}

	public String getFeedbackRule() {
		return this.feedbackRule;
	}

	public void setFeedbackRule(String feedbackRule) {
		this.feedbackRule = feedbackRule;
	}

	public String getFeedbackTime() {
		return this.feedbackTime;
	}

	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getIsRead() {
		return this.isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	

	public String getNextTime() {
		return this.nextTime;
	}

	public void setNextTime(String nextTime) {
		this.nextTime = nextTime;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOverseeTime() {
		return this.overseeTime;
	}

	public void setOverseeTime(String overseeTime) {
		this.overseeTime = overseeTime;
	}

	public String getProgress() {
		return this.progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getProposedClosingTime() {
		return this.proposedClosingTime;
	}

	public void setProposedClosingTime(String proposedClosingTime) {
		this.proposedClosingTime = proposedClosingTime;
	}

	public String getResultForm() {
		return this.resultForm;
	}

	public void setResultForm(String resultForm) {
		this.resultForm = resultForm;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceStatus() {
		return this.serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getServiceUnid() {
		return this.serviceUnid;
	}

	public void setServiceUnid(String serviceUnid) {
		this.serviceUnid = serviceUnid;
	}

	public String getSponsorId() {
		return this.sponsorId;
	}

	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsorName() {
		return this.sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getWorkPlan() {
		return this.workPlan;
	}

	public void setWorkPlan(String workPlan) {
		this.workPlan = workPlan;
	}

	public String getWorkStatus() {
		return this.workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getVerifiers() {
		return verifiers;
	}

	public void setVerifiers(String verifiers) {
		this.verifiers = verifiers;
	}

	public String getNeedVerify() {
		return needVerify;
	}

	public void setNeedVerify(String needVerify) {
		this.needVerify = needVerify;
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
}