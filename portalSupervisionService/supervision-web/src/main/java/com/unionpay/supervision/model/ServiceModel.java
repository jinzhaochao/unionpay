package com.unionpay.supervision.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Date;

public class ServiceModel extends BaseRowModel {

	// 督办部门编号
	private String unid;

	@ExcelProperty(value = { "事项编号" }, index = 0)
	private String serviceId;

	@ExcelProperty(value = { "事项类别" }, index = 1)
	private String typeName;

	@ExcelProperty(value = {"来源时间"},index = 2)
	private String serviceTime;

	// 事项名称(批示文件名称,出访活动,会议名称)
	@ExcelProperty(value = { "事项名称" }, index = 3)
	private String serviceName;

	// 批示文件来源
	private String commandSource;
	
	@ExcelProperty(value = { "批示或出访领导" }, index = 4)
	private String commandLeader;

	@ExcelProperty(value = { "任务要求" }, index = 5)
	private String taskNote;

	@ExcelProperty(value = { "主办单位" }, index = 6)
	private String orgName;

	@ExcelProperty(value = { "主办人" }, index = 7)
	private String sponsorName;

	@ExcelProperty(value = { "拟办结时间" }, index = 8)
	private String proposedClosingTime;

	@ExcelProperty(value = {"成果形式"},index = 9)
	private String resultForm;

	@ExcelProperty(value = {"工作计划"},index = 10)
	private String workPlan;

	@ExcelProperty(value = { "最近反馈时间" }, index = 11)
	private String feedbackTime;

	@ExcelProperty(value = { "推进状态" }, index = 12)
	private String workStatus;

	@ExcelProperty(value = { "进展情况" }, index = 13)
	private String progress;

	@ExcelProperty(value = { "频次" }, index = 14)
	private Integer overseeFrequency;

	//@ExcelProperty(value = { "来源类型" }, index = 1)
	private String overseeName;

	//@ExcelProperty(value = { "督办室联系人" }, index = 5)
	private String overseeUsername;
	
	//@ExcelProperty(value = { "事项状态" }, index = 6)
	private String serviceStatus;

	//创建时间,查询结果按照此时间排序（从最新到最早）
	private Date createTime;

	//办理时间
	@ExcelProperty(value = {"办理时间"},index = 15)
	private Integer throughTimes;

	//标签
//	@ExcelProperty(value = {"标签"},index = 16)
	private String tag;

	//是否阅知件   --jinzhao  2019-12-19
	@ExcelProperty(value = {"是否阅知件"},index = 16)
	private String isRead;


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

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
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

	public String getResultForm() {
		return resultForm;
	}

	public void setResultForm(String resultForm) {
		this.resultForm = resultForm;
	}

	public String getWorkPlan() {
		return workPlan;
	}

	public void setWorkPlan(String workPlan) {
		this.workPlan = workPlan;
	}

	public String getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public String getOverseeUsername() {
		return overseeUsername;
	}

	public void setOverseeUsername(String overseeUsername) {
		this.overseeUsername = overseeUsername;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public Integer getOverseeFrequency() {
		return overseeFrequency;
	}

	public void setOverseeFrequency(Integer overseeFrequency) {
		this.overseeFrequency = overseeFrequency;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Integer getThroughTimes() {
		return throughTimes;
	}

	public void setThroughTimes(Integer throughTimes) {
		this.throughTimes = throughTimes;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
}
