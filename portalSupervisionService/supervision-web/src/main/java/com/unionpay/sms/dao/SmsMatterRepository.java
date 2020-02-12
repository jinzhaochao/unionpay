package com.unionpay.sms.dao;

import java.util.List;

import com.unionpay.sms.domain.smsMatter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author wangyue
 * @date 2018-12-7
 * 短信发送_申请
 */
@Repository
public interface SmsMatterRepository extends JpaRepository<smsMatter, String> {
  @Query(value="select count(*) from sms_matter;",nativeQuery=true)
  Integer findSmsMatterTotal();

  /**
   * 根据unid查询短信详情
   * @param unid
   * @return
   */
  public smsMatter findByUnid(String unid);

  @Query(value = "select sm.unid from sms_matter sm",nativeQuery = true)
  List<String> findAllUnid();

}
