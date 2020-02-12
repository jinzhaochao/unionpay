package com.unionpay.pager.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pager_manager_tag database table.
 * 
 */
@Entity
@Table(name="pager_manager_tag")
@NamedQuery(name="PagerManagerTag.findAll", query="SELECT p FROM PagerManagerTag p")
public class PagerManagerTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="page_id")
	private int pageId;

	private int sort;

	private int status;

	@Column(name="tag_id")
	private int tagId;

	public PagerManagerTag() {
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

	public int getPageId() {
		return this.pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
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

}