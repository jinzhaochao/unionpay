package com.unionpay.common.resultBean;


public class Pager {
	
	private Integer currentPage = null;//当前页码
	
	//private Integer pageTotal = null;//总页码
	
	private Integer total  = null;//总条数
	
	private Integer size = null;//每页显示条数

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	
}
