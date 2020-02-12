package com.unionpay.services.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 定时拉取流程平台推送数据日志实体类
 * @author lishuang
 * @date 2019/10/10
 */
@Entity
@Table(name = "server_apply_log")
@NamedQuery(name="ServerApplyLog.findAll", query="SELECT s FROM ServerApplyLog s")
public class ServerApplyLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "bizcode")
    private String bizcode;
    @Column(name = "client_type")
    private Integer clientType;
    @Column(name = "apply_id")
    private Long applyId;
    @Column(name = "ser_name")
    private String serName;
    @Column(name = "chg_data")
    private String chgData;
    @Column(name = "status")
    private Integer status;
    @Column(name = "create_time")
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBizcode() {
        return bizcode;
    }

    public void setBizcode(String bizcode) {
        this.bizcode = bizcode;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public String getChgData() {
        return chgData;
    }

    public void setChgData(String chgData) {
        this.chgData = chgData;
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
        return "ServerApplyLog{" +
                "id=" + id +
                ", bizcode=" + bizcode +
                ", clientType=" + clientType +
                ", applyId=" + applyId +
                ", serName=" + serName +
                ", chgData=" + chgData +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
