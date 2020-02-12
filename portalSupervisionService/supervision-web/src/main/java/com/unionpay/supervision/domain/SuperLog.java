package com.unionpay.supervision.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the super_log database table.
 * 
 */
@Entity
@Table(name="super_log")
@NamedQuery(name="SuperLog.findAll", query="SELECT s FROM SuperLog s")
public class SuperLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="super_log_id")
	private int superLogId;

	@Column(name="change_type")
	private Integer changeType;

	@Column(name="chg_data")
	private String chgData;

	@Column(name="client_type")
	private int clientType;

	@Column(name="create_time")
	private String createTime;

	@Column(name="hist_data")
	private String histData;

	@Column(name="oversee_userid")
	private String overseeUserid;

	@Column(name="service_unid")
	private String serviceUnid;

	@Column(name="sponsor_id")
	private String sponsorId;

	@Column(name="sponsor_unid")
	private String sponsorUnid;

	private int status;

	public SuperLog() {
	}

	public int getSuperLogId() {
		return this.superLogId;
	}

	public void setSuperLogId(int superLogId) {
		this.superLogId = superLogId;
	}

	public Integer getChangeType() {
		return this.changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	public String getChgData() {
		return this.chgData;
	}

	public void setChgData(String chgData) {
		this.chgData = chgData;
	}

	public int getClientType() {
		return this.clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getHistData() {
		return this.histData;
	}

	public void setHistData(String histData) {
		this.histData = histData;
	}

	public String getOverseeUserid() {
		return this.overseeUserid;
	}

	public void setOverseeUserid(String overseeUserid) {
		this.overseeUserid = overseeUserid;
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

}