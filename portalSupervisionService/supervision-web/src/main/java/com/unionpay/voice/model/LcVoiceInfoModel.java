package com.unionpay.voice.model;

import com.alibaba.fastjson.JSONArray;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 第三方新增基本信息实体类
 * @author lishuang
 * @date 2019-05-13
 */
public class LcVoiceInfoModel {
    /**  标题 */
    @NotBlank(message = "不能为空")
    private String title;
    /**  状态 */
    @NotNull(message = "不能为空")
    private Integer status;
    /**  首页展示 */
    @NotNull(message = "不能为空")
    private Integer homeShow;
    /**  案例标签 */
    @NotNull(message = "不能为空")
    private Integer caseTag;
    /**  客户身份 */
    @NotNull(message = "不能为空")
    private Integer clientIdentity;
    /**  案例类型 */
    @NotNull(message = "不能为空")
    private Integer caseType;
    /**  来电时间 */
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "不符合yyyy-MM-dd hh:mm:ss格式")
    private String callTime;
    /**  转递时间 */
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "不符合yyyy-MM-dd hh:mm:ss格式")
    private String transferTime;
    /**  内容 */
    @NotBlank(message = "不能为空")
    private String infoContent;
    /**  处理部门 */
    @NotNull(message = "不能为空")
    private Integer handleOrgid;
    /**  反馈时间 */
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "不符合yyyy-MM-dd hh:mm:ss格式")
    private String handleTime;
    /**  处理情况 */
    @NotBlank(message = "不能为空")
    private String handleContent;
    /**  图片 */
    private String picUnids;
    /**  音频 */
    private String audioUnids;
    /**  文档 */
    private String docUnids;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHomeShow() {
        return homeShow;
    }

    public void setHomeShow(Integer homeShow) {
        this.homeShow = homeShow;
    }

    public Integer getCaseTag() {
        return caseTag;
    }

    public void setCaseTag(Integer caseTag) {
        this.caseTag = caseTag;
    }

    public Integer getClientIdentity() {
        return clientIdentity;
    }

    public void setClientIdentity(Integer clientIdentity) {
        this.clientIdentity = clientIdentity;
    }

    public Integer getCaseType() {
        return caseType;
    }

    public void setCaseType(Integer caseType) {
        this.caseType = caseType;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
    }

    public Integer getHandleOrgid() {
        return handleOrgid;
    }

    public void setHandleOrgid(Integer handleOrgid) {
        this.handleOrgid = handleOrgid;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getHandleContent() {
        return handleContent;
    }

    public void setHandleContent(String handleContent) {
        this.handleContent = handleContent;
    }

    public String getPicUnids() {
        return picUnids;
    }

    public void setPicUnids(String picUnids) {
        this.picUnids = picUnids;
    }

    public String getAudioUnids() {
        return audioUnids;
    }

    public void setAudioUnids(String audioUnids) {
        this.audioUnids = audioUnids;
    }

    public String getDocUnids() {
        return docUnids;
    }

    public void setDocUnids(String docUnids) {
        this.docUnids = docUnids;
    }

    @Override
    public String toString() {
        return "LcVoiceInfoModel{" +
                "title=" + title +
                ", status=" + status +
                ", homeShow=" + homeShow +
                ", caseTag=" + caseTag +
                ", clientIdentity=" + clientIdentity +
                ", caseType=" + caseType +
                ", callTime=" + callTime +
                ", transferTime=" + transferTime +
                ", infoContent=" + infoContent +
                ", handleOrgid=" + handleOrgid +
                ", handleTime=" + handleTime +
                ", handleContent=" + handleContent +
                ", picUnids=" + picUnids +
                ", audioUnids=" + audioUnids +
                ", docUnids=" + docUnids +
                '}';
    }
}
