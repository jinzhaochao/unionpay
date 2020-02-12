package com.unionpay.pager.dao;

import com.unionpay.pager.domain.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusinessTypeRepository extends JpaRepository<BusinessType,Integer>, JpaSpecificationExecutor<BusinessType> {
    /**
     * 根据parentId查询
     * @param parentId
     * @return
     */
    public List<BusinessType> getAllByParentId(Integer parentId);
}
