package com.unionpay.supervision.bussniss;

import java.util.*;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperLcSponsorRepository;
import com.unionpay.supervision.domain.SuperLcSponsor;
import com.unionpay.supervision.service.SuperLcSponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.HttpUtil;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.domain.SuperTypeOversee;
import com.unionpay.supervision.domain.SuperTypeService;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.supervision.service.SuperTypeOverseeService;
import com.unionpay.supervision.service.SuperTypeServiceService;
import com.unionpay.supervision.supportController.BaseController;

/*
 * 综合性查询
 * author :xiongym
 * dataTime :2018 -11-02
 */
@Component
public class CommonOperator {

	@Autowired
	private SuperTypeOverseeService superTypeOverseeService;
	@Autowired
	private SuperTypeServiceService superTypeServiceService;
	@Autowired
	private OmUserService omUserService;
	@Autowired
	private LiuchengOperator liuchengOperator;
	@Autowired
	private SuperLcSponsorRepository superLcSponsorRepository;
	@Autowired
	private SuperLcSponsorService superLcSponsorService;

	/**
	 * 督办事项新增编辑页，下拉选项所用 "seleteMap":{ "SuperTypeService" :{ "unid" :typeName },
	 * "SuperTypeOversee" :{ "unid" :typeName }, "sponsor" :{ "unid" :sponsorName } }
	 */
	public JSONObject getSelectMap() {
		JSONObject selectMap = new JSONObject();
		// 督办类型
		List<SuperTypeOversee> SuperTypeOverseelsit = superTypeOverseeService.SuperTypeOverseeFind();
		Map<String, Object> SuperTypeOversee = new HashMap<String, Object>();
		for (SuperTypeOversee superTypeOversee : SuperTypeOverseelsit) {
			if (SuperTypeOversee != null) {
				SuperTypeOversee.put(superTypeOversee.getUnid(), superTypeOversee.getTypeName());
			}
		}
		selectMap.put("SuperTypeOversee", SuperTypeOversee);
		// 事项类型
		List<SuperTypeService> SuperTypeServicelist = superTypeServiceService.findAll();
		Map<String, Object> SuperTypeService = new HashMap<String, Object>();
		for (SuperTypeService superTypeService : SuperTypeServicelist) {
			SuperTypeService.put(superTypeService.getUnid(), superTypeService.getTypeName());
		}
		selectMap.put("SuperTypeService", SuperTypeService);
		// 分管公司领导
		Map<String, String> commandLeader = new LinkedHashMap<String, String>();
		String orgName = "公司领导";
		List<OmUser> userlist = omUserService.findUserByOrgName(orgName);
		if (userlist != null && userlist.size() > 0) {
			for (OmUser user : userlist) {
				commandLeader.put(user.getUserid(), user.getEmpname());
			}
		}
		selectMap.put("commandLeader", commandLeader);
		// 督办室联系人
		//Map<String, String> overseeUsername = new HashMap<String, String>();
		/*orgName = "督查督办室";
		List<OmUser> userlist2 = omUserService.findUserByOrgName(orgName);
		if (userlist2 != null && userlist2.size() > 0) {
			for (OmUser user : userlist2) {
				overseeUsername.put(user.getUserid(), user.getEmpname());
			}
		}*/
		Map<String, String> overseeUsername = liuchengOperator.findOverseeUsername();
		selectMap.put("overseeUsername", overseeUsername);
		String userid = BaseController.getUserId();
		OmUser user = omUserService.findByUserid(userid);
		HashMap<String,String> owner = new HashMap<>();
		owner.put(userid, user.getEmpname());
		selectMap.put("owner", owner);
		//推进状态
		Map<String,String> workStatus = new LinkedHashMap<>();
		workStatus.put("1", "已完成");
		workStatus.put("2", "推进中,有阶段性进展");
		workStatus.put("3", "推进中,暂无阶段性进展");
		workStatus.put("4", "工作中止");
		//去掉工作终止  -- jinzhao  2019-11-29
//		workStatus.put("5", "工作终止");
		selectMap.put("workStatus", workStatus);
		return selectMap;
	}

	/**
	 * 督办事项新增编辑页（流程平台数据），下拉选项所用 "seleteMap":{ "orgMange" :{ "unid" :userName },
	 *  "sponsor" :{ "unid" :sponsorName } }
	 */
	public JSONObject getLiuichengSelectMap() {
		JSONObject selectMap = new JSONObject();
		//Map<String, String> orgMange = liuchengOperator.getOrgId(SessionUtils.getUserId(HttpUtil.getRequest()));
		//改为从本地数据库获取（已通过定时任务，写入本地数据库，每天更新）
		//selectMap.put("orgMange", orgMange);
		//审核领导
		Map<String, String> AccountHolder = liuchengOperator.getAccountHolderAll(SessionUtils.getUserId(HttpUtil.getRequest()));
		selectMap.put("verifiers", AccountHolder);
		return selectMap;
	}

	public Map<String,String> getSuperLcSponsor(){
		Map<String,String> orgMange = new LinkedHashMap<>();
		// 主办单位
//		List<SuperLcSponsor> list = superLcSponsorRepository.findAll();
		//按部门表排序 --jinzhao   20191213
		List<SuperLcSponsor> list = superLcSponsorRepository.find();
		if (ToolUtil.isNotEmpty(list)){
			for (SuperLcSponsor s : list){
				orgMange.put(s.getUserId(),s.getEmpName());
			}
		}
		return orgMange;
	}

	/**
	 * 查询公司领导
	 */
	public Map<String, String> selectCommandLeader() {
		// 分管公司领导
		Map<String, String> commandLeader = new HashMap<String, String>();
		String orgName = "公司领导";
		List<OmUser> userlist = omUserService.findUserByOrgName(orgName);
		if (userlist != null && userlist.size() > 0) {
			for (OmUser user : userlist) {
				commandLeader.put(user.getUserid(), user.getEmpname());
			}
		}

		return commandLeader;
	}

}
