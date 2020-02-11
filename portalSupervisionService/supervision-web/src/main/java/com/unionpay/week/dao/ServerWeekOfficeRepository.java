package com.unionpay.week.dao;

import com.unionpay.week.pojo.ServerWeekOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerWeekOfficeRepository extends JpaRepository<ServerWeekOffice,String>,JpaSpecificationExecutor<ServerWeekOffice> {

    List<ServerWeekOffice> findByYear(String time);

    void deleteByYear(String time);
}
