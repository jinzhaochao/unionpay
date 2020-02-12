package com.unionpay.supervision.dao;

import com.unionpay.supervision.domain.SuperLcSponsor;
import com.unionpay.supervision.domain.SuperLcSponsorAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SuperLcSponsorRepository extends JpaRepository<SuperLcSponsor,Integer> {

    @Query(value = "select sls.id,sls.userid as userId, sls.empname as empName from super_lc_sponsor sls LEFT JOIN om_user u on sls.userid = u.USERID LEFT JOIN om_organization o on u.ORGID = o.ORGID ORDER BY o.SORTNO",nativeQuery = true)
    List<SuperLcSponsor> find();

//    @Query(value = " select sa.agentUserid as agentUserId,sa.agentUserName from super_lc_sponsor sl left join super_sponsor sp on (sp.org_id = sl.userid and sp.org_name = sl.empname) left join super_lc_sponsor_agent sa on (sl.userid = sa.userid and sl.empname = sa.empname) where sp.org_id = ?1 and sp.org_name = ?2 group BY sa.id ",nativeQuery = true)
//    SuperLcSponsorAgent findByUseridAndEmpname(String orgId, String orgName);

}
