package com.unionpay.sms.service.impl;

import com.unionpay.sms.dao.SmsLogRepository;
import com.unionpay.sms.model.SmsLog;
import com.unionpay.sms.service.SmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SmsLogServiceImpl implements SmsLogService {
    @Autowired
    private SmsLogRepository smsLogRepository;
    @Override
    public void add(SmsLog log) {
        smsLogRepository.save(log);
    }
}
