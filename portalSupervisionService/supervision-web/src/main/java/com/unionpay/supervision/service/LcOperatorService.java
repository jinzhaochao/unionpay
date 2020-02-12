package com.unionpay.supervision.service;

import com.unionpay.supervision.model.LcFeedBackDto;

public interface LcOperatorService {
	
	/**
	 * 事项信息补充
	*/
	public void editSponsorInfo(LcFeedBackDto lcFeedBackDto);
	
	/**
	 * 事项信息变更与延期
	*/
	public void SponsorChangeAndDelay(LcFeedBackDto lcFeedBackDto);

}
