package com.unionpay.services.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the server_handling_materials database table.
 * 
 */
@Entity
@Table(name="server_handling_materials")
@NamedQuery(name="ServerHandlingMaterial.findAll", query="SELECT s FROM ServerHandlingMaterial s")
public class ServerHandlingMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	private String name;

	private Integer necessary;

	private Integer number;

	private String remark;

	private Integer sort;

	private Integer status;

	private Integer type;

	@Column(name="server_id")
	private int serverId;

	//附件
	@OneToMany
	@JoinColumn(name = "mater_id")
	private List<ServerAttachment> serverAttachments;

	public List<ServerAttachment> getServerAttachments() {
		return serverAttachments;
	}

	public void setServerAttachments(List<ServerAttachment> serverAttachments) {
		this.serverAttachments = serverAttachments;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public ServerHandlingMaterial() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getNecessary() {
		return necessary;
	}

	public void setNecessary(Integer necessary) {
		this.necessary = necessary;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}