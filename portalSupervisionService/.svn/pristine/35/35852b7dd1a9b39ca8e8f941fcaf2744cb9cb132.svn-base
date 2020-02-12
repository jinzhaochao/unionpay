package com.unionpay.tree.controller;

import com.unionpay.services.util.Result;
import com.unionpay.tree.service.TreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 组织树
 * @author lishuang
 * @date 2019/10/17
 */
@Api(tags = "组织树接口",value = "加载组织树接口",description = "用于加载组织树(根据传入的id，查询对应的子部门或员工信息)")
@RestController
@RequestMapping("/organizationTree")
public class OrganizationTree {
    @Autowired
    private TreeService treeService;

    @GetMapping("/depts")
    @ApiOperation(value = "加载组织树",notes = "只加载部门信息")
    @ResponseBody
    public Result loadDept(@RequestParam(name = "id",required = false) String id){
        return Result.success(treeService.getDept(id));
    }

    @GetMapping("/users")
    @ApiOperation(value = "加载组织树",notes = "加载部门及部门下员工信息")
    @ResponseBody
    public Result loadDeptAndUser(@RequestParam(name = "id",required = false) String id){
        return Result.success(treeService.getDeptAndUser(id));
    }
}
