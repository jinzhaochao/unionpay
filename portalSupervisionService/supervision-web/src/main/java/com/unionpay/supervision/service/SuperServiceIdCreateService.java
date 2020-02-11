package com.unionpay.supervision.service;

public interface SuperServiceIdCreateService {
	
	/**
	 * 生成SuperServiceId
	*/
	String geSuperServiceId(String overseeTypeId,Integer year);
	
	/**
	 * 生成SuperServiceId
	*/
	String geSuperServiceId(String uind);

}
