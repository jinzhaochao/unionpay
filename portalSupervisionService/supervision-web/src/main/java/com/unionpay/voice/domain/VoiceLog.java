package com.unionpay.voice.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "voice_log")
@NamedQuery(name="VoiceLog.findAll", query="SELECT v FROM VoiceLog v")
public class VoiceLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voice_log_id")
    private Integer voiceLogId;
    @Column(name = "voice_info_unid")
    private String voiceInfoUnid;
    @Column(name = "client_type")
    private String clientType;
    @Column(name = "interface_type")
    private String interfaceType;
    @Column(name = "chg_data")
    private String chgData;
    @Column(name = "result_status")
    private String resultStatus;
    @Column(name = "result_data")
    private String resultData;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "status")
    private Integer status;

    public Integer getVoiceLogId() {
        return voiceLogId;
    }

    public void setVoiceLogId(Integer voiceLogId) {
        this.voiceLogId = voiceLogId;
    }

    public String getVoiceInfoUnid() {
        return voiceInfoUnid;
    }

    public void setVoiceInfoUnid(String voiceInfoUnid) {
        this.voiceInfoUnid = voiceInfoUnid;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getChgData() {
        return chgData;
    }

    public void setChgData(String chgData) {
        this.chgData = chgData;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VoiceLog{" +
                "voiceLogId=" + voiceLogId +
                ", voiceInfoUnid=" + voiceInfoUnid +
                ", clientType=" + clientType +
                ", interfaceType=" + interfaceType +
                ", chgData=" + chgData +
                ", resultStatus='" + resultStatus +
                ", resultData=" + resultData +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}
