package com.unionpay.sms.service.impl;

import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.sms.dao.OmUserAddressbookRepository;
import com.unionpay.sms.dao.SmsReceiverRepository;
import com.unionpay.sms.domain.OmUserAddressbook;
import com.unionpay.sms.domain.smsReceiver;
import com.unionpay.sms.model.SmsOmOrganization;
import com.unionpay.sms.model.SmsReceiverResult;
import com.unionpay.sms.model.SmsReceiverToDoModel;
import com.unionpay.sms.service.SmsReceiverService;
import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.domain.OmOrganization;
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
import java.math.BigInteger;
import java.util.*;

/**
 * 短信发送_接收人的service实现类
 * 
 * @author wangyue
 * @date 2018-12-10
 */
@Service
public class smsReceiverServiceImpl implements SmsReceiverService {
	@Autowired
	private SmsReceiverRepository smsReceiverRepository;
	@Autowired
	private OmUserAddressbookRepository omUserAddressbookRepository;
	@Autowired
	private OMOrganizationRepository omOrganizationRepository;
	@PersistenceContext
	private EntityManager em;

	private String jpqlModel = "select sm.receiver_name receiverName,sm.receiver_tel receiverTel"
			+ ",sm.org_level orgLevel from sms_receiver sm";

	private final Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 * 查询短信接收人信息列表
	 * @param currentPage
	 * @param size
	 * @param unid
	 * @return
	 */
	public List<SmsReceiverResult> findByMatterUnid(Integer currentPage, Integer size, String unid) {
		String jpql = "select sm.unid unid,sm.receiver_name receiverName,sm.receiver_tel receiverTel,sm.type type,sm.org_name orgName"
				+ ",DATE_FORMAT(sm.send_time,'%Y-%m-%d %H:%i:%S') sendTime,sm.status status FROM sms_receiver sm where sm.matter_unid =:matterUnid "
				+ "ORDER BY sm.org_name,sm.receiver_name";
		Query query = em.createNativeQuery(jpql);
		query.setParameter("matterUnid",unid);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsReceiverResult.class));
		query.setFirstResult((currentPage - 1) * size);
		query.setMaxResults(size);
		List<SmsReceiverResult> list = query.getResultList();
		return list;
	}

	/**
	 * 接收人插入
	 * 
	 * @author wangyue
	 */
	@Override
	@Transactional
	public void saveSmsReceiver(smsReceiver smsReceiver) {
		smsReceiverRepository.save(smsReceiver);
	}

	/**
	 * 添加短信接收人（按userid添加）
	 * @param empIds
	 * @param unid
	 * @author lishuang
	 * @date 2018-12-24
	 */
	@Override
	public String saveInSmsReceiver(List<String> empIds,String unid) {
		String invalidOprator = "";
		for (String empId:empIds){
			OmUserAddressbook omUser = omUserAddressbookRepository.findByEmpid(Integer.parseInt(empId));
			OmOrganization omOrganization = null;
			if (ToolUtil.isNotEmpty(omUser)){
				omOrganization = omOrganizationRepository.findByOrgid(omUser.getOrgid());
				String regix = "^(1[3|4|5|7|8|9])\\d{9}$";
				if (ToolUtil.isEmpty(omUser.getMobileno())//手机号码为空
						||omUser.getMobileno().length()<11//手机号码长度小于11位
						||(omUser.getMobileno().length()>11 && (!omUser.getMobileno().substring(0,11).matches(regix)))//手机号码长度大于11，且截取前11位后不匹配正则
						||(omUser.getMobileno().length()==11&&(!omUser.getMobileno().matches(regix)))//手机号码长度为11位，且不匹配正则
				){
					invalidOprator = invalidOprator.concat(omUser.getUserid()+",");
					continue;
				}
				smsReceiver smsReceiver = new smsReceiver();
				System.out.println("omUser==="+omUser.getMobileno());
				smsReceiver.setReceiverTel(omUser.getMobileno().substring(0,11));
				smsReceiver.setOrgId(omUser.getOrgid());
				smsReceiver.setOrgName(omOrganization.getOrgname());
				smsReceiver.setUnid(UUID.randomUUID().toString());
				smsReceiver.setReceiverId(omUser.getEmpid().toString());
				smsReceiver.setReceiverName(omUser.getEmpname());
				smsReceiver.setOrgLevel(omOrganization.getOrglevel());
				smsReceiver.setOrgRemark(omUser.getRemark());
				smsReceiver.setMatterUnid(unid);
				smsReceiver.setType("IN");
				smsReceiver.setStatus("待发送");
				smsReceiverRepository.save(smsReceiver);
				//list.add(smsReceiver);
			}
		}
		//smsReceiverRepository.saveAll(list);
		if (invalidOprator.length()>0){
			invalidOprator = invalidOprator.substring(0,invalidOprator.lastIndexOf(","));
			throw new BussinessException(501,"用户"+invalidOprator+"添加失败，手机号码不符合要求");
		}
		return invalidOprator;
	}

	/**
	 * 申请发送-->删除短信接收人(批量删除)
	 * @param unid
	 * @author lishuang
	 * @date 2018-12-25
	 */
	@Transactional
	public void deleteByUnid(String[] unid){
		for (String str : unid){
			if (ToolUtil.isNotEmpty(str)){
				smsReceiverRepository.deleteById(str);
			}
		}
	}

	/**
	 * 申请发送-->查询内部接收人
	 * @param unid
	 * @return
	 * @author lishuang
	 * @date 2018-12-25
	 */
	public List<smsReceiver> findInSmsReceivers(String unid){
		String sql = "SELECT sms.unid,sms.receiver_id receiverId,sms.receiver_name receiverName,sms.receiver_tel receiverTel,"
				+ "sms.org_name orgName FROM `sms_receiver` sms LEFT JOIN om_organization om ON om.ORGID=sms.org_id LEFT JOIN "
				+ "om_user ou ON ou.USERID = sms.receiver_id WHERE sms.type = 'IN' AND sms.matter_unid =:unid ORDER BY om.SORTNO,ou.SORTNO";
		Query query = em.createNativeQuery(sql);
		query.setParameter("unid",unid);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(smsReceiver.class));
		List<smsReceiver> receivers = query.getResultList();
		return receivers;
	}

	/**
	 * 申请发送-->查询内部接收人总数
	 * @param unid
	 * @return
	 */
	public Integer findInSmsReceiversTotal(String unid){
		int count = 0;
		String sql = "SELECT count(sms.unid) FROM `sms_receiver` sms WHERE sms.matter_unid =:unid";
		Query querycount = em.createNativeQuery(sql);
		querycount.setParameter("unid",unid);
		List<BigInteger> counts = querycount.getResultList();
		if (counts != null && counts.size() > 0) {
			count = counts.get(0).intValue();
		}
		return count;
	}

	/**
     * 申请发送-->查询已添加内部部门、科室、内部接收人及对应的数量
	 * @param unid
     * @return
	 * @author lishuang
	 * @date 2019-01-03
     */
	public Map<String,Object> findOmOrganizationNameAndTotal(String unid){
		Map<String,Object> data = new LinkedHashMap<>();
		//查询所有内部接收人所属的部门信息
		String sql = "SELECT om.orgid orgid,om.orgname orgname,om.orgtype orgtype,om.parentorgid parentorgid from sms_receiver sm " +
				"LEFT JOIN om_organization om on sm.org_id = om.orgid where sm.type = 'IN' AND sm.matter_unid =:unid";
		Query query = em.createNativeQuery(sql);
		query.setParameter("unid",unid);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsOmOrganization.class));
		List<SmsOmOrganization> list=query.getResultList();
		//声明部门集合
		List<String> deptName = new ArrayList<>();
		//声明科室集合
		List<String> orgName = new ArrayList<>();
		if(ToolUtil.isNotEmpty(list)){
			for (SmsOmOrganization smsOmOrganization:list){
				//判断是科室还是部门，然后归类
				if (smsOmOrganization.getOrgtype().equals("05")&&!orgName.contains(smsOmOrganization.getOrgname())){
					orgName.add(smsOmOrganization.getOrgname());
					OmOrganization omOrganization = omOrganizationRepository.findByOrgid(smsOmOrganization.getParentorgid());
					if (ToolUtil.isNotEmpty(omOrganization)&&!deptName.contains(omOrganization.getOrgname())){
						deptName.add(omOrganization.getOrgname());
					}
				}
				if (smsOmOrganization.getOrgtype().equals("04")&&!deptName.contains(smsOmOrganization.getOrgname())){
					deptName.add(smsOmOrganization.getOrgname());
				}
			}
		}
		//部门名称
		data.put("deptName",deptName);
		//部门数量
		data.put("deptTotal",deptName.size());
		//科室名称
		data.put("orgName",orgName);
		//科室数量
		data.put("orgTotal",orgName.size());
		//内部接收人总人数
		data.put("receiverTotal",list.size());
		return data;
    }

	/**
	 * 申请发送-->添加外部短信接收人
	 * @param unid
	 * @param name
	 * @param tel
	 * @param company
	 * @author lishuang
	 * @date 2018-12-24
	 */
	@Transactional
	public void saveOutSmsReceiver(String unid,String name,String tel,String company){
		smsReceiver smsReceiver = new smsReceiver();
		smsReceiver.setUnid(UUID.randomUUID().toString());
		smsReceiver.setReceiverId(UUID.randomUUID().toString());
		smsReceiver.setReceiverName(name);
		smsReceiver.setReceiverTel(tel);
		smsReceiver.setOrgName(company);
		smsReceiver.setMatterUnid(unid);
		smsReceiver.setType("OUT");
		smsReceiver.setStatus("待发送");
		smsReceiverRepository.save(smsReceiver);
	}

    /**
     * 申请发送-->查询外部接收人
	 * @param unid
     * @return
     * @author lishuang
     * @date 2018-12-25
     */
    public List<smsReceiver> findOutSmsReceivers(String unid){
		String sql = "SELECT sms.unid,sms.receiver_name receiverName,sms.receiver_tel receiverTel,sms.org_name orgName"
				+ " FROM `sms_receiver` sms WHERE sms.type = 'OUT' AND sms.matter_unid =:unid ORDER BY sms.org_name,sms.receiver_name";
		Query query = em.createNativeQuery(sql);
		query.setParameter("unid",unid);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(smsReceiver.class));
		List<smsReceiver> receivers = query.getResultList();
		return receivers;
    }

    /**
     * 申请发送-->查询外部接收人总数
     * @return
     */
    @Override
    public Integer findOutSmsReceiverTotal(String unid) {
    	int count = 0;
		String sql = "SELECT count(sms.unid) FROM `sms_receiver` sms WHERE sms.type = 'OUT' AND sms.matter_unid =:unid";
		Query querycount = em.createNativeQuery(sql);
		querycount.setParameter("unid",unid);
		List<BigInteger> counts = querycount.getResultList();
		if (counts != null && counts.size() > 0) {
			count = counts.get(0).intValue();
		}
    	return count;
    }

	/**
	 * 申请发送-->查询所有接收人
	 * @param matterUnid
	 * @return
	 * @author lishuang
	 * @date 2018-12-25
	 */
	public List<smsReceiver> findAllSmsReceiver(String matterUnid){return smsReceiverRepository.findByMatterUnid(matterUnid);}

	/**
	 * 申请发送-->查询所有接收人总数
	 * @param matter_unid
	 * @return
	 * @author lishuang
	 * @date 2018-12-25
	 */
	public Integer findAllSmsReceiverTotal(String matter_unid){return smsReceiverRepository.findTotalByMatterUnid(matter_unid);}

	/**
	 * 生成模板的数据查询
	 * 
	 * @author wangyue
	 */
	public List<SmsReceiverToDoModel> SuperServiceReadyToDo(Integer currentPage, Integer size) {
		List<SmsReceiverToDoModel> list = null;
		Query query = em.createNativeQuery(jpqlModel);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsReceiverToDoModel.class));
		query.setFirstResult((currentPage - 1) * size);
		query.setMaxResults(size);
		list = query.getResultList();
		return list;
	}

	/**
	 * 根据orgid查询部门下所有员工userid（包括递归查询所有子部门员工userid）
	 * @param orgIds
	 * @return
	 * @author lishuang
	 * @date 2019-04-23
	 */
	public List<String> getAllEmpId(List<String> orgIds){
		Set<String> empIds = new HashSet<>();
		for (String orgid : orgIds){
			if (ToolUtil.isNotEmpty(orgid)){
				String id = orgid.replace("o","");
				List<String> ids = getAllStaff(id);
				if (!empIds.containsAll(ids)){
					empIds.addAll(ids);
				}
			}
		}
		return new ArrayList<>(empIds);
	}
	private List<String> getAllStaff(String orgId){
		//当前部门下员工
		List<String> empids = new ArrayList<>();
		OmOrganization omOrganization = omOrganizationRepository.findByOrgid(Integer.parseInt(orgId));
		if (ToolUtil.isEmpty(omOrganization)){
			return empids;
		}
		if (omOrganization.getIsleaf().equals("Y")){
			String sql = "SELECT om.EMPID empid FROM `om_user_addressbook` om where ORGID =:orgid and om.EMPSTATUS <> '00'";
			Query query = em.createNativeQuery(sql);
			query.setParameter("orgid",omOrganization.getOrgid());
			query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(OmUserAddressbook.class));
			List<OmUserAddressbook> users = query.getResultList();
			if (ToolUtil.isNotEmpty(users)){
				for (OmUserAddressbook user:users){
					//OmUserAddressbook user1 = (OmUserAddressbook)user;
					empids.add(user.getEmpid().toString());
				}
			}
		}else {
			List<OmOrganization> omOrganizations = omOrganizationRepository.findByParentorgidAndStatus(omOrganization.getOrgid(),"0");
			for (OmOrganization organization : omOrganizations){
				String s = organization.getOrgid()+"";
				List<String> list = getAllStaff(s);
				empids.addAll(list);
			}
		}
		return empids;
	}

	public Map getAll(){
		Map map = new HashMap();
		String sql = "SELECT orgid,orgname,orgtype,parentorgid,orglevel FROM `om_organization`";
		Query query = em.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(OmOrganization.class));
		List<OmOrganization> omOrganizations = query.getResultList();



		map.put("total",omOrganizations.size());

		return map;
	}

	public static void main(String[] args) {
		System.out.println("HelloWorld!");
	}

}
