package com.unionpay.voice.dao;

import com.unionpay.voice.domain.VoiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 客户之声基本信息表
 * @author lishuang
 * @date 2019-05-08 10:16:30
 */
@Repository
public interface VoiceInfoRepository extends JpaRepository<VoiceInfo,String> , JpaSpecificationExecutor<VoiceInfo> {

	VoiceInfo findByUnid(String unid);
	
	//List<VoiceInfo> list(Map<String,Object> map);
	
	//int count(Map<String,Object> map);

	VoiceInfo save(VoiceInfo info);
	
	//int update(VoiceInfo info);
	
	int deleteByUnid(String unid);

	//int batchRemove(String[] unids);
}
