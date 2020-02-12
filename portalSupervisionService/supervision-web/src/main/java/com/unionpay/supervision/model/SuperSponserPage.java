package com.unionpay.supervision.model;

/**
 * @Author jinzhao
 * @Date 2019-10-011
 * 督办分页查询返回数据实体
 */
public class SuperSponserPage {
    private String unid;           //督办部门id
    private String serviceId;      //督办事项编码
    private String overseeName;    //督办类型名称
    private String serviceTime;    //事项时间
    private String serviceName;    //事项名称
    private String commandSource;  //批文来源
    private String commandLeader;  //来源领导
    private String taskNote;       //任务要求
    private String serviceType;    //事项类型
    private String orgId;          //主办部门编码
    private String orgName;        //主办部门名称
    private String isRead;         //是否阅知件(是:Y;否:N)
    private String sponsorId;      //主办人编号
    private String sponsorName;    //主办人姓名

    public SuperSponserPage() {
    }

    public SuperSponserPage(String unid, String serviceId, String overseeName, String serviceTime, String serviceName, String commandSource, String commandLeader, String taskNote, String serviceType, String orgId, String orgName, String isRead, String sponsorId, String sponsorName) {
        this.unid = unid;
        this.serviceId = serviceId;
        this.overseeName = overseeName;
        this.serviceTime = serviceTime;
        this.serviceName = serviceName;
        this.commandSource = commandSource;
        this.commandLeader = commandLeader;
        this.taskNote = taskNote;
        this.serviceType = serviceType;
        this.orgId = orgId;
        this.orgName = orgName;
        this.isRead = isRead;
        this.sponsorId = sponsorId;
        this.sponsorName = sponsorName;
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

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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
}
