package com.unionpay.services.controller;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.ServerDict;
import com.unionpay.services.service.ServerdictServiceImpl;
import com.unionpay.services.util.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author xiaopengcheng
 * 字典表
 */
@RestController
@RequestMapping("/serverdict")
@Api(value = "/serverdict", tags = "字典表接口", description = "字典表接口")
public class ServerdictController {
	@Autowired
	private ServerdictServiceImpl serverdict_modelService;

	@PostMapping("/selcetAll")
	@ApiOperation(value = "展示下拉框", notes = "展示下拉框")
	public Result selcetAll(){
		try{
			List<ServerDict> serverDictList = serverdict_modelService.selectAll();
			if (ToolUtil.isNotEmpty(serverDictList)){
				return Result.build(200, "查询成功", serverDictList);
			} else {
				return Result.build(400, "查询失败", "");
			}
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}



	@RequestMapping("/add")
	public Result add(ServerDict serverdict_model){
		try{
			return Result.success(serverdict_modelService.add(serverdict_model));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}
	@RequestMapping("/delete")
	public Result delete(Integer id){
		try{
			serverdict_modelService.delete(id);
			return Result.success();
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}
	@RequestMapping("/update")
	public Result update(ServerDict serverdict_model){
		try{
			return Result.success(serverdict_modelService.update(serverdict_model));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}
	@RequestMapping("/get")
	public Result get(Integer id){
		try{
			ServerDict serverdict_model = serverdict_modelService.get(id);
			if(serverdict_model==null){throw new RuntimeException();}
			return Result.success(serverdict_model);
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}
	@RequestMapping("/getPage")
	public Result getPage(Integer page, Integer rows){
		try{
			Page<ServerDict> pageBean = serverdict_modelService.getPage(page,rows);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("total",pageBean.getTotalElements());
			result.put("rows",pageBean.getContent());
			return Result.success(result);
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(e.toString());
		}
	}
}