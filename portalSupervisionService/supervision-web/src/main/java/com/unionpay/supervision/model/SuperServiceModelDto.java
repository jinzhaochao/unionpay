package com.unionpay.supervision.model;

import java.util.List;

/**
 * 督办事项查询，修改事项实体类，前后端参数实体类
 */
public class SuperServiceModelDto {
    //督办事项id
    private String unid;
    //事项类型
    private String serviceType;
    //督办频次(X周/次)；
    private Integer overseeFrequency;
    //分管领导(多选)
    private String branchedLeader;
    //督办用户编码  --jinzhao 2019-11-22
    private String overseeUserid;
    //督办联系人 --jinzhao 2019-11-22
    private String overseeUsername;
    //督办事项与督办类型关联详情
    List<SuperOverseeMappingDto> superOverseeMappingDto;

    public SuperServiceModelDto() {
    }

    public SuperServiceModelDto(String unid, String serviceType, Integer overseeFrequency, String branchedLeader, String overseeUserid, String overseeUsername, List<SuperOverseeMappingDto> superOverseeMappingDto) {
        this.unid = unid;
        this.serviceType = serviceType;
        this.overseeFrequency = overseeFrequency;
        this.branchedLeader = branchedLeader;
        this.overseeUserid = overseeUserid;
        this.overseeUsername = overseeUsername;
        this.superOverseeMappingDto = superOverseeMappingDto;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getOverseeFrequency() {
        return overseeFrequency;
    }

    public void setOverseeFrequency(Integer overseeFrequency) {
        this.overseeFrequency = overseeFrequency;
    }

    public String getBranchedLeader() {
        return branchedLeader;
    }

    public void setBranchedLeader(String branchedLeader) {
        this.branchedLeader = branchedLeader;
    }

    public List<SuperOverseeMappingDto> getSuperOverseeMappingDto() {
        return superOverseeMappingDto;
    }

    public void setSuperOverseeMappingDto(List<SuperOverseeMappingDto> superOverseeMappingDto) {
        this.superOverseeMappingDto = superOverseeMappingDto;
    }

    public String getOverseeUsername() {
        return overseeUsername;
    }

    public void setOverseeUsername(String overseeUsername) {
        this.overseeUsername = overseeUsername;
    }

    public String getOverseeUserid() {
        return overseeUserid;
    }

    public void setOverseeUserid(String overseeUserid) {
        this.overseeUserid = overseeUserid;
    }

    @Override
    public String toString() {
        return "SuperServiceModelDto{" +
                "unid='" + unid + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", overseeFrequency=" + overseeFrequency +
                ", branchedLeader='" + branchedLeader + '\'' +
                ", overseeUserid='" + overseeUserid + '\'' +
                ", overseeUsername='" + overseeUsername + '\'' +
                ", superOverseeMappingDto=" + superOverseeMappingDto +
                '}';
    }
}
