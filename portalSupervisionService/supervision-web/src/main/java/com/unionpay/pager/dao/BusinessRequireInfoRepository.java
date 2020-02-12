package com.unionpay.pager.dao;

import com.unionpay.pager.domain.BusinessRequireInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
/**
 * @description: 商户入网需求信息  DAO接口
 * @author: lishuang
 * @date: 2019/12/04
 */
@Repository
public interface BusinessRequireInfoRepository extends JpaRepository<BusinessRequireInfo,Integer>, JpaSpecificationExecutor<BusinessRequireInfo> {
}
