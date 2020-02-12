package com.unionpay.supervision.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the super_service_id_create database table.
 * 
 */
@Entity
@Table(name="super_service_id_create")
@NamedQuery(name="SuperServiceIdCreate.findAll", query="SELECT s FROM SuperServiceIdCreate s")
public class SuperServiceIdCreate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="create_time")
	private String createTime;

	@Column(name="oversee_type_id")
	private String overseeTypeId;

	private int sort;

	@Column(name="update_time")
	private String updateTime;

	private int year;

	public SuperServiceIdCreate() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOverseeTypeId() {
		return this.overseeTypeId;
	}

	public void setOverseeTypeId(String overseeTypeId) {
		this.overseeTypeId = overseeTypeId;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}