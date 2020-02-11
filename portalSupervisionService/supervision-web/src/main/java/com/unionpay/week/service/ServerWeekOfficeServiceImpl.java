package com.unionpay.week.service;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.week.dao.ServerWeekOfficeRepository;
import com.unionpay.week.pojo.ServerWeekOffice;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
@Transactional
public class ServerWeekOfficeServiceImpl implements ServerWeekOfficeService {
    @Autowired
    private ServerWeekOfficeRepository serverWeekOfficeRepository;
    @PersistenceContext
    private EntityManager em;
    @Override
    public void add(ServerWeekOffice serverWeekOffice) {
        serverWeekOfficeRepository.save(serverWeekOffice);
    }

    @Override
    public Integer count(String startTime, String endTime) {


        String sql = "select swo.id,swo.office_time as officeTime,DATE_FORMAT(swo.create_time,'%Y-%m-%d %H:%i:%S') as createTime  from server_week_office swo where 1=1 ";
        if (ToolUtil.isNotEmpty(startTime)){
            sql += " and swo.office_time > '"+startTime+"' ";
        }
        if (ToolUtil.isNotEmpty(endTime)){
            sql += " and swo.office_time <= '"+endTime+"' ";
        }
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerWeekOffice.class));
        return query.getResultList().size();
    }

    @Override
    public List<ServerWeekOffice> select(String time) {
        List<ServerWeekOffice> list = serverWeekOfficeRepository.findByYear(time);
        return list;
    }

    @Override
    public void del(String time) {
        serverWeekOfficeRepository.deleteByYear(time);
    }
}
