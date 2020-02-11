package com.unionpay.common.exception;
import org.apache.http.HttpStatus;
/*
  * FileName：BizExceptionEnum.java
  * Description：	所有业务异常的枚举
  *
  * History：
  * 版本号 			作者 			日期       				简介
  * 	1.0				chenchen		2017/7/14			    create
  */
public enum BizExceptionEnum {
	/**
     * 错误码简述
     */
    SUCCESS(200, "操作成功！"),
    FAILED(400,"请求信息异常！"),

    /**
     * 数据库错误码
     */
    DB_FAILED(500,"数据库操作失败！"),

    /**
     * 验签错误码
     */
    BAD_REQUEST(HttpStatus.SC_BAD_REQUEST,"请求信息异常！"),
    FORBIDDEN_REQUEST(HttpStatus.SC_FORBIDDEN,"访问被禁止！"),

    /**
     * 文件上传
     */
    FILE_STAFF_ERROR(400,"文件员工类型有误！"),
    FILE_OVER_SIZE(400,"文件超出限制！"),
    FILE_TYPE_ERROR(400, "上传文件类型错误！"),
    FILE_NOT_FOUND(400, "文件不存在！"),
    FILE_READING_ERROR(400, "文件解析出错！"),
    FILE_UPLOAD_ERROR(500, "上传文件出错！"),


    /**
     * 请求信息错误码
     */
    CODE_PARAM_ERROR(300, "传入参数错误！"),
    CODE_TOKEN_ERROR(301, "Token 验证失败！"),
    CODE_CAPTCHA_ERROR(302, "验证码错误！"),
    CODE_DATA_VALIDATE_FAILED(303, "数据校验未通过！"),
    CODE_STATE_EXIST(305, "请勿重复请求！"),
    CODE_BAD_REQUEST(400, "请求内容错误！"),
    CODE_REQUEST_INVALID(404, "请求资源不存在！"),
    CODE_DATA_ERROR_PAGETIME_EXPIRE(401, "页面超时不可用！"),

    /**
     * 系统错误码
     */
    CODE_SERVICE_NOT_AVAILABLE(500, "系统服务出错，请联系管理员"),
    CODE_200(200,"成功"),
    CODE_401(401,"未登录，需要登录"),
    CODE_402(402,"未登录"),
    CODE_405(405, "权限不足"),
    CODE_406(406,"客户端请求接口参数不正确或缺少参数"),
    CODE_501(501,"服务器接口错误"),
    CODE_999(999,"保留码");


    BizExceptionEnum(int code, String message) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
    }

    BizExceptionEnum(int code, String message, String urlPath) {
        this.friendlyCode = code;
        this.friendlyMsg = message;
        this.urlPath = urlPath;
    }

    private int friendlyCode;

    private String friendlyMsg;

    private String urlPath;

    public int getCode() {
        return friendlyCode;
    }

    public void setCode(int code) {
        this.friendlyCode = code;
    }

    public String getMessage() {
        return friendlyMsg;
    }

    public void setMessage(String message) {
        this.friendlyMsg = message;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

}
