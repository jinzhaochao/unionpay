package com.unionpay.services.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/18/018 18:49
 * @Description:
 */
@Entity
@Table(name="server_suggest")
@NamedQuery(name="ServerSuggest.findAll", query="SELECT s FROM ServerSuggest s")
public class ServerSuggest implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    /**
     * 关联服务id
     */
    @Column(name = "server_id")
    private Integer serverId;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 服务标题
     */
    private String title;

    /**
     * 意见（服务描述，咨询描述，投诉描述）
     */
    @Column(name = "other_describe")
    private String otherDescribe;

    /**
     * 处理状态  0：待处理  2：已处理
     */
    private Integer status;

    /**
     * 处理人empid
     */
    @Column(name = "reply_id")
    private Integer replyId;

    /**
     * 处理人名字
     */
    @Column(name = "reply_name")
    private String replyName;

    /**
     * 回复时间
     */
    @Column(name = "reply_time")
    private Date replyTime;

    /**
     * 回复原因
     */
    @Column(name = "reply_reason")
    private String replyReason;

    /**
     * 提交人id
     */
    @Column(name = "emp_id")
    private Integer empId;

    /**
     * 提交人名字
     */
    @Column(name = "emp_name")
    private String empName;

    /**
     * 提交意见人的部门
     */
    @Column(name = "emp_dept_name")
    private String empDeptName;

    /**
     * 提交人部门Id
     */
    @Column(name = "emp_dept_id")
    private Integer empDeptId;

    /**
     * 提交人的科室Id
     */
    @Column(name = "emp_org_id")
    private Integer empOrgId;

    /**
     * 反馈途径类型：1服务反馈，2服务建议，3意见反馈, 4部门投诉  5服务投诉
     */
    private Integer type;

    /**
     * 是否转交：0否，1是
     */
    @Column(name = "is_deliver")
    private Integer isDeliver;

    /**
     * 是否匿名   0否 1是
     */
    @Column(name = "is_anonymous")
    private Integer isAnonymous;

    /**
     * 投诉部门id
     */
    @Column(name = "complaint_dept_id")
    private Integer complaintDeptId;

    /**
     * 投诉部门
     */
    @Column(name = "complaint_dept_name")
    private String complaintDeptName;

    public ServerSuggest() {
    }

    public ServerSuggest(int id, Integer serverId, Date createtime, String title, String otherDescribe, Integer status, Integer replyId, String replyName, Date replyTime, String replyReason, Integer empId, String empName, String empDeptName, Integer empDeptId, Integer empOrgId, Integer type, Integer isDeliver, Integer isAnonymous, Integer complaintDeptId, String complaintDeptName) {
        this.id = id;
        this.serverId = serverId;
        this.createtime = createtime;
        this.title = title;
        this.otherDescribe = otherDescribe;
        this.status = status;
        this.replyId = replyId;
        this.replyName = replyName;
        this.replyTime = replyTime;
        this.replyReason = replyReason;
        this.empId = empId;
        this.empName = empName;
        this.empDeptName = empDeptName;
        this.empDeptId = empDeptId;
        this.empOrgId = empOrgId;
        this.type = type;
        this.isDeliver = isDeliver;
        this.isAnonymous = isAnonymous;
        this.complaintDeptId = complaintDeptId;
        this.complaintDeptName = complaintDeptName;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOtherDescribe() {
        return otherDescribe;
    }

    public void setOtherDescribe(String otherDescribe) {
        this.otherDescribe = otherDescribe;
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

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyReason() {
        return replyReason;
    }

    public void setReplyReason(String replyReason) {
        this.replyReason = replyReason;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpDeptName() {
        return empDeptName;
    }

    public void setEmpDeptName(String empDeptName) {
        this.empDeptName = empDeptName;
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

    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public Integer getComplaintDeptId() {
        return complaintDeptId;
    }

    public void setComplaintDeptId(Integer complaintDeptId) {
        this.complaintDeptId = complaintDeptId;
    }

    public String getComplaintDeptName() {
        return complaintDeptName;
    }

    public void setComplaintDeptName(String complaintDeptName) {
        this.complaintDeptName = complaintDeptName;
    }

}
