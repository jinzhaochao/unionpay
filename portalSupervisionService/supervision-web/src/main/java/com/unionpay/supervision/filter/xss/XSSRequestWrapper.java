package com.unionpay.supervision.filter.xss;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
/*
 * FileName：XSSRequestWrapper.java
 * Description：	Request请求过滤包装Wrapper，用于过滤 XSS
 *
 * History：
 * 版本号 			作者 			日期       				简介
 * 	1.0				chenchen		2017/7/14			    create
 */

/**
 * Request请求过滤包装
 * <p>
 * @author   hubin
 * @Date	 2014-5-8 	 
 */

public class XSSRequestWrapper extends HttpServletRequestWrapper {
    //是否过滤xss
	private boolean filterXSS = true;
    //是否过滤sql
	private boolean filterSQL = true;
    //判断是否是上传 上传忽略
	private boolean isUpData = false;

	public XSSRequestWrapper(HttpServletRequest request, boolean filterXSS, boolean filterSQL) {
        super(request);
        this.filterXSS = filterXSS;
        this.filterSQL = filterSQL;
        String contentType = request.getContentType ();
        if (null != contentType){
            isUpData =contentType.startsWith ("multipart");
        }

	}


	public XSSRequestWrapper(HttpServletRequest request) {
		this(request, true, true);
	}


	/**
	 * @Description 数组参数过滤
	 * @param parameter
	 * 				过滤参数
	 * @return
	 */
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if ( values == null ) {
			return null;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for ( int i = 0 ; i < count ; i++ ) {
			encodedValues[i] = filterParamString(values[i]);
		}

		return encodedValues;
	}
    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (null != value && value instanceof String) {
            value = filterParamString((String) value);
        }
        return value;
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (isUpData){
            return super.getInputStream ();
        }else{

            final ByteArrayInputStream bais = new ByteArrayInputStream(inputHandlers(super.getInputStream ()).getBytes());

            return new ServletInputStream() {

                @Override
                public int read() throws IOException {
                    return bais.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) { }
            };
        }

    }
    private String inputHandlers(ServletInputStream servletInputStream){
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(servletInputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  filterParamString(sb.toString ());
    }
    @Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getParameterMap() {
		Map<String, String[]> primary = super.getParameterMap();
		Map<String, String[]> result = new HashMap<String, String[]>(primary.size());
		for ( Map.Entry<String, String[]> entry : primary.entrySet() ) {
			result.put(entry.getKey(), filterEntryString(entry.getValue()));
		}
		return result;

	}
	
	protected String[] filterEntryString(String[] rawValue) {
		for ( int i = 0 ; i < rawValue.length ; i++ ) {
			rawValue[i] = filterParamString(rawValue[i]);
		}
		return rawValue;
	}

	/**
	 * @Description 参数过滤
	 * @param parameter
	 * 				过滤参数
	 * @return
	 */
	@Override
	public String getParameter(String parameter) {
        return filterParamString(super.getParameter(parameter));
	}


	/**
	 * @Description 请求头过滤 
	 * @param name
	 * 				过滤内容
	 * @return
	 */
	@Override
	public String getHeader(String name) {
		return filterParamString(super.getHeader(name));
	}


	/**
	 * @Description Cookie内容过滤
	 * @return
	 */
	@Override
	public Cookie[] getCookies() {
		Cookie[] existingCookies = super.getCookies();
		if (existingCookies != null) {
			for (int i = 0 ; i < existingCookies.length ; ++i) {
				Cookie cookie = existingCookies[i];
				cookie.setValue(filterParamString(cookie.getValue()));
			}
		}
		return existingCookies;
	}

	/**
	 * @Description 过滤字符串内容
	 * @param rawValue
	 * 				待处理内容
	 * @return
	 */
	protected String filterParamString(String rawValue) {
		if (null == rawValue) {
			return null;
		}
		String tmpStr = rawValue;
		if (this.filterXSS) {
			tmpStr = XSSUtil.stripXSS(rawValue);
		}
		if (this.filterSQL) {
			tmpStr = XSSUtil.stripSqlInjection(tmpStr);
		}
		return tmpStr;
	}
}
