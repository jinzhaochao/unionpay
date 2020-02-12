package com.unionpay.services.repository;
import com.unionpay.services.model.ServerAcceptanceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServeracceptanceuserRepository extends JpaRepository<ServerAcceptanceUser,Integer>,JpaSpecificationExecutor<ServerAcceptanceUser> {

    /**
     * 根据服务id删除
     * @param serverId
     *
     * @author lishuang
     * @data 2019-03-12
     */
    void deleteAllByServerId(Integer serverId);

    /**
     * 根据服务id查询受理人
     * @param id
     * @return
     *
     * @author lishuang
     * @data 2019-03-12
     */
    List<ServerAcceptanceUser> getAllByServerId(Integer id);
}