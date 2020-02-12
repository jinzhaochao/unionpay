package com.unionpay.supervision.serviceImpl;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.PingYinUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperFileRepository;
import com.unionpay.supervision.dao.SuperServiceOverseeMappingRepository;
import com.unionpay.supervision.dao.SuperTypeOverseeRepository;
import com.unionpay.supervision.domain.SuperFile;
import com.unionpay.supervision.domain.SuperService;
import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.domain.SuperTypeOversee;
import com.unionpay.supervision.model.OverseeMappingDto;
import com.unionpay.supervision.service.SuperServiceService;
import com.unionpay.supervision.service.SuperTypeOverseeService;

/**
 * 
 * @author wangyue 督办类型的实现类
 */
@Service
public class SuperTypeOverseeServiceImpl implements SuperTypeOverseeService {

	@Autowired
	private SuperTypeOverseeRepository superTypeOverseeRepository;

	@Autowired
	private SuperServiceOverseeMappingRepository superServiceOverseeMappingRepository;

	@Autowired
	private SuperFileRepository superFileRepository;

	@Autowired
	private SuperServiceService superServiceService;

	@Override
	public SuperTypeOversee SuperTypeOverseeInsert(SuperTypeOversee SuperTypeOversee) {
		SuperTypeOversee superType = null;
		// 获取TypeID 与sort
		SuperTypeOversee select = findOneOrderByTypeSort();
		if (select != null) {
			Integer sort = select.getTypeSort();
			SuperTypeOversee.setTypeSort(sort + 1);
			//String typeId = PingYinUtil.getPYIndexStr(SuperTypeOversee.getTypeName(), true);
			//由页面输入两位大写字母typeId--新需求-0403 lishuang
			String typeId = SuperTypeOversee.getTypeId().toUpperCase();//防止输入小写字母
			SuperTypeOversee.setTypeId(typeId);
			SuperTypeOversee.setIsSelfDefine(1);
			Integer id = Integer.parseInt(select.getUnid()) + 1;
			SuperTypeOversee.setCreateTime(DateUtil.getTime());
			SuperTypeOversee.setUnid(id.toString());
			superTypeOverseeRepository.save(SuperTypeOversee);
			return SuperTypeOversee;
		}
		return superType;
	}

	@Override
	public void DeleteSuperTypeOversee(SuperTypeOversee SuperTypeOversee) {

		superTypeOverseeRepository.delete(SuperTypeOversee);
	}

	@Override
	public void UpdateSuperTypeOverseeUpdate(SuperTypeOversee SuperTypeOversee) {

		superTypeOverseeRepository.save(SuperTypeOversee);
	}

	@Override
	public List<SuperTypeOversee> SuperTypeOverseeFind() {

		return superTypeOverseeRepository.findAll();
	}

	public SuperTypeOversee getFindById(SuperTypeOversee SuperTypeOversee) {
		return superTypeOverseeRepository.findByUnid(SuperTypeOversee.getUnid());
	}

	/**
	 * 查询最近督办类型 xiongym
	 */
	@Override
	public SuperTypeOversee findOneOrderByTypeSort() {
		return superTypeOverseeRepository.findFirstByOrderByTypeSortDesc();
	}

	/**
	 * 督办类型的按名称查找
	 * 
	 * @param typeName
	 * @return
	 */
	@Override
	public List<SuperTypeOversee> findByTypeName(String typeName) {
		return superTypeOverseeRepository.findByTypeName(typeName);
	}

	/**
	 * 督办类型的按编码查找
	 * @param typeId
	 * @return
	 * @author lishuang
	 * @date 2019-04-03
	 */
	public List<SuperTypeOversee> findByTypeId(String typeId){
		return superTypeOverseeRepository.findByTypeId(typeId);
	}

	/**
	 * 新增督办类型
	 * 
	 * @param unid
	 * @param typeName
	 * @return
	 */
	public SuperServiceOverseeMapping SuperTypeOverseeNewInsert(String unid, String typeName) {
		SuperServiceOverseeMapping superServiceOverseeMapping = null;
		SuperTypeOversee superTypeOversee = null;
		SuperService superService = superServiceService.findByUnid(unid);
		if (unid != null && typeName != null) {
			superTypeOversee = new SuperTypeOversee();
			superTypeOversee.setTypeName(typeName);
			// 查询是否重复新增
			List<SuperTypeOversee> list = findByTypeName(typeName);
			if (list != null && list.size() > 0) {
				superTypeOversee = list.get(0);
			} else {
				// 督办类型的新增；
				superTypeOversee = SuperTypeOverseeInsert(superTypeOversee);
			}
			// 督办映射表的新增
			superServiceOverseeMapping = new SuperServiceOverseeMapping();
			superServiceOverseeMapping.setUnid(UUID.randomUUID().toString());
			superServiceOverseeMapping.setServiceUnid(unid);
			superServiceOverseeMapping.setOverseeUnid(superTypeOversee.getUnid());
			superServiceOverseeMapping.setOverseeName(typeName);
			superServiceOverseeMapping.setCreateTime(DateUtil.getTime());
			// 事项名称
			superServiceOverseeMapping.setServiceName(superService.getServiceName());
			// 批文来源
			superServiceOverseeMapping.setCommandSource(superService.getCommandSource());
			// 来源领导
			superServiceOverseeMapping.setCommandLeader(superService.getCommandLeader());
			// 任务要求
			superServiceOverseeMapping.setTaskNote(superService.getNote());
			superServiceOverseeMapping.setServiceTime(superService.getServiceTime());
			// 事项时间
			superServiceOverseeMapping.setIsPrimary("N");
			superServiceOverseeMapping.setStatus(1);
			superServiceOverseeMappingRepository.save(superServiceOverseeMapping);
			return superServiceOverseeMapping;

		}
		return null;

	}

	/**
	 * 新增任务来源
	 */
	public void addTaskSource(OverseeMappingDto overseeMappingDto) {
		SuperServiceOverseeMapping superServiceOverseeMapping = null;
		SuperService superService = superServiceService.findByUnid(overseeMappingDto.getServiceUnid());
		if (superService != null) {
			// 督办映射表的新增
			superServiceOverseeMapping = new SuperServiceOverseeMapping();
			superServiceOverseeMapping.setUnid(UUID.randomUUID().toString());
			superServiceOverseeMapping.setServiceUnid(overseeMappingDto.getServiceUnid());
			superServiceOverseeMapping.setOverseeUnid(overseeMappingDto.getOverseeUnid());
			superServiceOverseeMapping.setOverseeName(overseeMappingDto.getOverseeName());
			superServiceOverseeMapping.setCreateTime(DateUtil.getTime());
			// 事项名称
			superServiceOverseeMapping.setServiceName(overseeMappingDto.getServiceName());
			// 任务要求
			superServiceOverseeMapping.setTaskNote(overseeMappingDto.getTaskNote());
			// 事项时间
			superServiceOverseeMapping.setServiceTime(overseeMappingDto.getServiceTime());
			// 附件
			superServiceOverseeMapping.setFileId(overseeMappingDto.getFileId());
			if ("Y".equals(overseeMappingDto.getIsPrimary())) {
				superServiceOverseeMapping.setIsPrimary(overseeMappingDto.getIsPrimary());
			} else {
				superServiceOverseeMapping.setIsPrimary("N");
			}
			//公司批示领导
			superServiceOverseeMapping.setCommandLeader(overseeMappingDto.getCommandLeader());
			//批示文件来源
			superServiceOverseeMapping.setCommandSource(overseeMappingDto.getCommandSource());


			superServiceOverseeMapping.setStatus(1);
			superServiceOverseeMappingRepository.save(superServiceOverseeMapping);

			// 附件变更
			String fileId = superServiceOverseeMapping.getFileId();
			if(ToolUtil.isNotEmpty(fileId)){
				SuperFile superFile = superFileRepository.findByFileId(fileId);
				if (superFile != null) {
					superFile.setSuperServiceId(superService.getUnid());
					superFile.setServiceName(superServiceOverseeMapping.getServiceName());
					superFile.setOverseeMappingId(superServiceOverseeMapping.getUnid());
					superFile.setIsUse(1);
					superFileRepository.save(superFile);
				}
			}
		}

	}
}
