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

import com.unionpay.services.model.ServerAcceptanceUser;
import com.unionpay.services.repository.ServeracceptanceuserRepository;
@Service
public class ServeracceptanceuserServiceImpl implements ServeracceptanceuserService{
	@Autowired
	private ServeracceptanceuserRepository serveracceptanceuser_modelRepository;

	/**
	 * 根据id查询受理人
	 * @param id
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-14
	 */
	public ServerAcceptanceUser getOne(Integer id){
		return serveracceptanceuser_modelRepository.getOne(id);
	}

	/**
	 * 根据服务id查询所有受理人
	 * @param serverId
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-12
	 */
	public List<ServerAcceptanceUser> getAllByServerId(Integer serverId){
		return serveracceptanceuser_modelRepository.getAllByServerId(serverId);
	}

	/**
	 * 根据服务id删除所有受理人
	 * @param serverId
	 *
	 * @author lishuang
	 * @data 2019-03-12
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void deleteAllByServerId(Integer serverId){
		serveracceptanceuser_modelRepository.deleteAllByServerId(serverId);
	}

	/**
	 * 新增受理人
	 * @param serverAcceptanceUser
	 *
	 * @author lishuang
	 * @data 2019-03-12
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerAcceptanceUser add(ServerAcceptanceUser serverAcceptanceUser){
		return serveracceptanceuser_modelRepository.save(serverAcceptanceUser);
	}

	/**
	 * 根据主键id删除受理人
	 * @param id
	 * @author lishuang
	 * @date 2019-04-19
	 */
	public void deleteById(Integer id){
		serveracceptanceuser_modelRepository.deleteById(id);
	}
}