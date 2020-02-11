package com.unionpay.supervision.web.major;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.log.LogManager;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.domain.*;
import com.unionpay.supervision.log.LogTaskFactory;
import com.unionpay.supervision.model.ServiceModel;
import com.unionpay.supervision.model.SponsorSearchDto;
import com.unionpay.supervision.model.SuperOverseeMappingDto;
import com.unionpay.supervision.model.SuperServiceModelDto;
import com.unionpay.supervision.service.*;
import com.unionpay.supervision.supportController.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  事项搜索查询Controller
 * </p>
 *
 * @author xiongym
 * @since 2018-12-27
 */
@Api(value = "SponsorSearchController", description = "事项搜索查询")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/sponsorSearch")
public class SponsorSearchController extends BaseController{
	
	@Autowired
    private SponsorSearchService sponsorSearchService;
	@Autowired
    private SuperSponsorService superSponsorService;
	@Autowired
	private SuperServiceService superServiceService;
	@Autowired
	private SuperServiceOverseeMappingService superServiceOverseeMappingService;
	@Autowired
	private SuperSponsorLogService superSponsorLogService;
	
	/**
	 * 事项搜索查询
	 */
	@ApiOperation(value = "事项搜索查询")
	@PostMapping("/findSupervisionlist")
	@ResponseBody
	public RESTResultBean findSupervisionlist(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, SponsorSearchDto sponsorSearchDto) {
		RESTResultBean result = new RESTResultBean(200, "suceess");
		
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setSize(size);
		List<ServiceModel> list = sponsorSearchService.findSuperVisionlist(page, size, sponsorSearchDto);
		pager.setTotal(sponsorSearchService.getSuperVisioncount(sponsorSearchDto));
		result.setPager(pager);
		result.setData(list);
		return result;
	}

	/**
	 * 事项unid回显
	 * jinzhao  2019-11-22
	 */
	@ApiOperation(value = "事项unid回显")
	@PostMapping("/selectOne")
	@ResponseBody
	public RESTResultBean findSupervisionlist(@RequestParam String unid) {
		RESTResultBean result = new RESTResultBean(200, "suceess");

		SuperSponsor superSponsor = superSponsorService.findById(unid);
		result.setData(superSponsor);
		return result;
	}

	/**
	 * 事项信息
	 * @param unid
	 * @return
	 * @author lishuang
	 * @date 2019-04-09
	 */
	@ApiOperation(value = "事项信息")
	@GetMapping("/getSuperService")
	@ResponseBody
	public RESTResultBean getSuperService(@RequestParam(value = "unid") String unid){
		RESTResultBean result = new RESTResultBean(200, "suceess");
		SuperService superService = null;
		SuperSponsor superSponsor = superSponsorService.findById(unid);
		if (ToolUtil.isEmpty(superSponsor)){
			result.setCode(500);
			result.setMessage("事项不存在");
			return result;
		}
		superService = superServiceService.findByUnid(superSponsor.getServiceUnid());
		if (ToolUtil.isNotEmpty(superService)){
			List<SuperOverseeMappingDto> superOverseeMappingDto =
					superServiceOverseeMappingService.findByServiceUnid(superService.getUnid());
			SuperServiceModelDto superServiceModelDto = new SuperServiceModelDto();
			superServiceModelDto.setBranchedLeader(superService.getBranchedLeader());
			superServiceModelDto.setOverseeFrequency(superService.getOverseeFrequency());
			superServiceModelDto.setServiceType(superService.getServiceType());
			superServiceModelDto.setUnid(superService.getUnid());
			superServiceModelDto.setSuperOverseeMappingDto(superOverseeMappingDto);
			result.setData(superServiceModelDto);
		}
		return result;
	}

	/**
	 * 修改事项
	 * @param superServiceModelDto
	 * @return
	 * @author lishuang
	 * @date 2019-04-09
	 */
	@ApiOperation(value = "修改事项")
	@PostMapping("/editSuperService")
	@ResponseBody
	public RESTResultBean editSuperService(@Valid @RequestBody SuperServiceModelDto superServiceModelDto){
		RESTResultBean result = new RESTResultBean(200, "suceess");
		//事项保存
		SuperService superService = superServiceService.findByUnid(superServiceModelDto.getUnid());
		if (ToolUtil.isEmpty(superService)){
			result.setCode(500);
			result.setMessage("数据不存在");
			return result;
		}
		sponsorSearchService.editSuperService(superServiceModelDto);
		return result;
	}
	
	/**
	 * 事项状态修改
	 {
	  "id":["0ff4995c-9c8a-4544-83da-87d6637681d8","8f954bd1-c1c6-44b0-9e0c-0b6c3cb3ed70"],
	   "workStatus":"工作中止"
	   }
	 */
	@ApiOperation(value = "事项状态修改", notes = "{\"id\":[\"0ff4995c-9c8a-4544-83da-87d6637681d8\"],"
			+ "\"workStatus\":\"工作中止\"}")
	@PostMapping("/updateSupervisionStatus")
	@ResponseBody
	public RESTResultBean updateSupervisionStatus(@RequestBody String jsonString) {
		RESTResultBean result = new RESTResultBean(200, "suceess");
		JSONObject json = JSONObject.parseObject(jsonString);
		JSONArray  ids = json.getJSONArray("id");
		String workStatus = json.getString("workStatus");
		if(ids == null || ids.size() == 0){
			result.setCode(500);
			result.setMessage("请勾选相关选项");
			return result;
		}
		if(ToolUtil.isEmpty(workStatus)){
			result.setCode(500);
			result.setMessage("请指定推进状态");
			return result;
		}
		String serviceStatus = null;
		if(workStatus.contains("推进")){
			serviceStatus = "督办";
		}else if(workStatus.equals("工作中止")){
			serviceStatus = "中止";
		}else if(workStatus.equals("已完成")){
			serviceStatus = "完成";
		}
		for(Object obj :ids){
			String sponsorUnid = null;
			sponsorUnid = obj.toString();
			//查询
			SuperSponsor select = superSponsorService.findById(sponsorUnid);
			if(select != null){
				//保存
				if(serviceStatus != null){
					select.setServiceStatus(serviceStatus);
				}
				//修改状态为工作中止时,同时修改下次督办时间为:9999-01-01 --新需求-0408 lishuang
				if ("工作中止".equals(workStatus)){
					select.setNextTime("9999-01-01");
				}
				select.setWorkStatus(workStatus);
		    	superSponsorService.edit(select);
			}
		}
		return result;
	}

	/**
	 * 修改立项信息
	 *
	 * @author jinzhao
	 * @Date 2019-10-08
	 */
	@ApiOperation(value = "修改立项信息", notes = "{\"id\":\"0ff4995c-9c8a-4544-83da-87d6637681d8\"," + "\"			targetServiceId\":\"HY-19-001-A\"}")
	@PostMapping("/updateSupervisionPlan")
	@ResponseBody
	public RESTResultBean supervisionPlan(@RequestBody SuperSponsorDto superSponsorDto) {
		RESTResultBean result = new RESTResultBean(200, "success");
		try {

			SuperSponsor sponsor = superSponsorService.findById(superSponsorDto.getUnid());
			sponsor.setProposedClosingTime(superSponsorDto.getProposedClosingTime());
			sponsor.setResultForm(superSponsorDto.getResultForm());
			sponsor.setWorkPlan(superSponsorDto.getWorkPlan());
			superSponsorService.edit(sponsor);
			// 督办记录 --不添加日志 2019-12-5 jinzhao
//            superSponsorLogService.add(sponsor);
			// 督办日志
			SuperService superService = superServiceService.findByUnid(sponsor.getServiceUnid());
			LogManager.me().executeLog(LogTaskFactory.systemLog(sponsor.getUnid(), sponsor.getServiceUnid(),
					JSONObject.toJSONString(superSponsorService.findById(superSponsorDto.getUnid())), JSONObject.toJSONString(sponsor), 1, 8, sponsor.getSponsorId(), superService.getOverseeUserid()));
		} catch (RuntimeException e) {
			result.setCode(500);
			result.setMessage("操作失败");
		}
		return result;
	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 事项添加进展
	 *
	 * @author jinzhao
	 * @Date 2019-10-09
	 */
	@ApiOperation(value = "添加进展", notes = "{\"id\":\"0ff4995c-9c8a-4544-83da-87d6637681d8\"," + "\"			targetServiceId\":\"HY-19-001-A\"}")
	@PostMapping("/addProgress")
	@ResponseBody
	public RESTResultBean supervisionProgress(@RequestBody SuperSponsorModel superSponsorModel) {
		RESTResultBean result = new RESTResultBean(200, "success");
		logger.info(superSponsorModel+"======================");
		try {
			SuperSponsor sponsor = superSponsorService.findById(superSponsorModel.getUnid());
			SuperService service = superServiceService.findByUnid(sponsor.getServiceUnid());
			String status = "";
			if (ToolUtil.isNotEmpty(sponsor.getWorkStatus())) {
				status = sponsor.getWorkStatus();
			}
			if (ToolUtil.isNotEmpty(superSponsorModel.getWorkStatus())) {
				if ("工作中止".equals(superSponsorModel.getWorkStatus())) {
					superSponsorModel.setWorkStatus("工作中止");
					superSponsorModel.setServiceStatus("中止");
				} else if ("已完成".equals(superSponsorModel.getWorkStatus())) {
					superSponsorModel.setWorkStatus("已完成");
					superSponsorModel.setServiceStatus("完成");
				} else if ("推进中,有阶段性进展".equals(superSponsorModel.getWorkStatus())) {
					superSponsorModel.setWorkStatus("推进中,有阶段性进展");
					superSponsorModel.setServiceStatus("督办");
				} else if ("推进中,暂无阶段性进展".equals(superSponsorModel.getWorkStatus())) {
					superSponsorModel.setWorkStatus("推进中,暂无阶段性进展");
					superSponsorModel.setServiceStatus("督办");
				} else if ("工作终止".equals(superSponsorModel.getWorkStatus())) {
					superSponsorModel.setWorkStatus("工作终止");
					superSponsorModel.setServiceStatus("中止");
				}

			}
			//反馈时间加时分秒
			if (ToolUtil.isNotEmpty(superSponsorModel.getFeedbackTime())) {
				superSponsorModel.setFeedbackTime(superSponsorModel.getFeedbackTime() + " " + (new SimpleDateFormat("HH:mm:ss").format(new Date())));
			}
			sponsor.setSponsorId(superSponsorModel.getSponsorId());
			sponsor.setSponsorName(superSponsorModel.getSponsorName());
			sponsor.setFeedbackUserid(superSponsorModel.getFeedbackUserid());
			sponsor.setFeedbackUsername(superSponsorModel.getFeedbackUsername());
			//要求反馈时间
			sponsor.setFeedbackDeadline(superSponsorModel.getFeedbackTime());
			//督办开始时间
			sponsor.setOverseeTime(superSponsorModel.getFeedbackTime());
			//下次督办时间
			sponsor.setNextTime(DateUtil.addDate(superSponsorModel.getFeedbackTime(),service.getOverseeFrequency() * 7 + ""));
			sponsor.setFeedbackTime(superSponsorModel.getFeedbackTime());
			sponsor.setWorkStatus(superSponsorModel.getWorkStatus());
			sponsor.setServiceStatus(superSponsorModel.getServiceStatus());
			sponsor.setProgress(superSponsorModel.getProgress());
			sponsor.setNote(superSponsorModel.getNote());
			if (superSponsorModel.getType() == 2 && ToolUtil.isNotEmpty(status)) {
				//推进状态不为空就把日志表中最新一条内容修改
				List<SuperSponsorLog> superSponsorLogList = superSponsorLogService.findBySponsorUnid(superSponsorModel.getUnid());
				for (int i = 0; i < superSponsorLogList.size(); i++) {
					SuperSponsorLog superSponsorLog = superSponsorLogList.get(0);
					String workStatus = superSponsorLog.getWorkStatus();
					if (ToolUtil.isNotEmpty(workStatus)) {
						superSponsorLog.setSponsorId(sponsor.getSponsorId());
						superSponsorLog.setSponsorName(sponsor.getSponsorName());
						superSponsorLog.setFeedbackUserid(sponsor.getFeedbackUserid());
						superSponsorLog.setFeedbackUsername(sponsor.getFeedbackUsername());
						//要求反馈时间
						superSponsorLog.setFeedbackDeadline(sponsor.getFeedbackDeadline());
						//督办开始时间
						superSponsorLog.setOverseeTime(sponsor.getOverseeTime());
						if (ToolUtil.isNotEmpty(superSponsorLog.getFeedbackTime())
								&& ToolUtil.isNotEmpty(superSponsorLog.getFeedbackDeadline())) {
							//反馈效率
							Long day = DateUtil.getDaySub(superSponsorLog.getFeedbackDeadline(), superSponsorLog.getFeedbackTime());
							superSponsorLog.setFeedbackEfficiency(Integer.parseInt(day.toString()));
						}
						superSponsorLog.setFeedbackTime(sponsor.getFeedbackTime());
						superSponsorLog.setWorkStatus(sponsor.getWorkStatus());
						superSponsorLog.setServiceStatus(sponsor.getServiceStatus());
						superSponsorLog.setProgress(sponsor.getProgress());
						superSponsorLog.setNote(sponsor.getNote());
						superSponsorLogService.add(superSponsorLog);
					}
				}
			} else {
				//督办记录
				superSponsorLogService.add(sponsor);
			}
			superSponsorService.edit(sponsor);
			//督办日志
			SuperService superService = superServiceService.findByUnid(sponsor.getServiceUnid());
			LogManager.me().executeLog(LogTaskFactory.systemLog(sponsor.getUnid(), sponsor.getServiceUnid(), JSONObject.toJSONString(superSponsorService.findById(superSponsorModel.getUnid())), JSONObject.toJSONString(sponsor), 1, 9, sponsor.getSponsorId(), superService.getOverseeUserid()));
		} catch (RuntimeException e) {
			result.setCode(500);
			result.setMessage("操作失败");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 事项合并
	 {
	  "id":"0ff4995c-9c8a-4544-83da-87d6637681d8",
	  "targetId""8f954bd1-c1c6-44b0-9e0c-0b6c3cb3ed70"
	   }
	 */
	@ApiOperation(value = "事项合并", notes = "{\"id\":\"0ff4995c-9c8a-4544-83da-87d6637681d8\","
			+ "\"targetServiceId\":\"HY-19-001-A\"}")
	@PostMapping("/merger")
	@ResponseBody
	public RESTResultBean supervisionCombine(@RequestBody String jsonString) {
		RESTResultBean result = new RESTResultBean(200, "suceess");
		JSONObject json = JSONObject.parseObject(jsonString);
		String id = json.getString("id");
		String targetServiceId = json.getString("targetServiceId");
		if(ToolUtil.isEmpty(targetServiceId)){
			result.setCode(500);
			result.setMessage("请填写合并的主事项编码");
			return result;
		}
		if(ToolUtil.isEmpty(id)){
			result.setCode(500);
			result.setMessage("请勾选被合并的事项");
			return result;
		}
		SuperSponsor removeSponsor = superSponsorService.findById(id);
		if(removeSponsor == null){
			result.setCode(500);
			result.setMessage("被合并的事项不存在");
			return result;
		}
		SuperSponsor targetSponsor = superSponsorService.findByServiceId(targetServiceId);
		if(targetSponsor == null){
			result.setCode(500);
			result.setMessage("合并主事项不存在");
			return result;
		}
		if(removeSponsor.getServiceUnid().equals(targetSponsor.getServiceUnid())){
			result.setCode(500);
			result.setMessage("同一事项不可合并");
			return result;
		}
		//合并
		sponsorSearchService.supervisionCombine(removeSponsor, targetSponsor);
		return result;
	}
}
