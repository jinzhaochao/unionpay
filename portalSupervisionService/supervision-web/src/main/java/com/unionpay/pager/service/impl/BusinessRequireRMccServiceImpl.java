package com.unionpay.pager.service.impl;

import com.unionpay.pager.dao.BusinessRequireRMccRepository;
import com.unionpay.pager.domain.BusinessRequireRMcc;
import com.unionpay.pager.service.BusinessRequireRMccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessRequireRMccServiceImpl implements BusinessRequireRMccService {
    @Autowired
    private BusinessRequireRMccRepository requireRMccRepository;

    /**
     * 新增关联关系
     * @param list
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public List<BusinessRequireRMcc> add(List<BusinessRequireRMcc> list){
        return requireRMccRepository.saveAll(list);
    }

    /**
     * 根据需求id删除所有关联关系
     * @param requireId
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void delete(Integer requireId){
        requireRMccRepository.getAllByRequireId(requireId);
    }
}
