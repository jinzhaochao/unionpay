package com.unionpay.supervision.service;

import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.domain.SuperSponsorLog;
import com.unionpay.supervision.model.SponsorLogList;

import java.util.LinkedList;
import java.util.List;

public interface SuperSponsorLogService {

	LinkedList<SuperSponsorLog> findBySponsorUnid(String sponsorUnid);
	
	SuperSponsorLog findById(String unid);
	
	SuperSponsorLog add(SuperSponsor superSponsor);
	
	void add(SuperSponsorLog superSponsorLog);
	
	void edit(SuperSponsorLog superSponsorLog);
	
	void delete(String unid);
	
	/**
	    * 督办部门置为无效
	    * @param sponsorUnid
	    * @return
	    */
	public void updateStatus(String sponsorUnid);

	/**
	 * 督办历史记录合并
	 */
	public void combine(String removeSponsorUnid, String tagerSponsorUnid);

}
