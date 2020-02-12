package com.unionpay.supervision.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unionpay.supervision.domain.SuperServiceIdCreate;

/**
 * @author xiongym
 * 事项编码最新序号
 */
@Repository
public interface SuperServiceIdCreateRepository extends JpaRepository<SuperServiceIdCreate, String>{
	
	 SuperServiceIdCreate findByOverseeTypeIdAndYear(String overseeTypeId, Integer year);

}