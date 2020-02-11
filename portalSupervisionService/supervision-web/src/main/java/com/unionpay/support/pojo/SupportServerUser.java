package com.unionpay.support.pojo;

import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description: 服务人实体类
 */
@Entity
@Table(name="support_server_user")
@NamedQuery(name="SupportServerUser.findAll", query="SELECT s FROM SupportServerUser s")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class SupportServerUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //创建时间
    @Column(name="create_time")
    private String createTime;

    //服务人name
    @Column(name="server_name")
    private String serverName;

    //办公电话
    @Column(name="office_phone")
    private String officePhone;

    //邮箱
    private String pemail;

    //手机号
    private String mobileno;

    //状态（1在用；2禁用）
    private Integer status;

    public SupportServerUser() {
    }

    public SupportServerUser(String createTime, String serverName, String officePhone, String pemail, String mobileno, Integer status) {
        this.createTime = createTime;
        this.serverName = serverName;
        this.officePhone = officePhone;
        this.pemail = pemail;
        this.mobileno = mobileno;
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SupportServerUser{" +
                "id=" + id +
                ", createTime='" + createTime + '\'' +
                ", serverName='" + serverName + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", pemail='" + pemail + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", status=" + status +
                '}';
    }
}
