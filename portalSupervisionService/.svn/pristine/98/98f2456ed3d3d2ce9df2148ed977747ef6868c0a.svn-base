package com.unionpay.services.repository;

import com.unionpay.services.model.ComplaintModel;
import com.unionpay.services.model.ServerSuggest;
import com.unionpay.services.model.ServerSuggestModel;
import com.unionpay.services.model.SuggestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/18/018 19:05
 * @Description:
 */
@Repository
public interface ServerSuggestRepository extends JpaRepository<ServerSuggest,Integer>,JpaSpecificationExecutor<ServerSuggest> {
    @Query(value = "select ss.* from server_suggest ss where ss.id = ?1", nativeQuery = true)
    ServerSuggest findServerSuggestById(Integer id);



    @Query(value = "select ss.title,ss.otherDescribe,ss.type,ss.empName,ss.empDeptName,ss.createtime,ss.replyName,ss.replyTime from server_suggest where ss.id in ?1", nativeQuery = true)
    List<SuggestModel> findByIdIn(List<String> strings);

    @Query(value = "select ss.title,ss.otherDescribe,ss.type,ss.empName,ss.empDeptName,ss.createtime,ss.replyName,ss.replyTime from server_suggest ss where ss.id = ?1", nativeQuery = true)
    ComplaintModel getComplaintById(int i);
}
