package com.unionpay.supervision.model;


/**
 * @author wangyue
 * super_type_oversee（type_name，事项大类） 根据super_service中的overseeUnid查找
 * super_service  （task_note，任务要求）varchar
 * super_sponsor   (org_name,主办单位/部门) varchar
 * super_sponsor（progress,进展情况）
 * super_sponsor（feedback_time，最近反馈时间） Stringtime
 */
public class SuperMessage {
	private String unid;
	//事项大类
	private String typeName;
	//任务要求
	private String taskNote;
	//主办单位
	private String orgName;
	//进展情况
	private String progress;
	//最近反馈时间
	private String feedbackTime;
	//状态
	private String state;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getUnid() {
		return unid;
	}
	public void setUnid(String unid) {
		this.unid = unid;
	}
	@Override
	public String toString() {
		return "SuperMessage [unid=" + unid + ", typeName=" + typeName + ", taskNote=" + taskNote + ", orgName="
				+ orgName + ", progress=" + progress + ", feedbackTime=" + feedbackTime + ", state=" + state + "]";
	}
	
	
}
