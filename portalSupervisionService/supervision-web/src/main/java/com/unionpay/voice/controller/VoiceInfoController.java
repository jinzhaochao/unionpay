package com.unionpay.voice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.TokenProccessor;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.voice.domain.VoiceInfo;
import com.unionpay.voice.model.SearchContionModel;
import com.unionpay.voice.model.VoiceInfoDto;
import com.unionpay.voice.model.VoiceInfoModel;
import com.unionpay.voice.service.VoiceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 客户之声基本信息接口
 * 
 * @author lishuang
 * @date 2019-05-08 10:16:30
 */
@Api(tags = "客户之声基本信息",description = "客户之声基本信息")
@Controller
@RequestMapping("/voiceInfo")
public class VoiceInfoController {
	@Autowired
	private VoiceInfoService voiceInfoService;
	@Autowired
	private OmUserService omUserService;

	/**
	 * 登录接口
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "登录接口",notes = "登录接口")
	@ResponseBody
	@GetMapping("/login")
	public RESTResultBean login(HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		String userid = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userid);
		if (ToolUtil.isEmpty(omUser)){
			result.setCode(500);
			result.setMessage("请先登录~");
			return result;
		}
		JSONObject data = new JSONObject();
		data.put("userid",omUser.getUserid());
		data.put("empName",omUser.getEmpname());
		result.setData(data);
		return result;
	}

	@ApiOperation(value = "点击新增按钮",notes = "点击新增按钮")
	@ResponseBody
	@GetMapping("/clickAddButton")
	public RESTResultBean clickAddButton(HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		String token = TokenProccessor.getInstance().makeToken();
		request.getSession().setAttribute("token",token);
		JSONObject json = new JSONObject();
		json.put("token",token);
		result.setData(json);
		return result;
	}

	@ApiOperation(value = "新增或修改基本信息",notes = "新增或修改基本信息")
	@ResponseBody
	@PostMapping("/addOrUpdateVoiceInfo")
	public RESTResultBean addOrUpdateVoiceInfo(@RequestBody VoiceInfoDto voiceInfoDto, HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		String userid = SessionUtils.getUserId(request);
		if (ToolUtil.isEmpty(userid)){
			result.setCode(500);
			result.setMessage("未登录");
			return result;
		}
		String sessionToken = (String) request.getSession().getAttribute("token");
		String clientToken = voiceInfoDto.getToken();
		//判断是否为重复提交
		boolean b = TokenProccessor.isRepeatSubmit(sessionToken,clientToken);
		if (b){
			result.setCode(500);
			result.setMessage("请不要重复提交");
			return result;
		}
		request.getSession().removeAttribute("token");
		voiceInfoService.addOrUpdate(voiceInfoDto,userid);
		return result;
	}

	/**
	 * 分页条件查询数据
	 * @param searchContionModel
	 * @return
	 */
	@ApiOperation(value = "分页条件查询基本信息",notes = "分页条件查询基本信息")
	@ResponseBody
	@PostMapping("/getPage")
	/*@ApiImplicitParams({
			@ApiImplicitParam(name = "page"),@ApiImplicitParam(name = "size"),
			@ApiImplicitParam(name = "searchContionModel",paramType = "body")
	})*/
	public RESTResultBean getPage(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer size,
							   SearchContionModel searchContionModel){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		//查询的数据
		List<VoiceInfoModel> voiceInfoModels = null;
		//数据总数
		int total = 0;
		if (searchContionModel.getFrontPage()){
			//首页列表--禁用状态的标题 不显示
			voiceInfoModels = voiceInfoService.list(searchContionModel,page,size);
			total = voiceInfoService.getTotal(searchContionModel);
		}else {
			//管理页列表--根据传入的状态，对应显示
			voiceInfoModels = voiceInfoService.list(searchContionModel,page,size);
			total = voiceInfoService.getTotal(searchContionModel);
		}
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setSize(size);
		pager.setTotal(total);
		result.setPager(pager);
		result.setData(voiceInfoModels);
		return result;
	}

	/**
	 * 处理部门树信息
	 * @return
	 */
	@ApiOperation(value = "受理部门树信息",notes = "受理部门树信息")
	@ResponseBody
	@GetMapping("/showDeptsTree")
	public RESTResultBean showDeptsTree(){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		JSONArray infos = voiceInfoService.findInfos();
		result.setData(infos);
		return result;
	}
    /**
     * 处理部门树信息
	 * @date 2019-08-06
     * @return
     */
    @ApiOperation(value = "受理部门树信息",notes = "受理部门树信息")
    @ResponseBody
    @GetMapping("/getDeptsTree")
    public RESTResultBean getDeptsTree(@RequestParam Integer id){
        RESTResultBean result = new RESTResultBean(200 ,"success");
        JSONArray infos = voiceInfoService.findInfos(id);
        result.setData(infos);
        return result;
    }

	/**
	 * 查询详情-前台
	 * @param unid
	 * @return
	 */
	@ApiOperation(value = "查看详情",notes = "查看详情")
	@ResponseBody
	@GetMapping("/getVoiceInfo")
	public RESTResultBean getVoiceInfo(@RequestParam String unid){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		//判断传入的参数是否有效
		VoiceInfo voiceInfo = voiceInfoService.get(unid);
		if (ToolUtil.isEmpty(voiceInfo)){
			result.setCode(500);
			result.setMessage("信息不存在");
			return result;
		}
		//记录该信息阅读量-请求成功一次，在原阅读量基础上增加1
		voiceInfo.setReadNumber(voiceInfo.getReadNumber()+1);
		voiceInfoService.update(voiceInfo);
		//查询数据
		JSONObject infos = voiceInfoService.getInfo(unid);
		result.setData(infos);
		return result;
	}

	/**
	 * 查询详情-后台管理
	 * @param infoUnid
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查看详情-后台管理",notes = "查看详情-后台管理")
	@ResponseBody
	@GetMapping("/findVoiceInfo")
	public RESTResultBean findVoiceInfo(@RequestParam String infoUnid,HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		VoiceInfo voiceInfo = voiceInfoService.get(infoUnid);
		if (ToolUtil.isEmpty(voiceInfo)){
			result.setCode(500);
			result.setMessage("信息不存在");
		}
		String token = TokenProccessor.getInstance().makeToken();
		request.getSession().setAttribute("token",token);
		JSONObject info = voiceInfoService.findInfo(infoUnid);
		info.put("token",token);
		result.setData(info);
		return result;
	}
	
}
