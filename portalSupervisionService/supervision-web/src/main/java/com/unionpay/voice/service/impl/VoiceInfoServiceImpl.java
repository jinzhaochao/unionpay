package com.unionpay.voice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.exception.ServiceException;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.ModelDto;
import com.unionpay.sms.model.SmsOmOrganization;
import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.domain.OmOrganization;
import com.unionpay.voice.domain.VoiceFile;
import com.unionpay.voice.domain.VoiceInfo;
import com.unionpay.voice.model.*;
import com.unionpay.voice.service.VoiceCommentService;
import com.unionpay.voice.service.VoiceFileService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

import com.unionpay.voice.dao.VoiceInfoRepository;
import com.unionpay.voice.service.VoiceInfoService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;


@Service
public class VoiceInfoServiceImpl implements VoiceInfoService {
	@Autowired
	private VoiceInfoRepository voiceInfoRepository;
	@Autowired
	private OMOrganizationRepository omOrganizationRepository;
	@Autowired
	private VoiceFileService voiceFileService;
	@Autowired
	private VoiceCommentService voiceCommentService;
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public VoiceInfo get(String unid){
		return voiceInfoRepository.findByUnid(unid);
	}

	/**
	 * 分页条件查询数据
	 * @param searchContionModel
	 * @return
	 */
	@Override
	public List<VoiceInfoModel> list(SearchContionModel searchContionModel,Integer page,Integer size){
		String sql = "SELECT v.unid,v.title,om.ORGNAME orgName,DATE_FORMAT(v.transfer_time,'%Y-%m-%d') transferTime,"
				+ "DATE_FORMAT(v.create_time,'%Y-%m-%d') createTime,"
				+ "v.read_number readNumber,(SELECT COUNT(*) FROM voice_comment vv where vv.info_unid = v.unid) commentNumber "
				+ " FROM voice_info v LEFT JOIN om_organization om ON om.ORGID = v.handle_orgid WHERE 1=1 ";
		if (searchContionModel.getFrontPage()){
			sql += " AND v.status = '1' ";
		}else {
			if (ToolUtil.isNotEmpty(searchContionModel.getStatus())){
				sql += " AND v.status =:status ";
			}

		}
		sql += getConditionsql(searchContionModel,true);
		Query query = entityManager.createNativeQuery(sql);
		if (!searchContionModel.getFrontPage()&&ToolUtil.isNotEmpty(searchContionModel.getStatus())){
			query.setParameter("status",searchContionModel.getStatus());
		}
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(VoiceInfoModel.class));
		query.setFirstResult((page-1)*size);
		query.setMaxResults(size);
		List<VoiceInfoModel> voiceInfoModels = query.getResultList();
		return voiceInfoModels;
	}

	/**
	 * 分页条件查询数据总数
	 * @param searchContionModel
	 * @return
	 */
	public int getTotal(SearchContionModel searchContionModel){
		int count = 0;
		String sql = "SELECT COUNT(v.unid) FROM voice_info v LEFT JOIN om_organization om ON om.ORGID = v.handle_orgid WHERE 1=1 ";
		if (searchContionModel.getFrontPage()){
			sql += " AND v.status = '1' ";
		}else {
			if (ToolUtil.isNotEmpty(searchContionModel.getStatus())){
				sql += " AND v.status =:status ";
			}
		}
		sql += getConditionsql(searchContionModel,false);
		Query query = entityManager.createNativeQuery(sql);
		if (!searchContionModel.getFrontPage()&&ToolUtil.isNotEmpty(searchContionModel.getStatus())){
			query.setParameter("status",searchContionModel.getStatus());
		}
		List<BigInteger> counts = query.getResultList();
		if (ToolUtil.isNotEmpty(counts)){
			count = counts.get(0).intValue();
		}
		return count;
	}

	private String getConditionsql(SearchContionModel searchContionModel,Boolean getTotal){
		String conditionsql = "";
		if (ToolUtil.isNotEmpty(searchContionModel.getCaseTag())){
			conditionsql += " AND v.case_tag ='"+searchContionModel.getCaseTag()+"'";
		}
		if (ToolUtil.isNotEmpty(searchContionModel.getCaseType())){
			conditionsql += " AND v.case_type ='"+searchContionModel.getCaseType()+"'";
		}
		if (ToolUtil.isNotEmpty(searchContionModel.getBeginTime())){
			conditionsql += " AND v.create_time >='"+searchContionModel.getBeginTime()+"'";
		}
		if (ToolUtil.isNotEmpty(searchContionModel.getEndTime())){
			conditionsql += " AND v.create_time <='"+searchContionModel.getEndTime()+" 23:59:59"+"'";
		}
		if (ToolUtil.isNotEmpty(searchContionModel.getOrgid())){
			conditionsql += " AND v.handle_orgid ='"+searchContionModel.getOrgid()+"'";
		}
		if (ToolUtil.isNotEmpty(searchContionModel.getKeyWord())){
			conditionsql += " AND (v.title LIKE CONCAT('%"+searchContionModel.getKeyWord()+"%')"
					+ " OR v.info_content LIKE CONCAT('%"+searchContionModel.getKeyWord()+"%')"
					+ " OR v.handle_content LIKE CONCAT('%"+searchContionModel.getKeyWord()+"%'))";
		}
		/*if (ToolUtil.isNotEmpty(searchContionModel.getStatus())){
			conditionsql += " AND v.status ='"+searchContionModel.getStatus()+"'";
		}*/
		if (ToolUtil.isNotEmpty(searchContionModel.getHomeShow())){
			conditionsql += " AND v.home_show ='"+searchContionModel.getHomeShow()+"'";
		}
		if (ToolUtil.isNotEmpty(searchContionModel.getClientIdentity())){
			conditionsql += " AND v.client_identity ='"+searchContionModel.getClientIdentity()+"'";
		}
		//根据条件查询总数时，不拼接以下sql排序语句
		if (getTotal){
			if (ToolUtil.isNotEmpty(searchContionModel.getCreateTime())){
				if (1==searchContionModel.getCreateTime()){
					conditionsql += " ORDER BY v.create_time ASC";
				}else {
					conditionsql += " ORDER BY v.create_time DESC";
				}
			}else if (ToolUtil.isNotEmpty(searchContionModel.getReadNumber())){
				if (1==searchContionModel.getReadNumber()){
					conditionsql += " ORDER BY v.read_number ASC";
				}else {
					conditionsql += " ORDER BY v.read_number DESC";
				}
			}else if (ToolUtil.isNotEmpty(searchContionModel.getCommentNumber())){
				if (1==searchContionModel.getCommentNumber()){
					conditionsql += " ORDER BY commentNumber ASC";
				}else {
					conditionsql += " ORDER BY commentNumber DESC";
				}
			}else {
				conditionsql += " ORDER BY v.create_time DESC";
			}
		}

		return conditionsql;
	}

	/**
	 * 新增或修改基本信息
	 * @param voiceInfoDto
	 * @param userid
	 * @return
	 */
	@Override
	@Transactional
	public VoiceInfo addOrUpdate(VoiceInfoDto voiceInfoDto, String userid){
		VoiceInfo info = new VoiceInfo();
		//基本信息新增
		if (voiceInfoDto.getAdd()){
			String uuid = UUID.randomUUID().toString();
			info.setUnid(uuid);
		}
		//基本信息修改
		if (voiceInfoDto.getUpdate()){
			info = voiceInfoRepository.findByUnid(voiceInfoDto.getUnid());
		}
		//数据修改
		info.setTitle(voiceInfoDto.getTitle());
		info.setStatus(voiceInfoDto.getStatus());
		info.setHomeShow(voiceInfoDto.getHomeShow());
		info.setCaseTag(voiceInfoDto.getCaseTag());
		info.setClientIdentity(voiceInfoDto.getClientIdentity());
		info.setCaseType(voiceInfoDto.getCaseType());
		info.setCallTime(voiceInfoDto.getCallTime());
		info.setTransferTime(voiceInfoDto.getTransferTime());
		info.setInfoContent(voiceInfoDto.getInfoContent());
		info.setHandleOrgid(voiceInfoDto.getHandleOrgid());
		info.setHandleTime(voiceInfoDto.getHandleTime());
		info.setHandleContent(voiceInfoDto.getHandleContent());
		info.setCreateUserid(userid);
		info.setCreateTime(DateUtil.getStrTime(new Date()));
		info = voiceInfoRepository.save(info);
		//音频信息补充
		if (ToolUtil.isNotEmpty(voiceInfoDto.getAudioUnids())){
			String[] unids = voiceInfoDto.getAudioUnids().split(",");
			for (String unid : unids){
				if (ToolUtil.isNotEmpty(unid)){
					VoiceFile voiceFile = voiceFileService.get(unid);
					voiceFile.setInfoUnid(info.getUnid());
					voiceFile.setFileType(3);
					voiceFileService.save(voiceFile);
				}
			}
		}
		//图片信息补充
		if (ToolUtil.isNotEmpty(voiceInfoDto.getPicUnids())){
			String[] unids = voiceInfoDto.getPicUnids().split(",");
			for (String unid : unids){
				if (ToolUtil.isNotEmpty(unid)){
					VoiceFile voiceFile = voiceFileService.get(unid);
					voiceFile.setInfoUnid(info.getUnid());
					voiceFile.setFileType(1);
					voiceFileService.save(voiceFile);
				}
			}
		}
		//文档信息补充
		if (ToolUtil.isNotEmpty(voiceInfoDto.getDocUnids())){
			String[] unids = voiceInfoDto.getDocUnids().split(",");
			for (String unid : unids){
				if (ToolUtil.isNotEmpty(unid)){
					VoiceFile voiceFile = voiceFileService.get(unid);
					voiceFile.setInfoUnid(info.getUnid());
					voiceFile.setFileType(2);
					voiceFileService.save(voiceFile);
				}
			}
		}
		return info;
	}
	
	@Override
	public VoiceInfo update(VoiceInfo info){
		return voiceInfoRepository.save(info);
	}

	/**
	 * 处理部门树信息
	 * @return
	 */
	public JSONArray findInfos(){
		JSONArray array = new JSONArray();
		//总公司
		OmOrganization headOffice = omOrganizationRepository.findByOrgid(206);
		ModelDto head = new ModelDto();
		String headId = (headOffice.getOrgid()+" ").trim();
		head.setId(headId);
		head.setName(headOffice.getOrgname());
		if (ToolUtil.isNotEmpty(head)){
			find(head);
		}
		array.add(head);
		//分公司
		OmOrganization branchOffice = omOrganizationRepository.findByOrgid(450);
		ModelDto branch = new ModelDto();
		String branchId = (branchOffice.getOrgid()+" ").trim();
		branch.setId(branchId);
		branch.setName(branchOffice.getOrgname());
		String firstSql = "select om.orgid orgid,om.orgname orgname,om.orgtype orgtype,om.parentorgid parentorgid,om.orglevel orglevel " +
				"from om_organization om where om.parentorgid=:parentOrgid AND om.STATUS =:status AND om.orgtype =:orgtype";
		Query firstQuery = entityManager.createNativeQuery(firstSql);
		firstQuery.setParameter("parentOrgid",branch.getId());
		firstQuery.setParameter("status",0);
		firstQuery.setParameter("orgtype",03);
		firstQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsOmOrganization.class));
		List<SmsOmOrganization> omOrganizations = firstQuery.getResultList();
		if (ToolUtil.isNotEmpty(omOrganizations)){
			branch.setIsParent(true);
			JSONArray branchChild = new JSONArray();
			for (SmsOmOrganization organization : omOrganizations){
				ModelDto modelDto = new ModelDto();
				modelDto.setId(organization.getOrgid().toString());
				modelDto.setName(organization.getOrgname());
				find(modelDto);
				branchChild.add(modelDto);
			}
			branch.setChildren(branchChild);
		}
		array.add(branch);
		return array;
	}
	private ModelDto find(ModelDto model){
		String firstSql = "select om.orgid orgid,om.orgname orgname,om.orgtype orgtype,om.parentorgid parentorgid,om.orglevel orglevel " +
				"from om_organization om where om.parentorgid=:parentOrgid AND om.STATUS =:status AND om.orgtype =:orgtype";
		Query firstQuery = entityManager.createNativeQuery(firstSql);
		firstQuery.setParameter("parentOrgid",model.getId());
		firstQuery.setParameter("status",0);
		firstQuery.setParameter("orgtype",04);
		firstQuery.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SmsOmOrganization.class));
		List<SmsOmOrganization> omOrganizations = firstQuery.getResultList();
		List<ModelDto> children = new ArrayList<>();
		if (ToolUtil.isNotEmpty(omOrganizations)){
			model.setIsParent(true);
			for (SmsOmOrganization organization : omOrganizations){
				ModelDto modelDto = new ModelDto();
				modelDto.setId(organization.getOrgid().toString());
				modelDto.setName(organization.getOrgname());
				children.add(modelDto);
			}
		}else {
			model.setIsParent(false);
		}
		model.setChildren(children);
		if (model.getIsParent()&&ToolUtil.isNotEmpty(children)){
			for (ModelDto dto : children){
				find(dto);
			}
		}
		return model;
	}

	/**
	 * 查询基本信息
	 * @param infoUnid
	 * @return
	 */
	public JSONObject getInfo(String infoUnid){
		JSONObject info = new JSONObject();
		//基本信息
		String sql = "SELECT v.unid,v.title,vd.`name` caseTag,vod.`name` caseType,v.info_content infoContent,DATE_FORMAT(v.transfer_time,"
				   + "'%Y-%m-%d') transferTime,DATE_FORMAT(v.call_time,'%Y-%m-%d') callTime,om.ORGNAME handleOrgName,"
				   + "DATE_FORMAT(v.handle_time,'%Y-%m-%d') handleTime,v.handle_content handleContent FROM `voice_info` v LEFT "
				   + "JOIN om_organization om ON om.ORGID = v.handle_orgid LEFT JOIN voice_dict vd ON vd.`value` = v.case_tag and "
				   + "vd.type='case_tag' LEFT JOIN voice_dict vod ON vod.`value` = v.case_type and vod.type='case_type' WHERE v.unid =:infoUnid";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(InfoModel.class));
		query.setParameter("infoUnid",infoUnid);
		InfoModel infoModel = (InfoModel)query.getSingleResult();
		info.put("voiceInfo",infoModel);
		//附件信息
		List<VoiceFile> list = voiceFileService.getAllByInfoUnid(infoUnid);
		if (ToolUtil.isNotEmpty(list)){
			List<FileModel> picFile = new ArrayList<>();
			List<FileModel> audioFile = new ArrayList<>();
			List<FileModel> docFile = new ArrayList<>();
			for (VoiceFile file : list){
				FileModel fileModel = new FileModel();
				if (1==file.getFileType()){
					fileModel.setUnid(file.getUnid());
					fileModel.setFileName(file.getFileName());
					picFile.add(fileModel);
				}else if (2==file.getFileType()){
					fileModel.setUnid(file.getUnid());
					fileModel.setFileName(file.getFileName());
					docFile.add(fileModel);
				}else if (3==file.getFileType()){
					fileModel.setUnid(file.getUnid());
					fileModel.setFileName(file.getFileName());
					audioFile.add(fileModel);
				}
			}
			info.put("picFiles",picFile);
			info.put("docFiles",docFile);
			info.put("audioFiles",audioFile);
		}
		//评论信息
		List<CommentModel> models = voiceCommentService.getAllCommentByInfoUnid(infoUnid);
		info.put("comments",models);
		return info;
	}

	/**
	 * 查询基本信息-管理页面
	 * @param unid
	 * @return
	 */
	public JSONObject findInfo(String unid){
		JSONObject info = new JSONObject();
		String sql = "SELECT v.unid,v.title,v.info_content infoContent,v.`status`,v.home_show homeShow,v.case_tag caseTag,v.case_type caseType,"
				+ "DATE_FORMAT(v.transfer_time,'%Y-%m-%d') transferTime,DATE_FORMAT(v.call_time,'%Y-%m-%d') callTime,"
				+ "v.client_identity clientIdentity,v.handle_orgid handleOrgid,DATE_FORMAT(v.handle_time,'%Y-%m-%d') handleTime,"
				+ "v.handle_content handleContent FROM\tvoice_info v WHERE unid =:unid";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(VoiceInfoDto.class));
		query.setParameter("unid",unid);
		VoiceInfoDto voiceInfoDto = (VoiceInfoDto)query.getSingleResult();
		info.put("voiceInfo",voiceInfoDto);
		//附件信息
		List<VoiceFile> list = voiceFileService.getAllByInfoUnid(unid);
		if (ToolUtil.isNotEmpty(list)){
			List<FileModel> picFile = new ArrayList<>();
			List<FileModel> audioFile = new ArrayList<>();
			List<FileModel> docFile = new ArrayList<>();
			for (VoiceFile file : list){
				FileModel fileModel = new FileModel();
				if (1==file.getFileType()){
					fileModel.setUnid(file.getUnid());
					fileModel.setFileName(file.getFileName());
					picFile.add(fileModel);
				}else if (2==file.getFileType()){
					fileModel.setUnid(file.getUnid());
					fileModel.setFileName(file.getFileName());
					docFile.add(fileModel);
				}else if (3==file.getFileType()){
					fileModel.setUnid(file.getUnid());
					fileModel.setFileName(file.getFileName());
					audioFile.add(fileModel);
				}
			}
			info.put("picFiles",picFile);
			info.put("docFiles",docFile);
			info.put("audioFiles",audioFile);
		}
		return info;
	}

	/**
	 * 新增基本信息（第三方调用接口）
	 * @param lcVoiceInfoModel
	 */
	public VoiceInfo addVoiceInfo(LcVoiceInfoModel lcVoiceInfoModel) throws ServiceException {
		//基本信息新增
		String uuid = UUID.randomUUID().toString();
		VoiceInfo voiceInfo = new VoiceInfo();
		voiceInfo.setUnid(uuid);
		voiceInfo.setTitle(lcVoiceInfoModel.getTitle());
		voiceInfo.setInfoContent(lcVoiceInfoModel.getInfoContent());
		voiceInfo.setStatus(lcVoiceInfoModel.getStatus());
		voiceInfo.setHomeShow(lcVoiceInfoModel.getHomeShow());
		voiceInfo.setCaseTag(lcVoiceInfoModel.getCaseTag());
		voiceInfo.setCaseType(lcVoiceInfoModel.getCaseType());
		voiceInfo.setClientIdentity(lcVoiceInfoModel.getClientIdentity());
		voiceInfo.setCallTime(lcVoiceInfoModel.getCallTime());
		voiceInfo.setTransferTime(lcVoiceInfoModel.getTransferTime());
		voiceInfo.setCreateTime(DateUtil.getStrTime(new Date()));
		voiceInfo.setHandleOrgid(lcVoiceInfoModel.getHandleOrgid());
		voiceInfo.setHandleTime(lcVoiceInfoModel.getHandleTime());
		voiceInfo.setHandleContent(lcVoiceInfoModel.getHandleContent());
		voiceInfo.setReadNumber(0);
		voiceInfoRepository.save(voiceInfo);
		//音频信息补充
		if (ToolUtil.isNotEmpty(lcVoiceInfoModel.getAudioUnids())){
			String[] unids = lcVoiceInfoModel.getAudioUnids().split(",");
			for (String unid : unids){
				if (ToolUtil.isNotEmpty(unid)){
					VoiceFile voiceFile = voiceFileService.get(unid);
					voiceFile.setInfoUnid(uuid);
					voiceFile.setFileType(3);
					voiceFileService.save(voiceFile);
				}
			}
		}
		//图片信息补充
		if (ToolUtil.isNotEmpty(lcVoiceInfoModel.getPicUnids())){
			String[] unids = lcVoiceInfoModel.getPicUnids().split(",");
			for (String  unid : unids){
				if (ToolUtil.isNotEmpty(unid)){
					VoiceFile voiceFile = voiceFileService.get(unid);
					voiceFile.setInfoUnid(uuid);
					voiceFile.setFileType(1);
					voiceFileService.save(voiceFile);
				}
			}
		}
		//文档信息补充
		if (ToolUtil.isNotEmpty(lcVoiceInfoModel.getDocUnids())){
			String[] unids = lcVoiceInfoModel.getDocUnids().split(",");
			for (String unid : unids){
				if (ToolUtil.isNotEmpty(unid)){
					VoiceFile voiceFile = voiceFileService.get(unid);
					voiceFile.setInfoUnid(uuid);
					voiceFile.setFileType(2);
					voiceFileService.save(voiceFile);
				}
			}
		}
		return voiceInfo;
	}

	/**
	 * 处理部门树信息
	 * @return
	 */
	public JSONArray findInfos(Integer id){
		JSONArray array = new JSONArray();
		if ( 0 == id ){
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
}
