package com.unionpay.supervision.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * jinzhao
 * 2019-11-19
 * 督办部门表和标签表的中间表
 */
@Entity
@Table(name="super_sponsor_tag")
@NamedQuery(name="SuperSponsorTag.findAll", query="SELECT s FROM SuperSponsorTag s")
public class SuperSponsorTag implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tag_id")
    private Integer tagId;

    @Column(name = "sponsor_unid")
    private String sponsorUnid;

    @Column(name = "create_time")
    private String createTime;

    public SuperSponsorTag() {
    }

    public SuperSponsorTag(Integer id, Integer tagId, String sponsorUnid, String createTime) {
        this.id = id;
        this.tagId = tagId;
        this.sponsorUnid = sponsorUnid;
        this.createTime = createTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getSponsorUnid() {
        return sponsorUnid;
    }

    public void setSponsorUnid(String sponsorUnid) {
        this.sponsorUnid = sponsorUnid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}