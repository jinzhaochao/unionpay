package com.unionpay.supervision.service;

import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.supervision.domain.SuperService;
import com.unionpay.supervision.domain.SuperTag;
import com.unionpay.supervision.model.SuperServiceInfo;

import java.util.List;

public interface SuperServiceService {
	
    public List<SuperService> findAll();
	
	public RESTResultBean saveAll(SuperServiceInfo superServiceInfo);
	
	public void save(SuperService superService);
	
	public SuperService translateSuperService(SuperServiceInfo superServiceInfo);
	
	public SuperServiceInfo translateSuperServiceInfo(SuperService superService);
	
	public SuperService findByUnid(String unid);
	
	public RESTResultBean editSuperService(SuperServiceInfo superServiceInfo);
	
	/**
	 * 督办事项删除
	 */
	public void delete(String unid);


}
