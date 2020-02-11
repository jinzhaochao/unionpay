package com.unionpay.pager.service.impl;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.dao.PagerManagerTagRepository;
import com.unionpay.pager.domain.PagerManagerTag;
import com.unionpay.pager.service.PagerManagerTagService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class PagerManagerTagServiceImpl implements PagerManagerTagService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PagerManagerTagRepository pagerManagerTagRepository;


    @Override
    public PagerManagerTag update(PagerManagerTag pagerManagerTag){
        return pagerManagerTagRepository.saveAndFlush(pagerManagerTag);
    }

    @Override
    public List<PagerManagerTag> selectPagerManagerTag(int pageId) {
        String jpql = "SELECT pmt.id as id ,pmt.sort as  sort, pmt.tag_id as tagId FROM pager_manager_tag pmt WHERE  pmt.`status` = 1 ";
        if (ToolUtil.isNotEmpty(pageId)) {
            jpql += " and pmt.page_id = :pageId";
        }
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(pageId)){
            query.setParameter("pageId",pageId);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PagerManagerTag.class));
        List<PagerManagerTag> pagerManagerTags = query.getResultList();
        return pagerManagerTags;
    }

}
