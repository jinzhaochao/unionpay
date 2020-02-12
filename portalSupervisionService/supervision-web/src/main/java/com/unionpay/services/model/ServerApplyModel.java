package com.unionpay.services.model;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 我的服务列表中 信息实体类
 * @author lishuang
 * @date 2019/10/10
 */
public class ServerApplyModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
     * 流程信息主键id
     */
    private BigInteger id;
    /*
     * 流程标题
     */
    private String serviceInfoName;
    /*
     * 申请人
     */
    private String launchUsername;
    /*
     * 申请时间
     */
    private String launchTime;
    /*
     * 结束时间
     */
    private String endTime;
    /*
     * 服务时长
     */
    private BigInteger serverDuration;
    /*
     * 流程环节（当前节点）
     */
    private String currentNode;
    /*
     * 当前操作人
     */
    private String currentUsername;
    /*
     * 当前处理部门
     */
    private String currentOrgname;
    /*
     * 所属部门
     */
    private String serOrgname;
    /*
     * 服务经理
     */
    private String flowUsername;
    /*
     * 评价（1满意；0不满意）
     */
    private Integer score;
    /*
     * 流程进度查询页面跳转地址
     */
    private String queryUrl;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getServiceInfoName() {
        return serviceInfoName;
    }

    public void setServiceInfoName(String serviceInfoName) {
        this.serviceInfoName = serviceInfoName;
    }

    public String getLaunchUsername() {
        return launchUsername;
    }

    public void setLaunchUsername(String launchUsername) {
        this.launchUsername = launchUsername;
    }

    public String getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(String launchTime) {
        this.launchTime = launchTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public BigInteger getServerDuration() {
        return serverDuration;
    }

    public void setServerDuration(BigInteger serverDuration) {
        this.serverDuration = serverDuration;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getCurrentOrgname() {
        return currentOrgname;
    }

    public void setCurrentOrgname(String currentOrgname) {
        this.currentOrgname = currentOrgname;
    }

    public String getSerOrgname() {
        return serOrgname;
    }

    public void setSerOrgname(String serOrgname) {
        this.serOrgname = serOrgname;
    }

    public String getFlowUsername() {
        return flowUsername;
    }

    public void setFlowUsername(String flowUsername) {
        this.flowUsername = flowUsername;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    @Override
    public String toString() {
        return "ServerApplyModel{" +
                "id=" + id +
                ", serviceInfoName='" + serviceInfoName + '\'' +
                ", launchUsername='" + launchUsername + '\'' +
                ", launchTime='" + launchTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", serverDuration=" + serverDuration +
                ", currentNode='" + currentNode + '\'' +
                ", currentUsername='" + currentUsername + '\'' +
                ", currentOrgname='" + currentOrgname + '\'' +
                ", serOrgname='" + serOrgname + '\'' +
                ", flowUsername='" + flowUsername + '\'' +
                ", score=" + score +
                ", queryUrl='" + queryUrl + '\'' +
                '}';
    }
}
