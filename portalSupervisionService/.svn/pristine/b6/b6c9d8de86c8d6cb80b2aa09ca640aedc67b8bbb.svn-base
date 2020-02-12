package com.unionpay.pager.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @description: 商户入网需求MCC实体类
 * @author: lishuang
 * @date: 2019/12/04
 */
@Entity
@Table(name = "pager_business_mcc")
public class BusinessMcc implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BusinessMcc{" +
				"id=" + id +
				", name=" + name +
				", value=" + value +
				'}';
	}
}
