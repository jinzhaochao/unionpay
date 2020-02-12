package com.unionpay.supervision.serviceImpl;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperSponsorTagRepository;
import com.unionpay.supervision.domain.SuperSponsorTag;
import com.unionpay.supervision.service.SupersponsorTagService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * jinzhao
 * 2019-11-20
 * 标签中间表实现类
 */
@Service
@Transactional
public class SupersponsorTagServiceImpl implements SupersponsorTagService {

    @Autowired
    private SuperSponsorTagRepository superSponsorTagRepository;
    @Autowired
    private EntityManager em;

    @Override
    public void delTag(String unid) {
        String sql = " select st.id,st.tag_id as tagId,st.sponsor_unid as sponsorUnid,DATE_FORMAT(st.create_time,'%Y-%m-%d %H:%i:%S') as createTime from super_sponsor_tag st where 1=1 ";
        if (ToolUtil.isNotEmpty(unid)){
            sql += " and st.sponsor_unid = '"+unid+"' ";
        }
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperSponsorTag.class));
        List<SuperSponsorTag> superSponsorTagList = query.getResultList();
        for (SuperSponsorTag superSponsorTag : superSponsorTagList) {
            superSponsorTagRepository.delete(superSponsorTag);

        }
    }

    @Override
    public void add(SuperSponsorTag superSponsorTag) {
        superSponsorTagRepository.save(superSponsorTag);
    }

    @Override
    public SuperSponsorTag findByTagIdAndSponsorUnid(Integer tagId, String unid) {
        String sql = " select st.id,st.tag_id as tagId,st.sponsor_unid as sponsorUnid,DATE_FORMAT(st.create_time,'%Y-%m-%d %H:%i:%S') as createTime from super_sponsor_tag st where st.tag_id = :tagId ";
        if (ToolUtil.isNotEmpty(unid)){
            sql +=  " and st.sponsor_unid = '"+unid+"' ";
        }
        Query query = em.createNativeQuery(sql);
        if (ToolUtil.isNotEmpty(tagId)){
            query.setParameter("tagId",tagId);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperSponsorTag.class));
        SuperSponsorTag superSponsorTag = (SuperSponsorTag) query.getSingleResult();
        return superSponsorTag;
    }
}
