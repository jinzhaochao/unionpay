package com.unionpay.supervision.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unionpay.supervision.dao.SuperLogRepository;
import com.unionpay.supervision.domain.SuperLog;
import com.unionpay.supervision.service.SuperLogService;

/**
 * <p>
 *  日志记录实现类
 * </p>
 *
 * @author xiongym
 * @since 2018-11-30
 */
@Service
@Transactional
public class SuperLogServiceImpl implements SuperLogService{
	
	@Autowired
	private SuperLogRepository superLogRepository;

	@Override
	public void add(SuperLog log) {
		superLogRepository.save(log);
	}

}
