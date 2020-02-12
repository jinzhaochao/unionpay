package com.unionpay.supervision.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.unionpay.supervision.domain.SuperFile;

public interface SuperFileService {
	
	
	/**
     * 上传附件
     * @param file
     * @return
     */
	SuperFile uploadFile(MultipartFile file ,String savePath,String userId);
	
	/**
     * 查询文件ById
     * @param fileId
     * @return
     */
	SuperFile selectByFileId(String fileId);
	
	/**
    * 查询文件BysuperServiceId
    * @param superServiceId
    * @return
    */
	List<SuperFile> findAllBySuperServiceId(String superServiceId);
	
	/**
	    * 文件置为无效
	    * @param superServiceId
	    * @return
	    */
	public void updateIsNotUse(String superServiceId);
	
	
	/**
     *文件删除
     * @param fileId
     * @return
     */
	public void  deleteByFileId(String fileId);
	
	
	public void edit(SuperFile superFile);
	
	
	/**
	 * 更新superServiceId
	*/
	public void updateSuperServiceId(String superServiceId,String targetSuperServiceId);

	/**
	 * 根据fileId查找文件
	 */
	SuperFile findByFileId(String fileId);

    List<SuperFile> findByOverseeMappingId(String unid);
}
