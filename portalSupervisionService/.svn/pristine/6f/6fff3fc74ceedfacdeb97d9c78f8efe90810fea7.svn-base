package com.unionpay.services.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the server_acceptance_user database table.
 * 
 */
@Entity
@Table(name="server_acceptance_user")
@NamedQuery(name="ServerAcceptanceUser.findAll", query="SELECT s FROM ServerAcceptanceUser s")
public class ServerAcceptanceUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="server_id")
	private int serverId;

	@Column(name="user_id")
	private int userId;

	@Column(name = "user_name")
	private String userName;

	public ServerAcceptanceUser() {
	}
	
	public ServerAcceptanceUser(int userId,int serverId,String userName) {
		this.serverId=serverId;
		this.userId=userId;
		this.userName=userName;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getServerId() {
		return this.serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}