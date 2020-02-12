package com.unionpay.supervision.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperFileRepository;
import com.unionpay.supervision.domain.SuperFile;
import com.unionpay.supervision.service.SuperFileService;

/**
 * <p>
 * 文件上传实现类
 * </p>
 *
 * @author xiongym
 * @since 2018-10-29
 */
@Service
@Transactional
public class SuperFileServiceImpl implements SuperFileService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SuperFileRepository superFileRepository;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 文件上传
	 */
	@Override
	public SuperFile uploadFile(MultipartFile file, String savePath, String userId) {
		SuperFile superFile = null;
		String origFileName = file.getOriginalFilename();
		String suffix = origFileName.substring(origFileName.lastIndexOf("."));
		String storeFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
		String storeFile = savePath + storeFileName;
		File uploadFile = new File(storeFile);
		try {
			file.transferTo(uploadFile);
			String uuid = UUID.randomUUID().toString();
			superFile = new SuperFile();
			superFile.setFileId(uuid);
			superFile.setCreateUserid(userId);
			superFile.setFileName(origFileName);
			superFile.setFileStoreName(storeFileName);
			superFile.setUrl(storeFile);
			superFile.setIsUse(1);
			superFile.setCreateTime(DateUtil.getTime());
			superFileRepository.save(superFile);
			return superFile;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return superFile;
	}

	/**
	 * 根据fileId查询文件
	 */
	@Override
	public SuperFile selectByFileId(String fileId) {
		SuperFile superFile = null;
		if (fileId != null) {
			superFile = superFileRepository.findByFileId(fileId);
		}
		return superFile;
	}

	/**
	 * 根据superServiceId查询文件集合
	 */
	@Override
	public List<SuperFile> findAllBySuperServiceId(String superServiceId) {
		return superFileRepository.findBySuperServiceId(superServiceId);
	}

	/**
	 * 根据superServiceId文件置为无效
	 */
	@Override
	public void updateIsNotUse(String superServiceId) {
		String sql = null;
		if (ToolUtil.isNotEmpty(superServiceId)) {
			sql = "UPDATE super_file f set f.is_use = 0 WHERE f.super_service_id = " + "'" + superServiceId + "'"
					+ " AND f.is_use = 1";
			Query query = entityManager.createNativeQuery(sql);
			query.executeUpdate();
		}
	}

	/**
	 * 更新superServiceId
	 */
	@Override
	public void updateSuperServiceId(String superServiceId, String targetSuperServiceId) {
		String sql = null;
		if (ToolUtil.isNotEmpty(superServiceId)) {
			sql = "UPDATE super_file f set f.super_service_id =  " + "'" + targetSuperServiceId + "'"
					+ " WHERE f.super_service_id = " + "'" + superServiceId + "'" + " AND f.is_use = 1";
			Query query = entityManager.createNativeQuery(sql);
			query.executeUpdate();
		}
	}

	@Override
	public void deleteByFileId(String fileId) {
		superFileRepository.deleteById(fileId);

	}

	@Override
	public void edit(SuperFile superFile) {
		superFileRepository.save(superFile);

	}

	public SuperFile findByFileId(String fileId){
		return superFileRepository.findByFileId(fileId);
	}

	@Override
	public List<SuperFile> findByOverseeMappingId(String unid) {
		return superFileRepository.findAllByOverseeMappingId(unid);
	}

}
