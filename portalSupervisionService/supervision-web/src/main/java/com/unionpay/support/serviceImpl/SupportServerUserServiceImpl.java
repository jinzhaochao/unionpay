package com.unionpay.support.serviceImpl;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.support.dao.SupportServerUserRepository;
import com.unionpay.support.pojo.SupportServerUser;
import com.unionpay.support.service.SupportServerUserService;
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
 * @Date: 2019/10/25 09:31
 * @Description:
 */
@Service
@Transactional
public class SupportServerUserServiceImpl implements SupportServerUserService {

    @Autowired
    private SupportServerUserRepository supportServerUserRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<SupportServerUser> selectServerUserAll(Integer page, Integer size, Integer status, String serverName) {
        String sql = " select s.id,s.server_name as serverName,DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') as createTime,s.office_phone as officePhone,s.pemail,s.mobileno,s.status from support_server_user s where 1=1 ";
        if (ToolUtil.isNotEmpty(status)){
            sql += " and s.status = '"+status+"' ";
        }
        if (ToolUtil.isNotEmpty(serverName)){
            sql += " and s.server_name like '%"+serverName+"%' ";
        }
        sql += " order by s.id ";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SupportServerUser.class));
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<SupportServerUser> list = query.getResultList();
        return list;
    }

    @Override
    public Integer getTotal(Integer status, String serverName) {
        String sql = " select s.id,s.server_name as serverName,DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') as createTime,s.office_phone as officePhone,s.pemail,s.mobileno,s.status from support_server_user s where 1=1 ";
        if (ToolUtil.isNotEmpty(status)){
            sql += " and s.status = '"+status+"' ";
        }
        if (ToolUtil.isNotEmpty(serverName)){
            sql += " and s.server_name like '%"+serverName+"%' ";
        }
        sql += " order by s.id ";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SupportServerUser.class));
        return query.getResultList().size();
    }

    @Override
    public SupportServerUser select(Integer id) {
        String sql = " select s.id,s.server_name as serverName,DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') as createTime,s.office_phone as officePhone,s.pemail,s.mobileno,s.status from support_server_user s where s.id = :id ";
        Query query = entityManager.createNativeQuery(sql);
        if (ToolUtil.isNotEmpty(id)){
            query.setParameter("id",id);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SupportServerUser.class));
        return (SupportServerUser)query.getSingleResult();
    }

    @Override
    public void save(SupportServerUser supportServerUser) {
        supportServerUserRepository.save(supportServerUser);
    }

    @Override
    public SupportServerUser selectByServerName(String serverName) {
        SupportServerUser supportServerUser = supportServerUserRepository.findByServerName(serverName);
        return supportServerUser;
    }

    @Override
    public List<SupportServerUser> selectServerUser() {
        String sql = "select s.id,s.server_name as serverName,DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') as createTime,s.office_phone as officePhone,s.pemail,s.mobileno,s.status from support_server_user s where s.status = '1'";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SupportServerUser.class));

        return query.getResultList();
    }
}
