package com.unionpay.services.service;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.ServerAttachment;
import com.unionpay.services.model.ServerHandlingMaterial;
import com.unionpay.services.repository.ServerhandlingmaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ServerhandlingmaterialServiceImpl implements ServerhandlingmaterialService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ServerhandlingmaterialRepository serverhandlingmaterial_modelRepository;
	@Autowired
	private ServerattachmentService serverattachmentService;

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerHandlingMaterial add(ServerHandlingMaterial serverhandlingmaterial_model){
		return serverhandlingmaterial_modelRepository.save(serverhandlingmaterial_model);
	}
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void delete(Integer id){
		serverhandlingmaterial_modelRepository.deleteById(id);
	}
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerHandlingMaterial update(ServerHandlingMaterial serverhandlingmaterial_model){
		return serverhandlingmaterial_modelRepository.saveAndFlush(serverhandlingmaterial_model);
	}
	public ServerHandlingMaterial get(Integer id){
		return serverhandlingmaterial_modelRepository.getOne(id);
	}
	public List<ServerHandlingMaterial> getAll(){
		return serverhandlingmaterial_modelRepository.findAll();
	}
	public Page<ServerHandlingMaterial> getPage(Integer page, Integer rows){
		Pageable pageable = new PageRequest(page-1, rows);
		return serverhandlingmaterial_modelRepository.findAll(pageable);
	}

	/**
	 * 查询一个办理材料的详情
	 * @param id
	 * @param serverId
	 * @return
	 */
	@Override
	public ServerHandlingMaterial getOneById(Integer id, Integer serverId) {
		/*String sql = "SELECT shm.id,shm.server_id,shm.name,shm.necessary,shm.number,shm.remark,\n" +
				"shm.status,shm.sort,shm.type,shm.createtime " +
				"FROM server_handling_materials shm WHERE shm.id = " + id + " and shm.server_id = " + serverId +" ";
		Query query = em.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerHandlingMaterial.class));
		ServerHandlingMaterial serverHandlingMaterial = (ServerHandlingMaterial) query.getSingleResult();*/
		return serverhandlingmaterial_modelRepository.findByIdAndServerId(id,serverId);
	}

	/**
	 * 查询某一项目所有的办理材料
	 * @param serverId
	 * @return
	 */
	@Override
	public List<ServerHandlingMaterial> getSelectAll(Integer serverId) {
		return serverhandlingmaterial_modelRepository.findByServerId(serverId);
	}

	@Override
	public ServerHandlingMaterial edit(ServerHandlingMaterial serverHandlingMaterial) {
		ServerHandlingMaterial serverHandlingMaterial1 = new ServerHandlingMaterial();
		serverHandlingMaterial1 = serverhandlingmaterial_modelRepository.save(serverHandlingMaterial);
		return serverHandlingMaterial1;
	}

	/**
	 * 根据服务id删除所有受理材料
	 * @param id
	 * @return
	 * @author lishuang
	 * @date 2019-03-29
	 */
	public void deleteAllByServerId(Integer id){
		List<ServerHandlingMaterial> serverHandlingMaterials = serverhandlingmaterial_modelRepository.findByServerId(id);
		if (ToolUtil.isNotEmpty(serverHandlingMaterials)){
			for (ServerHandlingMaterial material : serverHandlingMaterials){
				List<ServerAttachment> attachments = serverattachmentService.getAll(material.getId());
				if (ToolUtil.isNotEmpty(attachments)){
					serverattachmentService.deleteByMaterId(material.getId());
				}
			}
		}
		serverhandlingmaterial_modelRepository.deleteAllByServerId(id);
	}
}