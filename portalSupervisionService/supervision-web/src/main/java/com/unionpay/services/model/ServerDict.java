package com.unionpay.services.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the server_dict database table.
 * 
 */
@Entity
@Table(name="server_dict")
@NamedQuery(name="ServerDict.findAll", query="SELECT s FROM ServerDict s")
public class ServerDict implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte id;

	private String name;

	private byte sort;

	private byte status;

	private String type;

	private String value;

	public ServerDict() {
	}

	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getSort() {
		return this.sort;
	}

	public void setSort(byte sort) {
		this.sort = sort;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}