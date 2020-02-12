package com.unionpay.supervision.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;


/**
 * jinzhao
 * 2019-11-15
 * 督办标签实体类
 */
@Entity
@Table(name="super_tag")
@NamedQuery(name="SuperTag.findAll", query="SELECT s FROM SuperTag s")

public class SuperTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tagId;

	@Column(name="tag_content")
	private String tagContent;

	@Column(name="create_time")
	private String createTime;



	public SuperTag() {
	}

	public SuperTag(Integer tagId, String tagContent, String createTime) {
		this.tagId = tagId;
		this.tagContent = tagContent;
		this.createTime = createTime;
	}



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagContent() {
		return tagContent;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SuperTag{" +
				"tagId=" + tagId +
				", tagContent='" + tagContent + '\'' +
				", createTime='" + createTime + '\'' +
				'}';
	}
}