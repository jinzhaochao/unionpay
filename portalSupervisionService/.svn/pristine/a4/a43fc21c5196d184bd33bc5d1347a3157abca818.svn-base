package com.unionpay.supervision.service;

import java.util.List;
import java.util.Optional;

import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.domain.SuperTypeOversee;
import com.unionpay.supervision.model.OverseeMappingDto;

/**
 * 
 * @author 11760
 * @Service
 * 督办类型的接口
 */
public interface SuperTypeOverseeService {
    /**
     * 督办类型的新增
     * @param SuperTypeOversee
     */
	public SuperTypeOversee SuperTypeOverseeInsert(SuperTypeOversee SuperTypeOversee);
	/**
	 * 督办类型的删除
	 * @param SuperTypeOversee
	 */
	public void DeleteSuperTypeOversee(SuperTypeOversee SuperTypeOversee);
	/**
	 * 督办类型的修改
	 * @param SuperTypeOversee
	 */
	public void UpdateSuperTypeOverseeUpdate(SuperTypeOversee SuperTypeOversee);
	/**
	 * 督办类型的查找
	 * @return
	 */
	public List<SuperTypeOversee> SuperTypeOverseeFind();
	/**
	 * 通过主键查找
	 * @param SuperTypeOversee
	 * @return
	 */
	public SuperTypeOversee getFindById(SuperTypeOversee SuperTypeOversee);
	
	/**
     * 查询最近督办类型
     */
	public SuperTypeOversee findOneOrderByTypeSort();
	
	/**
	 * 督办类型的按名称查找
	 * @param typeName
	 * @return
	 */
	public List<SuperTypeOversee> findByTypeName(String typeName);

	/**
	 * 督办类型的按编码查找
	 * @param typeId
	 * @return
	 * @author lishuang
	 * @date 2019-04-03
	 */
	public List<SuperTypeOversee> findByTypeId(String typeId);
	/**
	 * 督办类型的新增
	 * @param unid
	 * @param typeName
	 * @return
	 */
	public  SuperServiceOverseeMapping SuperTypeOverseeNewInsert(String unid,String typeName);
	
	/**
	 * 新增任务来源
	 */
	public void addTaskSource(OverseeMappingDto overseeMappingDto);
}
