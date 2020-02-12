package com.unionpay.pager.service.impl;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.dao.BusinessMccRepository;
import com.unionpay.pager.dto.BusinessMccDto;
import com.unionpay.pager.service.BusinessMccService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import java.util.List;
@Service
public class BusinessMccServiceImpl implements BusinessMccService {
    @Autowired
    private BusinessMccRepository businessMccRepository;
    @Autowired
    private EntityManager entityManager;

    /**
     * 获取所有MCC
     * @return
     */
    public List<BusinessMccDto> getAll(){
        String sql = "SELECT id,CONCAT(`value`,'-',`name`) `name` FROM pager_business_mcc";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(BusinessMccDto.class));
        return query.getResultList();
    }
}
