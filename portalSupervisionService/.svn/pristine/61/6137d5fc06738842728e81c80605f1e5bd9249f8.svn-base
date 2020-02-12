package com.unionpay.voice.service;

import com.unionpay.voice.domain.VoiceFile;
import com.unionpay.voice.model.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 客户之声附件操作
 * 
 * @author lishuang
 * @date 2019-05-08 10:16:29
 */
public interface VoiceFileService {

	/**
	 * 上传附件
	 * @param file
	 * @return
	 */
	public FileModel uploadFile(MultipartFile file , String savePath, String userId, String infoUnid, Integer fileType);

	/**
	 * 查询附件
	 * @param unid
	 * @return
	 */
	public VoiceFile get(String unid);

	/**
	 * 新增附件
	 * @param file
	 * @return
	 */
	public VoiceFile save(VoiceFile file);

	/**
	 * 附件删除
	 * @param voiceFile
	 */
	public boolean delete(VoiceFile voiceFile);

	/**
	 * 根据infoUnid查询所有附件
	 * @param infoUnid
	 * @return
	 */
	public List<VoiceFile> getAllByInfoUnid(String infoUnid);

}
