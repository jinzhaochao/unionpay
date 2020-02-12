package com.unionpay.common.exception;
/**
 * 
*/
public class ServiceException extends RuntimeException{
	
	/**
	 * 接口运行时异常
	 * author ：xiongym
	 */
	private static final long serialVersionUID = 2433373015678861608L;

	private Integer code;

    private String message;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }
}