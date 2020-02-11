package com.unionpay.supervision.bussniss;

import org.springframework.stereotype.Component;

import com.unionpay.common.util.MD5Util;
import com.unionpay.common.util.ToolUtil;


/**
 * <p>
 * 流程平台反馈操作
 * </p>
 *
 * @author xiongym
 * @since 2018-11-29
 */

@Component
public class FeedBackOperator {
	
	
	/*
	 * 时间戳验证
	*/
	public static Boolean isTrueTime(String requestTime){
		Boolean isTrueTime = false;
		if(ToolUtil.isNotEmpty(requestTime)){
			Long currentTime = System.currentTimeMillis();
			Long requestTimetoLong = Long.parseLong(requestTime);
			Long beforeTime = requestTimetoLong - (1000*60*5);
			Long afterTime = requestTimetoLong + (1000*60*5);
			if(beforeTime < currentTime  &&  currentTime < afterTime){
				isTrueTime = true;
			}
		}
		
		return isTrueTime;
	}
	
	
	/*
	 * 签名生成
	*/
	public static String getSign(String appkey,String appSecret,String requestTime ){
		String sign = null;
		sign = MD5Util.encrypt(appkey + appSecret + requestTime);
		return sign;
	}

}
