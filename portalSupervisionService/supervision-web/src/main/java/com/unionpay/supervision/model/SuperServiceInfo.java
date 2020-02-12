package com.unionpay.supervision.model;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.unionpay.supervision.domain.SuperSponsorLog;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import com.unionpay.supervision.domain.SuperFile;
import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.domain.SuperSponsor;

/**
 * 督办事项综合类
 * 
*/
public class SuperServiceInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//督办事项id
	private String unid;

	//分管领导(多选)
	private String branchedLeader;

	//来源领导(批示领导/出访领导)
	private String commandLeader;

	//批文来源
	private String commandSource;

	//创建时间
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "时间格式有误")
	private String createTime;

	//创建人userID
	private String createUserid;

	//创建人姓名
	private String createUsername;

	//督办结束时间
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "时间格式有误")
	private String endTime;

	//备注
	private String note;

	//督办频次(X周)；
	@NotNull
	@Range(min=1, max=30)
	private Integer overseeFrequency;

	//督办人姓名
	private String overseeName;

	//督办类型
	private String overseeUnid;

	//督办用户编码
	private String overseeUserid;

	//督办人姓名
	private String overseeUsername;

	//立项结束时间;
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "时间格式有误")
	private String proposalEndTime;

	//立项开始时间;
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "时间格式有误")
	private String proposalStartTime;

	//事项时间(会议时间,批示时间,出访时间;
	private String serviceTime;

	//督办级别(公司级/部门级);
	private String serviceLevel;

	//事项名称(批示文件名称,出访活动,会议名称)
	@NotBlank
	@Length(max=200)  // --jinzhao 2019-12-2 长度修改50为200
	private String serviceName;

	//事项状态(草稿,立项,督办,完成,中止)
	private String serviceStatus;

	//事项类型
	@NotBlank
	private String serviceType;

	//任务要求(任务要求,批示内容,相关指示,部署内容)
	private String taskNote;
	
	//事项编码：HY-2018-001
	private String serviceId;
	//反馈要求
	private String feedbackRule;
	//反馈要求时间
	private String feedbackDeadline;
	//是否送领导审批
	private String needVerify;
	//审批领导
	private String verifiers;
	
	//是否为保存
	private Integer isSave;
	
	//是否为提交
	private Integer isSubmit;
	
	//主办单位详情
	List<SuperSponsor> superSponsor ;
	//督办日志信息
	List<SuperSponsorLog> superSponsorLogList;
	//督办事项与督办类型关联详情
	List<SuperServiceOverseeMapping> superServiceOverseeMapping;
	//附件详情
	List<SuperFile> superFile;
	
	Map<String,Object> superFileMap;
	
	public String getUnid() {
		return unid;
	}
	public void setUnid(String unid) {
		this.unid = unid;
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
	public String getCommandSource() {
		return commandSource;
	}
	public void setCommandSource(String commandSource) {
		this.commandSource = commandSource;
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
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getOverseeFrequency() {
		return overseeFrequency;
	}
	public void setOverseeFrequency(Integer overseeFrequency) {
		this.overseeFrequency = overseeFrequency;
	}
	public String getOverseeName() {
		return overseeName;
	}
	public void setOverseeName(String overseeName) {
		this.overseeName = overseeName;
	}
	public String getOverseeUnid() {
		return overseeUnid;
	}
	public void setOverseeUnid(String overseeUnid) {
		this.overseeUnid = overseeUnid;
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
	public String getProposalEndTime() {
		return proposalEndTime;
	}
	public void setProposalEndTime(String proposalEndTime) {
		this.proposalEndTime = proposalEndTime;
	}
	public String getProposalStartTime() {
		return proposalStartTime;
	}
	public void setProposalStartTime(String proposalStartTime) {
		this.proposalStartTime = proposalStartTime;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getTaskNote() {
		return taskNote;
	}
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public void setTaskNote(String taskNote) {
		this.taskNote = taskNote;
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
	public Integer getIsSave() {
		return isSave;
	}
	public void setIsSave(Integer isSave) {
		this.isSave = isSave;
	}
	public Integer getIsSubmit() {
		return isSubmit;
	}
	public void setIsSubmit(Integer isSubmit) {
		this.isSubmit = isSubmit;
	}
	public List<SuperSponsor> getSuperSponsor() {
		return superSponsor;
	}
	public void setSuperSponsor(List<SuperSponsor> superSponsor) {
		this.superSponsor = superSponsor;
	}
	public List<SuperServiceOverseeMapping> getSuperServiceOverseeMapping() {
		return superServiceOverseeMapping;
	}
	public void setSuperServiceOverseeMapping(List<SuperServiceOverseeMapping> superServiceOverseeMapping) {
		this.superServiceOverseeMapping = superServiceOverseeMapping;
	}
	public List<SuperFile> getSuperFile() {
		return superFile;
	}
	public void setSuperFile(List<SuperFile> superFile) {
		this.superFile = superFile;
	}
	public Map<String, Object> getSuperFileMap() {
		return superFileMap;
	}
	public void setSuperFileMap(Map<String, Object> superFileMap) {
		this.superFileMap = superFileMap;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<SuperSponsorLog> getSuperSponsorLogList() {
		return superSponsorLogList;
	}

	public void setSuperSponsorLogList(List<SuperSponsorLog> superSponsorLogList) {
		this.superSponsorLogList = superSponsorLogList;
	}
}
