package com.unionpay.sms.task;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.util.DateUtil;
import com.unionpay.sms.dao.SmsMatterRepository;
import com.unionpay.sms.dao.SmsReceiverRepository;
import com.unionpay.sms.domain.smsMatter;
import com.unionpay.sms.domain.smsReceiver;
import com.unionpay.sms.model.SmsInfo;
import com.unionpay.sms.service.SmsMatterService;
import com.unionpay.sms.service.SmsReceiverService;
import com.unionpay.sms.utils.WMResult;
import com.unionpay.sms.utils.WS_ToSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
@Component
public class SmsTask {

    @Autowired
    private SmsReceiverService smsReceiverService;
    @Autowired
    private SmsMatterService smsMatterService;
    @Autowired
    private SmsReceiverRepository smsReceiverRepository;
    @Autowired
    private SmsMatterRepository smsMatterRepository;
    @PersistenceContext
    private EntityManager em;
    @Value("${application.scheduled.isRun}")
    private String isRun;

    @Scheduled(cron = "${sms.time}")
    public void scheduleSmsSend(){
        Boolean bool = Boolean.parseBoolean(isRun);
        if(!bool){
            return;
        }
        WMResult wr = null;
        //查询所有  审批通过 状态为待发送 的短信
        List<SmsInfo> list = smsMatterService.findAllSmsMatter();
        for (SmsInfo sms:list){
            //当为手动发送时
            if (sms.getMode().equals("2")){
                continue;
            }
            JSONObject object = new JSONObject();
            //当为审批后自动发送
            if (sms.getMode().equals("1")){
                List<smsReceiver> smsReceivers = smsReceiverService.findAllSmsReceiver(sms.getUnid());
                for (smsReceiver receiver : smsReceivers){
                    object.put("content",sms.getContent());
                    object.put("mobile",receiver.getReceiverTel());
                    object.put("userid",sms.getUserid());//创建该短信的人的userid
                    //调用短信发送方法
                    wr = WS_ToSend.sendMsg(object.toJSONString());
                    //修改接收人接收状态
                    if (wr.isSuccess()){
                        receiver.setStatus("已发送");
                    }else {
                        receiver.setStatus("发送失败");
                    }
                    receiver.setSendTime(DateUtil.getStrTime(new Date()));
                    smsReceiverRepository.save(receiver);
                }
            }
            //当为定时发送状态，且  定时发送时间跟当前时间相同 或  定时发送时间在当前时间之前
            if (sms.getMode().equals("3")){
                if ((new Date()).before(DateUtil.parseDates(sms.getTimingTime()))){
                    continue;
                }
                List<smsReceiver> smsReceivers = smsReceiverService.findAllSmsReceiver(sms.getUnid());
                for (smsReceiver receiver : smsReceivers){
                    object.put("content",sms.getContent());
                    object.put("mobile",receiver.getReceiverTel());
                    object.put("userid",sms.getUserid());//创建该短信的人的userid
                    //调用短信发送方法
                    wr = WS_ToSend.sendMsg(object.toJSONString());
                    //修改接收人接收状态
                    if (wr.isSuccess()){
                        receiver.setStatus("已发送");
                    }else {
                        receiver.setStatus("发送失败");
                    }
                    receiver.setSendTime(DateUtil.getStrTime(new Date()));
                    smsReceiverRepository.save(receiver);
                }
            }
            //修改短信状态
            smsMatter smsMatter = smsMatterRepository.findByUnid(sms.getUnid());
            smsMatter.setStatus("已发送");
            smsMatter.setEndTime(DateUtil.getStrTime(new Date()));
            smsMatterRepository.save(smsMatter);
        }
    }
}
