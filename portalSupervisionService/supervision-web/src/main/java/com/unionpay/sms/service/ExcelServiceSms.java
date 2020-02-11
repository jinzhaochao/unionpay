package com.unionpay.sms.service;

import org.apache.poi.ss.usermodel.Row;

import com.unionpay.supervision.domain.OmUser;

/**
 * 导入Excel的Service
 * 
 * @author wangyue
 * @date 2018-12-12
 *
 */
public interface ExcelServiceSms {
	String importServiceAndSms(Row row,String matterUnid);
}
