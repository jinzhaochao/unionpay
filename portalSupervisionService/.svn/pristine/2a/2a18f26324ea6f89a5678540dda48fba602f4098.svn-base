package com.unionpay.voice.service.impl;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.voice.domain.VoiceDict;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.unionpay.voice.service.VoiceDictService;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class VoiceDictServiceImpl implements VoiceDictService {
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Map<String,Object> findAll(){
		String sql = "SELECT v.type,v.name,v.value FROM voice_dict v";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(VoiceDict.class));
		List<VoiceDict> list = query.getResultList();
		Map<String,Object> map = new LinkedHashMap<>();
		//案例标签
		List<VoiceDict> caseTags = new ArrayList<>();
		//案例类型
		List<VoiceDict> caseTypes = new ArrayList<>();
		//客户身份
		List<VoiceDict> clientIdentities = new ArrayList<>();
		//状态
		List<VoiceDict> status = new ArrayList<>();
		//是否首页展示
		List<VoiceDict> homeShow = new ArrayList<>();
		if (ToolUtil.isNotEmpty(list)){
			for (VoiceDict dict : list){
				if ("case_tag".equals(dict.getType())){
					caseTags.add(dict);
				}else if ("case_type".equals(dict.getType())){
					caseTypes.add(dict);
				}else if ("client_identity".equals(dict.getType())){
					clientIdentities.add(dict);
				}else if ("status".equals(dict.getType())){
					status.add(dict);
				}else if ("home_show".equals(dict.getType())){
					homeShow.add(dict);
				}
			}
			map.put("caseTags",caseTags);
			map.put("caseTypes",caseTypes);
			map.put("clientIdentities",clientIdentities);
			map.put("status",status);
			map.put("homeShow",homeShow);
		}
		return map;
	}
	
}
