package com.unionpay.sms.utils;

public class WS_ToSend {
	
	public static WMResult sendMsg(String msg){
		SMSMessageServiceServiceService ss = new SMSMessageServiceServiceService();
		SMSMessageServiceService smsService = ss.getSMSMessageServiceServicePort();
		//String in0 = "{\"content\": \"徐佳男发布会议通知：综合信息系统技术架构评审会，3月14日08:00至3月14日10:00，银联大厦1005会议室，邀请您参会。\",\"mobile\": \"15216706115\",\"userid\": \"xujianan\"}";
		ArrayOfString in1 = new ArrayOfString();
		WMResult wr = smsService.send(msg, in1);//--实际发送时，将此行注释取消掉即可
		System.out.println("=====wr====="+wr);
		/*WMResult wr = new WMResult();//实际发送时，将此行注释掉即可
		wr.setSuccess(true);//实际发送时，将此行注释掉即可*/
		return wr;
	}

}
