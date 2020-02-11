package com.unionpay.week.controller;

import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.util.ResultList;
import com.unionpay.week.pojo.ServerWeek;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: jinzhao
 * @Date: 2019/12/25 18:21
 * @Description:
 */
@RestController
@RequestMapping("/serverWeek")
@Api(value = "/serverWeek", tags = "计算工作日", description = "计算工作日")
public class ServerWeekController {

    @Autowired
    private ServerWeekService serverWeekService;
    @Autowired
    private ServerWeekOfficeService serverWeekOfficeService;

    @ApiOperation(value = "计算工作日并保存", notes = "计算工作日并保存")
    @PostMapping("/workingDays")
    @ResponseBody
    public ResultList workingDays(String time) {
        ResultList resultList = new ResultList();
        try {
            List<ServerWeek> serverWeekList = serverWeekService.findByYear(time);
            if (ToolUtil.isEmpty(serverWeekList)){
                resultList.setCode(500);
                resultList.setMessage("无该年份节假日数据");
                return resultList;
            }
            serverWeekService.working(time);
//        ServerWeekOffice serverWeekOffice = new ServerWeekOffice();
//        int days = 0;
//        int year = Integer.parseInt(time);
//        try {
//            for (int month = 1; month < 13; month++) {
//
//                switch (month) {
//                    case 1:
//                    case 3:
//                    case 5:
//                    case 7:
//                    case 8:
//                    case 10:
//                    case 12:
//                        days = 31;
//                        break;
//                    case 4:
//                    case 6:
//                    case 9:
//                    case 11:
//                        days = 30;
//                        break;
//                    case 2:
//                        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
//                            days = 29;
//                        else
//                            days = 28;
//                        break;
//                }
//                for (int day = 1; day <= days; day++) {
//
//                    Date bdate = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day);
//
//                    String date = DateUtil.getDay(bdate);
//                    List<ServerWeek> serverWeekListH = serverWeekService.selectH(date);
//                    //如果为空，则不是节假日
//                    if (ToolUtil.isEmpty(serverWeekListH)) {
//                        List<ServerWeek> serverWeekListO = serverWeekService.selectO(date);
//                        //如果不为空，则是加班日
//                        if (ToolUtil.isNotEmpty(serverWeekListO)) {
//                            serverWeekOffice.setId(UUID.randomUUID().toString());
//                            serverWeekOffice.setOfficeTime(date);
//                            serverWeekOffice.setCreateTime(DateUtil.getStrTime(new Date()));
//                            serverWeekOfficeService.add(serverWeekOffice);
//                        } else {  //不是节假日和加班日，则判断是否是周末
//                    Calendar cal = Calendar.getInstance();
//                            cal.setTime(bdate);
//                            boolean isWeekend = true; //默认是周末
//                            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//                                isWeekend = true;
//                            } else {
//                                isWeekend = false;
//                            }
//                            //不是周末，则是工作日
//                            if (!isWeekend) {
//                                serverWeekOffice.setId(UUID.randomUUID().toString());
//                                serverWeekOffice.setOfficeTime(date);
//                                serverWeekOffice.setCreateTime(DateUtil.getStrTime(new Date()));
//                                serverWeekOfficeService.add(serverWeekOffice);
//                            }
//                        }
//                    }
//                }
//
//            }
            resultList.setCode(200);
            resultList.setMessage("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultList.setCode(500);
            resultList.setMessage("保存失败");
        }
        return resultList;
    }
}
