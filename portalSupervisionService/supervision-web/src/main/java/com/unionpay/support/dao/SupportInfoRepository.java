package com.unionpay.support.dao;

import com.unionpay.support.pojo.SupportInfo;
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
public interface SupportInfoRepository extends JpaRepository<SupportInfo,Integer>,JpaSpecificationExecutor<SupportInfo> {

    @Query(value = " select * from support_info si where si.id = ?1 ",nativeQuery = true)
    SupportInfo findSupportInfoById(Integer id);

    @Query(value = " select s.number as number from support_info s where s.id = (select max(si.id) as id from support_info si) ",nativeQuery = true)
    String select();
}
