package com.unionpay.services.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 意见反馈实体类
 * @author lishuang
 * @date 2019-03-19
 */
@Entity
@Table(name = "server_opinion_feedback")
@NamedQuery(name = "Server0pinionFeedback.findAll",query = "SELECT s FROM Server0pinionFeedback s")
public class Server0pinionFeedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "server_id")
    private Integer serverId;
    private String content;
    @Column(name = "emp_id")
    private Integer empId;
    private Date createtime;
    private Integer status;
    @Column(name = "reply_id")
    private Integer replyId;
    @Column(name = "reply_time")
    private Date replyTime;
    @Column(name = "reply_reason")
    private String replyReason;

    public Server0pinionFeedback() {
    }

    public Server0pinionFeedback(Integer serverId, String content, Integer empId, Date createtime, Integer status, Integer replyId, Date replyTime, String replyReason) {
        this.serverId = serverId;
        this.content = content;
        this.empId = empId;
        this.createtime = createtime;
        this.status = status;
        this.replyId = replyId;
        this.replyTime = replyTime;
        this.replyReason = replyReason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyReason() {
        return replyReason;
    }

    public void setReplyReason(String replyReason) {
        this.replyReason = replyReason;
    }
}
