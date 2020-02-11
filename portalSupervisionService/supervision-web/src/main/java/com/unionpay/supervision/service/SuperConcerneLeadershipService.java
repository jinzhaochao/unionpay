package com.unionpay.supervision.service;

import java.util.List;

import com.unionpay.supervision.domain.SuperConcerneLeadership;

public interface SuperConcerneLeadershipService {

	/**
	 * 根据主办单位查询关注领导
	*/
	public List<SuperConcerneLeadership> findBySponsorUnid(String sponsorUnid);

	/**
	 * 根据关注领导查询
	*/
	public List<SuperConcerneLeadership> findByleaderUserid(String leaderUserid);
	
	/**
	 * 新增
	*/
	public void insertOrUpdata(SuperConcerneLeadership superConcerneLeadership);
	

}
