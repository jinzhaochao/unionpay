package com.unionpay.supervision.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class SponsorServiceItemModel extends BaseRowModel {

	// 督办部门编号
	private String unid;

	@ExcelProperty(value = { "督办类型" }, index = 0)
	private String overseeName;

	@ExcelProperty(value = { "来源时间" }, index = 1)
	private String serviceTime;

	@ExcelProperty(value = { "主办单位" }, index = 2)
	private String orgName;

	@ExcelProperty(value = { "主办人" }, index = 3)
	private String sponsorName;

	@ExcelProperty(value = { "最近反馈时间" }, index = 4)
	private String feedbackTime;

	@ExcelProperty(value = { "任务要求" }, index = 5)
	private String taskNote;

	@ExcelProperty(value = { "进展情况" }, index = 6)
	private String progress;

	//事项编号
	private String serviceId;

	//事项名称
	private String serviceName;

	//办理时间
	private Integer throughTimes;

	//推进状态
	private String workStatus;

	//是否阅知件   --jinzhao  2019-12-19
	private String isRead;

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getOverseeName() {
		return overseeName;
	}

	public void setOverseeName(String overseeName) {
		this.overseeName = overseeName;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
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

	public String getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getTaskNote() {
		return taskNote;
	}

	public void setTaskNote(String taskNote) {
		this.taskNote = taskNote;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Integer getThroughTimes() {
		return throughTimes;
	}

	public void setThroughTimes(Integer throughTimes) {
		this.throughTimes = throughTimes;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
}
