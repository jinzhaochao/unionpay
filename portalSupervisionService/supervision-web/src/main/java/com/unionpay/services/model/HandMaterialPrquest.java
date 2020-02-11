package com.unionpay.services.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/22/022 16:07
 * @Description:
 */
public class HandMaterialPrquest implements Serializable {
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;

    private String name;

    private Integer necessary;

    private Integer number;

    private String remark;

    private Integer sort;

    private Integer status;

    private Integer type;

    @Column(name="server_id")
    private int serverId;

    //附件
    @OneToMany
    @JoinColumn(name = "mater_id")
    private List<ServerAttachment> serverAttachments;

    private Integer mater_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNecessary() {
        return necessary;
    }

    public void setNecessary(Integer necessary) {
        this.necessary = necessary;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public List<ServerAttachment> getServerAttachments() {
        return serverAttachments;
    }

    public void setServerAttachments(List<ServerAttachment> serverAttachments) {
        this.serverAttachments = serverAttachments;
    }

    public Integer getMater_id() {
        return mater_id;
    }

    public void setMater_id(Integer mater_id) {
        this.mater_id = mater_id;
    }
}
