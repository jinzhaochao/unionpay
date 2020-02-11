package com.unionpay.services.model;

public class ServerComplaintModel {

    private int id;

    /**
     * 关联服务id
     */
    private Integer serverId;

    /**
     * 提交时间
     */
    private String createtime;

    /**
     * 服务投诉标题
     */
    private String title;

    /**
     * 投诉描述
     */
    private String serverDescribe;

    /**
     * 其他意见
     */
    private String otherSuggest;

    /**
     * 提交人id
     */
    private Integer empId;

    /**
     * 处理状态  0：待处理  2：已处理
     */
    private Integer status;

    /**
     * 处理人empid
     */
    private Integer replyId;

    /**
     * 回复时间
     */

    private String replyTime;

    /**
     * 回复原因
     */
    private String replyReason;

    /**
       提交意见人的部门
     */
    private String empDept;

    /**
        提交人部门Id
     */
    private Integer empDeptId;

    /**
        提交人的科室Id
     */
    private Integer empOrgId;

    /**
        提交人名字
     */
    private String empName;
    /**
        投诉途径类型：1部门投诉，2服务投诉
     */
    private Integer type;

    /**
        是否转交：0否，1是
     */
    private Integer isDeliver;

    /**
        是否匿名:  0否，1是
     */
    private Integer isAnonyMous;

    /**
     * 处理人名字
     */
    private String replyName;

    /**
        处理时长
     */
    private String timeLength;

    public ServerComplaintModel() {
    }

    public ServerComplaintModel(int id, Integer serverId, String createtime, String title, String serverDescribe, String otherSuggest, Integer empId, Integer status, Integer replyId, String replyTime, String replyReason, String empDept, Integer empDeptId, Integer empOrgId, String empName, Integer type, Integer isDeliver, Integer isAnonyMous, String replyName, String timeLength) {
        this.id = id;
        this.serverId = serverId;
        this.createtime = createtime;
        this.title = title;
        this.serverDescribe = serverDescribe;
        this.otherSuggest = otherSuggest;
        this.empId = empId;
        this.status = status;
        this.replyId = replyId;
        this.replyTime = replyTime;
        this.replyReason = replyReason;
        this.empDept = empDept;
        this.empDeptId = empDeptId;
        this.empOrgId = empOrgId;
        this.empName = empName;
        this.type = type;
        this.isDeliver = isDeliver;
        this.isAnonyMous = isAnonyMous;
        this.replyName = replyName;
        this.timeLength = timeLength;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getServerDescribe() {
        return serverDescribe;
    }

    public void setServerDescribe(String serverDescribe) {
        this.serverDescribe = serverDescribe;
    }

    public String getOtherSuggest() {
        return otherSuggest;
    }

    public void setOtherSuggest(String otherSuggest) {
        this.otherSuggest = otherSuggest;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyReason() {
        return replyReason;
    }

    public void setReplyReason(String replyReason) {
        this.replyReason = replyReason;
    }

    public String getEmpDept() {
        return empDept;
    }

    public void setEmpDept(String empDept) {
        this.empDept = empDept;
    }

    public Integer getEmpDeptId() {
        return empDeptId;
    }

    public void setEmpDeptId(Integer empDeptId) {
        this.empDeptId = empDeptId;
    }

    public Integer getEmpOrgId() {
        return empOrgId;
    }

    public void setEmpOrgId(Integer empOrgId) {
        this.empOrgId = empOrgId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(Integer isDeliver) {
        this.isDeliver = isDeliver;
    }

    public Integer getIsAnonyMous() {
        return isAnonyMous;
    }

    public void setIsAnonyMous(Integer isAnonyMous) {
        this.isAnonyMous = isAnonyMous;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    @Override
    public String toString() {
        return "ServerComplaintModel{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", createtime='" + createtime + '\'' +
                ", title='" + title + '\'' +
                ", serverDescribe='" + serverDescribe + '\'' +
                ", otherSuggest='" + otherSuggest + '\'' +
                ", empId=" + empId +
                ", status=" + status +
                ", replyId=" + replyId +
                ", replyTime='" + replyTime + '\'' +
                ", replyReason='" + replyReason + '\'' +
                ", empDept='" + empDept + '\'' +
                ", empDeptId=" + empDeptId +
                ", empOrgId=" + empOrgId +
                ", empName='" + empName + '\'' +
                ", type=" + type +
                ", isDeliver=" + isDeliver +
                ", isAnonyMous=" + isAnonyMous +
                ", replyName='" + replyName + '\'' +
                ", timeLength='" + timeLength + '\'' +
                '}';
    }
}
