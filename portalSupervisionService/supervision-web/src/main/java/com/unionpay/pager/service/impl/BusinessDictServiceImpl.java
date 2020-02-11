package com.unionpay.pager.service.impl;

import com.unionpay.pager.dao.BusinessDictRepository;
import com.unionpay.pager.domain.BusinessDict;
import com.unionpay.pager.service.BusinessDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessDictServiceImpl implements BusinessDictService {
    @Autowired
    private BusinessDictRepository dictRepository;

    /**
     * 查询价格字典信息
     * @return
     */
    public List<BusinessDict> getPriceType(){
        return dictRepository.getAllByDictType("price_type");
    }
}
