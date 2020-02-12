package com.unionpay.supervision.serviceImpl;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperTagRepository;
import com.unionpay.supervision.domain.SuperTag;
import com.unionpay.supervision.service.SuperTagService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.table.TableRowSorter;
import javax.xml.transform.Transformer;
import java.util.List;

/**
 *
 * @author jinzhao  2019-11-19
 * @Service
 *
 */
@Service
@Transactional
public class SuperTagServiceImpl implements SuperTagService{

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private SuperTagRepository superTagRepository;

    @Override
    public SuperTag findByTagContent(String s) {
        String sql = " select t.tag_id as tagId,t.tag_content as tagContent from super_tag t where 1=1 ";
        if (ToolUtil.isNotEmpty(s)){
            sql += " and t.tag_content = '"+s+"' ";
        }
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperTag.class));
        SuperTag superTag = (SuperTag) query.getSingleResult();
        return superTag;
    }

    @Override
    public List<SuperTag> findAll() {
        List<SuperTag> list = superTagRepository.findAll();
        return list;
    }

    @Override
    public void add(SuperTag superTag) {
        superTagRepository.save(superTag);
    }
}
