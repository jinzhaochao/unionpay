package com.unionpay.voice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.voice.model.CommentDto;
import com.unionpay.voice.model.CommentModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.unionpay.voice.service.VoiceCommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 客户之声评论信息接口
 * 
 * @author lishuang
 * @date 2019-05-08 10:13:05
 */
@Api(tags = "客户之声评论信息接口",description = "客户之声评论信息接口")
@Controller
@RequestMapping("/voiceComment")
public class VoiceCommentController {
	@Autowired
	private VoiceCommentService voiceCommentService;
	@Autowired
	private OmUserService omUserService;

	/**
	 * 新增评论
	 * @param commentModel
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新增评论接口",notes = "新增评论接口")
	@ResponseBody
	@PostMapping("/addVoiceComment")
	public RESTResultBean addVoiceComment(@RequestBody CommentModel commentModel, HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		String userid = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userid);
		if (ToolUtil.isEmpty(omUser)){
			result.setCode(500);
			result.setMessage("请先登录");
			return result;
		}
		voiceCommentService.save(commentModel,omUser);
		return result;
	}

	/**
	 * 分页条件查询评论
	 * @param page
	 * @param size
	 * @param commentStatus
	 * @param keyWord
	 * @return
	 */
	@ApiOperation(value = "分页条件查询评论接口",notes = "分页条件查询评论接口")
	@ResponseBody
	@PostMapping("/getPageVoiceComment")
	public RESTResultBean getPageVoiceComment(@RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "10")Integer size,
			@RequestParam(required = false) Integer commentStatus,@RequestParam(required = false) String keyWord){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		List<CommentDto> commentDtos = voiceCommentService.findAll(commentStatus,keyWord,page,size);
		result.setData(commentDtos);
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setSize(size);
		pager.setTotal(voiceCommentService.findTotal(commentStatus,keyWord));
		result.setPager(pager);
		return result;
	}

	/**
	 * 处理评论
	 * @param json
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "处理评论接口",notes = "处理评论接口")
	@ResponseBody
	@PostMapping("/handleComment")
	public RESTResultBean handleComment(@RequestBody String json,HttpServletRequest request){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		String userid = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userid);
		if (ToolUtil.isEmpty(omUser)){
			result.setCode(500);
			result.setMessage("未登录");
			return result;
		}
		if (ToolUtil.isNotEmpty(json)){
			JSONObject object = JSONObject.parseObject(json);
			JSONArray unids = object.getJSONArray("unid");
			String isPass = object.getString("pass");
			if (ToolUtil.isNotEmpty(unids)&&ToolUtil.isNotEmpty(isPass)){
				voiceCommentService.updateComment(unids,omUser,isPass);
			}
		}
		return result;
	}

}
