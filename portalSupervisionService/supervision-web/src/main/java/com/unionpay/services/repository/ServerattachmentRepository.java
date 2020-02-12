package com.unionpay.services.repository;
import com.unionpay.services.model.ServerAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerattachmentRepository extends JpaRepository<ServerAttachment,Integer>,JpaSpecificationExecutor<ServerAttachment> {

    //查询办理材料中上传的附件（空白表格，实例表格）
    List<ServerAttachment> findByMaterId(Integer materId);

}