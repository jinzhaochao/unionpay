package com.unionpay.pager.dao;
import com.unionpay.pager.domain.BusinessRequireRMcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 商户入网需求信息与MCC关系  DAO接口
 * @author: lishuang
 * @date: 2019/12/04
 */
@Repository
public interface BusinessRequireRMccRepository extends JpaRepository<BusinessRequireRMcc,Integer>, JpaSpecificationExecutor<BusinessRequireRMcc>{
    /**
     * 根据需求id查找所有关联关系
     * @param requireId
     * @return
     */
    @Query(value = "SELECT mcc_id from pager_business_require_r_mcc WHERE require_id =?1",nativeQuery = true)
    public List<Integer> getAllByRequireId(Integer requireId);

    /**
     * 根据需求id删除所有关联关系
     * @param requireId
     */
    public void deleteAllByRequireId(Integer requireId);
}
