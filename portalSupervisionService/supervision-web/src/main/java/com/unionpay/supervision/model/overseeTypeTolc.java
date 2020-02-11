package com.unionpay.supervision.model;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * 事项推送，督办类型关联类
*/
public class overseeTypeTolc implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//督办类型主键编码
	private String overseeUnid;
	//督办类型名称
	private String overseeName;
	//事项名称(批示文件名称,出访活动,会议名称)
	private String serviceName;
	//批文来源
	private String commandSource;
	//来源领导(批示领导/出访领导)
	private String commandLeader;
	//任务要求(任务要求,批示内容,相关指示,部署内容)
	private String taskNote;
	//事项时间(会议时间,批示时间,出访时间)
	private String serviceTime;
	//是否主督办类型(Y/N)
	private String isPrimary;
	//状态（0：删除,1：正常）
	private Integer status;
	//附件----lishuang
	private JSONArray fileList;

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
	public String getCommandSource() {
		return commandSource;
	}
	public void setCommandSource(String commandSource) {
		this.commandSource = commandSource;
	}
	public String getCommandLeader() {
		return commandLeader;
	}
	public void setCommandLeader(String commandLeader) {
		this.commandLeader = commandLeader;
	}
	public String getTaskNote() {
		return taskNote;
	}
	public void setTaskNote(String taskNote) {
		this.taskNote = taskNote;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public JSONArray getFileList() {return fileList;}
	public void setFileList(JSONArray fileList) {this.fileList = fileList;}

	@Override
	public String toString() {
		return "overseeTypeTolc [overseeUnid=" + overseeUnid + ", overseeName=" + overseeName + ", serviceName="
				+ serviceName + ", commandSource=" + commandSource + ", commandLeader=" + commandLeader + ", taskNote="
				+ taskNote + ", serviceTime=" + serviceTime + ", isPrimary=" + isPrimary + ", status=" + status + ", fileList=" + fileList + "]";
	}
	
	
	
	
}
