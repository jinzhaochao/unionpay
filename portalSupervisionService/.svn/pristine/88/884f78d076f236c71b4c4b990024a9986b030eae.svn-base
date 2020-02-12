package com.unionpay.pager.service.impl;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.dto.PagerManagerModel;
import com.unionpay.pager.service.PagerManagerService;
import com.unionpay.services.model.ServerSuggestModel;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-18 13:46
 **/
@Service
public class PagerManagerServiceImpl implements PagerManagerService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PagerManagerModel> SelectPagerManager(Integer page, Integer size, String pageName) {
        String jpql = "SELECT pm.page_id as pageId,pm.page_name as pageName, pm.org_name as orgName, pm.org_id as orgId, pm.`status` as status, pm.url as url  " +
                " FROM pager_manager pm where pm.status != 2 ";
        if (ToolUtil.isNotEmpty(pageName)) {
            jpql += " and  pm.page_name like CONCAT('%',:pageName,'%') ";
        }
        jpql += " Order By pm.create_time Desc";
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(pageName)){
            query.setParameter("pageName",pageName);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PagerManagerModel.class));
//        List<PagerManagerModel> pagerManagerModels = query.getResultList();
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<PagerManagerModel> pagerManagerModelList = query.getResultList();
        return pagerManagerModelList;
    }

    @Override
    public Integer getCount(Integer page, Integer size, String pageName) {
        int count = 0;
        String jpql = "SELECT pm.page_id as pageId,pm.page_name as pageName, pm.org_name as orgName, pm.`status` as status, pm.url as url  " +
                " FROM pager_manager pm where pm.status != 2 ";
        if (ToolUtil.isNotEmpty(pageName)) {
            jpql += " and pm.page_name = :pageName";
        }
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(pageName)){
            query.setParameter("pageName",pageName);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PagerManagerModel.class));
        List<PagerManagerModel> pagerManagerModels = query.getResultList();
        if (ToolUtil.isNotEmpty(pagerManagerModels)) {
            count = pagerManagerModels.size();
        }
        return count;
    }
}
