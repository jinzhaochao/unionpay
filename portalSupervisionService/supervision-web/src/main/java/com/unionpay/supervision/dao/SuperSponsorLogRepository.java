package com.unionpay.supervision.dao;

import com.unionpay.supervision.domain.SuperSponsorLog;
import com.unionpay.supervision.model.SponsorLogList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xiongym 督办部门历史记录表
 */
@Repository
public interface SuperSponsorLogRepository extends JpaRepository<SuperSponsorLog, String> {

	List<SuperSponsorLog> findBySponsorUnid(String sponsorUnid);

	SuperSponsorLog findByUnid(String unid);

	LinkedList<SuperSponsorLog> findBySponsorUnidAndStatusOrderByCreateTimeDesc(String sponsorUnid, int status);

    List<SuperSponsorLog> findBySponsorUnidOrderByCreateTimeDesc(String unid);

}
