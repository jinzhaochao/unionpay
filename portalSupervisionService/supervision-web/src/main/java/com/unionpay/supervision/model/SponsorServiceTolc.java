package com.unionpay.supervision.model;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * 发起立项/督办推送类
*/
public class SponsorServiceTolc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 督办日志unid
	private String unid;
	// 督办部门unid
	private String sponsorUnid;
	// 督办事项unid
	private String serviceUnid;
	// 事项编码：HY-2018-001
	private String serviceId;
	// 事项类型
	private String serviceType;
    //分管领导
	private String branchedLeader;
	// 督办级别(公司级/部门级);
	private String serviceLevel;
	// 事项状态(草稿,立项,督办,完成,中止)
	private String serviceStatus;
	// 状态（0：删除,1：正常）
	private String status;
	// 创建时间
	private String createTime;
	// 创建人id
	private String createUserid;
	// 创建人姓名
	private String createUsername;
	// 督办用户编码
	private String overseeUserid;
	// 督办人姓名
	private String overseeUsername;
	// 督办频次(X周/次)；
	private Integer overseeFrequency;
	// 主办单位id
	private String orgId;
	// 主办单位
	private String orgName;
	//部门督办扎口代理人编号
	private String agentUserid;
	//部门督办扎口代理人姓名
	private String agentUserName;
	// 是否阅知件(是:Y;否:N
	private String isRead;
	// 主办人id
	private String sponsorId;
	// 主办人
	private String sponsorName;
	// 反馈要求
	private String feedbackRule;
	// 要求反馈时间
	private String feedbackDeadline;
	// 备注
	private String note;
	// 是否送领导审批(是：Y ；否：N)
	private String needVerify;
	// 审批领导
	private String verifiers;
	// 督办类型关联
	private JSONArray overseeType;

	//应推送给流程平台的数据增加三个字段，此处增加三个属性
	//成果形式
	private String resultForm;
	//工作计划
	private String workPlan;
	//拟办结时间
	private String proposedClosingTime;
	//事项名称--新需求-0403 lishuang
	private String flowTitle;

	//新增信息反馈人--lishuang 2019-08-21
	//信息反馈人userid
	private String feedbackUserid;
	//信息反馈人姓名
	private String feedbackUsername;

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
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getBranchedLeader() {
		return branchedLeader;
	}
	public void setBranchedLeader(String branchedLeader) {
		this.branchedLeader = branchedLeader;
	}
	public String getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserid() {
		return createUserid;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	public String getOverseeUserid() {
		return overseeUserid;
	}
	public void setOverseeUserid(String overseeUserid) {
		this.overseeUserid = overseeUserid;
	}
	public String getOverseeUsername() {
		return overseeUsername;
	}
	public void setOverseeUsername(String overseeUsername) {
		this.overseeUsername = overseeUsername;
	}
	public Integer getOverseeFrequency() {
		return overseeFrequency;
	}
	public void setOverseeFrequency(Integer overseeFrequency) {
		this.overseeFrequency = overseeFrequency;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAgentUserid() {
		return agentUserid;
	}

	public void setAgentUserid(String agentUserid) {
		this.agentUserid = agentUserid;
	}

	public String getAgentUserName() {
		return agentUserName;
	}

	public void setAgentUserName(String agentUserName) {
		this.agentUserName = agentUserName;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNeedVerify() {
		return needVerify;
	}
	public void setNeedVerify(String needVerify) {
		this.needVerify = needVerify;
	}
	public String getVerifiers() {
		return verifiers;
	}
	public void setVerifiers(String verifiers) {
		this.verifiers = verifiers;
	}
	public JSONArray getOverseeType() {
		return overseeType;
	}
	public void setOverseeType(JSONArray overseeType) {
		this.overseeType = overseeType;
	}
	public String getResultForm() { return resultForm;}
	public void setResultForm(String resultForm) {	this.resultForm = resultForm;	}
	public String getWorkPlan() {return workPlan;}
	public void setWorkPlan(String workPlan) {	this.workPlan = workPlan;	}
    public String getProposedClosingTime() {return proposedClosingTime; }
    public void setProposedClosingTime(String proposedClosingTime) {this.proposedClosingTime = proposedClosingTime; }
	public String getFlowTitle() {
		return flowTitle;
	}
	public void setFlowTitle(String flowTitle) {
		this.flowTitle = flowTitle;
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

	@Override
	public String toString() {
		return "SponsorServiceTolc{" +
				"unid=" + unid +
				", sponsorUnid=" + sponsorUnid +
				", serviceUnid=" + serviceUnid +
				", serviceId=" + serviceId +
				", serviceType=" + serviceType +
				", branchedLeader=" + branchedLeader +
				", serviceLevel=" + serviceLevel +
				", serviceStatus=" + serviceStatus +
				", status=" + status +
				", createTime=" + createTime +
				", createUserid=" + createUserid +
				", createUsername=" + createUsername +
				", overseeUserid=" + overseeUserid +
				", overseeUsername=" + overseeUsername +
				", overseeFrequency=" + overseeFrequency +
				", orgId=" + orgId +
				", orgName=" + orgName +
				", agentUserid=" + agentUserid +
				", agentUserName=" + agentUserName +
				", isRead=" + isRead +
				", sponsorId=" + sponsorId +
				", sponsorName=" + sponsorName +
				", feedbackRule=" + feedbackRule +
				", feedbackDeadline=" + feedbackDeadline +
				", note=" + note +
				", needVerify=" + needVerify +
				", verifiers=" + verifiers +
				", overseeType=" + overseeType +
				", resultForm=" + resultForm +
				", workPlan=" + workPlan +
				", proposedClosingTime=" + proposedClosingTime +
				", flowTitle=" + flowTitle +
				", feedbackUserid=" + feedbackUserid +
				", feedbackUsername=" + feedbackUsername +
				'}';
	}
}
