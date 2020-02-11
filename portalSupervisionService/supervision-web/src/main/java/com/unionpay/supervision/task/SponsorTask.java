package com.unionpay.supervision.task;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.bussniss.LiuchengOperator;
import com.unionpay.supervision.dao.SuperLcSponsorRepository;
import com.unionpay.supervision.domain.SuperLcSponsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class SponsorTask {
    @Autowired
    private LiuchengOperator liuchengOperator;

    @Autowired
    private SuperLcSponsorRepository superLcSponsorRepository;

    @Value("${application.scheduled.isRun}")
    private String isRun;



    @Scheduled(cron = "${super.time}")//每晚零点进行刷新数据
    public void saveSponsorLcSponsor(){
        Boolean bool = Boolean.parseBoolean(isRun);
        if(!bool){
            return;
        }
        Map<String, String> orgMange = liuchengOperator.getOrgId("portal");
        Set<String> keys = orgMange.keySet();

        if (ToolUtil.isNotEmpty(keys)){
            superLcSponsorRepository.deleteAll();
            for (String str : keys){
                if (ToolUtil.isNotEmpty(str)){
                    SuperLcSponsor superLcSponsor = new SuperLcSponsor();
                    superLcSponsor.setUserId(str);
                    superLcSponsor.setEmpName(orgMange.get(str));
                    superLcSponsorRepository.save(superLcSponsor);
                }
            }
        }
    }
}
