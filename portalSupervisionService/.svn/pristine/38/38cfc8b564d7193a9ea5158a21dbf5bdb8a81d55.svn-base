package com.unionpay.supervision.service;

import com.unionpay.supervision.domain.SuperService;
import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.domain.SuperTypeOversee;
import com.unionpay.supervision.model.OverseeTypeList;
import com.unionpay.supervision.model.SuperOverseeMappingDto;

import java.util.List;

public interface SuperServiceOverseeMappingService {
	
	
	/**
     * 根据事项id查找
     * @param serviceUnid
     * @return
     */
	List<SuperServiceOverseeMapping> findByServiceUnidAndIsPrimary(String serviceUnid, String isPrimary);

	/**
	 * 根据事项id查找
	 * @param serviceUnid
	 * @return
	 * @author lishuang
	 * @date 2019-04-09
	 */
	List<SuperServiceOverseeMapping> findAllByServiceUnid(String serviceUnid);

	/**
	 * 根据事项id查找
	 * @param unid
	 * @return
	 * @author lishuang
	 * @date 2019-04-12
	 */
	SuperServiceOverseeMapping findByUnid(String unid);

	/**
	 * 根据事项id查找
	 * @param serviceUnid
	 * @return
	 * @author lishuang
	 * @date 2019-04-09
	 */
	List<SuperOverseeMappingDto> findByServiceUnid(String serviceUnid);

	/**
	    * 督办类型置为无效
	    * @param superServiceId
	    * @return
	    */
	public void updateStatus(String superServiceId, String isPrimary);

	/**
	    * 督办类型删除
	    * @param unid
	    * @return
	    */
	public void deleteById(String unid);

	/**
	    * 督办类型与事项关联添加
	    * @param superService
	    * @param superTypeOversee
	    * @return
	    */
	public void addSuperServiceOverseeMapping(SuperService superService, SuperTypeOversee superTypeOversee, String isprimary);
	
	/**
     * 是否与督办类型关联
     * @param overseeUnid
     * @return
     */
	public Boolean isContaOversee(String overseeUnid);
	
	/**
	    * 督办类型与事项关联保存
	    */
	public void save(SuperServiceOverseeMapping superServiceOverseeMapping);

	/**
	 * 督办类型置合并
	 * @param removeServiceUnid
	 * @param targetServiceUnid
	 * @return
	 */
	public void  combine(String removeServiceUnid, String targetServiceUnid);

	List<SuperServiceOverseeMapping> findServiceUnid(String serviceUnid);
}
