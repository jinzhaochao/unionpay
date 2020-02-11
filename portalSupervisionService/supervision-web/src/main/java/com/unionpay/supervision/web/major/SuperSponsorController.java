package com.unionpay.supervision.web.major;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.exception.MyException;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.domain.SuperConcerneLeadership;
import com.unionpay.supervision.domain.SuperService;
import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.domain.SuperSponsorLog;
import com.unionpay.supervision.model.SuperCondition;
import com.unionpay.supervision.model.SuperProject;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.supervision.service.SponsorServiceDetailService;
import com.unionpay.supervision.service.SuperConcerneLeadershipService;
import com.unionpay.supervision.service.SuperServiceService;
import com.unionpay.supervision.service.SuperSponsorLogService;
import com.unionpay.supervision.service.SuperSponsorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  督办部门Controller
 * </p>
 *
 * @author xiongym
 * @since 2018-11-07
 */
@Api(value = "SuperSponsorController", description = "督办部门操作Controller")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/sponsor")
public class SuperSponsorController {
	@Autowired
    private SuperSponsorService superSponsorService;
	@Autowired
    private OmUserService omUserService;
	@Autowired
    private SuperSponsorLogService superSponsorLogService;

    @Autowired
	private SponsorServiceDetailService superObjectService;
    @Autowired
    private SuperServiceService superServiceService;
    @Autowired
    private SuperConcerneLeadershipService superConcerneLeadershipService;
	
	/**
     * 批量创建后，事项分页查询
     */
    @ApiOperation(value="督办事项分页查询", notes="批量创建后，事项分页查询")
	@PostMapping("/selectPageSuperServiceByExcel")
	@ResponseBody
	public RESTResultBean selectPageService(@RequestParam(value="page",defaultValue="1") Integer page,
			@RequestParam(value="size",defaultValue="10") Integer size,String overseeName,HttpServletRequest request){   
		RESTResultBean result = new RESTResultBean(200,"suceess");
		SuperCondition superCondition = new SuperCondition();
		superCondition.setOverseeName(overseeName);
		superCondition.setStatus(2);
		//查询用户信息
    	String userId = SessionUtils.getUserId(request);
    	OmUser omUser= omUserService.findByUserid(userId);
    	if(omUser == null){
    		result.setCode(100);
    		result.setMessage("未登录");
    		return result;
    	}
    	superCondition.setCreateUserid(omUser.getUserid());
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setSize(size);
		List<SuperProject> list = superObjectService.getSuperCreateProjectFind(page, size, superCondition);
		pager.setTotal(superObjectService.getCountWithSuperCreateProjectFind(page, size, superCondition));
		result.setPager(pager);
		result.setData(list);
		return result;
	}

	
	/**
	 * 督办部门新增接口
	*/
	@ApiOperation(value="督办部门新增", notes="督办部门新增接口")
    @PostMapping("/addSuperSponsor")
    @ResponseBody
    public RESTResultBean addSuperSponsor(@RequestBody SuperSponsor superSponsor,HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		if(ToolUtil.isEmpty(superSponsor.getOrgId() )|| ToolUtil.isEmpty(superSponsor.getOrgName())){
			result.setCode(500);
			result.setMessage("orgName is null");
			return result;
		}
		if(ToolUtil.isEmpty(superSponsor.getServiceUnid())){
			result.setCode(500);
			result.setMessage("督办部门信息不全");
			return result;
		}
		
		//查询用户信息
    	String userId = SessionUtils.getUserId(request);
    	OmUser omUser= omUserService.findByUserid(userId);
    	superSponsor.setCreateUserid(omUser.getUserid());
    	superSponsor.setCreateUsername(omUser.getEmpname());
    	superSponsorService.add(superSponsor);
    	return result;
	}
	
	/**
	 * 督办部门编辑接口
	*/
	@ApiOperation(value="督办部门编辑", notes="督办部门编辑接口")
    @PostMapping("/editSuperSponsor")
    @ResponseBody
    public RESTResultBean editSuperSponsor(@RequestBody SuperSponsor superSponsor,HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		if(ToolUtil.isEmpty(superSponsor.getUnid())){
			result.setCode(500);
			result.setMessage("unid is null");
			return result;
		}
		
		//查询用户信息
		SuperSponsor select = superSponsorService.findById(superSponsor.getUnid());
		if(select == null){
			result.setCode(500);
			result.setMessage("unid is error");
			return result;
		}
    	superSponsorService.edit(superSponsor);
    	return result;
	}
	
	@ApiOperation(value="督办部门删除", notes="督办部门删除接口")
    @PostMapping("/deleteSuperSponsor")
    @ResponseBody
    public RESTResultBean deleteSuperSponsor(@RequestBody SuperSponsor superSponsor,HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		if(ToolUtil.isEmpty(superSponsor.getUnid())){
			result.setCode(500);
			result.setMessage("unid is null");
			return result;
		}
		
		//查询用户信息
		SuperSponsor select = superSponsorService.findById(superSponsor.getUnid());
		if(select == null){
			result.setCode(500);
			result.setMessage("unid is error");
			return result;
		}
    	superSponsorService.delete(superSponsor.getUnid());
    	return result;
	}
	/**
	 * 督办部门查询接口
	*/
	@ApiOperation(value="督办部门查询", notes="督办部门查询接口")
    @PostMapping("/selectSuperSponsor")
    @ResponseBody
    public RESTResultBean selectSuperSponsor(@RequestBody SuperSponsor superSponsor,HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		if(ToolUtil.isEmpty(superSponsor.getUnid())){
			result.setCode(500);
			result.setMessage("unid is null");
			return result;
		}
		
		//查询用户信息
		SuperSponsor select = superSponsorService.findById(superSponsor.getUnid());
		if(select == null){
			result.setCode(500);
			result.setMessage("unid is error");
			return result;
		}
		result.setData(select);
    	return result;
	}
	
	
	
	/**
	 * 事项督办接口
	 {
	  "id":["0ff4995c-9c8a-4544-83da-87d6637681d8","8f954bd1-c1c6-44b0-9e0c-0b6c3cb3ed70"],
	  "feedbackDeadline":"2018-12-12",
	  "feedbackRule":"反馈要求",
	  "verifiers":"3323,3325",
	  "needVerify":"Y",
	  "flowTitle":"测试"
	  }
	*/
	@ApiOperation(value="事项督办接口",notes = "{\"id\":[\"0ff4995c-9c8a-4544-83da-87d6637681d8\"],\"needVerify\":\"Y\""
			+ ",\"feedbackDeadline\":\"2018-12-12\",\"verifiers\":\"3323,3325\",\"flowTitle\":\"测试\"}")
    @PostMapping("/SuperServiceOperator")
    @ResponseBody
    public RESTResultBean SuperServiceOperator(@RequestBody String jsonString,HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		JSONObject json = JSONObject.parseObject(jsonString);
		JSONArray  id = json.getJSONArray("id");
		if(id == null || id.size() == 0){
			result.setCode(500);
			result.setMessage("unid 未选中");
			return result;
		}
		//要求反馈时间
		String feedbackDeadline = json.getString("feedbackDeadline");
		if(!DateUtil.isTrueDate(feedbackDeadline)){
			result.setCode(500);
			result.setMessage("时间不合法");
			return result;
		}
		//反馈要求-立项或督办时，弹出窗口去掉反馈要求--新需求-0410 lishuang
		//String feedbackRule = json.getString("feedbackRule");
		//是否送领导审批(是：Y ；否：N)',
		String needVerify = json.getString("needVerify");
		if(!"Y".equals(needVerify)){
			needVerify = "N";//--------督办时，若不需要领导审批----lishuang
		}
		//审批领导
		String verifiers = json.getString("verifiers");
		//事项名称--新需求-0403 lishuang
		String flowTitle = json.getString("flowTitle");
		//督办
		List<SuperSponsor> selectlist = new ArrayList<SuperSponsor>();
		for(Object unid : id){
			//查询
			SuperSponsor select = superSponsorService.findById(unid.toString());
			if(select == null || select.getStatus() != 1){
				result.setCode(500);
				result.setMessage("unid is error");
				return result;
			}
			//督办中的事项，不可以再次督办--新需求-0402 lishuang
			if (ToolUtil.isEmpty(select.getWorkStatus())){
				result.setCode(500);
				result.setMessage("事项："+select.getServiceId()+"的工作状态为null");
				return result;
			}
			boolean a = "立项".equals(select.getServiceStatus())||"督办".equals(select.getServiceStatus()) || "中止".equals(select.getServiceStatus());
			boolean b = select.getWorkStatus().matches("推进(.*)") || select.getWorkStatus().matches("工作中止");
			boolean c = ToolUtil.isNotEmpty(select.getFeedbackTime());
			if (a && b && c){
				select.setFeedbackDeadline(feedbackDeadline);
				//立项或督办时，弹出窗口去掉反馈要求--新需求-0410 lishuang
				//select.setFeedbackRule(feedbackRule);
				select.setNeedVerify(needVerify);
				select.setVerifiers(verifiers);
				//督办开始时间
				select.setOverseeTime(DateUtil.getDay());
				//下次督办时间
				SuperService service = superServiceService.findByUnid(select.getServiceUnid());
				if(service != null && service.getOverseeFrequency() != null){
					int overseeFrequency = service.getOverseeFrequency();
					int day  = overseeFrequency * 7;
					select.setNextTime(DateUtil.getAfterDayDate(day+"").substring(0, 10));
				}
			}else {
				result.setCode(500);
				result.setMessage("事项编号为："+select.getServiceId()+"的事项不符合督办要求");
				return result;
			}
			/*select.setFeedbackDeadline(feedbackDeadline);
			select.setFeedbackRule(feedbackRule);
			select.setNeedVerify(needVerify);
			select.setVerifiers(verifiers);
			//督办开始时间
			select.setOverseeTime(DateUtil.getDay());
			//下次督办时间
			SuperService service = superServiceService.findByUnid(select.getServiceUnid());
			if(service != null && service.getOverseeFrequency() != null){
				int overseeFrequency = service.getOverseeFrequency();
				int day  = overseeFrequency * 7;
				select.setNextTime(DateUtil.getAfterDayDate(day+"").substring(0, 10));
			}*/
			selectlist.add(select);
		}
		Boolean feekback = null;
		//执行,1为立项；2为督办
		try {
			feekback = superSponsorService.SuperServiceOperator(selectlist, 2,null,flowTitle);
		} catch (Exception e) {
			result.setCode(500);
			result.setMessage("推送给流程平台失败");
			return result;
		}
		if(feekback == null || !feekback){
			result.setCode(500);
			result.setMessage("推送给流程平台失败2");
			return result;
		}
    	return result;
	}
	/**
	 * 批量导入后，督办部门事项批量操作;
	 * {
	  "id":["0ff4995c-9c8a-4544-83da-87d6637681d8","8f954bd1-c1c6-44b0-9e0c-0b6c3cb3ed70"],
	  "isSave":false,
	 "isDelete":false,
	  "isBuild":true
	  }
	*/
	@ApiOperation(value="督办部门事项批量操作", notes = "{\"id\":[\"0ff4995c-9c8a-4544-83da-87d6637681d8\"],"
			+ "\"isSave\":\"false\" ,\"isBuild\":\"true\",\"isDelete\":\"false\",\"needVerify\":\"Y\",\"verifiers\":\"3323,3325\","
			+ "\"feedbackDeadline\":\"2018-12-12\",\"flowTitle\":\"测试\"}")
    @PostMapping("/SuperServiceBatchOperate")
    @ResponseBody
    public RESTResultBean SuperServiceBatchOperate(@RequestBody String jsonString,HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		JSONObject json = JSONObject.parseObject(jsonString);
		JSONArray  ids = json.getJSONArray("id");
		Boolean isSave = json.getBoolean("isSave");
		Boolean isDelete = json.getBoolean("isDelete");
		Boolean isBuild = json.getBoolean("isBuild");
		//批量立项，新增弹出框，含是否送领导审批及标题（取选中的第一条serviceName）--新需求-0403 lishuang
		//是否送领导审批(是：Y ；否：N)
		String needVerify = json.getString("needVerify");
		//审批领导
		String verifiers = json.getString("verifiers");
		//反馈要求--立项或督办时，弹出窗口去掉反馈要求--新需求-0410 lishuang
		//String feedbackRule = json.getString("feedbackRule");
		//要求反馈时间
		String feedbackDeadline = json.getString("feedbackDeadline");
		//事项名称
		String flowTitle = json.getString("flowTitle");
		if(ids == null || ids.size() == 0){
			result.setCode(500);
			result.setMessage("请勾选相关选项");
			return result;
		}
		if(isSave != null && isSave){
			//保存
			for(Object obj :ids){
				String sponsorUnid = null;
				sponsorUnid = obj.toString();
				//查询
				SuperSponsor select = superSponsorService.findById(sponsorUnid);
				if(select != null ){
					//保存
					select.setStatus(1);
			    	superSponsorService.edit(select);
				}
			}
		}else if (isBuild != null && isBuild){
			//立项
			List<SuperSponsor> selectlist = new ArrayList<SuperSponsor>();
			for(Object obj :ids){
				String sponsorUnid = null;
				sponsorUnid = obj.toString();
				//查询
				SuperSponsor select = superSponsorService.findById(sponsorUnid);
				if(select == null || select.getStatus() == 0){
					result.setCode(500);
					result.setMessage("unid is error");
					return result;
				}
				//下次督办时间
				SuperService service = superServiceService.findByUnid(select.getServiceUnid());
				if(service != null && service.getOverseeFrequency() != null){
					int overseeFrequency = service.getOverseeFrequency();
					int day  = overseeFrequency * 7;
					select.setNextTime(DateUtil.getAfterDayDate(day+"").substring(0, 10));
				}
				select.setStatus(1);
				select.setFeedbackDeadline(feedbackDeadline);
				//立项或督办时，弹出窗口去掉反馈要求--新需求-0410 lishuang
				//select.setFeedbackRule(feedbackRule);
				select.setNeedVerify(needVerify);
				select.setVerifiers(verifiers);
				selectlist.add(select);
				
			}
			//批量立项时，判断是否都需要领导审批，审批领导是否为同一个，否则提示（审核人不是同一人）----lishuang
			/*if (ToolUtil.isNotEmpty(selectlist) && selectlist.size()>1){
				List<String> verifies = new ArrayList<>();
				for (SuperSponsor superSponsor : selectlist){
					if ("N".equals(superSponsor.getNeedVerify())){
						continue;
					}
					if (!verifies.contains(superSponsor.getVerifiers())){
						verifies.add(superSponsor.getVerifiers());
					}
				}
				if (verifies.size()>1){
					result.setCode(500);
					result.setMessage("审批领导必须是同一个人");
					return result;
				}
			}*/
			Boolean feekback = null;
			//立项
			try {
				feekback = superSponsorService.SuperServiceOperator(selectlist, 1,null,flowTitle);
			} catch (Exception e) {
				result.setCode(500);
				result.setMessage("推送给流程平台失败");
				return result;
			}
			if(feekback == null || !feekback){
				result.setCode(500);
				result.setMessage("推送给流程平台失败2");
				return result;
			}
		}else if (isDelete != null && isDelete){
			//删除
			for(Object obj :ids){
				String sponsorUnid = null;
				sponsorUnid = obj.toString();
				//查询
				SuperSponsor select = superSponsorService.findById(sponsorUnid);
				if(select != null){
					//删除
					superSponsorService.delete(sponsorUnid);
				}
			}
		}
		
    	return result;
	}
	
	/**
	 * 查看历史信息
	 * @param unid
	 * @return
	 */
	@ApiOperation(value="督办事项查看历史",notes="查看历史")
	@ResponseBody
	@GetMapping("/historyMessage")
	public RESTResultBean SuperSponsorLog(String unid)
	{    
		RESTResultBean restResultBean = null;
		if(unid!=null)
		{
	    restResultBean = new RESTResultBean(200,"成功");
	    JSONObject jsonObject = new JSONObject();
	    LinkedList<SuperSponsorLog> loglist = superSponsorLogService.findBySponsorUnid(unid);
	    if(loglist != null && loglist.size() > 0){
	    	loglist.remove(0);
	    }
	    jsonObject.put("superSponsorLog",loglist);
	    restResultBean.setData(jsonObject);
		}else{
			restResultBean = new RESTResultBean(500,"失败");
		}
		return restResultBean;
	}
	
	
	/**
	 * 领导事项关注接口
	 {
	  "leaderUserid":["2736","2736"],
	  "sponsorUnid":"10835c2e-b36d-4e89-98de-135136120b73"
	  }
	*/
	@ApiOperation(value="领导事项关注接口", notes = "{\"leaderUserid\":[\"2736\"],"
			+ "\"sponsorUnid\":\"10835c2e-b36d-4e89-98de-135136120b73\" }")
    @PostMapping("/addSuperConcerneLeadership")
    @ResponseBody
    public RESTResultBean addSuperConcerneLeadership(@RequestBody String jsonString,HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		JSONObject json = JSONObject.parseObject(jsonString);
		JSONArray  leaderUserids = json.getJSONArray("leaderUserid");
		if(leaderUserids == null || leaderUserids.size() == 0){
			result.setCode(500);
			result.setMessage("unid 未选中");
			return result;
		}
		//主办部门id
		String sponsorUnid = json.getString("sponsorUnid");
		if(ToolUtil.isEmpty(sponsorUnid)){
			result.setCode(500);
			result.setMessage("sponsorUnid is null");
			return result;
		}
		//查询
		SuperSponsor sponsor = superSponsorService.findById(sponsorUnid);
		if(sponsor == null){
			result.setCode(500);
			result.setMessage("sponsorUnid is error");
			return result;
		}
		SuperService service = superServiceService.findByUnid(sponsor.getServiceUnid());
		if(service == null){
			result.setCode(500);
			result.setMessage("数据错误");
			return result;
		}
		for(Object leaderUserid : leaderUserids){
			SuperConcerneLeadership lead = new SuperConcerneLeadership();
			lead.setId(UUID.randomUUID().toString());
			lead.setSponsorUnid(sponsorUnid);
			lead.setOrgId(sponsor.getOrgId());
			lead.setOrgName(sponsor.getOrgName());
			lead.setWorkStatus(sponsor.getWorkStatus());
			lead.setServiceStatus(sponsor.getServiceStatus());
			lead.setServiceUnid(sponsor.getServiceUnid());
			lead.setServiceName(service.getServiceName());
			lead.setServiceLevel(service.getServiceLevel());
			lead.setServiceType(service.getServiceType());
			lead.setOverseeUserid(service.getOverseeUserid());
			lead.setOverseeUsername(service.getOverseeUsername());
			String userid = leaderUserid.toString();
			lead.setLeaderUserid(userid);
			//Integer empid = Integer.parseInt(leaderUserid.toString());
			OmUser user = omUserService.findByUserid(userid);
			lead.setLeaderUsername(user.getEmpname());
			lead.setCreateTime(new Date());
			lead.setStatus(1);
			
			superConcerneLeadershipService.insertOrUpdata(lead);
		}
    	return result;
	}
	

}
