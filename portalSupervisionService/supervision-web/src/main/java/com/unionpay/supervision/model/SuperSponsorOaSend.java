package com.unionpay.supervision.model;

import javax.validation.constraints.NotEmpty;

/**
 * @Author jinzhao
 * @Date 2019-10-14
 * 督办办结推送消息请求参数实体
 */
public class SuperSponsorOaSend {
    //督办部门主键
    @NotEmpty
    private String unid;
    //督办事项编码
    @NotEmpty
    private String serviceId;
    //督办类型名称
    @NotEmpty
    private String overseeName;
    //事项名称
    @NotEmpty
    private String serviceName;
    //批文来源
    private String commandSource;
    //来源领导
    private String commandLeader;
    //任务要求
    @NotEmpty
    private String taskNote;
    //事项时间
    @NotEmpty
    private String serviceTime;
    //推进状态
    private String workStatus;
    //反馈时间
    private String feedbackTime;
    //进展情况
    private String progress;
    //主办单位编号
    private String deptId;
    //督办状态类型
    @NotEmpty
    private Integer type;

    public SuperSponsorOaSend() {
    }

    public SuperSponsorOaSend(String unid, String serviceId, String overseeName, String serviceName, String commandSource, String commandLeader, String taskNote, String serviceTime, String workStatus, String feedbackTime, String progress, String deptId, Integer type) {
        this.unid = unid;
        this.serviceId = serviceId;
        this.overseeName = overseeName;
        this.serviceName = serviceName;
        this.commandSource = commandSource;
        this.commandLeader = commandLeader;
        this.taskNote = taskNote;
        this.serviceTime = serviceTime;
        this.workStatus = workStatus;
        this.feedbackTime = feedbackTime;
        this.progress = progress;
        this.deptId = deptId;
        this.type = type;
    }

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

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
