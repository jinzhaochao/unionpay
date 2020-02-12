package com.unionpay.supervision.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unionpay.supervision.domain.SuperService;

public interface SuperProjectRepository extends JpaRepository<SuperService,String>{
    @Query(value="select count(*) from  super_sponsor sp Inner JOIN  super_service se on se.unid = sp.service_unid Inner Join "
    		+ " super_type_service ty  on se.service_type = ty.unid",nativeQuery = true)
	public Integer getCount();
}
