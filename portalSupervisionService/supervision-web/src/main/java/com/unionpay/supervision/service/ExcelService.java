package com.unionpay.supervision.service;

import org.apache.poi.ss.usermodel.Row;

import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.domain.SuperTypeOversee;

public interface ExcelService {
	
	/**
	 * 会议议定事项
	*/
	void importServiceAndSponsorForMeeting(Row row,OmUser omUser,SuperTypeOversee superTypeOversee);
	/**
	 * 公司领导批示
	*/
	void importServiceAndSponsorForWritten(Row row,OmUser omUser,SuperTypeOversee superTypeOversee);
	
	/**
	 * 公司领导境外出访布置工作
	*/
	void importServiceAndSponsorForAssign(Row row,OmUser omUser,SuperTypeOversee superTypeOversee);
	
	/**
	 * 自定义
	*/
	void importServiceAndSponsorForDefined(Row row,OmUser omUser,SuperTypeOversee superTypeOversee);

}
