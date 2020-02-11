package com.unionpay.supervision.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the super_sponsor_master database table.
 * 
 */
@Entity
@Table(name="super_sponsor_master")
@NamedQuery(name="SuperSponsorMaster.findAll", query="SELECT s FROM SuperSponsorMaster s")
public class SuperSponsorMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private Integer office_Id;

	@Column(name="org_id")
	private Integer orgId;

	@Column(name="org_name")
	private String orgName;

	@Column(name="service_unid")
	private String serviceUnid;

	@Column(name="sponsor_id")
	private String sponsorId;

	@Column(name="sponsor_name")
	private String sponsorName;

	@Column(name="sponsor_unid")
	private String sponsorUnid;

	private int status;

	public SuperSponsorMaster() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOffice_Id() {
		return this.office_Id;
	}

	public void setOffice_Id(Integer office_Id) {
		this.office_Id = office_Id;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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