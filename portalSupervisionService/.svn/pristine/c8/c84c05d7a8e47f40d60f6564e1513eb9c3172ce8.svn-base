package com.unionpay.pager.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pager_manager database table.
 * 
 */
@Entity
@Table(name="pager_manager")
@NamedQuery(name="PagerManager.findAll", query="SELECT p FROM PagerManager p")
public class PagerManager implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="page_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pageId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="org_id")
	private int orgId;

	@Column(name="org_name")
	private String orgName;

	@Column(name="page_name")
	private String pageName;

	private int status;

	private String url;

	public PagerManager() {
	}

	public int getPageId() {
		return this.pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getOrgId() {
		return this.orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPageName() {
		return this.pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}