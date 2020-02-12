package com.unionpay.supervision.web.major;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.supervision.supportController.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * <p>
 *  userController
 * </p>
 *
 * @author xiongym
 * @since 2018-11-15
 */
@Api(value = "UserController", description = "督办--模拟登录")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Autowired
    private OmUserService omUserService;
	
	
	/**
	 * 督办--模拟登录
	 * @author xiongym
	 */
	@ApiOperation(value="督办--模拟登录", notes="session存入")
	@RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
    public RESTResultBean login(@RequestParam("account") String account,@RequestParam("passWord") String passWord,
    		HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		if(ToolUtil.isEmpty(account)){
			result.setCode(500);
			result.setMessage("账号不能为空");
			return result;
		}
		//查询用户信息
		OmUser omUser= omUserService.findByUserid(account);
		if(omUser == null){
			result.setCode(100);
			result.setMessage("账号不存在");
			return result;
		}
    	HttpSession session = request.getSession();
    	session.setAttribute(SessionUtils.userKey, account);
		Map<String ,String> data = new HashMap<>();
		data.put("account", account);
		result.setData(data);
		return result;
	}
	
	/**
	 * 督办--登录信息刷新
	 * @author xiongym
	 */
	@ApiOperation(value="督办--登录信息刷新", notes="session信息刷新")
	@RequestMapping(value = "/checklogin", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
    public RESTResultBean checklogin(HttpServletRequest req){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		//session注销
		HttpSession session = req.getSession();
		session.removeAttribute(SessionUtils.userKey);
		// 检查Cookie获得 SSO Token
        String cookieString = SessionUtils.getCookie(req);
        if(ToolUtil.isEmpty(cookieString)){
        	result.setCode(500);
			result.setMessage("cookie未写入");
			return result;
        }
        String userId = SessionUtils.SSOTokenLogin(cookieString);
        if(ToolUtil.isEmpty(userId)){
        	result.setCode(500);
			result.setMessage("用户信息不存在");
			return result;
        }
        session.setAttribute(SessionUtils.userKey, userId);
        Map<String ,String> data = new HashMap<>();
		data.put("userId", userId);
		result.setData(data);
		return result;
	}

}
