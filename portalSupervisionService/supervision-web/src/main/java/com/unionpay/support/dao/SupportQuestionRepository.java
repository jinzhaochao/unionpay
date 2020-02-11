package com.unionpay.support.dao;

import com.unionpay.support.pojo.SupportQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/11/04 16:59
 * @Description:
 */

@Repository
public interface SupportQuestionRepository extends JpaRepository<SupportQuestion,Integer>,JpaSpecificationExecutor<SupportQuestion> {

    @Query(value = " select sq.id,sq.type_id as typeId,sq.type_name as typeName,sq.question_name as questionName,sq.content,sq.url from support_question sq where sq.type_id = ?1 ",nativeQuery = true)
    List<SupportQuestion> findByTypeId(String id);
}
