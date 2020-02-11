package com.unionpay.supervision.domain;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the super_sponsor_log database table.
 * 
 */
@Entity
@Table(name="super_sponsor_log")
@NamedQuery(name="SuperSponsorLog.findAll", query="SELECT s FROM SuperSponsorLog s")
public class SuperSponsorLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String unid;

	@Column(name="create_time")
	private String createTime;

	@Column(name="create_userid")
	private String createUserid;

	@Column(name="create_username")
	private String createUsername;

	@Column(name="feedback_deadline")
	private String feedbackDeadline;

	@Column(name="feedback_efficiency")
	private int feedbackEfficiency;

	@Lob
	@Column(name="feedback_rule")
	private String feedbackRule;

	@Column(name="feedback_time")
	private String feedbackTime;

	@Lob
	private String note;

	@Column(name="oversee_time")
	private String overseeTime;

	@Lob
	private String progress;

	@Column(name="service_status")
	private String serviceStatus;

	@Column(name="sponsor_id")
	private String sponsorId;

	@Column(name="sponsor_name")
	private String sponsorName;

	@Column(name="sponsor_unid")
	private String sponsorUnid;

	private int status;

	@Column(name="work_status")
	private String workStatus;
	
	@Column(name="need_verify")
	private String needVerify;
	
	private String verifiers;

	//信息反馈人userid
	@Column(name = "feedback_userid")
	private String feedbackUserid;
	//信息反馈人姓名
	@Column(name = "feedback_username")
	private String feedbackUsername;

	public SuperSponsorLog() {
	}

	public String getUnid() {
		return this.unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
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

	public int getFeedbackEfficiency() {
		return this.feedbackEfficiency;
	}

	public void setFeedbackEfficiency(int feedbackEfficiency) {
		this.feedbackEfficiency = feedbackEfficiency;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getServiceStatus() {
		return this.serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
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

	public String getSponsorUnid() {
		return this.sponsorUnid;
	}

	public void setSponsorUnid(String sponsorUnid) {
		this.sponsorUnid = sponsorUnid;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getWorkStatus() {
		return this.workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getNeedVerify() {
		return needVerify;
	}

	public void setNeedVerify(String needVerify) {
		this.needVerify = needVerify;
	}

	public String getVerifiers() {
		return verifiers;
	}

	public void setVerifiers(String verifiers) {
		this.verifiers = verifiers;
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