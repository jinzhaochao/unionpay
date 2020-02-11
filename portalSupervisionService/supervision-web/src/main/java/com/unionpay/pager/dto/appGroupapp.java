package com.unionpay.pager.dto;

public class appGroupapp {

    private String applicationName;
    private String url;

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

    @Override
    public String toString() {
        return "appGroupapp{" +
                "applicationName='" + applicationName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
