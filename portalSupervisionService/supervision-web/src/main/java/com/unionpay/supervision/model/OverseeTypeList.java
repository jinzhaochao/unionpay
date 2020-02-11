package com.unionpay.supervision.model;

/**
 * @Author jinzhao
 * @Date 2019-10-11
 * 督办来源列表实体
 */
public class OverseeTypeList {
    private String overseeUnid;  //主键
    private String overseeName;  //督办类型名称
    private String serviceName;  //事项名称
    private String commandSource;//批文来源
    private String commandLeader;//来源领导
    private String taskNote;     //任务要求
    private String serviceTime;  //事项时间
    private String isPrimary;    //是否主督办类

    public OverseeTypeList() {
    }

    public OverseeTypeList(String overseeUnid, String overseeName, String serviceName, String commandSource, String commandLeader, String taskNote, String serviceTime, String isPrimary) {
        this.overseeUnid = overseeUnid;
        this.overseeName = overseeName;
        this.serviceName = serviceName;
        this.commandSource = commandSource;
        this.commandLeader = commandLeader;
        this.taskNote = taskNote;
        this.serviceTime = serviceTime;
        this.isPrimary = isPrimary;
    }

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
}
