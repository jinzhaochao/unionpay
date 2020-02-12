package com.unionpay.supervision.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the super_record database table.
 * 
 */
@Entity
@Table(name="super_record")
@NamedQuery(name="SuperRecord.findAll", query="SELECT s FROM SuperRecord s")
public class SuperRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="record_id")
	private int recordId;

	@Column(name="create_time")
	private String createTime;

	@Column(name="feedback_deadline")
	private String feedbackDeadline;

	@Lob
	private String reason;

	@Column(name="service_status")
	private String serviceStatus;

	@Column(name="sponsor_unid")
	private String sponsorUnid;

	private int status;

	private int type;

	public SuperRecord() {
	}

	public int getRecordId() {
		return this.recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFeedbackDeadline() {
		return this.feedbackDeadline;
	}

	public void setFeedbackDeadline(String feedbackDeadline) {
		this.feedbackDeadline = feedbackDeadline;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getServiceStatus() {
		return this.serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
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

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

}