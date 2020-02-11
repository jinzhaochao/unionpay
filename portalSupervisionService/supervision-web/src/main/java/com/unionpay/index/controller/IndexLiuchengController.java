package com.unionpay.index.controller;

import com.unionpay.index.service.IndexLiuchengService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/indexLiucheng")
@Api(value = "首页模块流程平台对接",tags = "首页模块流程平台对接", description = "首页模块流程平台对接")
public class IndexLiuchengController {

    @Autowired
    private IndexLiuchengService indexLiuchengService;

    @ApiOperation(value = "未批签文件",notes = "未批签文件")
    @ResponseBody
    @PostMapping("/LiuChengReq")
    public Map LiuChengReq(@RequestBody Map map){
        return indexLiuchengService.LiuChengReq(map);
    }

    @ApiOperation(value = "获取部门ID",notes = "获取部门ID")
    @ResponseBody
    @PostMapping("/getOrgId")
    public Integer getOrgId(){
        return indexLiuchengService.getOrgId();
    }

}
