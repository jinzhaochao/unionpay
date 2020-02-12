package com.unionpay.supervision.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unionpay.supervision.dao.SuperClientRepository;
import com.unionpay.supervision.domain.SuperClient;
import com.unionpay.supervision.service.SuperClientService;


/**
 *
 * 督办客户连接实现类
 *
 * @author xiongym
 * @since 2018-11-29
 */
@Service
@Transactional
public class SuperClientServiceImpl implements SuperClientService{
	
	@Autowired
	private SuperClientRepository superClientRepository;

	@Override
	public SuperClient findByAppkey(String appkey) {
		return superClientRepository.findByAppKey(appkey);
	}

}
