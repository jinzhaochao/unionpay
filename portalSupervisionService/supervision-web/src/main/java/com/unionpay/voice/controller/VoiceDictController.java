package com.unionpay.voice.controller;

import com.unionpay.common.resultBean.RESTResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unionpay.voice.service.VoiceDictService;

import java.util.Map;

/**
 * 客户之声字典信息接口
 * 
 * @author lishuang
 * @date 2019-05-08 10:16:28
 */
@Api(tags = "客户之声字典信息",description = "客户之声字典信息")
@Controller
@RequestMapping("/voiceDict")
public class VoiceDictController {
	@Autowired
	private VoiceDictService voiceDictService;
	@ApiOperation(value = "首页、新增、修改页面下拉框")
	@ResponseBody
	@GetMapping("/selectMap")
	public RESTResultBean selectMap(){
		RESTResultBean result = new RESTResultBean(200 ,"success");
		Map<String,Object> data = voiceDictService.findAll();
		result.setData(data);
		return result;
	}
}
