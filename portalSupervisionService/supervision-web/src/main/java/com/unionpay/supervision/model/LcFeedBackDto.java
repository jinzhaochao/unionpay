package com.unionpay.supervision.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 流程平台反馈信息类
 * 
 */
public class LcFeedBackDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
     
	// super_sponsor_log主键
	@NotBlank
	private String unid;

	// 督办部门编号
	@NotBlank
	private String sponsorUnid;

	// 督办事项编号
	private String serviceUnid;

	// 事项编号
	private String serviceId;

	// 督办部门id
	private String orgId;

	// 主办单位/部门
	private String orgName;

	// 是否阅知件(是:Y;否:N)
	private String isRead;

	// 主办人unid
	private String sponsorId;

	// 主办人
	private String sponsorName;

	// 反馈要求
	private String feedbackRule;
	// 要求反馈时间^\\d{4}\\-\\d{2}\\-\\d{2}$
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "不符合yyyy-MM-dd格式")
	private String feedbackDeadline;
	// 成果形式
	private String resultForm;
	// 工作计划
	private String workPlan;
	// 拟办时间
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "不符合yyyy-MM-dd格式")
	private String proposedClosingTime;
	// 最近反馈时间
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "不符合yyyy-MM-dd hh:mm:ss格式")
	private String feedbackTime;
	// 进展情况
	private String progress;

	// 状态
	private String workStatus;
	// 备注
	private String note;
	// 状态
	private Integer status;
	// 变更类型(1：立项；2：督办；3：变更；4：延期)
	private Integer changeType;
	// 变更理由
	private String changeReason;
	//信息反馈人userid
	private String feedbackUserid;
	//信息反馈人姓名
	private String feedbackUsername;
	//办结时间  jinzhao  2019-12-12
	private String throughTime;

	public String getUnid() {
		return unid;
	}
	public void setUnid(String unid) {
		this.unid = unid;
	}
	public String getSponsorUnid() {
		return sponsorUnid;
	}
	public void setSponsorUnid(String sponsorUnid) {
		this.sponsorUnid = sponsorUnid;
	}
	public String getServiceUnid() {
		return serviceUnid;
	}
	public void setServiceUnid(String serviceUnid) {
		this.serviceUnid = serviceUnid;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public String getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public String getFeedbackRule() {
		return feedbackRule;
	}
	public void setFeedbackRule(String feedbackRule) {
		this.feedbackRule = feedbackRule;
	}
	public String getFeedbackDeadline() {
		return feedbackDeadline;
	}
	public void setFeedbackDeadline(String feedbackDeadline) {
		this.feedbackDeadline = feedbackDeadline;
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
	public String getProposedClosingTime() {
		return proposedClosingTime;
	}
	public void setProposedClosingTime(String proposedClosingTime) {
		this.proposedClosingTime = proposedClosingTime;
	}
	public String getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getChangeType() {
		return changeType;
	}
	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}
	public String getChangeReason() {
		return changeReason;
	}
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public String getFeedbackUserid() {
		return feedbackUserid;
	}

	public void setFeedbackUserid(String feedbackUserid) {
		this.feedbackUserid = feedbackUserid;
	}

	public String getFeedbackUsername() {
		return feedbackUsername;
	}

	public void setFeedbackUsername(String feedbackUsername) {
		this.feedbackUsername = feedbackUsername;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getThroughTime() {
		return throughTime;
	}

	public void setThroughTime(String throughTime) {
		this.throughTime = throughTime;
	}

	@Override
	public String toString() {
		return "LcFeedBackDto{" +
				"unid='" + unid + '\'' +
				", sponsorUnid='" + sponsorUnid + '\'' +
				", serviceUnid='" + serviceUnid + '\'' +
				", serviceId='" + serviceId + '\'' +
				", orgId='" + orgId + '\'' +
				", orgName='" + orgName + '\'' +
				", isRead='" + isRead + '\'' +
				", sponsorId='" + sponsorId + '\'' +
				", sponsorName='" + sponsorName + '\'' +
				", feedbackRule='" + feedbackRule + '\'' +
				", feedbackDeadline='" + feedbackDeadline + '\'' +
				", resultForm='" + resultForm + '\'' +
				", workPlan='" + workPlan + '\'' +
				", proposedClosingTime='" + proposedClosingTime + '\'' +
				", feedbackTime='" + feedbackTime + '\'' +
				", progress='" + progress + '\'' +
				", workStatus='" + workStatus + '\'' +
				", note='" + note + '\'' +
				", status=" + status +
				", changeType=" + changeType +
				", changeReason='" + changeReason + '\'' +
				", feedbackUserid='" + feedbackUserid + '\'' +
				", feedbackUsername='" + feedbackUsername + '\'' +
				", throughTime='" + throughTime + '\'' +
				'}';
	}
}
