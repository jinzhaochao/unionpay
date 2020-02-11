package com.unionpay.supervision.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the super_concerne_leadership database table.
 * 
 */
@Entity
@Table(name="super_concerne_leadership")
@NamedQuery(name="SuperConcerneLeadership.findAll", query="SELECT s FROM SuperConcerneLeadership s")
public class SuperConcerneLeadership implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="leader_userid")
	private String leaderUserid;

	@Column(name="leader_username")
	private String leaderUsername;

	@Column(name="org_id")
	private String orgId;

	@Column(name="org_name")
	private String orgName;

	@Column(name="oversee_userid")
	private String overseeUserid;

	@Column(name="oversee_username")
	private String overseeUsername;

	@Column(name="service_level")
	private String serviceLevel;

	@Column(name="service_name")
	private String serviceName;

	@Column(name="service_status")
	private String serviceStatus;

	@Column(name="service_type")
	private String serviceType;

	@Column(name="service_unid")
	private String serviceUnid;

	@Column(name="sponsor_unid")
	private String sponsorUnid;

	private int status;

	@Column(name="work_status")
	private String workStatus;

	public SuperConcerneLeadership() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLeaderUserid() {
		return this.leaderUserid;
	}

	public void setLeaderUserid(String leaderUserid) {
		this.leaderUserid = leaderUserid;
	}

	public String getLeaderUsername() {
		return this.leaderUsername;
	}

	public void setLeaderUsername(String leaderUsername) {
		this.leaderUsername = leaderUsername;
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

	public String getOverseeUserid() {
		return this.overseeUserid;
	}

	public void setOverseeUserid(String overseeUserid) {
		this.overseeUserid = overseeUserid;
	}

	public String getOverseeUsername() {
		return this.overseeUsername;
	}

	public void setOverseeUsername(String overseeUsername) {
		this.overseeUsername = overseeUsername;
	}

	public String getServiceLevel() {
		return this.serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceStatus() {
		return this.serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceUnid() {
		return this.serviceUnid;
	}

	public void setServiceUnid(String serviceUnid) {
		this.serviceUnid = serviceUnid;
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

}