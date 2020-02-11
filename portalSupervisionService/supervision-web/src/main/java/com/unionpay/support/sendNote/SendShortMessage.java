package com.unionpay.support.sendNote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.util.DateUtil;
import com.unionpay.sms.utils.WMResult;
import com.unionpay.supervision.dao.OMUserRepository;
import com.unionpay.support.dao.SupportSendMsgLogRepository;
import com.unionpay.support.pojo.SupportInfo;
import com.unionpay.support.pojo.SupportSendMsgLog;
import com.unionpay.support.pojo.SupportServerUser;
import com.unionpay.support.service.SupportInfoService;
import com.unionpay.support.service.SupportServerUserService;
import com.unionpay.support.utils.WS_Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class SendShortMessage {

    @Value("${application.scheduled.isRun}")
    private String isRun;
    @Autowired
    private SupportInfoService supportInfoService;
    @Autowired
    private SupportServerUserService supportServerUserService;

    @Autowired
    private SupportSendMsgLogRepository supportSendMsgLogRepository;
    @Autowired
    private OMUserRepository omUserRepository;

    @Scheduled(cron = "${support.time}") //定时提醒服务人
    public void scheduleSupportSend() {
        Boolean bool = Boolean.parseBoolean(isRun);
        if (!bool) {
            return;
        }
        WMResult wr = null;
        String supportType = null;
        Date dateTime = new Date();
        SupportSendMsgLog log = new SupportSendMsgLog();

        try {
            //查询所有当天服务时间的预约信息
            List<SupportInfo> list = supportInfoService.findAppointment(new SimpleDateFormat("yyyy-MM-dd").format(dateTime));
            //查询所有 今天的预约
            for (SupportInfo support : list) {
                SupportServerUser serverUser = supportServerUserService.select(support.getServerUserId());
                String serverTime = support.getServerTime().substring(0, support.getServerTime().indexOf("-"));
                String date = new SimpleDateFormat("HH:mm").format(dateTime);
                long time = (new SimpleDateFormat("HH:mm").parse(serverTime).getTime() - new SimpleDateFormat("HH:mm").parse(date).getTime()) / (1000);
                //当定时发送时间在当前时间之前一小时
                if (time == 3600) {
                    JSONObject object = new JSONObject();
                    String week = new SimpleDateFormat("E").format(new SimpleDateFormat("yyyy-MM-dd").parse(support.getServerDay()));
                    String strings = support.getServerDay().substring(5);

                    if (support.getSupportType() == 1) {
                        supportType = "电话支持";
                    } else if (support.getSupportType() == 2) {
                        supportType = "现场支持";
                    }
                    object.put("content", "服务提醒: 您在" + strings + " （周" + week.substring(2, 3) + "） " + support.getServerTime() + "有一个【" + support.getMyPlace() + "】【" + supportType + "】的预约待服务");
                    object.put("mobile", serverUser.getMobileno());
//                    object.put("userid",);
                    //调用短信发送方法
                    wr = WS_Send.sendMsg(object.toJSONString());
                    //发送短信日志记录
                    if (wr.isSuccess()) {
                        log.setSendStatus("已发送");
                    } else {
                        log.setSendStatus("发送失败");
                    }
                    log.setSupportInfoId(support.getId());
                    log.setData(JSON.toJSONString(object));
                    log.setSendUserid("定时提醒fasongren ");
                    log.setReceiveUserid(omUserRepository.findByEmpid(support.getServerUserId()).getUserid());
                    log.setCreateTime(DateUtil.getStrTime(new Date()));
                    log.setSendType("定时提醒");
                    supportSendMsgLogRepository.save(log);
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
