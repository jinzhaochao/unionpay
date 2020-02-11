package com.unionpay.pager.service;

import com.alibaba.fastjson.JSONArray;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.pager.domain.PagerTag;
import com.unionpay.pager.dto.addTagRequestDto;

import java.util.List;


import com.unionpay.pager.dto.DropdownPagerTag;

public interface PagerTagService {

    //页面界面---页签下拉框信息展示
    List<DropdownPagerTag> dropdownBoxPagerTag(Integer orgId);

    //查询页签信息
    PagerTag getPagerTag(Integer tagId);

    //根据页签id与模板类型查找页签信息
    PagerTag getPagerTagByIdAndFormwork(Integer tagId, Integer formwork);


    //查询页签列表
    List<PagerTag> selectPagerTags(String tagName, Integer currentPage, Integer size);

    //查询页签总数
    Integer findPagerTagsTotle(String tagName);

    //查询页签下是否有关联关系
    RESTResultBean checkRelation(Integer tagId);

    //根据id查询有效页签
    List<PagerTag> seleTagById(Integer tagId);

    //新增页签(类型为文章列表)
    PagerTag addPagerTagArticle(addTagRequestDto addTagRequestDto);



}
