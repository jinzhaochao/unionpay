package com.unionpay.voice.model;

import java.math.BigInteger;

/**
 * 基本信息分页查询，列表数据实体类
 * @author lishuang
 * @date 2019-05-08
 */
public class VoiceInfoModel {
    private String unid;
    private String title;
    private String orgName;
    private String transferTime;
    private String createTime;
    private Integer readNumber;
    private BigInteger commentNumber;

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(Integer readNumber) {
        this.readNumber = readNumber;
    }

    public BigInteger getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(BigInteger commentNumber) {
        this.commentNumber = commentNumber;
    }

    @Override
    public String toString() {
        return "VoiceInfoModel{" +
                "unid=" + unid +
                ", title=" + title +
                ", orgName=" + orgName +
                ", transferTime=" + transferTime +
                ", readNumber=" + readNumber +
                ", commentNumber=" + commentNumber +
                '}';
    }
}
