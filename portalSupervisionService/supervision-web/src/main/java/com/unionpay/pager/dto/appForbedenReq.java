package com.unionpay.pager.dto;

import java.util.List;

public class appForbedenReq {

    private List<Integer> applicationIds;
    private int status;

    public List<Integer> getApplicationIds() {
        return applicationIds;
    }

    public void setApplicationIds(List<Integer> applicationIds) {
        this.applicationIds = applicationIds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "appForbedenReq{" +
                "applicationIds=" + applicationIds +
                ", status=" + status +
                '}';
    }
}
