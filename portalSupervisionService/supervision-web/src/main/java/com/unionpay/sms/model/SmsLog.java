package com.unionpay.sms.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 短信推送记录日志实体类
 * @author lishuang
 * @date 2019-04-25
 */
@Entity
@Table(name="sms_log")
@NamedQuery(name="SmsLog.findAll", query="SELECT s FROM SmsLog s")
public class SmsLog implements Serializable {
    private static final long serialVersionUID = 1L;
    //日志记录主键id
    @Id
    @Column(name="sms_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer smsLogId;
    //短信申请记录主键
    @Column(name = "sms_unid")
    private String unid;
    //操作平台
    @Column(name = "client_type")
    private Integer clientType;
    //历史数据
    @Column(name = "chg_data")
    private String chgData;
    //日志状态
    @Column(name = "status")
    private Integer status;
    //日志创建时间
    @Column(name = "create_time")
    private String createTime;

    public SmsLog() {
    }

    public Integer getSmsLogId() {
        return smsLogId;
    }

    public void setSmsLogId(Integer smsLogId) {
        this.smsLogId = smsLogId;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
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
        return "SmsLog{" +
                "smsLogId=" + smsLogId +
                ", unid=" + unid +
                ", clientType=" + clientType +
                ", chgData=" + chgData +
                ", status=" + status +
                ", createTime=" + createTime +
                "}";
    }
}
