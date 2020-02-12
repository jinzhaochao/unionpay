package com.unionpay.services.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ServerInfoModelDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //防止页面重复提交token
    private String token;

    private int id;

    //申办材料递送地址
    private String biddingMaterials;

    //创建时间
    private Date createtime;

    //办理渠道
    private String handlingChannels;

    //办理要求
    private String handlingRequirements;

    //发起权限
    private String initiationAuthority;

    //关键字
    private String keyword;

    //是否在线办理
    private Byte online_Processing;

    //在线办理地址
    private String online_Processing_addr;

    //流程描述
    private String processDescription;

    //办理时间
    private Byte processingTime;

    //服务编码
    private String serId;

    //服务名称
    private String serName;

    //服务范围
    private String serRange;

    //办结材料领取地址
    private String settlementMaterials;

    //排序
    private Integer sort;

    //状态（1启用 0停用）
    private Byte status;

    //服务简介
    private String summary;

    //服务分类
    private Byte type;

    //服务类型( 1查询服务 2办理服务)
    private Byte serviceType;

    //热门服务（1是 0否）
    private Byte hotService;

    //受理部门
    private String orgIds;

    //受理人
    private String userIds;

    //流程图
    private String flowChartIds;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBiddingMaterials() {
        return biddingMaterials;
    }

    public void setBiddingMaterials(String biddingMaterials) {
        this.biddingMaterials = biddingMaterials;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getHandlingChannels() {
        return handlingChannels;
    }

    public void setHandlingChannels(String handlingChannels) {
        this.handlingChannels = handlingChannels;
    }

    public String getHandlingRequirements() {
        return handlingRequirements;
    }

    public void setHandlingRequirements(String handlingRequirements) {
        this.handlingRequirements = handlingRequirements;
    }

    public String getInitiationAuthority() {
        return initiationAuthority;
    }

    public void setInitiationAuthority(String initiationAuthority) {
        this.initiationAuthority = initiationAuthority;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Byte getOnline_Processing() {
        return online_Processing;
    }

    public void setOnline_Processing(Byte online_Processing) {
        this.online_Processing = online_Processing;
    }

    public String getOnline_Processing_addr() {
        return online_Processing_addr;
    }

    public void setOnline_Processing_addr(String online_Processing_addr) {
        this.online_Processing_addr = online_Processing_addr;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public Byte getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Byte processingTime) {
        this.processingTime = processingTime;
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

    public String getSerRange() {
        return serRange;
    }

    public void setSerRange(String serRange) {
        this.serRange = serRange;
    }

    public String getSettlementMaterials() {
        return settlementMaterials;
    }

    public void setSettlementMaterials(String settlementMaterials) {
        this.settlementMaterials = settlementMaterials;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getServiceType() {
        return serviceType;
    }

    public void setServiceType(Byte serviceType) {
        this.serviceType = serviceType;
    }

    public Byte getHotService() {
        return hotService;
    }

    public void setHotService(Byte hotService) {
        this.hotService = hotService;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getFlowChartIds() {
        return flowChartIds;
    }

    public void setFlowChartIds(String flowChartIds) {
        this.flowChartIds = flowChartIds;
    }

    @Override
    public String toString() {
        return "ServerInfoModelDto{" +
                "token=" + token +
                ", id=" + id +
                ", biddingMaterials='" + biddingMaterials +
                ", createtime=" + createtime +
                ", handlingChannels='" + handlingChannels +
                ", handlingRequirements='" + handlingRequirements +
                ", initiationAuthority='" + initiationAuthority +
                ", keyword='" + keyword +
                ", online_Processing=" + online_Processing +
                ", online_Processing_addr='" + online_Processing_addr +
                ", processDescription='" + processDescription +
                ", processingTime=" + processingTime +
                ", serId='" + serId +
                ", serName='" + serName +
                ", serRange='" + serRange +
                ", settlementMaterials='" + settlementMaterials +
                ", sort=" + sort +
                ", status=" + status +
                ", summary='" + summary +
                ", type=" + type +
                ", serviceType=" + serviceType +
                ", serviceType=" + hotService +
                ", orgIds=" + orgIds +
                ", userIds=" + userIds +
                ", flowChartIds=" + flowChartIds +
                '}';
    }
}
