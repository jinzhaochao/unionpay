package com.unionpay.services.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the server_attachment database table.
 * 
 */
@Entity
@Table(name="server_attachment")
@NamedQuery(name="ServerAttachment.findAll", query="SELECT s FROM ServerAttachment s")
public class ServerAttachment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="mater_id")
	private int materId;
	@Column(name = "name")
	private String name;
	@Column(name = "type")
	private Integer type;
	@Column(name = "url")
	private String url;

	public ServerAttachment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaterId() {
		return this.materId;
	}

	public void setMaterId(int materId) {
		this.materId = materId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ServerAttachment{" +
				"id=" + id +
				", materId=" + materId +
				", name='" + name +
				", type=" + type +
				", url='" + url +
				'}';
	}
}