package com.unionpay.common.resultBean;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.exception.BussinessException;

/**
 * 接口返回
 * author : xiongym
*/
public class RESTResultBean {
	
	private Integer code;
	private String message;
	private Pager pager = null ;
	private Object data;
	
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public RESTResultBean(){
	}
	
	public RESTResultBean(Integer code,String message){
		this.code = code;
		this.message = message;
	}
	
	public RESTResultBean(Integer code,String message,JSONObject data){
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public RESTResultBean SUGGESTION(){
        return new RESTResultBean(ResultStatus.OK,"success");
	}
	
	public RESTResultBean(BussinessException bizException){
        this.code = bizException.getCode();
        this.message = bizException.getMessage();
    }

	@Override
	public String toString() {
		return "RESTResultBean{" +
				"code=" + code +
				", message=" + message +
				", pager=" + pager +
				", data=" + data +
				'}';
	}
}
