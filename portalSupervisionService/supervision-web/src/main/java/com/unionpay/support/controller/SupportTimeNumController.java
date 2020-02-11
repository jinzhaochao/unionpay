package com.unionpay.support.controller;

import com.unionpay.support.model.ServerDateTime;
import com.unionpay.support.model.TimeNum;
import com.unionpay.support.pojo.SupportTimeNum;
import com.unionpay.support.service.SupportInfoService;
import com.unionpay.support.service.SupportTimeNumService;
import com.unionpay.support.utils.ResultList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */

@RestController
@RequestMapping("/supportTimeNum")
@Api(value = "/supportTimeNum", tags = "办公支持服务之约定预约时间段次数", description = "办公支持服务之约定预约时间段次数")
public class SupportTimeNumController {

    @Autowired
    private SupportInfoService supportInfoService;
    @Autowired
    private SupportTimeNumService supportTimeNumService;


    @ApiOperation(value = "查询发起预约时不同地点下的时间段", notes = "查询发起预约时不同地点下的时间段")
    @PostMapping("/getTime")
    @ResponseBody
    public ResultList getTime(@RequestParam String myPlace, @RequestParam String dateTime) {
        ResultList resultList = new ResultList();
//        List<String> arrayList = new ArrayList<>();
//        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        try {
//
//            //根据地名查询该地名下所约定的服务时间段和可服务次数
//            List<SupportTimeNum> list = supportTimeNumService.selectByPlaceNameAndExceptedTime(myPlace);
//            for (int i = 0; i < list.size(); i++) {
//                String exceptedTime = list.get(i).getExceptedTime();
//                String serverTime = exceptedTime.substring(0, exceptedTime.indexOf("-"));
//                if ((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime + " " +serverTime+":00").getTime() > new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime())) {
//
//                    Integer num = list.get(i).getExceptedNum();
//                    //根据地名，日期和时间段查询该条件下已服务次数
//                    ServerDateTime serverDateTime = supportInfoService.selectByMyplaceAndDatetime(myPlace, exceptedTime, dateTime);
//                    Integer count = serverDateTime.getCount();
//                    //比较次数，已服务次数小于约定次数则添加到list返回给前端
//                    if (count < num) {
//                        arrayList.add(exceptedTime);
//                    }
//
//                }
//
//            }
////        arrayList.add(myPlace);
////        arrayList.add(dateTime);
//                resultList.setCode(200);
//                resultList.setMessage("查询成功");
//                resultList.setData(arrayList);
//        } catch (Exception e) {
//
//        }


        //需求更改
        List<TimeNum> arrayList = new ArrayList<>();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            //根据地名查询该地名下所约定的服务时间段和可服务次数
            List<SupportTimeNum> list = supportTimeNumService.selectByPlaceNameAndExceptedTime(myPlace);
            for (SupportTimeNum supportTimeNum : list) {
                TimeNum timeNum = new TimeNum();
                timeNum.setTimeNum(supportTimeNum.getExceptedTime());
                String serverTime = supportTimeNum.getExceptedTime().substring(0, supportTimeNum.getExceptedTime().indexOf("-"));

                if ((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime + " " +serverTime+":00").getTime() > new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime())) {
                    Integer num = supportTimeNum.getExceptedNum();
                    //根据地名，日期和时间段查询该条件下已服务次数
                    ServerDateTime serverDateTime = supportInfoService.selectByMyplaceAndDatetime(myPlace, supportTimeNum.getExceptedTime(), dateTime);
                    Integer count = serverDateTime.getCount();
                    //比较次数，已服务次数小于约定次数就显示2可用
                    if (count < num) {
                        timeNum.setStatus(2);
                    }else {
                        timeNum.setStatus(1);
                    }
                }else {
                    timeNum.setStatus(0);
                }
            arrayList.add(timeNum);
            }
            resultList.setCode(200);
                resultList.setMessage("查询成功");
                resultList.setData(arrayList);
        }catch (Exception e){
             e.printStackTrace();
        }
        return resultList;
    }
}
