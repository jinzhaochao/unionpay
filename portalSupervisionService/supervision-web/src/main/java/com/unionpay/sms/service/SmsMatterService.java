package com.unionpay.sms.service;

import com.alibaba.fastjson.JSONArray;
import com.unionpay.common.exception.MyException;
import com.unionpay.sms.domain.smsMatter;
import com.unionpay.sms.model.SmsInfo;
import com.unionpay.sms.model.smsMatterCondition;
import com.unionpay.sms.utils.WMResult;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 
 * @author wangyue
 * @date 2018-12-7
 */
@Component
public interface SmsMatterService {

	WMResult findByUnidAndUpdate(String unid);

	smsMatter SmsMatterSave(smsMatter smsMatter);

	/**
	 * 查询短信申请记录总数
	 * @param userid
	 * @param smsMatterCondition
	 * @return
	 * @author lishuang
	 * @date 2019-04-24
	 */
	public Integer findSmsMatterTotal(String userid, smsMatterCondition smsMatterCondition);

	/**
	 * 查询短信申请记录列表
	 * @param currentPage
	 * @param size
	 * @param userid
	 * @param smsMatterCondition
	 * @return
	 * @author lishuang
	 * @date 2019-04-24
	 */
	public List<smsMatterCondition> getAllSmsMatter(Integer currentPage, Integer size, String userid, smsMatterCondition smsMatterCondition);

	/**
	 * 查询短信申请信息详情
	 * @param unid
	 * @return
	 * @author lishuang
	 * @date 2019-04-24
	 */
	public smsMatter findByUnid(String unid);

	/**
	 * 根据unid获取申请信息详情
	 * @param unid
	 * @return
	 */
	public smsMatter getByUnid(String unid);

	/**
	 * 部门及员工树信息(递归查询)
	 * @param orgid
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-04-18
	 */
	public JSONArray getDeptTree(Integer orgid);

	/**
	 * 申请发送-->提交和保存
	 * @param condition
	 * @param request
	 *
	 * @author lishuang
	 * @date 2018-12-24
	 */
	public smsMatter smsApplyAndSave(smsMatterCondition condition, HttpServletRequest request);

	/**
	 * 推送给流程平台审批
	 * @param smsMatter
	 * @return
	 * @throws MyException
	 */
	public Boolean sendApprove(smsMatter smsMatter) throws MyException;

	/**
	 * 查询所有短信申请记录主键unid
	 * @return
	 */
	public List<String> findUnid();

	/**
	 * 获取所有短信申请
	 * @return
	 */
	public List<SmsInfo> findAllSmsMatter();
}
