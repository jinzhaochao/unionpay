package com.unionpay.voice.model;

/**
 * 分页查询条件实体类
 * @author lishuang
 * @date 2019-05-08
 */
public class SearchContionModel {
    /**  案例标签 */
    private String caseTag;
    /**  案例类型 */
    private String caseType;
    /**  开始时间 */
    private String beginTime;
    /**  结束时间 */
    private String endTime;
    /**  处理部门 */
    private Integer orgid;
    /**  关键词 */
    private String keyWord;
    /**  状态 */
    private Integer status;
    /**  首页展示 */
    private Integer homeShow;
    /**  客户身份 */
    private Integer clientIdentity;
    /**  转递时间 */
    private Integer createTime;
    /**  阅读量 */
    private Integer readNumber;
    /**  评论量 */
    private Integer commentNumber;
    /**  是否首页 */
    private Boolean frontPage;

    public String getCaseTag() {
        return caseTag;
    }

    public void setCaseTag(String caseTag) {
        this.caseTag = caseTag;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getOrgid() {
        return orgid;
    }

    public void setOrgid(Integer orgid) {
        this.orgid = orgid;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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

    public Integer getClientIdentity() {
        return clientIdentity;
    }

    public void setClientIdentity(Integer clientIdentity) {
        this.clientIdentity = clientIdentity;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(Integer readNumber) {
        this.readNumber = readNumber;
    }

    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Boolean getFrontPage() {
        return frontPage;
    }

    public void setFrontPage(Boolean frontPage) {
        this.frontPage = frontPage;
    }
}
