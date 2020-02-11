package com.unionpay.supervision.serviceImpl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unionpay.common.util.Cal26ToNumUntil;
import com.unionpay.common.util.DateUtil;
import com.unionpay.supervision.dao.SuperServiceIdCreateRepository;
import com.unionpay.supervision.dao.SuperTypeOverseeRepository;
import com.unionpay.supervision.domain.SuperServiceIdCreate;
import com.unionpay.supervision.domain.SuperTypeOversee;
import com.unionpay.supervision.service.SuperServiceIdCreateService;

@Service
@Transactional
public class SuperServiceIdCreateServiceImpl implements SuperServiceIdCreateService{
	
	@Autowired
    private SuperServiceIdCreateRepository superServiceIdCreateRepository;
	@Autowired
    private SuperTypeOverseeRepository superTypeOverseeRepository;
	
	/**
	 * 生成SuperServiceId
	*/
	@Override
	public String geSuperServiceId(String overseeTypeId, Integer year) {
		String superServiceId = null;
		// 查询目前的ServiceID
		SuperServiceIdCreate superServiceIdCreate= superServiceIdCreateRepository.findByOverseeTypeIdAndYear(overseeTypeId, year);
		if(superServiceIdCreate == null){
			//当前未有记录
			superServiceIdCreate = new SuperServiceIdCreate();
			superServiceIdCreate.setId(UUID.randomUUID().toString());
			superServiceIdCreate.setCreateTime(DateUtil.getTime());
			superServiceIdCreate.setOverseeTypeId(overseeTypeId);
			superServiceIdCreate.setYear(year);
			superServiceIdCreate.setSort(1);
			superServiceIdCreateRepository.save(superServiceIdCreate);
			
		}else{
			//有记录，更新
			Integer sort = superServiceIdCreate.getSort();
			sort += 1;
			superServiceIdCreate.setSort(sort);
			superServiceIdCreate.setUpdateTime(DateUtil.getTime());
			superServiceIdCreateRepository.save(superServiceIdCreate);
		}

		//组合成superServiceId
		superServiceId = superServiceIdCreate.getOverseeTypeId() + "-" + superServiceIdCreate.getYear() + 
				"-" + Cal26ToNumUntil.getSort(superServiceIdCreate.getSort());
		return superServiceId;
	}

	@Override
	public String geSuperServiceId(String uind) {
		String serviceId = null;
		SuperTypeOversee superTypeOversee = superTypeOverseeRepository.findByUnid(uind);
    	if(superTypeOversee != null){
    		//生成
    		String overseeTypeId = superTypeOversee.getTypeId();
    		SimpleDateFormat ft = new SimpleDateFormat ("yyyy");
    		String year = ft.format(new Date());
    		Integer yearTo = Integer.parseInt(year.substring(2));
    		serviceId = geSuperServiceId(overseeTypeId, yearTo);
    	}
    	return serviceId;
	}
	
	


}
