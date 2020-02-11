package com.unionpay.supervision.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unionpay.supervision.domain.SuperLog;

/**
 * 日志记录
 */
@Repository
public interface SuperLogRepository extends JpaRepository<SuperLog,Integer>{
	
	List<SuperLog> findBySponsorUnid(String sponsorUnid);
	
	List<SuperLog> findBySponsorId(String sponsorId);
	
	List<SuperLog> findByOverseeUserid(String overseeUserid);

}
