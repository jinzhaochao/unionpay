package com.unionpay.services.service;
import java.util.List;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unionpay.services.model.ServerCommonProblem;
import com.unionpay.services.repository.ServercommonproblemRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class ServercommonproblemServiceImpl implements ServercommonproblemService{

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ServercommonproblemRepository servercommonproblem_modelRepository;
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerCommonProblem add(ServerCommonProblem servercommonproblem_model){
		return servercommonproblem_modelRepository.save(servercommonproblem_model);
	}
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void delete(Integer id){
		servercommonproblem_modelRepository.deleteById(id);
	}
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerCommonProblem update(ServerCommonProblem servercommonproblem_model){
		return servercommonproblem_modelRepository.saveAndFlush(servercommonproblem_model);
	}
	public ServerCommonProblem get(Integer id){
		return servercommonproblem_modelRepository.getOne(id);
	}
	public List<ServerCommonProblem> getAll(){
		return servercommonproblem_modelRepository.findAll();
	}
	public Page<ServerCommonProblem> getPage(Integer page, Integer rows){
		Pageable pageable = new PageRequest(page-1, rows);
		return servercommonproblem_modelRepository.findAll(pageable);
	}


	/**
	 * 查询一个问题的详情
	 * @param id
	 * @param serverId
	 * @return
	 */
	public ServerCommonProblem getOneById(Integer id,Integer serverId){
		String sql = "SELECT scp.id,scp.server_id as serverId,scp.ask,scp.answer,scp.status,scp.sort,scp.createtime \n" +
				"FROM server_common_problem scp  WHERE scp.id = :id AND scp.server_id = :serverId ";
		Query query = em.createNativeQuery(sql);
		query.setParameter("id",id);
		query.setParameter("serverId",serverId);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerCommonProblem.class));
		ServerCommonProblem serverCommonProblem = (ServerCommonProblem) query.getSingleResult();
		return serverCommonProblem;
	}

	/**
	 * 查询某一项目的所有问题
	 * @param serverId
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public List<ServerCommonProblem> getSelectAll(Integer serverId) {
		String sql = "SELECT scp.id,scp.server_id as serverId,scp.ask,scp.answer,scp.status,scp.sort,scp.createtime \n" +
				"FROM server_common_problem scp  WHERE scp.server_id =:serverId ORDER BY scp.sort ASC";
		Query query = em.createNativeQuery(sql);
		query.setParameter("serverId",serverId);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerCommonProblem.class));
		return query.getResultList();
	}

	/**
	 * 根据服务serverId，删除所有相关问题
	 * @param serverId
	 * @author lishaung
	 * @date 2019-04-01
	 */
	public void deleteAllByServerId(Integer serverId){
		servercommonproblem_modelRepository.deleteAllByServerId(serverId);
	}
}