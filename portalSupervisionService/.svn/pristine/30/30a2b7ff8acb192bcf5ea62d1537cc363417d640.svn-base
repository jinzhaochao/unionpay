package com.unionpay.services.model;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 页面新增接口，防止重复提交实体类
 * @author lishuang
 * @date 2019-04-08
 */
public class TokenProccessor {

    private TokenProccessor() {
    }

    public static final TokenProccessor instance = new TokenProccessor();

    public static TokenProccessor getInstance(){
        return instance;
    }

    public String makeToken(){
        String token = UUID.randomUUID().toString();
        return token;
    }

    public static boolean isRepeatSubmit(String sessionToken,String clientToken){
        //如果页面提交的，没有token，则为重复提交
        if (null == clientToken){
            return true;
        }
        //如果session中没有token，则为重复提交
        if (null == sessionToken){
            return true;
        }
        //如果页面提交的token和session中的token不一致，则为重复提交
        if (!clientToken.equals(sessionToken)){
            return true;
        }
        return false;
    }
}
