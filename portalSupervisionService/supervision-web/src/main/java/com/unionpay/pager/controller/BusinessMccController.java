package com.unionpay.pager.controller;

import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.dto.BusinessMccDto;
import com.unionpay.pager.service.BusinessMccService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 商户入网需求MCC管理
 * @author: lishuang
 * @date: 2019/12/04
 */
@RestController
@RequestMapping("/businessEnterNetMCC")
@Api(value = "商户入网MCC管理", tags = "商户入网MCC管理", description = "商户入网MCC管理")
public class BusinessMccController {
    @Autowired
    private BusinessMccService mccService;

    @GetMapping("/getMcc")
    @ApiOperation(value = "获取MCC",notes = "获取MCC(查询入网需求详情时，调用)")
    public RESTResultBean getMcc(){
        RESTResultBean result = new RESTResultBean(500,"操作失败");
        List<BusinessMccDto> mccDtos = mccService.getAll();
        if (ToolUtil.isNotEmpty(mccDtos)){
            result.setCode(200);
            result.setMessage("操作成功");
            result.setData(mccDtos);
        }
        return result;
    }
}
