package com.unionpay.supervision.web.major;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.bussniss.LiuchengOperator;
import com.unionpay.supervision.domain.*;
import com.unionpay.supervision.model.*;
import com.unionpay.supervision.service.*;
import com.unionpay.supervision.supportController.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 督办事项综合查询 用户信息带加入（xiongym）
 * 
 * @author wangyue
 */
@Api(value = "sponsorDetailController", description = "督办综合查询")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("sponsorDetail")
public class SponsorServiceDetailController extends BaseController {
	@Autowired
	private SponsorServiceDetailService superObjectService;
	@Autowired
	private SuperSponsorService superSponsorService;
	@Autowired
	private SuperServiceService superServiceService;
	@Autowired
	private SuperTypeServiceService superTypeServiceService;
	@Autowired
	private OmUserService omUserService;
	@Autowired
	private SuperServiceOverseeMappingService superServiceOverseeMappingService;
	@Autowired
	private LiuchengOperator liuchengOperator;

	/**
	 * 待立项分页从容contrller
	 */
	// @PostMapping("/selectPageService")
	// @ResponseBody
	// public RESTResultBean selectPageService(@RequestParam(value = "page",
	// defaultValue = "1") Integer page,
	// @RequestParam(value = "size", defaultValue = "10") Integer size,
	// SuperCondition superCondition,
	// HttpServletRequest request) {
	// RESTResultBean result = new RESTResultBean(200, "suceess");
	// // 查询用户信息
	// String userId = SessionUtils.getUserId(request);
	// OmUser omUser = omUserService.findByUserid(userId);
	// if (omUser == null) {
	// result.setCode(100);
	// result.setMessage("未登录");
	// return result;
	// }
	// superCondition.setUserId(omUser.getEmpid().toString());
	// Pager pager = new Pager();
	// pager.setCurrentPage(page);
	// pager.setSize(size);
	// List<SuperProject> list =
	// superObjectService.getSuperCreateProjectFind(page, size, superCondition);
	// pager.setTotal(superObjectService.getCountWithSuperCreateProjectFind(page,
	// size, superCondition));
	// result.setPager(pager);
	// result.setData(list);
	// return result;
	// }

	/**
	 * 我的待办已办分页查询contrller top=1 待确认事项 ;top=2 待立项事项 草稿 ; top=3 待督办事项;  top=4 超期未督办; top=5待回复;top=6 我的已办
	 *  下一次督办时间;
	 */
	@ApiOperation(value = "我的待办已办分页查询", notes = "top=1 待确认 ;top=2 待立项 ;  top=3 待督办事项;top=4 超期未督办 下一次督办时间;top=5待回复;top=6 我的已办")
	@PostMapping("/selectPageSuperServiceReadyToDo")
	@ResponseBody
	public RESTResultBean selectPageSuperServiceReadyToDo(Integer top,Integer page,Integer size, SuperCondition superCondition,HttpServletRequest request) {
		RESTResultBean result = new RESTResultBean(200, "suceess");
		// 查询用户信息
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (omUser == null) {
			result.setCode(100);
			result.setMessage("未登录");
			return result;
		}
		superCondition.setUserId(omUser.getUserid());
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setSize(size);
		if (top != null && (top == 1 || top == 5)) {
			// 流程平台获取数据
			result = liuchengOperator.geQueryTodoTaskList(page, size, userId, superCondition, top);
		} else {
			List<SponsorServiceReadyToDoModel> list = superObjectService.getSuperServiceReadyToDo(page, size,
					superCondition, top);
			pager.setTotal(superObjectService.getCountWithSuperServiceReadyToDo(superCondition, top));
			result.setPager(pager);
			result.setData(list);
		}
		return result;
	}

	/**
	 * 我的待办已办-待督办事项逐条修改反馈要求
	 * @param jsonString
	 * @return
	 * @author lishuang
	 * @date 2019-04-10
	 */
	@ApiOperation(value = "我的待办已办-待督办事项逐条修改反馈要求",notes = "{\"unid\":\"0ff4995c-9c8a-4544-83da-87d6637681d8\",\"feedbackRule\":\"反馈要求\"}")
	@PostMapping("/editFeedbackRule")
	@ResponseBody
	public RESTResultBean editFeedbackRule(@RequestBody String jsonString){
		RESTResultBean result = new RESTResultBean(200, "suceess");
		JSONObject json = JSONObject.parseObject(jsonString);
		String sponsorUnid = json.getString("unid");
		if (null == sponsorUnid){
			result.setCode(500);
			result.setMessage("未选中unid");
			return result;
		}
		String feedbakRule = json.getString("feedbackRule");
		SuperSponsor superSponsor = superSponsorService.findById(sponsorUnid);
		if (ToolUtil.isNotEmpty(superSponsor)){
			superSponsor.setFeedbackRule(feedbakRule);
			superSponsorService.edit(superSponsor);
		}
		return result;
	}

	/**
	 * 我的待办已办、督办事项查询，自定义事项大类下拉框
	 * @return
	 * @author lishuang
	 * @date 2019-04-11
	 */
	@ApiOperation(value = "获取自定义事项下拉框")
	@PostMapping("/getSelfDefineType")
	@ResponseBody
	public RESTResultBean getSelfDefineType(){
		RESTResultBean result = new RESTResultBean(200, "suceess");
		List<SuperTypeOverseeModel> superTypeOverseeModels = superObjectService.getSelfDefineType();
		result.setData(superTypeOverseeModels);
		return result;
	}

	/**
	 * 事项综合分页查询contrller top=1 推进中 ;top=2 推进中超期未督办的事项 ;top=3 已完成 ; top=4 已中止;top=5超过拟办结时间未办结的事项;top = 6 超过6个月未办结的事项
	 * type = 1 分管单位主办事项查询;type = 2 本单位主办事项;type = 3 本科室主办事项 ;type = 4 我的主办事项;
	 * type = 5领导出访布置工作;type = 6 我的督办事项
	 *
	 */
	@ApiOperation(value = "事项综合分页查询", notes = "top=1 推进中 ;top=2 推进中超期未督办的事项 ;top=3 已完成 ; top=4 已中止;top=5超过拟办结时间未办结的事项" +
			"type = 1 分管单位主办事项查询;type = 2 本单位主办事项;type = 3 本科室主办事项 ;type = 4 我的主办事项;type = 5 领导出访布置工作;type = 6 我的督办事项")
	@PostMapping("/selectPageSuperServiceItemSynthesis")
	@ResponseBody
	public RESTResultBean selectPageSuperServiceItemSynthesis(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size, SuperCondition superCondition, Integer top, Integer type, HttpServletRequest request) {
		RESTResultBean result = new RESTResultBean(200, "suceess");
		// 查询用户信息
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (omUser == null) {
			result.setCode(100);
			result.setMessage("未登录");
			return result;
		}
		superCondition.setUserId(omUser.getUserid());
		superCondition.setOrgId(omUser.getDeptorgid());// 部门id
		superCondition.setOfficeOrgId(omUser.getOrgid());// 科室id
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setSize(size);
		List<SponsorServiceItemModel> list = superObjectService.getSuperServiceItemSynthesis(page, size, superCondition,
				top, type);
		pager.setTotal(superObjectService.getCountWithSuperServiceItemSynthesis(superCondition, top, type));
		result.setPager(pager);
		result.setData(list);
		return result;
	}

	/**
	 * 我的部门事项详情页
	 * 
	 * @unid superSponsor的unid ; 类型返回结构不明确。。。。（xiongym）
	 * 
	 */
	@ApiOperation(value = "我的部门事项详情页")
	@GetMapping("/selectDetailByUnid")
	@ResponseBody
	public RESTResultBean SuperSponsorDetail(@RequestParam("unid") String unid) {
		RESTResultBean resultBean = new RESTResultBean(200, "success");
		SuperSponsor SuperSponsor = superSponsorService.findById(unid);
		if (SuperSponsor == null) {
			resultBean.setCode(500);
			resultBean.setMessage("unid is error");
			return resultBean;
		}
		SuperService superservice = superServiceService.findByUnid(SuperSponsor.getServiceUnid());

		SuperTypeService typeService = superTypeServiceService.findByUnid(superservice.getServiceType());
		// 督办事项与督办类型关联
		List<SuperServiceOverseeMapping> superServiceOverseeMappings = superServiceOverseeMappingService
				.findByServiceUnidAndIsPrimary(SuperSponsor.getServiceUnid(), null);

		// 其余主办单位
		List<SuperSponsor> superSponsorlist = superSponsorService.findByServiceUnid(SuperSponsor.getServiceUnid(), 1);
		for (SuperSponsor spo : superSponsorlist) {
			if (spo != null && spo.getUnid().equals(unid)) {
				superSponsorlist.remove(spo);
				break;
			}
		}
		String feedbackTime = SuperSponsor.getFeedbackTime() == null ? ""
				: SuperSponsor.getFeedbackTime().substring(0, 10);
		SuperSponsor.setFeedbackTime(feedbackTime);
		if (superservice != null) {
			// 页面显示的待督办事项详情页，立项时间格式调整---lishuang
			if (ToolUtil.isNotEmpty(superservice.getCreateTime()) && superservice.getCreateTime().length() > 18) {
				superservice.setCreateTime(superservice.getCreateTime().substring(0, 19));
			} else {
				superservice.setCreateTime("");
			}
		}
		// 页面显示的待督办事项详情页，任务来源时间格式调整---lishuang
		for (SuperServiceOverseeMapping ssom : superServiceOverseeMappings) {
			if (ToolUtil.isNotEmpty(ssom.getServiceTime())) {
				ssom.setServiceTime(ssom.getServiceTime().substring(0, 10));
			} else {
				ssom.setServiceTime("");
			}
		}
		JSONObject data = new JSONObject();
		data.put("SuperSponsor", SuperSponsor);
		data.put("superservice", superservice);
		data.put("typeService", typeService);
		data.put("superServiceOverseeMappings", superServiceOverseeMappings);
		data.put("superSponsorlist", superSponsorlist);
		resultBean.setData(data);
		return resultBean;
	}

	/**
	 * 督办首页查询 top=1 待确认事项 ;top=2 待立项事项 草稿 ; top=3 待督办事项; 督办 top=4 超期未督办
	 * 下一次督办时间;top=5待回复;top=6 我的已办;top默认值是1
	 */
	@ApiOperation(value = "首页查询（废弃，采用我的待办已办）")
	@GetMapping("/MySuperSponsor")
	@ResponseBody
	public RESTResultBean MySuperSponsor(@RequestParam(value = "top", defaultValue = "1") Integer top,
			HttpServletRequest request) {
		RESTResultBean resultBean = new RESTResultBean(200, "成功");
		List<SuperMessage> list = null;
		// 查询用户信息
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (omUser == null) {
			resultBean.setCode(100);
			resultBean.setMessage("未登录");
			return resultBean;
		}
		if (top == 1 || top == 5) {
			// 流程平台获取数据

		} else {
			list = superObjectService.getSuperMessageCheck(top, omUser.getUserid());
		}
		JSONObject data = new JSONObject();
		data.put("SuperMessage", list);
		resultBean.setData(data);
		return resultBean;
	}

	/**
	 * 督办首页统计
	 *   添加 6超过拟办结时间未办结事项总数和7超过六个月未办结事项总数  jinzhao  2019-12-10   tabPage=1 我的督办  tabPage=2 本单位主办  tabPage=3本科室主办  tabPage=4 我的主办
	 */
	@ApiOperation(value = "首页统计")
	@GetMapping("/MySuperSponsorTotal")
	@ResponseBody
	public RESTResultBean MySuperSponsorTotal(Integer tabPage) {
		RESTResultBean resultBean = new RESTResultBean(200, "成功");
		// 查询用户信息
		String userId = getUserId();
		OmUser omUser = omUserService.findByUserid(userId);
		if (omUser == null) {
			resultBean.setCode(100);
			resultBean.setMessage("未登录");
			return resultBean;
		}
		SuperCondition superCondition = new SuperCondition();
		superCondition.setUserId(omUser.getUserid());
		superCondition.setOrgId(omUser.getDeptorgid());// 部门id
		superCondition.setOfficeOrgId(omUser.getOrgid());// 科室id
		superCondition.setUserId(omUser.getUserid());
			// 推进中的事项
		int driveNum = superObjectService.getCountWithMySuperService(superCondition, 1,tabPage);
		// 其中超期未办理
		int outTimeNum = superObjectService.getCountWithMySuperService(superCondition, 2,tabPage);
		// 已完成的事项
		int finisheNum = superObjectService.getCountWithMySuperService(superCondition, 3,tabPage);
		// 已中止的事项
		int discontinueNum = superObjectService.getCountWithMySuperService(superCondition, 4,tabPage);
		// 已终止的事项  注掉 --jinzhao  2019-12-10
//		int stopNum = superObjectService.getCountWithMySuperService(superCondition, 5);
		//超出拟办结时间未办结的事项
		int outPlannedTime = superObjectService.getCountWithMySuperService(superCondition,6,tabPage);
		//超出6个月未办结的事项
		int sixOutTimeNum = superObjectService.getCountWithMySuperService(superCondition,7,tabPage);
		JSONObject data = new JSONObject();
		data.put("driveNum", driveNum);
		data.put("outTimeNum", outTimeNum);
		data.put("finisheNum", finisheNum);
		data.put("discontinueNum", discontinueNum);
//		data.put("stopNum", stopNum);
		data.put("outPlannedTime", outPlannedTime);
		data.put("sixOutTimeNum", sixOutTimeNum);
		resultBean.setData(data);
		return resultBean;
	}
	
	/**
	 * 我的督办事项分页查询 top=1 推进中;top=2 推进中超期未督办；top=3 已完成 ; top=4 已中止; top=5 已终止;top=6 超期拟办结时间未办结的事项; top=7 超过6个月未办结的事项;
	 *
	 */
	@ApiOperation(value = "我的督办事项分页查询", notes = "top=1 推进中;top=2 推进中超期未督办；top=3 已完成 ; top=4 已中止; top=5 已终止;top=6 超期拟办结时间未办结的事项; top=7 超过6个月未办结的事项;")
	@PostMapping("/selectPageMySuperService")
	@ResponseBody
	public RESTResultBean selectPageMySuperService(HttpServletRequest request, Integer top,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, SuperCondition superCondition) {
		RESTResultBean result = new RESTResultBean(200, "suceess");
		// 查询用户信息
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (omUser == null) {
			result.setCode(100);
			result.setMessage("未登录");
			return result;
		}
		superCondition.setUserId(omUser.getUserid());
		superCondition.setOrgId(omUser.getDeptorgid());// 部门id
		superCondition.setOfficeOrgId(omUser.getOrgid());// 科室id
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setSize(size);
		List<SponsorServiceItemModel> list = superObjectService.getMySuperService(page, size, superCondition,top);
//		pager.setTotal(superObjectService.getCountWithMySuperService(superCondition, top));
		result.setPager(pager);
		result.setData(list);
		return result;
	}
}
