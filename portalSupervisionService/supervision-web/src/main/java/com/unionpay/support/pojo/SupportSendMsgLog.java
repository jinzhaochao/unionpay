package com.unionpay.support.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
* @Author: jinzhao
* @Date: 2019/11/14 17:17
* @Description:
*/
@Entity
@Table(name="support_send_msg_log")
@NamedQuery(name="SupportSendMsgLog.findAll", query="SELECT s FROM SupportSendMsgLog s")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class SupportSendMsgLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //关联预约信息表
    @Column(name = "support_info_id")
    private Integer supportInfoId;

    //发送信息
    private String data;

    //发送人
    private String sendUserid;

    //接收人
    private String receiveUserid;

    //发送类型
    @Column(name = "send_type")
    private String sendType;

    //创建时间
    @Column(name="create_time")
    private String createTime;

    //发送状态
    @Column(name="send_status")
    private String sendStatus;

    public SupportSendMsgLog() {
    }

    public SupportSendMsgLog(Integer supportInfoId, String data, String sendUserid, String receiveUserid, String sendType, String createTime, String sendStatus) {
        this.supportInfoId = supportInfoId;
        this.data = data;
        this.sendUserid = sendUserid;
        this.receiveUserid = receiveUserid;
        this.sendType = sendType;
        this.createTime = createTime;
        this.sendStatus = sendStatus;
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

    public Integer getSupportInfoId() {
        return supportInfoId;
    }

    public void setSupportInfoId(Integer supportInfoId) {
        this.supportInfoId = supportInfoId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSendUserid() {
        return sendUserid;
    }

    public void setSendUserid(String sendUserid) {
        this.sendUserid = sendUserid;
    }

    public String getReceiveUserid() {
        return receiveUserid;
    }

    public void setReceiveUserid(String receiveUserid) {
        this.receiveUserid = receiveUserid;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }
}
