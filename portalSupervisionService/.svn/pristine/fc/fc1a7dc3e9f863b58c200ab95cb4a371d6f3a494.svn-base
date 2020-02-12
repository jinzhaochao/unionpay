package com.unionpay.pager.dao;


import com.unionpay.pager.domain.PagerApplication;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Parameter;
import java.util.List;

@Repository
public interface PagerApplicationRepository extends JpaRepository<PagerApplication,Integer>,JpaSpecificationExecutor<PagerApplication> {

    //查询某个页签下所有启用的所有应用
    List<PagerApplication> findByTagIdAndStatusOrderBySort( int tagId, int status);

    //应用下所有的子应用
    List<PagerApplication> findAllByParentIdAndStatus(int parent_id,int status);

    List<PagerApplication> findAllByApplicationIdIn(List<Integer> applicationIds);

    PagerApplication findByApplicationId(int applicationId);

    List<PagerApplication> findAllByParentIdAndStatusIsNotAndTagIdOrderBySort(Integer parentId,int status,Integer tagId);

    List<PagerApplication> findAllByApplicationIdIsNotInAndParentId(List<Integer> applicationIds,Integer applicationId);

    void deleteAllByParentId(Integer parentId);

    List<PagerApplication> findAllByParentId(int applicationId);

//    @Query("select * from pager_application left join pager_tage on pager_application.tag_id = pager_tag.tag_id where pager_application.tag_id = #{tagId}  ")
    List<PagerApplication> findAllByTagId(Integer tagId);
}
