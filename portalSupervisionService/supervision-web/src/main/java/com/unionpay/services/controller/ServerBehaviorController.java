package com.unionpay.services.controller;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.entity.ServerBehavior;
import com.unionpay.services.model.BehaviorModel;
import com.unionpay.services.service.ServerBehaviorService;
import com.unionpay.services.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "行为记录接口",value = "行为记录接口",description = "行为记录操作")
@RequestMapping("/serverBehavior")
public class ServerBehaviorController {
    @Autowired
    private ServerBehaviorService behaviorService;

    @ApiOperation(value = "行为记录下拉框",notes = "行为记录下拉框")
    @ResponseBody
    @PostMapping("/selectMap")
    public Result selectMap(){
        JSONObject object = new JSONObject();
        List list = behaviorService.listSelect();
        object.put("selectMap",list);
        return Result.success(object);
    }

    @ApiOperation(value = "查询列表",notes = "条件查询")
    @ResponseBody
    @GetMapping("/listServerBehavior")
    public Result getAll(@RequestParam(name = "page",defaultValue = "1")Integer page,
                         @RequestParam(name = "size",defaultValue = "10")Integer size,
                         @RequestParam(name = "value",required = false)Integer value){
        List<BehaviorModel> list = behaviorService.getAll(value,page,size);
        Pager pager = new Pager();
        pager.setSize(size);
        pager.setCurrentPage(page);
        pager.setTotal(behaviorService.count(value,page,size));
        return Result.success(list,pager);
    }

    @ApiOperation(value = "新增行为记录",notes = "记录用户进入服务中心入口路径")
    @ResponseBody
    @PostMapping("/addServerBehabior")
    public Result add(@RequestBody String type, HttpServletRequest request){
        String userid = SessionUtils.getUserId(request);
        if (ToolUtil.isEmpty(userid)){
            return Result.failure("请登录");
        }
        JSONObject object = JSONObject.parseObject(type);
        Integer id = object.getInteger("type");
        ServerBehavior behavior = behaviorService.add(id,userid);
        if (ToolUtil.isEmpty(behavior)){
            return Result.failure("新增失败");
        }
        return Result.success();
    }
}
