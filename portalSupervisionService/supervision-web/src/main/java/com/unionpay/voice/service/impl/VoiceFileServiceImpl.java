package com.unionpay.voice.service.impl;

import com.unionpay.common.exception.ServiceException;
import com.unionpay.common.util.DateUtil;
import com.unionpay.voice.dao.VoiceFileRepository;
import com.unionpay.voice.domain.VoiceFile;
import com.unionpay.voice.model.FileModel;
import com.unionpay.voice.service.VoiceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class VoiceFileServiceImpl implements VoiceFileService {
	@Autowired
	private VoiceFileRepository voiceFileRepository;

	/**
	 * 上传附件
	 * @param file
	 * @return
	 */
	public FileModel uploadFile(MultipartFile file, String savePath, String userId,String infoUnid,Integer fileType){
		FileModel fileModel = null;
		String origFileName = getFileName(file.getOriginalFilename());
		String suffix = origFileName.substring(origFileName.lastIndexOf("."));
		String storeFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
		String storeFile = savePath + storeFileName;
		File uploadFile = new File(storeFile);
		try {
			file.transferTo(uploadFile);
			String uuid = UUID.randomUUID().toString();
			VoiceFile voiceFile = new VoiceFile();
			voiceFile.setUnid(uuid);
			if (null != infoUnid){
				voiceFile.setInfoUnid(infoUnid);
			}
			if (null != fileType){
				voiceFile.setFileType(fileType);
			}
			voiceFile.setFileName(origFileName);
			voiceFile.setFileStoreName(storeFileName);
			voiceFile.setFilePath(storeFile);
			voiceFile.setCreateUserid(userId);
			voiceFile.setCreateTime(DateUtil.getTime(new Date()));
			voiceFile = voiceFileRepository.save(voiceFile);
			//页面显示附件信息
			fileModel = new FileModel();
			fileModel.setUnid(voiceFile.getUnid());
			fileModel.setFileName(voiceFile.getFileName());
			return fileModel;
		} catch (Exception e) {
			throw new ServiceException(500,e.getMessage());
		}
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
	 * 查询附件
	 * @param unid
	 * @return
	 */
	@Override
	public VoiceFile get(String unid){
		return voiceFileRepository.findByUnid(unid);
	}

	/**
	 * 新增附件
	 * @param file
	 * @return
	 */
	@Override
	public VoiceFile save(VoiceFile file){
		return voiceFileRepository.save(file);
	}

	/**
	 * 附件删除
	 * @param voiceFile
	 */
	@Override
	public boolean delete(VoiceFile voiceFile){
		voiceFileRepository.delete(voiceFile);
		File file = new File(voiceFile.getFilePath());
		if (file.exists() && file.isFile()){
			boolean bool = file.delete();
			if (bool){
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据infoUnid查询所有附件
	 * @param infoUnid
	 * @return
	 */
	public List<VoiceFile> getAllByInfoUnid(String infoUnid){
		return voiceFileRepository.findAllByInfoUnid(infoUnid);
	}

}
