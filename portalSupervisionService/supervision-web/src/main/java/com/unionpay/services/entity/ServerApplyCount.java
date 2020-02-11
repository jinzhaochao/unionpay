package com.unionpay.services.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 服务统计信息实体类
 * @author lishuang
 * @date 2019/10/10
 */
@Entity
@Table(name = "server_apply_count")
@NamedQuery(name="ServerApplyCount.findAll", query="SELECT s FROM ServerApplyCount s")
public class ServerApplyCount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "server_total")
    private Integer serverTotal;
    @Column(name = "underway_count")
    private Integer underwayCount;
    @Column(name = "average_time")
    private Integer averageTime;
    @Column(name = "complaint_count")
    private Integer complaintCount;
    @Column(name = "server_increments")
    private String serverIncrements;
    @Column(name = "satisfied_count")
    private Integer satisfiedCount;
    @Column(name = "unsatisfied_count")
    private Integer unSatisfiedCount;
    @Column(name = "type")
    private Integer type;
    @Column(name = "create_time")
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServerTotal() {
        return serverTotal;
    }

    public void setServerTotal(Integer serverTotal) {
        this.serverTotal = serverTotal;
    }

    public Integer getUnderwayCount() {
        return underwayCount;
    }

    public void setUnderwayCount(Integer underwayCount) {
        this.underwayCount = underwayCount;
    }

    public Integer getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Integer averageTime) {
        this.averageTime = averageTime;
    }

    public Integer getComplaintCount() {
        return complaintCount;
    }

    public void setComplaintCount(Integer complaintCount) {
        this.complaintCount = complaintCount;
    }

    public String getServerIncrements() {
        return serverIncrements;
    }

    public void setServerIncrements(String serverIncrements) {
        this.serverIncrements = serverIncrements;
    }

    public Integer getSatisfiedCount() {
        return satisfiedCount;
    }

    public void setSatisfiedCount(Integer satisfiedCount) {
        this.satisfiedCount = satisfiedCount;
    }

    public Integer getUnSatisfiedCount() {
        return unSatisfiedCount;
    }

    public void setUnSatisfiedCount(Integer unSatisfiedCount) {
        this.unSatisfiedCount = unSatisfiedCount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ServerApplyCount{" +
                "id=" + id +
                ", serverTotal=" + serverTotal +
                ", underwayCount=" + underwayCount +
                ", averageTime=" + averageTime +
                ", complaintCount=" + complaintCount +
                ", serverIncrements=" + serverIncrements +
                ", satisfiedCount=" + satisfiedCount +
                ", unSatisfiedCount=" + unSatisfiedCount +
                ", type=" + type +
                ", createTime='" + createTime +
                '}';
    }
}
