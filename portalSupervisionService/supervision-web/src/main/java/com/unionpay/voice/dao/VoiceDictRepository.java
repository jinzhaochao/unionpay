package com.unionpay.voice.dao;


import java.util.List;
import java.util.Map;

import com.unionpay.voice.domain.VoiceDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 客户之声字典表
 * @author lishuang
 * @email 1992lcg@163.com
 * @date 2019-05-08 10:16:28
 */
@Repository
public interface VoiceDictRepository extends JpaRepository<VoiceDict,Integer>, JpaSpecificationExecutor<VoiceDict> {
}
