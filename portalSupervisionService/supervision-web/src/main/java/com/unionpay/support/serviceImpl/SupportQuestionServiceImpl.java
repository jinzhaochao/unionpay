package com.unionpay.support.serviceImpl;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.support.dao.SupportQuestionRepository;
import com.unionpay.support.pojo.SupportQuestion;
import com.unionpay.support.service.SupportQuestionService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/11/04 16:59
 * @Description:
 */
@Service
@Transactional
public class SupportQuestionServiceImpl implements SupportQuestionService{

    @Autowired
    private SupportQuestionRepository supportQuestionRepository;
    @Autowired
    private EntityManager em;


    @Override
    public List<SupportQuestion> select(String id) {
//        List<SupportQuestion> list = supportQuestionRepository.findByTypeId(id);
        String sql = "select sq.id,cast(sq.type_id as char) as typeId,sq.type_name as typeName,sq.question_name as questionName,sq.content,sq.url from support_question sq where 1=1 ";
        if (ToolUtil.isNotEmpty(id)){
            sql += " and sq.type_id = "+id+" ";
        }
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SupportQuestion.class));
        return query.getResultList();
    }
}
