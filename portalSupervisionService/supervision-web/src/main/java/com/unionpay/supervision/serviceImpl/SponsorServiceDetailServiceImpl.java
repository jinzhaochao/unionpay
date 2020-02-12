package com.unionpay.supervision.serviceImpl;

import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.domain.SuperService;
import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.domain.SuperSponsorLog;
import com.unionpay.supervision.model.*;
import com.unionpay.supervision.service.SponsorServiceDetailService;
import com.unionpay.supervision.service.SuperServiceService;
import com.unionpay.supervision.service.SuperSponsorService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门综合事项查询实现类
 * 
 */
@Service
@Transactional
public class SponsorServiceDetailServiceImpl implements SponsorServiceDetailService {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private SuperServiceService superServiceService;
	@Autowired
	private SuperSponsorService superSponsorService;

	/**
	 * 分页,条件查询
	 */
	@Override
	public List<SuperProject> getSuperCreateProjectFind(Integer page, Integer size, SuperCondition condition) {
		String jpql = "select sp.unid unid, se.oversee_name overseeName, sp.service_id serviceId,ty.type_name typeName,"
				+ "se.oversee_username overseeUsername,DATE_FORMAT(se.service_time,'%Y-%m-%d') serviceTime,se.task_note taskNote,"
				+ "sp.org_name orgName,sp.sponsor_name sponsorName,DATE_FORMAT(sp.proposed_closing_time,'%Y-%m-%d') proposedClosingTime,"
				+ "se.oversee_frequency overseeFrequency,sp.note note,sp.service_status serviceStatus,sp.progress,sp.work_status workStatus,"
				+ "DATE_FORMAT(sp.feedback_time,'%Y-%m-%d') feedbackTime  ,se.service_name serviceName "
				+ "from  super_sponsor sp LEFT JOIN  super_service se on se.unid = sp.service_unid "
				+ "LEFT Join super_type_service ty  on se.service_type = ty.unid Where 1 = 1";

		String conditionsql = jpql + getConditionsql(condition);
		Query query = em.createNativeQuery(conditionsql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperProject.class));
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		List<SuperProject> list = query.getResultList();
		return list;
	}

	/**
	 * 查询总条数；
	 */
	@Override
	public Integer getCountWithSuperCreateProjectFind(Integer page, Integer size, SuperCondition condition) {
		Integer count = null;
		String countjpql = "select count(sp.unid) from  super_sponsor sp LEFT JOIN super_service se on se.unid = sp.service_unid "
				+ "LEFT Join super_type_service ty  on se.service_type = ty.unid Where 1 = 1";
		String conditionsql = countjpql + getConditionsql(condition);
		Query querycount = em.createNativeQuery(conditionsql);
		List<BigInteger> counts = querycount.getResultList();
		if (counts != null && counts.size() > 0) {
			count = counts.get(0).intValue();
		} else {
			count = 0;
		}
		return count;
	}

	private String getConditionsql(SuperCondition condition) {
		String conditionsql = "";
		if (condition.getOverseeUserid() != null && condition.getOverseeUserid().size()>0 && ToolUtil.isNotEmpty(condition.getOverseeUserid())){
			conditionsql += " And se.oversee_userid in ( ";
			for (int i = 0 ; i<condition.getOverseeUserid().size();i++){
				if (i<condition.getOverseeUserid().size()-1){
					conditionsql += "'"+condition.getOverseeUserid().get(i)+"'"+",";
				}else {
					conditionsql +=  "'"+condition.getOverseeUserid().get(i)+"'";
				}
			}
			conditionsql += " )";
		}
		if (ToolUtil.isNotEmpty(condition.getCreateUserid())) {
			conditionsql += " AND se.create_userid = " + "'" + condition.getCreateUserid() + "'";
		}
		if (ToolUtil.isNotEmpty(condition.getOverseeUnid())) {
			//conditionsql += " AND se.oversee_unid = " + "'" + condition.getOverseeUnid() + "'";
			conditionsql += " AND ss.oversee_unid = " + "'" + condition.getOverseeUnid() + "'";//---lishuang
		}
		if (ToolUtil.isNotEmpty(condition.getServiceType())) {
			conditionsql += " AND se.service_type = " + "'" + condition.getServiceType() + "'";
		}
		if (ToolUtil.isNotEmpty(condition.getServiceId())) {
			conditionsql += " AND sp.service_id like concat('%','" + condition.getServiceId() + "','%')";
		}
		if (ToolUtil.isNotEmpty(condition.getServiceName())) {
			conditionsql += " AND se.service_name like concat('%','" + condition.getServiceName() + "','%')";
		}
		if (ToolUtil.isNotEmpty(condition.getTaskNote())) {
			conditionsql += " AND se.task_note like concat('%','" + condition.getTaskNote() + "','%')";
		}
		if (ToolUtil.isNotEmpty(condition.getOrgName())) {
			conditionsql += " AND sp.org_name like concat('%','" + condition.getOrgName() + "','%')";
		}
		if (ToolUtil.isNotEmpty(condition.getSponsorName())) {
			conditionsql += " AND sp.sponsor_name like concat('%','" + condition.getSponsorName() + "','%')";
		}
		if (ToolUtil.isNotEmpty(condition.getFeedbackStartTime())) {
			conditionsql += " AND sp.feedback_time >=" + "'" + condition.getFeedbackStartTime() + "'";
		}
		if (ToolUtil.isNotEmpty(condition.getFeedbackEndTime())) {
			conditionsql += " AND sp.feedback_time <=" + "'" + condition.getFeedbackEndTime() + "'";
		}
		if (ToolUtil.isNotEmpty(condition.getProgress())) {
			conditionsql +=  " AND sp.progress like concat('%','" + condition.getProgress() + "','%')";
		}
		if (ToolUtil.isNotEmpty(condition.getWorkStatus())) {
			conditionsql += " AND sp.work_status =" + "'"  + condition.getWorkStatus() + "'";
		}
		if ((ToolUtil.isNotEmpty(condition.getServiceStartTime()))) {
			conditionsql += " AND se.service_time >=" + "'" + condition.getServiceStartTime() + "'";
		}
		if ((ToolUtil.isNotEmpty(condition.getServiceEndTime()))) {
			conditionsql += " AND se.service_time <=" + "'" + condition.getServiceEndTime() + "'";
		}
		//分管公司领导
		if (ToolUtil.isNotEmpty(condition.getBranchedLeader())) {
			conditionsql += " AND se.branched_leader like concat('%','" + condition.getBranchedLeader() + "','%')";
		}
		//批示领导--新需求-0418 lishuang
		if (ToolUtil.isNotEmpty(condition.getCommandLeader())){
			conditionsql += " AND se.command_leader like concat('%','" + condition.getCommandLeader() + "','%')";
		}
		//根据标签查询  --jinzhao  2019-12-10
		if (ToolUtil.isNotEmpty(condition.getTag())) {
			conditionsql += " and t.tag_content like concat('%','" + condition.getTag() + "','%') ";
		}
		//最新反馈时间排序  --jinzhao  2019-12-10
		if (ToolUtil.isNotEmpty(condition.getFeedbackTimeSort())) {
			if (condition.getFeedbackTimeSort() == true) {
				conditionsql += ",sp.feedbackTime  ";
			} else {
				conditionsql += ",sp.feedbackTime desc ";
			}
		}
		//办理时间排序  --jinzhao  2019-12-10
		if (ToolUtil.isNotEmpty(condition.getThroughTimesSort())) {
			if (condition.getThroughTimesSort() == true) {
				conditionsql += ",sp.through_times ";
			} else {
				conditionsql += ",sp.through_times desc ";
			}
		}
		if (ToolUtil.isNotEmpty(condition.getStatus())) {
			conditionsql += " AND sp.status =" + condition.getStatus();
		} else {
			conditionsql += " AND sp.status = 1";
		}
		return conditionsql;
	}

	/**
	 * 我的待办
	 *
	 * @param top
	 * @return top=1 待确认事项 @Return null top=2 待立项事项 草稿 @Return 查询数据库得到的结果 top=3
	 *         待督办事项 督办 @Return 查询数据库得到的结果 top=4 超期未督办 下一次督办时间 @Return
	 *         查询数据库得到的结果 top=5 待回复事项@Return null
	 */
	public List<SuperMessage> getSuperMessageCheck(Integer top, String userId) {
		List<SuperMessage> list = null;
		String tempSql = "select sp.unid unid,sto.type_name typeName,se.task_note taskNote,"
				+ "sp.org_name orgName,sp.progress progress,DATE_FORMAT(sp.feedback_time,'%Y-%m-%d') feedbackTime "
				+ "from super_service se LEFT JOIN super_sponsor sp on se.unid = sp.service_unid "
				+ "LEFT join super_type_oversee sto on se.oversee_unid=sto.unid where 1 = 1";
		boolean flag = false;
		String serviceStatus = null;
		if (ToolUtil.isNotEmpty(userId)) {
			tempSql += " AND ( sp.sponsor_id=:userId OR oversee_userid =:userId)";
		}
		if (top == 2) {
			tempSql += " AND sp.service_status=:serviceStatus";
			serviceStatus = "草稿";
			flag = true;
		} else if (top == 3) {
			tempSql += " AND sp.service_status=:serviceStatus";
			serviceStatus = "立项";
			flag = true;
		} else if (top == 4) {
			String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
			tempSql += " AND sp.next_time<" + currentData;
			flag = true;
		}else if (top == 6) {
			tempSql += " AND sp.service_status=:serviceStatus";
			serviceStatus = "完成";
		}
		if (flag) {
			Query query = em.createNativeQuery(tempSql);
			query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperMessage.class));
			if (serviceStatus != null) {
				query.setParameter("serviceStatus", serviceStatus);
			}
			if (ToolUtil.isNotEmpty(userId)) {
				query.setParameter("userId", userId);
			}
			query.setMaxResults(5);
			list = query.getResultList();
		}
		return list;
	}

	/**
	 * 查询我的待办已办数据，数据导出
	 * 
	 * @return
	 */
	public List<SponsorServiceReadyToDoModel> getSuperServiceReadyToDo(Integer page, Integer size, SuperCondition condition, Integer top) {
		String jpql = "select sp.unid,sp.service_id serviceId,ty.type_name typeName,se.service_name serviceName ,"
				+ "se.command_leader commandLeader,se.task_note taskNote,DATE_FORMAT(se.create_time,'%Y-%m-%d') createTime,"
				+ "sp.org_name orgName,sp.sponsor_name sponsorName,DATE_FORMAT(sp.feedback_time,'%Y-%m-%d') feedbackTime,sp.work_status "
				+ "workStatus,DATE_FORMAT(se.service_time,'%Y-%m-%d') serviceTime,DATE_FORMAT(sp.proposed_closing_time,'%Y-%m-%d') "
				+ "proposedClosingTime,sp.progress,se.oversee_frequency overseeFrequency,se.oversee_username overseeUsername,"
				+ "sp.result_form resultForm,sp.work_plan workPlan,DATE_FORMAT(sp.next_time,'%Y-%m-%d') nextTime,"
				+ "sp.service_status serviceStatus,sp.feedback_rule feedbackRule, group_concat(t.tag_content) tag from  super_sponsor sp LEFT JOIN  super_service se "
				+ "on se.unid = sp.service_unid LEFT Join super_type_service ty  on se.service_type = ty.unid "
				+ "LEFT JOIN super_service_oversee_mapping ss ON ss.service_unid = se.unid left join super_sponsor_tag st on sp.unid = st.sponsor_unid left join super_tag t on st.tag_id = t.tag_id Where 1 = 1";
		String serviceStatus = null;
		if (top == null || top == 6) {
			// 无类型
		} else if (top == 2) {
			jpql += " AND sp.service_status=:serviceStatus";
			serviceStatus = "草稿";
		} else if (top == 3) {
			jpql += " AND sp.service_status in ('立项','督办','中止') "
					+ " AND sp.work_status in ('推进中,有阶段性进展','推进中,暂无阶段性进展','工作中止') "
					+ " AND sp.feedback_time is not null ";
		} else if (top == 4) {
			String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
			jpql += " AND sp.next_time<" + "'" +  currentData + "'" + "  AND sp.service_status in ('立项','督办','中止')"
					+ "AND sp.work_status in ('推进中,有阶段性进展','推进中,暂无阶段性进展','工作中止') "
					+ " AND sp.feedback_time is not null ";//中止状态的也要显示--新需求-0403 lishuang
		}
		String conditionsql = jpql + getConditionsql(condition);
		if(top == null || top == 6){
			if (ToolUtil.isEmpty(condition.getOverseeUserid()) && ToolUtil.isNotEmpty(condition.getUserId())) {
				conditionsql += " AND (  se.oversee_userid = " + "'" + condition.getUserId() + "'" + " OR sp.sponsor_id = " + "'" + condition.getUserId() + "' )";
			}
			//我的已办中，根据最近操作时间（创建时间与开始督办时间相比较，大者即为最近操作时间）进行倒序排序
			conditionsql += " group by sp.unid order by " +
					" IF (UNIX_TIMESTAMP(sp.create_time) > UNIX_TIMESTAMP(sp.oversee_time),sp.create_time,sp.oversee_time) desc";
		}else{
			if (ToolUtil.isEmpty(condition.getOverseeUserid()) && ToolUtil.isNotEmpty(condition.getUserId())) {
				conditionsql += " AND se.oversee_userid = " + "'" + condition.getUserId() + "' " ;
			}
			if(top == 2){
				conditionsql += " group by sp.unid order by sp.create_time desc";
			}else{
				conditionsql += " group by sp.unid order by sp.next_time";
			}
		}
		Query query = em.createNativeQuery(conditionsql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SponsorServiceReadyToDoModel.class));
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		if (serviceStatus != null) {
			query.setParameter("serviceStatus", serviceStatus);
		}
		//jinzhao 2019-12-11  同一事项其他主办单位正在督办中的返回1 进行红点提醒
		List<SponsorServiceReadyToDoModel> list = query.getResultList();
		for (SponsorServiceReadyToDoModel sponsorServiceReadyToDoModel : list) {
			SuperService service = superServiceService.findByUnid(superSponsorService.findById(sponsorServiceReadyToDoModel.getUnid()).getServiceUnid());
			List<SuperSponsor> sponsorList = superSponsorService.findByServiceUnid(service.getUnid(), 1);
			for (SuperSponsor superSponsor : sponsorList) {
				if ("是".equals(superSponsor.getIsRead())){
					sponsorServiceReadyToDoModel.setRemind(1);
				}
			}
		}
		return list;
	}

	/**
	 * 我的待办已办数据查询数量
	 * 
	 * @return
	 */
	public int getCountWithSuperServiceReadyToDo(SuperCondition condition, Integer top) {
		int count = 0;
		String jpql = "select count(sp.unid) from  super_sponsor sp LEFT JOIN  super_service se on se.unid = sp.service_unid "
				+ "LEFT Join super_type_service ty  on se.service_type = ty.unid "
				+ "LEFT JOIN super_service_oversee_mapping ss ON ss.service_unid = se.unid "
				+ "LEFT join super_type_oversee st on st.unid = ss.oversee_unid Where 1 = 1";
		String serviceStatus = null;
		if (top == null || top == 6) {
			// 无类型
		} else if (top == 2) {
			jpql += " AND sp.service_status=:serviceStatus";
			serviceStatus = "草稿";
		} else if (top == 3) {
			jpql += " AND sp.service_status in ('立项','督办','中止') "
					+ " AND sp.work_status in ('推进中,有阶段性进展','推进中,暂无阶段性进展','工作中止') "
					+ " AND sp.feedback_time is not null ";
		} else if (top == 4) {
			String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
			jpql += " AND sp.next_time<" + "'" +  currentData + "'" + "  AND sp.service_status in ('立项','督办','中止')"
					+ "AND sp.work_status in ('推进中,有阶段性进展','推进中,暂无阶段性进展','工作中止') "
					+ " AND sp.feedback_time is not null ";//中止状态的也要显示--新需求-0403 lishuang
		}
		String conditionsql = jpql + getConditionsql(condition);
		if(top == null || top == 6){
			if (ToolUtil.isEmpty(condition.getOverseeUserid()) && ToolUtil.isNotEmpty(condition.getUserId())) {
				conditionsql += " AND (  se.oversee_userid = " + "'" + condition.getUserId() + "'" + " OR sp.sponsor_id = "
			          + "'" + condition.getUserId() + "' )";
				}
			conditionsql += " group by sp.unid order by sp.create_time desc";
		}else{
			if (ToolUtil.isEmpty(condition.getOverseeUserid()) && ToolUtil.isNotEmpty(condition.getUserId())) {
				conditionsql += " AND se.oversee_userid = " + "'" + condition.getUserId() + "' " ;
			}
			if(top == 2){
				conditionsql += " group by sp.unid order by sp.create_time desc";
			}else{
				conditionsql += " group by sp.unid order by sp.next_time";
			}
		}
		Query querycount = em.createNativeQuery(conditionsql);
		if (serviceStatus != null) {
			querycount.setParameter("serviceStatus", serviceStatus);
		}
		List<BigInteger> counts = querycount.getResultList();
		if (counts != null && counts.size() > 0) {
			//count = counts.get(0).intValue();   //------lishuang
			count = counts.size();
		}
		return count;
	}

	/**
	 * 事项综合查询
	 * 
	 * @return
	 */
	@Override
	public List<SponsorServiceItemModel> getSuperServiceItemSynthesis(Integer page, Integer size,SuperCondition condition, Integer top, Integer type) {
		String jpql = "select DISTINCT sp.unid,se.oversee_name overseeName,DATE_FORMAT(se.service_time,'%Y-%m-%d') serviceTime,sp.org_name "
				+ "orgName,sp.sponsor_name sponsorName,DATE_FORMAT(sp.feedback_time,'%Y-%m-%d') feedbackTime,se.task_note taskNote,"
				+ "sp.progress,sp.service_id as serviceId,se.service_name as serviceName,sp.through_times as throughTimes,sp.is_read as isRead from super_sponsor sp LEFT JOIN  super_service se on se.unid = sp.service_unid "
				+ " LEFT JOIN super_sponsor_master ms ON sp.unid = ms.sponsor_unid LEFT JOIN  super_service_oversee_mapping ss ON ss.service_unid = se.unid Where 1 = 1";
		if (top != null) {
			if (top == 1) {
				condition.setWorkStatus(null);
				jpql += " AND sp.work_status like '推进中%' AND sp.service_status in ('立项', '督办')";
			}else if (top == 2){
				String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
				condition.setWorkStatus(null);
				jpql += " AND sp.next_time<" + "'" +  currentData + "'" + "  AND sp.work_status like '推进中%' AND sp.service_status in ('立项', '督办')";
			}else if (top == 3){
				condition.setWorkStatus("已完成");
			}else if (top == 4){
				condition.setWorkStatus("工作中止");
			}else if (top == 5){
				String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
				condition.setWorkStatus(null);
				jpql += " and sp.proposed_closing_time < '" + currentData +"' AND sp.work_status like '推进中%'  ";
			}else if(top == 6){
				String currentData = DateUtil.getOutTime(6);
				condition.setWorkStatus(null);
				jpql += " and sp.create_time < '"+currentData+"' AND sp.work_status like '推进中%' ";
			}
		}

		if(type != null){
			if(type == 1){
				//查询全部
			}else if(type == 2){
				// 本单位主办事项
				// jpql += " AND ms.org_id =" + condition.getOrgId();
				// 修改于2019/10/31 ---lishuang
				jpql += " AND sp.org_id IN (SELECT s.userid FROM super_lc_sponsor s,om_user u " +
						" WHERE s.userid = u.USERID AND u.DEPTORGID = "+condition.getOrgId()+")";
			}else if(type == 3){
				//本科室主办事项
				jpql += " AND ms.office_Id =" + condition.getOfficeOrgId();
			}else if(type == 4){
				//我的主办事项
				jpql += " AND sp.sponsor_id =" + "'" + condition.getUserId() + "'" ;
			}else if(type == 5){
				//领导出访布置工作
				condition.setOverseeUnid("9913");
			}else if(type == 6){
				//我的督办事项
//				jpql += " AND se.oversee_userid  =" + "'" + condition.getUserId() + "'" ;
			}
		}
		String conditionsql = jpql + getConditionsql(condition);
		Query query = em.createNativeQuery(conditionsql);
		query.unwrap(NativeQueryImpl.class)
				.setResultTransformer(Transformers.aliasToBean(SponsorServiceItemModel.class));
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		return query.getResultList();
	}

	/**
	 * 事项综合查询数量
	 * 
	 * @return
	 */
	@Override
	public int getCountWithSuperServiceItemSynthesis(SuperCondition condition, Integer top, Integer type) {
		int count = 0;
		String jpql = "select count( DISTINCT sp.unid)from super_sponsor sp LEFT JOIN  super_service se on se.unid = sp.service_unid "
				+ " LEFT JOIN super_sponsor_master ms ON sp.unid = ms.sponsor_unid LEFT JOIN super_service_oversee_mapping ss ON ss.service_unid = se.unid Where 1 = 1";
		if (top != null) {
			if (top == 1) {
				condition.setWorkStatus(null);
				jpql += " AND sp.work_status like '推进中%' AND sp.service_status in ('立项', '督办')";
			} else if (top == 2) {
				condition.setWorkStatus("已完成");
			} else if (top == 3) {
				condition.setWorkStatus("工作中止");
			} else if (top == 4) {
				condition.setWorkStatus(null);
				jpql += " AND (sp.feedback_deadline is not null AND sp.feedback_deadline < IFNULL(feedback_time,CURDATE()) )";
			}else if (top == 5) {
				//top=5推进中的超期办理的事项
				String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
				condition.setWorkStatus(null);
				jpql += " AND sp.next_time<" + "'" +  currentData + "'" + "  AND sp.work_status like '推进中%' AND sp.service_status in ('立项', '督办')";
			}
		}
		if(type != null){
			if(type == 1){
				//查询全部
			}
			else if(type == 2){
				// 本单位主办事项
				// jpql += " AND ms.org_id =" + condition.getOrgId();
				// 修改于2019/10/31 ---lishuang
				/*jpql += " AND sp.org_id IN (SELECT u.userid FROM om_user u " +
						"LEFT JOIN om_organization o ON o.ORGID = u.DEPTORGID WHERE o.ORGID ="+condition.getOrgId()+")";*/
				jpql += " AND sp.org_id IN (SELECT s.userid FROM super_lc_sponsor s,om_user u " +
						" WHERE s.userid = u.USERID AND u.DEPTORGID = "+condition.getOrgId()+")";
			}
			else if(type == 3){
				//本科室主办事项
				jpql += " AND ms.office_Id =" + condition.getOfficeOrgId() ;
			}else if(type == 4){
				//我的主办事项
				jpql += " AND sp.sponsor_id =" + "'" + condition.getUserId() + "'" ;
			}else if(type == 5){
				//领导出访布置工作
				condition.setOverseeUnid("9913");
			}else if(type == 6){
				//我的督办事项
				jpql += " AND se.oversee_userid  =" + "'" + condition.getUserId() + "'" ;
			}
		}
		String conditionsql = jpql + getConditionsql(condition);
		Query querycount = em.createNativeQuery(conditionsql);
		List<BigInteger> counts = querycount.getResultList();
		if (counts != null && counts.size() > 0) {
			count = counts.get(0).intValue();
		}
		return count;
	}

	/**
	 * 我的待办已办、督办事项查询，自定义事项大类下拉框数据
	 * @return
	 * @author lishuang
	 * @date 2019-04-11
	 */
	public List<SuperTypeOverseeModel> getSelfDefineType(){
		String sql = "SELECT s.unid,s.type_name typeName FROM `super_type_oversee` s where s.is_self_define = '1'";
		Query query = em.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperTypeOverseeModel.class));
		List<SuperTypeOverseeModel> list = query.getResultList();
		return list;
	}
	
	/**
	 * 我的督办事项，数据导出
	 * 
	 * @return
	 */
	@Override
	public List<SponsorServiceItemModel> getMySuperService(Integer page, Integer size, SuperCondition condition, Integer top) {
		String jpql = "select sp.unid,se.oversee_name overseeName,DATE_FORMAT(se.service_time,'%Y-%m-%d') serviceTime,sp.org_name "
				+ "orgName,sp.sponsor_name sponsorName,DATE_FORMAT(sp.feedback_time,'%Y-%m-%d') feedbackTime,se.task_note taskNote,"
				+ "sp.progress  from super_sponsor sp LEFT JOIN  super_service se on se.unid = sp.service_unid "
				+ " LEFT JOIN super_service_oversee_mapping ss ON ss.service_unid = se.unid Where 1 = 1";
		if (top != null) {
			if (top == 1) {
				condition.setWorkStatus(null);
				jpql += " AND sp.work_status like '推进中%' AND sp.service_status in ('立项', '督办')";
			}else if (top == 2) {
				//top=5推进中的超期办理的事项
				String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
				condition.setWorkStatus(null);
				jpql += " AND sp.next_time<" + "'" +  currentData + "'" + "  AND sp.work_status like '推进中%' AND sp.service_status in ('立项', '督办')";
			} else if (top == 3) {
				condition.setWorkStatus("已完成");
			} else if (top == 4) {
				condition.setWorkStatus("工作中止");
			} else if (top == 5) {
				condition.setWorkStatus("工作终止");
			}
		}
		condition.setOverseeUserid(null);
//		jpql += " AND se.oversee_userid = " + "'" + condition.getUserId() +  "'" ;
		String conditionsql = jpql + getConditionsql(condition);
		Query query = em.createNativeQuery(conditionsql);
		query.unwrap(NativeQueryImpl.class)
				.setResultTransformer(Transformers.aliasToBean(SponsorServiceItemModel.class));
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		return query.getResultList();
	}
	
	/**
	 * 我的督办事项数量
	 * 
	 * @return
	 */
	@Override
	public int getCountWithMySuperService(SuperCondition condition, Integer top,Integer tabPage) {
		int count = 0;
		String jpql = "select count(sp.unid) from super_sponsor sp LEFT JOIN  super_service se on se.unid = sp.service_unid "
				+ " LEFT JOIN super_service_oversee_mapping ss ON ss.service_unid = se.unid Where 1 = 1";
		if (top != null) {
			if (top == 1) {
				condition.setWorkStatus(null);
				jpql += " AND sp.work_status like '推进中%' AND sp.service_status in ('立项', '督办')";
			}else if (top == 2) {
				//top=2推进中的超期办理的事项
				String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
				condition.setWorkStatus(null);
				jpql += " AND sp.next_time<" + "'" +  currentData + "'" + "  AND sp.work_status like '推进中%' AND sp.service_status in ('立项', '督办')";
			} else if (top == 3) {
				condition.setWorkStatus("已完成");
			} else if (top == 4) {
				condition.setWorkStatus("工作中止");
			}
//			else if (top == 5) {
//				condition.setWorkStatus("工作终止");
//			}

			else if (top == 6){
                String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
				condition.setWorkStatus(null);
				jpql += " and sp.proposed_closing_time < '" + currentData +"' AND sp.work_status like '推进中%'  ";
			}else if(top == 7){
                String currentData = DateUtil.getOutTime(6);
                condition.setWorkStatus(null);
                jpql += " and sp.create_time < '"+currentData+"' AND sp.work_status like '推进中%' ";
			}
		}
		if(tabPage != null){
			if (tabPage == 1){
				//查询全部
//				condition.setOverseeUserid(null);
//				jpql += " AND se.oversee_userid = " + "'" + condition.getUserId() +  "'" ;
			}else if (tabPage == 2){
				//本单位主办
				jpql += " and sp.org_id in (select s.userid from super_lc_sponsor s,om_user u where s.userid = u.USERID and u.DEPTORGID = "+condition.getOrgId()+") ";
			}else if (tabPage == 3){
				//本科室主办
				jpql += " and ms.office_Id = "+condition.getOfficeOrgId() ;
			}else if (tabPage == 4){
				//我的主办
				jpql += " and sp.sponsor_id = '"+condition.getUserId()+"' ";
			}
		}
		String conditionsql = jpql + getConditionsql(condition);
		Query querycount = em.createNativeQuery(conditionsql);
		List<BigInteger> counts = querycount.getResultList();
		if (counts != null && counts.size() > 0) {
			count = counts.get(0).intValue();
		}
		return count;
	}

	//jinzhao -- 2019-12-4  分管单位主办事项 本单位主办事项 本科室主办事项 我的主办事项 领导出访布置工作 我的督办 导出字段数据修改
	@Override
	public List<ServiceModel> getSuperServic(Integer page, Integer size, SuperCondition condition, Integer top, Integer type) {
		List<ServiceModel> list = null;
		List<ServiceModel> arrayList = new ArrayList<>();
		String jpql = "SELECT sp.unid,sp.service_id serviceId,ty.type_name typeName,"
				+ "GROUP_CONCAT(IFNULL(DATE_FORMAT(ss.service_time, '%Y-%m-%d'),' ') SEPARATOR'@_@') serviceTime,"
				+ "GROUP_CONCAT(IFNULL(ss.service_name, ' ') SEPARATOR'@_@') serviceName,"
				+ "GROUP_CONCAT(IFNULL(ss.command_leader, ' ') SEPARATOR'@_@') commandLeader,"
				+ "GROUP_CONCAT(IFNULL(ss.task_note, ' ') SEPARATOR'@_@') taskNote,"
				+ "sp.org_name orgName,sp.sponsor_name sponsorName,DATE_FORMAT(sp.proposed_closing_time,'%Y-%m-%d') proposedClosingTime,"
				+ "sp.result_form resultForm,sp.work_plan workPlan,DATE_FORMAT(sp.feedback_time,'%Y-%m-%d') feedbackTime,sp.work_status workStatus,"
				+ "sp.progress,se.oversee_frequency overseeFrequency,sp.is_read as isRead FROM super_sponsor sp LEFT JOIN super_service se ON se.unid = sp.service_unid LEFT JOIN super_sponsor_master ms ON sp.unid = ms.sponsor_unid "
				+ "LEFT JOIN super_type_service ty ON se.service_type = ty.unid LEFT JOIN super_service_oversee_mapping ss "
				+ "ON (ss.service_unid = sp.service_unid AND ss.`status` = '1') Where 1 = 1 ";
		if (top != null) {
			if (top == 1) {
				condition.setWorkStatus(null);
				jpql += " AND sp.work_status like '推进中%' AND sp.service_status in ('立项', '督办')";
			} else if (top == 2) {
				condition.setWorkStatus("已完成");
			} else if (top == 3) {
				condition.setWorkStatus("工作中止");
			} else if (top == 4) {
				condition.setWorkStatus(null);
				jpql += " AND (sp.feedback_deadline is not null AND sp.feedback_deadline < IFNULL(feedback_time,CURDATE()) )";
			}else if (top == 5) {
				//top=5推进中的超期办理的事项
				String currentData = DateUtil.format(DateUtil.getCurrentDateStart(), "yyyy-MM-dd");
				condition.setWorkStatus(null);
				jpql += " AND sp.next_time<" + "'" +  currentData + "'" + "  AND sp.work_status like '推进中%' AND sp.service_status in ('立项', '督办')";
			}
		}
		if(type != null){
			if(type == 1){
				//查询全部
			}
			else if(type == 2){
				// 本单位主办事项
				jpql += " AND sp.org_id IN (SELECT s.userid FROM super_lc_sponsor s,om_user u " +
						" WHERE s.userid = u.USERID AND u.DEPTORGID = "+condition.getOrgId()+")";
			}
			else if(type == 3){
				//本科室主办事项
				jpql += " AND ms.office_Id =" + condition.getOfficeOrgId();
			}else if(type == 4){
				//我的主办事项
				jpql += " AND sp.sponsor_id =" + "'" + condition.getUserId() + "'" ;
			}else if(type == 5){
				//领导出访布置工作
				condition.setOverseeUnid("9913");
			}else if(type == 6){
				//我的督办事项
//				jpql += " AND se.oversee_userid  =" + "'" + condition.getUserId() + "'" ;
			}
		}
		String conditionsql = jpql + getConditionsql(condition);
		conditionsql += " GROUP BY sp.unid order by se.create_time DESC,se.unid DESC,sp.service_id ";
		Query query = em.createNativeQuery(conditionsql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServiceModel.class));
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		list = query.getResultList();
		if (ToolUtil.isEmpty(list)) {
			return list;
		}
		for (ServiceModel model : list) {
//                if (ToolUtil.isEmpty(model) || !model.getServiceTime().contains("@_@")) {
//                    continue;
//                }
			String[] serviceTimes = model.getServiceTime().split("@_@");
			String[] serviceNames = model.getServiceName().split("@_@");
			String[] taskNotes = model.getTaskNote().split("@_@");
			String[] commandLeaders = model.getCommandLeader().split("@_@");
			model.setServiceTime(getStr(serviceTimes));
			model.setServiceName(getStr(serviceNames));
			model.setTaskNote(getStr(taskNotes));
			model.setCommandLeader(getStr(commandLeaders));
//			//修改导出台账为空的问题
//			String sqlq = "select date_format(sl.feedback_time,'%Y-%m-%d') feedbackTime,sl.work_status as workStatus,sl.progress  from super_sponsor_log sl where sl.sponsor_unid = '" + model.getUnid() + "' and sl.status = '1' and sl.work_status is not null and sl.work_status <> '' order by sl.create_time desc limit 1 ";
//			Query query1 = em.createNativeQuery(sqlq);
//			query1.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperSponsorLog.class));
//			List<SuperSponsorLog> superSponsorLogList = query1.getResultList();
////            List<SuperSponsorLog> superSponsorLogList = superSponsorLogRepository.findSponsorLog(model.getUnid());
//			ServiceModel serviceModel = new ServiceModel();
//
//			if (ToolUtil.isNotEmpty(superSponsorLogList)) {
//				SuperSponsorLog superSponsorLog = superSponsorLogList.get(0);
//
//				serviceModel.setFeedbackTime(superSponsorLog.getFeedbackTime());
//				serviceModel.setWorkStatus(superSponsorLog.getWorkStatus());
//				serviceModel.setProgress(superSponsorLog.getProgress());
//			}
//			if (ToolUtil.isEmpty(superSponsorLogList)) {
//				serviceModel.setFeedbackTime(model.getFeedbackTime());
//				serviceModel.setWorkStatus(model.getWorkStatus());
//				serviceModel.setProgress(model.getProgress());
//			}
//			serviceModel.setUnid(model.getUnid());
//			serviceModel.setServiceId(model.getServiceId());
//			serviceModel.setTypeName(model.getTypeName());
//			serviceModel.setServiceTime(model.getServiceTime());
//			serviceModel.setServiceName(model.getServiceName());
//			serviceModel.setCommandLeader(model.getCommandLeader());
//			serviceModel.setTaskNote(model.getTaskNote());
//			serviceModel.setOrgName(model.getOrgName());
//			serviceModel.setSponsorName(model.getSponsorName());
//			serviceModel.setProposedClosingTime(model.getProposedClosingTime());
//			serviceModel.setResultForm(model.getResultForm());
//			serviceModel.setWorkPlan(model.getWorkPlan());
//			serviceModel.setOverseeFrequency(model.getOverseeFrequency());
//			arrayList.add(serviceModel);
		}
		return list;
	}
	private String getStr(String[] field) {
		String str = "";
		if (ToolUtil.isNotEmpty(field)) {
			for (int i = 0; i < field.length; i++) {
				if (i < field.length - 1) {
					str += (i + 1) + "." + field[i] + "\n";
				} else {
					str += (i + 1) + "." + field[i];
				}
			}
		}
		return str;
	}
}
