package com.unionpay.pager.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pager_tag database table.
 *
 */
@Entity
@Table(name="pager_tag")
@NamedQuery(name="PagerTag.findAll", query="SELECT p FROM PagerTag p")
public class PagerTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@NotFound(action=NotFoundAction.IGNORE)
	private int tagId;

	@Column(name="column_id")
	private int columnId;

	@Column(name="column_name")
	private String columnName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private int formwork;

	@Column(name="is_together")
	private int isTogether;

	@Column(name="org_id")
	private int orgId;

	@Column(name="org_name")
	private String orgName;

	private int status;

	@Column(name="tag_name")
	private String tagName;

	@Column(name="tag_title")
	private String tagTitle;

	@Column(name="hrefurl")
	private String hrefurl;

	@Column(name="chnl_Id")
	private int chnlId;

	public PagerTag() {
	}

	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public int getColumnId() {
		return this.columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getFormwork() {
		return this.formwork;
	}

	public void setFormwork(int formwork) {
		this.formwork = formwork;
	}

	public int getIsTogether() {
		return this.isTogether;
	}

	public void setIsTogether(int isTogether) {
		this.isTogether = isTogether;
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

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagTitle() {
		return this.tagTitle;
	}

	public void setTagTitle(String tagTitle) {
		this.tagTitle = tagTitle;
	}


	public String getHrefurl() {
		return hrefurl;
	}

	public void setHrefurl(String hrefurl) {
		this.hrefurl = hrefurl;
	}

	public int getChnlId() {
		return chnlId;
	}

	public void setChnlId(int chnlId) {
		this.chnlId = chnlId;
	}

}