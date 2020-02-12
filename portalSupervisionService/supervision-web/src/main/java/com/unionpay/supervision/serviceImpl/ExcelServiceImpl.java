package com.unionpay.supervision.serviceImpl;

import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.util.Cal26ToNumUntil;
import com.unionpay.common.util.ExcelReadUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.bussniss.LiuchengOperator;
import com.unionpay.supervision.domain.*;
import com.unionpay.supervision.service.*;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ExcelServiceImpl implements ExcelService {

	@Autowired
	private SuperServiceService superServiceService;
	@Autowired
	private SuperSponsorService superSponsorService;
	@Autowired
	private SuperTypeServiceService superTypeServiceService;
	@Autowired
	private SuperServiceIdCreateService superServiceIdCreateService;
	@Autowired
	private SuperServiceOverseeMappingService superServiceOverseeMappingService;
	@Autowired
	private LiuchengOperator liuchengOperator;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public Map<String, String> orgNameMap;

	/**
	 * 会议议定事项
	 */
	@Override
	@Transactional
	public void importServiceAndSponsorForMeeting(Row row, OmUser omUser, SuperTypeOversee superTypeOversee) {
		// 事项名称(批示文件名称,出访活动,会议名称)
		String serviceName = ExcelReadUtil.convertToString(row.getCell(0));
		// 任务要求(任务要求,批示内容,相关指示,部署内容)
		String taskNote = ExcelReadUtil.convertToString(row.getCell(1));
		// 备注
		String note = ExcelReadUtil.convertToString(row.getCell(2));
		// 事项时间(会议时间,批示时间,出访时间)
		if (ToolUtil.isEmpty(row.getCell(3))){
			throw new BussinessException(501,"会议时间不能为空");
		}
		Date serviceTime = row.getCell(3).getDateCellValue();
		// 事项类型
		String serviceType = ExcelReadUtil.convertToString(row.getCell(4));
		// 分管领导(多选)
		String branchedLeader = ExcelReadUtil.convertToString(row.getCell(5));
		// 督办频次(X周)
		Integer overseeFrequency = ExcelReadUtil.convertToInteger(row.getCell(6));
		// 主办部门名称
		String orgName = ExcelReadUtil.convertToString(row.getCell(7));
		// 是否阅知件(是:Y;否:N)
		String isRead = ExcelReadUtil.convertToString(row.getCell(8));
		// 开始工作时间
		if (ToolUtil.isEmpty(row.getCell(9))){
			throw new BussinessException(501,"立项反馈截止期限不能为空");
		}
		Date approvalDeadline = row.getCell(9).getDateCellValue();

		add(serviceName, null, null, taskNote, note, serviceTime, branchedLeader, overseeFrequency,
				serviceType, orgName, isRead, approvalDeadline, omUser, superTypeOversee);
	}

	/**
	 * 公司领导批示
	 */
	@Override
	@Transactional
	public void importServiceAndSponsorForWritten(Row row, OmUser omUser, SuperTypeOversee superTypeOversee) {
		// 批文名称
		String serviceName = ExcelReadUtil.convertToString(row.getCell(0));
		//批文来源
		String commandSource = ExcelReadUtil.convertToString(row.getCell(1));
		//批示领导
		String commandLeader = ExcelReadUtil.convertToString(row.getCell(2));
		// 任务要求(任务要求,批示内容,相关指示,部署内容)
		String taskNote = ExcelReadUtil.convertToString(row.getCell(3));
		// 备注
		String note = ExcelReadUtil.convertToString(row.getCell(4));
		// 事项时间(会议时间,批示时间,出访时间)
		if (ToolUtil.isEmpty(row.getCell(5))){
			throw new BussinessException(501,"批示时间不能为空");
		}
		Date serviceTime = row.getCell(5).getDateCellValue();
		// 事项类型
		String serviceType = ExcelReadUtil.convertToString(row.getCell(6));
		// 分管领导(多选)
		String branchedLeader = ExcelReadUtil.convertToString(row.getCell(7));
		// 督办频次(X周)
		Integer overseeFrequency = ExcelReadUtil.convertToInteger(row.getCell(8));
		// 主办部门名称
		String orgName = ExcelReadUtil.convertToString(row.getCell(9));
		// 是否阅知件(是:Y;否:N)
		String isRead = ExcelReadUtil.convertToString(row.getCell(10));
		// 立项反馈截止期限
		if (ToolUtil.isEmpty(row.getCell(11))){
			throw new BussinessException(501,"立项反馈截止期限不能为空");
		}
		Date approvalDeadline = row.getCell(11).getDateCellValue();

		add(serviceName, commandLeader, commandSource, taskNote, note, serviceTime, branchedLeader, overseeFrequency,
				serviceType, orgName, isRead, approvalDeadline, omUser, superTypeOversee);
	}

	/**
	 * 公司领导境外出访布置工作
	 */
	@Override
	@Transactional
	public void importServiceAndSponsorForAssign(Row row, OmUser omUser, SuperTypeOversee superTypeOversee) {
		// 事项名称(批示文件名称,出访活动,会议名称)
		String serviceName = ExcelReadUtil.convertToString(row.getCell(0));
		//出访领导
		String commandLeader = ExcelReadUtil.convertToString(row.getCell(1));
		// 任务要求(任务要求,批示内容,相关指示,部署内容)
		String taskNote = ExcelReadUtil.convertToString(row.getCell(2));
		// 备注
		String note = ExcelReadUtil.convertToString(row.getCell(3));
		// 事项时间(会议时间,批示时间,出访时间)
		if (ToolUtil.isEmpty(row.getCell(4))){
			throw new BussinessException(501,"出访时间不能为空");
		}
		Date serviceTime = row.getCell(4).getDateCellValue();
		// 事项类型
		String serviceType = ExcelReadUtil.convertToString(row.getCell(5));
		// 分管领导(多选)
		String branchedLeader = ExcelReadUtil.convertToString(row.getCell(6));
		// 督办频次(X周)
		Integer overseeFrequency = ExcelReadUtil.convertToInteger(row.getCell(7));
		// 主办部门名称
		String orgName = ExcelReadUtil.convertToString(row.getCell(8));
		// 是否阅知件(是:Y;否:N)
		String isRead = ExcelReadUtil.convertToString(row.getCell(9));
		// 开始工作时间
		if (ToolUtil.isEmpty(row.getCell(10))){
			throw new BussinessException(501,"立项反馈截止期限不能为空");
		}
		Date approvalDeadline = row.getCell(10).getDateCellValue();

		add(serviceName, commandLeader, null, taskNote, note, serviceTime, branchedLeader, overseeFrequency,
				serviceType, orgName, isRead, approvalDeadline, omUser, superTypeOversee);

	}

	/**
	 * 自定义
	 */
	@Override
	@Transactional
	public void importServiceAndSponsorForDefined(Row row, OmUser omUser, SuperTypeOversee superTypeOversee) {
		// 事项名称(批示文件名称,出访活动,会议名称)
		String serviceName = ExcelReadUtil.convertToString(row.getCell(0));
		// 任务要求(任务要求,批示内容,相关指示,部署内容)
		String taskNote = ExcelReadUtil.convertToString(row.getCell(1));
		// 备注
		String note = ExcelReadUtil.convertToString(row.getCell(2));
		// 事项时间(会议时间,批示时间,出访时间)
		if (ToolUtil.isEmpty(row.getCell(3))){
			throw new BussinessException(501,"事项时间不能为空");
		}
		Date serviceTime = row.getCell(3).getDateCellValue();
		// 事项类型
		String serviceType = ExcelReadUtil.convertToString(row.getCell(4));
		// 分管领导(多选)
		String branchedLeader = ExcelReadUtil.convertToString(row.getCell(5));
		// 督办频次(X周)
		Integer overseeFrequency = ExcelReadUtil.convertToInteger(row.getCell(6));
		// 主办部门名称
		String orgName = ExcelReadUtil.convertToString(row.getCell(7));
		// 是否阅知件(是:Y;否:N)
		String isRead = ExcelReadUtil.convertToString(row.getCell(8));
		// 开始工作时间
		if (ToolUtil.isEmpty(row.getCell(9))){
			throw new BussinessException(501,"立项反馈截止期限不能为空");
		}
		Date approvalDeadline = row.getCell(9).getDateCellValue();

		add(serviceName, null, null, taskNote, taskNote, serviceTime, branchedLeader, overseeFrequency,
				serviceType, orgName, isRead, approvalDeadline, omUser, superTypeOversee);
	}
	
	@Transactional
	public void add(String serviceName, String commandLeader,String commandSource,String taskNote,String note,
			Date serviceTime,String branchedLeader,Integer overseeFrequency,String serviceType,String orgName,
			String isRead,Date approvalDeadline,OmUser omUser, SuperTypeOversee superTypeOversee){
		if (ToolUtil.isEmpty(serviceName)||ToolUtil.isEmpty(taskNote)||ToolUtil.isEmpty(serviceTime)
			||ToolUtil.isEmpty(branchedLeader)||ToolUtil.isEmpty(overseeFrequency)||ToolUtil.isEmpty(orgName)
				||ToolUtil.isEmpty(isRead)){
			throw new BussinessException(501,"必填项不能为空");
		}
		if (ToolUtil.isNotEmpty(serviceName) && ToolUtil.isNotEmpty(orgName)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 督办事项
			SuperService superService = new SuperService();
			superService.setUnid(UUID.randomUUID().toString());
			superService.setServiceName(serviceName.trim());
			superService.setCommandLeader(commandLeader);
			superService.setCommandSource(commandSource);
			superService.setTaskNote(taskNote.trim());
			superService.setNote(note.trim());
			superService.setServiceTime(format.format(serviceTime));
			superService.setBranchedLeader(branchedLeader);
			superService.setOverseeFrequency(overseeFrequency);
			superService.setServiceStatus("草稿");
			superService.setCreateTime(format.format(new Date()));
			superService.setCreateUserid(omUser.getUserid());
			superService.setCreateUsername(omUser.getEmpname());
			superService.setOverseeUserid(omUser.getUserid());
			superService.setOverseeUsername(omUser.getEmpname());
			superService.setOverseeName(superTypeOversee.getTypeName());
			superService.setOverseeUnid(superTypeOversee.getUnid());
			//默认督办级别为公司级-----lishuang
			superService.setServiceLevel("公司级");
			superService.setStatus(1);
			// 事项类型,serviceType为事项类别名
			if (ToolUtil.isEmpty(serviceType)) {
				throw new BussinessException(501,serviceType + "事项类型名称不能为空");
			}
			SuperTypeService typeService = superTypeServiceService.findByTypeName(serviceType);
			if (typeService == null) {
				// 事项类型新增
				//typeService = superTypeServiceService.add(serviceType);
				throw new BussinessException(501,serviceType + "--事项类型名称不存在");
			}
			superService.setServiceType(typeService.getUnid());
			logger.info("excelImport=====superService=" + superService.toString());
			superServiceService.save(superService);

			// 督办类型与事项关联添加
			superServiceOverseeMappingService.addSuperServiceOverseeMapping(superService, null, "Y");
			// serviceId
			String overseeTypeId = superTypeOversee.getTypeId();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy");
			String year = ft.format(new Date());
			Integer yearTo = Integer.parseInt(year.substring(2));
			String serviceId = superServiceIdCreateService.geSuperServiceId(overseeTypeId, yearTo);
			// 督办部门新增,orgId需要流程平台接口匹配
			if (orgNameMap == null) {
				orgNameMap = liuchengOperator.getOrgName(omUser.getUserid());
			}
			String[] orgNames = orgName.split(",");
			String[] isReads = isRead.split(",");
			int num = 0;
			for (int i=0;i<orgNames.length;i++) {
				num += 1;
				orgNames[i] = orgNames[i].trim();
				//导入的主办部门跟流程平台获取的不匹配时，提示
				String orgId = orgNameMap.get(orgNames[i]);
				if (ToolUtil.isEmpty(orgId)){
					throw new BussinessException(501,"'" + orgNames[i] + "' 部门名称异常");
				}
				String cal = Cal26ToNumUntil.getCal26r(num);
				SuperSponsor superSponsor = new SuperSponsor();
				superSponsor.setUnid(UUID.randomUUID().toString());
				superSponsor.setServiceUnid(superService.getUnid());
				superSponsor.setOrgName(orgNames[i]);
				superSponsor.setOrgId(orgId);
				superSponsor.setApprovalDeadline(format.format(approvalDeadline));
				superSponsor.setFeedbackDeadline(format.format(approvalDeadline));//--------lishuang
				superSponsor.setIsRead(isRead);
				superSponsor.setServiceId(serviceId + "-" + cal);
				superSponsor.setCreateTime(format.format(new Date()));
				superSponsor.setCreateUserid(superService.getCreateUserid());
				//多个主办部门，是否为阅知,不同时--新需求-0403 lishuang
				//当部门与是否阅知一一对应时
				if (isReads.length==orgNames.length){
					isReads[i] = isReads[i].trim();
					if ("否".equals(isReads[i])||"".equals(isReads[i])){
						superSponsor.setIsRead("N");
					}else {
						superSponsor.setIsRead("Y");
					}
				}
				//当部门个数大于是否阅知个数时
				if (isReads.length<orgNames.length && i<=isReads.length-1){
					isReads[i] = isReads[i].trim();
					if ("否".equals(isReads[i])||"".equals(isReads[i])){
						superSponsor.setIsRead("N");
					}else {
						superSponsor.setIsRead("Y");
					}
				}else if (isReads.length<orgNames.length && i>isReads.length-1){
					superSponsor.setIsRead("N");
				}
				superSponsor.setServiceStatus(superService.getServiceStatus());
				superSponsor.setCreateUsername(superService.getCreateUsername());
				superSponsor.setStatus(2);// 导入未保存类型
                superSponsor.setNeedVerify("N");
				superSponsorService.insert(superSponsor);
			}

		}
	}
	

}
