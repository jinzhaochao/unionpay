package com.unionpay.supervision.web.major;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.domain.SuperTypeOversee;
import com.unionpay.supervision.model.OverseeMappingDto;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.supervision.service.SuperServiceOverseeMappingService;
import com.unionpay.supervision.service.SuperTypeOverseeService;
import com.unionpay.supervision.supportController.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "SuperTypeOverseeController")
@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("oversee")
public class SuperTypeOverseeController extends BaseController {
	@Autowired
	private SuperTypeOverseeService superTypeOverseeService;
	@Autowired
	private SuperServiceOverseeMappingService superServiceOverseeMappingService;
	@Autowired
    private OmUserService omUserService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 督办类型删除
	 * 
	 * @param SuperTypeOversee
	 */
	@DeleteMapping("/delete")
	public RESTResultBean deleteSuperTypeOversee(SuperTypeOversee SuperTypeOversee) {
		RESTResultBean result = new RESTResultBean(200, "success");

		SuperTypeOversee superTypeOversee = superTypeOverseeService.getFindById(SuperTypeOversee);
		if (superTypeOversee != null) {
			// 查询关联关系
			Boolean iscontact = superServiceOverseeMappingService.isContaOversee(superTypeOversee.getUnid());
			if (iscontact) {
				result.setCode(500);
				result.setMessage("督办类型有相关关联，不能删除");
				return result;
			}
			logger.info(SuperTypeOversee.toString());
			superTypeOverseeService.DeleteSuperTypeOversee(superTypeOversee);
		}
		return result;
	}

	/**
	 * 督办类型新增 新增时应查看前一条数据的sort与typeId,生成新的（xiongym） 重复性的不可添加
	 * 
	 * @param SuperTypeOversee
	 */
	@ResponseBody
	@PostMapping("/add")
	public RESTResultBean addSuperTypeOversee(SuperTypeOversee SuperTypeOversee) {
		RESTResultBean result = new RESTResultBean(200, "success");
		if (SuperTypeOversee.getTypeName() != null) {
			logger.info(SuperTypeOversee.toString());
			// 查询是否重复新增
			List<SuperTypeOversee> list = superTypeOverseeService.findByTypeId(SuperTypeOversee.getTypeId());
			if (list != null && list.size() > 0) {
				result.setCode(500);
				result.setMessage("重复新增!!!");
				return result;
			}
			//自定义事项大类时,增加指定事项编码前缀的功能--新需求-0403 lishuang
			if (!SuperTypeOversee.getTypeId().matches("[a-zA-Z]{2}")){
				result.setCode(500);
				result.setMessage("事项编码输入有误");
				return result;
			}
			SuperTypeOversee superTypeOversee = superTypeOverseeService.SuperTypeOverseeInsert(SuperTypeOversee);
			result.setData(superTypeOversee);
		}
		return result;
	}

	/**
	 * 督办类型修改
	 * 
	 * @param SuperTypeOversee
	 */
	@PostMapping("/edit")
	public RESTResultBean editSuperTypeOversee(SuperTypeOversee SuperTypeOversee) {
		RESTResultBean result = new RESTResultBean(200, "success");
		if(ToolUtil.isEmpty(SuperTypeOversee.getUnid())){
			result.setCode(500);
			result.setMessage("督办类型未选定");
			return result;
		}
		SuperTypeOversee superTypeOversee = superTypeOverseeService.getFindById(SuperTypeOversee);
		if (superTypeOversee == null) {
			result.setCode(500);
			result.setMessage("督办类型不存在");
			return result;
		}
		if (SuperTypeOversee.getTypeName() != null) {
			logger.info(SuperTypeOversee.toString());
			superTypeOverseeService.UpdateSuperTypeOverseeUpdate(SuperTypeOversee);
		}
		return result;
	}

	/**
	 * 查询
	 */
	@ResponseBody
	@GetMapping("/select")
	public RESTResultBean findSuperTypeOverseeList() {
		RESTResultBean result = new RESTResultBean(200, "success");
		List<SuperTypeOversee> superTypeOverseeFind = superTypeOverseeService.SuperTypeOverseeFind();
		result.setData(superTypeOverseeFind);
		return result;
	}

	/**
	 * 督办类型的新增
	 * 
	 * @param unid
	 * @param typeName
	 * @return
	 */
	@ApiOperation(value = "督办类型新增", notes = "新增,已立项后的事项新增督办类型操作")
	@PostMapping("/insertAfter")
	@ResponseBody
	public RESTResultBean SuperTypeOverseeNewInsert(String unid, String typeName) {
		RESTResultBean result = null;
		SuperServiceOverseeMapping overseeMapping = superTypeOverseeService.SuperTypeOverseeNewInsert(unid, typeName);
		if (overseeMapping != null) {
			result = new RESTResultBean(200, "成功");
		}
		result = new RESTResultBean(500, "失败");
		return result;
	}

	/**
	 * 新增任务来源
	 * 
	 * @param overseeMappingDto
	 * @return
	 */
	@ApiOperation(value = "新增任务来源")
	@PostMapping("/addTaskSource")
	@ResponseBody
	public RESTResultBean addTaskSource(@RequestBody OverseeMappingDto overseeMappingDto, BindingResult bindingResult,
			HttpServletRequest request) {
		RESTResultBean result = new RESTResultBean(200, "成功");
		verify(bindingResult, request, overseeMappingDto);
		// 查询用户信息
		String userId = SessionUtils.getUserId(request);
		OmUser omUser = omUserService.findByUserid(userId);
		if (omUser == null) {
			result.setCode(100);
			result.setMessage("未登录");
			return result;
		}
		//新增类型关联
		superTypeOverseeService.addTaskSource(overseeMappingDto);
		return result;
	}

}
