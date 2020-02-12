package com.unionpay.services.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 服务投诉列表实体类
 * @author jinzhao
 * @date 2019/09/24
 */
public class ComplaintModel extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 服务咨询 ID
     */
    //@ExcelProperty(value = { "序号" }, index = 0)
    private BigInteger suggestId;
    /*
     * 服务标题
     */
    @ExcelProperty(value = {"部门/服务名称"}, index = 1)
    private String title;
    /*
     * 投诉意见
     */
    @ExcelProperty(value = {"投诉描述"}, index = 2)
    private String otherDescribe;
    /*
     * 所属类型
     */
    @ExcelProperty(value = {"投诉类型"}, index = 3)
    private String type;
    /*
     * 投诉人
     */
    @ExcelProperty(value = {"投诉人"}, index = 4)
    private String empName;
    /*
     * 投诉人部门
     */
    @ExcelProperty(value = {"投诉人部门"}, index = 5)
    private String empDeptName;
    /*
     * 投诉时间
     */
    @ExcelProperty(value = {"投诉时间"}, index = 6)
    private String createtime;
    /*
     * 处理人
     */
    @ExcelProperty(value = {"处理人"}, index = 7)
    private String replyName;
    /*
     * 处理状态
     */
    @ExcelProperty(value = {"处理状态"}, index = 8)
    private String status;

    /*
     * 是否匿名
     */
    private Integer isAnonymous;

    public ComplaintModel() {
    }

    public ComplaintModel(BigInteger suggestId, String title, String otherDescribe, String type, String empName, String empDeptName, String createtime, String replyName, String status, Integer isAnonymous) {
        this.suggestId = suggestId;
        this.title = title;
        this.otherDescribe = otherDescribe;
        this.type = type;
        this.empName = empName;
        this.empDeptName = empDeptName;
        this.createtime = createtime;
        this.replyName = replyName;
        this.status = status;
        this.isAnonymous = isAnonymous;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigInteger getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(BigInteger suggestId) {
        this.suggestId = suggestId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    @Override
    public String toString() {
        return "ComplaintModel{" +
                "suggestId=" + suggestId +
                ", title='" + title + '\'' +
                ", otherDescribe='" + otherDescribe + '\'' +
                ", type='" + type + '\'' +
                ", empName='" + empName + '\'' +
                ", empDeptName='" + empDeptName + '\'' +
                ", createtime='" + createtime + '\'' +
                ", replyName='" + replyName + '\'' +
                ", status='" + status + '\'' +
                ", isAnonymous=" + isAnonymous +
                '}';
    }
}

