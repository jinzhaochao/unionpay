package com.unionpay.supervision.dao;

import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.model.SponsorList;
import com.unionpay.supervision.model.SuperSponserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiongym
 * 督办部门表
 */
@Repository
public interface SuperSponsorRepository extends JpaRepository<SuperSponsor,String>{
	/**
	 * 根据事项查询主办单位
	 */
	List<SuperSponsor> findByServiceUnidOrderByServiceId(String serviceUnid);
	/**
	 * 根据事项查询主办单位
	 */
	List<SuperSponsor> findByServiceUnidAndStatusOrderByServiceId(String serviceUnid, Integer status);

	SuperSponsor findByUnid(String unid);
	/**
	 * 根据事项编号查询主办单位
	 */
	SuperSponsor findByServiceId(String serviceId);


	List<SuperSponsor> findByServiceUnid(String serviceUnid);

	@Query(value = "select GROUP_CONCAT(sp.org_name) orgName from super_sponsor sp where sp.service_unid = ?1",nativeQuery = true)
	String findSuperServiceByUnid(String unid);
}
