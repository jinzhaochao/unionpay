package com.unionpay.services.model;

import java.math.BigInteger;

/**
 * 分页查询，返回服务信息类
 * @author lishuang
 * @date 2019-03-14
 */
public class ServiceInfoModelDto {
    private Integer id;
    private String serId;
    private String serName;
    private String orgName;
    private String userName;
    private String sortNo;
    private String typeName;
    private Byte type;
    private Integer sort;
    private Boolean status;
    private Byte service_type;
    private Boolean online_Processing;
    private String online_Processing_addr;

    public ServiceInfoModelDto(Integer id, String serId, String serName, String orgName, String userName, String sortNo, String typeName, Byte type, Integer sort, Boolean status, Byte service_type, Boolean online_Processing, String online_Processing_addr) {
        this.id = id;
        this.serId = serId;
        this.serName = serName;
        this.orgName = orgName;
        this.userName = userName;
        this.sortNo = sortNo;
        this.typeName = typeName;
        this.type = type;
        this.sort = sort;
        this.status = status;
        this.service_type = service_type;
        this.online_Processing = online_Processing;
        this.online_Processing_addr = online_Processing_addr;
    }

    public ServiceInfoModelDto() {
        super();
    }

    @Override
    public String toString() {
        return "ServiceInfoModelDto{" +
                "id=" + id +
                ", serId='" + serId +
                ", serName='" + serName +
                ", orgName='" + orgName +
                ", userName='" + userName +
                ", sortNo='" + sortNo +
                ", typeName=" + typeName +
                ", type=" + type +
                ", sort=" + sort +
                ", status=" + status +
                ", service_type=" + service_type +
                ", online_Processing=" + online_Processing +
                ", online_Processing_addr='" + online_Processing_addr +
                '}';
    }

    public Byte getService_type() {
        return service_type;
    }

    public void setService_type(Byte service_type) {
        this.service_type = service_type;
    }

    public Boolean getOnline_Processing() {
        return online_Processing;
    }

    public void setOnline_Processing(Boolean online_Processing) {
        this.online_Processing = online_Processing;
    }

    public String getOnline_Processing_addr() {
        return online_Processing_addr;
    }

    public void setOnline_Processing_addr(String online_Processing_addr) {
        this.online_Processing_addr = online_Processing_addr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
