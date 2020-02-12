package com.unionpay.pager.dto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class selectAppDto {

    @Id
    @Column(name="application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;

    @Column(name="application_name")
    private String applicationName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time")
    private Date createTime;

    @Column(name="file_id")
    private List<fileInfo> files;

    private int level;

    @Column(name="parent_id")
    private Integer parentId;

    private int sort;

    private int status;

    @Column(name="tag_id")
    private int tagId;

    private String url;

    private List<appGroupapp> appGroup;

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<fileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<fileInfo> files) {
        this.files = files;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<appGroupapp> getAppGroup() {
        return appGroup;
    }

    public void setAppGroup(List<appGroupapp> appGroup) {
        this.appGroup = appGroup;
    }

    @Override
    public String toString() {
        return "selectAppDto{" +
                "applicationId=" + applicationId +
                ", applicationName='" + applicationName + '\'' +
                ", createTime=" + createTime +
                ", files=" + files +
                ", level=" + level +
                ", parentId=" + parentId +
                ", sort=" + sort +
                ", status=" + status +
                ", tagId=" + tagId +
                ", url='" + url + '\'' +
                ", appGroup=" + appGroup +
                '}';
    }
}
