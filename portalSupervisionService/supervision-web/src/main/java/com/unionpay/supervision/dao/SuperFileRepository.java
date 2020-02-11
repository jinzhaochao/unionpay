package com.unionpay.supervision.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unionpay.supervision.domain.SuperFile;

/**
 * 文件上传类
 */
@Repository
public interface SuperFileRepository  extends JpaRepository<SuperFile, String>{
	
	SuperFile findByFileId(String fileId);
	List<SuperFile> findBySuperServiceId(String superServiceId);
	List<SuperFile> findAllByOverseeMappingId(String overseeMappingId);
	
//	@Modifying
//	@Transactional
//	@Query("update super_file f set f.super_service_id = :superServiceId AND f.service_name = :serviceName  where f.file_id = :fileId")
//	public void updateSuperServiceIdByFileId(@Param("superServiceId")String superServiceId,
//			@Param("serviceName")String serviceName,@Param("fileId")String fileId);

}
