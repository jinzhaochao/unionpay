package com.unionpay.services.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the server_common_problem database table.
 * 
 */
@Entity
@Table(name="server_common_problem")
@NamedQuery(name="ServerCommonProblem.findAll", query="SELECT s FROM ServerCommonProblem s")
public class ServerCommonProblem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String answer;

	private String ask;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	private Integer sort;

	private Integer status;

	@Column(name="server_id")
	private int serverId;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public ServerCommonProblem() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAsk() {
		return this.ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}