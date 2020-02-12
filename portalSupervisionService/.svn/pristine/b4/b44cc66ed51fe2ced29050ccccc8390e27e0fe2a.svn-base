package com.unionpay.services.controller;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.entity.ServerApplyInfo;
import com.unionpay.services.model.CheckConditionModel;
import com.unionpay.services.model.ServerApplyModel;
import com.unionpay.services.repository.ServerApplyFlowRepository;
import com.unionpay.services.service.MyServerService;
import com.unionpay.services.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

@RestController
@Api(tags = "我的服务接口",value = "我的服务接口",description = "我的服务列表（已完成、进行中）、评价、服务详情")
@RequestMapping("/myServer")
public class MyServerController {

    @Autowired
    private MyServerService applyService;
    @Autowired
    private ServerApplyFlowRepository flowRepository;
    @Autowired
    private MyServerService myServerService;

    @ApiOperation(value = "进行中办件分页查询用get请求")
    @ResponseBody
    @GetMapping("/underWayServer")
    public Result getAllUseGet(@RequestParam(value = "classify") String classify,
                               @RequestParam(value = "launchTimeEnd",required = false)String launchTimeEnd,
                               @RequestParam(value = "launchTimeStart",required = false)String launchTimeStart,
                               @RequestParam(value = "serviceInfoName",required = false)String serviceInfoName,
                               @RequestParam(value = "size",defaultValue = "10")String size,
                               @RequestParam(value = "currentPage",defaultValue = "1")String currentPage,HttpServletRequest req){
        String userid = SessionUtils.getUserId(req);
        return applyService.getUnderWayServerUseGet(classify,launchTimeEnd,launchTimeStart,userid,size,currentPage,serviceInfoName);
    }

    @ApiOperation(value = "已完成办件分页查询")
    @ResponseBody
    @PostMapping("/completeServer")
    public Result getCompleteServer(@RequestBody CheckConditionModel conditionModel, HttpServletRequest request){
        String userid = SessionUtils.getUserId(request);
        List<ServerApplyModel> modelList = applyService.getCompleteServer(conditionModel,userid);
        Pager pager = new Pager();
        pager.setSize(conditionModel.getSize());
        pager.setCurrentPage(conditionModel.getCurrentPage());
        pager.setTotal(applyService.count(conditionModel,userid));
        return Result.success(modelList,pager);
    }

    @ApiOperation(value = "查看详情页")
    @ResponseBody
    @GetMapping("/get")
    public ServerApplyModel get(@RequestParam BigInteger id){
        return applyService.get(id);
    }

    @ApiOperation(value = "服务统计接口")
    @ResponseBody
    @GetMapping("/getCount")
    public Result getCount(@RequestParam Integer type){
        return Result.success(applyService.getDate(type));
    }

    @ApiOperation(value = "部门满意度排行接口")
    @ResponseBody
    @GetMapping("/deptSort")
    public Result deptSort(@RequestParam Integer type){
        return Result.success(applyService.sort(type));
    }

    @ApiOperation(value = "评价接口",notes = "已完成服务评价操作")
    @ResponseBody
    @PostMapping("/giveScore")
    public Result giveScore(@RequestBody String data){
        if (ToolUtil.isNotEmpty(data)){
            JSONObject object = JSONObject.parseObject(data);
            BigInteger id = object.getBigInteger("id");
            Integer score = object.getInteger("score");
            if (ToolUtil.isNotEmpty(id)&&ToolUtil.isNotEmpty(score)){
                boolean bool = applyService.giveScore(id,score);
                if (bool){
                    return Result.success();
                }else {
                    return Result.failure();
                }
            }
        }
        return Result.failure();
    }

    @GetMapping("/test")
    public List<ServerApplyInfo> test(@RequestParam("endTimeStart")String endTimeStart,
                                      @RequestParam("endTimeEnd") String endTimeEnd){
        List<ServerApplyInfo> a = myServerService.getCompleteServer(endTimeStart,endTimeEnd);
        return a;
    }


}
