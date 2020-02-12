package com.unionpay.services.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.*;
import com.unionpay.services.service.ServerInfoService;
import com.unionpay.services.util.Result;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author xiaopengcheng
 * 基本信息
 */
@Api(tags = "基本信息",description = "基本信息")
@RestController
@RequestMapping("/serverinfo")
public class ServerinfoController {
	@Autowired
	private ServerInfoService serverInfoService;
	@Autowired
	private OmUserService omUserService;

	/**
	 * 点击新增时，进入基本信息编辑页面
	 * @param request
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-11
	 */
	@ApiOperation(value = "登录设置token",notes = "提交保存时，利用token防止重复提交")
	@GetMapping(value = "/login")
	@ResponseBody
	public Result login(HttpServletRequest request){
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (ToolUtil.isEmpty(omUser)){
			return Result.failure("请先登录");
		}
		String token = TokenProccessor.getInstance().makeToken();
		JSONObject json = new JSONObject();
		json.put("token",token);
		request.getSession().setAttribute("token",token);
		return Result.success(json);
	}

	/**
	 * 受理部门、受理人员，选择树
	 *
	 * @author lishuang
	 * @date 2019-03-11
	 */
	@ApiOperation(value = "受理部门树信息",notes = "受理部门树信息")
	@PostMapping("/showHandleInfo")
	@ResponseBody
	public Result showHandleInfo(){
        JSONArray infos = serverInfoService.findInfos();
		return Result.success(infos);
	}

	/**
	 * 根据部门orgid，查询该部门人员信息
	 * @param orgid
	 * @return
	 */
	@ApiOperation(value = "受理人信息",notes = "受理人信息")
	@PostMapping("/getPerson")
	@ResponseBody
	public Result getPerson(@RequestParam Integer orgid){
		JSONArray infos = serverInfoService.getPerson(orgid);
		return Result.success(infos);
	}

	/**
	 * 新增基本信息
	 * @param serverInfoModelDto
	 * @param request
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-12
	 */
	@ApiOperation(value="add-新增基本信息", notes="新增基本信息")
	@PostMapping("/add")
	@ResponseBody
	public Result add(@RequestBody ServerInfoModelDto serverInfoModelDto,HttpServletRequest request){
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (ToolUtil.isEmpty(omUser)){
			return Result.failure("未登录");
		}
		String sessionToken = (String) request.getSession().getAttribute("token");
		String clientToken = serverInfoModelDto.getToken();
		//判断是否为重复提交
		boolean b = TokenProccessor.isRepeatSubmit(sessionToken,clientToken);
		if (true == b){
			return Result.failure("请不要重复提交");
		}
		request.getSession().removeAttribute("token");
		//受理部门-根据第一个受理部门生成服务编码
		String[] depts = serverInfoModelDto.getOrgIds().split(",");
		try{
			ServerInfo serverinfo = serverInfoService.add(serverInfoModelDto,Integer.parseInt(depts[0]));
			if(serverinfo != null && serverinfo.getId() > 0){
				return Result.success(serverinfo.getId());
			}else{
				return Result.failure("初始化数据失败!");
			}
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}

	/**
	 * 删除基本信息（物理删除）
	 * @param jsonId
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-12
	 */
	@ApiOperation(value="delete-删除基本信息", notes="删除基本信息")
	@PostMapping("/delete")
	@ResponseBody
	public Result delete(@RequestBody String jsonId){
		try {
			if (ToolUtil.isNotEmpty(jsonId)){
				JSONObject json = JSONObject.parseObject(jsonId);
				JSONArray ids = json.getJSONArray("ids");
				if (ToolUtil.isNotEmpty(ids)){
					for (Object id : ids){
						serverInfoService.delete(Integer.parseInt(id.toString()));
					}
				}
			}
			return Result.success();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}

	/**
	 * 修改基本信息
	 * @param serverInfoModelDto
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-14
	 */
	@ApiOperation(value="update-修改基本信息", notes="修改基本信息")
	@PostMapping("/update")
	public Result update(@Valid @RequestBody ServerInfoModelDto serverInfoModelDto){
		try{
			if (ToolUtil.isEmpty(serverInfoModelDto)){
				return Result.failure("提交的数据不正确");
			}
			ServerInfo serverInfoModelDto1 = serverInfoService.updateServerInfo(serverInfoModelDto);
			return Result.success(serverInfoModelDto1);
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}

	/**
	 * 修改基本信息（修改后测试接口用 -08/27）
	 * @param serverInfoModelDto
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-14
	 */
	@ApiOperation(value="updateServerInfo-修改基本信息", notes="修改基本信息")
	@PostMapping("/updateServerInfo")
	public Result updateServerInfo(@Valid @RequestBody ServerInfoModelDto serverInfoModelDto){
		try{
			if (ToolUtil.isEmpty(serverInfoModelDto)){
				return Result.failure("提交的数据有误");
			}
			ServerInfo serverInfoModelDto1 = serverInfoService.updateServerInfo(serverInfoModelDto);
			return Result.success(serverInfoModelDto1);
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}

	/**
	 * 查询基本信息-后台
	 * @param id
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-13
	 */
	@ApiOperation(value="get-查询基本信息", notes="查询基本信息")
	@GetMapping("/get")
	@ResponseBody
	public Result get(@RequestParam(name = "id",required = true) Integer id){
		try{
			JSONObject jsonObject = serverInfoService.getById(id);
			if(null == jsonObject){
				throw new RuntimeException();
			}
			return Result.success(jsonObject);
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}

	/**
	 * 分页查询
	 * @param conditionModel
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-3-14
	 */
	@ApiOperation(value="getPage-分页查询基本信息", notes="分页查询基本信息")
	@PostMapping("/getPage")
	@ResponseBody
	public Result getPage(@RequestBody ConditionModel conditionModel){
		try{
			List<ServiceInfoModelDto> list = serverInfoService.getPage(conditionModel);
			Pager pager = new Pager();
			pager.setCurrentPage(conditionModel.getPage());
			pager.setSize(conditionModel.getSize());
			pager.setTotal(serverInfoService.getCount(conditionModel));
			return Result.success(list,pager);
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}

	/**
	 * 分页查询条件下拉列表
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-19
	 */
	@ApiOperation(value="selectTypeAndStatus-分类、状态下拉列表", notes="分类、状态下拉列表")
	@GetMapping("/selectTypeAndStatus")
	@ResponseBody
	public Result selectTypeAndStatus(){
		JSONObject jsonObject = serverInfoService.getTypeAndStatus();
		if (ToolUtil.isNotEmpty(jsonObject)){
			return Result.success(jsonObject);
		}
		return Result.failure();
	}

	/**
	 * 热门服务显示
	 * @param top
	 *
	 * @author lishuang
	 * @date 2019-03-18
	 */
	@ApiOperation(value="getHotService-热门服务", notes="热门服务")
	@GetMapping("/getHotService")
	@ResponseBody
	public Result getHotService(@RequestParam Integer top){
		JSONObject hotService = serverInfoService.getHotService(top);
		if (ToolUtil.isNotEmpty(hotService)){
			return Result.success(hotService);
		}
		return Result.failure();
	}

	/**
	 * 查看服务详情-前台
	 * @param id
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-18
	 */
	@ApiOperation(value="getServerInfo-查看服务详情", notes="查看服务详情")
	@GetMapping("/getServerInfo")
	@ResponseBody
	public Result getServerInfo(@RequestParam Integer id){
		JSONObject jsonObject = serverInfoService.getServerInfo(id);
		if (ToolUtil.isNotEmpty(jsonObject)){
			return Result.success(jsonObject);
		}
		return Result.failure();
	}

	/**
	 * 服务禁用
	 * @param id
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-26
	 */
	@ApiOperation(value="serverDisable-服务禁用", notes="服务禁用")
	@PostMapping("/serverDisable")
	@ResponseBody
	public Result serverDisable(@RequestBody String id ){
		if (ToolUtil.isEmpty(id)){
			return Result.failure("请勾选要禁用的服务");
		}
		JSONObject json = JSONObject.parseObject(id);
		String str = json.getString("id");
		String[] ids = str.split(",");
		if (ToolUtil.isNotEmpty(ids)){
			serverInfoService.serverDisable(ids);
			return Result.success("禁用成功");
		}
		return Result.failure("禁用服务失败");
	}

	/**
	 * 服务启用
	 * @param id
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-26
	 */
	@ApiOperation(value="serverEnable-服务启用", notes="服务启用")
	@PostMapping("/serverEnable")
	@ResponseBody
	public Result serverEnable(@RequestBody String id ){
		if (ToolUtil.isEmpty(id)){
			return Result.failure("请勾选要启用的服务");
		}
		JSONObject json = JSONObject.parseObject(id);
		String str = json.getString("id");
		String[] ids = str.split(",");
		if (ToolUtil.isNotEmpty(ids)){
			serverInfoService.serverEnable(ids);
			return Result.success("启用成功");
		}
		return Result.failure("启用服务失败");
	}

	/**
	 * 受理部门、受理人员，选择树
	 *
	 * @author lishuang
	 * @date 2019-08-06
	 */
	@ApiOperation(value = "受理部门树信息",notes = "受理部门树信息")
	@PostMapping("/getDeptsTree")
	@ResponseBody
	public Result getDeptsTree(@RequestParam Integer id){
		JSONArray infos = serverInfoService.findInfos(id);
		return Result.success(infos);
	}
}