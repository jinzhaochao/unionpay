
package com.unionpay.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/*
 * FileName：HttpUtil.java
 * Description：	http 工具类
 *
 * History：
 * 版本号 			作者 			日期       				简介
 * 	1.0				chenchen		2017/7/14			    create
 */
public class HttpUtil {
    private static Logger log= LoggerFactory.getLogger(HttpUtil.class);
    /**
     * Get ip string.
     *
     * @return the string
     * @version * 2017-07-14 chenchen create
     */
    public static String getIp(){
       return HttpUtil.getRequest().getRemoteHost();
    }

    /**
     * 获取所有请求的值
     *
     * @return the request parameters
     * @version * 2017-07-14 chenchen create
     */
    public static Map<String, String> getRequestParameters() {
        HashMap<String, String> values = new HashMap<>();
        HttpServletRequest request = HttpUtil.getRequest();
        Enumeration enums = request.getParameterNames();
        while ( enums.hasMoreElements()){
            String paramName = (String) enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.put(paramName, paramValue);
        }
        return values;
    }

    /**
     * 获取 HttpServletRequest
     *
     * @return the response
     * @version * 2017-07-14 chenchen create
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    /**
     * 获取 包装防Xss Sql注入的 HttpServletRequest
     *
     * @return request request
     * @version * 2017-07-14 chenchen create
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数
     * @return URL 所代表远程资源的响应结果
     * @version * 2017-07-14 chenchen create
     */
    public static String sendGet(String url, Map<String, String> param) {
        String result = "";
        BufferedReader in = null;
        try {
            String para = "";
            for (String key : param.keySet()) {
                para += (key + "=" + param.get(key) + "&");
            }
            if (para.lastIndexOf("&") > 0) {
                para = para.substring(0, para.length() - 1);
            }
            String urlNameString = url + "?" + para;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                log.debug(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果 string
     * @version * 2017-07-14 chenchen create
     */
    public static String sendPost(String url, Map<String, String> param) {
        CloseableHttpClient httpclient=null;
        CloseableHttpResponse response=null;
        try {
            httpclient = HttpClients.createDefault();
            // 构造一个post对象
            HttpPost httpPost = new HttpPost(url);
            if (ToolUtil.isNotEmpty(param)) {
                // 添加所需要的post内容
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                param.forEach((Key,value)->nvps.add(new BasicNameValuePair(Key,value)));
                //设置
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            }
            response = httpclient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (Exception e) {
            log.error("发送失败！！！", e);
        }finally{
            try {
                if (response!=null) {
                    response.close();
                }
                if (httpclient!=null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                log.error("关闭http连接出错",e);
            }
        }
        return null;
    }

    /**
     * 判断当前是否ajax请求
     * @return
     */
    public static boolean assertAjax() {
        HttpServletRequest request=HttpUtil.getRequest();
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            //如果是ajax请求响应头会有，x-requested-with
            return true;
        }else if(StrUtil.isNotEmpty(request.getHeader("Content-Type"))&&request.getHeader("Content-Type").startsWith("multipart/form-data")) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 输出json，用于ajax返回
     * @param resultMap
     * @throws IOException
     */
    public static void outToAjax( Map<String, Object> resultMap)
        throws IOException {
        try {
            HttpServletResponse response=HttpUtil.getResponse();
            response.setCharacterEncoding("utf-8");
            response.setStatus(400);
            PrintWriter out = response.getWriter();
            out.println(JSON.toJSONString(resultMap));
            out.flush();
            out.close();
        } catch (Exception e) {
            System.err.println("输出JSON异常，可以忽略。");
        }
    }
   


    public static String getRemortIP() {
        if (getRequest().getHeader("x-forwarded-for") == null) {
            return getRequest().getRemoteAddr();
        }
        return getRequest().getHeader("x-forwarded-for");
    }


}
