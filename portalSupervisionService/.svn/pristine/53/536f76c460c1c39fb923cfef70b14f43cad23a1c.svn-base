package com.unionpay.voice.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "voice_comment")
@NamedQuery(name="VoiceComment.findAll", query="SELECT voice FROM VoiceComment voice")
public class VoiceComment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String unid;
    @Column(name = "info_unid")
    private String infoUnid;
    @Column(name = "comment_content")
    private String commentContent;
    @Column(name = "show_name")
    private String showName;
    @Column(name = "comment_userid")
    private String commentUserid;
    @Column(name = "comment_empname")
    private String commentEmpname;
    @Column(name = "create_time")
    private String createTime;
    private Integer incognito;
    @Column(name = "comment_status")
    private Integer commentStatus;
    @Column(name = "approver_userid")
    private String approverUserid;
    @Column(name = "approver_empname")
    private String approverEmpname;
    @Column(name = "approver_time")
    private String approverTime;
    private String note;


    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getInfoUnid() {
        return infoUnid;
    }

    public void setInfoUnid(String infoUnid) {
        this.infoUnid = infoUnid;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getCommentUserid() {
        return commentUserid;
    }

    public void setCommentUserid(String commentUserid) {
        this.commentUserid = commentUserid;
    }

    public String getCommentEmpname() {
        return commentEmpname;
    }

    public void setCommentEmpname(String commentEmpname) {
        this.commentEmpname = commentEmpname;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getIncognito() {
        return incognito;
    }

    public void setIncognito(Integer incognito) {
        this.incognito = incognito;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getApproverUserid() {
        return approverUserid;
    }

    public void setApproverUserid(String approverUserid) {
        this.approverUserid = approverUserid;
    }

    public String getApproverEmpname() {
        return approverEmpname;
    }

    public void setApproverEmpname(String approverEmpname) {
        this.approverEmpname = approverEmpname;
    }

    public String getApproverTime() {
        return approverTime;
    }

    public void setApproverTime(String approverTime) {
        this.approverTime = approverTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "VoiceComment{" +
                "unid=" + unid +
                ", infoUnid=" + infoUnid +
                ", commentContent=" + commentContent +
                ", showName=" + showName +
                ", commentUserid=" + commentUserid +
                ", commentEmpname=" + commentEmpname +
                ", createTime=" + createTime +
                ", incognito=" + incognito +
                ", commentStatus=" + commentStatus +
                ", approverUserid=" + approverUserid +
                ", approverEmpname='" + approverEmpname +
                ", approverTime='" + approverTime +
                ", note=" + note +
                '}';
    }
}
