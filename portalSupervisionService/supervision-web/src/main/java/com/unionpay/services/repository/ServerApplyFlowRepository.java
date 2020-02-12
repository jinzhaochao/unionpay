package com.unionpay.services.repository;

import com.unionpay.services.entity.ServerApplyFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerApplyFlowRepository extends JpaRepository<ServerApplyFlow,Integer>, JpaSpecificationExecutor<ServerApplyFlow> {
    /**
     * 根据流程编号，服务项编码，查询对应关系，获取服务中心中服务名称
     * @return
     */
    public ServerApplyFlow findByFlowNameIdAndServerItemId(String flowNameId,String serverItemId);
}
