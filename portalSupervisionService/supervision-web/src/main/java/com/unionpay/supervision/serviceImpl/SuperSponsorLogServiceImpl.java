package com.unionpay.supervision.serviceImpl;

import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperSponsorLogRepository;
import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.domain.SuperSponsorLog;
import com.unionpay.supervision.model.SponsorLogList;
import com.unionpay.supervision.service.SuperSponsorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 督办部门历史记录实现类
 * </p>
 *
 * @author xiongym
 * @since 2018-11-15
 */
@Service
@Transactional
public class SuperSponsorLogServiceImpl implements SuperSponsorLogService {

	@Autowired
	private SuperSponsorLogRepository superSponsorLogRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public  LinkedList<SuperSponsorLog> findBySponsorUnid(String sponsorUnid) {
		return superSponsorLogRepository.findBySponsorUnidAndStatusOrderByCreateTimeDesc(sponsorUnid,1);
	}

	@Override
	public SuperSponsorLog findById(String unid) {
		return superSponsorLogRepository.findByUnid(unid);
	}

	/**
	 * 督办部门历史信息记录
	 */
	@Override
	@Transactional
	public SuperSponsorLog add(SuperSponsor superSponsor) {
		SuperSponsorLog superSponsorLog = null;
		if (superSponsor != null) {
			superSponsorLog = new SuperSponsorLog();
			superSponsorLog.setUnid(UUID.randomUUID().toString());
			superSponsorLog.setSponsorId(superSponsor.getSponsorId());
			superSponsorLog.setSponsorUnid(superSponsor.getUnid());
			superSponsorLog.setCreateTime(DateUtil.getTime());
			superSponsorLog.setCreateUserid(superSponsor.getCreateUserid());
			superSponsorLog.setCreateUsername(superSponsor.getCreateUsername());
			superSponsorLog.setFeedbackRule(superSponsor.getFeedbackRule());
			superSponsorLog.setFeedbackTime(superSponsor.getFeedbackTime());
			superSponsorLog.setFeedbackDeadline(superSponsor.getFeedbackDeadline());
			if (ToolUtil.isNotEmpty(superSponsorLog.getFeedbackTime())
					&& ToolUtil.isNotEmpty(superSponsorLog.getFeedbackDeadline())) {
                   //反馈效率
				Long day = DateUtil.getDaySub(superSponsorLog.getFeedbackDeadline(), superSponsorLog.getFeedbackTime());
				superSponsorLog.setFeedbackEfficiency(Integer.parseInt(day.toString()));
			}
			// superSponsorLog.setFeedbackEfficiency(superSponsor.getf);
			superSponsorLog.setNote(superSponsor.getNote());
			superSponsorLog.setOverseeTime(superSponsor.getOverseeTime());
			superSponsorLog.setProgress(superSponsor.getProgress());
			superSponsorLog.setSponsorName(superSponsor.getSponsorName());
			superSponsorLog.setStatus(1);
			superSponsorLog.setServiceStatus(superSponsor.getServiceStatus());
			superSponsorLog.setWorkStatus(superSponsor.getWorkStatus());
			superSponsorLog.setNeedVerify(superSponsor.getNeedVerify());
			superSponsorLog.setVerifiers(superSponsor.getVerifiers());
			//新增信息反馈人--lishuang 2019-08-21
			superSponsorLog.setFeedbackUserid(superSponsor.getFeedbackUserid());
			superSponsorLog.setFeedbackUsername(superSponsor.getFeedbackUsername());
			superSponsorLogRepository.save(superSponsorLog);

		}
		return superSponsorLog;

	}

	@Override
	public void add(SuperSponsorLog superSponsorLog) {
		superSponsorLogRepository.save(superSponsorLog);
	}

	@Override
	public void edit(SuperSponsorLog superSponsorLog) {
		superSponsorLogRepository.save(superSponsorLog);

	}

	@Override
	public void delete(String unid) {
		superSponsorLogRepository.deleteById(unid);
	}

	@Override
	public void updateStatus(String sponsorUnid) {
		String sql = null;
		if (ToolUtil.isNotEmpty(sponsorUnid)) {
			sql = "UPDATE super_sponsor_log SET `status` = 0 WHERE sponsor_unid = :sponsorUnid AND `status` = 1";
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("sponsorUnid", sponsorUnid);
			query.executeUpdate();
		}
	}

	/**
	 * 督办历史记录合并
	 */
	@Override
	@Transactional
	public void combine(String removeSponsorUnid,String tagerSponsorUnid) {
		if(ToolUtil.isEmpty(removeSponsorUnid) || ToolUtil.isEmpty(tagerSponsorUnid)){
			return ;
		}
		String sql = "UPDATE super_sponsor_log SET sponsor_unid = :tagerSponsorUnid WHERE sponsor_unid = :removeSponsorUnid AND `status` = 1";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("tagerSponsorUnid", tagerSponsorUnid);
		query.setParameter("removeSponsorUnid", removeSponsorUnid);
		query.executeUpdate();
	}



}
