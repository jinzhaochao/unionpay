package com.unionpay.supervision.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the om_organization database table.
 * 
 */
@Entity
@Table(name="om_organization")
@NamedQuery(name="OmOrganization.findAll", query="SELECT o FROM OmOrganization o")
public class OmOrganization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int orgid;

	private String area;

	private Integer cmsid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	private String duties;

	private String ehrid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;

	private String faxno;

	private String isleaf;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdate;

	private String linktel;

	private String opertype;

	private String orgaddr;

	private String orgcode;

	private String orglevel;

	private String orgname;

	private String orgseq;

	private String orgtype;

	private Integer parentorgid;

	private String sortno;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startdate;

	private String status;

	private int subcount;

	private String weburl;

	private String zipcode;

	public OmOrganization() {
	}

	public int getOrgid() {
		return this.orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getCmsid() {
		return this.cmsid;
	}

	public void setCmsid(Integer cmsid) {
		this.cmsid = cmsid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDuties() {
		return this.duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getEhrid() {
		return this.ehrid;
	}

	public void setEhrid(String ehrid) {
		this.ehrid = ehrid;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getFaxno() {
		return this.faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	public String getIsleaf() {
		return this.isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public Date getLastupdate() {
		return this.lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getLinktel() {
		return this.linktel;
	}

	public void setLinktel(String linktel) {
		this.linktel = linktel;
	}

	public String getOpertype() {
		return this.opertype;
	}

	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}

	public String getOrgaddr() {
		return this.orgaddr;
	}

	public void setOrgaddr(String orgaddr) {
		this.orgaddr = orgaddr;
	}

	public String getOrgcode() {
		return this.orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getOrglevel() {return this.orglevel;}

	public void setOrglevel(String orglevel) {
		this.orglevel = orglevel;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrgseq() {
		return this.orgseq;
	}

	public void setOrgseq(String orgseq) {
		this.orgseq = orgseq;
	}

	public String getOrgtype() {
		return this.orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public Integer getParentorgid() {
		return this.parentorgid;
	}

	public void setParentorgid(Integer parentorgid) {
		this.parentorgid = parentorgid;
	}

	public String getSortno() {
		return this.sortno;
	}

	public void setSortno(String sortno) {
		this.sortno = sortno;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSubcount() {
		return this.subcount;
	}

	public void setSubcount(int subcount) {
		this.subcount = subcount;
	}

	public String getWeburl() {
		return this.weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}