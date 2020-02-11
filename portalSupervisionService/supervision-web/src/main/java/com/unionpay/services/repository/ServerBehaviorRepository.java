package com.unionpay.services.repository;

import com.unionpay.services.entity.ServerBehavior;
import com.unionpay.services.model.BehaviorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServerBehaviorRepository extends JpaRepository<ServerBehavior,Integer>, JpaSpecificationExecutor<ServerBehavior> {
    public List<BehaviorModel> getByDictIdOrderByCreateTime(Integer dicId);
}
