package com.unionpay.services.service;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.*;
import com.unionpay.services.model.ServiceInfoModelDto;
import com.unionpay.services.repository.ServerattachmentRepository;
import com.unionpay.services.repository.ServerhandlingmaterialRepository;
import com.unionpay.sms.dao.OmUserAddressbookRepository;
import com.unionpay.sms.domain.OmUserAddressbook;
import com.unionpay.sms.model.SmsOmOrganization;
import com.unionpay.sms.model.SmsOmUser;
import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.dao.OMUserRepository;
import com.unionpay.supervision.domain.OmOrganization;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.services.model.OnlineTotal;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unionpay.services.repository.ServerinfoRepository;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ServerinfoServiceImpl implements ServerInfoService{
	@Autowired
	private ServeracceptancedeptService serveracceptancedeptService;
	@Autowired
	private ServeracceptanceuserService serveracceptanceuserService;
	@Autowired
	private ServerattachmentflowchartService serverattachmentflowchartService;
	@Autowired
	private ServerhandlingmaterialService serverhandlingmaterialService;
    @Autowired
	private ServercommonproblemService servercommonproblemService;
	@Autowired
	private ServerdictService serverdictService;

	@Autowired
	private ServerinfoRepository serverinfo_modelRepository;
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private OMOrganizationRepository omOrganizationRepository;
	@Autowired
	private OMUserRepository omUserRepository;
	@Autowired
	private OmUserAddressbookRepository omUserAddressbookRepository;
	@Autowired
	private ServerattachmentRepository serverattachmentRepository;
	@Autowired
	private ServerhandlingmaterialRepository serverhandlingmaterialRepository;
	@Value("${http.servicecenterfile.download}")
	private String url;
	@Value("${http.servicecenterfile.download1}")
	private String attachmentUrl;

	/**
	 * 进入新增页面，生成服务主键id
	 * @author lishuang
	 * @date 2019-04-01
	 */
	public Integer generateId(){
		String sql = "select id from server_info";
		Query query = em.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerInfo.class));
		List<ServerInfo> list = query.getResultList();
		Integer id = list.get(list.size()-1).getId()+1;
		return id;
	}

	/**
	 * 生成主键id后，保存空数据
	 * @author lishuang
	 * @date 2019-04-01
	 */
	public void save(Integer id){
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setId(id);
		serverinfo_modelRepository.save(serverInfo);
	}

	/**
	 * 新增基本信息
	 * @param serverInfoModelDto
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-12
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerInfo add(ServerInfoModelDto serverInfoModelDto,Integer orgid){
		ServerInfo serverInfo = translateServerInfo(serverInfoModelDto);
		serverInfo.setSerId(getSerId(orgid));
		serverInfo.setCreatetime(new Date());
		//基本信息保存，获取save后的ServerInfo
		serverInfo = serverinfo_modelRepository.save(serverInfo);
		//受理部门保存
		String[] orgIds = null;
		String str = serverInfoModelDto.getOrgIds();
		if (ToolUtil.isNotEmpty(str)){
			orgIds = str.split(",");
		}
		if (ToolUtil.isNotEmpty(orgIds)){
			List<ServerAcceptanceDept> newDepts = new ArrayList<>();
			for (String orgId : orgIds){
				if (ToolUtil.isEmpty(orgId)){
					continue;
				}
				OmOrganization omOrganization = omOrganizationRepository.findByOrgid(Integer.parseInt(orgId));
				ServerAcceptanceDept dept = new ServerAcceptanceDept();
				if (ToolUtil.isNotEmpty(omOrganization) && "04".equals(omOrganization.getOrgtype())){
					dept.setOrgId(omOrganization.getOrgid());
				}else if (ToolUtil.isNotEmpty(omOrganization) && "05".equals(omOrganization.getOrgtype())){
					dept.setOrgId(omOrganization.getParentorgid());
				}
				dept.setServerId(serverInfo.getId());
				newDepts.add(dept);
				serveracceptancedeptService.add(dept);
			}
		}
		//受理人保存
		String[] userIds = serverInfoModelDto.getUserIds().split(",");
		if (ToolUtil.isNotEmpty(userIds)){
			for (String userId : userIds){
			    if (ToolUtil.isEmpty(userId)){
			        continue;
                }
				OmUser omUser = omUserRepository.findByUserid(userId);
				if (ToolUtil.isNotEmpty(omUser)){
					ServerAcceptanceUser serverAcceptanceUser = new ServerAcceptanceUser();
					serverAcceptanceUser.setServerId(serverInfo.getId());
					serverAcceptanceUser.setUserName(omUser.getEmpname());
					serverAcceptanceUser.setUserId(omUser.getEmpid());
					serveracceptanceuserService.add(serverAcceptanceUser);
				}

			}
		}
		if (1 == serverInfoModelDto.getServiceType() && ToolUtil.isNotEmpty(serverInfoModelDto.getFlowChartIds())){
			//流程图保存
			String[] flowChartsIds = serverInfoModelDto.getFlowChartIds().split(",");
			if (ToolUtil.isNotEmpty(flowChartsIds)){
				for (String flowChartId : flowChartsIds){
					ServerAttachmentFlowChart serverAttachmentFlowChart = serverattachmentflowchartService.get(Integer.parseInt(flowChartId));
					serverAttachmentFlowChart.setServerId(serverInfo.getId());
					serverattachmentflowchartService.add(serverAttachmentFlowChart);
				}
			}
		}
		return serverInfo;
	}

	/**
	 * 删除基本信息
	 * @param id
	 *
	 * @author lishaung
	 * @data 2019-03-12
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void delete(Integer id){
		//判断是否存在此服务信息
		if (ToolUtil.isNotEmpty(id)){
			if (ToolUtil.isNotEmpty(serverinfo_modelRepository.findById(id))){
				//删除受理部门
				if (ToolUtil.isNotEmpty(serveracceptancedeptService.getAllByServerId(id))){
					serveracceptancedeptService.deleteAllByServerId(id);
				}
				//删除受理人
				if (ToolUtil.isNotEmpty(serveracceptanceuserService.getAllByServerId(id))){
					serveracceptanceuserService.deleteAllByServerId(id);
				}
				//删除流程图
				if (ToolUtil.isNotEmpty(serverattachmentflowchartService.getAllByServerId(id))){
					serverattachmentflowchartService.deleteAllByServerId(id);
				}
				//删除常见问题
				if (ToolUtil.isNotEmpty(servercommonproblemService.getSelectAll(id))){
					servercommonproblemService.deleteAllByServerId(id);
				}
				//删除受理材料
				if (ToolUtil.isNotEmpty(serverhandlingmaterialService.getSelectAll(id))){
					serverhandlingmaterialService.deleteAllByServerId(id);
				}
				//关联的信息删除完毕，删除基本信息
				serverinfo_modelRepository.deleteById(id);
			}
		}
	}

	/**
	 * 修改基本信息（废弃）
	 * @param serverInfoModelDto
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-14
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerInfoModelDto update(ServerInfoModelDto serverInfoModelDto){
		//基本信息
		ServerInfo newServerInfo = new ServerInfo();
		//服务类型为查询服务
		newServerInfo.setId(serverInfoModelDto.getId());
		//服务编号根据选中的第一个受理部门生成
		String[] depts = serverInfoModelDto.getOrgIds().split(",");
		String serId = getSerId(Integer.parseInt(depts[0]));
		newServerInfo.setSerId(serId);
		//newServerInfo.setSerId(serverInfoModelDto.getSerId());
		newServerInfo.setSerName(serverInfoModelDto.getSerName());
		newServerInfo.setKeyword(serverInfoModelDto.getKeyword());
		newServerInfo.setSummary(serverInfoModelDto.getSummary());
		newServerInfo.setStatus(serverInfoModelDto.getStatus());
		newServerInfo.setSort(serverInfoModelDto.getSort());
		newServerInfo.setType(serverInfoModelDto.getType());
		newServerInfo.setServiceType(serverInfoModelDto.getServiceType());
		newServerInfo.setOnline_Processing_addr(serverInfoModelDto.getOnline_Processing_addr());
		newServerInfo.setHotService(serverInfoModelDto.getHotService());
		if (1 == serverInfoModelDto.getServiceType()){
			//服务类型为办理服务
			newServerInfo.setProcessingTime(serverInfoModelDto.getProcessingTime());
			newServerInfo.setSerRange(serverInfoModelDto.getSerRange());
			newServerInfo.setInitiationAuthority(serverInfoModelDto.getInitiationAuthority());
			newServerInfo.setHandlingChannels(serverInfoModelDto.getHandlingChannels());
			newServerInfo.setBiddingMaterials(serverInfoModelDto.getBiddingMaterials());
			newServerInfo.setSettlementMaterials(serverInfoModelDto.getSettlementMaterials());
			newServerInfo.setOnline_Processing(serverInfoModelDto.getOnline_Processing());
			newServerInfo.setHandlingRequirements(serverInfoModelDto.getHandlingRequirements());
			newServerInfo.setProcessDescription(serverInfoModelDto.getProcessDescription());
		}
		serverinfo_modelRepository.save(newServerInfo);
		//变更受理部门
		String[] ids = serverInfoModelDto.getOrgIds().split(",");
		List<ServerAcceptanceDept> newDepts = new ArrayList<>();
		if (ToolUtil.isNotEmpty(ids)){
			for (String id : ids){
				if (ToolUtil.isEmpty(id)){
					continue;
				}
				ServerAcceptanceDept serverAcceptanceDept = new ServerAcceptanceDept();
				OmOrganization omOrganization = omOrganizationRepository.findByOrgid(Integer.parseInt(id));
				if (ToolUtil.isNotEmpty(omOrganization) && "04".equals(omOrganization.getOrgtype())){
					serverAcceptanceDept.setOrgId(omOrganization.getOrgid());
				}else if (ToolUtil.isNotEmpty(omOrganization) && "05".equals(omOrganization.getOrgtype())){
					serverAcceptanceDept.setOrgId(omOrganization.getParentorgid());
				}
				serverAcceptanceDept.setServerId(serverInfoModelDto.getId());
				newDepts.add(serverAcceptanceDept);
			}
		}
		List<ServerAcceptanceDept> oldDepts = serveracceptancedeptService.getAllByServerId(serverInfoModelDto.getId());
		for (ServerAcceptanceDept dept : oldDepts){
			//如果新提交的受理部门中，不包含新增时添加的部门，则删除
			if (!newDepts.contains(dept)){
				continue;
			}else {
				serveracceptancedeptService.deleteById(dept.getId());
			}
		}
		for (ServerAcceptanceDept dept : newDepts){
			serveracceptancedeptService.add(dept);
		}
		//变更受理人
		String[] userIds = serverInfoModelDto.getUserIds().split(",");
		List<ServerAcceptanceUser> newUsers = new ArrayList<>();
		if (ToolUtil.isNotEmpty(userIds)){
			for (String userId : userIds){
				if (ToolUtil.isEmpty(userId)){
					continue;
				}
                OmUser omUser = omUserRepository.findByUserid(userId);
				ServerAcceptanceUser serverAcceptanceUser = new ServerAcceptanceUser();
				serverAcceptanceUser.setServerId(serverInfoModelDto.getId());
				serverAcceptanceUser.setUserId(omUser.getEmpid());
				serverAcceptanceUser.setUserName(omUser.getEmpname());
				newUsers.add(serverAcceptanceUser);
			}
		}
		List<ServerAcceptanceUser> oldUsers = serveracceptanceuserService.getAllByServerId(serverInfoModelDto.getId());
		if (ToolUtil.isNotEmpty(oldUsers)){
			for (ServerAcceptanceUser user : oldUsers){
				if (!newUsers.contains(user)){
					continue;
				}else {
					serveracceptanceuserService.deleteById(user.getId());
				}
			}
		}
		for (ServerAcceptanceUser user : newUsers){
            serveracceptanceuserService.add(user);
        }
		//变更流程图
		if (1 == serverInfoModelDto.getServiceType() && ToolUtil.isNotEmpty(serverInfoModelDto.getFlowChartIds())){
			String[] flowChartIds = serverInfoModelDto.getFlowChartIds().split(",");
			if (ToolUtil.isNotEmpty(flowChartIds)){
				for (String flowChartId : flowChartIds){
					ServerAttachmentFlowChart serverAttachmentFlowChart = serverattachmentflowchartService.get(Integer.parseInt(flowChartId));
					serverAttachmentFlowChart.setServerId(serverInfoModelDto.getId());
					serverattachmentflowchartService.add(serverAttachmentFlowChart);
				}
			}
		}
		return serverInfoModelDto;
	}

	/**
	 * 修改基本信息时，先进行数据库中该服务下受理部门、受理人、流程图的清除操作
	 * @param id
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-14
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public Boolean deleteAll(Integer id){
		//删除受理部门
		if (ToolUtil.isNotEmpty(serveracceptancedeptService.getAllByServerId(id))){
			serveracceptancedeptService.deleteAllByServerId(id);
		}
		//删除受理人
		if (ToolUtil.isNotEmpty(serveracceptanceuserService.getAllByServerId(id))){
			serveracceptanceuserService.deleteAllByServerId(id);
		}
		//删除流程图
		/*if (ToolUtil.isNotEmpty(serverattachmentflowchartService.getAllByServerId(id))){
			serverattachmentflowchartService.deleteAllByServerId(id);
		}*/
		//删除受理材料
		/*if (ToolUtil.isNotEmpty(serverhandlingmaterialService.getSelectAll(id))){
            serverhandlingmaterialService.deleteAllByServerId(id);
        }*/
		return true;
	}

	/**
	 * 查询基本信息-后台
	 * @param id
	 * @return
	 *
	 * @author lishaung
	 * @data 2019-03-12
	 */
	public JSONObject getById(Integer id){
		JSONObject json = new JSONObject();
		//查询受理部门信息
		List<ServerAcceptanceDept> depts = serveracceptancedeptService.getAllByServerId(id);
		json.put("ServerAcceptanceDept",depts);
		//查询受理人信息
		List<ServerAcceptanceUser> users = serveracceptanceuserService.getAllByServerId(id);
		String userIds = "";
		for (int i=0;i<users.size();i++){
		    OmUser omUser = omUserRepository.findByEmpid(users.get(i).getUserId());
		    if (ToolUtil.isNotEmpty(omUser)&&i<users.size()-1){
				userIds += omUser.getUserid()+",";
			}else {
				userIds += omUser.getUserid();
			}
        }
		json.put("ServerAcceptanceUser",userIds);
		//查询流程图信息
		List<ServerAttachmentFlowChart> flowCharts = serverattachmentflowchartService.getAllByServerId(id);
		List<FlowChart> list = new ArrayList<>();
		if (ToolUtil.isNotEmpty(flowCharts)){
			for (ServerAttachmentFlowChart flowChart : flowCharts){
				FlowChart chart = new FlowChart();
				chart.setId(flowChart.getId());
				chart.setName(flowChart.getName());
				chart.setUrl(url+flowChart.getId());
				//flowChart.setUrl(url+flowChart.getId());
				list.add(chart);
			}
		}
		json.put("ServerAttachmentFlowChart",list);
		//查询基本信息
		ServerInfo serverInfo = serverinfo_modelRepository.getOne(id);
		json.put("ServerInfo",serverInfo);
		return json;
	}

	/**
	 * 基本信息分页查询
	 * @param conditionModel
	 * @return
	 *
	 */
	public List<ServiceInfoModelDto> getPage(ConditionModel conditionModel){
		String jpql = "SELECT s.id,s.ser_id serId,s.ser_name serName,s.sort,s.status,sd.`name` typeName,d.ORGNAME orgName,d.SORTNO sortNo,u.username userName "
				+ "FROM server_info s INNER JOIN server_dict sd ON sd.id = s.type INNER JOIN(SELECT d.server_id,GROUP_CONCAT(om.ORGNAME)"
				+ "ORGNAME,MIN(om.SORTNO) SORTNO FROM server_acceptance_dept d LEFT JOIN om_organization om ON d.org_id = om.ORGID "
				+ "WHERE server_id in (SELECT d.server_id FROM server_acceptance_dept d  WHERE 1=1 ";
		if (ToolUtil.isNotEmpty(conditionModel.getOrgId())){
			jpql +="AND d.org_id =:orgid ";
		}
		jpql += "GROUP BY d.server_id) GROUP BY d.server_id) d on d.server_id = s.id "
			  + "LEFT JOIN (SELECT sau.server_id,GROUP_CONCAT(sau.user_name) username FROM server_acceptance_user sau GROUP BY sau.server_id) u "
			  +	"ON u.server_id=s.id WHERE 1 = 1 AND sd.type = 'type' ";
		if (ToolUtil.isNotEmpty(conditionModel.getType())){
			jpql += " and s.type = '"+conditionModel.getType()+"'";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getStatus())){
			jpql += " and s.status = '"+conditionModel.getStatus()+"'";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getHotService())){
			jpql += " and s.hot_service = '"+conditionModel.getHotService()+"'";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getServiceType())){
			jpql += " and s.service_type = '"+conditionModel.getServiceType()+"'";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getSerName())){
			jpql += " and s.ser_name like '%"+conditionModel.getSerName()+"%'";
		}
		jpql += " order by sortNo,s.sort";
		Query query = em.createNativeQuery(jpql);
		if (ToolUtil.isNotEmpty(conditionModel.getOrgId())){
			query.setParameter("orgid",conditionModel.getOrgId());
		}
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServiceInfoModelDto.class));
		if (ToolUtil.isNotEmpty(conditionModel.getPage()) && ToolUtil.isNotEmpty(conditionModel.getSize())) {
			query.setFirstResult((conditionModel.getPage() - 1) * conditionModel.getSize());
			query.setMaxResults(conditionModel.getSize());
		}
		List<ServiceInfoModelDto> infos = query.getResultList();
		return infos;
	}

	/**
	 * 基本信息分页查询总条数
	 * @param conditionModel
	 * @return
	 */
	public int getCount(ConditionModel conditionModel){
		int count = 0;
		String jpql = "SELECT count(s.id) "
				+ "FROM server_info s INNER JOIN server_dict sd ON sd.id = s.type INNER JOIN(SELECT d.server_id,GROUP_CONCAT(om.ORGNAME)"
				+ "ORGNAME,MIN(om.SORTNO) SORTNO FROM server_acceptance_dept d LEFT JOIN om_organization om ON d.org_id = om.ORGID "
				+ "WHERE server_id in (SELECT d.server_id FROM server_acceptance_dept d  WHERE 1=1 ";
		if (ToolUtil.isNotEmpty(conditionModel.getOrgId())){
			jpql +="AND d.org_id =:orgid ";
		}
		jpql += "GROUP BY d.server_id) GROUP BY d.server_id) d on d.server_id = s.id WHERE 1 = 1 AND sd.type = 'type' ";
		if (ToolUtil.isNotEmpty(conditionModel.getType())){
			jpql += " and s.type = '"+conditionModel.getType()+"'";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getStatus())){
			jpql += " and s.status = '"+conditionModel.getStatus()+"'";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getHotService())){
			jpql += " and s.hot_service = '"+conditionModel.getHotService()+"'";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getServiceType())){
			jpql += " and s.service_type = '"+conditionModel.getServiceType()+"'";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getSerName())){
			jpql += " and s.ser_name like '%"+conditionModel.getSerName()+"%'";
		}
		Query querycount = em.createNativeQuery(jpql);
		if (ToolUtil.isNotEmpty(conditionModel.getOrgId())){
			querycount.setParameter("orgid",conditionModel.getOrgId());
		}
		List<BigInteger> counts = querycount.getResultList();
		if (counts != null && counts.size() > 0) {
			count = counts.get(0).intValue();
		}
		return count;
	}

	/**
	 * 分页查询条件下拉列表
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-19
	 */
	public JSONObject getTypeAndStatus(){
		//部门
		String sql = "select om.orgid orgid,om.orgname orgname,om.orgtype orgtype from om_organization om " +
				"where om.parentorgid=:parentOrgid AND om.STATUS =:status";
		Query query = em.createNativeQuery(sql);
		query.setParameter("parentOrgid",206);
		query.setParameter("status",0);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsOmOrganization.class));
		List<SmsOmOrganization> depts = query.getResultList();
		//分类、状态
		JSONObject data = serverdictService.selectType();
		data.put("organization",depts);
		return data;
	}


	/**
	 * 页面传的对象ServerInfoModelDto转换成基本信息ServerInfo
	 * @param serverInfoModelDto
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-12
	 */
	public ServerInfo translateServerInfo(ServerInfoModelDto serverInfoModelDto){
		ServerInfo serverInfo = new ServerInfo();

		//服务类型为查询服务
		//服务主键id
		serverInfo.setId(serverInfoModelDto.getId());
		//服务名称
		serverInfo.setSerName(serverInfoModelDto.getSerName());
		//关键字
		serverInfo.setKeyword(serverInfoModelDto.getKeyword());
		//服务简介
		serverInfo.setSummary(serverInfoModelDto.getSummary());
		//状态
		serverInfo.setStatus(serverInfoModelDto.getStatus());
		//排序
		serverInfo.setSort(serverInfoModelDto.getSort());
		//所属分类
		serverInfo.setType(serverInfoModelDto.getType());
		//服务类型
		serverInfo.setServiceType(serverInfoModelDto.getServiceType());
		//在线办理地址
		serverInfo.setOnline_Processing_addr(serverInfoModelDto.getOnline_Processing_addr());
		//创建时间
		//serverInfo.setCreatetime(serverInfoModelDto.getCreatetime());
		//热门服务
		serverInfo.setHotService(serverInfoModelDto.getHotService());

		if ( 1 == serverInfoModelDto.getServiceType()){
			//服务类型为办理服务
			//办理时间
			serverInfo.setProcessingTime(serverInfoModelDto.getProcessingTime());
			//服务范围
			serverInfo.setSerRange(serverInfoModelDto.getSerRange());
			//发起权限
			serverInfo.setInitiationAuthority(serverInfoModelDto.getInitiationAuthority());
			//办理渠道
			serverInfo.setHandlingChannels(serverInfoModelDto.getHandlingChannels());
			//申办材料递送地址
			serverInfo.setBiddingMaterials(serverInfoModelDto.getBiddingMaterials());
			//办结材料领取地址
			serverInfo.setSettlementMaterials(serverInfoModelDto.getSettlementMaterials());
			//是否在线办理
			serverInfo.setOnline_Processing(serverInfoModelDto.getOnline_Processing());
			//办理要求
			serverInfo.setHandlingRequirements(serverInfoModelDto.getHandlingRequirements());
			//流程描述
			serverInfo.setProcessDescription(serverInfoModelDto.getProcessDescription());
		}
		return serverInfo;
	}

	/**
	 * 服务编码生成
	 * @param orgid
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-12
	 */
	public String getSerId(Integer orgid){
		//服务编码前五位
		String front = frontCompWithZero(orgid);
		//服务编码后五位
		String rear = null;
		//查询所有的服务编码
		List<ServerInfo> serverInfos = serverinfo_modelRepository.findAll();
		//获取当前部门的服务编码的序列号集合
		List<String> list = new ArrayList<>();
		if (ToolUtil.isNotEmpty(serverInfos)){
			for (ServerInfo info:serverInfos){
				int orgId = 0;
				if (info !=null && null != info.getSerId() &&info.getSerId().length()>0){
					orgId = Integer.parseInt(info.getSerId().substring(0,5));
					if (ToolUtil.isNotEmpty(orgId) && orgid.equals(orgId)){
						list.add(info.getSerId().substring(5,info.getSerId().length()));
					}
				}
			}
		}
		if (ToolUtil.isNotEmpty(list)){
			Collections.sort(list);
			rear = getNewSerialNo(list.get(list.size()-1));
		}else {
			rear = getNewSerialNo("");
		}
		String serId = front + rear;
		return serId;
	}

	/**
	 * 服务编码生成策略（部门orgid不足5位，在前面补0）
	 * @param sourceDate
	 * @return
	 *
	 * @author lishaung
	 * @data 2019-03-12
	 */
	private String frontCompWithZero(Integer sourceDate){
		String newString = String.format("%05d", sourceDate);
		return newString;
	}

	/**
	 * 服务编码生成策略（序列号生成，从00001开始）
	 * @param serialNo
	 * @return
	 *
	 * @author lishaung
	 * @data 2019-03-12
	 */
	private String getNewSerialNo(String serialNo){
		String newSerialNo = "00001";
		if(ToolUtil.isNotEmpty(serialNo)){
			int newEquipment = Integer.parseInt(serialNo) + 1;
			newSerialNo = String.format("%05d", newEquipment);
		}
		return newSerialNo;
	}

	/**
	 * 根据部门orgid查找人员信息
	 * @param orgid
	 * @return
	 */
	private List<SmsOmUser> findAll(Integer orgid){
		String sql = "select om.empid empid,om.userid userid,om.empname empname,om.empstatus empstatus,om.orgid orgid,om.mobileno mobileno from om_user om where om.EMPSTATUS <> '00'";
		if (ToolUtil.isNotEmpty(orgid)){
			sql += " and om.orgid =:orgid";
		}
		Query query = em.createNativeQuery(sql);
		query.setParameter("orgid",orgid);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsOmUser.class));
		List<SmsOmUser> list = query.getResultList();
		return list;
	}

	/**
	 * 热门服务
	 * @param top
	 *
	 * @author lishuang
	 * @date 2019-03-18
	 */
	public JSONObject getHotService(Integer top){
		JSONObject services = new JSONObject();
		String sql = "SELECT s.id,s.ser_name serName,s.service_type serviceType,s.online_Processing onlineProcessing,s.online_Processing_addr "
				+"onlineProcessingAddr from server_info s where s.status = '1' and s.hot_service ='1' and s.service_type = '1' ORDER BY sort LIMIT 0,"+top;
		Query query = em.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(HotServiceModelDto.class));
		List<HotServiceModelDto> handleService = query.getResultList();
		services.put("handleService",handleService);
		String sql2 = "SELECT s.id,s.ser_name serName,s.service_type serviceType,s.online_Processing onlineProcessing,s.online_Processing_addr "
				+"onlineProcessingAddr from server_info s where s.status = '1' and s.hot_service ='1' and s.service_type = '2' ORDER BY sort LIMIT 0,"+top;
		Query query2 = em.createNativeQuery(sql2);
		query2.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(HotServiceModelDto.class));
		List<HotServiceModelDto> checkService = query2.getResultList();
		services.put("checkService",checkService);
		return services;
	}

	/**
	 * 查看服务详情-前台
	 * @param id
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-18
	 */
	public JSONObject getServerInfo(Integer id){
		JSONObject date = new JSONObject();
		//详细信息
		ServerInfo serverInfo = serverinfo_modelRepository.getOne(id);
		//受理部门
		String serverAcceptanceDept= "";
		List<ServerAcceptanceDept> depts = serveracceptancedeptService.getAllByServerId(id);
		if (ToolUtil.isNotEmpty(depts)){
			for (int i=0;i<depts.size();i++){
				OmOrganization omOrganization = omOrganizationRepository.findByOrgid(depts.get(i).getOrgId());
				if (ToolUtil.isNotEmpty(omOrganization)&&i<depts.size()-1){
					serverAcceptanceDept += omOrganization.getOrgname()+",";
				}else {
					serverAcceptanceDept += omOrganization.getOrgname();
				}
			}
		}
		//受理人
		String serverAcceptanceUser="";
		List<ServerAcceptanceUser> users = serveracceptanceuserService.getAllByServerId(id);
		if (ToolUtil.isNotEmpty(users)){
			for (int i=0;i<users.size();i++){
				OmUser omUser = omUserRepository.findByEmpid(users.get(i).getUserId());
				if (ToolUtil.isNotEmpty(omUser)&&i<users.size()-1){
					serverAcceptanceUser += omUser.getEmpname()+"("+omUser.getOtel()+")"+",";
				}else {
					serverAcceptanceUser += omUser.getEmpname()+"("+omUser.getOtel()+")";
				}
			}
		}
	    //流程图
		List<ServerAttachmentFlowChart> serverAttachmentFlowCharts = serverattachmentflowchartService.getAllByServerId(id);
		List<FlowChart> flowCharts = new ArrayList<>();
		for (ServerAttachmentFlowChart flowChart : serverAttachmentFlowCharts){
			FlowChart chart = new FlowChart();
			chart.setId(flowChart.getId());
			chart.setName(flowChart.getName());
			chart.setUrl(url+flowChart.getId());
			//flowChart.setUrl(url+flowChart.getId());
			flowCharts.add(chart);
		}
		//常见问题
		List<ServerCommonProblem> problems = servercommonproblemService.getSelectAll(id);
		date.put("serverCommonProblem",problems);
		//办理材料及附件
		List<ServerHandlingMaterial> serverHandlingMaterials = serverhandlingmaterialService.getSelectAll(id);
		List<ServerHandlingMaterial> materials = new ArrayList<>();
		if (ToolUtil.isNotEmpty(serverHandlingMaterials)){
			for (ServerHandlingMaterial material : serverHandlingMaterials){
				//获取该材料的附件
				if (ToolUtil.isNotEmpty(material)){
					List<ServerAttachment> serverAttachments = material.getServerAttachments();
					if (ToolUtil.isNotEmpty(serverAttachments)){
						//循环附件，修改url地址
						List<ServerAttachment> attachments = new ArrayList<>();
						for (ServerAttachment attachment : serverAttachments){
							attachment.setUrl(attachmentUrl+attachment.getId());
							attachments.add(attachment);
						}
						material.setServerAttachments(attachments);
					}
					materials.add(material);
				}
			}
		}
		//办理材料按照sort升序排序
		/*Collections.sort(materials, new Comparator<ServerHandlingMaterial>() {
			@Override
			public int compare(ServerHandlingMaterial o1, ServerHandlingMaterial o2) {
				if (o1.getSort()>=o2.getSort()){
					return 1;
				}
				return -1;
			}
		});*/
		// 服务满意度
		double total = countTotal(id);
		double count = countSatisfaction(id);
		String satisfaction = "";
		if (total != 0){
			satisfaction = Integer.parseInt(new DecimalFormat("0").
					format(count/total*100))+"%";
		}else {
			satisfaction = "0";
		}
		// 平均时效
		//int avg = countBySerId(id);
		int avg = 0;
		if (ToolUtil.isNotEmpty(serverInfo.getAvgTime())){
			avg = serverInfo.getAvgTime();
		}
		// 数据返回
		date.put("satisfaction",satisfaction);
		date.put("avgTime",avg);
		date.put("serverHandlingMaterial",materials);
		date.put("serverInfo",serverInfo);
		date.put("serverAcceptanceDept",serverAcceptanceDept);
		date.put("serverAcceptanceUser",serverAcceptanceUser);
		date.put("serverAttachmentFlowChart",flowCharts);
		return date;
	}

	/**
	 * 查询服务对应的办件，投票总数（评价为满意、不满意）
	 * @param serId
	 * @return
	 */
	private int countTotal(Integer serId){
		int total = 0;
		String sql = "SELECT COUNT(id) FROM server_apply_info WHERE ser_id =:serId AND (score = '0' OR score = '1')";
		Query query = em.createNativeQuery(sql);
		query.setParameter("serId",serId);
		List<BigInteger> counts = query.getResultList();
		if (ToolUtil.isNotEmpty(counts))
			total = counts.get(0).intValue();
		return total;
	}

	/**
	 * 查询服务对应的办件，评价是满意的总数
	 * @param serId
	 * @return
	 */
	private int countSatisfaction(Integer serId){
		int total = 0;
		String sql = "SELECT COUNT(id) FROM server_apply_info WHERE ser_id =:serId AND score = '1'";
		Query query = em.createNativeQuery(sql);
		query.setParameter("serId",serId);
		List<BigInteger> counts = query.getResultList();
		if (ToolUtil.isNotEmpty(counts))
			total = counts.get(0).intValue();
		return total;
	}

	/**
	 * 查询服务对应的办件平均时效
	 * @param serId
	 * @return
	 */
	public int countBySerId(Integer serId){
		int avg = 0;
		int count = 0;
		int sum = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE,-30);
		Date date = calendar.getTime();
		String startTime = DateUtil.getStrTime(date);
		// 已完成办件数量
		String countSql = "SELECT COUNT(id) FROM server_apply_info "
				+ "WHERE ser_id =:serId AND (`status` = '2' OR `status` = '3') AND end_time >='"+startTime+"'";
		Query countQuery = em.createNativeQuery(countSql);
		countQuery.setParameter("serId",serId);
		List<BigInteger> counts = countQuery.getResultList();
		if (ToolUtil.isNotEmpty(counts)){
			count = counts.get(0).intValue();
		}
		// 已完成办件，花费总时长
		String sumSql = "SELECT IFNULL(SUM(work_day),0) FROM server_apply_info "
				+ "WHERE ser_id =:serId AND (`status` = '2' OR `status` = '3') AND end_time >='"+startTime+"'";
		Query sumQuery = em.createNativeQuery(sumSql);
		sumQuery.setParameter("serId",serId);
		List<BigDecimal> sums = sumQuery.getResultList();
		if (ToolUtil.isNotEmpty(sums)){
			sum = sums.get(0).intValue();
		}
		if (count == 0){
			return avg;
		}else {
			avg = sum/count;
		}
		return avg;
	}

	/**
	 * 服务禁用
	 * @param ids
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-26
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void serverDisable(String[] ids){
		for (String id : ids){
			ServerInfo serverInfo = serverinfo_modelRepository.getOne(Integer.parseInt(id));
			serverInfo.setStatus(Byte.parseByte("0"));
			serverinfo_modelRepository.save(serverInfo);
		}
	}

	/**
	 * 服务启用
	 * @param ids
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-26
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void serverEnable(String[] ids){
		for (String id : ids){
			ServerInfo serverInfo = serverinfo_modelRepository.getOne(Integer.parseInt(id));
			serverInfo.setStatus(Byte.parseByte("1"));
			serverinfo_modelRepository.save(serverInfo);
		}
	}


	/**
	 * ServerFrontPage-分页查询基本信息
	 * @param orgId
	 * @param keyword
	 * @return
	 *  jinzhao  2019-12-18 查询添加服务经理
	 */
	public List<ServiceInfoModelDto> ServerFrontPage(Integer page,Integer size, Integer orgId, String keyword,Integer onlineProcessing){
		String jpql = "SELECT DISTINCT s.id,s.ser_id serId,s.ser_name serName,s.type,s.sort,s.status,s.online_Processing,s.online_Processing_addr,s.service_type,(SELECT GROUP_CONCAT(sau.user_name) username FROM server_acceptance_user sau where sau.server_id = s.id GROUP BY sau.server_id) as userName from server_info s LEFT JOIN server_acceptance_dept d on s.id = d.server_id LEFT JOIN server_dict sd on s.type = sd.id LEFT JOIN om_organization om ON om.ORGID = d.org_id WHERE s.status = 1";
		if (ToolUtil.isNotEmpty(orgId)){
			jpql += " and d.org_id = '"+orgId+"'";
		}
		if (ToolUtil.isNotEmpty(keyword)){
			jpql += " and (s.keyword like '%"+keyword+"%' or s.ser_name like '%"+keyword +"%' or om.ORGNAME like '%"+keyword+"%' or sd.name like '%"+keyword + "%')";
		}
		jpql += " order by s.sort";
		Query query = em.createNativeQuery(jpql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServiceInfoModelDto.class));
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		List<ServiceInfoModelDto> infos = query.getResultList();
		return infos;
	}

	@Override
	public Integer getCount(Integer page, Integer size, Integer orgId, String keyword,Integer onlineProcessing) {
		int count = 0;
		String jpql = "SELECT DISTINCT s.id,s.ser_id serId,s.ser_name serName,s.type,s.sort,s.status,s.online_Processing," +
				" s.online_Processing_addr,s.service_type " +
				"from server_info s " +
				" LEFT JOIN server_acceptance_dept d on s.id = d.server_id " +
				" LEFT JOIN server_dict sd on s.type = sd.id " +
				" LEFT JOIN om_organization om ON om.ORGID = d.org_id WHERE s.status = 1";
		if (ToolUtil.isNotEmpty(orgId)){
			jpql += " and d.org_id = '"+orgId+"'";
		}
		if (ToolUtil.isNotEmpty(keyword)){
			jpql += " and (s.keyword like '%"+keyword+"%' or s.ser_name like '%"+keyword +"%' " +
					"or om.ORGNAME like '%"+keyword+"%' or sd.name like '%"+keyword + "%')";
		}

		Query query = em.createNativeQuery(jpql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServiceInfoModelDto.class));
		List<ServiceInfoModelDto> infos = query.getResultList();
		if (ToolUtil.isNotEmpty(infos)){
			count = infos.size();
		}
		return count;
	}

	/**
	 * 查询每个部门有多少个办理事件
	 * @return
	 */
	public List<ServerFrontDeptAndNum> listFront(){
		String sql = "select tab1.org_id,tab1.ORGNAME,tab1.serverNum from (SELECT ORGID as org_id,ORGNAME as ORGNAME,IFNULL(d.SUM,0) serverNum FROM om_organization o\n" +
				"LEFT JOIN (\n" +
				"  SELECT org_id,COUNT(*) SUM \n" +
				"  FROM server_acceptance_dept d\n" +
				"  INNER JOIN server_info s ON s.id = d.server_id AND s.status=1\n" +
				"  GROUP BY org_id\n" +
				") d ON d.org_id = o.ORGID\n" +
				"WHERE PARENTORGID ='206' AND `STATUS`='0' and o.ORGID not in ('207','1295','4323','284','1200','442','4681') ORDER BY SUM DESC,SORTNO) tab1 where tab1.serverNum != 0";
		Query query = em.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerFrontDeptAndNum.class));
		List<ServerFrontDeptAndNum> serverFrontDeptAndNumList = query.getResultList();
		return serverFrontDeptAndNumList;
	}

	/**
	 * 受理部门、受理人等树信息(递归查询)
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-04-17
	 */
	public JSONArray findInfos(){
		JSONArray array = new JSONArray();
		OmOrganization omOrganization = omOrganizationRepository.findByOrgid(206);
		ModelDto object = new ModelDto();
		String id = (omOrganization.getOrgid()+" ").trim();
		object.setId(id);
		object.setName(omOrganization.getOrgname());
		object.setType(omOrganization.getOrgtype());
		object.setIsOrg(true);
		if (ToolUtil.isNotEmpty(object)){
			find(object);
		}
		array.add(object);
		return array;
	}
	private ModelDto find(ModelDto model){
		String firstSql = "select om.orgid orgid,om.orgname orgname,om.orgtype orgtype,om.parentorgid parentorgid,om.orglevel orglevel " +
				"from om_organization om where om.parentorgid=:parentOrgid AND om.STATUS =:status";
		Query firstQuery = em.createNativeQuery(firstSql);
		firstQuery.setParameter("parentOrgid",model.getId());
		firstQuery.setParameter("status",0);
		firstQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsOmOrganization.class));
		List<SmsOmOrganization> omOrganizations = firstQuery.getResultList();
		List<ModelDto> children = new ArrayList<>();
		if (ToolUtil.isNotEmpty(omOrganizations)){
			model.setIsParent(true);
			for (SmsOmOrganization organization : omOrganizations){
				ModelDto modelDto = new ModelDto();
				modelDto.setId(organization.getOrgid().toString());
				modelDto.setName(organization.getOrgname());
				modelDto.setIsOrg(true);
				modelDto.setType(organization.getOrgtype());
				children.add(modelDto);
			}
		}else {
			model.setIsParent(false);
		}
		List<SmsOmUser> users =findAll(Integer.parseInt(model.getId()));
		if (ToolUtil.isNotEmpty(users)){
			for (SmsOmUser user : users){
				ModelDto modelDto = new ModelDto();
				modelDto.setId(user.getUserid());
				modelDto.setName(user.getEmpname());
				modelDto.setIsOrg(false);
				children.add(modelDto);
			}
		}
		model.setChildren(children);
		if (true == model.getIsParent()&&ToolUtil.isNotEmpty(children)){
			for (ModelDto dto : children){
				if (true == dto.getIsOrg()){
					find(dto);
				}
			}
		}
		return model;
	}

    /**
     * 处理部门树信息
     *
     * @date 2019-08-06
     * @return
     */
    public JSONArray findInfos(Integer id){
        JSONArray array = new JSONArray();
        if (id == 0){
            List<Integer> ids = new ArrayList<>(3);
            ids.add(206);
            ids.add(450);
            ids.add(2817);
            for (Integer orgid: ids){
                OmOrganization omOrganization = omOrganizationRepository.findByOrgid(orgid);
                Map map = new HashMap();
                map.put("id",omOrganization.getOrgid());
                map.put("name",omOrganization.getOrgname());
                map.put("isParent",true);
                array.add(map);
            }
        }else {
            List<OmOrganization> omOrganizations = omOrganizationRepository.findByParentorgidAndStatusOrderBySortno(id,"0");
            if (ToolUtil.isNotEmpty(omOrganizations)){
                for (OmOrganization omOrganization:omOrganizations){
                    Map map = new HashMap();
                    map.put("id",omOrganization.getOrgid());
                    map.put("name",omOrganization.getOrgname());
                    if (getInfo(omOrganization.getOrgid())){
                        map.put("isParent",true);
                    }else {
                        map.put("isParent",false);
                    }
                    array.add(map);
                }
            }
        }
        return array;
    }

    private boolean getInfo(Integer orgid){
        List<OmOrganization> omOrganizations = omOrganizationRepository.findByParentorgidAndStatus(orgid,"0");
        if (ToolUtil.isEmpty(omOrganizations)){
            return false;
        }
        return true;
    }

    public JSONArray getPerson(Integer orgid){
    	JSONArray array = new JSONArray();
        List<OmUserAddressbook> omUsers = omUserAddressbookRepository.findByOrgid(orgid);
        if (ToolUtil.isNotEmpty(omUsers)){
        	for (OmUserAddressbook omUser:omUsers){
        		Map map = new HashMap();
        		map.put("id",omUser.getEmpid());
        		map.put("name",omUser.getEmpname());
        		array.add(map);
			}
		}
        return array;
    }

	/**
	 * 修改基本信息
	 * @param serverInfoModelDto
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-14
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerInfo updateServerInfo(ServerInfoModelDto serverInfoModelDto){
		//基本信息
		ServerInfo newServerInfo = serverinfo_modelRepository.getOne(serverInfoModelDto.getId());
		if (ToolUtil.isEmpty(newServerInfo)){
			return newServerInfo;
		}
		//ServerInfo newServerInfo = new ServerInfo();
		//服务类型为查询服务
		//服务编号根据选中的第一个受理部门生成
		String[] depts = serverInfoModelDto.getOrgIds().split(",");
		String serId = getSerId(Integer.parseInt(depts[0]));
		newServerInfo.setSerId(serId);
		newServerInfo.setSerName(serverInfoModelDto.getSerName());
		newServerInfo.setKeyword(serverInfoModelDto.getKeyword());
		newServerInfo.setSummary(serverInfoModelDto.getSummary());
		newServerInfo.setStatus(serverInfoModelDto.getStatus());
		newServerInfo.setSort(serverInfoModelDto.getSort());
		newServerInfo.setType(serverInfoModelDto.getType());
		newServerInfo.setServiceType(serverInfoModelDto.getServiceType());
		newServerInfo.setOnline_Processing_addr(serverInfoModelDto.getOnline_Processing_addr());
		newServerInfo.setHotService(serverInfoModelDto.getHotService());
		newServerInfo.setCreatetime(new Date());
		if (1 == serverInfoModelDto.getServiceType()){
			//服务类型为办理服务
			newServerInfo.setProcessingTime(serverInfoModelDto.getProcessingTime());
			newServerInfo.setSerRange(serverInfoModelDto.getSerRange());
			newServerInfo.setInitiationAuthority(serverInfoModelDto.getInitiationAuthority());
			newServerInfo.setHandlingChannels(serverInfoModelDto.getHandlingChannels());
			newServerInfo.setBiddingMaterials(serverInfoModelDto.getBiddingMaterials());
			newServerInfo.setSettlementMaterials(serverInfoModelDto.getSettlementMaterials());
			newServerInfo.setOnline_Processing(serverInfoModelDto.getOnline_Processing());
			newServerInfo.setHandlingRequirements(serverInfoModelDto.getHandlingRequirements());
			newServerInfo.setProcessDescription(serverInfoModelDto.getProcessDescription());
		}
		serverinfo_modelRepository.save(newServerInfo);
		//变更受理部门-首先删除原受理部门
		serveracceptancedeptService.deleteAllByServerId(newServerInfo.getId());//删除原受理部门
		String[] ids = serverInfoModelDto.getOrgIds().split(",");
		if (ToolUtil.isNotEmpty(ids)){
			for (String id : ids){
				if (ToolUtil.isEmpty(id)){
					continue;
				}
				ServerAcceptanceDept serverAcceptanceDept = new ServerAcceptanceDept();
				OmOrganization omOrganization = omOrganizationRepository.findByOrgid(Integer.parseInt(id));
				serverAcceptanceDept.setOrgId(omOrganization.getOrgid());
				serverAcceptanceDept.setServerId(serverInfoModelDto.getId());
				serveracceptancedeptService.add(serverAcceptanceDept);
			}
		}
		//变更受理人-首先删除原受理人
		serveracceptanceuserService.deleteAllByServerId(newServerInfo.getId());
		String[] userIds = serverInfoModelDto.getUserIds().split(",");
		if (ToolUtil.isNotEmpty(userIds)){
			for (String userId : userIds){
				if (ToolUtil.isEmpty(userId)){
					continue;
				}
				OmUser omUser = omUserRepository.findByUserid(userId);
				ServerAcceptanceUser serverAcceptanceUser = new ServerAcceptanceUser();
				serverAcceptanceUser.setServerId(newServerInfo.getId());
				serverAcceptanceUser.setUserId(omUser.getEmpid());
				serverAcceptanceUser.setUserName(omUser.getEmpname());
				serveracceptanceuserService.add(serverAcceptanceUser);
			}
		}
		//变更流程图
		if (1 == serverInfoModelDto.getServiceType() && ToolUtil.isNotEmpty(serverInfoModelDto.getFlowChartIds())){
			String[] flowChartIds = serverInfoModelDto.getFlowChartIds().split(",");
			if (ToolUtil.isNotEmpty(flowChartIds)){
				for (String flowChartId : flowChartIds){
					ServerAttachmentFlowChart serverAttachmentFlowChart = serverattachmentflowchartService.get(Integer.parseInt(flowChartId));
					serverAttachmentFlowChart.setServerId(serverInfoModelDto.getId());
					serverattachmentflowchartService.add(serverAttachmentFlowChart);
				}
			}
		}
		return newServerInfo;
	}

	@Override
	public OnlineTotal selectOnlineTotal(Integer orgId, String keyword) {
		String jpql = "select count(DISTINCT s.id) as onlineTotal from server_info s LEFT JOIN server_acceptance_dept d on s.id = d.server_id LEFT JOIN server_dict sd on s.type = sd.id LEFT JOIN om_organization om ON om.ORGID = d.org_id WHERE s.status = 1 and s.online_Processing = 1 ";
		if (ToolUtil.isNotEmpty(orgId)){
			jpql += " and d.org_id = '"+orgId+"'";
		}
		if (ToolUtil.isNotEmpty(keyword)){
			jpql += " and (s.keyword like '%"+keyword+"%' or s.ser_name like '%"+keyword +"%' " +
					"or om.ORGNAME like '%"+keyword+"%' or sd.name like '%"+keyword + "%')";
		}
//		jpql += " order by s.sort";
		Query query = em.createNativeQuery(jpql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(OnlineTotal.class));
		OnlineTotal onlineTotal = (OnlineTotal) query.getSingleResult();
//		List<ServiceInfoModelDto> infos = query.getResultList();
		return onlineTotal ;
	}

    @Override
    public List<ServerModelDto> select(ConditionModel conditionModel, String ids) {
//
//		String sql = " select si.ser_id as serId,si.ser_name as serName,(select sd.name from server_dict sd where sd.type = 'type' and sd.value = si.type) as type,si.sort,(case si.status when 1 then '启用' when 0 then '禁用' end ) as status,group_concat(om.ORGNAME) as orgName,GROUP_CONCAT(sau.user_name) as flowUsername from server_info si LEFT join server_acceptance_dept sad  on si.id = sad.server_id left join om_organization om ON sad.org_id = om.ORGID LEFT JOIN server_acceptance_user sau on si.id = sau.server_id where 1=1 ";
//		if (ToolUtil.isNotEmpty(ids)) {
//			String[] unid = ids.split(",");
//			sql += " and si.id in (";
//			for (int i = 0; i < unid.length; i++) {
//				if (ToolUtil.isEmpty(unid[i])) {
//					continue;
//				}
//				if (i < unid.length - 1) {
//					sql += "'" + unid[i] + "',";
//				} else {
//					sql += "'" + unid[i] + "')";
//				}
//			}
//		}
//			if (ToolUtil.isNotEmpty(conditionModel.getOrgId())){
//				sql +="AND d.org_id =:orgid ";
//			}
//			if (ToolUtil.isNotEmpty(conditionModel.getType())){
//				sql += " and s.type = '"+conditionModel.getType()+"'";
//			}
//			if (ToolUtil.isNotEmpty(conditionModel.getStatus())){
//				sql += " and s.status = '"+conditionModel.getStatus()+"'";
//			}
//			if (ToolUtil.isNotEmpty(conditionModel.getHotService())){
//				sql += " and s.hot_service = '"+conditionModel.getHotService()+"'";
//			}
//			if (ToolUtil.isNotEmpty(conditionModel.getServiceType())){
//				sql += " and s.service_type = '"+conditionModel.getServiceType()+"'";
//			}
//			if (ToolUtil.isNotEmpty(conditionModel.getSerName())){
//				sql += " and s.ser_name like '%"+conditionModel.getSerName()+"%'";
//			}
//			sql += " group by si.ser_id ORDER BY si.sort ";

		String jpql = "SELECT s.ser_id as serId,s.ser_name as serName,s.sort,(case s.status when 1 then '启用' when 0 then '禁用' end ) as status,sd.`name` as type,d.ORGNAME as orgName,d.SORTNO as sortNo,u.username as flowUsername FROM server_info s INNER JOIN server_dict sd ON sd.id = s.type INNER JOIN(SELECT d.server_id,GROUP_CONCAT(om.ORGNAME) ORGNAME,MIN(om.SORTNO) SORTNO FROM server_acceptance_dept d LEFT JOIN om_organization om ON d.org_id = om.ORGID WHERE server_id in (SELECT d.server_id FROM server_acceptance_dept d  WHERE 1=1 ";

		if (ToolUtil.isNotEmpty(conditionModel.getOrgId())){
			jpql +=" AND d.org_id =:orgid ";
		}
		jpql += "GROUP BY d.server_id) GROUP BY d.server_id) d on d.server_id = s.id LEFT JOIN (SELECT sau.server_id,GROUP_CONCAT(sau.user_name) username FROM server_acceptance_user sau GROUP BY sau.server_id) u ON u.server_id=s.id WHERE sd.type = 'type' ";
		if (ToolUtil.isNotEmpty(ids)) {
			String[] unid = ids.split(",");
			jpql += " and s.id in (";
			for (int i = 0; i < unid.length; i++) {
				if (ToolUtil.isEmpty(unid[i])) {
					continue;
				}
				if (i < unid.length - 1) {
					jpql += "'" + unid[i] + "',";
				} else {
					jpql += "'" + unid[i] + "')";
				}
			}
		}
		if (ToolUtil.isNotEmpty(conditionModel.getType())){
			jpql += " and s.type = '"+conditionModel.getType()+"' ";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getStatus())){
			jpql += " and s.status = '"+conditionModel.getStatus()+"' ";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getHotService())){
			jpql += " and s.hot_service = '"+conditionModel.getHotService()+"' ";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getServiceType())){
			jpql += " and s.service_type = '"+conditionModel.getServiceType()+"' ";
		}
		if (ToolUtil.isNotEmpty(conditionModel.getSerName())){
			jpql += " and s.ser_name like '%"+conditionModel.getSerName()+"%' ";
		}
		jpql += " order by sortNo,s.sort ";
		Query query = em.createNativeQuery(jpql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerModelDto.class));

		return query.getResultList();
    }
}