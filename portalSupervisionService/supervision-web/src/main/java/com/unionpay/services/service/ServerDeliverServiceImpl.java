package com.unionpay.services.service;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.ServerDeliver;
import com.unionpay.services.repository.ServerDeliverRepository;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * jinzhao  2019-12-18
 */

@Service
@Transactional
public class ServerDeliverServiceImpl implements ServerDeliverService{

    @Autowired
    private ServerDeliverRepository serverDeliverRepository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ServerDeliver> findBySuggestId(Integer id) {
        String sql = " select sd.id,sd.suggest_id as suggestId,sd.deliver_userid as deliverUserid,sd.deliver_username as deliverUsername,DATE_FORMAT(sd.deliver_time ,'%Y-%m-%d %H:%i:%S') as deliverTime,sd.reply_userid as replyUserid,sd.reply_username as replyUsername,sd.deliver_content as deliverContent from server_deliver sd where 1=1 ";
        if (ToolUtil.isNotEmpty(id)){
            sql += " and sd.suggest_id = '"+id+"'  ";
        }
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerDeliver.class));

        return query.getResultList();
    }
}
