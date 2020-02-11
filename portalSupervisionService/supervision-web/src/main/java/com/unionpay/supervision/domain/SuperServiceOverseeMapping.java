package com.unionpay.supervision.domain;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the super_service_oversee_mapping database table.
 * 
 */
@Entity
@Table(name="super_service_oversee_mapping")
@NamedQuery(name="SuperServiceOverseeMapping.findAll", query="SELECT s FROM SuperServiceOverseeMapping s")
public class SuperServiceOverseeMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String unid;

	@Column(name="command_leader")
	private String commandLeader;

	@Column(name="command_source")
	private String commandSource;

	@Column(name="create_time")
	private String createTime;

	@Column(name="create_userid")
	private String createUserid;

	@Column(name="file_id")
	private String fileId;

	@Column(name="is_primary")
	private String isPrimary;

	@Column(name="oversee_name")
	private String overseeName;

	@Column(name="oversee_unid")
	private String overseeUnid;

	@Column(name="service_time")
	private String serviceTime;

	@Column(name="service_name")
	private String serviceName;

	@Column(name="service_unid")
	private String serviceUnid;

	private int status;

	@Lob
	@Column(name="task_note")
	private String taskNote;

	public SuperServiceOverseeMapping() {
	}

	public String getUnid() {
		return this.unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getCommandLeader() {
		return this.commandLeader;
	}

	public void setCommandLeader(String commandLeader) {
		this.commandLeader = commandLeader;
	}

	public String getCommandSource() {
		return this.commandSource;
	}

	public void setCommandSource(String commandSource) {
		this.commandSource = commandSource;
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

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getIsPrimary() {
		return this.isPrimary;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getOverseeName() {
		return this.overseeName;
	}

	public void setOverseeName(String overseeName) {
		this.overseeName = overseeName;
	}

	public String getOverseeUnid() {
		return this.overseeUnid;
	}

	public void setOverseeUnid(String overseeUnid) {
		this.overseeUnid = overseeUnid;
	}

	public String getServiceTime() {
		return this.serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceUnid() {
		return this.serviceUnid;
	}

	public void setServiceUnid(String serviceUnid) {
		this.serviceUnid = serviceUnid;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTaskNote() {
		return this.taskNote;
	}

	public void setTaskNote(String taskNote) {
		this.taskNote = taskNote;
	}

	@Override
	public String toString() {
		return "SuperServiceOverseeMapping [unid=" + unid + ", commandLeader=" + commandLeader + ", commandSource="
				+ commandSource + ", createTime=" + createTime + ", createUserid=" + createUserid + ", fileId=" + fileId
				+ ", isPrimary=" + isPrimary + ", overseeName=" + overseeName + ", overseeUnid=" + overseeUnid
				+ ", serviceTime=" + serviceTime + ", serviceName=" + serviceName + ", serviceUnid=" + serviceUnid
				+ ", status=" + status + ", taskNote=" + taskNote + "]";
	}

}