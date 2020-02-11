package com.unionpay.supervision.dao;

import com.unionpay.supervision.domain.SuperLcSponsorAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperLcSponsorAgentRepository extends JpaRepository<SuperLcSponsorAgent,Integer> {
}
