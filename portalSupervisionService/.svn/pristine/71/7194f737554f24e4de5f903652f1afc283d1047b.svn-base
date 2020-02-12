package com.unionpay.supervision.service;

import java.util.List;

import com.unionpay.supervision.domain.OmUser;

public interface OmUserService {
    /**
     * 根据userid查找用户
     * @param userid
     * @return
     */
	public OmUser findByUserid(String userid);

	public OmUser findById(Integer empid);

    public List viewTree();

    public List findLatestUnReadCalendarId(String empcode);
    
    /**
     * 根据组织查询用户
    */
    public List<OmUser> findUserByOrgName(String orgName);
}
