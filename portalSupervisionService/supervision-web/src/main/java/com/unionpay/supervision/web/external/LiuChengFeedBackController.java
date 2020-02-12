package com.unionpay.supervision.web.external;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.exception.ServiceException;
import com.unionpay.common.log.LogManager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.HttpClientUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.sms.domain.smsMatter;
import com.unionpay.sms.model.Message;
import com.unionpay.sms.model.SmsLog;
import com.unionpay.sms.service.SmsMatterService;
import com.unionpay.supervision.bussniss.FeedBackOperator;
import com.unionpay.supervision.dao.SuperSponsorLogRepository;
import com.unionpay.supervision.domain.SuperClient;
import com.unionpay.supervision.domain.SuperLog;
import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.log.LogTaskFactory;
import com.unionpay.supervision.model.LcFeedBackDto;
import com.unionpay.supervision.model.SuperSponsorOaSend;
import com.unionpay.supervision.service.LcOperatorService;
import com.unionpay.supervision.service.SuperClientService;
import com.unionpay.supervision.service.SuperServiceOverseeMappingService;
import com.unionpay.supervision.service.SuperSponsorService;
import com.unionpay.supervision.supportController.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 流程平台反馈Controller
 * </p>
 *
 * @author xiongym
 * @since 2018-11-28
 */
@Api(value = "LiuChenFeedBackController")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/lcfeedback")
public class LiuChengFeedBackController extends BaseController {

	@Autowired
	private SuperClientService superClientService;
	@Autowired
	private LcOperatorService lcOperatorService;
	@Autowired
	private SuperSponsorService superSponsorService;
	@Autowired
	private SmsMatterService smsMatterService;

	@Autowired
	private SuperServiceOverseeMappingService superServiceOverseeMappingService;

	@Autowired
	private SuperSponsorLogRepository superSponsorLogRepository;
	@Value("${http.liucheng.push}")
	private String push;

//	/**
//	 * 5.1 督办反馈办结信息处理(IN-01)
//	 */
//	@ApiOperation(value = "督办反馈办结信息处理")
//	@PostMapping("/messagePush")
//	@ResponseBody
//	public RESTResultBean messagePush(@RequestHeader("appKey") String appKey,
//									  @RequestHeader("requestTime") String requestTime, @RequestHeader("sign") String sign,
//									  HttpServletRequest request, @Valid @RequestBody LcFeedBackDto lcFeedBackDto, BindingResult bindingResult) {
//		RESTResultBean result = new RESTResultBean(200, "success");
//		verify(bindingResult, request, lcFeedBackDto);
//		// 时间戳验证
//		if (!FeedBackOperator.isTrueTime(requestTime)) {
//			result.setCode(500);
//			result.setMessage("时间戳失效");
//			return result;
//		}
//		SuperClient client = superClientService.findByAppkey(appKey);
//		if (client == null) {
//			result.setCode(500);
//			result.setMessage("appKey is error");
//			return result;
//		}
//		String appSecret = client.getAppSecret();
//		// 签名验证
//		if (!sign.equals(FeedBackOperator.getSign(appKey, appSecret, requestTime))) {
//			result.setCode(500);
//			result.setMessage("签名错误");
//			return result;
//		}
//
//			//督办办结消息推送
//			String workstatus = lcFeedBackDto.getWorkStatus();
////			Integer workstatus = Integer.parseInt(workStatus);
//			if (workstatus != null && !"".equals(workstatus)) {
//				if (workstatus == "已完成" || workstatus == "工作中止" || workstatus == "工作终止") {
//					String sponsorUnid = lcFeedBackDto.getSponsorUnid();
//					String serviceUnid = lcFeedBackDto.getServiceUnid();
//					SuperSponsorOaSend superSponsorOaSend = superSponsorService.selectByWorkStatus(sponsorUnid,serviceUnid);
//					//督办状态类型：已办结 1 (写死的)
//					superSponsorOaSend.setType(1);
//					Map<String, String> params = new HashMap<>();
//					params = getparam(superSponsorOaSend.getUnid(), superSponsorOaSend.getOverseeName(), superSponsorOaSend.getServiceName(), superSponsorOaSend.getDeptId(), superSponsorOaSend.getTaskNote(), superSponsorOaSend.getType(), superSponsorOaSend.getServiceTime(), superSponsorOaSend.getCommandLeader(), superSponsorOaSend.getCommandLeader(), superSponsorOaSend.getServiceId(), superSponsorOaSend.getWorkStatus(), superSponsorOaSend.getFeedbackTime(), superSponsorOaSend.getProgress());
//					//修改请求头
//					String header = "application/x-www-form-urlencoded";
////					new Thread(new Runnable() {
////                        @Override
////                        public void run() {
////                        }
////                    }).start();
//					String str = HttpClientUtils.doPostHeader(push, header, params);
//					if (ToolUtil.isNotEmpty(str)) {
//						JSONObject json = JSONObject.parseObject(str);
//						Boolean isSuccess = json.getBoolean("isSuccess");
//						String message = json.getString("message");
//						if (isSuccess == true) {
//							result.setCode(200);
//							result.setMessage("发送成功");
//						}
//					}
//
//				}
//			}
//
//		return result;
//	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 5.1 督办反馈信息(IN-01)
	 */
	@ApiOperation(value = "督办反馈信息处理")
	@PostMapping("/operator")
	@ResponseBody
	public RESTResultBean operator(@RequestHeader("appKey") String appKey,
								   @RequestHeader("requestTime") String requestTime, @RequestHeader("sign") String sign,
								   HttpServletRequest request, @Valid @RequestBody LcFeedBackDto lcFeedBackDto, BindingResult bindingResult) {
		RESTResultBean result = new RESTResultBean(200, "success");
		verify(bindingResult, request, lcFeedBackDto);
		// 时间戳验证
		if (!FeedBackOperator.isTrueTime(requestTime)) {
			result.setCode(500);
			result.setMessage("时间戳失效");
			return result;
		}
		SuperClient client = superClientService.findByAppkey(appKey);
		if (client == null) {
			result.setCode(500);
			result.setMessage("appKey is error");
			return result;
		}
		String appSecret = client.getAppSecret();
		// 签名验证
		if (!sign.equals(FeedBackOperator.getSign(appKey, appSecret, requestTime))) {
			result.setCode(500);
			result.setMessage("签名错误");
			return result;
		}
		// 根据status，changeType操作
		//判断status，changeType是否传值----lishuang
		Integer changeType = lcFeedBackDto.getChangeType();
		Integer status = lcFeedBackDto.getStatus();
		if (null == status && null == changeType) {
			result = new RESTResultBean(500, "status或changeType 的值不对！");
			return result;
		}
		Integer logChangeType = null;
		if (status != null && status == 0) {
			// 进行删除操作，督办部门删除  修改  jinzhao  2019-12-17
//			superSponsorService.invalidateStatusByUnid(lcFeedBackDto.getSponsorUnid());
			logChangeType = -1;

			//流程平台返回status=0  则把督办信息还原为督办之前的样子  jinzhao  2019-12-17
			superSponsorService.updateIsSuper(lcFeedBackDto);

		} else if (changeType != null) {
			// changeType 1：立项；2：督办；3：变更；4：延期
			if (changeType == 1) {
				lcOperatorService.editSponsorInfo(lcFeedBackDto);
				logChangeType = 1;
			} else if (changeType == 2) {
				lcOperatorService.editSponsorInfo(lcFeedBackDto);
				logChangeType = 2;
			} else if (changeType == 3) {
				lcOperatorService.SponsorChangeAndDelay(lcFeedBackDto);
				logChangeType = 3;
			} else if (changeType == 4) {
				lcOperatorService.SponsorChangeAndDelay(lcFeedBackDto);
				logChangeType = 4;
			}
		}

		// 日志记录
		SuperLog log = new SuperLog();
		log.setSponsorId(lcFeedBackDto.getSponsorId());
		log.setSponsorUnid(lcFeedBackDto.getSponsorUnid());
		log.setServiceUnid(lcFeedBackDto.getServiceUnid());
		log.setChgData(JSONObject.toJSONString(lcFeedBackDto));
		log.setClientType(2);// 流程平台
		log.setChangeType(logChangeType);
		log.setStatus(1);
		log.setCreateTime(DateUtil.getTime());
		LogManager.me().executeLog(LogTaskFactory.systemLog(log));



		//督办办结消息推送
		// jinzhao 2019-12-4  董监事会议定事项、银联国际需总公司支持事项、公司领导境外出访布置工作和公司领导调研期间关注的问题，四种事项大类都不需要推送到U聊行政中心里
		List<SuperServiceOverseeMapping> list = superServiceOverseeMappingService.findServiceUnid(lcFeedBackDto.getServiceUnid());
		if (ToolUtil.isNotEmpty(list)) {
			String workStatus = lcFeedBackDto.getWorkStatus();
			Integer workstatus = Integer.parseInt(workStatus);
			if (workstatus != null && !"".equals(workstatus)) {
				if (workstatus == 1 || workstatus == 4) {
					String sponsorUnid = lcFeedBackDto.getSponsorUnid();
					String serviceUnid = lcFeedBackDto.getServiceUnid();
					SuperSponsorOaSend superSponsorOaSend = superSponsorService.selectByWorkStatus(sponsorUnid, serviceUnid);
					//督办状态类型：已办结 1  已中止 2
					if (workstatus == 1) {
						superSponsorOaSend.setType(1);
					} else if (workstatus == 4) {
						superSponsorOaSend.setType(2);
					}
					Map<String, String> params = new HashMap<>();
					params = getparam(superSponsorOaSend.getUnid(), superSponsorOaSend.getOverseeName(), superSponsorOaSend.getServiceName(), superSponsorOaSend.getDeptId(), superSponsorOaSend.getTaskNote(), superSponsorOaSend.getType(), superSponsorOaSend.getServiceTime(), superSponsorOaSend.getCommandLeader(), superSponsorOaSend.getCommandLeader(), superSponsorOaSend.getServiceId(), superSponsorOaSend.getWorkStatus(), superSponsorOaSend.getFeedbackTime(), superSponsorOaSend.getProgress());
					//修改请求头
					String header = "application/x-www-form-urlencoded";
//					new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                        }
//                    }).start();
					String str = HttpClientUtils.doPostHeader(push, header, params);
//				logger.info("-------------str:  "+str);
					if (ToolUtil.isNotEmpty(str)) {
						JSONObject json = JSONObject.parseObject(str);
						Boolean isSuccess = json.getBoolean("isSuccess");
						String message = json.getString("message");
						if (isSuccess == true) {
							result.setCode(200);
							result.setMessage("发送成功");
						}
					}

				}
			}
		}

		return result;
	}


	/**
	 * 4.1 短信审批结果信息(IN-01)
	 */
	@ApiOperation(value = "短信审批结果信息")
	@PostMapping("/smsOperator")
	@ResponseBody
	public RESTResultBean smsOperator(@RequestParam("appKey") String appKey,
									  @RequestParam("requestTime") String requestTime, @RequestParam("sign") String sign,
									  HttpServletRequest request, @Valid @RequestBody Message message, BindingResult bindingResult) {
		RESTResultBean result = new RESTResultBean(200, "success");
		try {
			verify(bindingResult, request, message);
		} catch (ServiceException e) {
			String error = e.getmessage();
			if (!e.getmessage().contains("不")){
				error = "";
			}
			throw new BussinessException(500,error);
		}
		// 时间戳验证
		if (!FeedBackOperator.isTrueTime(requestTime)) {
			result.setCode(500);
			result.setMessage("时间戳失效");
			return result;
		}
		//appKey验证
		SuperClient client = superClientService.findByAppkey(appKey);
		if (client == null) {
			result.setCode(500);
			result.setMessage("appKey is error");
			return result;
		}
		String appSecret = client.getAppSecret();
		// 签名验证
		if (!sign.equals(FeedBackOperator.getSign(appKey, appSecret, requestTime))) {
			result.setCode(500);
			result.setMessage("签名错误");
			return result;
		}
		//反馈信息处理
		List<String> unids = smsMatterService.findUnid();
		if (ToolUtil.isNotEmpty(unids)&&!unids.contains(message.getUnid())){
			result.setCode(500);
			result.setMessage("信息不存在");
			return result;
		}
		if (ToolUtil.isNotEmpty(message.getTiming_time())){
			String regix = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
			if (!message.getTiming_time().matches(regix)){
				result.setCode(500);
				result.setMessage("[timing_time]不符合yyyy-MM-dd hh:mm:ss格式");
				return result;
			}
		}
		smsMatter smsMatter = smsMatterService.getByUnid(message.getUnid());
		smsMatter.setApprovalResult(message.getApproval_result());
		smsMatter.setApprovalOpinion(message.getApproval_opinion());
		//审批时间超过定时发送时间
		if (ToolUtil.isNotEmpty(message.getTiming_time())&&DateUtil.parseTime(message.getTiming_time()).before(DateUtil.parseTime(message.getApproval_time()))){
			smsMatter.setMode("2");
			smsMatter.setNote("定时发送改为手动发送");
		}
		//根据审批结果，修改短信状态
		if (message.getApproval_result().equals("Y")){
			smsMatter.setStatus("待发送");
		}else {
			smsMatter.setStatus("审核未通过");
		}
		smsMatter.setNote(message.getNote());
		smsMatter sms = smsMatterService.SmsMatterSave(smsMatter);
		//保存申请发送信息异常判断
		if (ToolUtil.isEmpty(sms)){
			result.setCode(500);
			result.setMessage("数据存储异常");
			return result;
		}
		//记录日志-流程平台推送结果
		SmsLog log = new SmsLog();
		log.setUnid(message.getUnid());
		log.setClientType(2);
		log.setChgData(JSONObject.toJSONString(message));
		log.setStatus(1);
		log.setCreateTime(DateUtil.getTime());
		try {
			LogManager.me().executeLog(LogTaskFactory.smsLog(log));
		} catch (BussinessException e) {
			throw new BussinessException(500,e.getMessage());
		}
		return result;
	}

	private Map<String, String> getparam(String unid, String overseeName, String serviceName, String deptId, String taskNote, Integer type, String serviceTime, String commandLeader, String commandLeader1, String serviceId, String workStatus, String feedbackTime, String progress) {
		HashMap<String, String> param = new HashMap<>();
		if (ToolUtil.isNotEmpty(unid)){
			param.put("unid",unid);
		}
		if (ToolUtil.isNotEmpty(overseeName)){
			param.put("overseeName",overseeName);
		}
		if (ToolUtil.isNotEmpty(serviceName)){
			param.put("serviceName",serviceName);
		}
		if (ToolUtil.isNotEmpty(deptId)){
			param.put("deptId",deptId);
		}
		if (ToolUtil.isNotEmpty(taskNote)){
			param.put("taskNote",taskNote);
		}
		if (type != null){
			param.put("type",type.toString());
		}
		if (ToolUtil.isNotEmpty(serviceTime)){
			param.put("serviceTime",serviceTime);
		}
		param.put("commandLeader",commandLeader);
		param.put("commandLeader1",commandLeader1);
		param.put("serviceId",serviceId);
		param.put("workStatus",workStatus);
		param.put("feedbackTime",feedbackTime);
		param.put("progress",progress);


		return param;
	}





}
