package com.unionpay.supervision.model;

import com.unionpay.supervision.domain.SuperFile;

import java.util.List;

/**
 * 督办事项查询，修改事项，督办事项与督办类型关联实体类
 * @author lishuang
 * @date 2019-04-09
 */
public class SuperOverseeMappingDto {
    //督办事项与督办类型关联表,主键
    private String unid;
    //督办类型unid
    private String overseeUnid;
    // 督办类型名称
    private String overseeName;
    // 事项名称(批示文件名称,出访活动,会议名称)
    private String serviceName;
    //任务要求  --jinzhao  2019-11-25
    private String taskNote;
    //来源时间（创建时间）
    private String createTime;
    //是否主督办类型(Y/N) --jinzhao 2019-11-22
    private String isPrimary;
    //文件id  --jinzhao  2019-11-27
    private String fileId;
    //来源时间
    private String serviceTime;
    //批文来源
    private String commandSource;
    //来源领导
    private String commandLeader;
    //督办事项和督办类型关联督办附件表
    private List<SuperFile> superFileList;

    public SuperOverseeMappingDto() {
    }

    public SuperOverseeMappingDto(String unid, String overseeUnid, String overseeName, String serviceName, String taskNote, String createTime, String isPrimary, String fileId, String serviceTime, String commandSource, String commandLeader, List<SuperFile> superFileList) {
        this.unid = unid;
        this.overseeUnid = overseeUnid;
        this.overseeName = overseeName;
        this.serviceName = serviceName;
        this.taskNote = taskNote;
        this.createTime = createTime;
        this.isPrimary = isPrimary;
        this.fileId = fileId;
        this.serviceTime = serviceTime;
        this.commandSource = commandSource;
        this.commandLeader = commandLeader;
        this.superFileList = superFileList;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
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

    public String getTaskNote() {
        return taskNote;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }

    public List<SuperFile> getSuperFileList() {
        return superFileList;
    }

    public void setSuperFileList(List<SuperFile> superFileList) {
        this.superFileList = superFileList;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
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

    public String getOverseeUnid() {
        return overseeUnid;
    }

    public void setOverseeUnid(String overseeUnid) {
        this.overseeUnid = overseeUnid;
    }

    @Override
    public String toString() {
        return "SuperOverseeMappingDto{" +
                "unid='" + unid + '\'' +
                ", overseeUnid='" + overseeUnid + '\'' +
                ", overseeName='" + overseeName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", taskNote='" + taskNote + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isPrimary='" + isPrimary + '\'' +
                ", fileId='" + fileId + '\'' +
                ", serviceTime='" + serviceTime + '\'' +
                ", commandSource='" + commandSource + '\'' +
                ", commandLeader='" + commandLeader + '\'' +
                ", superFileList=" + superFileList +
                '}';
    }
}
