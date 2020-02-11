package com.unionpay.pager.service;

import com.unionpay.pager.domain.PagerApplication;
import com.unionpay.pager.dto.ApplicationInfoDTO;

import java.util.List;

import com.unionpay.pager.dto.PagerAppDto;
import com.unionpay.pager.dto.TagApplicationInfoDTO;


public interface PagerApplicationService {

    //查找某个页签下的所有应用
    List<TagApplicationInfoDTO> applicationInfos(Integer tagId, Integer status);

    List<PagerApplication> selectApp(Integer status, String applicationName, Integer tagId, Integer currentPage, Integer size);

    Integer selectAppTotal(Integer status, String applicationName, Integer tagId);

    void deleteApplication(List<PagerApplication> pagerApplicationList);

    Integer getCount(Integer status, String applicationName, Integer tagId, Integer page, Integer size);
}
