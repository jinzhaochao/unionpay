package com.unionpay.common.seesion;

/**
 * Created by test0009 on 2017/11/3.
 */
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class SessionUtils {
    public static String userKey="meetUser";

    public static String getUserId(HttpServletRequest request){
        Object userId=request.getSession().getAttribute(userKey);
        if(userId!=null){
            return userId.toString();
        }else{
            return null;
        }
    }
    public static String getUserId() {
        HttpServletRequest request = SessionUtils.getRequest();
        String userId = getUserId(request);
        return userId;
    }

    /**
     * 获取  HttpServletRequest
     *
     * @return request request
     * @version Janye Hu create
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    // 根据SSO Token 获得用户ID
    public static String SSOTokenLogin(String cookieString) {
        String UID = null;
        URLConnection uc = null;
        try {
            URL url = new URL("http://portal.oa.unionpay.com/ssotoken/checktoken");
            // 发送验证信息给desktop.
            uc = url.openConnection();
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.setUseCaches(false);

            ObjectOutputStream objectstream = new ObjectOutputStream(uc.getOutputStream());
            objectstream.writeObject(cookieString);
            objectstream.flush();
            objectstream.close();

            InputStream in = uc.getInputStream();
            ObjectInputStream objectin = new ObjectInputStream(in);
            Object myobject = objectin.readObject();
            UID = (String) myobject;// 获得返回的验证UID,此处UID即LDAP中//的用户名.
            objectin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UID;
    }

    // 检查Cookie获得 SSO Token
    public static String getCookie(HttpServletRequest request) {
        Cookie cookies[] = request.getCookies();
        String cookieString = null;
        if (cookies != null) {
            System.out.println("cookie not null");
            for (int i = 0; i < cookies.length; i++) {
                System.out.println(cookies[i].getName()+"="+cookies[i].getValue());
                if (cookies[i].getName().equals("iPlanetDirectoryPro")) {
                    cookieString = cookies[i].getValue();
                    break;
                }
            }
        }
        return cookieString;
    }
}

