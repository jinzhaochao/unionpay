package com.unionpay.services.service;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.unionpay.common.util.DateUtil;
import com.unionpay.services.model.FlowChart;
import com.unionpay.supervision.domain.SuperFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unionpay.services.model.ServerAttachmentFlowChart;
import com.unionpay.services.repository.ServerattachmentflowchartRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServerattachmentflowchartServiceImpl implements ServerattachmentflowchartService{
	@Autowired
	private ServerattachmentflowchartRepository serverattachmentflowchart_modelRepository;
	@Value("${http.servicecenterfile.download}")
	private String url;


	/**
	 * 新增流程图
	 * @param serverAttachmentFlowChart
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-12
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerAttachmentFlowChart add(ServerAttachmentFlowChart serverAttachmentFlowChart){
		ServerAttachmentFlowChart flowChart = serverattachmentflowchart_modelRepository.save(serverAttachmentFlowChart);
		return flowChart;
	}

	/**
	 * 删除流程图
	 * @param id
	 *
	 * @author lishuang
	 * @data 2019-03-11
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void delete(Integer id){
		serverattachmentflowchart_modelRepository.deleteById(id);
	}

	/**
	 * 根据服务id删除所有流程图
	 * @param serverId
	 *
	 * @author lishuang
	 * @data 2019-03-12
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void deleteAllByServerId(Integer serverId){
		serverattachmentflowchart_modelRepository.deleteAllByServerId(serverId);
	}

	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ServerAttachmentFlowChart update(ServerAttachmentFlowChart serverattachmentflowchart_model){
		return serverattachmentflowchart_modelRepository.saveAndFlush(serverattachmentflowchart_model);
	}

	/**
	 * 根据流程图id查看
	 * @param id
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-11
	 */
	public ServerAttachmentFlowChart get(Integer id){
		return serverattachmentflowchart_modelRepository.getOne(id);
	}

	/**
	 * 根据服务id查询所有流程图
	 * @param serverId
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-12
	 */
	public List<ServerAttachmentFlowChart> getAllByServerId(Integer serverId){
		return serverattachmentflowchart_modelRepository.getAllByServerId(serverId);
	}

	public Page<ServerAttachmentFlowChart> getPage(Integer page, Integer rows){
		Pageable pageable = new PageRequest(page-1, rows);
		return serverattachmentflowchart_modelRepository.findAll(pageable);
	}

	/**
	 * 上传流程图，并保存至数据库
	 * @param file
	 * @param savePath
	 * @param userId
	 * @return
	 *
	 * @author lishuang
	 * @data 2019-03-11
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public FlowChart uploadAndSaveFlowChart(MultipartFile file, String savePath, String userId) {
		ServerAttachmentFlowChart serverAttachmentFlowChart = null;
		FlowChart chart = null;
		String origFileName = getFileName(file.getOriginalFilename());
		String suffix = origFileName.substring(origFileName.lastIndexOf("."));
		String storeFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
		String storeFile = savePath + storeFileName;
		File uploadFile = new File(storeFile);
		try {
			file.transferTo(uploadFile);
			serverAttachmentFlowChart = new ServerAttachmentFlowChart();
			serverAttachmentFlowChart.setServerId(0);
			serverAttachmentFlowChart.setName(origFileName);
			serverAttachmentFlowChart.setUrl(storeFile);
			serverAttachmentFlowChart = serverattachmentflowchart_modelRepository.save(serverAttachmentFlowChart);
			chart = new FlowChart();
			chart.setId(serverAttachmentFlowChart.getId());
			chart.setName(serverAttachmentFlowChart.getName());
			chart.setUrl(url+serverAttachmentFlowChart.getId());
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

	/**
	 * 下载流程图
	 * @param id
	 * @return
	 *
	 * @author lishuang
	 * @date 2019-03-27
	 */
	public ServerAttachmentFlowChart downLoadFlowChar(Integer id){
		ServerAttachmentFlowChart flowChart = null;
		if (null != id){
			flowChart = serverattachmentflowchart_modelRepository.getOne(id);
		}
		return flowChart;
	}
}