package com.unionpay.services.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 行为记录实体类
 * @author lishuang
 * @date 2019/08/27
 */
@Entity
@Table(name = "server_behavior", schema = "cms")
@NamedQuery(name="ServerBehavior.findAll", query="SELECT s FROM ServerBehavior s")
public class ServerBehavior implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "behavior_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer behaviorId;
    @Column(name = "dict_id")
    private Integer dictId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "emp_name")
    private String empName;
    @Column(name = "org_id")
    private Integer orgId;
    @Column(name = "org_name")
    private String orgName;
    @Column(name = "status")
    private Integer status;
    @Column(name = "create_time")
    private String createTime;


    public Integer getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(Integer behaviorId) {
        this.behaviorId = behaviorId;
    }

    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ServerBehavior{" +
                "behaviorId=" + behaviorId +
                ", dictId=" + dictId +
                ", userId=" + userId +
                ", empName=" + empName +
                ", orgId=" + orgId +
                ", orgName=" + orgName +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
