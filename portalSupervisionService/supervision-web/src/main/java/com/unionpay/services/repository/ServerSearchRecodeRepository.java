package com.unionpay.services.repository;

import com.unionpay.services.model.ServerSearchRecode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/20/020 10:34
 * @Description:
 */
@Repository
public interface ServerSearchRecodeRepository extends JpaRepository<ServerSearchRecode,Integer>,JpaSpecificationExecutor<ServerSearchRecode> {
}
