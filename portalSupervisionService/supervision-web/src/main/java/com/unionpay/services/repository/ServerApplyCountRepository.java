package com.unionpay.services.repository;

import com.unionpay.services.entity.ServerApplyCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ServerApplyCountRepository extends JpaRepository<ServerApplyCount, BigInteger>, JpaSpecificationExecutor<ServerApplyCount> {
}

