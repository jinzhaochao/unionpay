package com.unionpay.pager.controller;

import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.pager.domain.BusinessDict;
import com.unionpay.pager.service.BusinessDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 商户入网需求MCC管理
 * @author: lishuang
 * @date: 2019/12/04
 */
@RestController
@RequestMapping("/businessEnterNetDict")
@Api(value = "商户入网字典管理", tags = "商户入网字典管理", description = "商户入网字典管理")
public class BusinessDictController {
    @Autowired
    private BusinessDictService dictService;

    @ApiOperation(value = "价格类型下拉框",notes = "价格类型下拉框")
    @GetMapping("/priceSelectMap")
    public RESTResultBean priceSelectMap(){
        RESTResultBean result = new RESTResultBean(200,"操作成功");
        List<BusinessDict> dictList = dictService.getPriceType();
        result.setData(dictList);
        return result;
    }
}
