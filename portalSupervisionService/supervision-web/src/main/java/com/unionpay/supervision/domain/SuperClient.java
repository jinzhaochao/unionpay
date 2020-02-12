package com.unionpay.supervision.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the super_client database table.
 * 
 */
@Entity
@Table(name="super_client")
@NamedQuery(name="SuperClient.findAll", query="SELECT s FROM SuperClient s")
public class SuperClient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="app_key")
	private String appKey;

	@Column(name="access_token")
	private String accessToken;

	@Column(name="app_secret")
	private String appSecret;

	@Column(name="client_note")
	private String clientNote;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="create_userid")
	private String createUserid;

	private String permission;

	private int status;

	public SuperClient() {
	}

	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getClientNote() {
		return this.clientNote;
	}

	public void setClientNote(String clientNote) {
		this.clientNote = clientNote;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreate_userId() {
		return this.createUserid;
	}

	public void setCreate_userId(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}