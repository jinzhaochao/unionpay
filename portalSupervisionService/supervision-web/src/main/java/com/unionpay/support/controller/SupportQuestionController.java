package com.unionpay.support.controller;

import com.unionpay.support.pojo.SupportQuestion;
import com.unionpay.support.service.SupportQuestionService;
import com.unionpay.support.utils.ResultList;
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
@Api(value = "/supportQuestion",tags = "问题信息查询",description = "问题信息查询")
@RestController
@RequestMapping("/supportQuestion")
public class SupportQuestionController {

    @Autowired
    private SupportQuestionService supportQuestionService;

    @ApiOperation(value = "问题信息查询",notes = "问题信息查询")
    @ResponseBody
    @PostMapping("/getQuestion")
    public ResultList query(@RequestParam String typeId){
        ResultList resultList = new ResultList();
        try {

            List<SupportQuestion> list = supportQuestionService.select(typeId);
            resultList.setCode(200);
            resultList.setMessage("查询成功");
            resultList.setData(list);
        }catch (Exception e){
            resultList.setCode(500);
            resultList.setMessage("查询失败");
        }
        return  resultList;
    }
}
