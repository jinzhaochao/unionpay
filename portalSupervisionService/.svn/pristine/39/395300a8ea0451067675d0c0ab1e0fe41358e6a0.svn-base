package com.unionpay.sms.service.impl;

import com.unionpay.sms.service.OmOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.domain.OmOrganization;

import java.util.List;

/**
 * 
 * @author wangyue
 * @date 2018-12-7 部门的service实现类
 */
@Service
public class OmOrganizationServiceImpl implements OmOrganizationService {
	@Autowired
	private OMOrganizationRepository omOrganizationRepository;

	/**
	 * 根据部门id获取信息
	 * 
	 * @param orgid 部门id
	 */
	@Override
	public OmOrganization findByOrgid(Integer orgid) {
		return omOrganizationRepository.findByOrgid(orgid);
	}

	public List<OmOrganization> fingByParentOrgid(Integer parentOrgid){
		return omOrganizationRepository.findByParentorgidAndStatusOrderBySortno(parentOrgid,"0");
	}

}
