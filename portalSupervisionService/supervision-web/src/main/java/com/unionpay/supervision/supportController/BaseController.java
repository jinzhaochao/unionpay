package com.unionpay.supervision.supportController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import com.unionpay.common.exception.ServiceException;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.HttpUtil;

/**
 * 通用Controller
 *
 * @author xiongym
 *
 */
public class BaseController {
	private static  Logger logger =  LoggerFactory.getLogger(BaseController.class);
	
	
	
	/**
	 * 验证请求参数
	 * 
	 * @param result
	 * @param request
	 * @param obj
	 */
	public static void verify(BindingResult result, HttpServletRequest request, Object obj) {
		// 验证上送字段是否合法
		if (result.hasErrors()) {
			String field = result.getFieldError().getField();
			String defaultMessage = result.getFieldError().getDefaultMessage();
			logger.info("参数返回：" + defaultMessage);
			throw new ServiceException(500, "[" + field + "]" + defaultMessage);
		}
	}
	
	/**
	 * 获取用户名
	 * 
	 * @param request
	 */
	public static String getUserId(HttpServletRequest request){
		return SessionUtils.getUserId(request);
	}
	
	/**
	 * 获取用户名
	 */
	public static String getUserId(){
		return SessionUtils.getUserId(HttpUtil.getRequest());
	}
	
	
	protected HttpServletRequest getHttpServletRequest() {
        return HttpUtil.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpUtil.getResponse();
    }

    protected HttpSession getSession() {
        return HttpUtil.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpUtil.getRequest().getSession(flag);
    }

    protected String getParam(String name) {
        return HttpUtil.getRequest().getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        HttpUtil.getRequest().setAttribute(name, value);
    }
	

}
