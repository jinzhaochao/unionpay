package com.unionpay.supervision.service;

import java.util.List;

import com.unionpay.supervision.model.*;

/**
 * 部门综合事项查询接口
 */
public interface SponsorServiceDetailService {
	/**
	 * 待督办查询带分页
	 * 
	 * @param page
	 *            当前页
	 * @param size
	 *            页数的大小
	 * @return
	 */
	public List<SuperProject> getSuperCreateProjectFind(Integer page, Integer size, SuperCondition condition);

	/**
	 * 查询总条数；
	 */
	public Integer getCountWithSuperCreateProjectFind(Integer page, Integer size, SuperCondition condition);

	/**
	 * 我的待办状态的判断
	 * 
	 * @param userId
	 * @param top
	 * @return
	 */
	public List<SuperMessage> getSuperMessageCheck(Integer top, String userId);

	/**
	 * 查询我的待办已办数据，数据导出
	 * 
	 * @return
	 */
	public List<SponsorServiceReadyToDoModel> getSuperServiceReadyToDo(Integer page, Integer size,
			SuperCondition condition, Integer top);

	/**
	 * 我的待办已办数据查询数量
	 * 
	 * @return
	 */
	public int getCountWithSuperServiceReadyToDo(SuperCondition condition, Integer top);

	/**
	 * 事项综合查询
	 * 
	 * @return
	 */
	public List<SponsorServiceItemModel> getSuperServiceItemSynthesis(Integer page, Integer size,
			SuperCondition condition,Integer top,Integer type);

	/**
	 * 事项综合查询数量
	 * 
	 * @return
	 */
	public int getCountWithSuperServiceItemSynthesis(SuperCondition condition,Integer top,Integer type);

	/**
	 * 我的待办已办、督办事项查询，自定义事项大类下拉框数据
	 * @return
	 * @author lishuang
	 * @date 2019-04-11
	 */
	public List<SuperTypeOverseeModel> getSelfDefineType();
	
	/**
	 * 我的督办事项，数据导出
	 */
	public List<SponsorServiceItemModel> getMySuperService(Integer page, Integer size,
			SuperCondition condition, Integer top) ;
	
	/**
	 * 我的督办事项查询数量
	 */
	public int getCountWithMySuperService(SuperCondition condition, Integer top,Integer tabPage);

	List<ServiceModel> getSuperServic(Integer page, Integer size, SuperCondition condition, Integer top, Integer type);
}
