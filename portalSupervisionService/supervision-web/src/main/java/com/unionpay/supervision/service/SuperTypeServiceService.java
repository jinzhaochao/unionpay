package com.unionpay.supervision.service;

import com.unionpay.supervision.domain.SuperTypeService;

import java.util.List;


public interface SuperTypeServiceService {
	
	List<SuperTypeService> findAll();

	SuperTypeService findByUnid(String unid);

	SuperTypeService findByTypeName(String typeName);

	SuperTypeService add(String typeName);
	
	void delete(String unid);
	
}
