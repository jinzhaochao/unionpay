package com.unionpay.pager.dto;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-19 16:21
 **/
public class PagerSelectDTO {

    //模板类型：1-应用列表(无分组)，2-应用列表(分组)，3-文章列表
    private Integer formwork;
    //模板名称
    private String formworkName;

    public Integer getFormwork() {
        return formwork;
    }

    public void setFormwork(Integer formwork) {
        this.formwork = formwork;
    }

    public String getFormworkName() {
        return formworkName;
    }

    public void setFormworkName(String formworkName) {
        this.formworkName = formworkName;
    }
}
