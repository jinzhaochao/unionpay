package com.unionpay.services.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 服务中心当前处理人实体类
 * @author lishuang
 * @date 2019/10/10
 */
@Entity
@Table(name = "server_apply_user")
@NamedQuery(name="ServerApplyUser.findAll", query="SELECT s FROM ServerApplyUser s")
public class ServerApplyUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "process_id")
    private String processId;
    @Column(name = "current_userid")
    private String currentUserid;
    @Column(name = "current_username")
    private String currentUsername;
    @Column(name = "current_orgid")
    private String currentOrgid;
    @Column(name = "current_orgname")
    private String currentOrgname;
    @Column(name = "current_node")
    private String currentNode;
    @Column(name = "node_time")
    private String nodeTime;
    @Column(name = "create_time")
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getCurrentUserid() {
        return currentUserid;
    }

    public void setCurrentUserid(String currentUserid) {
        this.currentUserid = currentUserid;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getCurrentOrgid() {
        return currentOrgid;
    }

    public void setCurrentOrgid(String currentOrgid) {
        this.currentOrgid = currentOrgid;
    }

    public String getCurrentOrgname() {
        return currentOrgname;
    }

    public void setCurrentOrgname(String currentOrgname) {
        this.currentOrgname = currentOrgname;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    public String getNodeTime() {
        return nodeTime;
    }

    public void setNodeTime(String nodeTime) {
        this.nodeTime = nodeTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ServerApplyUser{" +
                "id=" + id +
                ", processId=" + processId +
                ", currentUserid=" + currentUserid +
                ", currentUsername=" + currentUsername +
                ", currentOrgid=" + currentOrgid +
                ", currentOrgname=" + currentOrgname +
                ", currentNode=" + currentNode +
                ", nodeTime=" + nodeTime +
                ", createTime=" + createTime +
                '}';
    }
}
