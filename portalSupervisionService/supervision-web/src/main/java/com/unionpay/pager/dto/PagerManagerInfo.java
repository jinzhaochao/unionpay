package com.unionpay.pager.dto;

import com.unionpay.pager.domain.PagerTag;

import java.util.List;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-18 18:44
 **/
public class PagerManagerInfo {

    private int pageId;
    //页面名称
    private String pageName;
    //主办部门名称
    private String orgName;
    //主办部门编码
    private int orgId;
    //0-禁用，1-启用   2-删除
    private int status;
    //页签信息
    List<PagerManagerTagInfo> pagerTags;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PagerManagerTagInfo> getPagerTags() {
        return pagerTags;
    }

    public void setPagerTags(List<PagerManagerTagInfo> pagerTags) {
        this.pagerTags = pagerTags;
    }
}
