package com.unionpay.sms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wangyue
 * @Date 2018-12-7
 * The persistent class for the sms_receiver database table.
 */
@Entity
@Table(name="sms_receiver")
public class smsReceiver implements Serializable{
  @Id
  private String unid;
  @Column(name="receiver_id")
  private String receiverId;
  @Column(name="receiver_name")
  private String receiverName;
  @Column(name="receiver_tel")
  private String receiverTel;
  @Column(name="matter_unid")
  private String matterUnid;
  @Column(name="org_id")
  private Integer orgId;
  @Column(name="org_name")
  private String orgName;
  @Column(name="org_level")
  private String orgLevel;
  @Column(name="org_remark")
  private String orgRemark;
  @Column(name="send_time")
  private String sendTime;
  private String status;
  private String type;
  private String note;
public String getUnid() {
	return unid;
}
public void setUnid(String unid) {
	this.unid = unid;
}
public String getReceiverId() {
	return receiverId;
}
public void setReceiverId(String receiverId) {
	this.receiverId = receiverId;
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
public String getMatterUnid() {
	return matterUnid;
}
public void setMatterUnid(String matterUnid) {
	this.matterUnid = matterUnid;
}
public Integer getOrgId() {
	return orgId;
}
public void setOrgId(Integer orgId) {
	this.orgId = orgId;
}
public String getOrgName() {
	return orgName;
}
public void setOrgName(String orgName) {
	this.orgName = orgName;
}
public String getOrgLevel() {
	return orgLevel;
}
public void setOrgLevel(String orgLevel) {
	this.orgLevel = orgLevel;
}
public String getOrgRemark() {
	return orgRemark;
}
public void setOrgRemark(String orgRemark) {
	this.orgRemark = orgRemark;
}
public String getSendTime() {
	return sendTime;
}
public void setSendTime(String sendTime) {
	this.sendTime = sendTime;
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
public String getNote() {
	return note;
}
public void setNote(String note) {
	this.note = note;
}

public smsReceiver() {
	super();
}
@Override
public String toString() {
	return "smsReceiver [unid=" + unid + ", receiverId=" + receiverId + ", receiverName=" + receiverName
			+ ", receiverTel=" + receiverTel + ", matterUnid=" + matterUnid + ", orgId=" + orgId + ", orgName="
			+ orgName + ", orgLevel=" + orgLevel + ", orgRemark=" + orgRemark + ", sendTime=" + sendTime + ", status="
			+ status + ", type=" + type + ", note=" + note + "]";
}
  
}

