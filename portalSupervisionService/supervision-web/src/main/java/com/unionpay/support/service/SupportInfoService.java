package com.unionpay.support.service;


import com.unionpay.supervision.domain.OmUser;
import com.unionpay.support.model.*;
import com.unionpay.support.pojo.SupportInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */
@Component
public interface SupportInfoService {
    SupportInfo selectById(Integer id);

    void save(SupportInfo supportInfo);

    List<SupportPage> selectSupportInfo(Integer page, Integer size, String appointmentName, Integer serverUserId, String serverUserName, String timeStart, String timeEnd, Integer status,String userId,Integer tabPage);

    Integer selectCount(String appointmentName, Integer serverUserId, String serverUserName, String timeStart, String timeEnd, Integer status, String userId,Integer tabPage);

    String selectAllCount();


    ServerDateTime selectByMyplaceAndDatetime(String myPlace,String exceptedTime, String dateTime);


    SupportPage selectAll(Integer id);

    List<ExcelSupportInfo> selectInfo(String timeStart, String timeEnd, Integer status, Integer serverUserId, String serverUserName, String ids);

    void add(SupportInfo supportInfo, OmUser omUser);

    void update(Integer id);

    void addAppointment(AppointmentDto appointmentDto, OmUser omUser);

    SupportInfo updateStatus(Integer id);

    SupportInfo updateEvaluate(SupportInfo supportInfo);

    SupportInfo overAppointment(SupportInfo supportInfo, OmUser omUser);

    List<SupportInfo> findAppointment(String dateTime);

}
