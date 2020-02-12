package com.unionpay.voice.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.exception.ServiceException;
import com.unionpay.voice.domain.VoiceInfo;
import com.unionpay.voice.model.LcVoiceInfoModel;
import com.unionpay.voice.model.SearchContionModel;
import com.unionpay.voice.model.VoiceInfoDto;
import com.unionpay.voice.model.VoiceInfoModel;

import java.util.List;

/**
 * 客户之声基本信息操作
 * 
 * @author lishuang
 * @date 2019-05-08 10:16:30
 */
public interface VoiceInfoService {

	VoiceInfo get(String unid);

	/**
	 * 分页条件查询数据
	 * @param searchContionModel
	 * @return
	 */
	public List<VoiceInfoModel> list(SearchContionModel searchContionModel,Integer page,Integer size);

	/**
	 * 分页条件查询数据总数
	 * @param searchContionModel
	 * @return
	 */
	public int getTotal(SearchContionModel searchContionModel);

    /**
     * 新增或修改基本信息
     * @param info
     * @param userid
     * @return
     */
	VoiceInfo addOrUpdate(VoiceInfoDto info, String userid);

	VoiceInfo update(VoiceInfo info);

    /**
     * 处理部门树信息
     * @return
     */
	public JSONArray findInfos();

	/**
	 * 查询基本信息
	 * @param infoUnid
	 * @return
	 */
	public JSONObject getInfo(String infoUnid);

	/**
	 * 查询基本信息-管理页面
	 * @param infoUnid
	 * @return
	 */
	public JSONObject findInfo(String infoUnid);

	/**
	 * 新增基本信息（第三方调用接口）
	 * @param lcVoiceInfoModel
	 */
	public VoiceInfo addVoiceInfo(LcVoiceInfoModel lcVoiceInfoModel) throws ServiceException;

	/**
	 * 处理部门树信息
	 * @return
	 */
	public JSONArray findInfos(Integer id);
}
