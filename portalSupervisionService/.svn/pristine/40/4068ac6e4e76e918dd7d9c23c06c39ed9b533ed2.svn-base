package com.unionpay.support.serviceImpl;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.support.dao.SupportQuestionRepository;
import com.unionpay.support.dao.SysDictEntryRepository;
import com.unionpay.support.pojo.SysDictEntry;
import com.unionpay.support.service.SupportQuestionService;
import com.unionpay.support.service.SysDictEntryService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.table.TableRowSorter;
import javax.xml.transform.Transformer;
import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/11/04 16:59
 * @Description:
 */
@Service
public class SysDictEntryServiceImpl implements SysDictEntryService{

    @Autowired
    private SysDictEntryRepository sysDictEntryRepository;
    @Autowired
    private EntityManager em;

    @Override
    public List<SysDictEntry> select(String str) {
        String sql = " select sd.dictId,sd.dictName,sd.sortNo from sys_dict_entry sd where 1=1 ";
        if (ToolUtil.isNotEmpty(str)){
            sql += " and sd.DICTTYPEID = '"+str+"' ";
        }
        sql += " order by sd.SORTNO ";
        Query query = em.createNativeQuery(sql);

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SysDictEntry.class));

        return query.getResultList();
    }
}
