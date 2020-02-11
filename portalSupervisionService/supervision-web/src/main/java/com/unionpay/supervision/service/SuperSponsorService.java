package com.unionpay.supervision.service;

import com.unionpay.supervision.domain.SuperService;
import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.domain.SuperSponsorModel;
import com.unionpay.supervision.model.SponsorList;
import com.unionpay.supervision.model.SuperSponserDetail;
import com.unionpay.supervision.model.SuperSponserPage;
import com.unionpay.supervision.model.SuperSponsorOaSend;

import java.util.List;

public interface SuperSponsorService {

	/**
	 * 根据事项查询主办单位
	 */
	List<SuperSponsor> findByServiceUnid(String serviceUnid, Integer status);

	SuperSponsor findById(String unid);

	public SuperSponsor findByServiceId(String serviceId);

	void add(SuperSponsor superSponsor);

	void edit(SuperSponsor superSponsor);
	//事项合并时，添加主办部门
	void mergerAdd(SuperSponsor superSponsor, String unid);

	void delete(String unid);

	/**
	 * 督办部门置为无效
	 *
	 * @param superServiceId
	 * @return
	 */
	public void updateStatus(String superServiceId);

	/**
	 * 督办部门置为无效
	 *
	 * @param unid
	 * @return
	 */
	public void updateStatusByUnid(String unid);

	/**
	 * 督办部门置为无效
	 *
	 * @param unid
	 * @return
	 */
	public void invalidateStatusByUnid(String unid);

	/**
	 * 主办单位新增，不需要生成serviceId
	 */
	public void insert(SuperSponsor superSponsor);

	/**
	 * 部门事项的立项与督办
	 */
	public Boolean SuperServiceOperator(SuperSponsor superSponsor, int type, Integer frequency);

	/**
	 * 部门事项批量立项与督办
	 */
	public Boolean SuperServiceOperator(List<SuperSponsor> superSponsorlist, int type, Integer frequency, String flowTitle);

	/**
	 * 部门事项批量立项与督办,编辑页提交
	 */
	public Boolean SuperServiceOperator(List<SuperSponsor> superSponsorlist,
										List<SuperServiceOverseeMapping> superServiceOverseeMappinglist, SuperService superService, int type,
										Integer frequency, String flowTitle) ;

	List<SuperSponserPage> selectPage(String type, Integer deptId, String createTimeStart, String createTimeEnd, Integer currentPage, Integer size);

	Integer getCount(String type, Integer deptId, String createTimeStart, String createTimeEnd);


    SuperSponsorOaSend selectByWorkStatus(String sponsorUnid,String serviceUnid);

}
