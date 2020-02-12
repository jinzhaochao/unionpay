package com.unionpay.supervision.service;

import java.util.List;

import com.unionpay.supervision.model.StatisticalDto;
import com.unionpay.supervision.model.StatisticsModel;
import com.unionpay.supervision.model.StatisticsOrgNameModel;
import com.unionpay.supervision.model.StatisticsOverseeNameModel;

public interface StatisticsService {
	
	/**
	 * 督办类型统计
	*/
	public List<StatisticsModel> getOverSeeStatistics();
	
	/**
	 * 事项类型统计
	*/
	public List<StatisticsModel> getServiceTypeStatistics();
	
	/**
	 * 事项类型统计,条件查询
	*/
	public List<StatisticsModel> getServiceTypeStatistics(StatisticalDto statisticalDto);

	/**
	 * 主办单位统计
	 */
	List<StatisticsOrgNameModel> getOrgNameStatistics();

	/**
	 * 主办单位统计,条件查询
	 */
	List<StatisticsOrgNameModel> getOrgNameStatistics(StatisticalDto statisticalDto);

	/**
	 * 主办单位统计
	 */
	List<StatisticsOverseeNameModel> getOverseeNameStatistics();

	/**
	 * 主办单位统计,条件查询
	 */
	List<StatisticsOverseeNameModel> getOverseeNameStatistics(StatisticalDto statisticalDto);
}
