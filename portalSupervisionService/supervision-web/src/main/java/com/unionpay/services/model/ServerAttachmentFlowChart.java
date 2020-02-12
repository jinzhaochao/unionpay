package com.unionpay.services.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the server_attachment_flow_chart database table.
 * 
 */
@Entity
@Table(name="server_attachment_flow_chart")
@NamedQuery(name="ServerAttachmentFlowChart.findAll", query="SELECT s FROM ServerAttachmentFlowChart s")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ServerAttachmentFlowChart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name="server_id")
	private Integer serverId;
	@Column(name = "url")
	private String url;


	public ServerAttachmentFlowChart() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getServerId() {
		return this.serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	public String toString() {
		return "ServerAttachmentFlowChart{" +
				"id=" + id +
				", name='" + name +
				", serverId=" + serverId +
				", url='" + url +
				'}';
	}
}