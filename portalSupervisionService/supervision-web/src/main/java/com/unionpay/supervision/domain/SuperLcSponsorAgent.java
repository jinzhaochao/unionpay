package com.unionpay.supervision.domain;

import javax.persistence.*;

/**
 * 流程平台获取部门督办扎口代理人信息实体类
 * @author jinzhao
 * @date 2019-10-16
 */
@Entity
@Table(name="super_lc_sponsor_agent")
@NamedQuery(name = "SuperLcSponsorAgent.findAll",query = "SELECT sa FROM SuperLcSponsorAgent sa")
public class SuperLcSponsorAgent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "userid")
    private String userId;
    @Column(name = "empname")
    private String empName;
    @Column(name = "agentUserid")
    private String agentUserId;
    private String agentUserName;

    public SuperLcSponsorAgent() {
    }

    public SuperLcSponsorAgent(String userId, String empName, String agentUserId, String agentUserName) {
        this.userId = userId;
        this.empName = empName;
        this.agentUserId = agentUserId;
        this.agentUserName = agentUserName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(String agentUserId) {
        this.agentUserId = agentUserId;
    }

    public String getAgentUserName() {
        return agentUserName;
    }

    public void setAgentUserName(String agentUserName) {
        this.agentUserName = agentUserName;
    }

    @Override
    public String toString() {
        return "SuperLcSponsorAgent{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", empName='" + empName + '\'' +
                ", agentUserId='" + agentUserId + '\'' +
                ", agentUserName='" + agentUserName + '\'' +
                '}';
    }
}
