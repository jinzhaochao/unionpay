package com.unionpay.services.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 服务中心_服务流程关系实体类
 * @author lishuang
 * @date 2019/10/10
 */
@Entity
@Table(name = "server_apply_flow")
@NamedQuery(name="ServerApplyFlow.findAll", query="SELECT s FROM ServerApplyFlow s")
public class ServerApplyFlow implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ser_id")
    private Integer serId;
    @Column(name = "ser_name")
    private String serName;
    @Column(name = "server_item_id")
    private String serverItemId;
    @Column(name = "flow_name_id")
    private String flowNameId;
    @Column(name = "flow_name")
    private String flowName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSerId() {
        return serId;
    }

    public void setSerId(Integer serId) {
        this.serId = serId;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public String getServerItemId() {
        return serverItemId;
    }

    public void setServerItemId(String serverItemId) {
        this.serverItemId = serverItemId;
    }

    public String getFlowNameId() {
        return flowNameId;
    }

    public void setFlowNameId(String flowNameId) {
        this.flowNameId = flowNameId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    @Override
    public String toString() {
        return "ServerApplyFlow{" +
                "id=" + id +
                ", serId=" + serId +
                ", serName=" + serName +
                ", serverItemId=" + serverItemId +
                ", flowNameId=" + flowNameId +
                ", flowName=" + flowName +
                '}';
    }
}
