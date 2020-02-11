package com.unionpay.sms.model;

/**
 * 联系人分页返回结果
 * 
 * @author wangyue
 * @date 2018-12-10
 *
 */
public class SmsReceiverResult {
	private String unid;
	private String receiverName;
	private String receiverTel;
	private String orgName;
	private String type;
	private String sendTime;
	private String status;

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverTel() {
		return receiverTel;
	}

	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "SmsReceiverResult{" +
				"unid='" + unid +
				", receiverName='" + receiverName +
				", receiverTel='" + receiverTel +
				", orgName='" + orgName +
				", type='" + type +
				", sendTime='" + sendTime +
				", status='" + status +
				'}';
	}
}
