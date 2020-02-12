package com.unionpay.supervision.dao;

import com.unionpay.supervision.domain.OmOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 * 组织机构
 */
@Repository
public interface OMOrganizationRepository extends JpaRepository<OmOrganization, Long> {

    /**
     * 查询部门
     * @param parentorgId
     * @param status
     * @return
     */
    List<OmOrganization> findByParentorgidAndStatus(Integer parentorgId,String status);

    List<OmOrganization> findByParentorgidAndStatusOrderBySortno(Integer parentorgId,String status);

    //根据部门编号查询部门信息
    OmOrganization findByOrgid(Integer orgid);
}
