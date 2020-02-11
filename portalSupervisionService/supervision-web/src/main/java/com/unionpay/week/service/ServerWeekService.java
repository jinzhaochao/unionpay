package com.unionpay.week.service;

import com.unionpay.week.pojo.ServerWeek;
import com.unionpay.week.pojo.ServerWeekOffice;

import java.util.List;

public interface ServerWeekService {
    List<ServerWeek> selectH(String day);

    List<ServerWeek> selectO(String day);

    void working(String time);

    List<ServerWeek> findByYear(String time);
}
