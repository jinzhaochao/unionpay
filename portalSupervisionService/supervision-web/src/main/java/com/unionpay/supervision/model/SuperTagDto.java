package com.unionpay.supervision.model;

/**
 * jinczhao
 * 2019-11-19
 * 修改标签返回实体类
 */
public class SuperTagDto {
    private String unid;
    private Integer tagId;
    private String tagContent;

    public SuperTagDto() {
    }

    public SuperTagDto(String unid, Integer tagId, String tagContent) {
        this.unid = unid;
        this.tagId = tagId;
        this.tagContent = tagContent;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }
}
