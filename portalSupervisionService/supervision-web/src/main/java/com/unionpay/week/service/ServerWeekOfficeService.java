package com.unionpay.week.service;

import com.unionpay.week.pojo.ServerWeekOffice;

import java.util.List;

public interface ServerWeekOfficeService {
    void add(ServerWeekOffice serverWeekOffice);

    Integer count(String startTime, String endTime);

    List<ServerWeekOffice> select(String time);

    void del(String time);
}
