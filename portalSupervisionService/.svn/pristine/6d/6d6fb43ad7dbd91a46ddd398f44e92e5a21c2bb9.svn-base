package com.unionpay.supervision.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import com.unionpay.supervision.domain.SuperTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unionpay.common.exception.MyException;
import com.unionpay.common.util.DateUtil;
import com.unionpay.supervision.dao.SuperTypeServiceRepository;
import com.unionpay.supervision.service.SuperTypeServiceService;

/**
 * 事项类型表实现类
 * 
*/
@Service
@Transactional
public class SuperTypeServiceServiceImpl implements SuperTypeServiceService {
	
	@Autowired
	private SuperTypeServiceRepository superTypeServiceRepository;

	@Override
	public List<SuperTypeService> findAll() {
		return superTypeServiceRepository.findAll();
	}

	@Override
	public SuperTypeService findByUnid(String unid) {
		return superTypeServiceRepository.findByUnid(unid);
	}

	@Override
	public SuperTypeService findByTypeName(String typeName) {
		return superTypeServiceRepository.findByTypeName(typeName);
	}

	@Override
	public SuperTypeService add(String typeName) {
		synchronized(this){
			SuperTypeService lastest = findlastest();
			if(lastest == null){
				new MyException("事项类型表查询为空");
			}
			Integer unid = Integer.parseInt(lastest.getUnid()) + 1;
			Integer sort = lastest.getTypeSort() + 1;
			SuperTypeService typeService = new SuperTypeService();
			typeService.setUnid(unid.toString());
			typeService.setTypeId(sort.toString());
			typeService.setTypeName(typeName);
			typeService.setTypeSort(sort);
			typeService.setCreateTime(DateUtil.getTime());
			return superTypeServiceRepository.save(typeService);
		}
	}

	@Override
	public void delete(String unid) {
		superTypeServiceRepository.deleteById(unid);
	}
	
	public SuperTypeService findlastest(){
		SuperTypeService superType = null;
		List<SuperTypeService> superTypelist = superTypeServiceRepository.OrderByTypeSortDesc();
		if(superTypelist != null && superTypelist.size() > 0 ){
			superType = superTypelist.get(0);
		}
		return superType;
	}

}
