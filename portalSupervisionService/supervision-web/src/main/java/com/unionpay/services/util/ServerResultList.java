package com.unionpay.services.util;

import com.unionpay.common.resultBean.Pager;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 服务中心部门服务返回结果封装
 * @author jinzhao
 * @date 2019-08-27
 */
public class ServerResultList implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private Object data;
    private Pager pager;
    private BigInteger onlineTotal;

    public ServerResultList() {
    }

    public ServerResultList(Integer code, String message, Object data, Pager pager, BigInteger onlineTotal) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.pager = pager;
        this.onlineTotal = onlineTotal;
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

    public BigInteger getOnlineTotal() {
        return onlineTotal;
    }

    public void setOnlineTotal(BigInteger onlineTotal) {
        this.onlineTotal = onlineTotal;
    }
}
