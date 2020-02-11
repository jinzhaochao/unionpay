package com.unionpay.support.dao;

import com.unionpay.support.pojo.SupportSendMsgLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: jinzhao
 * @Date: 2019/11/14 17:17
 * @Description:
 */
@Repository
public interface SupportSendMsgLogRepository extends JpaRepository<SupportSendMsgLog,Integer>,JpaSpecificationExecutor<SupportSendMsgLog>{
}
