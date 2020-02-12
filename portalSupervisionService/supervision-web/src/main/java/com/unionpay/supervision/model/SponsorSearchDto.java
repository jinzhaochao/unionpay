package com.unionpay.supervision.model;

/**
 * 督办项目查询条件
 */
public class SponsorSearchDto {

	// 督办类型
	private String overseeUnid;
	// 事项类别
	private String serviceType;
	// 督办室联系人
	private String overseeUsername;
	// 来源时间的开始时间
	private String serviceStartTime;
	// 来源时间的结束时间
	private String serviceEndTime;
	// 督办类型名称
	private String overseeName;
	// 任务要求
	private String taskNote;
	// 分管公司领导
	private String branchedLeader;
	// 批示领导
	private String commandLeader;
	// 主办人
	private String sponsorName;
	// 主办单位
	private String orgName;
		// 工作推进状态(有进展,无进展,完成,中止)',
	private String workStatus;
	// 进展情况
	private String progress;
	// 事项编号
	private String serviceId;
	// 出访活动
	private String serviceName;
	// 反馈开始时间
	private String feedbackStartTime;
	// 反馈结束时间
	private String feedbackEndTime;
	//信息反馈人
	private String feedbackUsername;
	//标签
	private String tag;
	//最新反馈时间排序 true升序 false降序
	private Boolean feedbackTimeSort;
	//主办单位排序  true升序 false降序
	private Boolean orgNameSort;
	//推进状态排序  true升序 false降序
	private Boolean workStatusSort;
	//来源时间排序  true升序 false降序
	private Boolean serviceTimeSort;
	//事项类别排序  true升序 false降序
	private Boolean serviceTypeSort;
	//批示领导排序  true升序 false降序
	private Boolean commandLeaderSort;
	//事项状态排序  true升序 false降序
	private Boolean serviceStatusSort;
	//办理时间排序  true升序 false降序
	private Boolean throughTimesSort;
	
	
	public String getOverseeUnid() {
		return overseeUnid;
	}
	public void setOverseeUnid(String overseeUnid) {
		this.overseeUnid = overseeUnid;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getOverseeUsername() {
		return overseeUsername;
	}
	public void setOverseeUsername(String overseeUsername) {
		this.overseeUsername = overseeUsername;
	}
	public String getServiceStartTime() {
		return serviceStartTime;
	}
	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}
	
	public String getServiceEndTime() {
		return serviceEndTime;
	}
	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}
	public String getOverseeName() {
		return overseeName;
	}
	public void setOverseeName(String overseeName) {
		this.overseeName = overseeName;
	}
	public String getTaskNote() {
		return taskNote;
	}
	public void setTaskNote(String taskNote) {
		this.taskNote = taskNote;
	}

	public String getBranchedLeader() {
		return branchedLeader;
	}

	public void setBranchedLeader(String branchedLeader) {
		this.branchedLeader = branchedLeader;
	}

	public String getCommandLeader() {
		return commandLeader;
	}
	public void setCommandLeader(String commandLeader) {
		this.commandLeader = commandLeader;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getFeedbackStartTime() {
		return feedbackStartTime;
	}
	public void setFeedbackStartTime(String feedbackStartTime) {
		this.feedbackStartTime = feedbackStartTime;
	}
	public String getFeedbackEndTime() {
		return feedbackEndTime;
	}
	public void setFeedbackEndTime(String feedbackEndTime) {
		this.feedbackEndTime = feedbackEndTime;
	}

	public String getFeedbackUsername() {
		return feedbackUsername;
	}

	public void setFeedbackUsername(String feedbackUsername) {
		this.feedbackUsername = feedbackUsername;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Boolean getFeedbackTimeSort() {
		return feedbackTimeSort;
	}

	public void setFeedbackTimeSort(Boolean feedbackTimeSort) {
		this.feedbackTimeSort = feedbackTimeSort;
	}

	public Boolean getOrgNameSort() {
		return orgNameSort;
	}

	public void setOrgNameSort(Boolean orgNameSort) {
		this.orgNameSort = orgNameSort;
	}

	public Boolean getWorkStatusSort() {
		return workStatusSort;
	}

	public void setWorkStatusSort(Boolean workStatusSort) {
		this.workStatusSort = workStatusSort;
	}

	public Boolean getServiceTimeSort() {
		return serviceTimeSort;
	}

	public void setServiceTimeSort(Boolean serviceTimeSort) {
		this.serviceTimeSort = serviceTimeSort;
	}

	public Boolean getServiceTypeSort() {
		return serviceTypeSort;
	}

	public void setServiceTypeSort(Boolean serviceTypeSort) {
		this.serviceTypeSort = serviceTypeSort;
	}

	public Boolean getCommandLeaderSort() {
		return commandLeaderSort;
	}

	public void setCommandLeaderSort(Boolean commandLeaderSort) {
		this.commandLeaderSort = commandLeaderSort;
	}

	public Boolean getServiceStatusSort() {
		return serviceStatusSort;
	}

	public void setServiceStatusSort(Boolean serviceStatusSort) {
		this.serviceStatusSort = serviceStatusSort;
	}

	public Boolean getThroughTimesSort() {
		return throughTimesSort;
	}

	public void setThroughTimesSort(Boolean throughTimesSort) {
		this.throughTimesSort = throughTimesSort;
	}
}
