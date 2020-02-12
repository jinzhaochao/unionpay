package com.unionpay.pager.dao;

import com.unionpay.pager.domain.BusinessMcc;
import com.unionpay.pager.dto.BusinessMccDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 商户入网需求MCC  DAO接口
 * @author: lishuang
 * @date: 2019/12/04
 */
@Repository
public interface BusinessMccRepository extends JpaRepository<BusinessMcc,Integer>, JpaSpecificationExecutor<BusinessMcc> {
    /**
     * 获取所有MCC
     * @return
     */
    @Query(value = "SELECT id,CONCAT(`value`,'-',`name`) mcc FROM pager_business_mcc",nativeQuery = true)
    public List<BusinessMccDto> getAll();
}
