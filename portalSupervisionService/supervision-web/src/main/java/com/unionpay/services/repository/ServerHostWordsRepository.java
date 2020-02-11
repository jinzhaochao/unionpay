package com.unionpay.services.repository;

import com.unionpay.services.model.HostWordsModel;
import com.unionpay.services.model.ServerHostWords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/18/018 18:37
 * @Description:
 */
@Repository
public interface ServerHostWordsRepository extends JpaRepository<ServerHostWords,Integer>,JpaSpecificationExecutor<ServerHostWords> {
    ServerHostWords findByWords(String words);
    @Query(value = "SELECT sh.words,sh.frequency,sh.status from server_host_words where sh.id in ?1 ",nativeQuery = true)
    List<HostWordsModel> findByIdIn(List<String> strings);
}
