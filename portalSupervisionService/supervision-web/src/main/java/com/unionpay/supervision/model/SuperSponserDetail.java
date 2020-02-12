package com.unionpay.supervision.model;


import java.util.List;

/**
 * @Author jinzhao
 * @Date 2019-10-11
 * 督办分页查询返回数据实体
 */
public class SuperSponserDetail {
    private String unid;           //督办部门id
    private String serviceUnid;    //督办事项id
    private String serviceType;    //事项类型
    private String branchedLeader; //分管领导
    private String createTime;     //创建时间
    private String createUserid;   //创建人账号
    private String createUsername; //创建人姓名
    private String overseeUserid;  //督办人账号
    private String overseeUsername;//督办人姓名
    private Integer overseeFrequency;//督办频次
    private String orgNames;         //督办事项主办单位信息
    private List<OverseeTypeList> overseeTypeList;//督办来源列表
    private List<SponsorList> sponsorList; //主办单位信息

    public SuperSponserDetail() {
    }

    public SuperSponserDetail(String unid, String serviceUnid, String serviceType, String branchedLeader, String createTime, String createUserid, String createUsername, String overseeUserid, String overseeUsername, Integer overseeFrequency, String orgNames, List<OverseeTypeList> overseeTypeList, List<SponsorList> sponsorList) {
        this.unid = unid;
        this.serviceUnid = serviceUnid;
        this.serviceType = serviceType;
        this.branchedLeader = branchedLeader;
        this.createTime = createTime;
        this.createUserid = createUserid;
        this.createUsername = createUsername;
        this.overseeUserid = overseeUserid;
        this.overseeUsername = overseeUsername;
        this.overseeFrequency = overseeFrequency;
        this.orgNames = orgNames;
        this.overseeTypeList = overseeTypeList;
        this.sponsorList = sponsorList;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getServiceUnid() {
        return serviceUnid;
    }

    public void setServiceUnid(String serviceUnid) {
        this.serviceUnid = serviceUnid;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getBranchedLeader() {
        return branchedLeader;
    }

    public void setBranchedLeader(String branchedLeader) {
        this.branchedLeader = branchedLeader;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getOverseeUserid() {
        return overseeUserid;
    }

    public void setOverseeUserid(String overseeUserid) {
        this.overseeUserid = overseeUserid;
    }

    public String getOverseeUsername() {
        return overseeUsername;
    }

    public void setOverseeUsername(String overseeUsername) {
        this.overseeUsername = overseeUsername;
    }

    public Integer getOverseeFrequency() {
        return overseeFrequency;
    }

    public void setOverseeFrequency(Integer overseeFrequency) {
        this.overseeFrequency = overseeFrequency;
    }

    public String getOrgNames() {
        return orgNames;
    }

    public void setOrgNames(String orgNames) {
        this.orgNames = orgNames;
    }

    public List<OverseeTypeList> getOverseeTypeList() {
        return overseeTypeList;
    }

    public void setOverseeTypeList(List<OverseeTypeList> overseeTypeList) {
        this.overseeTypeList = overseeTypeList;
    }

    public List<SponsorList> getSponsorList() {
        return sponsorList;
    }

    public void setSponsorList(List<SponsorList> sponsorList) {
        this.sponsorList = sponsorList;
    }
}
