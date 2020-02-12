package com.unionpay.pager.service;

import com.unionpay.pager.dto.PagerManagerModel;
import com.unionpay.services.model.ServerSuggestModel;

import java.util.List;

public interface PagerManagerService {

    /**
     * 页面信息查询与分页
     * @param page
     * @param size
     * @param pageName
     * @return
     */
    List<PagerManagerModel> SelectPagerManager(Integer page, Integer size, String pageName);

    //页面信息查询与分页获取总条数
    Integer getCount(Integer page, Integer size, String pageName);
}
