package com.unionpay.pager.dto;

import javax.persistence.Column;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-19 09:59
 **/
public class DropdownPagerTag {

    //页签主键
    private int tagId;
    //页签名称
    private String tagName;
    //页签标题
    private String tagTitle;

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

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }
}
