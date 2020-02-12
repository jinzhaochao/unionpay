package com.unionpay.supervision.serviceImpl;

import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperRecordRepository;
import com.unionpay.supervision.domain.SuperRecord;
import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.domain.SuperSponsorLog;
import com.unionpay.supervision.model.LcFeedBackDto;
import com.unionpay.supervision.service.LcOperatorService;
import com.unionpay.supervision.service.SuperSponsorLogService;
import com.unionpay.supervision.service.SuperSponsorMasterService;
import com.unionpay.supervision.service.SuperSponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/**
*
* 流程平台数据操作实现类
*
* @author xiongym
* @since 2018-11-29
*/
@Service
@Transactional
public class LcOperatorServiceImpl implements LcOperatorService{
	
	@Autowired
	private SuperSponsorLogService superSponsorLogService;
	@Autowired
	private SuperSponsorService superSponsorService;
	@Autowired
	private SuperRecordRepository superRecordRepository;
	@Autowired
	private SuperSponsorMasterService superSponsorMasterService;
	
	/**
	 * 事项信息补充
	*/
	@Override
	@Transactional
	public void editSponsorInfo(LcFeedBackDto lcFeedBackDto){
		
		//super_sponsor_log主键
		String unid = lcFeedBackDto.getUnid();
		//部门unid
		String sponsorUnid = lcFeedBackDto.getSponsorUnid();
		//事项unid
		//String serviceUnid = lcFeedBackDto.getServiceUnid();
		SuperSponsor sponsor=null;
		if(ToolUtil.isNotEmpty(sponsorUnid)){
			//督办部门信息更新
            sponsor = superSponsorService.findById(sponsorUnid);
			if(sponsor != null){
				sponsor.setIsRead(lcFeedBackDto.getIsRead());
				sponsor.setSponsorId(lcFeedBackDto.getSponsorId());
				sponsor.setSponsorName(lcFeedBackDto.getSponsorName());
				sponsor.setFeedbackRule(lcFeedBackDto.getFeedbackRule());
				sponsor.setFeedbackDeadline(lcFeedBackDto.getFeedbackDeadline());
				sponsor.setFeedbackTime(lcFeedBackDto.getFeedbackTime());
				//新增信息反馈人--lishuang 2019-08-21
				sponsor.setFeedbackUserid(lcFeedBackDto.getFeedbackUserid());
				sponsor.setFeedbackUsername(lcFeedBackDto.getFeedbackUsername());

				sponsor.setWorkStatus(lcFeedBackDto.getWorkStatus());
				if(ToolUtil.isNotEmpty(lcFeedBackDto.getProposedClosingTime())){
					sponsor.setProposedClosingTime(lcFeedBackDto.getProposedClosingTime());
				}
				if(ToolUtil.isNotEmpty(lcFeedBackDto.getResultForm())){
					sponsor.setResultForm(lcFeedBackDto.getResultForm());
				}
				if(ToolUtil.isNotEmpty(lcFeedBackDto.getWorkPlan())){
					sponsor.setWorkPlan(lcFeedBackDto.getWorkPlan());
				}
				sponsor.setNote(lcFeedBackDto.getNote());
				sponsor.setProgress(lcFeedBackDto.getProgress());
				if("4".equals(sponsor.getWorkStatus())){
					sponsor.setWorkStatus("工作中止");
					sponsor.setServiceStatus("中止");
				}else if("1".equals(sponsor.getWorkStatus())){
					sponsor.setWorkStatus("已完成");
					sponsor.setServiceStatus("完成");
				}else if("2".equals(sponsor.getWorkStatus())){
					sponsor.setWorkStatus("推进中,有阶段性进展");
					//sponsor.setServiceStatus("督办");
				}else if("3".equals(sponsor.getWorkStatus())){
					sponsor.setWorkStatus("推进中,暂无阶段性进展");
					//sponsor.setServiceStatus("督办");
				}else if("5".equals(sponsor.getWorkStatus())){
					sponsor.setWorkStatus("工作终止");
					sponsor.setServiceStatus("中止");
				}
				superSponsorService.edit(sponsor);
				
				//主办人信息处理
				String sponsorIds = lcFeedBackDto.getSponsorId();
				superSponsorMasterService.saveorUpdate(sponsor, sponsorIds);
				
			}
		}
		if(ToolUtil.isNotEmpty(unid)){
			//部门日志记录更新
			SuperSponsorLog log = superSponsorLogService.findById(unid);
			if(log != null){
				log.setSponsorId(lcFeedBackDto.getSponsorId());
				log.setSponsorName(lcFeedBackDto.getSponsorName());
				log.setFeedbackRule(lcFeedBackDto.getFeedbackRule());
				log.setFeedbackDeadline(lcFeedBackDto.getFeedbackDeadline());
				log.setFeedbackTime(lcFeedBackDto.getFeedbackTime());
				//新增信息反馈人--lishuang 2019-08-21
				log.setFeedbackUserid(lcFeedBackDto.getFeedbackUserid());
				log.setFeedbackUsername(lcFeedBackDto.getFeedbackUsername());
                //log.setWorkStatus(lcFeedBackDto.getWorkStatus());

				/*if ("1".equals(lcFeedBackDto.getWorkStatus())){
                    log.setWorkStatus("已完成");
                }else if ("2".equals(lcFeedBackDto.getWorkStatus())){
                    log.setWorkStatus("推进中,有阶段性进展");
                }else if ("3".equals(lcFeedBackDto.getWorkStatus())){
                    log.setWorkStatus("推进中,暂无阶段性进展");
                }else if ("4".equals(lcFeedBackDto.getWorkStatus())){
                    log.setWorkStatus("工作中止");
                }*/// lishuang
				if (sponsor!=null){
					log.setWorkStatus(sponsor.getWorkStatus());
				}
				log.setNote(lcFeedBackDto.getNote());
				log.setProgress(lcFeedBackDto.getProgress());
				
				superSponsorLogService.edit(log);
			}
		}
		
	}
	
	/**
	 * 事项信息变更与延期
	*/
	@Override
	@Transactional
	public void SponsorChangeAndDelay(LcFeedBackDto lcFeedBackDto){
		Integer changeType = lcFeedBackDto.getChangeType();
		SuperRecord record = new SuperRecord();
		record.setSponsorUnid(lcFeedBackDto.getSponsorUnid());
		record.setFeedbackDeadline(lcFeedBackDto.getFeedbackDeadline());
		record.setReason(lcFeedBackDto.getChangeReason());
		record.setStatus(1);
		record.setCreateTime(DateUtil.getTime());
		if(changeType.equals(3)){
			//3：变更
			record.setType(1);//1.变更；2.延期
			superRecordRepository.save(record);
		}else if(changeType.equals(4)){
			//4：延期
			record.setType(2);//1.变更；2.延期
			superRecordRepository.save(record);
		}
		editSponsorInfo(lcFeedBackDto);
	}

}
