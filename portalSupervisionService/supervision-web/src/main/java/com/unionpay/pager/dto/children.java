package com.unionpay.pager.dto;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-08-02 19:33
 **/
public class children {
    //应用名称
    private String applicationName;
    //url
    private String url;
    //附件id
    private String fileId;
    //附件名称
    private String name;
    //附件路径
    private String fileUrl;

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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
