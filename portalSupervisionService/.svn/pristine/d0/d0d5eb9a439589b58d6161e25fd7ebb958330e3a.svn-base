package com.unionpay.pager.dto;

import java.util.List;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-26 17:02
 **/
public class ApplicationInfoDTO {

    //页签id
    private Integer tagId;
    //页签名称
    private String tagName;
    //页签标题
    private String tagTitle;
    //模板类型：1-应用列表(无分组)，2-应用列表(分组)，3-文章列表
    private Integer formwork;
    //wcm接口所需字段   父栏目id(无用,按channelId的父级栏目id传)
    private  Integer chnlId;
    //栏目id,文章类型的页签必选
    private  Integer columnId;
    //栏目名称
    private String columnName;
    //wcm接口所需字段    跳转路径(无用,按默认值传)
    private String hrefurl;
    //是否聚合：0-非聚合，1-聚合
    private  Integer isTogether;

    //应用
    List<TagApplicationInfoDTO> Applications;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
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

    public Integer getFormwork() {
        return formwork;
    }

    public void setFormwork(Integer formwork) {
        this.formwork = formwork;
    }

    public List<TagApplicationInfoDTO> getApplications() {
        return Applications;
    }

    public void setApplications(List<TagApplicationInfoDTO> applications) {
        Applications = applications;
    }

    public Integer getChnlId() {
        return chnlId;
    }

    public void setChnlId(Integer chnlId) {
        this.chnlId = chnlId;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getHrefurl() {
        return hrefurl;
    }

    public void setHrefurl(String hrefurl) {
        this.hrefurl = hrefurl;
    }

    public Integer getIsTogether() {
        return isTogether;
    }

    public void setIsTogether(Integer isTogether) {
        this.isTogether = isTogether;
    }
}
