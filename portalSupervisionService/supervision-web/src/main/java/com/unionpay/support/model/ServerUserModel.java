package com.unionpay.support.model;

import com.alibaba.excel.metadata.BaseRowModel;

import javax.persistence.Column;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:服务人新增实体类
 */
public class ServerUserModel  {

    private int id;

    //服务人name
    private String serverName;

    //办公电话
    private String officePhone;

    //邮箱
    private String pemail;

    //手机号
    private String mobileno;

    //状态（1在用；2禁用）
    private Integer status;

    public ServerUserModel() {
    }

    public ServerUserModel(int id, String serverName, String officePhone, String pemail, String mobileno, Integer status) {
        this.id = id;
        this.serverName = serverName;
        this.officePhone = officePhone;
        this.pemail = pemail;
        this.mobileno = mobileno;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
