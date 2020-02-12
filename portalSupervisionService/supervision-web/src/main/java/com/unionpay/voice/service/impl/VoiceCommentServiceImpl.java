package com.unionpay.voice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.voice.domain.VoiceComment;
import com.unionpay.voice.model.CommentDto;
import com.unionpay.voice.model.CommentModel;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unionpay.voice.dao.VoiceCommentRepository;
import com.unionpay.voice.service.VoiceCommentService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class VoiceCommentServiceImpl implements VoiceCommentService {
	@Autowired
	private VoiceCommentRepository voiceCommentRepository;
	@Autowired
	private EntityManager entityManager;

	/**
	 * 新增评论
	 * @param commentModel
	 * @param omUser
	 * @return
	 */
	@Override
	@Transactional
	public VoiceComment save(CommentModel commentModel,OmUser omUser){
		VoiceComment voiceComment = new VoiceComment();
		String uuid = UUID.randomUUID().toString();
		voiceComment.setUnid(uuid);
		voiceComment.setInfoUnid(commentModel.getInfoUnid());
		voiceComment.setCommentContent(commentModel.getCommentContent());
		//匿名（1是；0否）
		if (1==commentModel.getIncognito()){
			voiceComment.setShowName("匿名");
			voiceComment.setIncognito(1);
		}else {
			voiceComment.setShowName(omUser.getEmpname());
			voiceComment.setIncognito(0);
		}
		voiceComment.setCommentUserid(omUser.getUserid());
		voiceComment.setCommentEmpname(omUser.getEmpname());
		voiceComment.setCreateTime(DateUtil.getStrTime(new Date()));
		voiceComment.setCommentStatus(1);//状态（1待审核；2通过；3未通过）
		return voiceCommentRepository.save(voiceComment);
	}

	/**
	 * 分页条件查询评论
	 * @return
	 */
	public List<CommentDto> findAll(Integer commentStatus, String keyWord,Integer page,Integer size){
		String sql = "SELECT vc.unid,v.title,vc.comment_content commentContent,vc.incognito,vc.comment_empname commentEmpname,"
				+ "DATE_FORMAT(vc.create_time,'%Y-%m-%d %H:%i:%S') createTime,vc.comment_status commentStatus"
				+ " FROM `voice_comment` vc LEFT JOIN voice_info v ON vc.info_unid = v.unid WHERE 1=1";
		if (ToolUtil.isNotEmpty(commentStatus)){
			sql += " AND vc.comment_status ='"+commentStatus+"'";
		}
		if (ToolUtil.isNotEmpty(keyWord)){
			sql += " AND (v.title LIKE CONCAT('%"+keyWord+"%') OR vc.comment_content LIKE CONCAT('%"+keyWord+"%'))";
		}
		sql += " ORDER BY vc.create_time DESC";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(CommentDto.class));
		query.setFirstResult((page-1)*size);
		query.setMaxResults(size);
		List<CommentDto> list = query.getResultList();
		return list;
	}

	/**
	 * 分页条件查询评论总数
	 * @return
	 */
	public int findTotal(Integer commentStatus, String keyWord){
		int count = 0;
		String sql = "SELECT COUNT(vc.unid) FROM `voice_comment` vc LEFT JOIN voice_info v ON vc.info_unid = v.unid WHERE 1=1";
		if (ToolUtil.isNotEmpty(commentStatus)){
			sql += " AND vc.comment_status ='"+commentStatus+"'";
		}
		if (ToolUtil.isNotEmpty(keyWord)){
			sql += " AND (v.title LIKE CONCAT('%"+keyWord+"%') OR vc.comment_content LIKE CONCAT('%"+keyWord+"%'))";
		}
		Query query = entityManager.createNativeQuery(sql);
		List<BigInteger> counts = query.getResultList();
		if (ToolUtil.isNotEmpty(counts)){
			count = counts.get(0).intValue();
		}
		return count;
	}

	/**
	 * 处理评论
	 * @param unids
	 */
	@Override
	@Transactional
	public void updateComment(JSONArray unids,OmUser omUser,String isPass){
		if (ToolUtil.isNotEmpty(unids)){
			for (Object unid : unids){
				VoiceComment comment = voiceCommentRepository.findByUnid(unid.toString());
				comment.setApproverUserid(omUser.getUserid());
				comment.setApproverEmpname(omUser.getEmpname());
				comment.setApproverTime(DateUtil.getStrTime(new Date()));
				if ("1".equals(isPass)){
					comment.setCommentStatus(2);
				}else {
					comment.setCommentStatus(3);
				}
				voiceCommentRepository.save(comment);
			}
		}
	}

	/**
	 * 根据infoUnid查询所有评论
	 * @param infoUnid
	 * @return
	 */
	public List<CommentModel> getAllCommentByInfoUnid(String infoUnid){
		String sql = "SELECT v.show_name showName,v.comment_content commentContent,DATE_FORMAT(v.create_time,'%Y-%m-%d %H:%i:%S')"
				   + " createTime FROM `voice_comment` v WHERE v.info_unid =:infoUnid AND v.comment_status = '2' ORDER BY v.create_time";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(CommentModel.class));
		query.setParameter("infoUnid",infoUnid);
		List<CommentModel> models = query.getResultList();
		return models;
	}
	
}
