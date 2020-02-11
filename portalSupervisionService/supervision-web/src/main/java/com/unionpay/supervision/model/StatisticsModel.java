package com.unionpay.supervision.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.math.BigDecimal;

public class StatisticsModel extends BaseRowModel{

	// 类型
	@ExcelProperty(value = { "类型" }, index = 0)
	private String typeName;
	// 总数
	@ExcelProperty(value = { "总计" }, index = 1)
	private BigDecimal totalService;
	// 立项统计
	@ExcelProperty(value = { "已立项" }, index = 2)
	private BigDecimal buildTotal;
	// 督办统计
	@ExcelProperty(value = { "已督办" }, index = 3)
	private BigDecimal supervisionTotal;
	// 完成统计
	@ExcelProperty(value = { "已完成" }, index = 4)
	private BigDecimal finshTotal;
	// 中止统计
	@ExcelProperty(value = { "已中止" }, index = 5)
	private BigDecimal stopTotal;
	
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public BigDecimal getTotalService() {
		return totalService;
	}
	public void setTotalService(BigDecimal totalService) {
		this.totalService = totalService;
	}
	public BigDecimal getBuildTotal() {
		return buildTotal;
	}
	public void setBuildTotal(BigDecimal buildTotal) {
		this.buildTotal = buildTotal;
	}
	public BigDecimal getSupervisionTotal() {
		return supervisionTotal;
	}
	public void setSupervisionTotal(BigDecimal supervisionTotal) {
		this.supervisionTotal = supervisionTotal;
	}
	public BigDecimal getFinshTotal() {
		return finshTotal;
	}
	public void setFinshTotal(BigDecimal finshTotal) {
		this.finshTotal = finshTotal;
	}
	public BigDecimal getStopTotal() {
		return stopTotal;
	}
	public void setStopTotal(BigDecimal stopTotal) {
		this.stopTotal = stopTotal;
	}
	
	
	

}
