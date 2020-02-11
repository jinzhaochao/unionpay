package com.unionpay.supervision.serviceImpl;

import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperServiceOverseeMappingRepository;
import com.unionpay.supervision.domain.SuperService;
import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.domain.SuperTypeOversee;
import com.unionpay.supervision.model.OverseeTypeList;
import com.unionpay.supervision.model.SuperOverseeMappingDto;
import com.unionpay.supervision.service.SuperServiceOverseeMappingService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


/**
 * <p>
 *  督办事项与督办类型关联实现类
 * </p>
 *
 * @author xiongym
 * @since 2018-11-02
 */
@Service
@Transactional
public class SuperServiceOverseeMappingServiceImpl implements SuperServiceOverseeMappingService {
	
	@Autowired
    private SuperServiceOverseeMappingRepository superServiceOverseeMappingRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<SuperServiceOverseeMapping> findByServiceUnidAndIsPrimary(String serviceUnid, String isPrimary) {
		if(ToolUtil.isEmpty(isPrimary)){
			return superServiceOverseeMappingRepository.findByServiceUnid(serviceUnid);
		}else{
			return superServiceOverseeMappingRepository.findByServiceUnidAndIsPrimary(serviceUnid,isPrimary);
		}
	}

	/**
	 * 根据事项id查找
	 * @param serviceUnid
	 * @return
	 * @author lishuang
	 * @date 2019-04-09
	 */
	public List<SuperServiceOverseeMapping> findAllByServiceUnid(String serviceUnid){
		return superServiceOverseeMappingRepository.findByServiceUnid(serviceUnid);
	}

	/**
	 * 根据事项id查找
	 * @param unid
	 * @return
	 * @author lishuang
	 * @date 2019-04-12
	 */
	public SuperServiceOverseeMapping findByUnid(String unid){
		return superServiceOverseeMappingRepository.findByUnid(unid);
	}

	/**
	 * 根据事项id查找
	 * @param serviceUnid
	 * @return
	 * @author lishuang
	 * @date 2019-04-09
	 */
	public List<SuperOverseeMappingDto> findByServiceUnid(String serviceUnid){
		String sql = "SELECT ssom.unid,ssom.oversee_name overseeName,ssom.service_name serviceName,"
				+ "DATE_FORMAT(ssom.service_time,'%Y-%m-%d') createTime FROM `super_service_oversee_mapping` ssom "
				+ "WHERE ssom.service_unid =:serviceUnid and ssom.status = '1' ORDER BY ssom.create_time";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("serviceUnid",serviceUnid);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperOverseeMappingDto.class));
		List<SuperOverseeMappingDto> list = query.getResultList();
		return list;
	}

	/**
	    * 督办类型置为无效
	    * @param superServiceId
	    * @return
	    */
	@Override
	public void updateStatus(String superServiceId, String isPrimary) {
		String sql= null ;
		if(ToolUtil.isNotEmpty(superServiceId)){
			if(ToolUtil.isNotEmpty(isPrimary)){
				sql = "UPDATE super_service_oversee_mapping AS oversee set oversee.status = 0 WHERE oversee.service_unid = " + "'"+ superServiceId 
						+ "'" + " AND oversee.status = 1" + " AND oversee.is_primary = " +  "'" + isPrimary + "'";
			}else{
				sql = "UPDATE super_service_oversee_mapping AS oversee set oversee.status = 0 WHERE oversee.service_unid = " + "'"+ superServiceId 
						+ "'" + " AND oversee.status = 1";
			}
			
			Query query = entityManager.createNativeQuery(sql);
		    query.executeUpdate();
		}
		
	}
	
	/**
	    * 督办类型与事项关联添加
	    * @param superService
	    * @param superTypeOversee
	    * @return
	    */
	public void addSuperServiceOverseeMapping(SuperService superService, SuperTypeOversee superTypeOversee, String isprimary){
		if(superService != null){
			SuperServiceOverseeMapping superServiceOverseeMapping = new SuperServiceOverseeMapping();
			superServiceOverseeMapping.setUnid(UUID.randomUUID().toString());
			superServiceOverseeMapping.setServiceTime(superService.getServiceTime());
			superServiceOverseeMapping.setServiceName(superService.getServiceName());
			superServiceOverseeMapping.setServiceUnid(superService.getUnid());
			superServiceOverseeMapping.setTaskNote(superService.getTaskNote());
			superServiceOverseeMapping.setCommandLeader(superService.getCommandLeader());
			superServiceOverseeMapping.setCommandSource(superService.getCommandSource());
			superServiceOverseeMapping.setCreateTime(DateUtil.getTime());
			superServiceOverseeMapping.setCreateUserid(superService.getCreateUserid());
			superServiceOverseeMapping.setIsPrimary(isprimary);
			if(superTypeOversee != null){
				superServiceOverseeMapping.setOverseeName(superTypeOversee.getTypeName());//督办类型
				superServiceOverseeMapping.setOverseeUnid(superTypeOversee.getUnid());//督办类型名称
			}else{
				superServiceOverseeMapping.setOverseeName(superService.getOverseeName());//督办类型
				superServiceOverseeMapping.setOverseeUnid(superService.getOverseeUnid());//督办类型名称
			}
			superServiceOverseeMapping.setStatus(1);
			logger.info("insert---superServiceOverseeMapping=" + superServiceOverseeMapping.toString());
			superServiceOverseeMappingRepository.save(superServiceOverseeMapping);
		}
	}

	/**
     * 是否与督办类型关联
     * @param overseeUnid
     * @return
     */
	@Override
	public Boolean isContaOversee(String overseeUnid) {
		Boolean isContact = false;
		List<SuperServiceOverseeMapping> overSeelist =  superServiceOverseeMappingRepository.
				findByOverseeUnidAndStatus(overseeUnid, 1);
		if(overSeelist != null && overSeelist.size() > 0){
			isContact = true;
		}
		return isContact;
	}
	
	
	/**
	    * 督办类型与事项关联更新
	    * @param superServiceOverseeMapping
	    * @return
	    */
	public void save(SuperServiceOverseeMapping superServiceOverseeMapping){
		if(superServiceOverseeMapping != null){
			superServiceOverseeMappingRepository.save(superServiceOverseeMapping);
		}
	}
	
	/**
	    * 督办类型删除
	    * @param unid
	    * @return
	    */
	public void deleteById(String unid){
		if(ToolUtil.isNotEmpty(unid)){
			superServiceOverseeMappingRepository.deleteById(unid);
		}
	}

	/**
	 * 督办类型置合并
	 * @param removeServiceUnid
	 * @param targetServiceUnid
	 * @return
	 */
	@Override
	@Transactional
	public void combine(String removeServiceUnid, String targetServiceUnid) {
		if(ToolUtil.isEmpty(removeServiceUnid) || ToolUtil.isEmpty(targetServiceUnid)){
			return;
		}
		String sql= "UPDATE super_service_oversee_mapping AS oversee set oversee.is_primary = 'N', oversee.service_unid = " + "'"
				+ targetServiceUnid + "'" + " WHERE oversee.service_unid = " + "'"+ removeServiceUnid 	+ "'"
				+ " AND oversee.status = 1";
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();

	}

	@Override
	public List<SuperServiceOverseeMapping> findServiceUnid(String serviceUnid) {
		String sql = " select som.unid from super_service_oversee_mapping som where som.oversee_unid not in ('9913','9914','9915','9921') ";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperServiceOverseeMapping.class));
		return query.getResultList();
	}


}
