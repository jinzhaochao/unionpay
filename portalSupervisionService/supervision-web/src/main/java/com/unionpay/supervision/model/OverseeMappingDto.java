package com.unionpay.supervision.model;

import javax.validation.constraints.NotBlank;

public class OverseeMappingDto {

	// 事项主键编码
	@NotBlank
	private String serviceUnid;
	// 督办类型主键编码
	@NotBlank
	private String overseeUnid;
	// 督办类型名称
	@NotBlank
	private String overseeName;
	// 事项名称(批示文件名称,出访活动,会议名称)
	@NotBlank
	private String serviceName;
	// 事项时间(会议时间,批示时间,出访时间)
	@NotBlank
	private String serviceTime;
    //任务要求(任务要求,批示内容,相关指示,部署内容)
	private String taskNote;
	//附件
	private String fileId;

	private String isPrimary;
	//公司批示领导
	private String commandLeader;
	//批示文件来源
	private String commandSource;

	public String getServiceUnid() {
		return serviceUnid;
	}

	public void setServiceUnid(String serviceUnid) {
		this.serviceUnid = serviceUnid;
	}

	public String getOverseeUnid() {
		return overseeUnid;
	}

	public void setOverseeUnid(String overseeUnid) {
		this.overseeUnid = overseeUnid;
	}

	public String getOverseeName() {
		return overseeName;
	}

	public void setOverseeName(String overseeName) {
		this.overseeName = overseeName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getTaskNote() {
		return taskNote;
	}

	public void setTaskNote(String taskNote) {
		this.taskNote = taskNote;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getCommandLeader() {
		return commandLeader;
	}

	public void setCommandLeader(String commandLeader) {
		this.commandLeader = commandLeader;
	}

	public String getCommandSource() {
		return commandSource;
	}

	public void setCommandSource(String commandSource) {
		this.commandSource = commandSource;
	}
}
