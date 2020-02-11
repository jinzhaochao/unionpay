package com.unionpay.services.repository;
import com.unionpay.services.model.ServerAcceptanceDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServeracceptancedeptRepository extends JpaRepository<ServerAcceptanceDept,Integer>,JpaSpecificationExecutor<ServerAcceptanceDept> {
    /**
     * 根据服务id删除
     * @param serverId
     *
     * @author lishuang
     * @data 2019-03-12
     */
    void deleteAllByServerId(Integer serverId);


    /**
     * 根据服务id查询受理部门
     * @param id
     * @return
     *
     * @author lishuang
     * @data 2019-03-12
     */
    List<ServerAcceptanceDept> getAllByServerId(Integer id);
}