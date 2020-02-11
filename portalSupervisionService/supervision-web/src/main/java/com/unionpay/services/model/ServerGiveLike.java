package com.unionpay.services.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "server_give_like")
@NamedQuery(name = "ServerGiveLike.findAll",query = "select s from ServerGiveLike s")
public class ServerGiveLike {
    @Id
    private long id;

    /*
      服务咨询id
    */
    @Column(name = "suggestion_id")
    private Integer suggestionId;

    /*
       点赞人 id
     */
    @Column(name = "user_id")
    private String userId;

    /*
       状态: 0取消点赞，1点赞
     */
    private Integer status;

    /*
       创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    public ServerGiveLike() {
    }

    public ServerGiveLike(long id, Integer suggestionId, String userId, Integer status, Date createTime) {
        this.id = id;
        this.suggestionId = suggestionId;
        this.userId = userId;
        this.status = status;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(Integer suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ServerGiveLike{" +
                "id=" + id +
                ", suggestionId=" + suggestionId +
                ", userId='" + userId + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
