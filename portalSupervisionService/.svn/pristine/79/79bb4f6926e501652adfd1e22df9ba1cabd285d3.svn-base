package com.unionpay.services.util;

import com.unionpay.common.resultBean.Pager;

import java.io.Serializable;


/**
 * 返回结果封装
 * @author jinzhao
 * @date 2019-08-27
 */
public class ResultList implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private Object data;
    private Pager pager;

    public ResultList() {
    }

    public ResultList(Integer code, String message, Object data, Pager pager) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.pager = pager;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    @Override
    public String toString() {
        return "ResultList{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", pager=" + pager +
                '}';
    }
}
