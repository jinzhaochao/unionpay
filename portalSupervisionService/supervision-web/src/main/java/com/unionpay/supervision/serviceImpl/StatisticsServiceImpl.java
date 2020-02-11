package com.unionpay.supervision.serviceImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.model.StatisticalDto;
import com.unionpay.supervision.model.StatisticsModel;
import com.unionpay.supervision.service.StatisticsService;

/**
 * <p>
 *  督办统计实现类
 * </p>
 *
 * @author xiongym
 * @since 2018-12-25
 */
@Service
public class StatisticsServiceImpl implements StatisticsService{
	
	@PersistenceContext
	private EntityManager em;

	
	/**
	 * 督办类型统计,无条件查询
	*/
	@Override
	public List<StatisticsModel> getOverSeeStatistics() {
		String hql = "SELECT type.type_name AS typeName,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status in ('立项','督办','完成','中止') "
				+ "AND sp.`status` = 1 THEN 1 ELSE 0 END ),0) AS totalService,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '立项' AND sp.`status` = 1 THEN 1 ELSE 0 END ),0) AS buildTotal,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '督办' AND sp.`status` = 1 THEN 1 ELSE 0 END ),0) AS supervisionTotal,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '完成' AND sp.`status` = 1 THEN 1 ELSE 0 END ),0) AS finshTotal,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '中止' AND sp.`status` = 1 THEN 1 ELSE 0 END ),0) AS stopTotal"
				+ " FROM super_type_oversee type LEFT JOIN super_service se ON se.oversee_unid = type.unid"
				+ " LEFT JOIN	super_sponsor sp ON sp.service_unid = se.unid"
				+ " GROUP BY type.type_name ORDER BY type.type_sort";
		
		Query query = em.createNativeQuery(hql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(StatisticsModel.class));
		return query.getResultList();
	}

	
	/**
	 * 事项类型统计,无条件查询
	*/
	@Override
	public List<StatisticsModel> getServiceTypeStatistics() {
		String hql = "SELECT type.type_name AS typeName,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status in ('立项','督办','完成','中止') "
				+ "AND sp.`status` = 1 THEN 1 ELSE 0 END ),0) AS totalService,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '立项' AND sp.`status` = 1 THEN 1 ELSE 0 END ),0) AS buildTotal,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '督办' AND sp.`status` = 1 THEN 1 ELSE 0 END ),0) AS supervisionTotal,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '完成' AND sp.`status` = 1 THEN 1 ELSE 0 END ),0) AS finshTotal,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '中止' AND sp.`status` = 1 THEN 1 ELSE 0 END ),0) AS stopTotal"
				+ " FROM super_type_service AS type LEFT JOIN super_service se ON se.service_type = type.unid "
				+ " LEFT JOIN super_sponsor sp ON sp.service_unid = se.unid"
				+ " GROUP BY type.type_name ORDER BY type.type_sort";
		
		Query query = em.createNativeQuery(hql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(StatisticsModel.class));
		return query.getResultList();
	}
	
	/**
	 * 事项类型统计,条件查询
	*/
	@Override
	public List<StatisticsModel> getServiceTypeStatistics(StatisticalDto statisticalDto) {
		String sql = "SELECT type.type_name AS typeName,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status in ('立项','督办','完成','中止') THEN 1 ELSE 0 END ),0) "
				+ "AS totalService,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '立项' THEN 1 ELSE 0 END ),0) AS buildTotal,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '督办' THEN 1 ELSE 0 END ),0) AS supervisionTotal,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '完成' THEN 1 ELSE 0 END ),0) AS finshTotal,"
				+ "IFNULL(SUM(CASE WHEN sp.service_status = '中止' THEN 1 ELSE 0 END ),0) AS stopTotal"
				+ " FROM super_type_service AS type LEFT JOIN super_service se ON se.service_type = type.unid "
				+ " LEFT JOIN super_sponsor sp ON sp.service_unid = se.unid"
				+ " WHERE sp.`status` = 1 ";
		String hql = sql +  getHql(statisticalDto);
		//分组排序
		hql +=  " GROUP BY type.type_name ORDER BY type.type_sort";
		Query query = em.createNativeQuery(hql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(StatisticsModel.class));
		return query.getResultList();
	}
	
	private String getHql(StatisticalDto statisticalDto){
		String conditionsql = "";
		// 督办类型
		if (ToolUtil.isNotEmpty(statisticalDto.getOverseeUnid())) {
			conditionsql += " AND se.oversee_unid = " + "'" + statisticalDto.getOverseeUnid() + "'";
		}
		// 事项类型
		if (ToolUtil.isNotEmpty(statisticalDto.getTypeName())) {
			conditionsql += " AND type.type_name like concat('%','" + statisticalDto.getTypeName() + "','%')";
		}
		// 事项编号
		if (ToolUtil.isNotEmpty(statisticalDto.getServiceId())) {
			conditionsql += " AND sp.service_id like concat('%','" + statisticalDto.getServiceId() + "','%')";
		}
		// 分公司领导
		if(ToolUtil.isNotEmpty(statisticalDto.getBranchedLeader())){
			conditionsql += " AND se.branched_leader like concat('%','" + statisticalDto.getBranchedLeader() + "','%')";
		}
		// 主办单位
		if (ToolUtil.isNotEmpty(statisticalDto.getOrgName())) {
			conditionsql += " AND sp.org_name like concat('%','" + statisticalDto.getOrgName() + "','%')";
		}
		// 主办人
		if (ToolUtil.isNotEmpty(statisticalDto.getSponsorName())) {
			conditionsql += " AND sp.sponsor_name like concat('%','" + statisticalDto.getSponsorName() + "','%')";
		}
		// 督办人
		if(ToolUtil.isNotEmpty(statisticalDto.getOverseeUsername())){
			conditionsql += " AND se.oversee_username like concat('%','" + statisticalDto.getOverseeUsername() + "','%')";
		}
		// 来源时间
		if ((ToolUtil.isNotEmpty(statisticalDto.getServiceStartTime()))) {
			conditionsql += " AND se.service_time >=" + "'" + statisticalDto.getServiceStartTime() + "'";
		}
		if ((ToolUtil.isNotEmpty(statisticalDto.getServiceEndTime()))) {
			conditionsql += " AND se.service_time <=" + "'" + statisticalDto.getServiceEndTime() + "'";
		}
	    return conditionsql;			
	}

}
