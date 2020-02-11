package com.unionpay.sms.service;

import com.unionpay.sms.domain.smsReceiver;
import com.unionpay.sms.model.SmsReceiverResult;
import com.unionpay.sms.model.SmsReceiverToDoModel;

import java.util.List;
import java.util.Map;


public interface SmsReceiverService {
	/**
	 * 查询短信接收人信息列表
	 * @param currentPage
	 * @param size
	 * @param unid
	 * @return
	 */
	List<SmsReceiverResult> findByMatterUnid(Integer currentPage, Integer size, String unid);

	void saveSmsReceiver(smsReceiver smsReceiver);

	List<SmsReceiverToDoModel> SuperServiceReadyToDo(Integer currentPage, Integer size);

	/**
	 * 申请发送-->添加短信接收人（内部人员）-->单个添加/全选添加
	 * @param userIds
	 * @param unid
	 * @author lishuang
	 * @date 2018-12-24
	 */
	public String saveInSmsReceiver(List<String> userIds,String unid);

	/**
	 * 申请发送-->添加短信外部接收人
	 * @param unid
	 * @param name
	 * @param tel
	 * @param company
	 * @author lishuang
	 * @date 2018-12-24
	 */
	public void saveOutSmsReceiver(String unid,String name,String tel,String company);

	/**
	 * 申请发送-->删除短信接收人(批量删除)
	 * @param unid
	 * @author lishuang
	 * @date 2018-12-25
	 */
	public void deleteByUnid(String[] unid);

	/**
	 * 申请发送-->查询内部接收人
	 * @param unid
	 * @return
	 * @author lishuang
	 * @date 2018-12-25
	 */
	public List<smsReceiver> findInSmsReceivers(String unid);

	/**
	 * 申请发送-->查询内部接收人总数
	 * @param unid
	 * @return
	 */
	public Integer findInSmsReceiversTotal(String unid);

	/**
	 * 申请发送-->查询外部接收人
	 * @param unid
	 * @return
	 * @author lishuang
	 * @date 2018-12-25
	 */
	public List<smsReceiver> findOutSmsReceivers(String unid);

	/**
	 * 申请发送-->查询外部接收人总数
	 * @param unid
	 * @return
	 */
	public Integer findOutSmsReceiverTotal(String unid);

	/**
	 * 申请发送-->查询所有接收人（根据matterUnid）
	 * @param matterUnid
	 * @return
	 * @author lishuang
	 * @date 2018-12-25
	 */
	public List<smsReceiver> findAllSmsReceiver(String matterUnid);

	/**
	 * 申请发送-->查询接收人总数（根据matter_unid）
	 * @param matter_unid
	 * @return
	 * @author lishuang
	 * @date 2018-12-25
	 */
	public Integer findAllSmsReceiverTotal(String matter_unid);

	/**
	 * 申请发送-->查询已添加的部门、科室及其总数
	 * @param unid
	 * @return
	 */
	public Map<String,Object> findOmOrganizationNameAndTotal(String unid);

	/**
	 * 根据已选部门orgid查找该部门下所有员工的userid
	 * @param orgIds
	 * @return
	 * @author lishuang
	 * @date 2019-04-23
	 */
	public List<String> getAllEmpId(List<String> orgIds);
}
