package com.unionpay.supervision.model;

import java.io.Serializable;

/**
 * 
 * @author wangyue 待立项查询 super_sponsor（service_id，事项编号）varchar
 *         super_type_service (type_name,事项类型) varchar super_service
 *         （oversee_username，督办室联系人）varchar super_service (service_time,来源时间)
 *         datetime super_service （task_note，任务要求）varchar super_sponsor
 *         (org_name,主办单位/部门) varchar super_sponsor (sponsor_name,主办人) varchar
 *         super_sponsor (proposed_closing_time,拟办时间） datetime super_service
 *         (oversee_frequency,频次） int super_sponsor(note,备注) varchar
 */
public class SuperProject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 督办部门编号
	private String unid;
	// 事项编号
	private String serviceId;
	// 事项名称(批示文件名称,出访活动,会议名称)
	private String serviceName;
	// 督办类型
	private String overseeName;
	// 事项类型名称
	private String typeName;
	// 督办室联系人
	private String overseeUsername;
	// 事项来源
	private String commandSource;
	// 来源时间
	private String serviceTime;
	// 任务要求
	private String taskNote;
	// 主办单位/部门
	private String orgName;
	// 主办人
	private String sponsorName;
	// 拟办时间
	private String proposedClosingTime;
	// 频次
	private Integer overseeFrequency;
	// 备注
	private String note;
	// 事项状态
	private String serviceStatus;
	// 进展情况
	private String progress;
	// 最近反馈时间
	private String feedbackTime;
	//状态
	private String workStatus;

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getOverseeUsername() {
		return overseeUsername;
	}

	public void setOverseeUsername(String overseeUsername) {
		this.overseeUsername = overseeUsername;
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getProposedClosingTime() {
		return proposedClosingTime;
	}

	public void setProposedClosingTime(String proposedClosingTime) {
		this.proposedClosingTime = proposedClosingTime;
	}

	public Integer getOverseeFrequency() {
		return overseeFrequency;
	}

	public void setOverseeFrequency(Integer overseeFrequency) {
		this.overseeFrequency = overseeFrequency;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getOverseeName() {
		return overseeName;
	}

	public void setOverseeName(String overseeName) {
		this.overseeName = overseeName;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}
	
	

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCommandSource() {
		return commandSource;
	}

	public void setCommandSource(String commandSource) {
		this.commandSource = commandSource;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	@Override
	public String toString() {
		return "SuperProject [unid=" + unid + ", serviceId=" + serviceId + ", overseeName=" + overseeName
				+ ", typeName=" + typeName + ", overseeUsername=" + overseeUsername + ", serviceTime=" + serviceTime
				+ ", taskNote=" + taskNote + ", orgName=" + orgName + ", sponsorName=" + sponsorName
				+ ", proposedClosingTime=" + proposedClosingTime + ", overseeFrequency=" + overseeFrequency + ", note="
				+ note + ", serviceStatus=" + serviceStatus + ", feedbackTime=" + feedbackTime + "]";
	}

}
