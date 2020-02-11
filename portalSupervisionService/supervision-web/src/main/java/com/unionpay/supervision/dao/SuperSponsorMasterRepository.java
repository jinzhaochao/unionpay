package com.unionpay.supervision.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unionpay.supervision.domain.SuperSponsorMaster;

/**
 * 督办部门关联督办人
 */
@Repository
public interface SuperSponsorMasterRepository extends JpaRepository<SuperSponsorMaster,Long>{
	
	SuperSponsorMaster findBySponsorUnidAndSponsorId(String sponsorUnid,String sponsorId);

}
