package com.unionpay.sms.dao;

import com.unionpay.sms.domain.OmUserAddressbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户信息
 */
@Repository
public interface OmUserAddressbookRepository extends JpaRepository<OmUserAddressbook, Long> {
    List<OmUserAddressbook> findByEmpstatus(String empstatus);
    OmUserAddressbook findByEmpid(Integer empid);


    /**
     * 短信发送-->申请发送-->根据部门orgid查找所有员工信息
     * @param orgid
     * @return
     *
     * @author lishuang
     * @date 2018-12-27
     */
    List<OmUserAddressbook> findByOrgid(Integer orgid);
}
