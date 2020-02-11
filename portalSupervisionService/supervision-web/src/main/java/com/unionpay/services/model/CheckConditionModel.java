package com.unionpay.services.model;

import java.io.Serializable;

/**
 * 我的服务 进行中流程查询条件model
 * @author lishuang
 * @date 2019/10/10
 */
public class CheckConditionModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
     * 0：我发起的；1：我经办的;
     */
    private Integer classify;
    /*
     * 业务编号
     */
    private String bizcode;
    /*
     * 服务名称（流程标题）
     */
    private String serviceInfoName;
    /*
     * 发起时间-开始（时间格式：2018-09-01 08:01:23）
     */
    private String launchTimeStart;
    /*
     * 发起时间-结束（时间格式：2018-09-01 08:01:23）
     */
    private String launchTimeEnd;
    /*
     * 当前待办人id
     */
    private String currentUserid;
    /*
     * 当前待办部门id
     */
    private Integer currentOrgid;
    /*
     * 发起人ID或经办人ID
     */
    private String userid;
    /*
     * 所属流程ID
     */
    private String flowNameid;
    /*
     * 流程对应的服务所属部门ID
     */
    private Integer serOrgid;
    /*
     * 满意情况（1满意；0不满意）
     */
    private Integer score;
    /*
     * 页码
     */
    private Integer currentPage;
    /*
     * 每页条数
     */
    private Integer size;

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public String getBizcode() {
        return bizcode;
    }

    public void setBizcode(String bizcode) {
        this.bizcode = bizcode;
    }

    public String getServiceInfoName() {
        return serviceInfoName;
    }

    public void setServiceInfoName(String serviceInfoName) {
        this.serviceInfoName = serviceInfoName;
    }

    public String getLaunchTimeStart() {
        return launchTimeStart;
    }

    public void setLaunchTimeStart(String launchTimeStart) {
        this.launchTimeStart = launchTimeStart;
    }

    public String getLaunchTimeEnd() {
        return launchTimeEnd;
    }

    public void setLaunchTimeEnd(String launchTimeEnd) {
        this.launchTimeEnd = launchTimeEnd;
    }

    public String getCurrentUserid() {
        return currentUserid;
    }

    public void setCurrentUserid(String currentUserid) {
        this.currentUserid = currentUserid;
    }

    public Integer getCurrentOrgid() {
        return currentOrgid;
    }

    public void setCurrentOrgid(Integer currentOrgid) {
        this.currentOrgid = currentOrgid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFlowNameid() {
        return flowNameid;
    }

    public void setFlowNameid(String flowNameid) {
        this.flowNameid = flowNameid;
    }

    public Integer getSerOrgid() {
        return serOrgid;
    }

    public void setSerOrgid(Integer serOrgid) {
        this.serOrgid = serOrgid;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
