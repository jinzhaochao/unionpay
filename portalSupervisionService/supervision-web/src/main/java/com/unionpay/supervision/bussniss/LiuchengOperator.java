package com.unionpay.supervision.bussniss;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.HttpClientUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.model.SponsorServiceReadyToDoModel;
import com.unionpay.supervision.model.SuperCondition;

/*
 * 流程平台http参数
 * author :xiongym
 * dataTime :2018 -12-20
 */
@Component
public class LiuchengOperator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${http.liucheng.readToDo}")
	private String readToDoURL;

	@Value("${http.liucheng.submit}")
	private String submitURL;

	@Value("${http.liucheng.finduser}")
	private String finduserURL;

	@Value("${http.liucheng.doApprove}")
	private String sendSmsApprovalURL;

	@Value("${http.liucheng.findOverseeUsername}")
	private String findOverseeUsername;

	@Value("${http.liucheng.appkey}")
	private String appkey;

	@Value("${http.liucheng.smskey}")
	private String smskey;

	@Value("${http.liucheng.secretkey}")
	private String secretkey;

	/**
	 * 待确认事项查询(OUT-01)
	 */
	public RESTResultBean geQueryTodoTaskList(Integer page, Integer size, String usertId, SuperCondition condition,
			Integer top) {
		List<SponsorServiceReadyToDoModel> list = new ArrayList<SponsorServiceReadyToDoModel>();
		RESTResultBean result = new RESTResultBean(200, "success");
		JSONObject param = getparam(page, size, usertId, condition, top);
		Map<String, String> params = new HashMap<String, String>();
		params.put("params", param.toString());
		String str = HttpClientUtils.doPost(readToDoURL, params);
		if (ToolUtil.isNotEmpty(str)) {
			JSONObject json = JSONObject.parseObject(str);
			Boolean isSuccess = json.getBoolean("isSuccess");
			String pager = json.getString("pager");
			result.setPager(JSON.parseObject(pager, Pager.class));
			JSONArray jsonData = json.getJSONArray("jsonData");
			if (isSuccess && jsonData != null && jsonData.size() > 0) {
				for (Object obj : jsonData) {
					JSONObject jsonobj  = JSONObject.parseObject(obj.toString());
					SponsorServiceReadyToDoModel readyDo = JSONObject.parseObject(obj.toString(),
							SponsorServiceReadyToDoModel.class);
					readyDo.setUnid(jsonobj.getString("sponsorUnid"));
					readyDo.setServiceId(jsonobj.getString("serviceId"));
					readyDo.setTypeName(jsonobj.getString("serviceType"));
					if(readyDo.getServiceTime()!=null){
						//页面显示时间格式调整--------lis
						readyDo.setServiceTime(jsonobj.getString("serviceTime").substring(0,10));
					}
					String workStatus = jsonobj.getString("workStatus");
					readyDo.setWorkStatus(getWorkStatus(workStatus));
					list.add(readyDo);
				}
			}
			result.setData(list);
		}

		return result;
	}

	/**
	 * 待确认事项查询(OUT-01)
	 */
	public List<SponsorServiceReadyToDoModel> geQueryTodoTaskList(String usertId, SuperCondition condition,
			Integer top) {
		List<SponsorServiceReadyToDoModel> list = new ArrayList<SponsorServiceReadyToDoModel>();
		JSONObject param = getparam(null, null, usertId, condition, top);
		Map<String, String> params = new HashMap<String, String>();
		params.put("params", param.toString());
		String str = HttpClientUtils.doPost(readToDoURL, params);
		if (ToolUtil.isNotEmpty(str)) {
			JSONObject json = JSONObject.parseObject(str);
			Boolean isSuccess = json.getBoolean("isSuccess");
			JSONArray jsonData = json.getJSONArray("jsonData");
			if (isSuccess && jsonData != null && jsonData.size() > 0) {
				for (Object obj : jsonData) {
					JSONObject jsonobj  = JSONObject.parseObject(obj.toString());
					SponsorServiceReadyToDoModel readyDo = JSONObject.parseObject(obj.toString(),
							SponsorServiceReadyToDoModel.class);
					readyDo.setUnid(jsonobj.getString("sponsorUnid"));
					readyDo.setServiceId(jsonobj.getString("serviceId"));
					readyDo.setTypeName(jsonobj.getString("serviceType"));
					if(ToolUtil.isNotEmpty(readyDo.getServiceTime())){
						readyDo.setServiceTime(jsonobj.getString("serviceTime"));
					}
					String workStatus = jsonobj.getString("workStatus");
					readyDo.setWorkStatus(getWorkStatus(workStatus));
					list.add(readyDo);
				}
			}
		}

		return list;
	}

	private JSONObject getparam(Integer page, Integer size, String usertId, SuperCondition condition, Integer top) {
		JSONObject param = new JSONObject();
		if(page != null){
			param.put("currentPage", page.toString());
		}
		if(size != null){
			param.put("size", size.toString());
		}
		if (top == 1) {
			// 待确认事项
			param.put("type", "2");
			param.put("overseeUserid", usertId);
		} else if (top == 5) {
			// 待回复
			param.put("type", "1");
			param.put("sponsorId", usertId);
		}
		if (ToolUtil.isNotEmpty(condition.getServiceId())) {
			param.put("serviceId", condition.getServiceId());
		}
		if (ToolUtil.isNotEmpty(condition.getServiceStartTime())) {
			param.put("serviceTimeStart", condition.getServiceStartTime());
		}
		if (ToolUtil.isNotEmpty(condition.getServiceEndTime())) {
			param.put("serviceTimeEnd", condition.getServiceEndTime());
		}
		if (ToolUtil.isNotEmpty(condition.getServiceName())) {
			param.put("serviceName", condition.getServiceName());
		}
		if (ToolUtil.isNotEmpty(condition.getTaskNote())) {
			param.put("taskNote", condition.getTaskNote());
		}
		if (ToolUtil.isNotEmpty(condition.getcommandLeader())) {
			param.put("commandLeader", condition.getcommandLeader());
		}
		if (ToolUtil.isNotEmpty(condition.getOrgName())) {
			param.put("orgName", condition.getOrgName());
		}
		if (ToolUtil.isNotEmpty(condition.getWorkStatus())) {
			param.put("workStatus", condition.getWorkStatus());
		}
		if (ToolUtil.isNotEmpty(condition.getFeedbackStartTime())) {
			param.put("feedbackTimeStart", condition.getFeedbackStartTime());
		}
		if (ToolUtil.isNotEmpty(condition.getFeedbackEndTime())) {
			param.put("feedbackTimeEnd", condition.getFeedbackEndTime());
		}
		if (ToolUtil.isNotEmpty(condition.getFeedbackEndTime())) {
			param.put("feedbackTimeEnd", condition.getFeedbackEndTime());
		}
		if (ToolUtil.isNotEmpty(condition.getSponsorName())) {
			param.put("sponsorName", condition.getSponsorName());
		}
		if (ToolUtil.isNotEmpty(condition.getOverseeName())) {
			param.put("overseeName", condition.getOverseeName());
		}
		return param;
	}

	/**
	 * 流程推送(OUT-02)
	 */
	public Boolean doOANext(JSONArray sponsorServicelog) {
		Boolean result = false;
		Map<String, String> params = new HashMap<String, String>();
		params.put("appKey", appkey);
		String requestTime = System.currentTimeMillis() + "";
		params.put("requestTime", requestTime);
		params.put("sign", FeedBackOperator.getSign(appkey, secretkey, requestTime));
		params.put("params", sponsorServicelog.toString());
		long time1= System.currentTimeMillis();
		String str = HttpClientUtils.doPost(submitURL, params);
		long time2 = System.currentTimeMillis();
		long time = time2 - time1;
		logger.info("流程推送时间 ===="+time);
		logger.info("流程推送结果===="+str);
		if (ToolUtil.isNotEmpty(str)) {
			JSONObject json = JSONObject.parseObject(str);
			Boolean isSuccess = json.getBoolean("isSuccess");
			if (isSuccess) {
				// 推送成功
				result = true;
			} else {
				logger.info("推送流程平台:" + result + "返回值：" + str + " params = " + sponsorServicelog);
			}
		}
		logger.info("推送流程平台:" + result + " params = " + sponsorServicelog);
		return result;
	}

	/**
	 * 送审人账号获取接口(OUT-03)
	 */
	public Map<String, String> getAccountHolderAll(String userId) {
		Map<String, String> accountHolder = new HashMap<String, String>();
		for (int i = 23; i <= 25; i++) {
			String resut = getAccountHolder("oa4Qianbao", userId, i + "");
			if (ToolUtil.isNotEmpty(resut)) {
				JSONObject json = JSONObject.parseObject(resut);
				Boolean isSuccess = json.getBoolean("isSuccess");
				JSONArray jsonData = json.getJSONArray("jsonData");
				if (isSuccess && jsonData != null && jsonData.size() > 0) {
					for (Object obj : jsonData) {
						JSONObject account = JSONObject.parseObject(obj.toString());
						String userid = account.getString("userid");
						String empname = account.getString("empname");
						accountHolder.put(userid, empname);
					}
				}
			}
		}

		return accountHolder;
	}

	/**
	 * 短信送审人账号获取接口(OUT-03)
	 */
	public Map<String, String> getApproveLeaderAll(String userId) {
		Map<String, String> accountHolder = new HashMap<String, String>();
		for (int i = 23; i <= 25; i++) {
			String resut = getAccountHolder("oa4Qianbao", userId, i + "");
			if (ToolUtil.isNotEmpty(resut)) {
				JSONObject json = JSONObject.parseObject(resut);
				Boolean isSuccess = json.getBoolean("isSuccess");
				JSONArray jsonData = json.getJSONArray("jsonData");
				if (isSuccess && jsonData != null && jsonData.size() > 0) {
					for (Object obj : jsonData) {
						JSONObject account = JSONObject.parseObject(obj.toString());
						String userid = account.getString("empid");
						String empname = account.getString("empname");
						accountHolder.put(userid, empname);
					}
				}
			}
		}

		return accountHolder;
	}

	/**
	 * 主办部门单位账号获取接口out-4
	 */
	public Map<String, String> getOrgId(String userId) {
		Map<String, String> org = new LinkedHashMap<String, String>();
		String resut = getAccountHolder("oa4Qianbao", userId, "6");
		if (ToolUtil.isNotEmpty(resut)) {
			JSONObject json = JSONObject.parseObject(resut);
			Boolean isSuccess = json.getBoolean("isSuccess");
			JSONArray jsonData = json.getJSONArray("jsonData");
			if (isSuccess && jsonData != null && jsonData.size() > 0) {
				for (Object obj : jsonData) {
					JSONObject account = JSONObject.parseObject(obj.toString());
					String userid = account.getString("userid");
					String empname = account.getString("empname");
					org.put(userid, empname);
				}
			}

		}
		return org;
	}

	/**
	 * 主办部门单位账号获取接口out-4
	 */
	public Map<String, String> getOrgName(String userId) {
		Map<String, String> org = new LinkedHashMap<>();
		String resut = getAccountHolder("oa4Qianbao", userId, "6");
		if (ToolUtil.isNotEmpty(resut)) {
			JSONObject json = JSONObject.parseObject(resut);
			Boolean isSuccess = json.getBoolean("isSuccess");
			JSONArray jsonData = json.getJSONArray("jsonData");
			if (isSuccess && jsonData != null && jsonData.size() > 0) {
				for (Object obj : jsonData) {
					JSONObject account = JSONObject.parseObject(obj.toString());
					String userid = account.getString("userid");
					String empname = account.getString("empname");
					org.put(empname, userid);
				}
			}

		}

		return org;
	}
	
	private String getWorkStatus(String workStatus){
		String newWorkStatus = null;
		if(ToolUtil.isNotEmpty(workStatus)){
			if("4".equals(workStatus)){
				newWorkStatus = "工作中止";
			}else if("1".equals(workStatus)){
				newWorkStatus = "已完成";
			}else if("2".equals(workStatus)){
				newWorkStatus = "推进中,有阶段性进展";
			}else if("3".equals(workStatus)){
				newWorkStatus = "推进中,暂无阶段性进展";
			}
		}
		return newWorkStatus;
	}

	/**
	 * 督办室联系人获取
	 * @author lishuang
	 */
	public Map<String,String> findOverseeUsername(){
		Map<String,String> map = new LinkedHashMap<>();
		String str = HttpClientUtils.doGetJson(findOverseeUsername);
		JSONArray json = JSONArray.parseArray(str);
		for (int i = 0;json.size()>0&&i<json.size();i++){
			JSONObject jsonObject = JSONObject.parseObject(json.getString(i));
			map.put(jsonObject.getString("account"),jsonObject.getString("name"));
		}
		return map;
	}

	/**
	 * 发起短信审批接口(OUT-01)
	 * @author lishuang
	 */
	public Boolean smsRequestApproval(JSONObject sms,String userId,String approvalId) {
		Boolean result = false;
		Map<String, String> params = new HashMap<String, String>();
		//http://uatworkflownew.oa.unionpay.com/default/oawf/httptrs/basehttp!doApprove.action?type=messageSend&approveType=0&workItemID=0&personID=xujianan&leaderIDs=1015386&ext=?
		String requestTime = System.currentTimeMillis() + "";
		params.put("sign", FeedBackOperator.getSign(smskey, secretkey, requestTime));
		params.put("type","messageSend");
		params.put("approveType","0");
		params.put("workItemID","0");
		params.put("personID",userId);
		params.put("leaderIDs",approvalId);
		params.put("ext", sms.toString());
		String str = HttpClientUtils.doPost(sendSmsApprovalURL, params);
		if (ToolUtil.isNotEmpty(str)) {
			JSONObject json = JSONObject.parseObject(str);
			Boolean isSuccess = json.getBoolean("isSuccess");
			if (isSuccess) {
				// 推送成功
				result = true;
			} else {
				logger.info("推送流程平台:" + result + "返回值：" + str + " params = " + sms);
			}
		}
		return result;
	}


	public String getAccountHolder(String type, String userId, String queryType) {
		String result = null;
		Map<String, String> param = new HashMap<String, String>();
		param.put("type", type);
		param.put("personID", userId);
		param.put("queryType", queryType);
		result = HttpClientUtils.doGet(finduserURL, param);
		return result;
	}

	public static void main(String[] args) {
		String str = HttpClientUtils
				.doGetJson("http://uatworkflownew.oa.unionpay.com/default/oawf/httptrs/basehttp!queryHandlerPerson.action?"
						+ "type=oa4Qianbao&personID=xujianan&queryType=23");
		System.out.println(str);
	}

}
