package com.unionpay.services.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the server_acceptance_dept database table.
 * 
 */
@Entity
@Table(name="server_acceptance_dept")
@NamedQuery(name="ServerAcceptanceDept.findAll", query="SELECT s FROM ServerAcceptanceDept s")
public class ServerAcceptanceDept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="org_id")
	private int orgId;

	@Column(name="server_id")
	private int serverId;

	public ServerAcceptanceDept() {
	}
	
	public ServerAcceptanceDept(int orgId,int serverId) {
		this.orgId=orgId;
		this.serverId=serverId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getOrgId() {
		return this.orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getServerId() {
		return this.serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

}