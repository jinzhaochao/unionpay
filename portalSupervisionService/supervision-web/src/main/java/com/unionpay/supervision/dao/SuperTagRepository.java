package com.unionpay.supervision.dao;

import com.unionpay.supervision.domain.SuperTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperTagRepository extends JpaRepository<SuperTag,Integer>,JpaSpecificationExecutor<SuperTag> {
    SuperTag findByTagContent(String tagContent);
}
