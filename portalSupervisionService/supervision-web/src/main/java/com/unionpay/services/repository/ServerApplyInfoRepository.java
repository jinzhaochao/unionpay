package com.unionpay.services.repository;

import com.unionpay.services.entity.ServerApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ServerApplyInfoRepository extends JpaRepository<ServerApplyInfo, BigInteger>, JpaSpecificationExecutor<ServerApplyInfo> {
    /**
     * 查询指定时间段内已完成流程信息时效
     * @param launchTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Query(value = "SELECT TIMESTAMPDIFF(DAY,launch_time,end_time) as diff FROM server_apply_info WHERE launch_time>= ?1 and end_time <=?2",nativeQuery = true)
    public List<BigInteger> findAllDiff(String launchTime,String endTime);

    /**
     * 根据满意度（1满意；0不满意）情况，查询指定时间段内已完成流程个数
     * @param score
     * @param launchTime
     * @param endTime
     * @return
     */
    @Query(value = "SELECT COUNT(id) FROM server_apply_info WHERE score = ?1 and launch_time>= ?2 and end_time <=?3",nativeQuery = true)
    public int findByScore(Integer score,String launchTime,String endTime);

    public ServerApplyInfo findByProcessIdAndAndBizcode(String processid,String bizcode);
}
