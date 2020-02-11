package com.unionpay.services.service;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.unionpay.services.model.FlowChart;
import com.unionpay.services.model.ServerAttachmentFlowChart;
import com.unionpay.services.model.ServerCommonProblem;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unionpay.services.model.ServerAttachment;
import com.unionpay.services.repository.ServerattachmentRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class ServerattachmentServiceImpl implements ServerattachmentService {
	@Autowired
	private ServerattachmentRepository serverattachment_modelRepository;
	@PersistenceContext
	private EntityManager em;
	@Value("${http.servicecenterfile.download1}")
	private String url;

	/**
	 * 上传附件
	 * @param file
	 * @param savePath
	 * @param materId
	 * @param type
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public FlowChart uploadAndSaveAttachment(MultipartFile file, String savePath, Integer materId, Integer type) {
		ServerAttachment serverAttachment = null;
		FlowChart chart = null;
        String origFileName = getFileName(file.getOriginalFilename());
        String suffix = origFileName.substring(origFileName.lastIndexOf("."));
        String storeFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        String storeFile = savePath + storeFileName;
        File uploadFile = new File(storeFile);
		try {
			file.transferTo(uploadFile);
			serverAttachment = new ServerAttachment();
			serverAttachment.setMaterId(materId);
			serverAttachment.setName(origFileName);
			serverAttachment.setType(type);
			serverAttachment.setUrl(storeFile);
			serverAttachment = serverattachment_modelRepository.save(serverAttachment);
			chart = new FlowChart();
			chart.setId(serverAttachment.getId());
			chart.setName(serverAttachment.getName());
			chart.setUrl(url+serverAttachment.getId());
			return chart;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chart;
	}
	private String getFileName(String filename){
		int unixSep = filename.lastIndexOf("/");
		int winSep = filename.lastIndexOf("\\");
		int pos = (winSep > unixSep ? winSep : unixSep);
		if (pos != -1)  {
			return filename.substring(pos + 1);
		}
		else {
			return filename;
		}
	}

	@Override
	@Transactional
	public void deleteByMaterId(Integer materId) {
		String sql = "DELETE FROM server_attachment WHERE mater_id = " + materId+"";
		Query query = em.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerAttachment.class));
		query.executeUpdate();
	}

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerAttachment add(ServerAttachment serverattachment_model){
		return serverattachment_modelRepository.save(serverattachment_model);
	}
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void delete(Integer id){
		serverattachment_modelRepository.deleteById(id);
	}
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerAttachment update(ServerAttachment serverattachment_model){
		return serverattachment_modelRepository.saveAndFlush(serverattachment_model);
	}
	public ServerAttachment get(Integer id){
		return serverattachment_modelRepository.getOne(id);
	}
	public List<ServerAttachment> getAll(){
		return serverattachment_modelRepository.findAll();
	}
	public Page<ServerAttachment> getPage(Integer page, Integer rows){
		Pageable pageable = new PageRequest(page-1, rows);
		return serverattachment_modelRepository.findAll(pageable);
	}

	/**
	 * 根据材料id，查询所有对应的附件
	 * @return
	 * @author lishuang
	 * @date 2019-04-01
	 */
	public List<ServerAttachment> getAll(Integer materId){
		return serverattachment_modelRepository.findByMaterId(materId);
	}

}