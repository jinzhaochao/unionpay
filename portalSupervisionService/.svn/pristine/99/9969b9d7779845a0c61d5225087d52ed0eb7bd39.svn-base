package com.unionpay.supervision.web.major;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.unionpay.common.exception.MyException;
import com.unionpay.common.util.DateUtil;
import com.unionpay.supervision.bussniss.LiuchengOperator;
import com.unionpay.supervision.dao.SuperLcSponsorRepository;
import com.unionpay.supervision.domain.*;
import com.unionpay.supervision.filter.submit.NoRepeatSubmit;

import com.unionpay.supervision.task.SponsorTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.bussniss.CommonOperator;
import com.unionpay.supervision.dao.SuperTypeOverseeRepository;
import com.unionpay.supervision.model.SuperServiceInfo;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.supervision.service.SuperFileService;
import com.unionpay.supervision.service.SuperServiceIdCreateService;
import com.unionpay.supervision.service.SuperServiceOverseeMappingService;
import com.unionpay.supervision.service.SuperServiceService;
import com.unionpay.supervision.service.SuperSponsorService;
import com.unionpay.supervision.supportController.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  督办事项Controller
 * </p>
 *
 * @author xiongym
 * @since 2018-11-07
 */
@Api(value = "ServiceController", description = "督办事项Controller")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/service")
public class ServiceController extends BaseController{
	
	@Autowired
    private OmUserService omUserService;
	@Autowired
    private SuperServiceService superServiceService;
	@Autowired
    private SuperFileService superFileService;
	@Autowired
    private SuperSponsorService superSponsorService;
	@Autowired
    private SuperServiceOverseeMappingService superServiceOverseeMappingService;
	@Autowired
	private CommonOperator commonOperator;
	@Autowired
	private SuperTypeOverseeRepository superTypeOverseeRepository;
	@Autowired
	private SuperServiceIdCreateService superServiceIdCreateService;
	@Autowired
	private SuperLcSponsorRepository superLcSponsorRepository;
	@Autowired
	private LiuchengOperator liuchengOperator;
	@Autowired
	private SponsorTask sponsorTask;

	   
    /**
     * 督办事项新增,新建录入接口
	*/
    @ApiOperation(value="督办事项新增", notes="新建录入接口")
    @PostMapping("/addSuperService")
    @ResponseBody
    @NoRepeatSubmit
    public RESTResultBean addSuperService(@Valid @RequestBody SuperServiceInfo superServiceInfo,BindingResult bindingResult,HttpServletRequest request){
    	RESTResultBean result = new RESTResultBean(200 ,"success");
    	verify(bindingResult, request, superServiceInfo);
    	//查询用户信息
    	String userId = SessionUtils.getUserId(request);
    	OmUser omUser= omUserService.findByUserid(userId);
    	if(omUser == null){
    		result.setCode(100);
    		result.setMessage("未登录");
    		return result;
    	}
    	superServiceInfo.setCreateUserid(omUser.getUserid());
    	superServiceInfo.setCreateUsername(omUser.getEmpname());
    	//督办人未指定，默认为登录人
    	if(ToolUtil.isEmpty(superServiceInfo.getOverseeUserid())){
    		superServiceInfo.setOverseeUserid(omUser.getUserid());
    		superServiceInfo.setOverseeUsername(omUser.getEmpname());
    	}
    	//判断请求是提交or保存
    	//Integer isSave = superServiceInfo.getIsSave();
    	/*Integer isSubmit = superServiceInfo.getIsSubmit();
    	if(isSubmit != null && isSubmit == 1){
    		superServiceInfo.setServiceStatus("立项");
    	}else{}*/
		//提交，为草稿状态
    	superServiceInfo.setServiceStatus("草稿");
    	//督办事项保存
    	result = superServiceService.saveAll(superServiceInfo);
    	return result;
    }
    
    /**
     * 督办事项id来获取事项详细信息
     * @param unid 主办单位unid
	*/
    @ApiOperation(value="督办事项详情查询", notes="督办事项id来获取事项详细信息")
    @GetMapping("/querySuperService")
    @ResponseBody
    public RESTResultBean querySuperService(@RequestParam("unid") String unid,HttpServletRequest request){
    	RESTResultBean result = new RESTResultBean(200 ,"success");
    	//主办单位查询
    	SuperSponsor sponsor = superSponsorService.findById(unid);
    	if(sponsor == null){
    		result.setCode(500);
    		result.setMessage("unid is error");
    		return result;
    	}
    	String serviceUnid = sponsor.getServiceUnid();
    	SuperService superService = superServiceService.findByUnid(serviceUnid);
    	if(superService == null){
    		result.setCode(500);
    		result.setMessage("事项不存在");
    		return result;
    	}
    	SuperServiceInfo superServiceInfo = superServiceService.translateSuperServiceInfo(superService);
    	
    	//督办部门
    	List<SuperSponsor> superSponsors= superSponsorService.findByServiceUnid(serviceUnid,1);
    	superServiceInfo.setSuperSponsor(superSponsors);
    	//督办事项与督办类型关联
    	List<SuperServiceOverseeMapping> superServiceOverseeMappings = 
    			superServiceOverseeMappingService.findByServiceUnidAndIsPrimary(serviceUnid,null);
    	if(superServiceOverseeMappings != null){
    		superServiceInfo.setSuperServiceOverseeMapping(superServiceOverseeMappings);
    	}
    	//附件
    	List<SuperFile> superFiles = superFileService.findAllBySuperServiceId(serviceUnid); 
    	Map<String ,Object> superFile = new HashMap<String ,Object>();
    	if(superFiles != null && superFiles.size() > 0){
    		for(SuperFile file : superFiles){
    			if(file != null && ToolUtil.isNotEmpty(file.getOverseeMappingId())){
    				superFile.put(file.getOverseeMappingId(), file);
    			}
    		}
    	}
    	superServiceInfo.setSuperFileMap(superFile);
    	result.setData(superServiceInfo);
    	return result;
    }
    
    /**
     * 督办事项下拉列表字段选项
	*/
    @ApiOperation(value="督办事项下拉列表字段选项")
    @GetMapping("/selectMap")
    @ResponseBody
    public RESTResultBean selectMap(){
    	RESTResultBean result = new RESTResultBean(200 ,"success");
    	//查询selectMap
    	JSONObject selectMap = commonOperator.getSelectMap();
    	result.setData(selectMap);
    	return result;
    }

	/**
	 * 督办事项下拉列表字段选项---流程平台数据
	 */
	@ApiOperation(value="督办事项下拉列表字段选项(流程平台数据)")
	@GetMapping("/liuchengSelectMap")
	@ResponseBody
	public RESTResultBean liuchengSelectMap1(){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		//查询selectMap
		JSONObject selectMap = commonOperator.getLiuichengSelectMap();
		Map<String,String> sponsors = commonOperator.getSuperLcSponsor();
		selectMap.put("orgMange",sponsors);
		result.setData(selectMap);
		return result;
	}

	/**
	 * 获取流程平台主办部门数据，存入本地数据库（定时任务，每天更新）
	 * @author lishuang
	 * @date 2019-03-22
	 */
	@ApiOperation(value="主办部门存入本地(流程平台数据)")
	@PostMapping("/saveSponsorLcSponsor")
	@ResponseBody
	public RESTResultBean saveSponsorLcSponsor(){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		sponsorTask.saveSponsorLcSponsor();
		return result;
	}
    
    /**
     * 查询公司领导
	*/
    @ApiOperation(value="查询公司领导")
    @GetMapping("/selectCommandLeader")
    @ResponseBody
    public RESTResultBean selectCommandLeader(){
    	RESTResultBean result = new RESTResultBean(200 ,"success");
    	//查询selectMap
    	Map<String, String> commandLeader = commonOperator.selectCommandLeader();
    	result.setData(commandLeader);
    	return result;
    }
    
    /**
     * 督办事项编辑接口
	*/
    @ApiOperation(value="督办事项编辑", notes="督办事项编辑接口")
    @PostMapping("/editSuperService")
    @ResponseBody
    @NoRepeatSubmit
    public RESTResultBean editSuperService(@Valid @RequestBody SuperServiceInfo superServiceInfo,BindingResult bindingResult,HttpServletRequest request){
    	RESTResultBean result = new RESTResultBean(200 ,"success");
    	verify(bindingResult, request, superServiceInfo);
    	if(ToolUtil.isEmpty(superServiceInfo.getUnid())){
    		result.setCode(500);
    		result.setMessage("unid is null");
    		return result;
    	}
    	//查询用户信息
    	String userId = SessionUtils.getUserId(request);
    	OmUser omUser= omUserService.findByUserid(userId);
    	superServiceInfo.setCreateUserid(omUser.getUserid());
    	superServiceInfo.setCreateUsername(omUser.getEmpname());
    	// isSubmit（0->未点击提交 ;1-> 立项  ;2 -> 督办）
    	//Integer isSave = superServiceInfo.getIsSave();
    	/*Integer isSubmit = superServiceInfo.getIsSubmit();
    	if(isSubmit != null && isSubmit == 1){}*/
    	//提交，为草稿状态
		superServiceInfo.setServiceStatus("立项");
    	//督办事项保存
    	result = superServiceService.editSuperService(superServiceInfo);
    	return result;
    }

	/**
	 * 新建或编辑页，提交弹框后，立项
	 * @param jsonString
	 * @return
	 */
	@ApiOperation(value="新建或编辑页，弹框后立项",notes = "{\"unid\":\"0ff4995c-9c8a-4544-83da-87d6637681d8\",\"needVerify\":\"Y\""
			+ ",\"feedbackDeadline\":\"2018-12-12\",\"verifiers\":\"3323,3325\",\"flowTitle\":\"测试\"}")
	@PostMapping("/superServiceOperator")
	@ResponseBody
    public RESTResultBean superServiceOperator(@RequestBody String jsonString){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		JSONObject json = JSONObject.parseObject(jsonString);
		String unid = json.getString("unid");
		if(ToolUtil.isEmpty(unid)){
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
		//是否送领导审批(是：Y ；否：N)',
		String needVerify = json.getString("needVerify");
		if(!"Y".equals(needVerify)){
			needVerify = "N";
		}
		//审批领导
		String verifiers = json.getString("verifiers");
		//事项名称--新需求-0403 lishuang
		String flowTitle = json.getString("flowTitle");
		SuperService superService = superServiceService.findByUnid(unid);
		List<SuperSponsor> superSponsorList = superSponsorService.findByServiceUnid(superService.getUnid(),null);
		List<SuperSponsor> selectList = new ArrayList<>();
		if (ToolUtil.isNotEmpty(superSponsorList)){
			for (SuperSponsor superSponsor : superSponsorList){
			    superSponsor.setFeedbackDeadline(feedbackDeadline);
				superSponsor.setNeedVerify(needVerify);
				superSponsor.setVerifiers(verifiers);
				selectList.add(superSponsor);
			}
		}
		List<SuperServiceOverseeMapping> superServiceOverseeMappingList =
				superServiceOverseeMappingService.findAllByServiceUnid(superService.getUnid());
		superSponsorService.SuperServiceOperator(selectList,superServiceOverseeMappingList,superService,
				1,superService.getOverseeFrequency(),flowTitle);
		return result;
	}
    
    /**
     * 督办事项编辑生成
	*/
    @ApiOperation(value="督办事项编辑生成", notes="督办事项编辑随督办类型变化")
    @ApiImplicitParam(name = "serviceIdCreate", value = "督办事项综合类", required = true, dataType = "String")
    @PostMapping("/serviceIdCreate")
    @ResponseBody
    public RESTResultBean serviceIdCreate(@RequestParam("unid") String unid,HttpServletRequest request){
    	RESTResultBean result = new RESTResultBean(200 ,"success");
    	if(ToolUtil.isEmpty(unid)){
    		result.setCode(500);
    		result.setMessage("unid is null");
    		return result;
    	}
    	SuperTypeOversee superTypeOversee = superTypeOverseeRepository.findByUnid(unid);
    	if(superTypeOversee == null){
    		result.setCode(500);
    		result.setMessage("unid is error");
    		return result;
    	}
    	//生成
    	String overseeTypeId = superTypeOversee.getTypeId();
    	SimpleDateFormat ft = new SimpleDateFormat ("yyyy");
    	String year = ft.format(new Date());
    	Integer yearTo = Integer.parseInt(year.substring(2));
    	String serviceId = superServiceIdCreateService.geSuperServiceId(overseeTypeId, yearTo);
    	JSONObject data = new JSONObject();
    	data.put("serviceId", serviceId);
    	result.setData(data);
    	return result;
    }
}