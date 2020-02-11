package com.unionpay.sms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.exception.MyException;
import com.unionpay.common.log.LogManager;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.sms.dao.SmsMatterRepository;
import com.unionpay.sms.dao.SmsReceiverRepository;
import com.unionpay.sms.domain.smsMatter;
import com.unionpay.sms.domain.smsReceiver;
import com.unionpay.sms.model.*;
import com.unionpay.sms.service.SmsMatterService;
import com.unionpay.sms.service.SmsReceiverService;
import com.unionpay.sms.utils.WMResult;
import com.unionpay.sms.utils.WS_ToSend;
import com.unionpay.supervision.bussniss.LiuchengOperator;
import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.domain.OmOrganization;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.log.LogTaskFactory;
import com.unionpay.supervision.service.OmUserService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;

/**
 * 我的短信纪录的Service实现类
 * @author wangyue
 * @date 2018-12-7
 */
@Service
@Transactional
@Component

public class SmsMatterServiceImpl implements SmsMatterService {
	@Autowired
	private SmsMatterRepository smsMatterRepository;
	@Autowired
	private OMOrganizationRepository omOrganizationRepository;
	@Autowired
	private SmsReceiverRepository smsReceiverRepository;
	@Autowired
	private OmUserService omUserService;
	@Autowired
	private SmsReceiverService smsReceiverService;
	@Autowired
    private SmsMatterService smsMatterService;
	@Autowired
	private LiuchengOperator liuchengOperator;

	@PersistenceContext
	private EntityManager em;
	private final Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 * 查询短信申请记录列表
	 * @param currentPage
	 * @param size
	 * @param userid
	 * @param smsMatterCondition
	 * @return
	 * @author lishuang
	 * @date 2019-04-24
	 */
	@Override
	public List<smsMatterCondition> getAllSmsMatter(Integer currentPage, Integer size, String userid,
													smsMatterCondition smsMatterCondition) {
		String jpqlCondition = "select sm.unid,sm.title,sm.reason,sm.content,DATE_FORMAT(sm.submit_time,'%Y-%m-%d %H:%i:%S') submitTime,"
				+ " sm.`status` status from sms_matter sm where 1=1 AND sm.user_id =:userid";
		// 判断标题是不是为空
		if (ToolUtil.isNotEmpty(smsMatterCondition.getTitle())) {
			jpqlCondition += " AND sm.title like concat('%','" + smsMatterCondition.getTitle() + "','%')";
		}
		// 判断申请原因是否为空
		if (ToolUtil.isNotEmpty(smsMatterCondition.getReason())) {
			jpqlCondition += " AND sm.reason like concat('%','" + smsMatterCondition.getReason() + "','%')";
		}
		// 判断状态是否为空
		if (ToolUtil.isNotEmpty(smsMatterCondition.getStatus())) {
			jpqlCondition += " AND sm.Status = " + "'" + smsMatterCondition.getStatus() + "'";
		}
		// 判断开始时间
		if (ToolUtil.isNotEmpty(smsMatterCondition.getSubmitStartTime())) {
			jpqlCondition += " AND sm.submit_time>=" + "'" + smsMatterCondition.getSubmitStartTime() + "'";
		}
		// 判断结束时间
		if (ToolUtil.isNotEmpty(smsMatterCondition.getSubmitEndTime())) {
			jpqlCondition += " AND sm.submit_time<=" + "'" + smsMatterCondition.getSubmitEndTime() + "'";
		}
		jpqlCondition += " ORDER BY sm.submit_time desc";
		Query query = em.createNativeQuery(jpqlCondition);
		query.setParameter("userid",userid);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(smsMatterCondition.class));
		query.setFirstResult((currentPage - 1) * size);
		query.setMaxResults(size);
		List<smsMatterCondition> list = query.getResultList();
		return list;
	}

	/**
	 * 查询短信申请记录总数
	 * @param userid
	 * @param smsMatterCondition
	 * @return
	 * @author lishuang
	 * @date 2019-04-24
	 */
	@Override
	public Integer findSmsMatterTotal(String userid, smsMatterCondition smsMatterCondition) {
		int count = 0;
		String jpqlCondition = "select count(sm.unid) from sms_matter sm where 1=1 AND sm.user_id =:userid";
		// 判断标题是不是为空
		if (ToolUtil.isNotEmpty(smsMatterCondition.getTitle())) {
			jpqlCondition += " AND sm.title like concat('%','" + smsMatterCondition.getTitle() + "','%')";
		}
		// 判断申请原因是否为空
		if (ToolUtil.isNotEmpty(smsMatterCondition.getReason())) {
			jpqlCondition += " AND sm.reason like concat('%','" + smsMatterCondition.getReason() + "','%')";
		}
		// 判断状态是否为空
		if (ToolUtil.isNotEmpty(smsMatterCondition.getStatus())) {
			jpqlCondition += " AND sm.Status = " + "'" + smsMatterCondition.getStatus() + "'";
		}
		// 判断开始时间
		if (ToolUtil.isNotEmpty(smsMatterCondition.getSubmitStartTime())) {
			jpqlCondition += " AND sm.submit_time>=" + "'" + smsMatterCondition.getSubmitStartTime() + "'";
		}
		// 判断结束时间
		if (ToolUtil.isNotEmpty(smsMatterCondition.getSubmitEndTime())) {
			jpqlCondition += " AND sm.submit_time<=" + "'" + smsMatterCondition.getSubmitEndTime() + "'";
		}
		Query query = em.createNativeQuery(jpqlCondition);
		query.setParameter("userid",userid);
		List<BigInteger> counts = query.getResultList();
		if (ToolUtil.isNotEmpty(counts)){
			count = counts.get(0).intValue();
		}
		return count;
	}

	/**
	 * @author wangyue
	 * @date 2018-12-10 我的发送记录—>申请信息详情
	 */
	@Override
	public smsMatter findByUnid(String unid) {
		String sql = "SELECT s.unid,s.title,s.org_id orgId,s.org_name orgName,s.user_name userName,s.`status`,DATE_FORMAT(s.submit_time,'%Y-%m-%d %H:%i:%S') submitTime,"
				   + "s.reason,s.content,s.approver_id approverId,s.approver_name approverName,s.approval_opinion approvalOpinion,s.`mode`,DATE_FORMAT(s.timing_time,"
				   + "'%Y-%m-%d %H:%i:%S') timingTime FROM `sms_matter` s WHERE unid =:unid";
		Query query = em.createNativeQuery(sql);
		query.setParameter("unid",unid);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(smsMatter.class));
		smsMatter smsMatter = (smsMatter)query.getSingleResult();
		return smsMatter;
	}

	/**
	 * 根据unid获取申请信息详情
	 * @param unid
	 * @return
	 */
	public smsMatter getByUnid(String unid){
		return smsMatterRepository.findByUnid(unid);
	}

	/**
	 * @author wangyue
	 * @date 2018-12-11 申请发送-->提交申请和保存
	 */
	@Override

	public smsMatter SmsMatterSave(smsMatter smsMatter) {
		return smsMatterRepository.save(smsMatter);
	}

	/**
	 * 申请发送-->提交和保存
	 * @param condition
	 * @param request
	 *
	 * @author lishuang
	 * @date 2018-12-24
	 */
	@Transactional
	public smsMatter smsApplyAndSave(smsMatterCondition condition, HttpServletRequest request){
		// 获取用户userid
		String userid = SessionUtils.getUserId(request);
		// 获取用户信息
		OmUser omUser = omUserService.findByUserid(userid);
		// 获取部门信息
		OmOrganization omOrganization = omOrganizationRepository.findByOrgid(omUser.getOrgid());
		smsMatter smsMatter = smsMatterRepository.findByUnid(condition.getUnid());
		//smsMatter为空-新建；smsMatter不为空-修改
		if (ToolUtil.isEmpty(smsMatter)){
			smsMatter=new smsMatter();
			smsMatter.setUnid(condition.getUnid());
		}
		//type为1，代表保存；type为0，代表提交领导审批
		if (1==condition.getType()){
			//保存
			smsMatter.setCreateTime(DateUtil.getStrTime(new Date()));
			smsMatter.setStatus("草稿");
		}else {
			//提交
			smsMatter.setSubmitTime(DateUtil.getStrTime(new Date()));
			smsMatter.setStatus("审核中");
		}
		smsMatter.setTitle(condition.getTitle());
		smsMatter.setOrgId(omUser.getOrgid());
		smsMatter.setOrgName(omOrganization.getOrgname());
		smsMatter.setUserId(userid);
		smsMatter.setUserName(omUser.getEmpname());
		smsMatter.setReason(condition.getReason());
		smsMatter.setContent(condition.getContent());
		smsMatter.setMode(condition.getMode());
		//若为定时发送方式，设置定时发送的时间
		if (condition.getMode().equals("3")){
			smsMatter.setTimingTime(condition.getTimingTime());
		}
		//设置审核人id
		smsMatter.setApproverId(condition.getApproverId());
		//设置审核人姓名
		smsMatter.setApproverName(condition.getApproverName());
		return smsMatterRepository.save(smsMatter);
	}

	/**
	 * 推送给流程平台审批
	 * @param smsMatter
	 * @return
	 * @throws MyException
	 */
	public Boolean sendApprove(smsMatter smsMatter) throws MyException {
		Boolean result = null;
		//推送至流程平台审批
		JSONObject sms = new JSONObject();
		sms.put("unid",smsMatter.getUnid());
		sms.put("title",smsMatter.getTitle());
		sms.put("org_id",smsMatter.getOrgId());
		sms.put("org_name",smsMatter.getOrgName());
		sms.put("user_id",smsMatter.getUserId());
		sms.put("user_name",smsMatter.getUserName());
		sms.put("reason",smsMatter.getReason());
		sms.put("content",smsMatter.getContent());
		sms.put("mode",smsMatter.getMode());
		if (smsMatter.getMode().equals("3")){
			sms.put("timing_time",smsMatter.getTimingTime());
		}
		sms.put("approver_id",smsMatter.getApproverId());
		sms.put("approver_name",smsMatter.getApproverName());
		sms.put("create_time",smsMatter.getCreateTime());
		sms.put("note",smsMatter.getNote());
		//短信接收人
		JSONArray receivers = new JSONArray();
		List<smsReceiver> smsReceivers = smsReceiverService.findAllSmsReceiver(smsMatter.getUnid());
		if (ToolUtil.isNotEmpty(smsReceivers)){
			for (smsReceiver s:smsReceivers){
				Receiver receiver = new Receiver();
				receiver.setUnid(s.getUnid());
				receiver.setReceiver_id(s.getReceiverId());
				receiver.setReceiver_name(s.getReceiverName());
				receiver.setReceiver_tel(s.getReceiverTel());
				receiver.setOrg_id(s.getOrgId());
				receiver.setOrg_name(s.getOrgName());
				receiver.setOrg_level(s.getOrgLevel());
				receiver.setOrg_remark(s.getOrgRemark());
				receiver.setType(s.getType());
				receivers.add(receiver);
			}
		}
		sms.put("receiver",receivers);
		if (sms.size()>0){
			//记录推送日志
			TimerTask tt = LogTaskFactory.smsLog(smsMatter.getUnid(),1,sms.toString());
			LogManager.me().executeLog(tt);
			result = liuchengOperator.smsRequestApproval(sms,smsMatter.getUserId(),smsMatter.getApproverId());
			if (!result) {
                smsMatter.setStatus("草稿");
                smsMatterService.SmsMatterSave(smsMatter);
				logger.info("........推送给流程平台失败.........");
				throw new MyException("推送给流程平台失败");
			}
		}
		return result;
	}

	/**
	 * 部门及员工树信息
	 * @param orgId
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-04-17
	 */
	public JSONArray getDeptTree(Integer orgId){
		JSONArray array = new JSONArray();
		if (orgId == 0){
			List<Integer> ids = new ArrayList<>(3);
			ids.add(206);
			ids.add(450);
			ids.add(2817);
			for (Integer orgid: ids){
				OmOrganization omOrganization = omOrganizationRepository.findByOrgid(orgid);
				if (ToolUtil.isNotEmpty(omOrganization)){
					ModelDto object = new ModelDto();
					String id = ("o"+omOrganization.getOrgid());
					object.setId(id);
					object.setName(omOrganization.getOrgname());
					object.setIsOrg(true);
					if (ToolUtil.isNotEmpty(object)){
						findHeadOfficeInfo(object);
					}
					array.add(object);
				}
			}
		}else {
			OmOrganization omOrganization = omOrganizationRepository.findByOrgid(orgId);
			if (ToolUtil.isNotEmpty(omOrganization)){
				ModelDto object = new ModelDto();
				String id = ("o"+omOrganization.getOrgid());
				object.setId(id);
				object.setName(omOrganization.getOrgname());
				object.setIsOrg(true);
				if (ToolUtil.isNotEmpty(object)){
					findHeadOfficeInfo(object);
				}
				array.add(object);
			}
		}
		return array;
	}

	/**
	 * 查询当前model部门下的子部门和人员
	 * @param model
	 * @return
	 */
	private ModelDto findHeadOfficeInfo(ModelDto model){
		String firstSql = "select om.orgid orgid,om.orgname orgname,om.parentorgid parentorgid " +
					"from om_organization om where om.parentorgid=:parentOrgid AND om.STATUS =:status ORDER BY om.SORTNO";
		Query firstQuery = em.createNativeQuery(firstSql);
		firstQuery.setParameter("parentOrgid",model.getId().replace("o",""));
		firstQuery.setParameter("status",0);
		firstQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsOmOrganization.class));
		List<SmsOmOrganization> omOrganizations = firstQuery.getResultList();
		List children = new ArrayList<>();
		if (ToolUtil.isNotEmpty(omOrganizations)){
			for (SmsOmOrganization organization : omOrganizations){
				Map map = new HashMap();
				map.put("id","o"+organization.getOrgid());
				map.put("name",organization.getOrgname());
				map.put("isOrg",true);
				/*ModelDto modelDto = new ModelDto();
				modelDto.setId("o"+organization.getOrgid().toString());
				modelDto.setName(organization.getOrgname());
				modelDto.setIsOrg(true);*/
				children.add(map);
			}
		}
		//部门下员工
		List<SmsOmUser> users =findEmployeeByOrgid(Integer.parseInt(model.getId().replace("o","")));
		if (ToolUtil.isNotEmpty(users)){
			for (SmsOmUser user : users){
				Map map = new HashMap<>();
				map.put("id","u"+user.getEmpid());
				map.put("name",user.getEmpname());
				map.put("isOrg",false);
				/*ModelDto modelDto = new ModelDto();
				modelDto.setId("u"+user.getEmpid());
				modelDto.setName(user.getEmpname());
				modelDto.setIsOrg(false);*/
				children.add(map);
			}
		}
		if (null != children){
			model.setChildren(children);
		}
		return model;
	}

	/**
	 * 根据部门orgid查找员工
	 * @param orgid
	 * @return
	 */
	private List<SmsOmUser> findEmployeeByOrgid(Integer orgid){
		String sql = "select om.empid empid,om.userid userid,om.empname empname from om_user_addressbook om where om.EMPSTATUS <> '00' and om.EMPTYPE = '01'";
		if (ToolUtil.isNotEmpty(orgid)){
			sql += " and om.orgid = '"+orgid+"'";
		}
		Query query = em.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsOmUser.class));
		List<SmsOmUser> list = query.getResultList();
		return list;
	}

	/*private ModelDto findBranchInfo(ModelDto model){
		String firstSql = "select om.orgid orgid,om.orgname orgname,om.orgtype orgtype,om.parentorgid parentorgid,om.orglevel orglevel " +
				"from om_organization om where om.parentorgid=:parentOrgid AND om.STATUS =:status";
		Query firstQuery = em.createNativeQuery(firstSql);
		firstQuery.setParameter("parentOrgid",model.getId());
		firstQuery.setParameter("status",0);
		firstQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsOmOrganization.class));
		List<SmsOmOrganization> omOrganizations = firstQuery.getResultList();
		List<ModelDto> children = new ArrayList<>();
		if (ToolUtil.isNotEmpty(omOrganizations)){
			model.setHasChildDept(true);
			for (SmsOmOrganization organization : omOrganizations){
				ModelDto modelDto = new ModelDto();
				modelDto.setId(organization.getOrgid().toString());
				modelDto.setName(organization.getOrgname());
				modelDto.setIsOrg(true);
				//modelDto.setType(organization.getOrgtype());
				children.add(modelDto);
			}
		}else {
			model.setHasChildDept(false);
		}
		*//*List<SmsOmUser> users =findAllBranch(Integer.parseInt(model.getId()));
		if (ToolUtil.isNotEmpty(users)){
			for (SmsOmUser user : users){
				ModelDto modelDto = new ModelDto();
				modelDto.setId(user.getUserid());
				modelDto.setName(user.getEmpname());
				modelDto.setIsOrg(false);
				children.add(modelDto);
			}
		}*//*
		model.setChildren(children);
		if (model.getHasChildDept()&&ToolUtil.isNotEmpty(children)){
			for (ModelDto dto : children){
				if (true == dto.getIsOrg()){
					findBranchInfo(dto);
				}
			}
		}
		return model;
	}
	private List<SmsOmUser> findAllBranch(Integer orgid){
		String sql = "select om.empid empid,om.userid userid,om.empname empname,om.empstatus empstatus,om.orgid orgid,om.mobileno mobileno from om_user_addressbook om where om.EMPSTATUS <> '00'";
		if (ToolUtil.isNotEmpty(orgid)){
			sql += " and om.orgid = '"+orgid+"'";
		}
		Query query = em.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsOmUser.class));
		List<SmsOmUser> list = query.getResultList();
		return list;
	}*/

	/**
	 * 查询所有短信申请记录主键unid
	 * @return
	 */
	public List<String> findUnid(){
		return smsMatterRepository.findAllUnid();
	}

	/**
	 * 我的发送记录-->待发送信息的发送（mode为手动发送）
	 * @param unid
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-01-16
	 */
	@Override
	public WMResult findByUnidAndUpdate(String unid) {
		WMResult result = null;
		smsMatter smsMatter = smsMatterRepository.findByUnid(unid);
		if (ToolUtil.isEmpty(smsMatter)){
			logger.error("该信息不存在");
			return result;
		}
		List<smsReceiver> smsReceivers = smsReceiverService.findAllSmsReceiver(unid);
		if (ToolUtil.isEmpty(smsReceivers)){
			logger.error("该信息没有接收人");
			return result;
		}
		if (smsMatter.getMode().equals("2")){
			for (smsReceiver smsReceiver : smsReceivers){
				if ("已发送".equals(smsReceiver.getStatus())){
					continue;
				}
				JSONObject object = new JSONObject();
				object.put("content",smsMatter.getContent());
				object.put("mobile",smsReceiver.getReceiverTel());
				object.put("userid",smsMatter.getUserId());//创建该短信的人的userid
				//调用发送方法
				result = WS_ToSend.sendMsg(object.toJSONString());
				System.out.println("=====result====="+result);
				//发送成功，修改短信状态及结束时间；修改接收人状态
				if (result.isSuccess()){
					//修改接收人接收状态
					smsReceiver.setStatus("已发送");
				}else {
					smsReceiver.setStatus("发送失败");
				}
				smsReceiver.setSendTime(DateUtil.getTime(new Date()));
				smsReceiverRepository.save(smsReceiver);
			}
			//修改短信状态
			smsMatter.setStatus("已发送");
			smsMatter.setEndTime(DateUtil.getStrTime(new Date()));
			smsMatterRepository.save(smsMatter);
		}
		return result;
	}

	/**
	 * 获取所有短信申请
	 * @return
	 */
	public List<SmsInfo> findAllSmsMatter(){
		String sql = "select sm.unid unid,sm.content content,sm.user_id userid,sm.mode mode,sm.status status," +
				"sm.approval_result approvalResult,sm.timing_time timingTime " +
				"from sms_matter sm where sm.approval_result = 'Y' and sm.status = '待发送'";
		Query query = em.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsInfo.class));
		List<SmsInfo> list = query.getResultList();
		return list;
	}

	/*private SmsInfo findSmsInfoByUnid(String unid){
		String sql = "select sm.content content,sm.user_id userid,sm.mode mode,sm.status status from sms_matter sm where 1=1";
		if (ToolUtil.isNotEmpty(unid)){
			sql += " AND sm.unid =:unid";
		}
		Query query = em.createNativeQuery(sql);
		if (ToolUtil.isNotEmpty(unid)){
			query.setParameter("unid",unid);
		}
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsInfo.class));
		SmsInfo sms = (SmsInfo)query.getSingleResult();
		return sms;
	}*/
}
