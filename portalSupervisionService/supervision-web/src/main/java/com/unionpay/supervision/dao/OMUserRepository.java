package com.unionpay.supervision.dao;

import com.unionpay.supervision.domain.OmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户信息
 */
@Repository
public interface OMUserRepository extends JpaRepository<OmUser, Long> {
	OmUser findByUserid(@Param("userid") String userid);
    List<OmUser> findByEmpstatus(String empstatus);
    OmUser findByEmpid(Integer empid);

    @Query(value = "select group_concat(om.USERID) as userId from om_user om where om.DEPTORGID = ?1",nativeQuery = true)
    String findByDeptorgid(String deptorgId);
}
