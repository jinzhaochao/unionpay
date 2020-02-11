package com.unionpay.supervision.domain;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the super_type_service database table.
 * 
 */
@Entity
@Table(name="super_type_service")
@NamedQuery(name="SuperTypeService.findAll", query="SELECT s FROM SuperTypeService s")
public class SuperTypeService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String unid;

	@Column(name="create_time")
	private String createTime;

	@Column(name="create_userid")
	private String createUserid;

	private String note;

	@Column(name="type_id")
	private String typeId;

	@Column(name="type_name")
	private String typeName;

	@Column(name="type_sort")
	private int typeSort;

	public SuperTypeService() {
	}

	public String getUnid() {
		return this.unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserid() {
		return this.createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTypeSort() {
		return this.typeSort;
	}

	public void setTypeSort(int typeSort) {
		this.typeSort = typeSort;
	}

}