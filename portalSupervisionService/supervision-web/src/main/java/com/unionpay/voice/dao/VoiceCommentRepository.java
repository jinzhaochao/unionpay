package com.unionpay.voice.dao;


import com.unionpay.voice.domain.VoiceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 客户之声评论表
 * @author lishuang
 * @date 2019-05-08 10:13:05
 */
@Repository
public interface VoiceCommentRepository extends JpaRepository<VoiceComment,Integer>,JpaSpecificationExecutor<VoiceComment> {

	VoiceComment findByUnid(String unid);

	VoiceComment save(VoiceComment comment);
}
