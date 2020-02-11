package com.unionpay.voice.service;

import com.alibaba.fastjson.JSONArray;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.voice.domain.VoiceComment;
import com.unionpay.voice.model.CommentDto;
import com.unionpay.voice.model.CommentModel;

import java.util.List;

/**
 * 客户之声评论操作
 * 
 * @author lishuang
 * @date 2019-05-08 10:13:05
 */
public interface VoiceCommentService {

	/**
	 * 新增评论
	 * @param commentModel
	 * @param omUser
	 * @return
	 */
	public VoiceComment save(CommentModel commentModel, OmUser omUser);

	/**
	 * 分页条件查询评论
	 * @return
	 */
	public List<CommentDto> findAll(Integer commentStatus,String keyWord,Integer page,Integer size);

	/**
	 * 分页条件查询评论总数
	 * @return
	 */
	public int findTotal(Integer commentStatus, String keyWord);

	/**
	 * 处理评论
	 * @param unids
	 */
	public void updateComment(JSONArray unids,OmUser omUser,String isPass);

	/**
	 * 根据infoUnid查询所有评论
	 * @param infoUnid
	 * @return
	 */
	public List<CommentModel> getAllCommentByInfoUnid(String infoUnid);

}
