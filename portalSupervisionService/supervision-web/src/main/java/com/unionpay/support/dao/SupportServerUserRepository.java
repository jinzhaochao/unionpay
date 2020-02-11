package com.unionpay.support.dao;

import com.unionpay.support.pojo.SupportServerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */

@Repository
public interface SupportServerUserRepository extends JpaRepository<SupportServerUser,Integer>,JpaSpecificationExecutor<SupportServerUser> {

    @Query(value = " select su.* from support_server_user su where su.server_name = ?1 ",nativeQuery = true)
    SupportServerUser findByServerName(String serverName);

    @Query(value = " select su.* from support_server_user su where su.id = ?1 ",nativeQuery = true)
    SupportServerUser findServerUserById(Integer serverUserId);
}
