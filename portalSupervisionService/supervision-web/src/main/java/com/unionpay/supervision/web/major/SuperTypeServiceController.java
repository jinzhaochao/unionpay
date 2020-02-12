package com.unionpay.supervision.web.major;

import java.util.List;

import com.unionpay.supervision.domain.SuperTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.service.SuperTypeServiceService;

import io.swagger.annotations.Api;

/**
 * <p>
 *  事项类型Controller
 * </p>
 *
 * @author xiongym
 * @since 2018-12-20
 */
@Api(value = "SuperTypeServiceController", description = "事项类型Controller")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/superType")
public class SuperTypeServiceController {
	
	@Autowired
	private SuperTypeServiceService superTypeServiceService;
	
	/**
	 * 事项类型型新增 
	 */
	@ResponseBody
	@PostMapping("/addSuperTypeService")
	public RESTResultBean addSuperTypeService(String typeName) {
		RESTResultBean result = new RESTResultBean(200, "success");
		SuperTypeService superTypeService = null;
		if(ToolUtil.isEmpty(typeName)){
			result.setCode(500);
			result.setMessage("typeName is null");
			return result;
		}
		superTypeService = superTypeServiceService.findByTypeName(typeName);
		if(superTypeService != null ){
			result.setCode(500);
			result.setMessage("重复新增!!!");
			return result;
		}
		superTypeService= superTypeServiceService.add(typeName);
		result.setData(superTypeService);
		return result;
	}
	
	/**
	 * 事项类型型传删除 
	 */
	@ResponseBody
	@PostMapping("/deleteSuperTypeService")
	public RESTResultBean deleteSuperTypeService(SuperTypeService superTypeService) {
		RESTResultBean result = new RESTResultBean(200, "success");
		String unid = superTypeService.getUnid();
		if(ToolUtil.isEmpty(unid)){
			result.setCode(500);
			result.setMessage("unid is null");
			return result;
		}
		superTypeService = superTypeServiceService.findByUnid(unid);
		if(superTypeService == null ){
			result.setCode(500);
			result.setMessage("事项类型不存在");
			return result;
		}
		superTypeServiceService.delete(unid);
		return result;
	}
	
	
	/**
	 * 事项类型型传查询 
	 */
	@ResponseBody
	@GetMapping("/findByUnid")
	public RESTResultBean findByUnid(String unid) {
		RESTResultBean result = new RESTResultBean(200, "success");
		SuperTypeService superTypeService = null;
		if(ToolUtil.isEmpty(unid)){
			result.setCode(500);
			result.setMessage("unid is null");
			return result;
		}
		superTypeService = superTypeServiceService.findByUnid(unid);
		if(superTypeService == null ){
			result.setCode(500);
			result.setMessage("事项类型不存在");
			return result;
		}
		result.setData(superTypeService);
		return result;
	}
	
	/**
	 * 事项类型型传查询 
	 */
	@ResponseBody
	@GetMapping("/findAll")
	public RESTResultBean findAll() {
		RESTResultBean result = new RESTResultBean(200, "success");
		List<SuperTypeService> superTypeServicelist = superTypeServiceService.findAll();
		result.setData(superTypeServicelist);
		return result;
	}

}
