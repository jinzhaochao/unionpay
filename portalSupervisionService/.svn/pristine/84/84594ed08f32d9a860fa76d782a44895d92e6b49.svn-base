package com.unionpay.voice.dao;


import java.util.List;
import java.util.Map;

import com.unionpay.voice.domain.VoiceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 客户之声附件表
 * @author lishuang
 * @email 1992lcg@163.com
 * @date 2019-05-08 10:16:29
 */
@Repository
public interface VoiceFileRepository extends JpaRepository<VoiceFile,Integer>, JpaSpecificationExecutor<VoiceFile> {

	VoiceFile findByUnid(String unid);

	VoiceFile findAllByUnid(String unid);

	List<VoiceFile> findAllByInfoUnid(String infoUnid);
}
