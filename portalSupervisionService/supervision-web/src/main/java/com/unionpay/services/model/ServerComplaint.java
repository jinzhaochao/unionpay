package com.unionpay.services.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: jinzhao
 * @Date: 2019/9/19 16:39
 * @Description:
 */
@Entity
@Table(name="server_complaint")
@NamedQuery(name="ServerComplaint.findAll", query="SELECT s FROM ServerComplaint s")
public class ServerComplaint implements Serializable{

        @Id
        private int id;

        /**
         * 关联服务id
         */
        @Column(name = "server_id")
        private Integer serverId;

        /**
         * 提交时间
         */
        private Date createtime;

        /**
         * 服务投诉标题
         */
        private String title;

        /**
         * 投诉描述
         */
        @Column(name = "server_describe")
        private String serverDescribe;

        /**
         * 其他意见
         */
        @Column(name = "other_suggest")
        private String otherSuggest;

        /**
         * 提交人id
         */
        @Column(name = "emp_id")
        private Integer empId;

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
         * 回复时间
         */

        @Column(name = "reply_time")
        private Date replyTime;

        /**
         * 回复原因
         */
        @Column(name = "reply_reason")
        private String replyReason;

        /*
        提交意见人的部门
         */
        @Column(name = "emp_dept")
        private String empDept;

        /*
            提交人部门Id
         */
        @Column(name = "emp_dept_id")
        private Integer empDeptId;

        /*
            提交人的科室Id
         */
        @Column(name = "emp_org_id")
        private Integer empOrgId;

        /*
            提交人名字
         */
        @Column(name = "emp_name")
        private String empName;
        /*
            反馈途径类型：1服务反馈，2服务建议，3意见反馈
         */
        private Integer type;

        /*
            是否转交：0否，1是
         */
        @Column(name = "is_deliver")
        private Integer isDeliver;

        /*
            是否匿名:  0否，1是
         */
        @Column(name = "is_anonymous")
        private Integer isAnonyMous;

    public ServerComplaint() {
    }

    public ServerComplaint(int id, Integer serverId, Date createtime, String title, String serverDescribe, String otherSuggest, Integer empId, Integer status, Integer replyId, Date replyTime, String replyReason, String empDept, Integer empDeptId, Integer empOrgId, String empName, Integer type, Integer isDeliver, Integer isAnonyMous) {
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
}
