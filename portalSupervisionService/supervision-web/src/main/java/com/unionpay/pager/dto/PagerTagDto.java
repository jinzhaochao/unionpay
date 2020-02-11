package com.unionpay.pager.dto;

public class PagerTagDto {

    private int tagId;
    private String tagName;
    private String tagTitle;
    private String orgName;
    private Integer status;

    public PagerTagDto() {
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PagerTagDto{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", tagTitle='" + tagTitle + '\'' +
                ", orgName='" + orgName + '\'' +
                ", status=" + status +
                '}';
    }
}
