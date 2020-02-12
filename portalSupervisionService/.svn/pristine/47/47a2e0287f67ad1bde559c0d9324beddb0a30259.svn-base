package com.unionpay.supervision.model;

public class StatisticalDto {

	// 督办类型
	private String overseeUnid;
	// 事项类型
	private String typeName;
	// 事项编号
	private String serviceId;
	// 分公司领导
	private String branchedLeader;
	// 主办单位
	private String orgName;
	// 主办人
	private String sponsorName;
	// 督办人
	private String overseeUsername;
	// 来源时间的开始时间
	private String serviceStartTime;
	// 来源时间的结束时间
	private String serviceEndTime;
	
	
	
	public String getOverseeUnid() {
		return overseeUnid;
	}
	public void setOverseeUnid(String overseeUnid) {
		this.overseeUnid = overseeUnid;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getBranchedLeader() {
		return branchedLeader;
	}
	public void setBranchedLeader(String branchedLeader) {
		this.branchedLeader = branchedLeader;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public String getOverseeUsername() {
		return overseeUsername;
	}
	public void setOverseeUsername(String overseeUsername) {
		this.overseeUsername = overseeUsername;
	}
	public String getServiceStartTime() {
		return serviceStartTime;
	}
	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}
	
	public String getServiceEndTime() {
		return serviceEndTime;
	}
	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}
	public StatisticalDto() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchedLeader == null) ? 0 : branchedLeader.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result + ((overseeUnid == null) ? 0 : overseeUnid.hashCode());
		result = prime * result + ((overseeUsername == null) ? 0 : overseeUsername.hashCode());
		result = prime * result + ((serviceStartTime == null) ? 0 : serviceStartTime.hashCode());
		result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
		result = prime * result + ((serviceStartTime == null) ? 0 : serviceStartTime.hashCode());
		result = prime * result + ((sponsorName == null) ? 0 : sponsorName.hashCode());
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatisticalDto other = (StatisticalDto) obj;
		if (branchedLeader == null) {
			if (other.branchedLeader != null)
				return false;
		} else if (!branchedLeader.equals(other.branchedLeader))
			return false;
		if (orgName == null) {
			if (other.orgName != null)
				return false;
		} else if (!orgName.equals(other.orgName))
			return false;
		if (overseeUnid == null) {
			if (other.overseeUnid != null)
				return false;
		} else if (!overseeUnid.equals(other.overseeUnid))
			return false;
		if (overseeUsername == null) {
			if (other.overseeUsername != null)
				return false;
		} else if (!overseeUsername.equals(other.overseeUsername))
			return false;
		if (serviceStartTime == null) {
			if (other.serviceStartTime != null)
				return false;
		} else if (!serviceStartTime.equals(other.serviceStartTime))
			return false;
		if (serviceId == null) {
			if (other.serviceId != null)
				return false;
		} else if (!serviceId.equals(other.serviceId))
			return false;
		if (serviceStartTime == null) {
			if (other.serviceStartTime != null)
				return false;
		} else if (!serviceStartTime.equals(other.serviceStartTime))
			return false;
		if (sponsorName == null) {
			if (other.sponsorName != null)
				return false;
		} else if (!sponsorName.equals(other.sponsorName))
			return false;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		return true;
	}
	
	public static boolean isEmpty(StatisticalDto statisticalDto) {
		if(statisticalDto == null){
			return true;
		}
		if(statisticalDto.hashCode() == -196513505){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		StatisticalDto dto = new StatisticalDto();
		System.out.println(dto.hashCode());
		System.out.println(dto.equals(new StatisticalDto()));
		System.out.println(isEmpty(dto));
	}

}
