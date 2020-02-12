package com.unionpay.sms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.sms.domain.smsMatter;
import com.unionpay.sms.domain.smsReceiver;
import com.unionpay.sms.model.ReceiveInfo;
import com.unionpay.sms.model.SmsReceiverResult;
import com.unionpay.sms.model.smsMatterCondition;
import com.unionpay.sms.service.OmOrganizationService;
import com.unionpay.sms.service.SmsMatterService;
import com.unionpay.sms.service.SmsReceiverService;
import com.unionpay.sms.task.SmsTask;
import com.unionpay.sms.utils.WMResult;
import com.unionpay.supervision.bussniss.LiuchengOperator;
import com.unionpay.supervision.domain.OmOrganization;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * @author wangyue
 * @date 2018_12-7
 *
 */
@Api(value = "ExcelController",description = "短信发送")
@Controller
@RequestMapping("smsMatter")
public class SmsMatterController {
	@Autowired
	private OmUserService omUserService;
	@Autowired
	private OmOrganizationService omOrganizationService;
	@Autowired
	private SmsMatterService smsMatterService;
	@Autowired
	private SmsReceiverService smsReceiverService;
	@Autowired
	private LiuchengOperator liuchengOperator;
	@Autowired
	private SmsTask smsTask;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 申请发送-->显示申请界面
	 * 
	 * @param request
	 * @return
	 *
	 * @author lishuang
	 * @date 2018-12-24
	 */
	@ApiOperation(value = "申请发送-->显示申请界面")
	@RequestMapping(value = "/matterLogin", method =  RequestMethod.GET)
	@ResponseBody
	public RESTResultBean smsMatterLogin(HttpServletRequest request) {
		RESTResultBean result = null;
		String userid = SessionUtils.getUserId(request);
		// 判断用户是否登陆
		if (userid == null) {
			result = new RESTResultBean(100, "请您先登录再使用！");
		} else {
			result = new RESTResultBean(200,"欢迎使用短信发送功能~");
			// 根据用户userid返回用户信息
			OmUser omUser = omUserService.findByUserid(userid);
			// 根据部门id返回部门信息
			OmOrganization omOrganization = omOrganizationService.findByOrgid(omUser.getOrgid());
			JSONObject data = new JSONObject();
			String uuid = UUID.randomUUID().toString();
			data.put("unid", uuid);
			data.put("empName", omUser.getEmpname());
			data.put("orgName", omOrganization.getOrgname());
			result.setData(data);
		}
		return result;
	}

	/**
	 * 部门及员工树信息
	 *
	 * @author lishuang
	 * @date 2019-04-18
	 */
	@ApiOperation(value = "申请发送-->显示部门及员工树信息")
	@RequestMapping(value = "/showAllInfo",method = RequestMethod.GET)
	@ResponseBody
	public RESTResultBean showOmOrganizationAndSmsReceivers(@RequestParam String orgid){
		RESTResultBean result = new RESTResultBean(200,"success");
		if ("0"==orgid){
			orgid = "0";
		}else {
			orgid = orgid.replace("o","");
		}
		JSONArray infos = smsMatterService.getDeptTree(Integer.parseInt(orgid));
		result.setData(infos);
		return result;
	}

	/**
	 * 申请发送-->添加内部短信接收人（单个/批量添加、按部门添加）
	 * @param receiveInfo 前端传入的部门及员工信息
	 *
	 * @author lishuang
	 * @date 2018-12-24
	 */
	@ApiOperation(value = "申请发送-->添加内部短信接收人（单个/批量添加、按部门添加）",
			notes = "{ \"org\":[o207],\"user\":[\"u2084\",\"u2645\"],\"unid\":\"1f8335f1-950b-43e4-9e6a-b5da7efcd82f\"}")
	@RequestMapping(value = "/saveInSmsReceiver",method = RequestMethod.POST)
	@ResponseBody
	public RESTResultBean saveInSmsReceiver(@RequestBody ReceiveInfo receiveInfo){
		RESTResultBean result = new RESTResultBean(200,"success");
		List<String> empIds = receiveInfo.getUser();
		List<String> orgIds = receiveInfo.getOrg();
		String unid = receiveInfo.getUnid();
		List<String> allEmpId = new ArrayList<>();
		if (ToolUtil.isNotEmpty(orgIds)){
			allEmpId = smsReceiverService.getAllEmpId(orgIds);
		}
		if (ToolUtil.isNotEmpty(empIds)){
			for (String empid : empIds){
				String id = empid.replace("u","");
				if (!allEmpId.contains(id)){
					allEmpId.add(id);
				}
			}
		}
		//判断已经添加的内部接收人，不予再次添加
		List<smsReceiver> smsReceivers = smsReceiverService.findInSmsReceivers(unid);
		if (ToolUtil.isNotEmpty(smsReceivers)){
			for (smsReceiver receiver : smsReceivers){
				if (allEmpId.contains(receiver.getReceiverId())){
					allEmpId.remove(receiver.getReceiverId());
				}
			}
		}
		if(ToolUtil.isNotEmpty(allEmpId)){
			try {
				smsReceiverService.saveInSmsReceiver(allEmpId,unid);
			} catch (Exception e) {
				String error = e.getMessage();
				if (!error.contains("不")){
					error = "";
				}
				logger.error(error);
				throw new BussinessException(500,error);
			}
		}
		return result;
	}

	/**
	 * 申请发送-->显示已选部门、科室及相应数量、内部接收人总数
	 * @return
	 */
	@ApiOperation(value = "申请发送-->显示已选部门、科室及相应数量、内部接收人总数")
	@RequestMapping(value = "/findOmOrganizationNameAndTotal",method = RequestMethod.GET)
	@ResponseBody
	public RESTResultBean findOmOrganizationNameAndTotal(@RequestParam String unid){
		RESTResultBean result = new RESTResultBean(200,"success");
		Map<String,Object> data = smsReceiverService.findOmOrganizationNameAndTotal(unid);
		result.setData(data);
		return result;
	}

	/**
	 * 申请发送-->添加短信接收人（外部人员）
	 * @param receiverInfo
	 * @return
	 *
	 * @author lishuang
	 * @date 2018-12-24
	 */
	@ApiOperation(value = "申请发送-->添加外部短信接收人", notes = "{\"name\":\"徐佳男\",\"tel\":\"13566669999\"," +
			"\"company\":\"银联\",\"unid\":\"c02f9113-d3d1-4b65-b616-147b412077c1\"}")
	@RequestMapping(value = "/saveOutSmsReceiver",method = RequestMethod.POST)
	@ResponseBody
	public RESTResultBean saveOutSmsReceiver(@RequestBody String receiverInfo){
		RESTResultBean result = null;
		JSONObject json = JSONObject.parseObject(receiverInfo);
		if (ToolUtil.isNotEmpty(json)){
			String name = json.getString("name");
			String tel = json.getString("tel");
			String company = json.getString("company");
			String unid = json.getString("unid");
			String regix = "^(1[3|4|5|7|8|9])\\d{9}$";
			if(name.length()<0||tel.length()<0||company.length()<0){
				result=new RESTResultBean(500,"请输入要添加的短信接收人的信息");
			}else if (!tel.matches(regix)){
				result=new RESTResultBean(500,"输入的手机号码不符合要求");
			}else {
				List<smsReceiver> smsReceivers = smsReceiverService.findAllSmsReceiver(unid);
				if (ToolUtil.isNotEmpty(smsReceivers)){
					for (smsReceiver receiver : smsReceivers){
						if (tel.equals(receiver.getReceiverTel())){
							result=new RESTResultBean(500,"手机号码重复");
							logger.error("手机号码重复");
							return result;
						}
					}
				}
				smsReceiverService.saveOutSmsReceiver(unid,name,tel,company);
				result=new RESTResultBean(200,"success");
			}
		}
		return result;
	}

	/**
	 * 申请发送-->删除接收人（批量删除）
	 * @param jsonUnid
	 * @return
	 *
	 * @author lishuang
	 * @date 2018-12-25
	 */
	@ApiOperation(value = "申请发送-->单个/批量删除接收人",
			notes = "{\"unid\":\"07fe6c65-5db7-4218-b926-c26ff8688a97,0a5dece6-149b-449e-9065-ac8bba5082a9\"}")
	@RequestMapping(value = "/deleteSmsReceiver",method = RequestMethod.POST)
	@ResponseBody
	public RESTResultBean deleteSmsReceiver(@RequestBody String jsonUnid){
		RESTResultBean result = null;
		JSONObject json = JSONObject.parseObject(jsonUnid);
		String[] unid = json.getString("unid").split(",");
		if (ToolUtil.isEmpty(unid)){
			result = new RESTResultBean(500,"请选择要删除的接收人");
		}else {
			smsReceiverService.deleteByUnid(unid);
			result = new RESTResultBean(200,"success");
		}
		return result;
	}

	/**
	 * 申请发送-->显示所有已选择内部接收人
	 * @return
	 *
	 * @author lishuang
	 * @date 2018-12-25
	 *
	 * 已测 OK
	 */
	@ApiOperation(value = "申请发送-->显示已选择内部接收人")
	@RequestMapping(value = "/listAllInSmsReceivers",method = RequestMethod.GET)
	@ResponseBody
	public RESTResultBean showAllInSmsReceivers(@RequestParam String unid){
		RESTResultBean result = new RESTResultBean(200,"success");
		List<smsReceiver> list = smsReceiverService.findInSmsReceivers(unid);
		JSONObject date = new JSONObject();
		date.put("InSmsReceivers",list);
		result.setData(date);
		Pager pager = new Pager();
		pager.setTotal(smsReceiverService.findInSmsReceiversTotal(unid));
		result.setPager(pager);
		return result;
	}

	/**
	 * 申请发送-->显示所有已选择外部接收人、人数
	 * @return
	 *
	 * @author lishuang
	 * @date 2018-12-25
	 *
	 * 已测 OK
	 */
	@ApiOperation(value = "申请发送-->显示所有已选择外部接收人、人数")
	@RequestMapping(value = "/listAllOutSmsReceivers",method = RequestMethod.GET)
	@ResponseBody
	public RESTResultBean showAllOutSmsReceivers(@RequestParam String unid){
		RESTResultBean result = new RESTResultBean(200,"success");
		List<smsReceiver> list = smsReceiverService.findOutSmsReceivers(unid);
		JSONObject date = new JSONObject();
		date.put("OutSmsReceivers",list);
		result.setData(date);
		Pager pager = new Pager();
		pager.setTotal(smsReceiverService.findOutSmsReceiverTotal(unid));
		result.setPager(pager);
		return result;
	}

    /**
     * 申请发送—>审核人的列表显示
     * @param request
     *
     * @author lishuang
     * @date 2018-12-29
     */
    @ApiOperation(value = "申请发送—>审核人的列表显示")
    @ResponseBody
    @GetMapping("/auditorList")
    public RESTResultBean AuditorList(HttpServletRequest request) {
        RESTResultBean result = result = new RESTResultBean(200, "success");
        // 获取当前申请人userid
        String userid = SessionUtils.getUserId(request);
        //调用LiuchengOperator中，送审人账号获取接口(OUT-03)
        Map<String,String> auditors = liuchengOperator.getApproveLeaderAll(userid);
        if (ToolUtil.isEmpty(auditors)){
            result = new RESTResultBean(500, "获取审核人信息失败");
        }else {
        	JSONObject date = new JSONObject();
        	date.put("auditors",auditors);
            result.setData(date);
        }
        return result;
    }

	/**
	 * 申请发送—>提交申请和保存
	 * @param condition
	 * @param request
	 * @return Type 0:代表提交 1：代表保存
	 *
	 * @author wangyue
	 * @date 2018-12-11
	 */
	@ApiOperation(value = "申请发送—>提交申请和保存")
	@RequestMapping(value = "/smsApplyAndSave",method = RequestMethod.POST)
	@ResponseBody
	public RESTResultBean SmsApplyAndSave(@Valid @RequestBody smsMatterCondition condition, HttpServletRequest request) {
		RESTResultBean result = new RESTResultBean(200, "success");
		smsMatter smsMatter = null;
		// 判断传入的类型和接收到参数是否为空
		if (ToolUtil.isEmpty(condition)||ToolUtil.isEmpty(condition.getType())){
			result.setCode(500);
			result.setMessage("请填写申请发送信息");
			return result;
		}
		if (0==condition.getType()){
			List<smsReceiver> smsReceiver = smsReceiverService.findAllSmsReceiver(condition.getUnid());
			if (ToolUtil.isEmpty(smsReceiver)){
				result.setCode(500);
				result.setMessage("请添加接收人");
				return result;
			}
		}
		smsMatter = smsMatterService.smsApplyAndSave(condition,request);
		try {
			if (0==condition.getType()){
				smsMatterService.sendApprove(smsMatter);
			}
		} catch (Exception e) {
			//推送失败
			result.setCode(500);
			result.setMessage("推送给流程平台失败");
			return result;
		}
		return result;
	}

    /**
     * 修改草稿状态的短信信息
     * @param unid
     * @return
     * @author lishuang
     * date 2019-04-28
     */
    @ApiOperation(value = "我的发送记录-->信息记录的列表-->草稿修改")
    @ResponseBody
    @GetMapping("/getSmsMatterDetail")
	public RESTResultBean updateSms(@RequestParam String unid){
        RESTResultBean result = new RESTResultBean(200, "success");
        //短信信息详情
        smsMatter smsMatter = smsMatterService.findByUnid(unid);
        //内部接收人
        List<smsReceiver> smsReceiversIn = smsReceiverService.findInSmsReceivers(unid);
        //内部接收人总数
        Integer totalIn = smsReceiverService.findInSmsReceiversTotal(unid);
        //外部接收人
        List<smsReceiver> smsReceiversOut = smsReceiverService.findOutSmsReceivers(unid);
        //外部接收人总数
        Integer totalOut = smsReceiverService.findOutSmsReceiverTotal(unid);
        //内部接收人所属部门、科室及相应总数
        Map<String,Object> data = smsReceiverService.findOmOrganizationNameAndTotal(unid);
        JSONObject object = new JSONObject();
        object.put("smsMatter",smsMatter);
        object.put("smsReceiversIn",smsReceiversIn);
        object.put("totalIn",totalIn);
        object.put("smsReceiversOut",smsReceiversOut);
        object.put("totalOut",totalOut);
        object.put("data",data);
        result.setData(object);
        return result;
    }

	/**
	 * 我的发送记录-->信息记录的列表-->查询/条件查询/分页
	 * 
	 * @param currentPage 当前页
	 * @param size 每页的大小
	 * @param smsMatterCondition 查询条件---页面传过来的是json
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "我的发送记录-->信息记录的列表-->查询/条件查询/分页")
	@ResponseBody
	@PostMapping("/matterPager")
	public RESTResultBean smsMatterPager(@RequestParam(value = "page", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
										 smsMatterCondition smsMatterCondition,HttpServletRequest request) {
		RESTResultBean result = new RESTResultBean(200, "success");
		String userid = SessionUtils.getUserId(request);
		List<smsMatterCondition> list = smsMatterService.getAllSmsMatter(currentPage, size,userid,smsMatterCondition);
		JSONObject date = new JSONObject();
		date.put("smsMatterCondition",list);
		result.setData(date);
		// 分页信息
		Pager pager = new Pager();
		pager.setCurrentPage(currentPage);
		pager.setSize(size);
		// 总数量
		pager.setTotal(smsMatterService.findSmsMatterTotal(userid,smsMatterCondition));
		result.setPager(pager);
		return result;
	}

	/**
	 * 我的发送记录-->申请信息详情
	 *
	 * @param unid 短信发送_申请表_主键
	 * @param currentPage
	 * @param size
	 * @return
     *
     * @author wangyue
     * @date 2018-12-10
	 */
	@ApiOperation(value = "我的发送记录-->申请信息详情")
	@ResponseBody
	@RequestMapping(value = "/SmsDetail", method = RequestMethod.GET)
	public RESTResultBean SmsMessageDetail(
			@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "unid",required = true)String unid) {
		RESTResultBean result = new RESTResultBean(200, "success");
		//标记该短信下是否有发送失败的接收人，默认为没有
		boolean isExistSendFailure = false;
		//获取短信信息详情
		smsMatter smsMatter = smsMatterService.findByUnid(unid);
		// 判断是否为空
		if (ToolUtil.isEmpty(smsMatter)) {
			result = new RESTResultBean(500, "该信息不存在");
			return result;
		}
		//获取unid下短信对应的接收人信息
		List<SmsReceiverResult> list = smsReceiverService.findByMatterUnid(currentPage, size, unid);
		if (ToolUtil.isNotEmpty(list)){
			for (SmsReceiverResult smsReceiverResult:list){
				if (smsReceiverResult.getStatus().equals("发送失败")){
					//如果有发送失败，值为YES
					isExistSendFailure=true;
					break;
				}
			}
		}
		JSONObject data = new JSONObject();
		data.put("smsMatter", smsMatter);
		data.put("smsReceiver", list);
		data.put("isExistSendFailure",isExistSendFailure);
		result.setData(data);
		Pager pager = new Pager();
		pager.setCurrentPage(currentPage);
		pager.setSize(size);
		pager.setTotal(smsReceiverService.findAllSmsReceiverTotal(unid));
		result.setPager(pager);
		return result;
	}

	/**
	 * 我的发送记录-->待发送信息的发送
	 *
	 * @author wangyue
	 * @date 2018-12-7
	 * @param unid 短信发送_申请表_主键
	 * @return
	 */
	@ApiOperation(value = "我的发送记录-->待发送信息的发送")
	@ResponseBody
	@GetMapping("/mySmsSend")
	public RESTResultBean MySmsSend(String unid) {
		RESTResultBean result = new RESTResultBean(200, "success");
		WMResult wmResult = smsMatterService.findByUnidAndUpdate(unid);
		// 判断是否为空
		if (ToolUtil.isEmpty(wmResult)||!wmResult.isSuccess()) {
			result = new RESTResultBean(500, "发送失败");
		}
		return result;
	}

	/**
	 * 申请发送-->定时发送任务
	 */
	@ApiOperation(value = "申请发送-->定时发送任务")
	@RequestMapping(value = "/scheduleSmsSend", method =  RequestMethod.POST)
	@ResponseBody
	public RESTResultBean scheduleSmsSend(){
		RESTResultBean result = new RESTResultBean(200, "sucess");
		smsTask.scheduleSmsSend();
		return result;
	}

	/**
	 * 按部门orgid添加接收人（方法测试接口）
	 * @param json
	 * @return
	 * @author lishuang
	 * @date 2019-04-23
	 */
	/*@ApiOperation(value = "getAll-查找当前部门下所有员工",
			notes = "{ \"org\":\"1494,1495\"}")
	@RequestMapping(value = "/getAll",method = RequestMethod.POST)
	@ResponseBody
	public RESTResultBean getAll(@RequestBody String json){
		RESTResultBean result = new RESTResultBean(200,"添加成功");
		JSONObject object = JSONObject.parseObject(json);
		if (ToolUtil.isNotEmpty(object)){
			String[] orgids = object.getString("org").split(",");
			if (ToolUtil.isNotEmpty(orgids)){
				for (String str : orgids){
					if (ToolUtil.isNotEmpty(str)){
						List<String> users = smsReceiverService.getAllOmUser(orgids);
						result.setData(users);
					}
				}
			}
		}
		return result;
	}*/

	/*@ApiOperation(value = "部门树")
	@RequestMapping(value = "/getAll", method =  RequestMethod.GET)
	@ResponseBody
	public RESTResultBean getAll(){
		RESTResultBean result = new RESTResultBean(200,"success");
		Map map = smsReceiverService.getAll();
		System.out.println(map);

		return result;
	}*/

}
