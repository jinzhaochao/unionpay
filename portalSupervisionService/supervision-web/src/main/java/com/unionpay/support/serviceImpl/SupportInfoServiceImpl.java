package com.unionpay.support.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.sms.utils.WMResult;
import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.dao.OMUserRepository;
import com.unionpay.supervision.domain.OmOrganization;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.support.business.SendSupportNotice;
import com.unionpay.support.dao.SupportInfoRepository;
import com.unionpay.support.dao.SupportSendMsgLogRepository;
import com.unionpay.support.dao.SupportServerUserRepository;
import com.unionpay.support.model.*;
import com.unionpay.support.pojo.SupportInfo;
import com.unionpay.support.pojo.SupportSendMsgLog;
import com.unionpay.support.pojo.SupportServerUser;
import com.unionpay.support.service.SupportInfoService;
import com.unionpay.support.utils.WS_Send;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */
@Service
@Transactional
public class SupportInfoServiceImpl implements SupportInfoService {
    @Autowired
    private SupportInfoRepository supportInfoRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private OMUserRepository omUserRepository;
    @Autowired
    private OMOrganizationRepository omOrganizationRepository;
    @Value("${http.support.account}")
    private String account;
    @Value("${http.support.password}")
    private String password;
    @Value("${http.support.add}")
    private String addUrl;
    @Value("${http.support.accept}")
    private String acceptUrl;
    @Value("${http.support.revoke}")
    private String revokeUrl;
    @Value("${http.support.overAppointment}")
    private String overAppointmentUrl;
    @Value("${http.support.evaluate}")
    private String evaluateUrl;
    @Value("${http.support.evaluated}")
    private String evaluatedUrl;
    @Autowired
    private SendSupportNotice sendNotice;
    @Autowired
    private SupportSendMsgLogRepository supportSendMsgLogRepository;
    @Autowired
    private SupportServerUserRepository supportServerUserRepository;

    @Override
    public SupportInfo selectById(Integer id) {
        SupportInfo supportInfo = supportInfoRepository.findSupportInfoById(id);
        return supportInfo;
    }

    @Override
    public void save(SupportInfo supportInfo) {
        supportInfoRepository.save(supportInfo);
    }


    @Override
    public List<SupportPage> selectSupportInfo(Integer page, Integer size, String appointmentName, Integer serverUserId, String serverUserName, String timeStart, String timeEnd, Integer status, String userId, Integer tabPage) {
        String sql = " select si.id,si.number,DATE_FORMAT(si.create_time ,'%Y-%m-%d %H:%i:%S') as createTime,si.appointment_name as appointmentName,si.appointment_connection as appointmentConnection,si.question_type as questionType,si.question_category as questionCategory,si.support_type as supportType,si.my_place as myPlace,si.place_detail as placeDetail,si.excepted_day as exceptedDay,si.excepted_time as exceptedTime,si.alternative_day as alternativeDay,si.alternative_time as alternativeTime,si.emp_id as empId,si.emp_name as empName,si.emp_dept_id as empDeptId,si.emp_dept_name as empDeptName,si.reply_id as replyId,si.reply_name as replyName,DATE_FORMAT(si.reply_time ,'%Y-%m-%d %H:%i:%S') as replyTime,si.server_userid as serverUserId,si.server_username as serverUserName,si.server_day as serverDay,si.server_time as serverTime,si.server_reason as serverReason,si.evaluate_type as evaluateType,si.status,si.evaluate_content as evaluateContent,DATE_FORMAT(si.evaluate_time ,'%Y-%m-%d %H:%i:%S') as evaluateTime,ss.office_phone as officePhone,if(si.status = '2',(if(si.evaluate_time is null,1,0)),0) as isevaluate,if(si.status < 2,1,0) as isrevoke, (select sd.DICTNAME from sys_dict_entry sd where sd.DICTTYPEID = 'SUPPORT_QUESTION' and sd.DICTID = si.question_type) as dictName from support_info si left join support_server_user ss on si.server_userid = ss.id where 1=1 ";
        if (tabPage == 1) {
            sql += " and si.emp_id = '" + userId + "'  ";
        }
        if (tabPage == 2) {
//            sql += " and si.reply_id = '"+userId+"' ";
        }
        if (ToolUtil.isNotEmpty(status)) {
            sql += " and si.status = '" + status + "' ";
        }
        if (ToolUtil.isNotEmpty(appointmentName)) {
            sql += " and si.appointment_name like '%" + appointmentName + "%' ";
        }
        if (ToolUtil.isNotEmpty(serverUserId)) {
            sql += " and si.server_userid = '" + serverUserId + "' ";
        }
        if (ToolUtil.isNotEmpty(serverUserName)) {
            sql += " and si.server_username = '" + serverUserName + "' ";
        }
        if (ToolUtil.isNotEmpty(timeStart)) {
            sql += " and si.server_day >= '" + timeStart + "' ";
        }
        if (ToolUtil.isNotEmpty(timeEnd)) {
            sql += " and si.server_day <= '" + timeEnd + "' ";
        }
        sql += " order by si.create_time desc ";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SupportPage.class));
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<SupportPage> list = query.getResultList();
        for (SupportPage supportPage : list) {
            supportPage.setServerDateTime(supportPage.getServerDay() + " " + supportPage.getServerTime());
            supportPage.setIsEvaluate(Integer.parseInt(supportPage.getIsevaluate().toString()));
            supportPage.setIsRevoke(Integer.parseInt(supportPage.getIsrevoke().toString()));
        }
        return list;
    }

    @Override
    public Integer selectCount(String appointmentName, Integer serverUserId, String serverUserName, String timeStart, String timeEnd, Integer status, String userId, Integer tabPage) {
        String sql = " select si.id,si.number,DATE_FORMAT(si.create_time ,'%Y-%m-%d %H:%i:%S') as createTime,si.appointment_name as appointmentName,si.appointment_connection as appointmentConnection,si.question_type as questionType,si.question_category as questionCategory,si.support_type as supportType,si.my_place as myPlace,si.place_detail as placeDetail,si.excepted_day as exceptedDay,si.excepted_time as exceptedTime,si.alternative_day as alternativeDay,si.alternative_time as alternativeTime,si.emp_id as empId,si.emp_name as empName,si.emp_dept_id as empDeptId,si.emp_dept_name as empDeptName,si.reply_id as replyId,si.reply_name as replyName,DATE_FORMAT(si.reply_time ,'%Y-%m-%d %H:%i:%S') as replyTime,si.server_userid as serverUserId,si.server_username as serverUserName,si.server_day as serverDay,si.server_time as serverTime,si.server_reason as serverReason,si.evaluate_type as evaluateType,si.status,si.evaluate_content as evaluateContent,DATE_FORMAT(si.evaluate_time ,'%Y-%m-%d %H:%i:%S') as evaluateTime,ss.office_phone as officePhone,if(si.status = '2',(if(si.evaluate_time is null,1,0)),0) as isevaluate,if(si.status < 2,1,0) as isrevoke,(select sd.DICTNAME from sys_dict_entry sd where sd.DICTTYPEID = 'SUPPORT_QUESTION' and sd.DICTID = si.question_type) as dictName from support_info si left join support_server_user ss on si.server_userid = ss.id where 1=1 ";
        if (tabPage == 1) {
            sql += " and si.emp_id = '" + userId + "'  ";
        }
        if (tabPage == 2) {
//            sql += " and si.reply_id = '"+userId+"' ";
        }
        if (ToolUtil.isNotEmpty(status)) {
            sql += " and si.status = '" + status + "' ";
        }
        if (ToolUtil.isNotEmpty(appointmentName)) {
            sql += " and si.appointment_name like '%" + appointmentName + "%' ";
        }
        if (ToolUtil.isNotEmpty(serverUserId)) {
            sql += " and si.server_userid = '" + serverUserId + "' ";
        }
        if (ToolUtil.isNotEmpty(serverUserName)) {
            sql += " and si.server_username = '" + serverUserName + "' ";
        }
        if (ToolUtil.isNotEmpty(timeStart)) {
            sql += " and si.server_day >= '" + timeStart + "' ";
        }
        if (ToolUtil.isNotEmpty(timeEnd)) {
            sql += " and si.server_day <= '" + timeEnd + "' ";
        }
        sql += " order by si.create_time desc ";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SupportPage.class));
        List<SupportPage> list = query.getResultList();
        for (SupportPage supportPage : list) {
            supportPage.setServerDateTime(supportPage.getServerDay() + " " + supportPage.getServerTime());
            supportPage.setIsEvaluate(Integer.parseInt(supportPage.getIsevaluate().toString()));
            supportPage.setIsRevoke(Integer.parseInt(supportPage.getIsrevoke().toString()));
        }
        return list.size();
    }

    @Override
    public String selectAllCount() {
        String number = supportInfoRepository.select();
        return number;
    }

    @Override
    public ServerDateTime selectByMyplaceAndDatetime(String myPlace, String exceptedTime, String dateTime) {
        String sql = " select count(*) as countNum,si.server_time as serverTime from support_info si where 1=1 ";
        if (ToolUtil.isNotEmpty(myPlace)) {
            sql += " and si.my_place = :myPlace ";
        }
        if (ToolUtil.isNotEmpty(dateTime)) {
            sql += " and si.server_day = :dateTime ";
        }
        if (ToolUtil.isNotEmpty(exceptedTime)) {
            sql += " and si.server_time = :exceptedTime ";
        }
        Query query = entityManager.createNativeQuery(sql);
        if (ToolUtil.isNotEmpty(myPlace)) {
            query.setParameter("myPlace", myPlace);
        }
        if (ToolUtil.isNotEmpty(dateTime)) {
            query.setParameter("dateTime", dateTime);
        }
        if (ToolUtil.isNotEmpty(exceptedTime)) {
            query.setParameter("exceptedTime", exceptedTime);
        }

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerDateTime.class));
        ServerDateTime serverDateTime = (ServerDateTime) query.getSingleResult();
        serverDateTime.setCount(Integer.parseInt(serverDateTime.getCountNum().toString()));
        return serverDateTime;
    }

    @Override
    public List<ExcelSupportInfo> selectInfo(String timeStart, String timeEnd, Integer status, Integer serverUserId, String serverUserName, String ids) {
        List<ExcelSupportInfo> list = new ArrayList<>();
        try {

            String sql = " select si.id,si.number,si.appointment_name as appointmentName,(case si.status when 0 then '未处理' when 1 then '待服务' when 2 then '已完成' when 3 then '已撤销' end) as status,si.server_username as serverUserName,si.appointment_connection as officePhone,(select sd.DICTNAME from sys_dict_entry sd where sd.DICTTYPEID = 'SUPPORT_QUESTION' and sd.DICTID = si.support_type ) as questionType,(case si.support_type when 1 then '电话支持' when 2 then '现场支持' end) as supportType,concat(si.server_day ,' ', si.server_time) as serverDateTime,si.my_place as myPlace,si.place_detail as placeDetail,(case si.evaluate_type when 0 then '满意' when 1 then '不满意' end) as evaluateType,si.evaluate_content as evaluateContent from support_info  si where 1=1 ";
            if (ToolUtil.isNotEmpty(ids)) {
                String[] unid = ids.split(",");
                sql += " and si.id in (";
                for (int i = 0; i < unid.length; i++) {
                    if (ToolUtil.isEmpty(unid[i])) {
                        continue;
                    }
                    if (i < unid.length - 1) {
                        sql += "'" + unid[i] + "',";
                    } else {
                        sql += "'" + unid[i] + "')";
                    }
                }
            }
            if (ToolUtil.isNotEmpty(status)) {
                sql += " and si.status = " + status + " ";
            }
            if (ToolUtil.isNotEmpty(timeStart)) {
                sql += " and si.server_day >= '" + timeStart + "' ";
            }
            if (ToolUtil.isNotEmpty(timeEnd)) {
                sql += " and si.server_day <= '" + timeEnd + "' ";
            }
            if (ToolUtil.isNotEmpty(serverUserId)) {
                sql += " and si.server_userid = '" + serverUserId + "' ";
            }
            if (ToolUtil.isNotEmpty(serverUserName)) {
                sql += " and si.server_username = '" + serverUserName + "' ";
            }
            sql += " order by si.id desc ";
            Query query = entityManager.createNativeQuery(sql);
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ExcelSupportInfo.class));
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void add(SupportInfo supportInfo, OmUser omUser) {
        try {

            SupportInfo info = supportInfoRepository.findSupportInfoById(supportInfo.getId());
            info.setReplyId(omUser.getEmpid());
            //受理人name
            info.setReplyName(omUser.getEmpname());
            //受理时间
            info.setReplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            //服务人员id
            info.setServerUserId(supportInfo.getServerUserId());
            //服务人员名字
            info.setServerUserName(supportInfo.getServerUserName());
            //服务日期
            info.setServerDay(supportInfo.getServerDay());
            //服务时间段
            info.setServerTime(supportInfo.getServerTime());
            //支持类型
            info.setSupportType(supportInfo.getSupportType());
            //回复原因
            info.setServerReason(supportInfo.getServerReason());
            //受理状态  待服务
            info.setStatus(1);
            supportInfoRepository.save(info);
            // 已接受预约告知预约人 (您的预约成功啦! xxx 将在10-25（周五）9:00-10:00为您服务)
            SendSupportNoticeModel sendNoticeModelDto = new SendSupportNoticeModel();
            String week = new SimpleDateFormat("E").format(new SimpleDateFormat("yyyy-MM-dd").parse(supportInfo.getServerDay()));
            String strings = supportInfo.getServerDay().substring(5);
            sendNoticeModelDto.setTitle("您的预约成功啦! " + info.getServerUserName() + " 将在" + strings + "（周" + week.substring(2, 3) + "） " + supportInfo.getServerTime() + "为您服务");
            sendNoticeModelDto.setTypeId(6);
            // 预约人empid获取
            List<Integer> receiverList = new ArrayList<>();
            receiverList.add(info.getEmpId());
            sendNoticeModelDto.setReceivers(receiverList);
            sendNoticeModelDto.setUrl(acceptUrl + info.getId());
            sendNoticeModelDto.setAccount(account);
            sendNoticeModelDto.setPassword(password);
            boolean notice = sendNotice.sendNotice(sendNoticeModelDto);
            //日志保存
            SupportSendMsgLog log = new SupportSendMsgLog();
            if (notice) {
                log.setSendStatus("已发送");
            } else {
                log.setSendStatus("发送失败");
            }
            log.setSupportInfoId(info.getId());
            log.setData(JSON.toJSONString(sendNoticeModelDto));
            log.setSendUserid(omUser.getUserid());
            log.setReceiveUserid(omUserRepository.findByEmpid(info.getEmpId()).getUserid());
            log.setSendType("接受预约");
            log.setCreateTime(DateUtil.getStrTime(new Date()));
            supportSendMsgLogRepository.save(log);
        } catch (Exception e) {

        }
    }

    @Override
    public void update(Integer id) {
        SupportInfo info = supportInfoRepository.findSupportInfoById(id);
        //受理状态  已完成
        info.setStatus(2);
        supportInfoRepository.save(info);
        // 已完成预约 告知预约人评价  (您发起的由xxx处理的预约服务已完成，请及时进行评价哦)
        SendSupportNoticeModel sendNoticeModelDto = new SendSupportNoticeModel();
        OmUser omUser = omUserRepository.findByEmpid(info.getReplyId());
        sendNoticeModelDto.setTitle("您发起的由" + omUser.getEmpname() + "处理的预约服务已完成，请及时进行评价哦");
        sendNoticeModelDto.setTypeId(6);
        // 服务人empid获取
        ArrayList<Integer> receiverList = new ArrayList<>();
        receiverList.add(info.getEmpId());
        sendNoticeModelDto.setReceivers(receiverList);
        sendNoticeModelDto.setUrl(evaluateUrl + info.getId());
        sendNoticeModelDto.setAccount(account);
        sendNoticeModelDto.setPassword(password);
        boolean notice = sendNotice.sendNotice(sendNoticeModelDto);
        //日志保存
        SupportSendMsgLog log = new SupportSendMsgLog();
        if (notice) {
            log.setSendStatus("已发送");
        } else {
            log.setSendStatus("发送失败");
        }
        log.setSupportInfoId(info.getId());
        log.setData(JSON.toJSONString(sendNoticeModelDto));
        log.setSendUserid(omUserRepository.findByEmpid(info.getReplyId()).getUserid());
        log.setReceiveUserid(omUserRepository.findByEmpid(info.getEmpId()).getUserid());
        log.setSendType("完成处理");
        log.setCreateTime(DateUtil.getStrTime(new Date()));
        supportSendMsgLogRepository.save(log);
    }

    /**
     * 新增或修改预约
     *
     * @param appointmentDto
     * @param omUser
     */
    @Override
    public void addAppointment(AppointmentDto appointmentDto, OmUser omUser) {
        SupportInfo supportInfo = new SupportInfo();

        if (appointmentDto.getId() != null && !"".equals(appointmentDto.getId())) {
            if (appointmentDto.getId() > 0) {
                supportInfo = supportInfoRepository.findSupportInfoById(appointmentDto.getId());
            }
        } else {
            //创建时间
            supportInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            //编号
            String number = supportInfoRepository.select();
            if (number == null || "".equals(number)) {
                supportInfo.setNumber("ZC00000001");
            } else {
                DecimalFormat decimalFormat = new DecimalFormat("00000000");
                String codenew = number.substring(2, number.length());
                int i = Integer.parseInt(codenew) + 1;
                String str = "ZC";
                String k = str + decimalFormat.format(i);
                supportInfo.setNumber(k);
            }
            //提交人id
            supportInfo.setEmpId(omUser.getEmpid());
            //提交人name
            supportInfo.setEmpName(omUser.getEmpname());
            //提交人部门id
            supportInfo.setEmpDeptId(omUser.getDeptorgid());
            //提交人部门name
            OmOrganization omOrganization = omOrganizationRepository.findByOrgid(omUser.getDeptorgid());
            supportInfo.setEmpDeptName(omOrganization.getOrgname());
            //受理状态  未处理
            supportInfo.setStatus(0);

        }
        //预约人name
        supportInfo.setAppointmentName(appointmentDto.getAppointmentName());
        //问题类别（1网络故障；2硬件故障；3权限开通）
        supportInfo.setQuestionType(appointmentDto.getQuestionType());
        //预约人联系方式
        supportInfo.setAppointmentConnection(appointmentDto.getAppointmentConnection());
        //问题描述
        supportInfo.setQuestionCategory(appointmentDto.getQuestionCategory());
        //我的地点
        supportInfo.setMyPlace(appointmentDto.getMyPlace());
        //详细地点
        supportInfo.setPlaceDetail(appointmentDto.getPlaceDetail());
        // 期望日期
        supportInfo.setExceptedDay(appointmentDto.getExceptedDay());
        // 期望时间段
        supportInfo.setExceptedTime(appointmentDto.getExceptedTime());
        // 备选日期
        supportInfo.setAlternativeDay(appointmentDto.getAlternativeDay());
        // 备选时间段
        supportInfo.setAlternativeTime(appointmentDto.getAlternativeTime());
        supportInfoRepository.save(supportInfo);
        // 通知受理人有新预约 (您收到了1条来自xxx的预约，请及时处理哦)
        SendSupportNoticeModel sendNoticeModelDto = new SendSupportNoticeModel();
        // 系统管理员账号获取
        List<Integer> receiverList = get();
        sendNoticeModelDto.setTitle("您收到了1条来自" + omUser.getEmpname() + "的预约，请及时处理哦");
        sendNoticeModelDto.setTypeId(6);
        sendNoticeModelDto.setReceivers(receiverList);
        sendNoticeModelDto.setUrl(addUrl + supportInfo.getId());
        sendNoticeModelDto.setAccount(account);
        sendNoticeModelDto.setPassword(password);
        boolean notice = sendNotice.sendNotice(sendNoticeModelDto);
        //日志保存
        for (Integer receive : receiverList) {
            SupportSendMsgLog log = new SupportSendMsgLog();
            if (notice) {
                log.setSendStatus("已发送");
            } else {
                log.setSendStatus("发送失败");
            }
            log.setSupportInfoId(supportInfo.getId());
            log.setData(JSON.toJSONString(sendNoticeModelDto));
            log.setSendUserid(omUser.getUserid());
            log.setReceiveUserid(omUserRepository.findByEmpid(receive).getUserid());
            log.setSendType("发起预约");
            log.setCreateTime(DateUtil.getStrTime(new Date()));
            supportSendMsgLogRepository.save(log);
        }
    }

    @Override
    public SupportInfo updateStatus(Integer id) {
        SupportInfo supportInfo1 = new SupportInfo();
        Date dateTime = new Date();
        try {
            SupportInfo supportInfo = supportInfoRepository.findSupportInfoById(id);

            if (supportInfo.getStatus() == 1) {
                // 告知受理人有待服务的预约被撤销  (预约撤销：将由xxx在10-25（周五）9:00-10:00 进行服务的预约已撤销)
                SendSupportNoticeModel sendNoticeModelDto = new SendSupportNoticeModel();
                String week = new SimpleDateFormat("E").format(new SimpleDateFormat("yyyy-MM-dd").parse(supportInfo.getServerDay()));
                String strings = supportInfo.getServerDay().substring(5);
                sendNoticeModelDto.setTitle("预约撤销：将由" + supportInfo.getServerUserName() + "在" + strings + "（周" + week.substring(2, 3) + "） " + supportInfo.getServerTime() + "进行服务的预约已撤销");
                sendNoticeModelDto.setTypeId(6);
                //受理人账号获取
                List<Integer> receiverList = new ArrayList<>();
                receiverList.add(supportInfo.getReplyId());
                sendNoticeModelDto.setReceivers(receiverList);
                sendNoticeModelDto.setUrl(revokeUrl + id);
                sendNoticeModelDto.setAccount(account);
                sendNoticeModelDto.setPassword(password);
                boolean notice = sendNotice.sendNotice(sendNoticeModelDto);
                //日志保存
                SupportSendMsgLog log = new SupportSendMsgLog();
                if (notice) {
                    log.setSendStatus("已发送");
                } else {
                    log.setSendStatus("发送失败");
                }
                log.setSupportInfoId(supportInfo.getId());
                log.setData(JSON.toJSONString(sendNoticeModelDto));
                log.setSendUserid(omUserRepository.findByEmpid(supportInfo.getEmpId()).getUserid());
                log.setReceiveUserid(omUserRepository.findByEmpid(supportInfo.getReplyId()).getUserid());
                log.setSendType("邮件通知撤销预约");
                log.setCreateTime(DateUtil.getStrTime(new Date()));
                supportSendMsgLogRepository.save(log);
                //  短信通知服务人  如果撤销服务时间快到一小时之内的预约  (预约撤销:将由xxx在10-25 (周五) 9:00进行服务的预约已撤销)
                if (supportInfo.getServerDay().equals(new SimpleDateFormat("yyyy-MM-dd").format(dateTime))) {
                    String serverTime = supportInfo.getServerTime().substring(0, supportInfo.getServerTime().indexOf("-"));
                    String date = new SimpleDateFormat("HH:mm").format(dateTime);
                    long time = (new SimpleDateFormat("HH:mm").parse(serverTime).getTime() - new SimpleDateFormat("HH:mm").parse(date).getTime()) / (1000);
                    if (time <= 3600) {
                        JSONObject jsonObject = new JSONObject();
//                String serverTime = supportInfo.getServerTime().substring(0, supportInfo.getServerTime().indexOf("-"));
                        jsonObject.put("content", "预约撤销：将由" + supportInfo.getServerUserName() + "在" + strings + "（周" + week.substring(2, 3) + "） " + supportInfo.getServerTime() + "进行服务的预约已撤销");
                        SupportServerUser supportServerUser = supportServerUserRepository.findServerUserById(supportInfo.getServerUserId());
                        jsonObject.put("mobile", supportServerUser.getMobileno());
//                jsonObject.put("userid",);
                        //发送短信
                        WMResult wr = null;
                        wr = WS_Send.sendMsg(jsonObject.toJSONString());
                        //发送短信日志记录
                        if (wr.isSuccess()) {
                            log.setSendStatus("已发送");
                        } else {
                            log.setSendStatus("发送失败");
                        }
                        log.setSupportInfoId(supportInfo.getId());
                        log.setData(JSON.toJSONString(sendNoticeModelDto));
                        log.setSendUserid(omUserRepository.findByEmpid(supportInfo.getEmpId()).getUserid());
                        log.setReceiveUserid(omUserRepository.findByEmpid(supportInfo.getReplyId()).getUserid());
                        log.setCreateTime(DateUtil.getStrTime(new Date()));
                        log.setSendType("短信撤销提醒");
                        supportSendMsgLogRepository.save(log);
                    }
                }
            }
            if (supportInfo.getStatus() == 0 || supportInfo.getStatus() == 1) {
                supportInfo.setStatus(3);
            }
            supportInfo1 = supportInfoRepository.save(supportInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supportInfo1;
    }

    @Override
    public SupportInfo updateEvaluate(SupportInfo supportInfo) {
        SupportInfo support = supportInfoRepository.findSupportInfoById(supportInfo.getId());
        support.setEvaluateType(supportInfo.getEvaluateType());
        support.setEvaluateContent(supportInfo.getEvaluateContent());
        support.setEvaluateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        SupportInfo supportInfo1 = supportInfoRepository.save(support);
        // 告知受理人已经完成了评价  (您处理的来自xxx的预约服务收到了1条新评价)
        SendSupportNoticeModel sendNoticeModelDto = new SendSupportNoticeModel();
        OmUser omUser = omUserRepository.findByEmpid(support.getEmpId());
        sendNoticeModelDto.setTitle("您处理的来自" + omUser.getEmpname() + "的预约服务收到了1条新评价");
        sendNoticeModelDto.setTypeId(6);
        //受理人账号获取
        List<Integer> receiverList = new ArrayList<>();
        receiverList.add(support.getReplyId());
        sendNoticeModelDto.setReceivers(receiverList);
        sendNoticeModelDto.setUrl(evaluatedUrl + supportInfo.getId());
        sendNoticeModelDto.setAccount(account);
        sendNoticeModelDto.setPassword(password);
        boolean notice = sendNotice.sendNotice(sendNoticeModelDto);
        //日志保存
        SupportSendMsgLog log = new SupportSendMsgLog();
        if (notice) {
            log.setSendStatus("已发送");
        } else {
            log.setSendStatus("发送失败");
        }
        log.setSupportInfoId(support.getId());
        log.setData(JSON.toJSONString(sendNoticeModelDto));
        log.setSendUserid(omUserRepository.findByEmpid(support.getEmpId()).getUserid());
        log.setReceiveUserid(omUserRepository.findByEmpid(support.getReplyId()).getUserid());
        log.setSendType("评价");
        log.setCreateTime(DateUtil.getStrTime(new Date()));
        supportSendMsgLogRepository.save(log);

        return supportInfo1;
    }

    @Override
    public SupportInfo overAppointment(SupportInfo supportInfo, OmUser omUser) {
        SupportInfo info = supportInfoRepository.findSupportInfoById(supportInfo.getId());
        //受理人id

        info.setReplyId(omUser.getEmpid());
        //受理人name
        info.setReplyName(omUser.getEmpname());
        //受理时间
        info.setReplyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //受理状态  已完成--拒绝
        info.setServerReason(supportInfo.getServerReason());
        info.setStatus(2);
        SupportInfo supportInfo1 = supportInfoRepository.save(info);
        // 告知预约人什么原因结束了预约   (感谢你的预约，非常抱歉你遇到的问题超出了服务范围，请点击查看详情)
        SendSupportNoticeModel sendNoticeModelDto = new SendSupportNoticeModel();
        sendNoticeModelDto.setTitle("感谢您的预约，非常抱歉您遇到的问题超出了服务范围，请点击查看详情");
        sendNoticeModelDto.setTypeId(6);
        //预约人empid获取
        List<Integer> receiverList = new ArrayList<>();
        receiverList.add(info.getEmpId());
        sendNoticeModelDto.setReceivers(receiverList);
        sendNoticeModelDto.setUrl(overAppointmentUrl + info.getId());
        sendNoticeModelDto.setAccount(account);
        sendNoticeModelDto.setPassword(password);
        boolean notice = sendNotice.sendNotice(sendNoticeModelDto);
        //日志保存
        SupportSendMsgLog log = new SupportSendMsgLog();
        if (notice) {
            log.setSendStatus("已发送");
        } else {
            log.setSendStatus("发送失败");
        }
        log.setSupportInfoId(info.getId());
        log.setData(JSON.toJSONString(sendNoticeModelDto));
        log.setSendUserid(omUserRepository.findByEmpid(info.getReplyId()).getUserid());
        log.setReceiveUserid(omUserRepository.findByEmpid(info.getEmpId()).getUserid());
        log.setSendType("结束预约");
        log.setCreateTime(DateUtil.getStrTime(new Date()));
        supportSendMsgLogRepository.save(log);
        return supportInfo1;
    }

    @Override
    public List<SupportInfo> findAppointment(String dateTime) {
        String sql = " select si.id,si.number,DATE_FORMAT(si.create_time ,'%Y-%m-%d %H:%i:%S') as createTime,si.appointment_name as appointmentName,si.appointment_connection as appointmentConnection,si.question_type as questionType,si.question_category as questionCategory,si.support_type as supportType,si.my_place as myPlace,si.place_detail as placeDetail,si.excepted_day as exceptedDay,si.excepted_time as exceptedTime,si.alternative_day as alternativeDay,si.alternative_time as alternativeTime,si.emp_id as empId,si.emp_name as empName,si.emp_dept_id as empDeptId,si.emp_dept_name as empDeptName,si.reply_id as replyId,si.reply_name as replyName,DATE_FORMAT(si.reply_time ,'%Y-%m-%d %H:%i:%S') as replyTime,si.server_userid as serverUserId,si.server_username as serverUserName,si.server_day as serverDay,si.server_time as serverTime,si.server_reason as serverReason,si.evaluate_type as evaluateType,si.status,si.evaluate_content as evaluateContent,DATE_FORMAT(si.evaluate_time ,'%Y-%m-%d %H:%i:%S') as evaluateTime from support_info si where si.status = 1 ";
        if (ToolUtil.isNotEmpty(dateTime)) {
            sql += " and si.server_day = '" + dateTime + "'  ";
        }
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SupportInfo.class));
        return query.getResultList();
    }


    @Override
    public SupportPage selectAll(Integer id) {
        SupportPage supportPage = new SupportPage();
        try {

            String sql = " select si.id,si.number,DATE_FORMAT(si.create_time ,'%Y-%m-%d %H:%i:%S') as createTime,si.appointment_name as appointmentName,si.appointment_connection as appointmentConnection,si.question_type as questionType,si.question_category as questionCategory,si.support_type as supportType,si.my_place as myPlace,si.place_detail as placeDetail,si.excepted_day as exceptedDay,si.excepted_time as exceptedTime,si.alternative_day as alternativeDay,si.alternative_time as alternativeTime,si.emp_id as empId,si.emp_name as empName,si.emp_dept_id as empDeptId,si.emp_dept_name as empDeptName,si.reply_id as replyId,si.reply_name as replyName,DATE_FORMAT(si.reply_time ,'%Y-%m-%d %H:%i:%S') as replyTime,si.server_userid as serverUserId,si.server_username as serverUserName,si.server_day as serverDay,si.server_time as serverTime,si.server_reason as serverReason,si.evaluate_type as evaluateType,si.status,si.evaluate_content as evaluateContent,DATE_FORMAT(si.evaluate_time ,'%Y-%m-%d %H:%i:%S') as evaluateTime,ss.office_phone as officePhone,if(si.status = '2',(if(si.evaluate_time is null,1,0)),0) as isevaluate,if(si.status < 2,1,0) as isrevoke,(select sd.DICTNAME from sys_dict_entry sd where sd.DICTTYPEID = 'SUPPORT_QUESTION' and sd.DICTID = si.question_type) as dictName from support_info si left join support_server_user ss on si.server_userid = ss.id where si.id = :id ";
            Query query = entityManager.createNativeQuery(sql);
            if (ToolUtil.isNotEmpty(id)) {
                query.setParameter("id", id);
            }
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SupportPage.class));
            supportPage = (SupportPage) query.getSingleResult();
            supportPage.setServerDateTime(supportPage.getServerDay() + " " + supportPage.getServerTime());
            supportPage.setIsEvaluate(Integer.parseInt(supportPage.getIsevaluate().toString()));
            supportPage.setIsRevoke(Integer.parseInt(supportPage.getIsrevoke().toString()));

            //返回期望时间和备选时间的周日期
            if (ToolUtil.isNotEmpty(supportPage.getAlternativeDay())) {
                String week = new SimpleDateFormat("E").format(new SimpleDateFormat("yyyy-MM-dd").parse(supportPage.getAlternativeDay()));
                supportPage.setAlternativeWeek("周" + week.substring(2, 3));
            }
            String week1 = new SimpleDateFormat("E").format(new SimpleDateFormat("yyyy-MM-dd").parse(supportPage.getExceptedDay()));
            supportPage.setExceptedWeek("周" + week1.substring(2, 3));

        } catch (Exception e) {

        }
        return supportPage;
    }

    /**
     * 获取系统管理员empid
     *
     * @return
     */
    private List<Integer> get() {
        String sql = "SELECT p.FK_USER_ID FROM portal_mgmt_db.seed_join_user_role p LEFT JOIN om_user o ON o.EMPID = p.FK_USER_ID WHERE FK_ROLE_ID = '534258' AND o.EMPSTATUS <> '00'";
        Query query = entityManager.createNativeQuery(sql);
        List<Integer> list = query.getResultList();
        return list;
    }
}
