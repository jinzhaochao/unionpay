package com.unionpay.supervision.dao;

import com.unionpay.supervision.domain.SuperSponsorTag;
import com.unionpay.supervision.domain.SuperTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperSponsorTagRepository extends JpaRepository<SuperSponsorTag,Integer> {
}
