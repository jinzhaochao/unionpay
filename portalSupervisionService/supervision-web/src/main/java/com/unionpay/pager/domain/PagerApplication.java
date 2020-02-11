package com.unionpay.pager.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pager_application database table.
 * 
 */
@Entity
@Table(name="pager_application")
@NamedQuery(name="PagerApplication.findAll", query="SELECT p FROM PagerApplication p")
public class PagerApplication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int applicationId;

	@Column(name="application_name")
	private String applicationName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="file_id")
	private String fileId;

	private int level;

	@Column(name="parent_id")
	private Integer parentId;

	private int sort;

	private int status;

	@Column(name="tag_id")
	private int tagId;

	private String url;

	public PagerApplication() {
	}

	public int getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}