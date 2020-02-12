package com.unionpay.services.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unionpay.services.model.ServerAcceptanceDept;
import com.unionpay.services.repository.ServeracceptancedeptRepository;
@Service
public class ServeracceptancedeptServiceImpl implements ServeracceptancedeptService{
	@Autowired
	private ServeracceptancedeptRepository serveracceptancedept_modelRepository;

	/**
	 * 根据服务id查询所有受理部门
	 * @param serverId
	 * @return
	 */
	public List<ServerAcceptanceDept> getAllByServerId(Integer serverId){
		return serveracceptancedept_modelRepository.getAllByServerId(serverId);
	}

	/**
	 * 根据服务id删除所有受理部门
	 * @param serverId
	 *
	 * @author lishuang
	 * @date 2019-03-12
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void deleteAllByServerId(Integer serverId){
		serveracceptancedept_modelRepository.deleteAllByServerId(serverId);
	}

	/**
	 * 新增受理部门
	 * @param serverAcceptanceDept
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-12
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerAcceptanceDept add(ServerAcceptanceDept serverAcceptanceDept){
		return serveracceptancedept_modelRepository.save(serverAcceptanceDept);
	}

	/**
	 * 根据主键id删除手里部门
	 * @param id
	 * @author lishuang
	 * @date 2019-04-19
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void deleteById(Integer id){
		serveracceptancedept_modelRepository.deleteById(id);
	}
}