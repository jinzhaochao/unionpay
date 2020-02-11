package com.unionpay.pager.dao;

import com.unionpay.pager.domain.BusinessDict;
import com.unionpay.pager.domain.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 商户入网需求字典  DAO接口
 * @author: lishuang
 * @date: 2019/12/04
 */
@Repository
public interface BusinessDictRepository extends JpaRepository<BusinessDict,Integer>, JpaSpecificationExecutor<BusinessDict> {
    /**
     * 根据字典类型，查询字典信息
     * @param dictType
     * @return
     */
    public List<BusinessDict> getAllByDictType(String dictType);

    /**
     * 根据name查询
     * @param name
     * @return
     */
    public BusinessDict getByName(String name);
}
