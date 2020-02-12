package com.unionpay.pager.controller;


import com.alibaba.fastjson.JSONArray;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.pager.dto.WcmChannel;
import com.unionpay.pager.service.ColumnTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: jinzhao
 * @create: 2019-08-07 14:52
 **/

@RestController
@RequestMapping("/columnTree")
@Api(value = "/columnTree",tags = "模板配置管理-页签管理-栏目树",description = "模板配置管理-页签管理-栏目树")
public class ColumnTreeController {


    @Autowired
    private ColumnTreeService columnTreeService;
    /**
     *栏目树信息
     * @author jinzhao
     * @date 2019-08-05
     */
    @ApiOperation(value = "查询栏目",notes = "查询栏目")
    @GetMapping(value = "/selectColumnTree")
    @ResponseBody
    public RESTResultBean selectColumnTree (Integer id) {
        if (null == id || id < 0 ){
            id = 0;
        }
        RESTResultBean result = new RESTResultBean(200, "操作成功");
        List<WcmChannel> columnInfos = columnTreeService.selectColumnTree(id);
        result.setData(columnInfos);

        return result;
    }
}
