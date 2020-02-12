package com.unionpay.supervision.model;

import java.util.List;

/**
 * @Author jinzhao
 * @Date 2019-10-11
 * 主办单位信息实体
 */
public class SponsorList {
    private String unid;   //督办部门主键
    private String orgId;  //主办部门编码
    private String orgName;//主办部门名称
    private String isRead; //是否阅知件
    private String resultForm;//成果形式
    private String workPlan;//工作计划
    private String proposedClosingTime;//拟办结时间
    private List<SponsorLogList> sponsorLogList;//历次督办进展情况

    public SponsorList() {
    }

    public SponsorList(String unid, String orgId, String orgName, String isRead, String resultForm, String workPlan, String proposedClosingTime, List<SponsorLogList> sponsorLogList) {
        this.unid = unid;
        this.orgId = orgId;
        this.orgName = orgName;
        this.isRead = isRead;
        this.resultForm = resultForm;
        this.workPlan = workPlan;
        this.proposedClosingTime = proposedClosingTime;
        this.sponsorLogList = sponsorLogList;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getResultForm() {
        return resultForm;
    }

    public void setResultForm(String resultForm) {
        this.resultForm = resultForm;
    }

    public String getWorkPlan() {
        return workPlan;
    }

    public void setWorkPlan(String workPlan) {
        this.workPlan = workPlan;
    }

    public String getProposedClosingTime() {
        return proposedClosingTime;
    }

    public void setProposedClosingTime(String proposedClosingTime) {
        this.proposedClosingTime = proposedClosingTime;
    }

    public List<SponsorLogList> getSponsorLogList() {
        return sponsorLogList;
    }

    public void setSponsorLogList(List<SponsorLogList> sponsorLogList) {
        this.sponsorLogList = sponsorLogList;
    }
}
