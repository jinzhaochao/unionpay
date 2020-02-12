package com.unionpay.voice.service.impl;

import com.unionpay.voice.dao.VoiceLogRepository;
import com.unionpay.voice.domain.VoiceLog;
import com.unionpay.voice.service.VoiceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoiceLogServiceImpl implements VoiceLogService {
    @Autowired
    private VoiceLogRepository voiceLogRepository;
    public void add(VoiceLog log){
        voiceLogRepository.save(log);
    }
}
