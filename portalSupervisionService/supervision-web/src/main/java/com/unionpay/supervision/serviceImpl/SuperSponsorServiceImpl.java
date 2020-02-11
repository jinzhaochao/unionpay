package com.unionpay.supervision.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.log.LogManager;
import com.unionpay.common.util.Cal26ToNumUntil;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.bussniss.LiuchengOperator;
import com.unionpay.supervision.dao.*;
import com.unionpay.supervision.domain.*;
import com.unionpay.supervision.log.LogTaskFactory;
import com.unionpay.supervision.model.*;
import com.unionpay.supervision.service.*;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 督办部门实现类
 * </p>
 *
 * @author xiongym
 * @since 2018-11-02
 */
@Service
@Transactional
public class SuperSponsorServiceImpl implements SuperSponsorService {

    @Autowired
    private SuperSponsorRepository superSponsorRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SuperSponsorLogService superSponsorLogService;
    @Autowired
    private SuperServiceOverseeMappingService superServiceOverseeMappingService;
    @Autowired
    private SuperServiceService superServiceService;
    @Autowired
    private SuperTypeServiceService superTypeServiceService;
    @Autowired
    private LiuchengOperator liuchengOperator;
    @Autowired
    private SuperFileService superFileService;
    @Autowired
    private SuperServiceRepository superServiceRepository;
    @Value("${http.file.download}")
    private String downloadUrl;
    @Autowired
    private OMUserRepository omUserRepository;
    @Autowired
    private SuperLcSponsorRepository superLcSponsorRepository;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据事项查询主办单位
     */
    @Override
    public List<SuperSponsor> findByServiceUnid(String serviceUnid, Integer status) {
        if (status == null) {
            return superSponsorRepository.findByServiceUnidOrderByServiceId(serviceUnid);
        } else {
            return superSponsorRepository.findByServiceUnidAndStatusOrderByServiceId(serviceUnid, status);
        }

    }

    /**
     * 主办单位新增
     */
    @Override
    @Transactional
    public void add(SuperSponsor superSponsor) {
        if (superSponsor != null) {
            // serviceId生成
            String serviceId = null;
            String serviceUnid = superSponsor.getServiceUnid();
            List<SuperSponsor> superSponsorlist = superSponsorRepository.findByServiceUnidOrderByServiceId(serviceUnid);
            for (SuperSponsor spo : superSponsorlist) {
                if (spo != null && ToolUtil.isNotEmpty(spo.getServiceId())) {
                    serviceId = spo.getServiceId();
                    break;
                }
            }
            if (serviceId != null) {
                if (ToolUtil.isNotEmpty(serviceId)) {
                    String[] serviceIds = serviceId.split("-");
                    serviceId = serviceIds[0] + "-" + serviceIds[1] + "-" + serviceIds[2] + "-"
                            + Cal26ToNumUntil.getCal26r(superSponsorlist.size() + 1);
                }
            }

            SuperSponsor add = new SuperSponsor();
            add.setOrgId(superSponsor.getOrgId());
            add.setOrgName(superSponsor.getOrgName());
            add.setIsRead(superSponsor.getIsRead());
            add.setServiceId(serviceId);
            add.setServiceUnid(superSponsor.getServiceUnid());
            add.setCreateUserid(superSponsor.getCreateUserid());
            add.setCreateUsername(superSponsor.getCreateUsername());
            add.setApprovalDeadline(superSponsor.getApprovalDeadline());
            add.setApprovalTime(superSponsor.getApprovalTime());
            add.setServiceStatus("草稿");
            add.setStatus(superSponsor.getStatus());
            add.setCreateTime(DateUtil.getTime());
            add.setUnid(UUID.randomUUID().toString());
            add.setStatus(1);
            superSponsorRepository.save(add);
//            SuperLcSponsorAgent superLcSponsorAgent = new SuperLcSponsorAgent();
//            superLcSponsorAgent.setUserId(superSponsor.getOrgId());
//            superLcSponsorAgent.setEmpName(superSponsor.getOrgName());
//            superLcSponsorAgent.setAgentUserId(superSponsor.getCreateUserid());
//            superLcSponsorAgent.setAgentUserName(superSponsor.getCreateUsername());
//            superLcSponsorAgentRepository.save(superLcSponsorAgent);
        }

    }

    @Override
    public void edit(SuperSponsor superSponsor) {
        superSponsorRepository.save(superSponsor);
    }

    @Override
    //事项合并时，添加主办部门
    public void mergerAdd(SuperSponsor superSponsor, String unid) {
        if (superSponsor != null) {
            // serviceId生成
            String serviceId = null;
            List<SuperSponsor> superSponsorlist = superSponsorRepository.findByServiceUnidOrderByServiceId(unid);
            for (int i = superSponsorlist.size() - 1; i >= 0; i--) {
                if (superSponsorlist.get(i) != null && ToolUtil.isNotEmpty(superSponsorlist.get(i).getServiceId())) {
                    serviceId = superSponsorlist.get(i).getServiceId();
                    break;
                }
            }
            if (serviceId != null) {
                if (ToolUtil.isNotEmpty(serviceId)) {
                    String[] serviceIds = serviceId.split("-");
                    serviceId = serviceIds[0] + "-" + serviceIds[1] + "-" + serviceIds[2] + "-"
                            + Cal26ToNumUntil.getCal26r(superSponsorlist.size() + 1);
                }
            }
            superSponsor.setServiceId(serviceId);
            superSponsor.setServiceUnid(unid);
            superSponsorRepository.save(superSponsor);
        }
    }

    @Override
    public void delete(String unid) {
        superSponsorRepository.deleteById(unid);
    }

    @Override
    public SuperSponsor findById(String unid) {
        return superSponsorRepository.findByUnid(unid);
    }

    @Override
    public SuperSponsor findByServiceId(String serviceId) {
        return superSponsorRepository.findByServiceId(serviceId);
    }

    /**
     * 督办部门置为无效
     *
     * @param superServiceId
     * @return
     */
    @Override
    @Transactional
    public void updateStatus(String superServiceId) {
        String sql = null;
        if (ToolUtil.isNotEmpty(superServiceId)) {
            sql = "UPDATE super_sponsor AS spo set spo.status = 0 WHERE spo.service_unid = " + "'" + superServiceId
                    + "'" + " AND spo.status = 1";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
        }

    }

    /**
     * 督办部门置为无效
     *
     * @param unid
     * @return
     */
    @Override
    @Transactional
    public void updateStatusByUnid(String unid) {
        String sql = null;
        if (ToolUtil.isNotEmpty(unid)) {
            sql = "UPDATE super_sponsor AS spo set spo.status = 0 WHERE spo.unid = " + "'" + unid + "'"
                    + " AND spo.status = 1";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
            // log表置为无效
            superSponsorLogService.updateStatus(unid);
        }

    }

    /**
     * 督办部门置为无效
     *
     * @param unid
     * @return
     */
    @Override
    @Transactional
    public void invalidateStatusByUnid(String unid) {
        String sql = null;
        if (ToolUtil.isNotEmpty(unid)) {
            sql = "UPDATE super_sponsor AS spo set spo.status = 0 WHERE spo.unid = " + "'" + unid + "'"
                    + " AND spo.status = 1";
            Query query = entityManager.createNativeQuery(sql);
            query.executeUpdate();
        }

    }

    /**
     * 主办单位新增，不需要生成serviceId
     */
    @Override
    public void insert(SuperSponsor superSponsor) {
        if (superSponsor != null) {
            SuperSponsor add = new SuperSponsor();
            add.setOrgId(superSponsor.getOrgId());
            add.setOrgName(superSponsor.getOrgName());
            add.setIsRead(superSponsor.getIsRead());
            add.setServiceId(superSponsor.getServiceId());
            add.setServiceUnid(superSponsor.getServiceUnid());
            add.setCreateUserid(superSponsor.getCreateUserid());
            add.setCreateUsername(superSponsor.getCreateUsername());
            add.setApprovalDeadline(superSponsor.getApprovalDeadline());
            add.setFeedbackDeadline(superSponsor.getFeedbackDeadline());
            add.setApprovalTime(superSponsor.getApprovalTime());
            add.setServiceStatus(superSponsor.getServiceStatus());
            add.setStatus(superSponsor.getStatus());
            add.setNeedVerify(superSponsor.getNeedVerify());
            add.setCreateTime(DateUtil.getTime());
            add.setUnid(UUID.randomUUID().toString());
            superSponsorRepository.save(add);
//            SuperLcSponsorAgent superLcSponsorAgent = new SuperLcSponsorAgent();
//            superLcSponsorAgent.setUserId(superSponsor.getOrgId());
//            superLcSponsorAgent.setEmpName(superSponsor.getOrgName());
//            superLcSponsorAgent.setAgentUserId(superSponsor.getCreateUserid());
//            superLcSponsorAgent.setAgentUserName(superSponsor.getCreateUsername());
//            superLcSponsorAgentRepository.save(superLcSponsorAgent);

        }

    }

    /**
     * 部门事项的立项与督办
     */
    @Override
    @Transactional
    public Boolean SuperServiceOperator(SuperSponsor superSponsor, int type, Integer frequency) {
        Boolean result = false;
        if (type == 1) {
            // 立项操作
            superSponsor.setServiceStatus("立项");
        } else if (type == 2) {
            // 督办操作
            superSponsor.setServiceStatus("督办");
            //督办时间
            superSponsor.setOverseeTime(DateUtil.getDay());
        }
        //下次督办时间
        if (frequency != null) {
            String nextTime = DateUtil.getAfterDayDate(frequency * 7 + "");
            if (ToolUtil.isNotEmpty(nextTime)) {
                superSponsor.setNextTime(nextTime.substring(0, 10));
            }

        }
        // 督办部门历史记录
        SuperSponsorLog sponsorlog = superSponsorLogService.add(superSponsor);
        edit(superSponsor);
        if (type == 1) {
            // 日志记录
            LogManager.me().executeLog(LogTaskFactory.systemLog(superSponsor.getUnid(), superSponsor.getServiceUnid(),
                    null, JSON.toJSONString(superSponsor), 1, 1, superSponsor.getSponsorId(), null));
        } else if (type == 2) {
            // 日志记录
            LogManager.me().executeLog(LogTaskFactory.systemLog(superSponsor.getUnid(), superSponsor.getServiceUnid(),
                    null, JSON.toJSONString(superSponsor), 1, 2, superSponsor.getSponsorId(), null));
        }
        // 推送给流程平台
        JSONArray sponsorServicelog = new JSONArray();
        SponsorServiceTolc SponsorServiceTolc = new SponsorServiceTolc();
        JSONArray overseeType = new JSONArray();
        String serviceUnid = superSponsor.getServiceUnid();
        List<SuperServiceOverseeMapping> overlist = superServiceOverseeMappingService
                .findByServiceUnidAndIsPrimary(serviceUnid, null);
        for (SuperServiceOverseeMapping over : overlist) {
            if (over != null && over.getStatus() == 1) {
                overseeTypeTolc overseeTypeTolc = new overseeTypeTolc();
                overseeTypeTolc.setCommandLeader(over.getCommandLeader());
                overseeTypeTolc.setCommandSource(over.getCommandSource());
                overseeTypeTolc.setIsPrimary(over.getIsPrimary());
                overseeTypeTolc.setOverseeName(over.getOverseeName());
                overseeTypeTolc.setOverseeUnid(over.getOverseeUnid());
                overseeTypeTolc.setServiceTime(over.getServiceTime());
                overseeTypeTolc.setServiceName(over.getServiceName());
                overseeTypeTolc.setStatus(over.getStatus());
                overseeTypeTolc.setTaskNote(over.getTaskNote());
                overseeType.add(overseeTypeTolc);
            }
        }
        // 事项
        SuperService superService = superServiceService.findByUnid(serviceUnid);
        SuperTypeService typeService = null;
        if (superService != null) {
            if (superService.getServiceType() != null) {
                typeService = superTypeServiceService.findByUnid(superService.getServiceType());
                SponsorServiceTolc.setServiceType(typeService.getTypeName());
            }
            SponsorServiceTolc.setBranchedLeader(superService.getBranchedLeader());
            SponsorServiceTolc.setServiceLevel(superService.getServiceLevel());
            SponsorServiceTolc.setOverseeFrequency(superService.getOverseeFrequency());
            SponsorServiceTolc.setOverseeUserid(superService.getOverseeUserid());
            SponsorServiceTolc.setOverseeUsername(superService.getOverseeUsername());

        }
        SponsorServiceTolc.setUnid(sponsorlog.getUnid());
        SponsorServiceTolc.setSponsorUnid(sponsorlog.getSponsorUnid());
        SponsorServiceTolc.setServiceUnid(serviceUnid);
        SponsorServiceTolc.setServiceId(superSponsor.getServiceId());
        SponsorServiceTolc.setServiceStatus(superSponsor.getServiceStatus());
        SponsorServiceTolc.setStatus(superSponsor.getStatus() + "");
        SponsorServiceTolc.setCreateTime(superSponsor.getCreateTime());
        SponsorServiceTolc.setCreateUserid(superSponsor.getCreateUserid());
        SponsorServiceTolc.setCreateUsername(superSponsor.getCreateUsername());
        SponsorServiceTolc.setOrgId(superSponsor.getOrgId());
        SponsorServiceTolc.setOrgName(superSponsor.getOrgName());
        SponsorServiceTolc.setIsRead(superSponsor.getIsRead());
        SponsorServiceTolc.setSponsorId(superSponsor.getSponsorId());
        SponsorServiceTolc.setSponsorName(superSponsor.getSponsorName());
        SponsorServiceTolc.setFeedbackRule(superSponsor.getFeedbackRule());
        SponsorServiceTolc.setFeedbackDeadline(superSponsor.getFeedbackDeadline());
        SponsorServiceTolc.setNote(superSponsor.getNote());
        SponsorServiceTolc.setNeedVerify(superSponsor.getNeedVerify());
        SponsorServiceTolc.setVerifiers(superSponsor.getVerifiers());
        //新增信息反馈人--lishuang 2019-08-21
        SponsorServiceTolc.setFeedbackUserid(superSponsor.getFeedbackUserid());
        SponsorServiceTolc.setFeedbackUsername(superSponsor.getFeedbackUsername());
        // 闸口人信息推送
        if (ToolUtil.isNotEmpty(superSponsor.getOrgId()) && ToolUtil.isNotEmpty(superSponsor.getOrgName())) {
            String sql = "select sa.agentUserid as agentUserId,sa.agentUserName from super_lc_sponsor sl "
                    + "left join super_sponsor sp on (sp.org_id = sl.userid and sp.org_name = sl.empname) "
                    + "left join super_lc_sponsor_agent sa on (sl.userid = sa.userid and sl.empname = sa.empname) "
                    + "where sp.org_id = '"+superSponsor.getOrgId()+"' and sp.org_name = '"+superSponsor.getOrgName()+"' "
                    + "group BY sa.id ";
            Query query = entityManager.createNativeQuery(sql);
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperLcSponsorAgent.class));
            SuperLcSponsorAgent superLcSponsorAgent = (SuperLcSponsorAgent) query.getSingleResult();
            //新增部门督办扎口代理人agentUserid和agentUserName  --jinzhao  2019-10-16
            if (ToolUtil.isNotEmpty(superLcSponsorAgent)) {
                SponsorServiceTolc.setAgentUserid(superLcSponsorAgent.getAgentUserId());
                SponsorServiceTolc.setAgentUserName(superLcSponsorAgent.getAgentUserName());
            }
        }
        // params
        sponsorServicelog.add(SponsorServiceTolc);
        result = liuchengOperator.doOANext(sponsorServicelog);
        return result;
    }

    /**
     * 部门事项批量立项与督办
     */
    @Override
    @Transactional
    public Boolean SuperServiceOperator(List<SuperSponsor> superSponsorlist, int type, Integer frequency, String flowTitle) {
        Boolean result = false;
        JSONArray sponsorServicelog = new JSONArray();
        for (SuperSponsor superSponsor : superSponsorlist) {
            if (type == 1) {
                // 立项操作
                superSponsor.setServiceStatus("立项");
            } else if (type == 2) {
                // 督办操作
                superSponsor.setServiceStatus("督办");
                //督办后，相关数据清空--->lishuang
                superSponsor.setWorkStatus("");
                superSponsor.setFeedbackTime(null);
                superSponsor.setProgress("");
                //督办时间
                //superSponsor.setOverseeTime(DateUtil.getDay());

            }
            //下次督办时间
            if (frequency != null) {
                String nextTime = DateUtil.getAfterDayDate(frequency * 7 + "");
                if (ToolUtil.isNotEmpty(nextTime)) {
                    superSponsor.setNextTime(nextTime.substring(0, 10));
                }

            }
            // 督办部门历史记录
            SuperSponsorLog sponsorlog = superSponsorLogService.add(superSponsor);
            edit(superSponsor);

            // 推送给流程平台
            SponsorServiceTolc SponsorServiceTolc = new SponsorServiceTolc();
            //督办时，添加选中的第一条数据的serviceName，推送给流程平台--新需求-0403 lishuang
            SponsorServiceTolc.setFlowTitle(flowTitle);
            JSONArray overseeType = new JSONArray();
            String serviceUnid = superSponsor.getServiceUnid();
            List<SuperServiceOverseeMapping> overlist = superServiceOverseeMappingService
                    .findByServiceUnidAndIsPrimary(serviceUnid, null);
            for (SuperServiceOverseeMapping over : overlist) {
                if (over != null && over.getStatus() == 1) {
                    overseeTypeTolc overseeTypeTolc = new overseeTypeTolc();
                    overseeTypeTolc.setCommandLeader(over.getCommandLeader());
                    overseeTypeTolc.setCommandSource(over.getCommandSource());
                    overseeTypeTolc.setIsPrimary(over.getIsPrimary());
                    overseeTypeTolc.setOverseeName(over.getOverseeName());
                    overseeTypeTolc.setOverseeUnid(over.getOverseeUnid());
                    if (ToolUtil.isNotEmpty(over.getServiceTime()) && !over.getServiceTime().contains(":")) {
                        overseeTypeTolc.setServiceTime(over.getServiceTime() + " 00:00:00");
                    } else {
                        overseeTypeTolc.setServiceTime(over.getServiceTime());
                    }
                    overseeTypeTolc.setServiceName(over.getServiceName());
                    overseeTypeTolc.setStatus(over.getStatus());
                    overseeTypeTolc.setTaskNote(over.getTaskNote());
                    //增加附件推送至流程平台----------lishuang
                    JSONArray files = new JSONArray();
                    String fileId = over.getFileId();
                    if (ToolUtil.isNotEmpty(fileId)) {
                        String ids[] = fileId.split(",");
                        for (String str : ids) {
                            if (ToolUtil.isNotEmpty(str)) {
                                SuperFile superFile = superFileService.findByFileId(str);
                                if (null != superFile) {
                                    FileTolc file = new FileTolc();
                                    file.setFileId(superFile.getFileId());
                                    file.setFileName(superFile.getFileName());
                                    file.setFileUrl(downloadUrl + superFile.getFileId());
                                    files.add(file);
                                }
                            }
                        }
                    }
                    overseeTypeTolc.setFileList(files);
                    overseeType.add(overseeTypeTolc);
                }
            }
            SponsorServiceTolc.setOverseeType(overseeType);
            // 事项
            SuperService superService = superServiceService.findByUnid(serviceUnid);
            SuperTypeService typeService = null;
            if (superService != null) {
                if (superService.getServiceType() != null) {
                    typeService = superTypeServiceService.findByUnid(superService.getServiceType());
                    SponsorServiceTolc.setServiceType(typeService.getTypeName());
                }
                SponsorServiceTolc.setBranchedLeader(superService.getBranchedLeader());
                SponsorServiceTolc.setServiceLevel(superService.getServiceLevel());
                SponsorServiceTolc.setOverseeUserid(superService.getOverseeUserid());
                SponsorServiceTolc.setOverseeUsername(superService.getOverseeUsername());
                SponsorServiceTolc.setOverseeFrequency(superService.getOverseeFrequency());

            }
            SponsorServiceTolc.setUnid(sponsorlog.getUnid());
            SponsorServiceTolc.setSponsorUnid(sponsorlog.getSponsorUnid());
            SponsorServiceTolc.setServiceUnid(serviceUnid);
            SponsorServiceTolc.setServiceId(superSponsor.getServiceId());
            SponsorServiceTolc.setServiceStatus(superSponsor.getServiceStatus());
            SponsorServiceTolc.setStatus(superSponsor.getStatus() + "");
            SponsorServiceTolc.setCreateTime(superSponsor.getCreateTime());
            SponsorServiceTolc.setCreateUserid(superSponsor.getCreateUserid());
            SponsorServiceTolc.setCreateUsername(superSponsor.getCreateUsername());
            SponsorServiceTolc.setOrgId(superSponsor.getOrgId());
            SponsorServiceTolc.setOrgName(superSponsor.getOrgName());
            // 闸口人信息推送
            if (ToolUtil.isNotEmpty(superSponsor.getOrgId()) && ToolUtil.isNotEmpty(superSponsor.getOrgName())) {
                String sql = "select sa.agentUserid as agentUserId,sa.agentUserName from super_lc_sponsor sl "
                        + "left join super_sponsor sp on (sp.org_id = sl.userid and sp.org_name = sl.empname) "
                        + "left join super_lc_sponsor_agent sa on (sl.userid = sa.userid and sl.empname = sa.empname) "
                        + "where sp.org_id = '"+superSponsor.getOrgId()+"' and sp.org_name = '"+superSponsor.getOrgName()+"' "
                        + "group BY sa.id ";
                Query query = entityManager.createNativeQuery(sql);
                query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperLcSponsorAgent.class));
                SuperLcSponsorAgent superLcSponsorAgent = (SuperLcSponsorAgent) query.getSingleResult();
                //新增部门督办扎口代理人agentUserid和agentUserName  --jinzhao  2019-10-16
                if (ToolUtil.isNotEmpty(superLcSponsorAgent)) {
                    SponsorServiceTolc.setAgentUserid(superLcSponsorAgent.getAgentUserId());
                    SponsorServiceTolc.setAgentUserName(superLcSponsorAgent.getAgentUserName());
                }
            }
            SponsorServiceTolc.setIsRead(superSponsor.getIsRead());
            SponsorServiceTolc.setSponsorId(superSponsor.getSponsorId());
            SponsorServiceTolc.setSponsorName(superSponsor.getSponsorName());
            SponsorServiceTolc.setFeedbackRule(superSponsor.getFeedbackRule());
            if (ToolUtil.isNotEmpty(superSponsor.getFeedbackDeadline()) && !superSponsor.getFeedbackDeadline().contains(":")) {
                SponsorServiceTolc.setFeedbackDeadline(superSponsor.getFeedbackDeadline() + " 00:00:00");
            } else {
                SponsorServiceTolc.setFeedbackDeadline(superSponsor.getFeedbackDeadline());
            }
            SponsorServiceTolc.setNote(superSponsor.getNote());
            SponsorServiceTolc.setNeedVerify(superSponsor.getNeedVerify());
            SponsorServiceTolc.setVerifiers(superSponsor.getVerifiers());
            //推送给流程平台的数据，加上三个字段------------lishuang
            SponsorServiceTolc.setResultForm(superSponsor.getResultForm());
            SponsorServiceTolc.setWorkPlan(superSponsor.getWorkPlan());
            if (ToolUtil.isNotEmpty(superSponsor.getProposedClosingTime())) {
                Date date = DateUtil.parse(superSponsor.getProposedClosingTime(), "yyyy-MM-dd HH:mm:ss");
                SponsorServiceTolc.setProposedClosingTime(DateUtil.getStrTime(date));
            } else {
                SponsorServiceTolc.setProposedClosingTime("");
            }
            //新增信息反馈人--lishuang 2019-08-21
            SponsorServiceTolc.setFeedbackUserid(superSponsor.getFeedbackUserid());
            SponsorServiceTolc.setFeedbackUsername(superSponsor.getFeedbackUsername());
            // params
            sponsorServicelog.add(SponsorServiceTolc);
        }

        if (type == 1) {
            // 日志记录
            LogManager.me()
                    .executeLog(LogTaskFactory.systemLog(null, null,
                            null, sponsorServicelog.toString(), 1, 1, null, null));
        } else if (type == 2) {
            // 日志记录
            LogManager.me()
                    .executeLog(LogTaskFactory.systemLog(null, null,
                            null, sponsorServicelog.toString(), 1, 2, null, null));
        }
        // 推送
        if (sponsorServicelog.size() > 0) {
            result = liuchengOperator.doOANext(sponsorServicelog);
            if (result == false) {
                logger.debug("........推送给流程平台失败.........");
                throw new BussinessException(500, "推送给流程平台失败");
            }
        }
        return result;

    }

    /**
     * 部门事项批量立项与督办,编辑页提交
     */
    @Override
    @Transactional
    public Boolean SuperServiceOperator(List<SuperSponsor> superSponsorlist,
                                        List<SuperServiceOverseeMapping> superServiceOverseeMappinglist, SuperService superService, int type
            , Integer frequency, String flowTitle) {
        Boolean result = false;
        JSONArray sponsorServicelog = new JSONArray();
        for (SuperSponsor superSponsor : superSponsorlist) {
            if (type == 1) {
                // 立项操作
                superSponsor.setServiceStatus("立项");
            } else if (type == 2) {
                // 督办操作
                superSponsor.setServiceStatus("督办");
                //督办时间
                superSponsor.setOverseeTime(DateUtil.getDay());
            }
            //下次督办时间
            if (frequency != null) {
                String nextTime = DateUtil.getAfterDayDate(frequency * 7 + "");
                if (ToolUtil.isNotEmpty(nextTime)) {
                    superSponsor.setNextTime(nextTime.substring(0, 10));
                }
            }
            // 督办部门历史记录
            SuperSponsorLog sponsorlog = superSponsorLogService.add(superSponsor);
            edit(superSponsor);

            // 推送给流程平台
            SponsorServiceTolc SponsorServiceTolc = new SponsorServiceTolc();
            SponsorServiceTolc.setFlowTitle(flowTitle);
            JSONArray overseeType = new JSONArray();
            String serviceUnid = superSponsor.getServiceUnid();
            for (SuperServiceOverseeMapping over : superServiceOverseeMappinglist) {
                if (over != null && over.getStatus() == 1) {
                    overseeTypeTolc overseeTypeTolc = new overseeTypeTolc();
                    overseeTypeTolc.setCommandLeader(over.getCommandLeader());
                    overseeTypeTolc.setCommandSource(over.getCommandSource());
                    overseeTypeTolc.setIsPrimary(over.getIsPrimary());
                    overseeTypeTolc.setOverseeName(over.getOverseeName());
                    overseeTypeTolc.setOverseeUnid(over.getOverseeUnid());
                    if (ToolUtil.isNotEmpty(over.getServiceTime()) && !over.getServiceTime().contains(":")) {
                        overseeTypeTolc.setServiceTime(over.getServiceTime() + " 00:00:00");
                    } else {
                        overseeTypeTolc.setServiceTime(over.getServiceTime());
                    }
                    overseeTypeTolc.setServiceName(over.getServiceName());
                    overseeTypeTolc.setStatus(over.getStatus());
                    overseeTypeTolc.setTaskNote(over.getTaskNote());
                    //增加附件推送至流程平台----------lishuang
                    JSONArray files = new JSONArray();
                    String fileId = over.getFileId();
                    if (ToolUtil.isNotEmpty(fileId)) {
                        String ids[] = fileId.split(",");
                        for (String str : ids) {
                            if (ToolUtil.isNotEmpty(str)) {
                                SuperFile superFile = superFileService.findByFileId(str);
                                if (null != superFile) {
                                    FileTolc file = new FileTolc();
                                    file.setFileId(superFile.getFileId());
                                    file.setFileName(superFile.getFileName());
                                    file.setFileUrl(downloadUrl + superFile.getFileId());
                                    files.add(file);
                                }
                            }
                        }
                    }
                    overseeTypeTolc.setFileList(files);
                    overseeType.add(overseeTypeTolc);
                }
            }
            SponsorServiceTolc.setOverseeType(overseeType);
            SuperTypeService typeService = null;
            if (superService != null) {
                if (superService.getServiceType() != null) {
                    typeService = superTypeServiceService.findByUnid(superService.getServiceType());
                    SponsorServiceTolc.setServiceType(typeService.getTypeName());
                }
                SponsorServiceTolc.setBranchedLeader(superService.getBranchedLeader());
                SponsorServiceTolc.setServiceLevel(superService.getServiceLevel());
                SponsorServiceTolc.setOverseeUserid(superService.getOverseeUserid());
                SponsorServiceTolc.setOverseeUsername(superService.getOverseeUsername());
                SponsorServiceTolc.setOverseeFrequency(superService.getOverseeFrequency());

            }
            SponsorServiceTolc.setUnid(sponsorlog.getUnid());
            SponsorServiceTolc.setSponsorUnid(sponsorlog.getSponsorUnid());
            SponsorServiceTolc.setServiceUnid(serviceUnid);
            SponsorServiceTolc.setServiceId(superSponsor.getServiceId());
            SponsorServiceTolc.setServiceStatus(superSponsor.getServiceStatus());
            SponsorServiceTolc.setStatus(superSponsor.getStatus() + "");
            SponsorServiceTolc.setCreateTime(superSponsor.getCreateTime());
            SponsorServiceTolc.setCreateUserid(superSponsor.getCreateUserid());
            SponsorServiceTolc.setCreateUsername(superSponsor.getCreateUsername());
            SponsorServiceTolc.setOrgId(superSponsor.getOrgId());
            SponsorServiceTolc.setOrgName(superSponsor.getOrgName());
            SponsorServiceTolc.setIsRead(superSponsor.getIsRead());
            SponsorServiceTolc.setSponsorId(superSponsor.getSponsorId());
            SponsorServiceTolc.setSponsorName(superSponsor.getSponsorName());
            SponsorServiceTolc.setFeedbackRule(superSponsor.getFeedbackRule());
            if (ToolUtil.isNotEmpty(superSponsor.getFeedbackDeadline()) && !superSponsor.getFeedbackDeadline().contains(":")) {
                SponsorServiceTolc.setFeedbackDeadline(superSponsor.getFeedbackDeadline() + " 00:00:00");
            } else {
                SponsorServiceTolc.setFeedbackDeadline(superSponsor.getFeedbackDeadline());
            }

            SponsorServiceTolc.setNote(superSponsor.getNote());
            SponsorServiceTolc.setNeedVerify(superSponsor.getNeedVerify());
            SponsorServiceTolc.setVerifiers(superSponsor.getVerifiers());
            //新增信息反馈人--lishuang 2019-08-21
            SponsorServiceTolc.setFeedbackUserid(superSponsor.getFeedbackUserid());
            SponsorServiceTolc.setFeedbackUsername(superSponsor.getFeedbackUsername());
            // 闸口人信息推送
            if (ToolUtil.isNotEmpty(superSponsor.getOrgId()) && ToolUtil.isNotEmpty(superSponsor.getOrgName())) {
                String sql = "select sa.agentUserid as agentUserId,sa.agentUserName from super_lc_sponsor sl "
                        + "left join super_sponsor sp on (sp.org_id = sl.userid and sp.org_name = sl.empname) "
                        + "left join super_lc_sponsor_agent sa on (sl.userid = sa.userid and sl.empname = sa.empname) "
                        + "where sp.org_id = '"+superSponsor.getOrgId()+"' and sp.org_name = '"+superSponsor.getOrgName()+"' "
                        + "group BY sa.id ";
                Query query = entityManager.createNativeQuery(sql);
                query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperLcSponsorAgent.class));
                SuperLcSponsorAgent superLcSponsorAgent = (SuperLcSponsorAgent) query.getSingleResult();
                //新增部门督办扎口代理人agentUserid和agentUserName  --jinzhao  2019-10-16
                if (ToolUtil.isNotEmpty(superLcSponsorAgent)) {
                    SponsorServiceTolc.setAgentUserid(superLcSponsorAgent.getAgentUserId());
                    SponsorServiceTolc.setAgentUserName(superLcSponsorAgent.getAgentUserName());
                }
            }
            // params
            sponsorServicelog.add(SponsorServiceTolc);
        }
        if (type == 1) {
            // 日志记录
            LogManager.me().executeLog(LogTaskFactory.systemLog(null, superService.getUnid(),
                    null, sponsorServicelog.toString(), 1, 1, null, null));
        } else if (type == 2) {
            // 日志记录
            LogManager.me().executeLog(LogTaskFactory.systemLog(null, superService.getUnid(),
                    null, sponsorServicelog.toString(), 1, 2, null, null));
        }
        // 推送
        if (sponsorServicelog.size() > 0) {
            result = liuchengOperator.doOANext(sponsorServicelog);
            if (!result) {
                logger.info("........推送给流程平台失败.........");
                throw new BussinessException(500, "推送给流程平台失败");
            }
        }
        return result;
    }

    //续加需求  jinzhao 2019-12-4  董监事会议定事项、银联国际需总公司支持事项、公司领导境外出访布置工作和公司领导调研期间关注的问题，四种事项大类都不需要推送到U聊行政中心里
    @Override
    public List<SuperSponserPage> selectPage(String type, Integer deptId, String createTimeStart, String createTimeEnd, Integer currentPage, Integer size) {

        String sql = "select sp.unid,sp.service_id as serviceId,se.oversee_name as overseeName, DATE_FORMAT(se.service_time,'%Y-%m-%d %H:%i:%S') serviceTime,se.service_name as serviceName,se.command_source as commandSource,se.command_leader as commandLeader,se.task_note as taskNote,se.service_type as serviceType,sp.org_id as orgId,sp.org_name as orgName,sp.is_read as isRead,sp.sponsor_id as sponsorId,sp.sponsor_name as sponsorName from super_service se left join super_sponsor sp on se.unid = sp.service_unid LEFT JOIN super_service_oversee_mapping sov on se.unid = sov.service_unid where se.status = 1 and sov.oversee_unid not in('9913','9914','9915','9921') ";

        if (ToolUtil.isNotEmpty(deptId)) {
            sql += " and sp.org_id in (SELECT s.userid FROM super_lc_sponsor s,om_user o WHERE o.userid = s.userid AND o.DEPTORGID = '"+deptId+"') ";
        }
        if (ToolUtil.isNotEmpty(type)) {
            if (Integer.parseInt(type) == 1) {
                // type=1是未办结的督办事项
                sql += " AND ( sp.service_status = '督办' or (sp.work_status like '推进中%' AND sp.service_status = '立项') ) ";
                if (ToolUtil.isNotEmpty(createTimeStart)){
                    sql += " and se.create_time >= '"+createTimeStart+"' ";
                }
                if (ToolUtil.isNotEmpty(createTimeEnd)){
                    sql += " and se.create_time <= '"+createTimeEnd+"' ";
                }
            } else if (Integer.parseInt(type) == 2) {
                // type=2是已办结的督办事项
                sql += " AND ( sp.work_status = '已完成' AND sp.service_status = '完成') ";
                if (ToolUtil.isNotEmpty(createTimeStart)) {
                    sql += " and sp.feedback_time >= '" + createTimeStart + "' ";
                }
                if (ToolUtil.isNotEmpty(createTimeEnd)) {
                    sql += " and sp.feedback_time <= '" + createTimeEnd + "' ";
                }
            }else if (Integer.parseInt(type) == 3){
                //type=3是已中止的督办事项
                sql += " and (sp.work_status in ('工作中止','工作终止') and sp.service_status = '中止') ";
                if (ToolUtil.isNotEmpty(createTimeStart)) {
                    sql += " and sp.feedback_time >= '" + createTimeStart + "' ";
                }
                if (ToolUtil.isNotEmpty(createTimeEnd)) {
                    sql += " and sp.feedback_time <= '" + createTimeEnd + "' ";
                }
            }
        }
        sql += " group by se.unid order by se.create_time desc ";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperSponserPage.class));
        if (ToolUtil.isNotEmpty(currentPage)) {
            query.setFirstResult((currentPage - 1) * size);
        }
        if (ToolUtil.isNotEmpty(size)) {
            query.setMaxResults(size);
        }
        return query.getResultList();
    }

    @Override
    public Integer getCount(String type, Integer deptId, String createTimeStart, String createTimeEnd) {

        String sql = "select sp.unid,sp.service_id as serviceId,se.oversee_name as overseeName,DATE_FORMAT(se.service_time,'%Y-%m-%d %H:%i:%S') serviceTime,se.service_name as serviceName,se.command_source as commandSource,se.command_leader as commandLeader,se.task_note as taskNote,se.service_type as serviceType,sp.org_id as orgId,sp.org_name as orgName,sp.is_read as isRead,sp.sponsor_id as sponsorId,sp.sponsor_name as sponsorName from super_service se left join super_sponsor sp on se.unid = sp.service_unid LEFT JOIN super_service_oversee_mapping sov on se.unid = sov.service_unid where se.status = 1 and sov.oversee_unid not in('9913','9914','9915','9921') ";

        if (ToolUtil.isNotEmpty(deptId)) {
            sql += " and sp.org_id in (SELECT s.userid FROM super_lc_sponsor s,om_user o WHERE o.userid = s.userid AND o.DEPTORGID = '"+deptId+"') ";
        }
        if (ToolUtil.isNotEmpty(type)) {
            if (Integer.parseInt(type) == 1) {
                // type=1是未办结的督办事项
                sql += " AND ( sp.service_status = '督办' or (sp.work_status like '推进中%' AND sp.service_status = '立项') ) ";
                if (ToolUtil.isNotEmpty(createTimeStart)){
                    sql += " and se.create_time >= '"+createTimeStart+"' ";
                }
                if (ToolUtil.isNotEmpty(createTimeEnd)){
                    sql += " and se.create_time <= '"+createTimeEnd+"' ";
                }
            } else if (Integer.parseInt(type) == 2) {
                // type=2是已办结的督办事项
                sql += " AND ( sp.work_status = '已完成' AND sp.service_status = '完成') ";
                if (ToolUtil.isNotEmpty(createTimeStart)) {
                    sql += " and sp.feedback_time >= '" + createTimeStart + "' ";
                }
                if (ToolUtil.isNotEmpty(createTimeEnd)) {
                    sql += " and sp.feedback_time <= '" + createTimeEnd + "' ";
                }
            }else if (Integer.parseInt(type) == 3){
                //type=3是已中止的督办事项
                sql += " and (sp.work_status in ('工作中止','工作终止') and sp.service_status = '中止') ";
                if (ToolUtil.isNotEmpty(createTimeStart)) {
                    sql += " and sp.feedback_time >= '" + createTimeStart + "' ";
                }
                if (ToolUtil.isNotEmpty(createTimeEnd)) {
                    sql += " and sp.feedback_time <= '" + createTimeEnd + "' ";
                }
            }
        }
        sql += " group by se.unid order by se.create_time desc ";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperSponserPage.class));

        return query.getResultList().size();
    }

    @Override
    public SuperSponsorOaSend selectByWorkStatus(String sponsorUnid,String serviceUnid) {
        SuperSponsorOaSend superSponsorOaSend = new SuperSponsorOaSend();

        String sql = " select sp.unid,se.oversee_name as overseeName,se.service_name as serviceName,se.task_note as taskNote,date_format(se.service_time,'%Y-%m-%d') as serviceTime,se.command_source as commandSource,se.command_leader as commandLeader,sp.service_id as serviceId,sp.work_status as workStatus,date_format(sp.feedback_time,'%H-%m-%d %H:%i:%S') as feedbackTime,sp.progress,cast((select om.DEPTORGID from om_user om where om.USERID = sp.org_id) as char) as deptId from super_service se left join super_sponsor sp on se.unid = sp.service_unid where se.unid = '" + serviceUnid+"' and sp.unid = '"+ sponsorUnid +"' ";
        //and sp.work_status in ('已完成','工作中止','工作终止') and sp.service_status in ('完成','中止')  and sp.status = '1' and se.status in ('-1','1') and se.service_status in ('完成','中止')

        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuperSponsorOaSend.class));
        superSponsorOaSend = (SuperSponsorOaSend) query.getSingleResult();
        return superSponsorOaSend;
    }

}
