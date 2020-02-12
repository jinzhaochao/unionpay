package com.unionpay.supervision.dao;

import com.unionpay.supervision.domain.SuperService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 * 事项表
 */
@Repository
public interface SuperServiceRepository extends JpaRepository<SuperService, String> {
    SuperService findByUnid(String unid);
    List<SuperService> findAll();

}
