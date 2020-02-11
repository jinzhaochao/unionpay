package com.unionpay.services.controller;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.HandlingMaterialRequest;
import com.unionpay.services.model.ServerCommonProblem;
import com.unionpay.services.service.ServercommonproblemServiceImpl;
import com.unionpay.services.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author xiaopengcheng
 * 常见问题
 */
@RestController
@RequestMapping("/servercommonproblem")
@Api(value = "/servercommonproblem", tags = "常见问题接口", description = "常见问题接口")
public class ServercommonproblemController {
    @Autowired
    private ServercommonproblemServiceImpl servercommonproblem_modelService;


    /**
     * 添加一个问题
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加一个问题", notes = "添加一个问题")
    public Result add(@Valid @RequestBody ServerCommonProblem servercommonproblem_model) {
        try {
            servercommonproblem_model.setCreatetime(new Date());
            ServerCommonProblem serverCommonProblem = servercommonproblem_modelService.add(servercommonproblem_model);
            if (ToolUtil.isNotEmpty(serverCommonProblem)) {
                return Result.build(200, "添加成功", serverCommonProblem);
            } else {
                return Result.build(400, "添加失败", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }


    /**
     * 常见问题---删除某一个问题
     */
    @PostMapping("/delete")
    @ApiOperation(value = "常见问题---删除某一个问题", notes = "常见问题---删除某一个问题")
    public Result delete(@Valid @RequestBody HandlingMaterialRequest handlingMaterialRequest) {
        try {
            Integer id = handlingMaterialRequest.getId();
            ServerCommonProblem serverCommonProblem = servercommonproblem_modelService.get(id);
            if (ToolUtil.isNotEmpty(serverCommonProblem)) {
                servercommonproblem_modelService.delete(id);
                return Result.success();
            } else {
                return Result.build(400, "该问题不存在", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }


    /**
     * 常见问题---修改某一个问题的情况
     */
    @PostMapping("/update")
    @ApiOperation(value = "常见问题---修改某一个问题的情况", notes = "常见问题---修改某一个问题的情况")
    public Result update(@Valid @RequestBody ServerCommonProblem servercommonproblem_model) {
        try {
            ServerCommonProblem serverCommonProblem =
                    servercommonproblem_modelService.getOneById(servercommonproblem_model.getId(), servercommonproblem_model.getServerId());
            if (ToolUtil.isNotEmpty(serverCommonProblem)) {
                return Result.success(servercommonproblem_modelService.update(servercommonproblem_model));
            } else {
                return Result.build(400, "该问题不存在", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 修改该问题禁用与启用的状态
     */
    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改该问题禁用与启用的状态", notes = "修改该问题禁用与启用的状态")
    public Result updateStatus(@Valid @RequestBody HandlingMaterialRequest handlingMaterialRequest) {
        try {
            Integer type = handlingMaterialRequest.getType();
            Integer id = handlingMaterialRequest.getId();
            Integer serverId = handlingMaterialRequest.getServerId();
            ServerCommonProblem serverCommonProblem =
                    servercommonproblem_modelService.getOneById(id, serverId);
            if (ToolUtil.isNotEmpty(serverCommonProblem)) {
                if (type == 0) {//启动改为禁用
                    serverCommonProblem.setStatus(0);
                }
                if (type == 1) {//禁用改为启动
                    serverCommonProblem.setStatus(1);
                }
                return Result.success(servercommonproblem_modelService.update(serverCommonProblem));
            } else {
                return Result.build(400, "该问题不存在", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 查询某一个问题的详情
     */
    @GetMapping("/getOne")
    @ApiOperation(value = "查询某一个问题的详情", notes = "查询某一个问题的详情")
    public Result get(@RequestParam(value="id") Integer id,@RequestParam(value="serverId") Integer serverId) {
        try {
            ServerCommonProblem servercommonproblem_model = servercommonproblem_modelService.getOneById(id, serverId);
            if (servercommonproblem_model == null) {
                throw new RuntimeException();
            }
            return Result.success(servercommonproblem_model);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 查询某一项目的所有问题
     *
     * @return
     */
    @PostMapping("/getSelectAll")
    @ApiOperation(value = "查询某一项目的所有问题", notes = "查询某一项目的所有问题")
    public Result getSelectAll(@Valid @RequestBody HandlingMaterialRequest handlingMaterialRequest) {
        try {
            Integer serverId = handlingMaterialRequest.getServerId();
            List<ServerCommonProblem> serverCommonProblems = servercommonproblem_modelService.getSelectAll(serverId);
            if (ToolUtil.isNotEmpty(serverCommonProblems)) {
                return Result.build(200, "查询成功", serverCommonProblems);
            } else if (ToolUtil.isEmpty(serverCommonProblems)) {
                return Result.build(200, "查询成功无数据", "");
            } else {
                return Result.build(400, "查询失败", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

}