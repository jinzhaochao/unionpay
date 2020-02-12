package com.unionpay.services.repository;


import com.unionpay.services.model.ServerGiveLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerGiveLikeRepository extends JpaRepository<ServerGiveLike,Long>,JpaSpecificationExecutor<ServerGiveLike> {

//    @Query(value = "select s.id,s.suggestion_id as suggestionId,s.user_id as userId,s.status from server_give_like s where s.suggestion_id = ?1 and s.user_id = ?2 ", nativeQuery = true)
    ServerGiveLike findAllBySuggestionIdAndUserId(Integer suggestionId, String userId);
}
