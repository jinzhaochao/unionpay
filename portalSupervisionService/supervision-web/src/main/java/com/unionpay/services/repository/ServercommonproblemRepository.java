package com.unionpay.services.repository;
import com.unionpay.services.model.ServerCommonProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServercommonproblemRepository extends JpaRepository<ServerCommonProblem,Integer>,JpaSpecificationExecutor<ServerCommonProblem> {
    /**
     * 根据服务serverId，删除所有相关问题
     * @param serverId
     * @author lishuang
     * @date 2019-04-01
     */
    public void deleteAllByServerId(Integer serverId);
}