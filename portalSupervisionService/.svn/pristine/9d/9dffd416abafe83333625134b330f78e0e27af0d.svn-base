package com.unionpay.pager.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @description: 商户入网需求信息与MCC关系实体类
 * @author: lishuang
 * @date: 2019/12/04
 */
@Entity
@Table(name = "pager_business_require_r_mcc")
public class BusinessRequireRMcc implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "require_id")
	private Integer requireId;

	@Column(name = "mcc_id")
	private Integer mccId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRequireId() {
		return requireId;
	}

	public void setRequireId(Integer requireId) {
		this.requireId = requireId;
	}

	public Integer getMccId() {
		return mccId;
	}

	public void setMccId(Integer mccId) {
		this.mccId = mccId;
	}

	@Override
	public String toString() {
		return "BusinessRequireRMcc{" +
				"id=" + id +
				", requireId=" + requireId +
				", mccId=" + mccId +
				'}';
	}
}
