package com.unionpay.supervision.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the super_service database table.
 *
 */
@Entity
@Table(name="super_service")
@NamedQuery(name="SuperService.findAll", query="SELECT s FROM SuperService s")
public class SuperService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String unid;

	@Column(name="branched_leader")
	private String branchedLeader;

	@Column(name="command_leader")
	private String commandLeader;

	@Column(name="command_source")
	private String commandSource;

	@Column(name="create_time")
	private String createTime;

	@Column(name="create_userid")
	private String createUserid;

	@Column(name="create_username")
	private String createUsername;

	@Column(name="end_time")
	private String endTime;

	@Lob
	private String note;

	@Column(name="oversee_frequency")
	private Integer overseeFrequency;

	@Column(name="oversee_name")
	private String overseeName;

	@Column(name="oversee_unid")
	private String overseeUnid;

	@Column(name="oversee_userid")
	private String overseeUserid;

	@Column(name="oversee_username")
	private String overseeUsername;

	@Column(name="proposal_end_time")
	private String proposalEndTime;

	@Column(name="proposal_start_time")
	private String proposalStartTime;

	@Column(name="service_time")
	private String serviceTime;

	@Column(name="service_level")
	private String serviceLevel;

	@Column(name="service_name")
	private String serviceName;

	@Column(name="service_status")
	private String serviceStatus;

	@Column(name="service_type")
	private String serviceType;

	@Lob
	@Column(name="task_note")
	private String taskNote;

	@Column(name="status")
	private Integer status;

	public SuperService() {
	}

	public String getUnid() {
		return this.unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getBranchedLeader() {
		return this.branchedLeader;
	}

	public void setBranchedLeader(String branchedLeader) {
		this.branchedLeader = branchedLeader;
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

	public String getCreateUsername() {
		return this.createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getOverseeFrequency() {
		return this.overseeFrequency;
	}

	public void setOverseeFrequency(Integer overseeFrequency) {
		this.overseeFrequency = overseeFrequency;
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

	public String getOverseeUserid() {
		return this.overseeUserid;
	}

	public void setOverseeUserid(String overseeUserid) {
		this.overseeUserid = overseeUserid;
	}

	public String getOverseeUsername() {
		return this.overseeUsername;
	}

	public void setOverseeUsername(String overseeUsername) {
		this.overseeUsername = overseeUsername;
	}

	public String getProposalEndTime() {
		return this.proposalEndTime;
	}

	public void setProposalEndTime(String proposalEndTime) {
		this.proposalEndTime = proposalEndTime;
	}

	public String getProposalStartTime() {
		return this.proposalStartTime;
	}

	public void setProposalStartTime(String proposalStartTime) {
		this.proposalStartTime = proposalStartTime;
	}

	public String getServiceTime() {
		return this.serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getServiceLevel() {
		return this.serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceStatus() {
		return this.serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getTaskNote() {
		return this.taskNote;
	}

	public void setTaskNote(String taskNote) {
		this.taskNote = taskNote;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SuperService [unid=" + unid + ", branchedLeader=" + branchedLeader + ", commandLeader=" + commandLeader
				+ ", commandSource=" + commandSource + ", createTime=" + createTime + ", createUserid=" + createUserid
				+ ", createUsername=" + createUsername + ", endTime=" + endTime + ", note=" + note
				+ ", overseeFrequency=" + overseeFrequency + ", overseeName=" + overseeName + ", overseeUnid="
				+ overseeUnid + ", overseeUserid=" + overseeUserid + ", overseeUsername=" + overseeUsername
				+ ", proposalEndTime=" + proposalEndTime + ", proposalStartTime=" + proposalStartTime + ", serviceTime="
				+ serviceTime + ", serviceLevel=" + serviceLevel + ", serviceName=" + serviceName + ", serviceStatus="
				+ serviceStatus + ", serviceType=" + serviceType + ", taskNote=" + taskNote + ", status=" + status
				+ "]";
	}
	

}