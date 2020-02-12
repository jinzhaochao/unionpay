package com.unionpay.supervision.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unionpay.supervision.domain.SuperConcerneLeadership;

/**
 * 领导事项关注记录表
 */
@Repository
public interface SuperConcerneLeadershipRepository extends JpaRepository<SuperConcerneLeadership,Integer>{
	
	public List<SuperConcerneLeadership> findBySponsorUnid(String sponsorUnid);
	
	public List<SuperConcerneLeadership> findByleaderUserid(String leaderUserid);

}
