package com.unionpay.sms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unionpay.sms.domain.smsReceiver;
/**
 * 
 * @author wangyue
 * @date 2018-12-7
 * 短信发送_接收人
 */
@Repository
public interface SmsReceiverRepository extends JpaRepository<smsReceiver, String>{
   List<smsReceiver> findByMatterUnid(String unid);

   /**
    * 根据matterUnid查询短信接收人电话
    * @param matterUnid
    * @return
    */
   @Query(value = "select sm.receiver_tel from  sms_receiver sm where sm.matter_unid = ?1",nativeQuery = true)
   List<String> findTelByMatterUnid(String matterUnid);

   /**
    * 查询接收人总数
    * @return
    *
    * @author lishuang
    * @date 2018-12-26
    */
   @Query(value = "select count(*) FROM sms_receiver sm where sm.matter_unid = ?1",nativeQuery = true)
   Integer findTotalByMatterUnid(String unid);
}
