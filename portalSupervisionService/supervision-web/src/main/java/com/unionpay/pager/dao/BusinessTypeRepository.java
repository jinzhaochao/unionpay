package com.unionpay.pager.dao;

import com.unionpay.pager.domain.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessType,Integer>, JpaSpecificationExecutor<BusinessType> {
    /**
     * 根据parentId查询
     * @param parentId
     * @return
     */
    public List<BusinessType> getAllByParentId(Integer parentId);

    /**
     * 根据parentId,name查询
     * @param parentId
     * @param name
     * @return
     */
    public BusinessType getByParentIdAndName(Integer parentId,String name);
}
