package com.unionpay.supervision.service;

import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.model.ServiceModel;
import com.unionpay.supervision.model.SponsorSearchDto;
import com.unionpay.supervision.model.SuperServiceModelDto;

import java.util.List;

/**
 * 事项查询与操作
*/
public interface SponsorSearchService {
	
	List<ServiceModel> findSuperVisionlist(Integer page, Integer size,SponsorSearchDto sponsorSearch);

	List<ServiceModel> findAndExportSuperVisionlist(Integer page, Integer size, SponsorSearchDto sponsorSearch, String unids);
	
	int getSuperVisioncount(SponsorSearchDto sponsorSearch);
	
	void supervisionCombine(SuperSponsor removeSponsor,SuperSponsor targetSponsor);

	boolean editSuperService(SuperServiceModelDto superServiceModelDto);

}
