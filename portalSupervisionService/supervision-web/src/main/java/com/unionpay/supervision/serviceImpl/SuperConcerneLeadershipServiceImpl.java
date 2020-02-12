package com.unionpay.supervision.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unionpay.supervision.dao.SuperConcerneLeadershipRepository;
import com.unionpay.supervision.domain.SuperConcerneLeadership;
import com.unionpay.supervision.service.SuperConcerneLeadershipService;

/**
 * <p>
 *  领导事项关注记录实现类
 * </p>
 *
 * @author xiongym
 * @since 2018-12-12
 */
@Service
@Transactional
public class SuperConcerneLeadershipServiceImpl implements SuperConcerneLeadershipService{
	
	@Autowired
    private SuperConcerneLeadershipRepository superConcerneLeadershipRepository;

	/**
	 * 根据主办单位查询关注领导
	*/
	public List<SuperConcerneLeadership> findBySponsorUnid(String sponsorUnid){
		return superConcerneLeadershipRepository.findBySponsorUnid(sponsorUnid);
	}

	/**
	 * 根据关注领导查询
	*/
	public List<SuperConcerneLeadership> findByleaderUserid(String leaderUserid){
		return superConcerneLeadershipRepository.findByleaderUserid(leaderUserid);
	}
	
	/**
	 * 新增
	*/
	public void insertOrUpdata(SuperConcerneLeadership superConcerneLeadership){
		superConcerneLeadershipRepository.save(superConcerneLeadership);
	}

}
