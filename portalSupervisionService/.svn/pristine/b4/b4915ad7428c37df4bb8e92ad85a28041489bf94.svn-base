package com.unionpay.support.controller;

import com.unionpay.services.util.ResultList;
import com.unionpay.support.pojo.SysDictEntry;
import com.unionpay.support.service.SysDictEntryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/11/04 16:45
 * @Description:
 */
@Api(value = "/sysDictEntry",tags = "办公支持类型查询",description = "办公支持类型查询")
@RestController
@RequestMapping("/sysDictEntry")
public class SysDictEntryController {

    @Autowired
    private SysDictEntryService sysDictEntryService;

    @ApiOperation(value = "设备类型查询",notes = "设备类型查询")
    @ResponseBody
    @GetMapping("/getQuestionType")
    public ResultList query(){
        ResultList resultList = new ResultList();
        try {

            String str = "SUPPORT_QUESTION";
            List<SysDictEntry> list = sysDictEntryService.select(str);
            resultList.setCode(200);
            resultList.setMessage("查询成功");
            resultList.setData(list);
        }catch (Exception e){
            resultList.setCode(500);
            resultList.setMessage("查询失败");
            e.printStackTrace();
        }
        return  resultList;
    }

    @ApiOperation(value = "地点查询",notes = "地点查询")
    @ResponseBody
    @GetMapping("/getSupportPlace")
    public ResultList getSupportPlace(){
        ResultList resultList = new ResultList();
        try {

            String str = "SUPPORT_PLACE";
            List<SysDictEntry> list = sysDictEntryService.select(str);
            resultList.setCode(200);
            resultList.setMessage("查询成功");
            resultList.setData(list);
        }catch (Exception e){
            resultList.setCode(500);
            resultList.setMessage("查询失败");
            e.printStackTrace();
        }
        return  resultList;
    }
}
