package com.unionpay.services.model;

import java.io.Serializable;

/**
 * 分页查询服务列表，查询条件实体类
 * @author lishuang
 * @date 2019-03-20
 */
public class ConditionModel implements Serializable {
    public static final long serialVersionUID = 1L;
    //page
    private Integer page;
    //size
    private Integer size;
    //分类
    private String type;
    //部门orgid
    private String orgId;
    //状态（1启用 0禁用）
    private String status;
    //热门服务（1是 0否）
    private String hotService;
    //服务类型（1办理服务 2查询服务）
    private String serviceType;
    //服务名称
    private String serName;

    public ConditionModel() {
    }

    public ConditionModel(Integer page, Integer size, String type, String orgId, String status, String hotService, String serviceType, String serName) {
        this.page = page;
        this.size = size;
        this.type = type;
        this.orgId = orgId;
        this.status = status;
        this.hotService = hotService;
        this.serviceType = serviceType;
        this.serName = serName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHotService() {
        return hotService;
    }

    public void setHotService(String hotService) {
        this.hotService = hotService;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    @Override
    public String toString() {
        return "ConditionModel{" +
                "page='" + page +
                ", size='" + size +
                ", type='" + type +
                ", orgId='" + orgId +
                ", status='" + status +
                ", hotService='" + hotService +
                ", serviceType='" + serviceType +
                ", serName='" + serName +
                '}';
    }
}
