package com.unionpay.supervision.filter.submit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.cache.Cache;
import com.unionpay.common.exception.BussinessException;

@Aspect
@Component
/**
 * @功能描述 aop解析注解
 */
public class NoRepeatSubmitAop {

	private Log logger = LogFactory.getLog(getClass());

	@Autowired
	private Cache<String, Integer> cache;
	
	@Pointcut("execution(* com.unionpay..*(..))")
    public void webLog() {
    }

	public String getKey() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
		HttpServletRequest request = attributes.getRequest();
		String key = sessionId + "-" + request.getServletPath();
		return key;
	}

	@Before("webLog() && @annotation(nrs)")
	public void before(NoRepeatSubmit nrs) {
		String key = getKey() ;
		if (cache.getIfPresent(key) == null) {// 如果缓存中有这个url视为重复提交		
			
            cache.put(key, 0);
		} else {
			logger.error("重复提交");
			throw new BussinessException(500, "重复提交");
		}
	}

	@AfterReturning("webLog() && @annotation(nrs)")
	public void doAfterReturning(NoRepeatSubmit nrs) {
		// 处理完请求，返回内容
		String key = getKey() ;
		if (cache.getIfPresent(key) != null) {
			cache.invalidate(key);
		}
	}

//	@Around("webLog() && @annotation(nrs)")
//	public Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit nrs) {
//		String key = getKey() ;
//		if (cache.getIfPresent(key) == null) {// 如果缓存中有这个url视为重复提交
//			Object o;
//			try {
//				o = pjp.proceed();
//			} catch (Throwable e) {
//				e.printStackTrace();
//				logger.error("验证重复提交时出现未知异常!");
//				return "{\"code\":-889,\"message\":\"验证重复提交时出现未知异常!\"}";
//			}
//			cache.put(key, 0);
//			return o;
//		} else {
//			logger.error("重复提交");
//			throw new BussinessException(500, "重复提交");
//		}
//
//	}

}
