package com.unionpay.sms.model;

import com.alibaba.excel.annotation.ExcelColumnNum;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * 模板导出实体类
 * 
 * @author wangyue
 * @date 2018-12-12
 *
 */
public class SmsReceiverToDoModel extends BaseRowModel {
	@ExcelProperty(value = { "接收人" }, index = 0)
	private String receiverName;
	@ExcelProperty(value = { "接收人手机" }, index = 1)
	private String receiverTel;
	@ExcelProperty(value = "部门/单位", index = 2)
	private String orgLevel;

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverTel() {
		return receiverTel;
	}

	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

}
