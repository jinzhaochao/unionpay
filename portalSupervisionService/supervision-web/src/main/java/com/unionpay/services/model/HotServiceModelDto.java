package com.unionpay.services.model;

import java.io.Serializable;

/**
 * 热门服务实体类
 * @author lishuang
 * @date 2019-03-18
 */
public class HotServiceModelDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String serName;
    private Byte serviceType;
    private Boolean onlineProcessing;
    private String onlineProcessingAddr;

    public HotServiceModelDto() {
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public Byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
    }

    public Boolean getOnlineProcessing() {
        return onlineProcessing;
    }

    public void setOnlineProcessing(Boolean onlineProcessing) {
        this.onlineProcessing = onlineProcessing;
    }

    public String getOnlineProcessingAddr() {
        return onlineProcessingAddr;
    }

    public void setOnlineProcessingAddr(String onlineProcessingAddr) {
        this.onlineProcessingAddr = onlineProcessingAddr;
    }

    @Override
    public String toString() {
        return "HotServiceModelDto{" +
                "id=" + id +
                ", serName='" + serName +
                ", serviceType=" + serviceType +
                ", onlineProcessing=" + onlineProcessing +
                ", onlineProcessingAddr='" + onlineProcessingAddr +
                '}';
    }
}
