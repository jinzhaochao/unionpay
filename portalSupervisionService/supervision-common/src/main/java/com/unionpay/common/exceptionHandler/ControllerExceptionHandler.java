package com.unionpay.common.exceptionHandler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import com.unionpay.common.exception.BizExceptionEnum;
import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.exception.ServiceException;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.ToolUtil;

/**
 * 控制器错误处理器，从控制器抛出的异常被它拦截。
 * 可以在此处封装错误信息，以友好的方式返回给前端
 */
@RestController
@ControllerAdvice
public class ControllerExceptionHandler implements ErrorController{
	
	
	
	/**
     * 处理ServiceException
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public Map<String,Object> HandlerServiceException(ServiceException e){
        Map<String,Object> errorMessage = new HashMap<>();
        errorMessage.put("code",e.getCode());
        errorMessage.put("message",e.getmessage());
        return errorMessage;
    }

    @ExceptionHandler(value = Exception.class)
    @RequestMapping("/error")
    public RESTResultBean handleError(HttpServletRequest request, Exception ex){
        return new RESTResultBean(this.getBizException(request,ex));

    }
    
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	private BussinessException getBizException(HttpServletRequest request, Exception ex){
        BussinessException bizException = null;
        StringBuffer sbf = new StringBuffer();
        if (ex instanceof BindException) {
            FieldError fieldErro = ((BindException) ex).getBindingResult().getFieldError();
            sbf.append(fieldErro.getField()).append("[").append(fieldErro.getDefaultMessage()).append("]");
            bizException = new BussinessException(BizExceptionEnum.BAD_REQUEST.getCode(),sbf.toString());
        }else if(ex instanceof MethodArgumentNotValidException){
            FieldError fieldErro = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldError();
            sbf.append(fieldErro.getField()).append("[").append(fieldErro.getDefaultMessage()).append("]");
            bizException = new BussinessException(BizExceptionEnum.BAD_REQUEST.getCode(),sbf.toString());
        }else if (ex instanceof HttpRequestMethodNotSupportedException) {
            sbf.append(ex.getMessage());
            bizException = new BussinessException(BizExceptionEnum.BAD_REQUEST.getCode(),sbf.toString());
        }else if (ex instanceof NoHandlerFoundException) {
            bizException = new BussinessException(BizExceptionEnum.CODE_REQUEST_INVALID);
        }else if (ex instanceof MissingServletRequestParameterException) {
            sbf.append("缺少必要的参数[").append(ex.getMessage()).append("]");
            bizException = new BussinessException(BizExceptionEnum.CODE_PARAM_ERROR.getCode(),sbf.toString());
        }else if (ex instanceof BussinessException){
            bizException = (BussinessException) ex;
        }else if(ex instanceof NullPointerException){
            ex.printStackTrace();
            sbf.append("数据内容为空！");
            bizException = new BussinessException(BizExceptionEnum.FAILED.getCode(),sbf.toString());
        }else{
            Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
            if(ToolUtil.isNotEmpty(statusCode)&& statusCode == BizExceptionEnum.CODE_REQUEST_INVALID.getCode()){
                bizException = new BussinessException(BizExceptionEnum.CODE_REQUEST_INVALID);
            }else{
                ex.printStackTrace();
                bizException = new BussinessException(BizExceptionEnum.CODE_SERVICE_NOT_AVAILABLE);
            }
        }
        return bizException;
    }


}
