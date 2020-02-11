package com.unionpay.supervision.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.log.LogManager;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.SuperSponsorLogRepository;
import com.unionpay.supervision.domain.SuperService;
import com.unionpay.supervision.domain.SuperServiceOverseeMapping;
import com.unionpay.supervision.domain.SuperSponsor;
import com.unionpay.supervision.domain.SuperSponsorLog;
import com.unionpay.supervision.log.LogTaskFactory;
import com.unionpay.supervision.model.ServiceModel;
import com.unionpay.supervision.model.SponsorSearchDto;
import com.unionpay.supervision.model.SuperOverseeMappingDto;
import com.unionpay.supervision.model.SuperServiceModelDto;
import com.unionpay.supervision.service.*;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;

/**
 * 事项查询与操作
 */
@Service
@Transactional
public class SponsorSearchServiceImpl implements SponsorSearchService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private SuperServiceService superServiceService;
    @Autowired
    private SuperFileService superFileService;
    @Autowired
    private SuperSponsorService superSponsorService;
    @Autowired
    private SuperSponsorLogService superSponsorLogService;
    @Autowired
    private SuperServiceOverseeMappingService superServiceOverseeMappingService;
    @Autowired
    private SuperSponsorLogRepository superSponsorLogRepository;

    /**
     * 事项搜索查询
     */
    @Override
    public List<ServiceModel> findSuperVisionlist(Integer page, Integer size, SponsorSearchDto sponsorSearch) {
        List<ServiceModel> list = null;
        String jpql = "select sp.unid,sp.service_id serviceId,se.oversee_name overseeName,ty.type_name typeName,"
                + "se.service_name serviceName,sp.org_name orgName,se.command_leader commandLeader,se.task_note taskNote,"
                + "sp.sponsor_name sponsorName,DATE_FORMAT(sp.proposed_closing_time,'%Y-%m-%d') proposedClosingTime,"
                + "sp.result_form resultForm,sp.work_plan workPlan,DATE_FORMAT(sp.feedback_time,'%Y-%m-%d') feedbackTime,"
                + "sp.progress,se.oversee_frequency overseeFrequency,se.oversee_username overseeUsername,sp.service_status "
                + "serviceStatus,DATE_FORMAT(se.service_time,'%Y-%m-%d') serviceTime,sp.work_status workStatus,"
                + "sp.create_time createTime from super_sponsor sp LEFT JOIN super_service se on se.unid = sp.service_unid "
                + "LEFT Join super_type_service ty on se.service_type = ty.unid LEFT JOIN super_service_oversee_mapping ss "
                + "ON ss.service_unid = sp.service_unid Where sp.status = 1 ";
        String sql = jpql + getsponsorSearchsql(sponsorSearch);
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServiceModel.class));
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        list = query.getResultList();
        return list;
    }

    /**
     * 事项总数
     */
    @Override
    public int getSuperVisioncount(SponsorSearchDto sponsorSearch) {
        int count = 0;
        String jpql = "select count(sp.unid) from  super_sponsor sp LEFT JOIN  super_service se  on se.unid = sp.service_unid "
                + "LEFT Join super_type_service ty  on se.service_type = ty.unid LEFT JOIN super_service_oversee_mapping ss "
                + "ON ss.service_unid = sp.service_unid Where sp.status = 1";

        String sql = jpql + getsponsorSearchsql(sponsorSearch);
        Query querycount = em.createNativeQuery(sql);
        List<BigInteger> counts = querycount.getResultList();
        if (counts != null && counts.size() > 0) {
            //count = counts.get(0).intValue();
            count = counts.size();
        }
        return count;
    }

    private String getsponsorSearchsql(SponsorSearchDto sponsorSearch) {
        String sponsorSearchsql = "";
        if (ToolUtil.isNotEmpty(sponsorSearch.getOverseeUnid())) {
            //sponsorSearchsql += " AND se.oversee_unid = " + "'" + sponsorSearch.getOverseeUnid() + "'";
            sponsorSearchsql += " AND ss.oversee_unid = " + "'" + sponsorSearch.getOverseeUnid() + "'";
        }
        if (ToolUtil.isNotEmpty(sponsorSearch.getServiceType())) {
            sponsorSearchsql += " AND se.service_type = " + "'" + sponsorSearch.getServiceType() + "'";
        }
        if (ToolUtil.isNotEmpty(sponsorSearch.getOverseeUsername())) {
            sponsorSearchsql += " AND se.oversee_username like concat('%','" + sponsorSearch.getOverseeUsername()
                    + "','%')";
        }
        if ((ToolUtil.isNotEmpty(sponsorSearch.getServiceStartTime()))) {
            sponsorSearchsql += " AND ss.service_time >=" + "'" + sponsorSearch.getServiceStartTime() + "'";
        }
        if ((ToolUtil.isNotEmpty(sponsorSearch.getServiceEndTime()))) {
            sponsorSearchsql += " AND ss.service_time <=" + "'" + sponsorSearch.getServiceEndTime() + "'";
        }
        // 督办类型名称
        if (ToolUtil.isNotEmpty(sponsorSearch.getOverseeName())) {
            sponsorSearchsql += " AND se.oversee_name like concat('%','" + sponsorSearch.getOverseeName() + "','%')";
        }
        if (ToolUtil.isNotEmpty(sponsorSearch.getTaskNote())) {
            //sponsorSearchsql += " AND se.task_note like concat('%','" + sponsorSearch.getTaskNote() + "','%')";
            sponsorSearchsql += " AND ss.task_note like concat('%','" + sponsorSearch.getTaskNote() + "','%')";
        }
        //批示领导--新需求-0418 lishuang
        if (ToolUtil.isNotEmpty(sponsorSearch.getCommandLeader())) {
            sponsorSearchsql += " AND se.command_leader like concat('%','" + sponsorSearch.getCommandLeader()
                    + "','%')";
        }
        //分管公司领导
        if (ToolUtil.isNotEmpty(sponsorSearch.getBranchedLeader())) {
            sponsorSearchsql += " AND se.branched_leader like concat('%','" + sponsorSearch.getBranchedLeader()
                    + "','%')";
        }
        // 主办人
        if (ToolUtil.isNotEmpty(sponsorSearch.getSponsorName())) {
            sponsorSearchsql += " AND sp.sponsor_name like concat('%','" + sponsorSearch.getSponsorName() + "','%')";
        }
        // 主办单位
        if (ToolUtil.isNotEmpty(sponsorSearch.getOrgName())) {
            sponsorSearchsql += " AND sp.org_name like concat('%','" + sponsorSearch.getOrgName() + "','%')";
        }
        // 工作推进状态
        if (ToolUtil.isNotEmpty(sponsorSearch.getWorkStatus())) {
            sponsorSearchsql += " AND sp.work_status =" + "'" + sponsorSearch.getWorkStatus() + "'";
        }
        // 进展情况
        if (ToolUtil.isNotEmpty(sponsorSearch.getProgress())) {
            sponsorSearchsql += " AND sp.progress like concat('%','" + sponsorSearch.getProgress() + "','%')";
        }
        if (ToolUtil.isNotEmpty(sponsorSearch.getServiceId())) {
            sponsorSearchsql += " AND sp.service_id like concat('%','" + sponsorSearch.getServiceId() + "','%')";
        }
        if (ToolUtil.isNotEmpty(sponsorSearch.getServiceName())) {
            sponsorSearchsql += " AND ss.service_name like concat('%','" + sponsorSearch.getServiceName() + "','%')";
        }
        if (ToolUtil.isNotEmpty(sponsorSearch.getFeedbackStartTime())) {
            sponsorSearchsql += " AND sp.feedback_time >=" + "'" + sponsorSearch.getFeedbackStartTime() + "'";
        }
        if (ToolUtil.isNotEmpty(sponsorSearch.getFeedbackEndTime())) {
            sponsorSearchsql += " AND sp.feedback_time <=" + "'" + sponsorSearch.getFeedbackEndTime() + "'";
        }
        //根据信息反馈人查询-- lishuang 2019-08-21
        if (ToolUtil.isNotEmpty(sponsorSearch.getFeedbackUsername())) {
            sponsorSearchsql += " AND sp.feedback_username like concat('%','" + sponsorSearch.getFeedbackUsername() + "','%')";
        }
        sponsorSearchsql += " GROUP BY sp.unid";
        //按照创建时间排序
        sponsorSearchsql += " order by se.create_time DESC,se.unid DESC,sp.service_id ";

        return sponsorSearchsql;
    }

    /**
     * 事项搜索查询后，导出
     */
    @Override
    public List<ServiceModel> findAndExportSuperVisionlist(Integer page, Integer size, SponsorSearchDto sponsorSearch, String unids) {
        List<ServiceModel> list = null;
        List<ServiceModel> arrayList = new ArrayList<>();

            String jpql = "SELECT sp.unid,sp.service_id serviceId,ty.type_name typeName,"
                    + "GROUP_CONCAT(IFNULL(DATE_FORMAT(ss.service_time, '%Y-%m-%d'),' ') SEPARATOR'@_@') serviceTime,"
                    + "GROUP_CONCAT(IFNULL(ss.service_name, ' ') SEPARATOR'@_@') serviceName,"
                    + "GROUP_CONCAT(IFNULL(ss.command_leader, ' ') SEPARATOR'@_@') commandLeader,"
                    + "GROUP_CONCAT(IFNULL(ss.task_note, ' ') SEPARATOR'@_@') taskNote,"
                    + "sp.org_name orgName,sp.sponsor_name sponsorName,DATE_FORMAT(sp.proposed_closing_time,'%Y-%m-%d') proposedClosingTime,"
                    + "sp.result_form resultForm,sp.work_plan workPlan,DATE_FORMAT(sp.feedback_time,'%Y-%m-%d') feedbackTime,sp.work_status workStatus,"
                    + "sp.progress,se.oversee_frequency overseeFrequency FROM super_sponsor sp LEFT JOIN super_service se ON se.unid = sp.service_unid "
                    + "LEFT JOIN super_type_service ty ON se.service_type = ty.unid INNER JOIN super_service_oversee_mapping ss "
                    + "ON (ss.service_unid = sp.service_unid AND ss.`status` = '1') Where sp.status = 1 ";
            if (ToolUtil.isNotEmpty(unids)) {
                String[] unid = unids.split(",");
                jpql += " and sp.unid in (";
                for (int i = 0; i < unid.length; i++) {
                    if (ToolUtil.isEmpty(unid[i])) {
                        continue;
                    }
                    if (i < unid.length - 1) {
                        jpql += "'" + unid[i] + "',";
                    } else {
                        jpql += "'" + unid[i] + "')";
                    }
                }
            }
            String sql = jpql + getsponsorSearchsql(sponsorSearch);
            Query query = em.createNativeQuery(sql);
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServiceModel.class));
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            list = query.getResultList();
            if (ToolUtil.isEmpty(list)) {
                return list;
            }
            for (ServiceModel model : list) {
                if (ToolUtil.isEmpty(model) || !model.getServiceTime().contains("@_@")) {
                    continue;
                }
                String[] serviceTimes = model.getServiceTime().split("@_@");
                String[] serviceNames = model.getServiceName().split("@_@");
                String[] taskNotes = model.getTaskNote().split("@_@");
                String[] commandLeaders = model.getCommandLeader().split("@_@");
                model.setServiceTime(getStr(serviceTimes));
                model.setServiceName(getStr(serviceNames));
                model.setTaskNote(getStr(taskNotes));
                model.setCommandLeader(getStr(commandLeaders));
                //修改导出台账为空的问题
//                String sqlq = "select date_format(sl.feedback_time,'%Y-%m-%d') feedbackTime,sl.work_status as workStatus,sl.progress  from super_sponsor_log sl where sl.sponsor_unid = '" + model.getUnid() + "' and sl.status = '1' and sl.work_status is not null and sl.work_status <> '' order by sl.create_time desc limit 1 ";
//                Query query1 = em.createNativeQuery(sqlq);
//                query1.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperSponsorLog.class));
//                List<SuperSponsorLog> superSponsorLogList = query1.getResultList();
////            List<SuperSponsorLog> superSponsorLogList = superSponsorLogRepository.findSponsorLog(model.getUnid());
//                ServiceModel serviceModel = new ServiceModel();
//
//                if (ToolUtil.isNotEmpty(superSponsorLogList)) {
//                    SuperSponsorLog superSponsorLog = superSponsorLogList.get(0);
//
//                    serviceModel.setFeedbackTime(superSponsorLog.getFeedbackTime());
//                    serviceModel.setWorkStatus(superSponsorLog.getWorkStatus());
//                    serviceModel.setProgress(superSponsorLog.getProgress());
//                }
//                if (ToolUtil.isEmpty(superSponsorLogList)) {
//                    serviceModel.setFeedbackTime(model.getFeedbackTime());
//                    serviceModel.setWorkStatus(model.getWorkStatus());
//                    serviceModel.setProgress(model.getProgress());
//                }
//                serviceModel.setUnid(model.getUnid());
//                serviceModel.setServiceId(model.getServiceId());
//                serviceModel.setTypeName(model.getTypeName());
//                serviceModel.setServiceTime(model.getServiceTime());
//                serviceModel.setServiceName(model.getServiceName());
//                serviceModel.setCommandLeader(model.getCommandLeader());
//                serviceModel.setTaskNote(model.getTaskNote());
//                serviceModel.setOrgName(model.getOrgName());
//                serviceModel.setSponsorName(model.getSponsorName());
//                serviceModel.setProposedClosingTime(model.getProposedClosingTime());
//                serviceModel.setResultForm(model.getResultForm());
//                serviceModel.setWorkPlan(model.getWorkPlan());
//                serviceModel.setOverseeFrequency(model.getOverseeFrequency());
//                arrayList.add(serviceModel);
            }
        return list;
    }

    private String getStr(String[] field) {
        String str = "";
        if (ToolUtil.isNotEmpty(field)) {
            for (int i = 0; i < field.length; i++) {
                if (i < field.length - 1) {
                    str += (i + 1) + "." + field[i] + "\n";
                } else {
                    str += (i + 1) + "." + field[i];
                }
            }
        }
        return str;
    }

    /**
     * 事项合并
     */
    @Override
    @Transactional
    public void supervisionCombine(SuperSponsor removeSponsor, SuperSponsor targetSponsor) {
        String removeServiceUnid = removeSponsor.getServiceUnid();
        String targetServiceUnid = targetSponsor.getServiceUnid();
        // 督办类型关联整合
//		List<SuperServiceOverseeMapping> removeOverseeMappinglist = superServiceOverseeMappingService
//				.findByServiceUnidAndIsPrimary(removeServiceUnid, null);
//		List<SuperServiceOverseeMapping> targetOverseeMappinglist = superServiceOverseeMappingService
//				.findByServiceUnidAndIsPrimary(targetServiceUnid, null);
//		HashMap<String, String> overseeMapping = new HashMap<String, String>();
//		for (SuperServiceOverseeMapping targetOverseeMapping : targetOverseeMappinglist) {
//			if (targetOverseeMapping != null) {
//				overseeMapping.put(targetOverseeMapping.getOverseeUnid(), targetOverseeMapping.getOverseeName());
//			}
//		}
//		for (SuperServiceOverseeMapping removeOverseeMapping : removeOverseeMappinglist) {
//			if (!overseeMapping.containsKey(removeOverseeMapping.getOverseeUnid())) {
//				// 添加
//				removeOverseeMapping.setServiceUnid(targetServiceUnid);
//				removeOverseeMapping.setIsPrimary("N");
//				superServiceOverseeMappingService.save(removeOverseeMapping);
//			}
//		}

        // 督办类型关联整合
        superServiceOverseeMappingService.combine(removeServiceUnid, targetServiceUnid);
        // 主办部门更新
        List<SuperSponsor> removeSuperSponsorlist = superSponsorService.findByServiceUnid(removeServiceUnid, 1);
        List<SuperSponsor> targetSuperSponsorlist = superSponsorService.findByServiceUnid(targetServiceUnid, 1);
        HashMap<String, SuperSponsor> superSponsor = new HashMap<String, SuperSponsor>();
        for (SuperSponsor targetSuperSponsor : targetSuperSponsorlist) {
            if (targetSuperSponsor != null) {
                superSponsor.put(targetSuperSponsor.getOrgId(), targetSuperSponsor);
            }
        }
        for (SuperSponsor removeSuperSponsor : removeSuperSponsorlist) {
            if (removeSuperSponsor != null) {
                if (!superSponsor.containsKey(removeSuperSponsor.getOrgId())) {
                    // 添加
                    //removeSuperSponsor.setServiceUnid(targetServiceUnid);
                    //superSponsorService.edit(removeSuperSponsor);
                    superSponsorService.mergerAdd(removeSuperSponsor, targetServiceUnid);
                } else {
                    // 多余的主办部门移除
                    superSponsorService.invalidateStatusByUnid(removeSuperSponsor.getUnid());
                    //督办记录合并
                    String removeSponsorUnid = removeSuperSponsor.getUnid();
                    String tagerSponsorUnid = superSponsor.get(removeSuperSponsor.getOrgId()).getUnid();
                    superSponsorLogService.combine(removeSponsorUnid, tagerSponsorUnid);
                }
            }
        }
        //附近信息更新
        superFileService.updateSuperServiceId(removeServiceUnid, targetServiceUnid);

        // 事项清除
        SuperService service = superServiceService.findByUnid(removeSponsor.getServiceUnid());
        if (service != null) {
            service.setStatus(-1);
            superServiceService.save(service);
        }
        //superServiceService.delete(removeSponsor.getServiceUnid());
        // 日志记录
        LogManager.me().executeLog(LogTaskFactory.systemLog(targetSponsor.getUnid(), targetSponsor.getServiceUnid(),
                JSONObject.toJSONString(removeSponsor), "项目合并", 1, 7, null, null));

    }

    /**
     * 修改事项
     *
     * @param superServiceModelDto
     * @return
     * @author lishuang
     * @date 2019-04-12
     */
    @Transactional
    public boolean editSuperService(SuperServiceModelDto superServiceModelDto) {
        SuperService superService = superServiceService.findByUnid(superServiceModelDto.getUnid());
        if (ToolUtil.isEmpty(superService)) {
            return false;
        }
        superService.setServiceType(superServiceModelDto.getServiceType());
        superService.setOverseeFrequency(superServiceModelDto.getOverseeFrequency());
        superService.setBranchedLeader(superServiceModelDto.getBranchedLeader());
        //主办部门下一次督办时间保存
        List<SuperSponsor> superSponsors = superSponsorService.findByServiceUnid(superService.getUnid(), 1);
        if (ToolUtil.isNotEmpty(superSponsors)) {
            for (SuperSponsor superSponsor : superSponsors) {
                superSponsor.setNextTime(DateUtil.getAfterDayDate(superService.getOverseeFrequency() * 7 + ""));
                superSponsorService.edit(superSponsor);
            }
        }
        //任务来源修改（注意是否主督办类型-Y/N）
        //原有的
        List<SuperServiceOverseeMapping> oldList =
                superServiceOverseeMappingService.findByServiceUnidAndIsPrimary(superService.getUnid(), null);
        //修改后的
        List<SuperOverseeMappingDto> newList = superServiceModelDto.getSuperOverseeMappingDto();
        //如果全部删除
        if (ToolUtil.isEmpty(newList)) {
            for (SuperServiceOverseeMapping overseeMapping : oldList) {
                overseeMapping.setStatus(0);
                superServiceOverseeMappingService.save(overseeMapping);
            }
        }
        //如果删除一部分
        List<SuperServiceOverseeMapping> list = new ArrayList<>();
        if (ToolUtil.isNotEmpty(newList)) {
            for (SuperOverseeMappingDto superOverseeMappingDto : newList) {
                SuperServiceOverseeMapping superServiceOverseeMapping =
                        superServiceOverseeMappingService.findByUnid(superOverseeMappingDto.getUnid());
                list.add(superServiceOverseeMapping);
            }
        }
        //判断主任务来源有没有被删除
        if (ToolUtil.isNotEmpty(list)) {
            boolean isPrimary = true;//默认主任务来源没有被删除
            for (SuperServiceOverseeMapping mapping : list) {
                if ("N".equals(mapping.getIsPrimary())) {
                    isPrimary = false;
                } else {
                    isPrimary = true;
                    break;
                }
            }
            if (!isPrimary) {
                Collections.sort(list, new Comparator<SuperServiceOverseeMapping>() {
                    @Override
                    public int compare(SuperServiceOverseeMapping o1, SuperServiceOverseeMapping o2) {
                        if (o1.getCreateTime() != null && o2.getCreateTime() != null) {
                            return o1.getCreateTime().compareTo(o2.getCreateTime());
                        } else {
                            return -1;
                        }
                    }
                });
                //如果来源时间最早的，不是主督办类型
                if ("N".equals(list.get(0).getIsPrimary())) {
                    list.get(0).setIsPrimary("Y");
                }
                SuperServiceOverseeMapping overseeMapping = list.get(0);
                //事项相应字段值修改
                superService.setServiceName(overseeMapping.getServiceName());
                superService.setServiceTime(overseeMapping.getServiceTime());
                superService.setCommandSource(overseeMapping.getCommandSource());
                superService.setCommandLeader(overseeMapping.getCommandLeader());
                superService.setOverseeUnid(overseeMapping.getOverseeUnid());
                superService.setOverseeName(overseeMapping.getOverseeName());
                superService.setTaskNote(overseeMapping.getTaskNote());
                superServiceService.save(superService);
            }
            for (SuperServiceOverseeMapping mapping : list) {
                superServiceOverseeMappingService.save(mapping);
            }
        }
        //从oldList中去除newList中的数据后，剩下的为删除的，置无效
        if (oldList.removeAll(list) && ToolUtil.isNotEmpty(oldList)) {
            for (SuperServiceOverseeMapping overseeMapping : oldList) {
                overseeMapping.setStatus(0);
                superServiceOverseeMappingService.save(overseeMapping);
            }
        }
        return true;
    }
}
