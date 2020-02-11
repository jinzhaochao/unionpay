package com.unionpay.services.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the server_info database table.
 * 
 */
@Entity
@Table(name="server_info")
@NamedQuery(name="ServerInfo.findAll", query="SELECT s FROM ServerInfo s")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class ServerInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//申办材料递送地址
	@Column(name="bidding_materials")
	private String biddingMaterials;

	//创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	//办理渠道
	@Column(name="handling_channels")
	private String handlingChannels;

	//办理要求
	@Column(name="handling_requirements")
	private String handlingRequirements;

	//发起权限
	@Column(name="initiation_authority")
	private String initiationAuthority;

	//关键字
	private String keyword;

	//是否在线办理
	private Byte online_Processing;

	//在线办理地址
	private String online_Processing_addr;

	//流程描述
	@Column(name="process_description")
	private String processDescription;

	//办理时间
	@Column(name="processing_time")
	private Byte processingTime;

	//平均时效
	@Column(name = "avg_time")
	private Integer avgTime;

	//服务编码
	@Column(name="ser_id")
	private String serId;

	//服务名称
	@Column(name="ser_name")
	private String serName;

	//服务范围
	@Column(name="ser_range")
	private String serRange;

	//办结材料领取地址
	@Column(name="settlement_materials")
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
	@Column(name = "service_type")
	private Byte serviceType;

	//热门服务
	@Column(name = "hot_service")
	private Byte hotService;

	/*//受理部门
	@OneToMany
	@JoinColumn(name = "server_id")
	private List<ServerAcceptanceDept> serverAcceptanceDept;

	//受理人
	@OneToMany
	@JoinColumn(name = "server_id")
	private List<ServerAcceptanceUser> serverAcceptanceUser;

	//流程图
	@OneToMany
	@JoinColumn(name = "server_id")
	private List<ServerAttachmentFlowChart> serverAttachmentFlowChart;*/
	
	/*public List<ServerAcceptanceDept> getServerAcceptanceDept() {
		return serverAcceptanceDept;
	}

	public void setServerAcceptanceDept(List<ServerAcceptanceDept> serverAcceptanceDept) {
		this.serverAcceptanceDept = serverAcceptanceDept;
	}

	public List<ServerAcceptanceUser> getServerAcceptanceUser() {
		return serverAcceptanceUser;
	}

	public void setServerAcceptanceUser(List<ServerAcceptanceUser> serverAcceptanceUser) {
		this.serverAcceptanceUser = serverAcceptanceUser;
	}

	public List<ServerAttachmentFlowChart> getServerAttachmentFlowChart() {
		return serverAttachmentFlowChart;
	}

	public void setServerAttachmentFlowChart(List<ServerAttachmentFlowChart> serverAttachmentFlowChart) {
		this.serverAttachmentFlowChart = serverAttachmentFlowChart;
	}*/

	public ServerInfo() {
	}

    /*@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
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

	public Integer getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(Integer avgTime) {
		this.avgTime = avgTime;
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

	@Override
	public String toString() {
		return "ServerInfo{" +
				"id=" + id +
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
				", avgTime=" + avgTime +
				", serId='" + serId +
				", serName='" + serName +
				", serRange='" + serRange +
				", settlementMaterials='" + settlementMaterials +
				", sort=" + sort +
				", status=" + status +
				", summary='" + summary +
				", type=" + type +
				", serviceType=" + serviceType +
				", hotService=" + hotService +
				'}';
	}
}