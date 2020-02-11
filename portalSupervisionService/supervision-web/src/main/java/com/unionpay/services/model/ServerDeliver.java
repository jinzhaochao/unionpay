package com.unionpay.services.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 咨询投诉转递表实体类
 * jinzhao   2019-12-18
 */
@Entity
@Table(name="server_deliver")
@NamedQuery(name="ServerDeliver.findAll", query="SELECT s FROM ServerDeliver s")
public class ServerDeliver implements Serializable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "suggest_id")
    private Integer suggestId;

    /**
     * 转递人
     */
    @Column(name = "deliver_userid")
    private String deliverUserid;

    /**
     * 转递人名字
     */
    @Column(name = "deliver_username")
    private String deliverUsername;

    /**
     * 转递时间
     */
    @Column(name = "deliver_time")
    private String deliverTime;

    /**
     * 被转递人
     */
    @Column(name = "reply_userid")
    private String replyUserid;

    /**
     * 被转递人名字
     */
    @Column(name = "reply_username")
    private String replyUsername;

    /**
     * 转递内容
     */
    @Column(name = "deliver_content")
    private String deliverContent;

    public ServerDeliver() {
    }

    public ServerDeliver(Integer suggestId, String deliverUserid, String deliverUsername, String deliverTime, String replyUserid, String replyUsername, String deliverContent) {
        this.suggestId = suggestId;
        this.deliverUserid = deliverUserid;
        this.deliverUsername = deliverUsername;
        this.deliverTime = deliverTime;
        this.replyUserid = replyUserid;
        this.replyUsername = replyUsername;
        this.deliverContent = deliverContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(Integer suggestId) {
        this.suggestId = suggestId;
    }

    public String getDeliverUserid() {
        return deliverUserid;
    }

    public void setDeliverUserid(String deliverUserid) {
        this.deliverUserid = deliverUserid;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getReplyUserid() {
        return replyUserid;
    }

    public void setReplyUserid(String replyUserid) {
        this.replyUserid = replyUserid;
    }

    public String getDeliverContent() {
        return deliverContent;
    }

    public void setDeliverContent(String deliverContent) {
        this.deliverContent = deliverContent;
    }

    public String getDeliverUsername() {
        return deliverUsername;
    }

    public void setDeliverUsername(String deliverUsername) {
        this.deliverUsername = deliverUsername;
    }

    public String getReplyUsername() {
        return replyUsername;
    }

    public void setReplyUsername(String replyUsername) {
        this.replyUsername = replyUsername;
    }
}
