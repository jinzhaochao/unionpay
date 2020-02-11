package com.unionpay.supervision.domain;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the super_reply_delay database table.
 * 
 */
@Entity
@Table(name="super_reply_delay")
@NamedQuery(name="SuperReplyDelay.findAll", query="SELECT s FROM SuperReplyDelay s")
public class SuperReplyDelay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String unid;

	@Column(name="create_time")
	private String createTime;

	@Column(name="create_userid")
	private String createUserid;

	@Column(name="create_username")
	private String createUsername;

	@Lob
	@Column(name="delay_reason")
	private String delayReason;

	@Column(name="end_time")
	private String endTime;

	@Column(name="feedback_deadline")
	private String feedbackDeadline;

	@Lob
	private String note;

	@Column(name="reply_time")
	private String replyTime;

	@Column(name="service_status")
	private String serviceStatus;

	@Column(name="sponsor_unid")
	private String sponsorUnid;

	public SuperReplyDelay() {
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserid() {
		return createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public String getDelayReason() {
		return delayReason;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFeedbackDeadline() {
		return feedbackDeadline;
	}

	public void setFeedbackDeadline(String feedbackDeadline) {
		this.feedbackDeadline = feedbackDeadline;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getSponsorUnid() {
		return sponsorUnid;
	}

	public void setSponsorUnid(String sponsorUnid) {
		this.sponsorUnid = sponsorUnid;
	}

	

}