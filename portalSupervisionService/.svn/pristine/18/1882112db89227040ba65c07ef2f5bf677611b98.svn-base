package com.unionpay.common.exception;

import org.springframework.validation.BindingResult;

/*
 * FileName：业务异常的封装
 * Description：
 *
 * History：
 * 版本号 			作者 			日期       				简介
 * 	1.0				chenchen		2017/7/14			    create
 */
@SuppressWarnings("serial")
public class BussinessException extends RuntimeException {

    //友好提示的code码
    private int code;

    //友好提示
    private String message;

    /**
     * Instantiates a new Bussiness exception.
     *
     * @param bizExceptionEnum the biz exception enum
     */
    public BussinessException(BizExceptionEnum bizExceptionEnum) {
        this.code = bizExceptionEnum.getCode();
        this.message = bizExceptionEnum.getMessage();
    }

    public BussinessException(int code, BindingResult result) {
        this.code = code;
        this.message = result.getFieldError().getDefaultMessage();

    }

    public BussinessException(int code, String message) {
        this.code = code;
        this.message = message;

    }

    /**
     * Gets code.
     *
     * @return the code
     * @version * 2017-07-14 chenchen create
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     * @version * 2017-07-14 chenchen create
     */
    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     * @version * 2017-07-14 chenchen create
     */
    public void setMessage(String message) {
        this.message = message;
    }


}
