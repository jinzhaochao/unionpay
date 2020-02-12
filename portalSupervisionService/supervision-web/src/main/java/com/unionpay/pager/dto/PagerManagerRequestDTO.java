package com.unionpay.pager.dto;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-18 14:50
 **/
public class PagerManagerRequestDTO {

    private Integer page;
    private Integer size;
    //页面名称
    private String pageName;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
