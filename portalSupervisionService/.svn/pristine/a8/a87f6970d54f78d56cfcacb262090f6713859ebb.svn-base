package com.unionpay.pager.controller;

import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.domain.BusinessRequireInfo;
import com.unionpay.pager.dto.BusinessRequireInfoVo;
import com.unionpay.pager.dto.RequireConditionDto;
import com.unionpay.pager.dto.RequireInfoDto;
import com.unionpay.pager.dto.ReturnRequireInfoDto;
import com.unionpay.pager.service.BusinessRequireInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 商户入网需求管理
 * @author: lishuang
 * @date: 2019/12/04
 */
@RestController
@RequestMapping("/businessEnterNetRequire")
@Api(value = "商户入网需求管理", tags = "商户入网需求管理", description = "商户入网需求管理")
public class BusinessRequireInfoController {

    @Autowired
    private BusinessRequireInfoService infoService;

    @PostMapping("/addAndUpdate")
    @ApiOperation(value = "新增或修改入网需求信息",notes = "新增或修改入网需求信息")
    public RESTResultBean addAndUpdate(@RequestBody @Validated RequireInfoDto infoDto){
        RESTResultBean result = new RESTResultBean(500,"操作失败");
        BusinessRequireInfo info = null;
        if (ToolUtil.isEmpty(infoDto.getId())){
            info = infoService.add(infoDto);
        }else {
            info = infoService.update(infoDto);
        }
        if (ToolUtil.isNotEmpty(info)){
            result.setData(info);
            result.setCode(200);
            result.setMessage("操作成功");
        }
        return result;
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除入网需求信息",notes = "删除入网需求信息")
    public RESTResultBean delete(@RequestParam List<Integer> ids){
        RESTResultBean result = new RESTResultBean(500,"操作失败");
        List<BusinessRequireInfo> infos = infoService.delete(ids);
        if (ToolUtil.isNotEmpty(infos)){
            result.setCode(200);
            result.setMessage("操作成功");
        }
        return result;
    }

    @GetMapping("/get")
    @ApiOperation(value = "查询入网需求信息详情",notes = "查询入网需求信息详情")
    public RESTResultBean get(@RequestParam Integer id){
        RESTResultBean result = new RESTResultBean(500,"操作失败");
        RequireInfoDto infoDto = infoService.get(id);
        if (ToolUtil.isNotEmpty(infoDto)){
            result.setData(infoDto);
            result.setCode(200);
            result.setMessage("操作成功");
        }
        return result;
    }

    @GetMapping("/getInfo")
    @ApiOperation(value = "查询入网需求信息（首页）",notes = "查询入网需求信息（首页根据类型查询）")
    public RESTResultBean getInfo(@RequestParam Integer businessType,@RequestParam Integer productType,@RequestParam Integer mcc){
        RESTResultBean result = new RESTResultBean(200,"操作成功");
        if (ToolUtil.isEmpty(businessType)){
            result.setCode(500);
            result.setMessage("请选择业务类型");
            return result;
        }
        if (ToolUtil.isEmpty(productType)){
            result.setCode(500);
            result.setMessage("请选择产品类型");
            return result;
        }
        if (ToolUtil.isEmpty(mcc)){
            result.setCode(500);
            result.setMessage("请选择可用MCC");
            return result;
        }
        List<BusinessRequireInfoVo> infoDtos = infoService.getInfos(businessType,productType,mcc);
        result.setData(infoDtos);
        return result;
    }

    @PostMapping("/getPage")
    @ApiOperation(value = "分页查询入网需求信息",notes = "分页查询入网需求信息")
    public RESTResultBean getPage(@RequestBody RequireConditionDto conditionDto){
        RESTResultBean result = new RESTResultBean(200,"操作成功");
        List<ReturnRequireInfoDto> infoDtos = infoService.getPage(conditionDto);
        result.setData(infoDtos);
        Pager pager = new Pager();
        pager.setTotal(infoService.getTotal(conditionDto));
        pager.setCurrentPage(conditionDto.getPage());
        pager.setSize(conditionDto.getSize());
        result.setPager(pager);
        return result;
    }

    @GetMapping("/selectMap")
    @ApiOperation(value = "下拉框（分页查询列表页面）",notes = "下拉框")
    public RESTResultBean selectMap(@RequestParam Integer id,@RequestParam Integer type,@RequestParam String mcc){
        RESTResultBean result = new RESTResultBean(200,"操作成功");
        List<Map> list = infoService.selectMap(id,type,mcc);
        result.setData(list);
        return result;
    }
}
