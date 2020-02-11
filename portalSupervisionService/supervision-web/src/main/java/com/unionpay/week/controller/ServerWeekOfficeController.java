package com.unionpay.week.controller;

import com.unionpay.common.easyexcel.DataUtil;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.util.ResultList;
import com.unionpay.week.pojo.ServerWeekOffice;
import com.unionpay.week.service.ServerWeekOfficeService;
import com.unionpay.week.service.ServerWeekService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.beans.SimpleBeanInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/12/25 20:31
 * @Description:
 */
@RestController
@RequestMapping("/serverWeekOffice")
@Api(value = "/serverWeekOffice", tags = "查询工作日", description = "查询工作日")
public class ServerWeekOfficeController {

    @Autowired
    private ServerWeekOfficeService serverWeekOfficeService;
    @Autowired
    private ServerWeekService serverWeekService;

    @ApiOperation(value = "查询工作日", notes = "查询工作日")
    @PostMapping("/selectOffice")
    @ResponseBody
    public ResultList selectOffice(String startTime,String endTime) {
        ResultList resultList = new ResultList();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startTime = DateUtil.getDay(sdf.parse(startTime));
            endTime = DateUtil.getDay(sdf.parse(endTime));

            //判断查询时间是否存在数据表中
            String s1 = startTime.substring(0, 4);
            String s2 = endTime.substring(0, 4);
            if (s1.equals(s2)) {
                List<ServerWeekOffice> list = serverWeekOfficeService.select(s1);
                if (ToolUtil.isEmpty(list)){
                    resultList.setCode(500);
                    resultList.setMessage("请查询正确时间段");
                    return resultList;
                }
            }else {
                List<ServerWeekOffice> list1 = serverWeekOfficeService.select(s1);
                List<ServerWeekOffice> list2 = serverWeekOfficeService.select(s2);
                if (ToolUtil.isEmpty(list1) || ToolUtil.isEmpty(list2)){
                    resultList.setCode(500);
                    resultList.setMessage("请查询正确时间段");
                    return resultList;
                }
            }

            Integer total = serverWeekOfficeService.count(startTime,endTime);
            Pager pager = new Pager();
            pager.setTotal(total);
            resultList.setCode(200);
            resultList.setMessage("查询成功");
            resultList.setPager(pager);
        } catch (Exception e) {
            e.printStackTrace();
            resultList.setCode(500);
            resultList.setMessage("查询失败");
        }
        return resultList;
    }
}
