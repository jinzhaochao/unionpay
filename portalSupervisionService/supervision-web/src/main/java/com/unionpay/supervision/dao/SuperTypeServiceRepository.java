package com.unionpay.supervision.dao;

import java.util.List;

import com.unionpay.supervision.domain.SuperTypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author xiongym
 * 事项类型表
 */
@Repository
public interface SuperTypeServiceRepository extends JpaRepository<SuperTypeService,String>{

	SuperTypeService findByUnid(String unid);

	SuperTypeService findByTypeName(String typeName);
	
	List<SuperTypeService> OrderByTypeSortDesc();
}
