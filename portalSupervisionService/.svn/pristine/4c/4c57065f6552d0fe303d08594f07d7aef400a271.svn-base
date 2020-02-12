package com.unionpay.pager.dao;


import com.unionpay.pager.domain.PagerTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PagerTagRepository extends JpaRepository<PagerTag,Integer>,JpaSpecificationExecutor<PagerTag> {

    PagerTag findAllByTagIdAndStatus(Integer tagId,Integer status);

    void deleteByTagId(Integer tagId);
}
