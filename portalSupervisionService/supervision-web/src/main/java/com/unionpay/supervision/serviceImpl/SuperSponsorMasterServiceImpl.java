package com.unionpay.supervision.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperSponsorMasterRepository;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.domain.SuperSponsorMaster;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.supervision.service.SuperSponsorMasterService;

/**
 * <p>
 * 督办部门关联督办人实现类
 * </p>
 *
 * @author xiongym
 * @since 2019-4-10
 */
@Service
@Transactional
public class SuperSponsorMasterServiceImpl implements SuperSponsorMasterService{
	
	@Autowired
	private SuperSponsorMasterRepository superSponsorMasterRepository;
	@Autowired
	private OmUserService omUserService;
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void add(SuperSponsorMaster superSponsorMaster){
		superSponsorMasterRepository.save(superSponsorMaster);
	}
	
	@Override
	@Transactional
	public void saveorUpdate(SuperSponsor sponsor,String sponsorIds){
		if(sponsor == null || ToolUtil.isEmpty(sponsorIds)){
			return ;
		}
		//先注销
		String sponsorUnid = sponsor.getUnid();
		updateStatus(sponsorUnid);
		String[] sponsorIdArray = sponsorIds.split(",");
		List<SuperSponsorMaster> masterlist = new ArrayList<>();
		for(String sponsorId : sponsorIdArray){
			SuperSponsorMaster select = superSponsorMasterRepository.findBySponsorUnidAndSponsorId(sponsorUnid,sponsorId);
			if(select == null){
				OmUser user = omUserService.findByUserid(sponsorId);
				SuperSponsorMaster superSponsorMaster = new SuperSponsorMaster();
				superSponsorMaster.setServiceUnid(sponsor.getServiceUnid());
				superSponsorMaster.setSponsorUnid(sponsorUnid);
				superSponsorMaster.setSponsorId(sponsorId);
				superSponsorMaster.setOrgName(sponsor.getOrgName());
				if(user != null){
					superSponsorMaster.setSponsorName(user.getEmpname());
					superSponsorMaster.setOffice_Id(user.getDeptorgid());
					superSponsorMaster.setOrgId(user.getOrgid());
				}
				superSponsorMaster.setStatus(1);
				superSponsorMaster.setCreateTime(new Date());
				masterlist.add(superSponsorMaster);
			}else{
				select.setStatus(1);
				//superSponsorMasterRepository.save(select);
				masterlist.add(select);
			}
		}
		if(masterlist.size() > 0 ){
			superSponsorMasterRepository.saveAll(masterlist);
		}
	}
	
	
	
	@Transactional
	public void updateStatus(String sponsorUnid) {
		String sql = null;
		if (ToolUtil.isNotEmpty(sponsorUnid)) {
			sql = "UPDATE super_sponsor_master SET `status` = 0 WHERE sponsor_unid = :sponsorUnid AND `status` = 1";
			Query query = entityManager.createNativeQuery(sql);
			query.setParameter("sponsorUnid", sponsorUnid);
			query.executeUpdate();
		}
	}

}
