package com.unionpay.supervision.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.Cal26ToNumUntil;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperFileRepository;
import com.unionpay.supervision.dao.SuperServiceOverseeMappingRepository;
import com.unionpay.supervision.dao.SuperServiceRepository;
import com.unionpay.supervision.dao.SuperSponsorRepository;
import com.unionpay.supervision.domain.SuperFile;
import com.unionpay.supervision.domain.SuperService;
import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.model.SuperServiceInfo;
import com.unionpay.supervision.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SuperServiceServiceImpl implements SuperServiceService {

	@Autowired
	private SuperServiceRepository superServiceRepository;
	@Autowired
	private SuperFileRepository superFileRepository;
	@Autowired
	private SuperSponsorRepository superSponsorRepository;
	@Autowired
	private SuperServiceOverseeMappingRepository superServiceOverseeMappingRepository;
	@Autowired
	private SuperFileService superFileService;
	@Autowired
	private SuperServiceOverseeMappingService superServiceOverseeMappingService;
	@Autowired
	private SuperSponsorService superSponsorService;
	@Autowired
	private SuperServiceIdCreateService superServiceIdCreateService;
	@Autowired
	private SuperSponsorLogService superSponsorLogService;

	@Override
	public List<SuperService> findAll() {
		List<SuperService> supSerList = superServiceRepository.findAll();
		return supSerList;
	}

	/**
	 * 督办事项新增
	 */
	@Override
	public void save(SuperService superService) {
		if (superService != null) {
			superServiceRepository.save(superService);
		}
	}
	
	/**
	 * 督办事项删除
	 */
	@Override
	public void delete(String unid) {
		if (ToolUtil.isNotEmpty(unid)) {
			superServiceRepository.deleteById(unid);
		}
	}



    /**
	 * 督办事项新增
	 */
	@Override
	@Transactional
	public RESTResultBean saveAll(SuperServiceInfo superServiceInfo) {
		RESTResultBean result = new RESTResultBean(200 ,"success");
		//保存成功后，返给页面的date
		JSONObject date = new JSONObject();
		SuperService superService = translateSuperService(superServiceInfo);
		superService.setUnid(UUID.randomUUID().toString());
		superService.setCreateTime(DateUtil.getTime());
		superServiceRepository.save(superService);
        Integer frequency = superServiceInfo.getOverseeFrequency();
		// 督办事项与督办类型关联详情新增或删除
		List<SuperServiceOverseeMapping> superServiceOverseeMappinglist = superServiceInfo
				.getSuperServiceOverseeMapping();
		for (SuperServiceOverseeMapping superServiceOverseeMapping : superServiceOverseeMappinglist) {
			// 信息补充
			if (superServiceOverseeMapping != null) {
				superServiceOverseeMapping.setUnid(UUID.randomUUID().toString());
				superServiceOverseeMapping.setServiceUnid(superService.getUnid());
				superServiceOverseeMapping.setCreateTime(DateUtil.getTime());
				superServiceOverseeMapping.setCreateUserid(superService.getCreateUserid());
				superServiceOverseeMapping.setIsPrimary(superServiceOverseeMapping.getIsPrimary() == null ? "N"
						: superServiceOverseeMapping.getIsPrimary());
				superServiceOverseeMapping.setStatus(1);
				if ("Y".equals(superServiceOverseeMapping.getIsPrimary())) {
					// 主督办类型
					superService.setCommandLeader(superServiceOverseeMapping.getCommandLeader());
					superService.setCommandSource(superServiceOverseeMapping.getCommandSource());
					superService.setServiceName(superServiceOverseeMapping.getServiceName());
					superService.setServiceTime(superServiceOverseeMapping.getServiceTime());
					superService.setTaskNote(superServiceOverseeMapping.getTaskNote());
					superService.setOverseeUnid(superServiceOverseeMapping.getOverseeUnid());
					superService.setOverseeName(superServiceOverseeMapping.getOverseeName());
				}
				// superServiceOverseeMapping.setServiceTime(superService.getServiceTime());
				// superServiceOverseeMapping.setServiceName(superService.getServiceName());
				// superServiceOverseeMapping.setTaskNote(superService.getTaskNote());
				// superServiceOverseeMapping.setCommandLeader(superService.getCommandLeader());
				// superServiceOverseeMapping.setCommandSource(superService.getCommandSource());
				// superServiceOverseeMapping.setOverseeName(superService.getOverseeName());//督办类型
				// superServiceOverseeMapping.setOverseeUnid(superService.getOverseeUnid());//督办类型名称
				superServiceOverseeMappingRepository.save(superServiceOverseeMapping);
				if (ToolUtil.isNotEmpty(superServiceOverseeMapping.getFileId())) {
					String fileId = superServiceOverseeMapping.getFileId();
					if (ToolUtil.isNotEmpty(fileId)) {
						SuperFile superFile = superFileRepository.findByFileId(fileId);
						if (superFile != null) {
							superFile.setSuperServiceId(superService.getUnid());
							superFile.setOverseeMappingId(superServiceOverseeMapping.getUnid());
							superFile.setServiceName(superService.getServiceName());
							superFile.setIsUse(1);
							superFileRepository.save(superFile);
						}
					}
				}
			}

		}
		// SuperServiceOverseeMapping superServiceOverseeMapping = new
		// SuperServiceOverseeMapping();
		// superServiceOverseeMapping.setUnid(UUID.randomUUID().toString());
		// superServiceOverseeMapping.setServiceTime(superService.getServiceTime());
		// superServiceOverseeMapping.setServiceName(superService.getServiceName());
		// superServiceOverseeMapping.setServiceUnid(superService.getUnid());
		// superServiceOverseeMapping.setTaskNote(superService.getTaskNote());
		// superServiceOverseeMapping.setCommandLeader(superService.getCommandLeader());
		// superServiceOverseeMapping.setCommandSource(superService.getCommandSource());
		// superServiceOverseeMapping.setCreateTime(new Date());
		// superServiceOverseeMapping.setCreateUserid(superService.getCreateUserid());
		// superServiceOverseeMapping.setIsPrimary("Y");
		// superServiceOverseeMapping.setOverseeName(superService.getOverseeName());//督办类型
		// superServiceOverseeMapping.setOverseeUnid(superService.getOverseeUnid());//督办类型名称
		// superServiceOverseeMapping.setStatus(1);
		// superServiceOverseeMappingRepository.save(superServiceOverseeMapping);
		Integer isSubmit = superServiceInfo.getIsSubmit();
		// serviceId
		String serviceId = superServiceIdCreateService.geSuperServiceId(superServiceInfo.getOverseeUnid());
		// 主办单位新增
		List<SuperSponsor> superSponsorlist = superServiceInfo.getSuperSponsor();
		if (superSponsorlist != null && superSponsorlist.size() > 0) {
			int num = 0;
			for (SuperSponsor superSponsor : superSponsorlist) {
				num += 1;
				String cal = Cal26ToNumUntil.getCal26r(num);
				// 信息补充
				superSponsor.setServiceUnid(superService.getUnid());
				// 督办类型2位+年份2位+序号4位+主办部门1位
				superSponsor.setUnid(UUID.randomUUID().toString());
				superSponsor.setServiceId(serviceId + "-" + cal);
				superSponsor.setCreateTime(DateUtil.getTime());
				superSponsor.setCreateUserid(superService.getCreateUserid());
				if (ToolUtil.isEmpty(superSponsor.getIsRead())) {
					superSponsor.setIsRead("N");
				}
				if ("Y".equals(superServiceInfo.getNeedVerify())) {
					superSponsor.setNeedVerify("Y");
				} else {
					superSponsor.setNeedVerify("N");
				}
				superSponsor.setVerifiers(superServiceInfo.getVerifiers());
				superSponsor.setStatus(1);
				superSponsor.setServiceStatus(superService.getServiceStatus());
				superSponsor.setCreateUsername(superService.getCreateUsername());
				superSponsor.setFeedbackRule(superServiceInfo.getFeedbackRule());
				//此处为要求反馈时间
				superSponsor.setFeedbackDeadline(superServiceInfo.getFeedbackDeadline());
				//superSponsor.setFeedbackTime(superServiceInfo.getFeedbackTime());
				superSponsorRepository.save(superSponsor);
//				if(isSubmit != null && isSubmit == 1){
//					// 督办部门历史记录
//					SuperSponsorLog sponsorlog = superSponsorLogService.add(superSponsor);
//					//日志记录
//					LogManager.me().executeLog(LogTaskFactory.systemLog(superSponsor.getUnid(),superSponsor.getServiceUnid(), null, 
//							JSON.toJSONString(superSponsor), 1, 1, superSponsor.getSponsorId(), superService.getOverseeUserid()));
//				}
			}
		}
		// 督办更新
		superServiceRepository.save(superService);
		//保存后，将事项unid返给页面，供弹出框使用
		date.put("unid",superService.getUnid());
		/*//立项操作
		if(isSubmit != null && isSubmit == 1){
			Boolean boo = null;
			try {
				boo = superSponsorService.SuperServiceOperator(superSponsorlist, superServiceOverseeMappinglist, superService, 1,frequency);
			} catch (MyException e) {
				//立项失败
				superService.setServiceStatus("草稿");
				superServiceRepository.save(superService);
				result.setCode(500);
				result.setMessage("推送给流程平台失败");
				return result;
			}
		}*/
		//标题，返给页面，显示在弹出框上
		String flowTitle = superService.getServiceName();
		date.put("flowTitle",flowTitle);
		result.setData(date);
		return result;
	}

	/**
	 * 督办事项详情查询
	 */
	@Override
	public SuperService findByUnid(String unid) {
		return superServiceRepository.findByUnid(unid);
	}

	/**
	 * 督办事项编辑
	 */
	@Override
	@Transactional
	public RESTResultBean editSuperService(SuperServiceInfo superServiceInfo) {
		RESTResultBean result = new RESTResultBean(200 ,"success");
		SuperService superService = translateSuperService(superServiceInfo);
		SuperSponsor sponsor = superSponsorService.findById(superService.getUnid());
		if(null != sponsor && ToolUtil.isNotEmpty(sponsor.getServiceUnid())){
			superService.setUnid(sponsor.getServiceUnid());
		}
		superServiceRepository.save(superService);
		Integer frequency = superServiceInfo.getOverseeFrequency();
		// 督办事项与督办类型关联
		superServiceOverseeMappingService.updateStatus(superService.getUnid(), "N");
		// 附件置为无效
		superFileService.updateIsNotUse(superService.getUnid());
		// 督办事项与督办类型关联详情新增
		List<SuperServiceOverseeMapping> list = superServiceInfo.getSuperServiceOverseeMapping();
		List<SuperServiceOverseeMapping> superServiceOverseeMappinglist = new ArrayList<>();
		for (SuperServiceOverseeMapping superServiceOverseeMapping : list) {
			// 信息补充
			if (superServiceOverseeMapping != null && superServiceOverseeMapping.getUnid() == null) {
				// 新增
				superServiceOverseeMapping.setUnid(UUID.randomUUID().toString());
				//superServiceOverseeMapping.setServiceTime(superService.getServiceTime());
				//superServiceOverseeMapping.setServiceName(superService.getServiceName());
				superServiceOverseeMapping.setServiceUnid(superService.getUnid());
				//superServiceOverseeMapping.setTaskNote(superService.getTaskNote());
				//superServiceOverseeMapping.setCommandLeader(superService.getCommandLeader());
				//superServiceOverseeMapping.setCommandSource(superService.getCommandSource());
				superServiceOverseeMapping.setCreateTime(DateUtil.getTime());
				superServiceOverseeMapping.setCreateUserid(superService.getCreateUserid());
				superServiceOverseeMapping.setIsPrimary("N");
				superServiceOverseeMapping.setStatus(1);
				// superServiceOverseeMapping.setOverseeName(superService.getOverseeName());//督办类型
				// superServiceOverseeMapping.setOverseeUnid(superService.getOverseeUnid());//督办类型名称
				superServiceOverseeMappingRepository.save(superServiceOverseeMapping);
			} else if (superServiceOverseeMapping != null && superServiceOverseeMapping.getUnid() != null) {
				//superServiceOverseeMapping = superServiceOverseeMappingRepository
						//.findByUnid(superServiceOverseeMapping.getUnid());
				superServiceOverseeMapping.setStatus(1);
				//superServiceOverseeMapping.setServiceTime(superService.getServiceTime());
				//superServiceOverseeMapping.setServiceName(superService.getServiceName());
				superServiceOverseeMapping.setServiceUnid(superService.getUnid());
				//superServiceOverseeMapping.setTaskNote(superService.getTaskNote());
				superServiceOverseeMappingRepository.save(superServiceOverseeMapping);
			}
			superServiceOverseeMappinglist.add(superServiceOverseeMapping);
			// 附件变更
			String fileId = superServiceOverseeMapping.getFileId();
			if (ToolUtil.isNotEmpty(fileId)) {
				SuperFile superFile = superFileRepository.findByFileId(fileId);
				if (superFile != null) {
					superFile.setSuperServiceId(superService.getUnid());
					superFile.setServiceName(superServiceOverseeMapping.getServiceName());
					superFile.setOverseeMappingId(superServiceOverseeMapping.getUnid());
					superFile.setIsUse(1);
					superFileRepository.save(superFile);
				}
			}
		}
		//Integer isSubmit = superServiceInfo.getIsSubmit();

		// 督办部门置为无效
		superSponsorService.updateStatus(superService.getUnid());
		// 主办单位新增
		String serviceId = null;
		List<SuperSponsor> superSponsorlist = superServiceInfo.getSuperSponsor();
		if (superSponsorlist != null && superSponsorlist.size() > 0) {
			int num = 0;
			serviceId = superSponsorlist.get(0).getServiceId();
			if (ToolUtil.isNotEmpty(serviceId)) {
				String[] serviceIds = serviceId.split("-");
				serviceId = serviceIds[0] + "-" + serviceIds[1] + "-" + serviceIds[2];
			}
			for (SuperSponsor superSponsor : superSponsorlist) {
				num += 1;
				String cal = Cal26ToNumUntil.getCal26r(num);
				if (ToolUtil.isEmpty(superSponsor.getUnid())) {
					// 信息补充
					superSponsor.setServiceUnid(superService.getUnid());
					// 督办类型2位+年份2位+序号4位+主办部门1位
					superSponsor.setUnid(UUID.randomUUID().toString());
					superSponsor.setServiceId(serviceId + "-" + cal);

					superSponsor.setCreateTime(DateUtil.getTime());
					superSponsor.setCreateUserid(superService.getCreateUserid());
					superSponsor.setCreateUsername(superService.getCreateUsername());
					if (ToolUtil.isEmpty(superSponsor.getIsRead())) {
						superSponsor.setIsRead("N");
					}
					superSponsor.setStatus(1);
					superSponsor.setServiceStatus(superService.getServiceStatus());
					//此处为要求反馈时间
					superSponsor.setFeedbackDeadline(superServiceInfo.getFeedbackDeadline());
					//superSponsor.setFeedbackTime(superServiceInfo.getFeedbackTime());
					superSponsor.setFeedbackRule(superServiceInfo.getFeedbackRule());
					// superSponsor.setApprovalTime(superService.geta);
					//此处为是否审核及审核人------lishuang
					superSponsor.setNeedVerify(superServiceInfo.getNeedVerify());
					superSponsor.setVerifiers(superServiceInfo.getVerifiers());
					superSponsorRepository.save(superSponsor);
				} else {
					SuperSponsor updateSuperSponsor = superSponsorRepository.findByUnid(superSponsor.getUnid());
					if (ToolUtil.isEmpty(superSponsor.getIsRead())) {
						updateSuperSponsor.setIsRead("N");
					}else {
					    updateSuperSponsor.setIsRead(superSponsor.getIsRead());
                    }
					updateSuperSponsor.setServiceId(serviceId + "-" + cal);
					updateSuperSponsor.setStatus(1);
					//此处为要求反馈时间
                    updateSuperSponsor.setFeedbackDeadline(superServiceInfo.getFeedbackDeadline());
					//superSponsor.setFeedbackTime(superServiceInfo.getFeedbackTime());
                    updateSuperSponsor.setFeedbackRule(superServiceInfo.getFeedbackRule());
					//updateSuperSponsor.setServiceStatus(superService.getServiceStatus());
					updateSuperSponsor.setServiceStatus("草稿");
					//此处为是否审核及审核人------lishuang
					updateSuperSponsor.setNeedVerify(superServiceInfo.getNeedVerify());
					updateSuperSponsor.setVerifiers(superServiceInfo.getVerifiers());
					//页面主办部门修改不成功bug--2019-03-22
					updateSuperSponsor.setOrgId(superSponsor.getOrgId());
					updateSuperSponsor.setOrgName(superSponsor.getOrgName());
					updateSuperSponsor.setCreateTime(DateUtil.getTime());
					superSponsorRepository.save(updateSuperSponsor);
				}
//				if(isSubmit != null && isSubmit == 1){
//					// 督办部门历史记录
//					SuperSponsorLog sponsorlog = superSponsorLogService.add(superSponsor);
//					//日志记录
//					LogManager.me().executeLog(LogTaskFactory.systemLog(superSponsor.getUnid(),superSponsor.getServiceUnid(), null, 
//							JSON.toJSONString(superSponsor), 1, 1, superSponsor.getSponsorId(), superService.getOverseeUserid()));
//				}
			}
			
			//立项操作
			/*if(isSubmit != null && isSubmit == 1){
				try {
					superSponsorlist = superSponsorService.findByServiceUnid(superService.getUnid(),null);
					superSponsorService.SuperServiceOperator(superSponsorlist, superServiceOverseeMappinglist, superService, 1,frequency);
				} catch (MyException e) {
					//立项失败
					superService.setServiceStatus("草稿");
					superServiceRepository.save(superService);
					result.setCode(500);
					result.setMessage("推送给流程平台失败");
					return result;
				}
			}*/
		}
		//保存成功后，返给页面的date
		JSONObject date = new JSONObject();
		date.put("unid",superService.getUnid());
		date.put("flowTitle",superService.getServiceName());
		result.setData(date);
		return result;
	}

	@Override
	public SuperService translateSuperService(SuperServiceInfo superServiceInfo) {
		SuperService superService = null;
		if (superServiceInfo != null) {
			superService = new SuperService();
			superService.setUnid(superServiceInfo.getUnid());
			superService.setServiceTime(superServiceInfo.getServiceTime());
			superService.setServiceLevel(superServiceInfo.getServiceLevel());
			superService.setServiceName(superServiceInfo.getServiceName());
			superService.setServiceStatus(superServiceInfo.getServiceStatus());
			superService.setServiceType(superServiceInfo.getServiceType());

			superService.setCommandLeader(superServiceInfo.getCommandLeader());
			superService.setCommandSource(superServiceInfo.getCommandSource());

			superService.setOverseeName(superServiceInfo.getOverseeName());
			superService.setOverseeUnid(superServiceInfo.getOverseeUnid());
			superService.setCreateTime(superServiceInfo.getCreateTime());
			superService.setCreateUserid(superServiceInfo.getCreateUserid());
			superService.setCreateUsername(superServiceInfo.getCreateUsername());
			superService.setNote(superServiceInfo.getNote());

			superService.setOverseeFrequency(superServiceInfo.getOverseeFrequency());
			superService.setOverseeUserid(superServiceInfo.getOverseeUserid());
			superService.setOverseeUsername(superServiceInfo.getOverseeUsername());
			superService.setBranchedLeader(superServiceInfo.getBranchedLeader());
			superService.setProposalEndTime(superServiceInfo.getProposalEndTime());
			superService.setProposalStartTime(superServiceInfo.getProposalStartTime());
			superService.setTaskNote(superServiceInfo.getTaskNote());
			superService.setEndTime(superServiceInfo.getEndTime());
			//新增时，手动设置默认为1有效状态（原因为数据库设置默认为1不起作用）--0418 lishuang
			superService.setStatus(1);
		}
		return superService;
	}

	@Override
	public SuperServiceInfo translateSuperServiceInfo(SuperService superService) {
		SuperServiceInfo superServiceInfo = null;
		if (superService != null) {
			superServiceInfo = new SuperServiceInfo();
			superServiceInfo.setUnid(superService.getUnid());
			superServiceInfo.setServiceTime(superService.getServiceTime());
			superServiceInfo.setServiceLevel(superService.getServiceLevel());
			superServiceInfo.setServiceName(superService.getServiceName());
			superServiceInfo.setServiceStatus(superService.getServiceStatus());
			superServiceInfo.setServiceType(superService.getServiceType());

			superServiceInfo.setCommandLeader(superService.getCommandLeader());
			superServiceInfo.setCommandSource(superService.getCommandSource());

			superServiceInfo.setOverseeName(superService.getOverseeName());
			superServiceInfo.setOverseeUnid(superService.getOverseeUnid());
			superServiceInfo.setCreateTime(superService.getCreateTime());
			superServiceInfo.setCreateUserid(superService.getCreateUserid());
			superServiceInfo.setCreateUsername(superService.getCreateUsername());
			superServiceInfo.setNote(superService.getNote());

			superServiceInfo.setOverseeFrequency(superService.getOverseeFrequency());
			superServiceInfo.setOverseeUserid(superService.getOverseeUserid());
			superServiceInfo.setOverseeUsername(superService.getOverseeUsername());
			superServiceInfo.setBranchedLeader(superService.getBranchedLeader());
			superServiceInfo.setProposalEndTime(superService.getProposalEndTime());
			superServiceInfo.setProposalStartTime(superService.getProposalStartTime());
			superServiceInfo.setTaskNote(superService.getTaskNote());
			superServiceInfo.setEndTime(superService.getEndTime());
		}
		return superServiceInfo;
	}

}
