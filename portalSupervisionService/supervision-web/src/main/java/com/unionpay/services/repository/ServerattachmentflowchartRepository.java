package com.unionpay.services.repository;
import com.unionpay.services.model.ServerAttachmentFlowChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerattachmentflowchartRepository extends JpaRepository<ServerAttachmentFlowChart,Integer>,JpaSpecificationExecutor<ServerAttachmentFlowChart> {
    /**
     * 根据服务id删除
     * @param serverId
     *
     * @author lishuang
     * @data 2019-03-12
     */
    void deleteByServerId(Integer serverId);

    /**
     * 根据服务id删除所有流程图
     * @param serverId
     *
     * @author lishuang
     * @data 2019-03-12
     */
    void deleteAllByServerId(Integer serverId);

    /**
     * 根据服务id查找所有流程图
     * @param serverId
     * @return
     */
    List<ServerAttachmentFlowChart> getAllByServerId(Integer serverId);
}