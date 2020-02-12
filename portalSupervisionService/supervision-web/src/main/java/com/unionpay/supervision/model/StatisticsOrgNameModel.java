package com.unionpay.supervision.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.math.BigDecimal;

/**
 * jinzhao  2019-12-11
 * 督办事项完成情况表(主办单位)统计实体类
 */

public class StatisticsOrgNameModel extends BaseRowModel{

	// 主办单位
	@ExcelProperty(value = { "主办单位" }, index = 0)
	private String orgName;
	// 办结率统计
	@ExcelProperty(value = { "办结率" }, index = 1)
	private String closingRate;
	// 总数
	@ExcelProperty(value = { "总计" }, index = 2)
	private BigDecimal totalService;
	// 完成统计
	@ExcelProperty(value = { "已完成" }, index = 3)
	private BigDecimal finshTotal;
	// 推进中统计
	@ExcelProperty(value = { "推进中" }, index = 4)
	private BigDecimal buildingTotal;
	// 中止统计
	@ExcelProperty(value = { "工作中止" }, index = 5)
	private BigDecimal stopTotal;


	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getClosingRate() {
		return closingRate;
	}

	public void setClosingRate(String closingRate) {
		this.closingRate = closingRate;
	}

	public BigDecimal getTotalService() {
		return totalService;
	}

	public void setTotalService(BigDecimal totalService) {
		this.totalService = totalService;
	}

	public BigDecimal getFinshTotal() {
		return finshTotal;
	}

	public void setFinshTotal(BigDecimal finshTotal) {
		this.finshTotal = finshTotal;
	}

	public BigDecimal getBuildingTotal() {
		return buildingTotal;
	}

	public void setBuildingTotal(BigDecimal buildingTotal) {
		this.buildingTotal = buildingTotal;
	}

	public BigDecimal getStopTotal() {
		return stopTotal;
	}

	public void setStopTotal(BigDecimal stopTotal) {
		this.stopTotal = stopTotal;
	}
}
