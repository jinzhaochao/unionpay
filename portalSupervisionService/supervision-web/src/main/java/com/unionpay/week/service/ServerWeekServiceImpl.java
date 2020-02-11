package com.unionpay.week.service;

import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.week.dao.ServerWeekRepository;
import com.unionpay.week.pojo.ServerWeek;
import com.unionpay.week.pojo.ServerWeekOffice;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ServerWeekServiceImpl implements ServerWeekService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ServerWeekService serverWeekService;
    @Autowired
    private ServerWeekOfficeService serverWeekOfficeService;
    @Autowired
    private ServerWeekRepository serverWeekRepository;

    @Override
    public List<ServerWeek> selectH(String day) {
        String sql = "select sw.UNID as unid,sw.TYPE as type,sw.STARTDATE as startDate,sw.ENDDATE as endDate,sw.NUM as num,sw.MEMO as memo,sw.STATE as state,sw.CREATETIME as createTime from server_week sw where sw.STATE = 'Y' and sw.TYPE = 'H' ";
        if (ToolUtil.isNotEmpty(day)) {
            sql += " and ( '" + day + "' >= sw.STARTDATE && '" + day + "' <= sw.ENDDATE ) ";
        }
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerWeek.class));
        return query.getResultList();
    }

    @Override
    public List<ServerWeek> selectO(String day) {
        String sql = "select sw.UNID as unid,sw.TYPE as type,sw.STARTDATE as startDate,sw.ENDDATE as endDate,sw.NUM as num,sw.MEMO as memo,sw.STATE as state,sw.CREATETIME as createTime from server_week sw where sw.STATE = 'Y' and sw.TYPE = 'O' ";
        if (ToolUtil.isNotEmpty(day)) {
            sql += " and ( '" + day + "' >= sw.STARTDATE && '" + day + "' <= sw.ENDDATE ) ";
        }
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerWeek.class));
        return query.getResultList();
    }

    @Override
    public void working(String time) {
        ServerWeekOffice serverWeekOffice = new ServerWeekOffice();
        int days = 0;
        int year = Integer.parseInt(time);
        try {
            List<ServerWeekOffice> list = serverWeekOfficeService.select(time);
            if (ToolUtil.isNotEmpty(list)) {
                serverWeekOfficeService.del(time);
            }

            for (int month = 1; month < 13; month++) {

                switch (month) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        days = 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        days = 30;
                        break;
                    case 2:
                        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                            days = 29;
                        else
                            days = 28;
                        break;
                }
                for (int day = 1; day <= days; day++) {

                    Date bdate = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day);

                    String date = DateUtil.getDay(bdate);
                    List<ServerWeek> serverWeekListH = serverWeekService.selectH(date);
                    //如果为空，则不是节假日
                    if (ToolUtil.isEmpty(serverWeekListH)) {
                        List<ServerWeek> serverWeekListO = serverWeekService.selectO(date);
                        //如果不为空，则是加班日
                        if (ToolUtil.isNotEmpty(serverWeekListO)) {
                            serverWeekOffice.setId(UUID.randomUUID().toString());
                            serverWeekOffice.setOfficeTime(date);
                            serverWeekOffice.setCreateTime(DateUtil.getStrTime(new Date()));
                            serverWeekOffice.setYear(time);
                            serverWeekOfficeService.add(serverWeekOffice);
                        } else {  //不是节假日和加班日，则判断是否是周末
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(bdate);
                            boolean isWeekend = true; //默认是周末
                            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                                isWeekend = true;
                            } else {
                                isWeekend = false;
                            }
                            //不是周末，则是工作日
                            if (!isWeekend) {
                                serverWeekOffice.setId(UUID.randomUUID().toString());
                                serverWeekOffice.setOfficeTime(date);
                                serverWeekOffice.setCreateTime(DateUtil.getStrTime(new Date()));
                                serverWeekOffice.setYear(time);
                                serverWeekOfficeService.add(serverWeekOffice);
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {

        }
    }

    @Override
    public List<ServerWeek> findByYear(String time) {
        List<ServerWeek> weekList = serverWeekRepository.findByYear(time);
        return weekList;
    }


}
