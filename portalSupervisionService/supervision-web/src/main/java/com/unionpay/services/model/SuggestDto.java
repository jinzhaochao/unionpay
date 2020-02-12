package com.unionpay.services.model;

import java.io.Serializable;
import java.util.Date;

/**
 *  新增条件分页实体类
 * @author jinzhao
 * @date 2019-08-30
 */
public class SuggestDto implements Serializable{

    private Integer page;
    private Integer size;
    private Integer status;
    private Date startTime;
    private Date endTime;
    private String title;
    private Integer type;
    private Integer empDeptId;
    private String empName;
    private String empDeptName;
    private Integer isDeliver;
    private Integer tabPage;
    private  Integer orgId;

    public SuggestDto() {
    }

    public SuggestDto(Integer page, Integer size, Integer status, Date startTime, Date endTime, String title, Integer type, Integer empDeptId, String empName, String empDeptName, Integer isDeliver, Integer tabPage, Integer orgId) {
        this.page = page;
        this.size = size;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.type = type;
        this.empDeptId = empDeptId;
        this.empName = empName;
        this.empDeptName = empDeptName;
        this.isDeliver = isDeliver;
        this.tabPage = tabPage;
        this.orgId = orgId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getEmpDeptId() {
        return empDeptId;
    }

    public void setEmpDeptId(Integer empDeptId) {
        this.empDeptId = empDeptId;
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

    public Integer getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(Integer isDeliver) {
        this.isDeliver = isDeliver;
    }

    public Integer getTabPage() {
        return tabPage;
    }

    public void setTabPage(Integer tabPage) {
        this.tabPage = tabPage;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "SuggestDto{" +
                "page=" + page +
                ", size=" + size +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", empDeptId=" + empDeptId +
                ", empName='" + empName + '\'' +
                ", empDeptName='" + empDeptName + '\'' +
                ", isDeliver=" + isDeliver +
                ", tabPage=" + tabPage +
                ", orgId=" + orgId +
                '}';
    }
}
