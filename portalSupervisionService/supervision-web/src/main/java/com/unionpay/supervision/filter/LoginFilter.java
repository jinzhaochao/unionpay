package com.unionpay.supervision.filter;

import com.unionpay.common.seesion.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter extends HttpServlet implements Filter {

    @Value("${login.address}")
    private String loginAdress;

    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
        logger.debug("isAccessAllowed");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        logger.info("referer is : " + req.getHeader("referer") + " URL:" + req.getRequestURL());
        String fullUrl = req.getRequestURL()+"";
        if(fullUrl.endsWith("/login") || fullUrl.contains("/lcfeedback") ||  fullUrl.contains("/mobileOaQuery") || fullUrl.endsWith("/downLoadFile") || fullUrl.endsWith("/addVoiceInfo") || fullUrl.endsWith("/uploadFile")){
//        if(fullUrl.contains("/lcfeedback") ||  fullUrl.contains("/mobileOaQuery") || fullUrl.endsWith("/downLoadFile") || fullUrl.endsWith("/addVoiceInfo") || fullUrl.endsWith("/uploadFile")){
            chain.doFilter(req, resp);
            return;
        }else if(fullUrl.endsWith("/logout")||fullUrl.endsWith(".jpg")||fullUrl.endsWith(".png")||fullUrl.endsWith(".jpeg")||fullUrl.endsWith(".gif")||fullUrl.endsWith(".ico")||fullUrl.endsWith(".bmp")||fullUrl.endsWith(".css")||fullUrl.endsWith(".js")|| fullUrl.endsWith(".gif")|| fullUrl.endsWith(".xls")){
            chain.doFilter(req, resp);
            return;
        }else if(fullUrl.endsWith(".xml")||fullUrl.endsWith(".properties")){
            resp.sendRedirect(loginAdress);
            return;
        }else{
            HttpSession session = req.getSession();
            // 用户登录名
//            String userId = SessionUtils.getUserId(req);
            String userId = "portal";
            if(StringUtils.isNotEmpty(userId)){
                chain.doFilter(req,resp);
                logger.info("1");
            }else{
                // 检查Cookie获得 SSO Token
                String cookieString = SessionUtils.getCookie(req);
                logger.info("cookieString is " + cookieString);
                // 验证登录
                if (StringUtils.isEmpty(cookieString)) {
                    resp.sendRedirect(loginAdress);
                    logger.info("2");
                    return;
                }else{
                    userId = SessionUtils.SSOTokenLogin(cookieString);
                    logger.info("3");
                }
                logger.info("userID is : " + userId);
                // SSO Token 没有登陆直接跳转
                if (StringUtils.isEmpty(userId)) {
                    resp.sendRedirect(loginAdress);
                } else {
                    session.setAttribute(SessionUtils.userKey, userId);
                    chain.doFilter(req,resp);
                }
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        System.out.println("==>LoginFilter启动");
    }

    @Override
    public void destroy() {
    }
}
