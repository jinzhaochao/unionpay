package com.unionpay.services.repository;

import com.unionpay.services.entity.ServerApplyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ServerApplyUserRepository extends JpaRepository<ServerApplyUser, BigInteger>, JpaSpecificationExecutor<ServerApplyUser> {
        }
