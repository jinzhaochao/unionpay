package com.unionpay.supervision.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the super_file database table.
 * 
 */
@Entity
@Table(name="super_file")
@NamedQuery(name="SuperFile.findAll", query="SELECT s FROM SuperFile s")
public class  SuperFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="file_id")
	private String fileId;

	@Column(name="create_time")
	private String createTime;

	@Column(name="create_userid")
	private String createUserid;

	@Column(name="file_name")
	private String fileName;

	@Column(name="file_store_name")
	private String fileStoreName;

	@Column(name="is_use")
	private int isUse;

	@Column(name="oversee_mapping_id")
	private String overseeMappingId;

	@Column(name="service_name")
	private String serviceName;

	@Column(name="super_service_id")
	private String superServiceId;

	private String url;

	public SuperFile() {
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserid() {
		return this.createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileStoreName() {
		return this.fileStoreName;
	}

	public void setFileStoreName(String fileStoreName) {
		this.fileStoreName = fileStoreName;
	}

	public int getIsUse() {
		return this.isUse;
	}

	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}

	public String getOverseeMappingId() {
		return this.overseeMappingId;
	}

	public void setOverseeMappingId(String overseeMappingId) {
		this.overseeMappingId = overseeMappingId;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSuperServiceId() {
		return this.superServiceId;
	}

	public void setSuperServiceId(String superServiceId) {
		this.superServiceId = superServiceId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}