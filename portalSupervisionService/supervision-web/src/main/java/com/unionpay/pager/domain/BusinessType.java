package com.unionpay.pager.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @description: 商户入网需求字典信息实体类
 * @author: lishuang
 * @date: 2019/12/04
 */
@Entity
@Table(name = "pager_business_type")
public class BusinessType implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "parent_id")
	private Integer parentId;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "dict_type")
	private String dictType;
	private String name;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Byte status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BusinessType{" +
				"id=" + id +
				", parentId=" + parentId +
				", dictType=" + dictType +
				", name=" + name +
				", status=" + status +
				'}';
	}
}
