package com.unionpay.services.service;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.HostWordsModel;
import com.unionpay.services.model.ServerHostWords;
import com.unionpay.services.model.SeverHost;
import com.unionpay.services.repository.ServerHostWordsRepository;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/20/020 18:13
 * @Description:
 */
@Service
public class ServerHostWordsServiceImpl implements ServerHostWordsService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ServerHostWordsRepository serverHostWordsRepository;

    @Override
    public List<SeverHost> SelectAllHostWords(Integer page, Integer size, String words, Integer status) {
        String jpql = "SELECT a.text, a.cC as cC, IF(shw.id is null ,0,1) as isHot  FROM \n" +
                "(SELECT ssr.text,COUNT(ssr.text) cC from server_search_recode ssr  \n" +
                "GROUP BY ssr.text ) a \n" +
                "LEFT JOIN server_host_words shw on shw.words = a.text \n" +
                "\tWHERE 1=1 ";
        if (ToolUtil.isNotEmpty(status)) {
            jpql += " AND IF(shw.id is null ,0,1) = :status ";
        }
        if (ToolUtil.isNotEmpty(words)) {
            jpql += " and a.text like CONCAT('%',:words,'%') ";
        }
        jpql += " order by IF(shw.id is null ,0,1) = 0 ,a.cC desc,a.text ";
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ToolUtil.isNotEmpty(words)) {
            query.setParameter("words", words);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SeverHost.class));
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<SeverHost> severHosts = query.getResultList();
        return severHosts;
    }

    @Override
    public Integer getCount(Integer page, Integer size, String words, Integer status) {
        int count = 0;
        String jpql = "SELECT a.text, a.cC as cC, IF(shw.id is null ,0,1) as isHot  FROM \n" +
                "(SELECT ssr.text,COUNT(ssr.text) cC from server_search_recode ssr  \n" +
                "GROUP BY ssr.text ) a \n" +
                "LEFT JOIN server_host_words shw on shw.words = a.text \n" +
                "\tWHERE 1=1 ";
        if (ToolUtil.isNotEmpty(status)) {
            jpql += " AND IF(shw.id is null ,0,1) = :status ";
        }
        if (ToolUtil.isNotEmpty(words)) {
            jpql += " and a.text like CONCAT('%',:words,'%') ";
        }
        jpql += " order by a.cC desc";
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ToolUtil.isNotEmpty(words)) {
            query.setParameter("words", words);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SeverHost.class));
        List<SeverHost> severHosts = query.getResultList();
        if (ToolUtil.isNotEmpty(severHosts)){
            count = severHosts.size();
        }
        return count;
    }

    @Override
    @Transactional
    public void deleteByWords(String words) {
        String sql = null;
        if (ToolUtil.isNotEmpty(words)) {
            sql = "delete from server_host_words where words = :words";
            Query query = em.createNativeQuery(sql);
            query.setParameter("words", words);
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerHostWords.class));
            query.executeUpdate();
        }
    }

    @Override
    public List selectHostWords() {
        String sql = "select words from server_host_words where 1=1 order by frequency desc";
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerHostWords.class));
        return query.getResultList();
    }

    @Override
    public ServerHostWords findByText(String words) {
        String sql = "select * from server_host_words where words = :words";
        Query query = em.createNativeQuery(sql);
        query.setParameter("words", words);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerHostWords.class));
        ServerHostWords serverHostWords = (ServerHostWords) query.getSingleResult();
        return serverHostWords;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public ServerHostWords add(ServerHostWords serverHostWords) {
        return serverHostWordsRepository.save(serverHostWords);
    }

    @Override
    public List<HostWordsModel> SelectHostWords( String words, Integer status) {

        String jpql = "SELECT a.text as words, a.cC as isHot, (case (IF(shw.id is null ,0,1)) when 0 then '非热词' when 1 then '热词' end) as status  FROM (SELECT ssr.text,COUNT(ssr.text) cC from server_search_recode ssr GROUP BY ssr.text ) a LEFT JOIN server_host_words shw on shw.words = a.text WHERE 1=1 ";
        if (ToolUtil.isNotEmpty(status)) {
            jpql += " AND IF(shw.id is null ,0,1) = :status ";
        }
        if (ToolUtil.isNotEmpty(words)) {
            jpql += " and a.text like CONCAT('%',:words,'%') ";
        }
        jpql += " order by IF(shw.id is null ,0,1) = 0 ,a.cC desc,a.text ";
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ToolUtil.isNotEmpty(words)) {
            query.setParameter("words", words);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(HostWordsModel.class));

        List<HostWordsModel> list = query.getResultList();
        for (HostWordsModel hostWordsModel : list) {
            if (ToolUtil.isNotEmpty(hostWordsModel.getIsHot())){
                Integer frequency = Integer.parseInt(hostWordsModel.getIsHot().toString());
                hostWordsModel.setFrequency(frequency);
            }
        }
        return list;
    }


}
