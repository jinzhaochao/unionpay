package com.unionpay.pager.dto;

import com.unionpay.pager.domain.PagerApplication;

import java.util.List;

public class appGroup {

    private int applicationId;
    private String applicationName;
    private String url;
    private int status;
    private int sort;
    private String fileId;
    private int tagId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public List<appGroupapp> getAppGroup() {
        return appGroup;
    }

    public void setAppGroup(List<appGroupapp> appGroup) {
        this.appGroup = appGroup;
    }

    @Override
    public String toString() {
        return "appGroup{" +
                "applicationId=" + applicationId +
                ", applicationName='" + applicationName + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", sort=" + sort +
                ", fileId='" + fileId + '\'' +
                ", tagId=" + tagId +
                ", appGroup=" + appGroup +
                '}';
    }
}
