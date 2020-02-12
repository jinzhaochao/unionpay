package com.unionpay.pager.service;

import com.unionpay.pager.domain.PagerManagerTag;

import java.util.List;

public interface PagerManagerTagService {

    /*
    查询页面信息关联的页签
     */
    List<PagerManagerTag> selectPagerManagerTag(int pageId);

    //修改页面与页签关联表有效改为无效
    PagerManagerTag update(PagerManagerTag pagerManagerTag);
}
