package com.unionpay.supervision.domain;

import javax.persistence.*;

/**
 * 流程平台获取主办部门信息实体类
 * @author lishuang
 * @date 2019-03-22
 */
@Entity
@Table(name="super_lc_sponsor")
@NamedQuery(name = "SuperLcSponsor.findAll",query = "SELECT s FROM SuperLcSponsor s")
public class SuperLcSponsor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "userid")
    private String userId;
    @Column(name = "empname")
    private String empName;

    public SuperLcSponsor() {
    }

    public SuperLcSponsor(Integer id, String userId, String empName) {
        this.id = id;
        this.userId = userId;
        this.empName = empName;
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

    @Override
    public String toString() {
        return "SuperLcSponsor{" +
                "id=" + id +
                ", userId='" + userId +
                ", empName='" + empName +
                '}';
    }
}
