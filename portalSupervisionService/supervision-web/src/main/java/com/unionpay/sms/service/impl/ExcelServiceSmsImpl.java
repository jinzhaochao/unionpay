package com.unionpay.sms.service.impl;

import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.util.ExcelReadUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.sms.domain.smsReceiver;
import com.unionpay.sms.service.ExcelServiceSms;
import com.unionpay.sms.service.SmsReceiverService;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Excel的service的实现类
 * 
 * @author wangyue
 * @date 2018-12-12
 */
@Service
public class ExcelServiceSmsImpl implements ExcelServiceSms {
	@Autowired
	private SmsReceiverService smsReceiverService;

	/**
	 * Excel导入
	 */
	@Transactional
	public String importServiceAndSms(Row row,String matterUnid) {
		// 主键
		String unid = UUID.randomUUID().toString();
		// 接收人编码
		String receiverId = UUID.randomUUID().toString();
		// 接收人姓名
		String receiverName = ExcelReadUtil.convertToString(row.getCell(0)).trim();
		// 接收人手机号码
		String receiverTel = ExcelReadUtil.convertToString(row.getCell(1)).trim();
		// 部门/单位
		String orgName = ExcelReadUtil.convertToString(row.getCell(2)).trim();
		if (ToolUtil.isEmpty(receiverName)||ToolUtil.isEmpty(receiverTel)){
			throw new BussinessException(501,"必填项不能为空");
		}
		if (!receiverTel.matches("^(1[3|4|5|7|8|9])\\d{9}$")){
			throw new BussinessException(501,"手机号不符合要求");
		}
		List<smsReceiver> smsReceivers = smsReceiverService.findOutSmsReceivers(matterUnid);
		String userName = "";
		if (ToolUtil.isNotEmpty(smsReceivers)){
			for (smsReceiver receiver : smsReceivers){
				if (receiverTel.equals(receiver.getReceiverTel())){
					userName += receiverName+",";
					return userName;
				}
			}
		}
		smsReceiver receiver = new smsReceiver();
		receiver.setUnid(unid);
		receiver.setReceiverId(receiverId);
		receiver.setReceiverName(receiverName);
		receiver.setReceiverTel(receiverTel);
		receiver.setMatterUnid(matterUnid);
		receiver.setOrgName(orgName);
		receiver.setStatus("待发送");
		receiver.setType("OUT");
		smsReceiverService.saveSmsReceiver(receiver);
		return "";
	}
}
