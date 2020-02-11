package com.unionpay.week.dao;

import com.unionpay.week.pojo.ServerWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerWeekRepository extends JpaRepository<ServerWeek,String>,JpaSpecificationExecutor<ServerWeek> {
    List<ServerWeek> findByYear(String time);
}
