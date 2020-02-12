package com.unionpay.supervision.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unionpay.supervision.domain.SuperClient;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 督办客户连接
 */
@Repository
public interface SuperClientRepository extends JpaRepository<SuperClient, String>{

	public SuperClient findByAppKey(String appKey);

}
