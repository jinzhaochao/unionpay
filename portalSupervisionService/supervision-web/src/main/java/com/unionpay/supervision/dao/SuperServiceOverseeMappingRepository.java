package com.unionpay.supervision.dao;
import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.model.OverseeTypeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 * 督办事项与督办类型关联表
 */
@Repository
public interface SuperServiceOverseeMappingRepository extends JpaRepository<SuperServiceOverseeMapping,String>{
	@Query(value = "select * from super_service_oversee_mapping where service_unid = ?1 and is_primary =?2 and status = '1' ",nativeQuery = true)
	List<SuperServiceOverseeMapping> findByServiceUnidAndIsPrimary(String serviceUnid, String isPrimary);

	@Query(value = "select * from super_service_oversee_mapping where service_unid = ?1 and status = '1' order by is_primary desc",nativeQuery = true)
	List<SuperServiceOverseeMapping> findByServiceUnid(String serviceUnid);

	SuperServiceOverseeMapping findByUnid(String unid);

	List<SuperServiceOverseeMapping> findByOverseeUnidAndStatus(String overseeUnid, int status);

	List<SuperServiceOverseeMapping> findByServiceUnidOrderByIsPrimaryDesc(String serviceUnid);

//查询来源时间最早的
    List<SuperServiceOverseeMapping> findByServiceUnidOrderByServiceTime(String serviceUnid);
}
