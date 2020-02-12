package com.unionpay.support.dao;


import com.unionpay.support.pojo.SupportTimeNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */

@Repository
public interface SupportTimeNumRepository extends JpaRepository<SupportTimeNum,Integer>,JpaSpecificationExecutor<SupportTimeNum> {

    @Query(value = " select st.* from support_time_num st where st.place_name = ?1 ",nativeQuery = true)
    List<SupportTimeNum> findByPlaceName(String myPlace);

    @Query(value = " select st.* from support_time_num st where st.place_name = ?1 order by st.sort",nativeQuery = true)
    List<SupportTimeNum> findByPlaceNameAndExceptedTime(String myPlace);
}
