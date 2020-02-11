package com.unionpay.services.quartz;

import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.entity.ServerApplyInfo;
import com.unionpay.services.service.MyServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class Task {
    @Autowired
    private MyServerService applyService;

    @Value("${application.scheduled.isRun}")
    private String isRun;

    @Scheduled(cron = "${service.time}")
    public void scheduleGetCompleteServer(){
        Boolean bool = Boolean.parseBoolean(isRun);
        if(!bool){
            return;
        }
        // 定时获取已完成服务信息（每十分钟执行一次）
        Calendar calendar = Calendar.getInstance();
        String endTime = DateUtil.getStrTime(calendar.getTime());
        calendar.add(Calendar.MINUTE,-15);
        String startTime = DateUtil.getTime(calendar.getTime());
        List<ServerApplyInfo> infoList = applyService.getCompleteServer(startTime,endTime);
        // 已完成服务信息获取成功后，进行服务统计操作
        /*if (ToolUtil.isNotEmpty(infoList)){
            applyService.getServerCount();
        }*/
    }
}
