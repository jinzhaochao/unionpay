package com.unionpay.services.service;

import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.business.SendNotice;
import com.unionpay.services.model.*;
import com.unionpay.services.repository.ServerDeliverRepository;
import com.unionpay.services.repository.ServerGiveLikeRepository;
import com.unionpay.services.repository.ServerSuggestRepository;
import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.dao.OMUserRepository;
import com.unionpay.supervision.domain.OmOrganization;
import com.unionpay.supervision.domain.OmUser;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/20/020 14:26
 * @Description:
 */
@Service
@Transactional
public class ServerSuggestServiceImpl implements ServerSuggestService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ServerSuggestRepository serverSuggestRepository;
    @Autowired
    private ServerGiveLikeRepository serverGiveLikeRepository;
    @Autowired
    private OMUserRepository omUserRepository;
    @Autowired
    private OMOrganizationRepository omOrganizationRepository;
    @Autowired
    private ServerDeliverRepository serverDeliverRepository;
    @Autowired
    private SendNotice sendNotice;
    @Value("${http.service.account}")
    private String account;
    @Value("${http.service.password}")
    private String password;
    @Value("${http.server.suggest}")
    private String suggestUrl;
    @Value("${http.server.suggest.docomplete}")
    private String suggestDoComplete;
    //咨询已完成详情页
    @Value("${http.server.suggest.complete}")
    private String completeSuggestUrl;
    @Value("${http.server.complain}")
    private String complaintUrl;
    @Value("${http.server.complain.complete}")
    private String completeComplainUrl;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public ServerSuggest add(ServerSuggest serverSuggest) {
        return serverSuggestRepository.save(serverSuggest);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void delete(Integer id) {
        serverSuggestRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public ServerSuggest update(ServerSuggest serverSuggest) {
        return serverSuggestRepository.saveAndFlush(serverSuggest);
    }

    public ServerSuggest get(Integer id) {
        return serverSuggestRepository.getOne(id);
    }

    public List<ServerSuggest> getAll() {
        return serverSuggestRepository.findAll();
    }

    public Page<ServerSuggest> getPage(Integer page, Integer rows) {
        Pageable pageable = new PageRequest(page - 1, rows);
        return serverSuggestRepository.findAll(pageable);
    }

    /**
     * 服务反馈信息查询与分页
     *
     * @param orgId
     * @param status
     * @return
     * @author lishuang
     * @date 2019-03-14
     */
    public List<ServerSuggestModel> SelectAllSuggest(Integer page, Integer size, Integer orgId, Integer status) {
        String jpql = "SELECT ss.id,ss.title,ss.emp_id,ss.emp_dept,ss.status,DATE_FORMAT(ss.createtime,'%Y-%m-%d %H:%i:%S') as createtime,ss.server_describe,ss.other_suggest,\n" +
                " ss.reply_reason,ss.emp_dept_id,ss.emp_org_id,ss.emp_name,ss.reply_id,DATE_FORMAT(ss.reply_time,'%Y-%m-%d %H:%i:%S') reply_time\n" +
                " FROM server_suggest ss WHERE 1=1";
        if (ToolUtil.isNotEmpty(orgId)) {
            jpql += " and (ss.emp_dept_id = :orgId or ss.emp_org_id = :orgId)";
        }
        if (ToolUtil.isNotEmpty(status)) {
            jpql += " and ss.status = :status";
        }
        jpql += " order by ss.createtime desc";
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(orgId)) {
            query.setParameter("orgId", orgId);
        }
        if (ToolUtil.isNotEmpty(status)) {
            query.setParameter("status", status);
        }

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerSuggestModel.class));
        List<ServerSuggestModel> serverSuggests1 = query.getResultList();
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<ServerSuggestModel> serverSuggests = query.getResultList();
        /*JSONObject json = new JSONObject();
        json.put("data", serverSuggests);
        json.put("total", serverSuggests1.size());*/
        return serverSuggests;
    }

    @Override
    public Integer getCount(Integer page, Integer size, Integer orgId, Integer status) {
        int count = 0;
        String jpql = "SELECT ss.id,ss.title,ss.emp_id,ss.emp_dept,ss.status,DATE_FORMAT(ss.createtime,'%Y-%m-%d %H:%i:%S') as createtime,ss.server_describe,ss.other_suggest,\n" +
                " ss.reply_reason,ss.emp_dept_id,ss.emp_org_id,ss.emp_name,ss.reply_id,DATE_FORMAT(ss.reply_time,'%Y-%m-%d %H:%i:%S') reply_time\n" +
                " FROM server_suggest ss WHERE 1=1";
        if (ToolUtil.isNotEmpty(orgId)) {
            jpql += " and (ss.emp_dept_id = :orgId or ss.emp_org_id = :orgId)";
        }
        if (ToolUtil.isNotEmpty(status)) {
            jpql += " and ss.status = :status";
        }
        jpql += " order by ss.createtime desc";
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(orgId)) {
            query.setParameter("orgId", orgId);
        }
        if (ToolUtil.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerSuggestModel.class));
        List<ServerSuggestModel> serverSuggests1 = query.getResultList();
        if (ToolUtil.isNotEmpty(serverSuggests1)) {
            count = serverSuggests1.size();
        }
        return count;
    }

    @Override
    public ServerSuggest findById(Integer id) {
        String jpql = "select * from server_suggest ss WHERE ss.id = :id";
        Query query = em.createNativeQuery(jpql);
        query.setParameter("id", id);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerSuggest.class));
        ServerSuggest serverSuggest = (ServerSuggest) query.getSingleResult();
        return serverSuggest;
    }

    @Override
    public ServerSuggest edit(ServerSuggest serverSuggest) {
        ServerSuggest suggest = new ServerSuggest();
        suggest = serverSuggestRepository.save(serverSuggest);
        return suggest;
    }

    @Override
    public List<ServerSuggestAndGiveLikeModel> selectSuggestAll(Integer page, Integer size, String title, Integer type, Integer empDeptId, String empName, Date startTime, Date endTime, Integer status, Integer isDeliver, Integer tabPage, String userId) {
        List<ServerSuggestAndGiveLikeModel> serverSuggestAndGiveLikeModelList = new ArrayList<>();
        try {

            String sql = "select ss.id,ss.server_id as serverId,ss.title,ss.other_describe as otherDescribe,ss.emp_name as empName,ss.emp_id as empId,ss.emp_dept_id as empDeptId,ss.emp_org_id as empOrgId,ss.emp_dept_name as empDeptName,DATE_FORMAT(ss.createtime,'%Y-%m-%d %H:%i:%S') as createtime,ss.status,ss.reply_id as replyId,ss.reply_name as replyName,DATE_FORMAT(ss.reply_time ,'%Y-%m-%d %H:%i:%S') as replyTime,ss.reply_reason as replyReason,ss.type,ss.is_deliver as isDeliver,count(s.id) as giveLikeCount,( SELECT gl.STATUS FROM server_give_like gl WHERE gl.user_id = :userId AND gl.suggestion_id = ss.id ) AS likeStatus FROM server_suggest ss left JOIN server_give_like s ON ss.id = s.suggestion_id and s.status = '1' WHERE 1 =1 ";
            if (ToolUtil.isNotEmpty(tabPage)) {
                if (tabPage == 1) {
                    sql += " and ss.emp_id = :userId ";
                }
                if (tabPage == 2) {
                    sql += " and ss.reply_id = :userId ";
                }
                if (tabPage == 3) {
                    sql += " and ss.status = '2' ";
                }
            }
            if (ToolUtil.isNotEmpty(title)) {
                sql += " and ss.title like CONCAT('%',:title,'%') ";
            }
            if (ToolUtil.isNotEmpty(type)) {
                sql += " and ss.type = :type ";
            } else if (ToolUtil.isEmpty(type)) {
                sql += " and ss.type in ('1','2','3') ";
            }
            if (ToolUtil.isNotEmpty(empName)) {
                sql += " and ss.emp_name like CONCAT('%',:empName, '%') ";
            }
            if (ToolUtil.isNotEmpty(empDeptId)) {
                sql += " and (ss.emp_dept_id = '" + empDeptId + "' || ss.emp_org_id = '" + empDeptId + "') ";
            }
            if (ToolUtil.isNotEmpty(startTime)) {
                sql += " and ss.createtime >= :startTime ";
            }
            if (ToolUtil.isNotEmpty(endTime)) {
                sql += " and ss.createtime <= :endTime ";
            }
            if (ToolUtil.isNotEmpty(status)) {
                sql += " and ss.status = :status ";
            }
            if (ToolUtil.isNotEmpty(isDeliver)) {
                sql += " and ss.is_deliver = :isDeliver ";
            }
            sql += " GROUP BY ss.id ";
            //我的回复已处理的按处理时间倒序  --jinzhao  2019-12-18
            if (ToolUtil.isNotEmpty(tabPage) && ToolUtil.isNotEmpty(status)) {
                if (tabPage == 2 && status == 2) {
                    sql += " order by ss.reply_time desc ";
                } else {
                    sql += " order by ss.createtime desc  ";
                }
            } else {
                sql += " order by ss.createtime desc  ";
            }

            Query query = em.createNativeQuery(sql);
            if (ToolUtil.isNotEmpty(title)) {
                query.setParameter("title", title);
            }
            if (ToolUtil.isNotEmpty(type)) {
                query.setParameter("type", type);
            }
            if (ToolUtil.isNotEmpty(empName)) {
                query.setParameter("empName", empName);
            }
//            if (ToolUtil.isNotEmpty(empDeptId)) {
//                query.setParameter("empDeptId", empDeptId);
//            }
            if (ToolUtil.isNotEmpty(startTime)) {
                query.setParameter("startTime", startTime);
            }
            if (ToolUtil.isNotEmpty(endTime)) {
                query.setParameter("endTime", endTime);
            }
            if (ToolUtil.isNotEmpty(status)) {
                query.setParameter("status", status);
            }
            if (ToolUtil.isNotEmpty(isDeliver)) {
                query.setParameter("isDeliver", isDeliver);
            }
            if (ToolUtil.isNotEmpty(userId)) {
                query.setParameter("userId", userId);
            }

            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerSuggestAndGiveLikeModel.class));
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            serverSuggestAndGiveLikeModelList = query.getResultList();
            for (ServerSuggestAndGiveLikeModel serverSuggestAndGiveLikeModel : serverSuggestAndGiveLikeModelList) {
                //jinzhao  2019-12-19
                Integer giveLikeStatus = 0;
                if (ToolUtil.isNotEmpty(serverSuggestAndGiveLikeModel.getLikeStatus())) {
                    giveLikeStatus = Integer.parseInt(serverSuggestAndGiveLikeModel.getLikeStatus().toString());
                }
                serverSuggestAndGiveLikeModel.setGiveLikeStatus(giveLikeStatus);
//                if (ToolUtil.isEmpty(serverSuggestAndGiveLikeModel.getGiveLikeStatus())){
//                    serverSuggestAndGiveLikeModel.setGiveLikeStatus(0);
//                }
                if (ToolUtil.isNotEmpty(tabPage)) {
                    if (tabPage == 2) {
                        serverSuggestAndGiveLikeModel.setIsGiveLike(0);
                    } else if (tabPage == 1 || tabPage == 3) {
                        serverSuggestAndGiveLikeModel.setIsGiveLike(1);
                    }
                }
                //根据提交时间和回复时间计算反馈消耗时长
                if (serverSuggestAndGiveLikeModel.getCreatetime() != null && !"".equals(serverSuggestAndGiveLikeModel.getCreatetime()) && serverSuggestAndGiveLikeModel.getReplyTime() != null && !"".equals(serverSuggestAndGiveLikeModel.getReplyTime())) {
                    serverSuggestAndGiveLikeModel.setTimeLength((new SimpleDateFormat("yyyy-MM-dd").parse(serverSuggestAndGiveLikeModel.getReplyTime()).getTime() - new SimpleDateFormat("yyyy-MM-dd").parse(serverSuggestAndGiveLikeModel.getCreatetime()).getTime()) / (1000 * 24 * 60 * 60) + 1 + "天");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverSuggestAndGiveLikeModelList;
    }


    @Override
    public Integer getTotal(String title, Integer type, Integer empDeptId, String empName, Date startTime, Date endTime, Integer status, Integer isDeliver, Integer tabPage, String userId) {
        int total = 0;

        String sql = "select ss.id,ss.server_id as serverId,ss.title,ss.other_describe as otherDescribe,ss.emp_name as empName,ss.emp_id as empId,ss.emp_dept_id as empDeptId,ss.emp_org_id as empOrgId,ss.emp_dept_name as empDeptName,DATE_FORMAT(ss.createtime,'%Y-%m-%d %H:%i:%S') as createtime,ss.status,ss.reply_id as replyId,DATE_FORMAT(ss.reply_time,'%Y-%m-%d %H:%i:%S') as replyTime ,ss.reply_reason as replyReason,ss.type,ss.is_deliver as isDeliver,count(s.id) as giveLikeCount,(select gl.`status` from server_give_like gl where gl.user_id = :userId AND gl.suggestion_id= ss.id) as likeStatus FROM server_suggest ss left JOIN server_give_like s ON ss.id = s.suggestion_id and s.status = '1' WHERE 1 =1 ";
        if (ToolUtil.isNotEmpty(tabPage)) {
            if (tabPage == 1) {
                sql += " and ss.emp_id = :userId ";
            }
            if (tabPage == 2) {
                sql += " and ss.reply_id = :userId ";
            }
            if (tabPage == 3) {
                sql += " and ss.status = '2' ";
            }
        }
        if (ToolUtil.isNotEmpty(title)) {
            sql += " and ss.title like CONCAT('%',:title,'%') ";
        }
        if (ToolUtil.isNotEmpty(type)) {
            sql += " and ss.type = :type ";
        } else if (ToolUtil.isEmpty(type)) {
            sql += " and ss.type in ('1','2','3') ";
        }
        if (ToolUtil.isNotEmpty(empName)) {
            sql += " and ss.emp_name like CONCAT('%', :empName, '%') ";
        }
        if (ToolUtil.isNotEmpty(empDeptId)) {
            sql += " and (ss.emp_dept_id = '" + empDeptId + "' || ss.emp_org_id = '" + empDeptId + "') ";
        }
        if (ToolUtil.isNotEmpty(startTime)) {
            sql += " and ss.createtime >= :startTime ";
        }
        if (ToolUtil.isNotEmpty(endTime)) {
            sql += " and ss.createtime <= :endTime ";
        }
        if (ToolUtil.isNotEmpty(status)) {
            sql += " and ss.status = :status ";
        }
        if (ToolUtil.isNotEmpty(isDeliver)) {
            sql += " and ss.is_deliver = :isDeliver ";
        }
        sql += " GROUP BY ss.id ";
        //我的回复已处理的按处理时间倒序  --jinzhao  2019-12-18
        if (ToolUtil.isNotEmpty(tabPage) && ToolUtil.isNotEmpty(status)) {
            if (tabPage == 2 && status == 2) {
                sql += " order by ss.reply_time desc ";
            } else {
                sql += " order by ss.createtime desc  ";
            }
        } else {
            sql += " order by ss.createtime desc  ";
        }

        Query query = em.createNativeQuery(sql);
        if (ToolUtil.isNotEmpty(title)) {
            query.setParameter("title", title);
        }
        if (ToolUtil.isNotEmpty(type)) {
            query.setParameter("type", type);
        }
        if (ToolUtil.isNotEmpty(empName)) {
            query.setParameter("empName", empName);
        }
//        if (ToolUtil.isNotEmpty(empDeptId)) {
//            query.setParameter("empDeptId", empDeptId);
//        }
        if (ToolUtil.isNotEmpty(startTime)) {
            query.setParameter("startTime", startTime);
        }
        if (ToolUtil.isNotEmpty(endTime)) {
            query.setParameter("endTime", endTime);
        }
        if (ToolUtil.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ToolUtil.isNotEmpty(isDeliver)) {
            query.setParameter("isDeliver", isDeliver);
        }
        if (ToolUtil.isNotEmpty(userId)) {
            query.setParameter("userId", userId);
        }

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerSuggestAndGiveLikeModel.class));
        List<ServerSuggestAndGiveLikeModel> serverSuggestList = query.getResultList();
        if (ToolUtil.isNotEmpty(serverSuggestList)) {
            total = serverSuggestList.size();
        }
        return total;
    }

    @Override
    public void update(Integer id, String userId) {
        ServerGiveLike serverGiveLike1 = new ServerGiveLike();

        //根据反馈关联id和点赞人id查询点赞情况
        ServerGiveLike serverGiveLike = serverGiveLikeRepository.findAllBySuggestionIdAndUserId(id, userId);
        if (serverGiveLike != null && !"".equals(serverGiveLike)) {
            Integer status = serverGiveLike.getStatus();
            //先判断是否有点赞记录，再判断状态进行点赞操作
//        if (status != null || !"".equals(status)) {
            if (status == 0) {
                serverGiveLike.setStatus(1);
            } else if (status == 1) {
                serverGiveLike.setStatus(0);
            }
            serverGiveLikeRepository.save(serverGiveLike);

        } else if (serverGiveLike == null || "".equals(serverGiveLike)) {
            serverGiveLike1.setSuggestionId(id);
            serverGiveLike1.setUserId(userId);
            serverGiveLike1.setStatus(1);
            serverGiveLike1.setCreateTime(new Date());
            serverGiveLikeRepository.save(serverGiveLike1);
        }
    }

    @Override
    public ServerSuggestAndGiveLikeModel select(Integer id, String userId, Integer tabPage) {
        ServerSuggestAndGiveLikeModel serverSuggestAndGiveLikeModel = new ServerSuggestAndGiveLikeModel();
        try {

            String sql = "select ss.id,ss.server_id as serverId,ss.title,ss.other_describe as otherDescribe,ss.emp_name as empName,ss.emp_id as empId,ss.emp_dept_id as empDeptId,ss.emp_org_id as empOrgId,ss.emp_dept_name as empDeptName,DATE_FORMAT(ss.createtime,'%Y-%m-%d %H:%i:%S') as createtime,ss.status,ss.reply_id as replyId,ss.reply_name as replyName,DATE_FORMAT(ss.reply_time,'%Y-%m-%d %H:%i:%S') as replyTime,ss.reply_reason as replyReason,ss.type,ss.is_deliver as isDeliver,count(s.id) as giveLikeCount,(select gl.status from server_give_like gl where gl.user_id = :userId AND gl.suggestion_id= ss.id) as giveLikeStatus FROM server_suggest ss left JOIN server_give_like s ON ss.id = s.suggestion_id and s.status = '1' WHERE ss.id = :id ";
            Query query = em.createNativeQuery(sql);
            if (ToolUtil.isNotEmpty(userId)) {
                query.setParameter("userId", userId);
            }
            if (ToolUtil.isNotEmpty(id)) {
                query.setParameter("id", id);
            }
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerSuggestAndGiveLikeModel.class));
            serverSuggestAndGiveLikeModel = (ServerSuggestAndGiveLikeModel) query.getSingleResult();
            if (ToolUtil.isNotEmpty(serverSuggestAndGiveLikeModel)) {
                if (ToolUtil.isEmpty(serverSuggestAndGiveLikeModel.getGiveLikeStatus())) {
                    serverSuggestAndGiveLikeModel.setGiveLikeStatus(0);
                }
            }
            if (tabPage == 2) {
                serverSuggestAndGiveLikeModel.setIsGiveLike(0);
            } else if (tabPage == 1 || tabPage == 3) {
                serverSuggestAndGiveLikeModel.setIsGiveLike(1);
            }
            //根据提交时间和回复时间计算反馈消耗时长
            serverSuggestAndGiveLikeModel.setTimeLength((new SimpleDateFormat("yyyy-MM-dd").parse(serverSuggestAndGiveLikeModel.getReplyTime()).getTime() - new SimpleDateFormat("yyyy-MM-dd").parse(serverSuggestAndGiveLikeModel.getCreatetime()).getTime()) / (1000 * 24 * 60 * 60) + 1 + "天");
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return serverSuggestAndGiveLikeModel;
    }

    @Override
    public ServerSuggestModel selectComplaint(Integer id) {
        ServerSuggestModel serverSuggestModel = new ServerSuggestModel();
        try {
            String sql = " select ss.id,ss.server_id as serverId,ss.title,ss.other_describe as otherDescribe,ss.emp_name as empName,ss.emp_id as empId,ss.emp_dept_id as empDeptId,ss.emp_org_id as empOrgId,ss.emp_dept_name as empDeptName,DATE_FORMAT(ss.createtime,'%Y-%m-%d %H:%i:%S') as createtime,ss.status,ss.reply_id as replyId,ss.reply_name as replyName,DATE_FORMAT(ss.reply_time,'%Y-%m-%d %H:%i:%S') as replyTime,ss.reply_reason as replyReason,ss.type,ss.is_deliver as isDeliver,ss.is_anonymous as isAnonymous,ss.complaint_dept_id as complaintDeptId,ss.complaint_dept_name as complaintDeptName from server_suggest ss where ss.id = :id ";
            Query query = em.createNativeQuery(sql);
            if (ToolUtil.isNotEmpty(id)) {
                query.setParameter("id", id);
            }
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerSuggestModel.class));
            serverSuggestModel = (ServerSuggestModel) query.getSingleResult();
//            serverSuggestModel = serverSuggestRepository.findComplaintById(id);
            if (serverSuggestModel.getIsAnonymous() == 1) {
                serverSuggestModel.setEmpName("匿名");
            }
            //根据提交时间和回复时间计算反馈消耗时长
            serverSuggestModel.setTimeLength((new SimpleDateFormat("yyyy-MM-dd").parse(serverSuggestModel.getReplyTime()).getTime() - new SimpleDateFormat("yyyy-MM-dd").parse(serverSuggestModel.getCreatetime()).getTime()) / (1000 * 24 * 60 * 60) + 1 + "天");
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return serverSuggestModel;
    }

    @Override
    public List<ServerSuggestModel> selectComplaintAll(Integer page, Integer size, String title, Integer type, Integer empDeptId, String empName, Date startTime, Date endTime, Integer status, Integer isDeliver, Integer tabPage, String userId) {
        List<ServerSuggestModel> serverSuggestModelList = new ArrayList<>();
        try {

            String sql = "select ss.id,ss.server_id as serverId,ss.title,ss.other_describe as otherDescribe,ss.emp_name as empName,ss.emp_id as empId,ss.emp_dept_id as empDeptId,ss.emp_org_id as empOrgId,ss.emp_dept_name as empDeptName,DATE_FORMAT(ss.createtime,'%Y-%m-%d %H:%i:%S') createtime,ss.status,ss.reply_id as replyId,ss.reply_name as replyName,DATE_FORMAT(ss.reply_time,'%Y-%m-%d %H:%i:%S') replyTime,ss.reply_reason as replyReason,ss.type,ss.is_deliver as isDeliver,ss.is_anonymous as isAnonymous,ss.complaint_dept_id as complaintDeptId,ss.complaint_dept_name as complaintDeptName FROM server_suggest ss WHERE 1 =1 ";
            if (tabPage == 1) {
                sql += " and ss.emp_id = :userId ";
            }
            if (tabPage == 2) {
                sql += " and ss.reply_id = :userId ";
            }
            if (tabPage == 3) {
                sql += " and ss.status = '2' ";
            }
            if (ToolUtil.isNotEmpty(title)) {
                sql += " and ss.title like CONCAT('%',:title,'%') ";
            }
            if (ToolUtil.isNotEmpty(type)) {
                sql += " and ss.type = :type ";
            } else if (ToolUtil.isEmpty(type)) {
                sql += " and ss.type in ('4','5') ";
            }
            if (ToolUtil.isNotEmpty(empName)) {
                sql += " and ss.emp_name like CONCAT('%', :empName, '%') ";
            }
            if (ToolUtil.isNotEmpty(empDeptId)) {
                sql += " and (ss.emp_dept_id = '" + empDeptId + "' || ss.emp_org_id = '" + empDeptId + "') ";
            }

            if (ToolUtil.isNotEmpty(startTime)) {
                sql += " and ss.createtime >= :startTime ";
            }
            if (ToolUtil.isNotEmpty(endTime)) {
                sql += " and ss.createtime <= :endTime ";
            }
            if (ToolUtil.isNotEmpty(status)) {
                sql += " and ss.status = :status ";
            }
            if (ToolUtil.isNotEmpty(isDeliver)) {
                sql += " and ss.is_deliver = :isDeliver ";
            }
            sql += "  order by ss.createtime desc ";
            Query query = em.createNativeQuery(sql);
            if (ToolUtil.isNotEmpty(title)) {
                query.setParameter("title", title);
            }
            if (ToolUtil.isNotEmpty(type)) {
                query.setParameter("type", type);
            }
            if (ToolUtil.isNotEmpty(empName)) {
                query.setParameter("empName", empName);
            }
//            if (ToolUtil.isNotEmpty(empDeptId)) {
//                query.setParameter("empDeptId", empDeptId);
//            }
            if (ToolUtil.isNotEmpty(startTime)) {
                query.setParameter("startTime", startTime);
            }
            if (ToolUtil.isNotEmpty(endTime)) {
                query.setParameter("endTime", endTime);
            }
            if (ToolUtil.isNotEmpty(status)) {
                query.setParameter("status", status);
            }
            if (ToolUtil.isNotEmpty(isDeliver)) {
                query.setParameter("isDeliver", isDeliver);
            }
            if (ToolUtil.isNotEmpty(userId)) {
                query.setParameter("userId", userId);
            }

            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerSuggestModel.class));
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            serverSuggestModelList = query.getResultList();
            for (ServerSuggestModel serverSuggestModel : serverSuggestModelList) {
                if (serverSuggestModel.getIsAnonymous() == 1) {
                    serverSuggestModel.setEmpName("匿名");
                }
                if (ToolUtil.isNotEmpty(serverSuggestModel.getReplyTime())) {
                    serverSuggestModel.setTimeLength((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(serverSuggestModel.getReplyTime()).getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(serverSuggestModel.getCreatetime()).getTime()) / (1000 * 24 * 60 * 60) + 1 + "天");
                } else {
                    serverSuggestModel.setTimeLength("0天");
                }
                //根据提交时间和回复时间计算反馈消耗时长
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverSuggestModelList;
    }

    @Override
    public Integer getCountTotal(String title, Integer type, Integer empDeptId, String empName, Date startTime, Date endTime, Integer status, Integer isDeliver, Integer tabPage, String userId) {
        int total = 0;

        String sql = "select ss.id,ss.server_id as serverId,ss.title,ss.other_describe as otherDescribe,ss.emp_name as empName,ss.emp_id as empId,ss.emp_dept_id as empDeptId,ss.emp_org_id as empOrgId,ss.emp_dept_name as empDeptName,DATE_FORMAT(ss.createtime,'%Y-%m-%d %H:%i:%S') createtime,ss.status,ss.reply_id as replyId,ss.reply_name as replyName,DATE_FORMAT(ss.reply_time,'%Y-%m-%d %H:%i:%S') replyTime,ss.reply_reason as replyReason,ss.type,ss.is_deliver as isDeliver,ss.is_anonymous as isAnonymous,ss.complaint_dept_id as complaintDeptId,ss.complaint_dept_name as complaintDeptName FROM server_suggest ss WHERE 1 =1 ";
        if (tabPage == 1) {
            sql += " and ss.emp_id = :userId ";
        }
        if (tabPage == 2) {
            sql += " and ss.reply_id = :userId ";
        }
        if (tabPage == 3) {
            sql += " and ss.status = '2' ";
        }
        if (ToolUtil.isNotEmpty(title)) {
            sql += " and ss.title like CONCAT('%',:title,'%') ";
        }
        if (ToolUtil.isNotEmpty(type)) {
            sql += " and ss.type = :type ";
        } else if (ToolUtil.isEmpty(type)) {
            sql += " and ss.type in ('4','5') ";
        }
        if (ToolUtil.isNotEmpty(empName)) {
            sql += " and ss.emp_name = :empName ";
        }
        if (ToolUtil.isNotEmpty(empDeptId)) {
            sql += " and (ss.emp_dept_id = '" + empDeptId + "' || ss.emp_org_id = '" + empDeptId + "') ";
        }
        if (ToolUtil.isNotEmpty(startTime)) {
            sql += " and ss.createtime >= :startTime ";
        }
        if (ToolUtil.isNotEmpty(endTime)) {
            sql += " and ss.createtime <= :endTime ";
        }
        if (ToolUtil.isNotEmpty(status)) {
            sql += " and ss.status = :status ";
        }
        if (ToolUtil.isNotEmpty(isDeliver)) {
            sql += " and ss.is_deliver = :idDeliver ";
        }
        sql += " order by ss.createtime desc ";
        Query query = em.createNativeQuery(sql);
        if (ToolUtil.isNotEmpty(title)) {
            query.setParameter("title", title);
        }
        if (ToolUtil.isNotEmpty(type)) {
            query.setParameter("type", type);
        }
        if (ToolUtil.isNotEmpty(empName)) {
            query.setParameter("empName", empName);
        }
//        if (ToolUtil.isNotEmpty(empDeptId)) {
//            query.setParameter("empDeptId", empDeptId);
//        }
        if (ToolUtil.isNotEmpty(startTime)) {
            query.setParameter("startTime", startTime);
        }
        if (ToolUtil.isNotEmpty(endTime)) {
            query.setParameter("endTime", endTime);
        }
        if (ToolUtil.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ToolUtil.isNotEmpty(isDeliver)) {
            query.setParameter("isDeliver", isDeliver);
        }
        if (ToolUtil.isNotEmpty(userId)) {
            query.setParameter("userId", userId);
        }

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerSuggestModel.class));
        List<ServerSuggestModel> serverSuggestList = query.getResultList();
        if (ToolUtil.isNotEmpty(serverSuggestList)) {
            total = serverSuggestList.size();
        }
        return total;
    }


    /**
     * 查询投诉总数
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public int getComplaintTotal(String beginTime, String endTime) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM server_suggest WHERE type= '4' OR type= '5' and createtime >=:beginTime and createtime <=:endTime";
        Query query = em.createNativeQuery(sql);
        query.setParameter("beginTime", beginTime);
        query.setParameter("endTime", endTime);
        List<BigInteger> counts = query.getResultList();
        if (counts != null && counts.size() > 0) {
            total = counts.get(0).intValue();
        }
        return total;
    }

    /**
     * 新增咨询投诉信息
     *
     * @param addSuggestDto
     * @param userid
     */
    public void addSuggestAndComplain(AddSuggestDto addSuggestDto, String userid) {
        ServerSuggest serverSuggest = new ServerSuggest();

        // 服务经理账号获取
        List<Integer> managerList = getSerManager(addSuggestDto.getId());

        if (addSuggestDto.getType() == 1) {//服务需求类型咨询
            //服务标题
            serverSuggest.setTitle(addSuggestDto.getTitle());
        } else if (addSuggestDto.getType() == 2) {//服务反馈类型咨询
            //服务标题
            serverSuggest.setTitle(addSuggestDto.getSerName());
            //关联服务id
            serverSuggest.setServerId(addSuggestDto.getId());

            if (ToolUtil.isNotEmpty(managerList)) {
                //需求更改  默认转给服务经理但还是要存未转递   --jinzhao  2019-12-18
//                serverSuggest.setIsDeliver(1);
                serverSuggest.setReplyId(managerList.get(0));
                //--2019-11-26  保存名字
                OmUser omUser = omUserRepository.findByEmpid(serverSuggest.getReplyId());
                serverSuggest.setReplyName(omUser.getEmpname());
            }

        } else if (addSuggestDto.getType() == 3) {//服务意见类型咨询
            //服务标题
            serverSuggest.setTitle(addSuggestDto.getTitle());
        } else if (addSuggestDto.getType() == 4) {//部门投诉类型
            //投诉部门id
            serverSuggest.setEmpDeptId(addSuggestDto.getComplaintDeptId());
            //投诉部门
            serverSuggest.setEmpDeptName(addSuggestDto.getComplaintDeptName());
        } else if (addSuggestDto.getType() == 5) {//服务投诉类型
            serverSuggest.setTitle(addSuggestDto.getSerName());
            //关联服务id
            serverSuggest.setServerId(addSuggestDto.getId());
        }
        //意见
        serverSuggest.setOtherDescribe(addSuggestDto.getOtherDescribe());

        OmUser user = omUserRepository.findByUserid(userid);
        serverSuggest.setEmpId(user.getEmpid());
        //用户姓名   根据用户id查询  并添加到咨询表中
        serverSuggest.setEmpName(user.getEmpname());
        //提交人部门id
        serverSuggest.setEmpDeptId(user.getDeptorgid());
        //提交人部门名称
        OmOrganization omOrganization = omOrganizationRepository.findByOrgid(user.getDeptorgid());
        serverSuggest.setEmpDeptName(omOrganization.getOrgname());
        //提交人科室id
        serverSuggest.setEmpOrgId(user.getOrgid());
        //提交时间
        serverSuggest.setCreatetime(new Date());
        //处理状态
        serverSuggest.setStatus(0);
        serverSuggest.setType(addSuggestDto.getType());
        serverSuggest.setIsDeliver(0);
        serverSuggest.setIsAnonymous(addSuggestDto.getIsAnonymous());
        serverSuggest.setComplaintDeptId(addSuggestDto.getComplaintDeptId());
        serverSuggest.setComplaintDeptName(addSuggestDto.getComplaintDeptName());
        serverSuggestRepository.save(serverSuggest);
        // 发送通知操作
        SendNoticeModelDto modelDto = new SendNoticeModelDto();
        Integer id = serverSuggest.getId();
        // 系统管理员账号获取
        List<Integer> receiverList = get();
        if (addSuggestDto.getType() == 1) {//服务反馈类型咨询
            modelDto.setTitle("您收到了1条来自" + user.getEmpname() + "的咨询，请及时处理哦");
            modelDto.setTypeId(3);
            modelDto.setReceivers(receiverList);
            modelDto.setUrl(suggestDoComplete + id);
        } else if (addSuggestDto.getType() == 2) {//服务咨询类型咨询
            modelDto.setTitle("您收到了1条来自" + user.getEmpname() + "的咨询，请及时处理哦");
            modelDto.setTypeId(3);
            modelDto.setUrl(suggestDoComplete + id);
            if (ToolUtil.isNotEmpty(managerList)) {
                List list = new ArrayList();
                list.add(managerList.get(0));
                modelDto.setReceivers(list);
            } else {
                modelDto.setReceivers(receiverList);
            }
        } else if (addSuggestDto.getType() == 3) {//意见反馈类型咨询
            modelDto.setTitle("您收到了1条来自" + user.getEmpname() + "的咨询，请及时处理哦");
            modelDto.setTypeId(3);
            modelDto.setUrl(suggestDoComplete + id);
            modelDto.setReceivers(receiverList);
        } else if (addSuggestDto.getType() == 4) {//部门投诉类型
            modelDto.setTitle("您收到了1条投诉，请及时处理哦");
            modelDto.setTypeId(4);
            modelDto.setReceivers(receiverList);
            modelDto.setUrl(complaintUrl + id);
        } else if (addSuggestDto.getType() == 5) {//服务投诉类型
            modelDto.setTitle("您收到了1条投诉，请及时处理哦");
            modelDto.setTypeId(4);
            modelDto.setReceivers(managerList);
            modelDto.setUrl(complaintUrl + id);
        }
        modelDto.setAccount(account);
        modelDto.setPassword(password);
        sendNotice.sendNotice(modelDto);
    }

    /**
     * 获取服务中心系统管理员empid
     *
     * @return
     */
    private List<Integer> get() {
        String sql = "SELECT p.FK_USER_ID FROM portal_mgmt_db.seed_join_user_role p LEFT JOIN om_user o ON o.EMPID = p.FK_USER_ID "
                + "WHERE FK_ROLE_ID = '735124' AND o.EMPSTATUS <> '00'";
        Query query = em.createNativeQuery(sql);
        List<Integer> list = query.getResultList();
        return list;
    }

    /**
     * 获取服务经理empid
     *
     * @return
     */
    private List<Integer> getSerManager(Integer serverId) {
        String sql = "SELECT user_id FROM server_acceptance_user WHERE server_id =:serverId";
        Query query = em.createNativeQuery(sql);
        query.setParameter("serverId", serverId);
        List<Integer> list = query.getResultList();
        return list;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理投诉咨询信息
     *
     * @return
     */
    public ServerSuggest updateSuggestInfo(ServerSuggestDeliver serverSuggestModel, String userid) {

        Integer id = serverSuggestModel.getId();
        ServerSuggest serverSuggest = findAllById(id);
        OmUser user = omUserRepository.findByUserid(userid);
        SendNoticeModelDto modelDto = new SendNoticeModelDto();
        if (serverSuggestModel.getIsDeliver() != 0) {
            if (serverSuggestModel.getReplyId() != null && !"".equals(serverSuggestModel.getReplyId())) {

                //保存转递信息  --jinzhao  2019-12-18 修改需求
                ServerDeliver serverDeliver = new ServerDeliver();
                serverDeliver.setSuggestId(serverSuggestModel.getId());
                serverDeliver.setDeliverUserid(userid);
                serverDeliver.setDeliverUsername(user.getEmpname());
                serverDeliver.setDeliverTime(DateUtil.getTime());
                serverDeliver.setReplyUserid(serverSuggestModel.getReplyUserid());
                serverDeliver.setReplyUsername(serverSuggestModel.getReplyName());
                serverDeliver.setDeliverContent(serverSuggestModel.getReplyReason());
                serverDeliverRepository.save(serverDeliver);


//                logger.info("======================"+user.getEmpid());
                if (user.getEmpid() == serverSuggestModel.getReplyId()) {
                    if (serverSuggestModel.getReplyReason() != null && !"".equals(serverSuggestModel.getReplyReason())) {
                        serverSuggest.setReplyId(serverSuggestModel.getReplyId());
                        serverSuggest.setReplyName(serverSuggestModel.getReplyName());
                        serverSuggest.setReplyReason(serverSuggestModel.getReplyReason());
                        serverSuggest.setStatus(2);
                        serverSuggest.setReplyTime(new Date());
                        serverSuggest.setIsDeliver(serverSuggestModel.getIsDeliver());
                    } else {
                        throw new BussinessException(500, "请输入处理说明");
                    }

                } else {
                    OmUser omUser = omUserRepository.findByEmpid(serverSuggestModel.getReplyId());
                    serverSuggest.setReplyId(serverSuggestModel.getReplyId());
                    serverSuggest.setReplyName(serverSuggestModel.getReplyName());
                    serverSuggest.setReplyReason(serverSuggestModel.getReplyReason());
                    serverSuggest.setStatus(0);
                    serverSuggest.setReplyTime(new Date());
                    serverSuggest.setIsDeliver(serverSuggestModel.getIsDeliver());
                    // 发送通知给转递人
                    List<Integer> receivers = new ArrayList<>();
                    receivers.add(omUser.getEmpid());
                    modelDto.setReceivers(receivers);
                }
            } else {
                throw new BussinessException(500, "请选择转递人");
            }
        } else {
            if (serverSuggestModel.getReplyReason() != null && !"".equals(serverSuggestModel.getReplyReason())) {
                serverSuggest.setReplyId(user.getEmpid());
                serverSuggest.setReplyName(user.getEmpname());
                serverSuggest.setReplyReason(serverSuggestModel.getReplyReason());
                serverSuggest.setStatus(2);
                serverSuggest.setReplyTime(new Date());
                serverSuggest.setIsDeliver(serverSuggestModel.getIsDeliver());
            } else {
                throw new BussinessException(500, "请输入处理说明");
            }
        }
        serverSuggestRepository.save(serverSuggest);
        if (serverSuggestModel.getIsDeliver() != 0) {
            // 发送通知给转递人
            if (serverSuggest.getType() == 1 || serverSuggest.getType() == 2 || serverSuggest.getType() == 3) {
                modelDto.setTitle("您收到了1条由" + user.getEmpname() + "转递的咨询，请及时处理哦");
                modelDto.setTypeId(3);
                modelDto.setUrl(suggestDoComplete + serverSuggest.getId());
            } else {
                modelDto.setTitle("您收到了1条由" + user.getEmpname() + "转递的投诉，请及时处理哦");
                modelDto.setTypeId(4);
                modelDto.setUrl(complaintUrl + serverSuggest.getId());
            }
        } else {
            // 发送通知给咨询人或投诉人
            List<Integer> receivers = new ArrayList<>();
            receivers.add(serverSuggest.getEmpId());
            modelDto.setReceivers(receivers);
            if (serverSuggest.getType() == 1) {
                modelDto.setTitle("您提交的【" + serverSuggest.getTitle() + "】已处理完成");
                modelDto.setTypeId(3);
                modelDto.setUrl(completeSuggestUrl + serverSuggest.getId());
            } else if (serverSuggest.getType() == 2) {
                modelDto.setTitle("您提交的【" + serverSuggest.getTitle() + "】已处理完成");
                modelDto.setTypeId(3);
                modelDto.setUrl(completeSuggestUrl + serverSuggest.getId());
            } else if (serverSuggest.getType() == 3) {
                modelDto.setTitle("您提交的【" + serverSuggest.getTitle() + "】已处理完成");
                modelDto.setTypeId(3);
                modelDto.setUrl(completeSuggestUrl + serverSuggest.getId());
            } else {
                modelDto.setTitle("您提交的【" + serverSuggest.getTitle() + "】已处理完成");
                modelDto.setTypeId(4);
                modelDto.setUrl(completeComplainUrl + serverSuggest.getId());
            }
        }
        modelDto.setAccount(account);
        modelDto.setPassword(password);
        sendNotice.sendNotice(modelDto);
        return serverSuggest;
    }

    private ServerSuggest findAllById(Integer id) {
        ServerSuggest serverSuggest = serverSuggestRepository.findServerSuggestById(id);
        return serverSuggest;
    }

    @Override
    public List<SuggestModel> find(Integer empDeptId, String empDeptName, Integer status, Integer type, Integer isDeliver, String ids) {
        String sql = "select ss.title,ss.other_describe as otherDescribe,(case ss.type when 1 then '服务反馈' when 2 then '服务咨询' when 3 then '意见反馈' end ) as type,ss.emp_name as empName,ss.emp_dept_name as empDeptName,DATE_FORMAT(ss.createtime,'%Y-%m-%d') as createtime,ss.reply_name as replyName,(case ss.status when 0 then '未处理' when 1 then '已处理' end ) as status FROM server_suggest ss WHERE 1=1 ";

        if (ToolUtil.isNotEmpty(ids)) {
            String[] unid = ids.split(",");
            sql += " and ss.id in (";
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

        if (ToolUtil.isNotEmpty(type)) {
            sql += " and ss.type = :type ";
        } else if (ToolUtil.isEmpty(type)) {
            sql += " and ss.type in ('1','2','3') ";
        }
        if (ToolUtil.isNotEmpty(empDeptId)) {
            sql += " and ss.emp_dept_id = :empDeptId ";
        }
        if (ToolUtil.isNotEmpty(status)) {
            sql += " and ss.status = :status ";
        }
        if (ToolUtil.isNotEmpty(isDeliver)) {
            sql += " and ss.is_deliver = :isDeliver ";
        }
        sql += " order by ss.createtime desc ";
        Query query = em.createNativeQuery(sql);
        if (ToolUtil.isNotEmpty(type)) {
            query.setParameter("type", type);
        }
        if (ToolUtil.isNotEmpty(empDeptId)) {
            query.setParameter("empDeptId", empDeptId);
        }
        if (ToolUtil.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ToolUtil.isNotEmpty(isDeliver)) {
            query.setParameter("isDeliver", isDeliver);
        }

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SuggestModel.class));

        return query.getResultList();
    }

    @Override
    public List<ComplaintModel> getComplaint(Integer empDeptId, String empDeptName, Integer status, Integer type, Integer isDeliver, String ids) {
        String sql = "select ss.title,ss.other_describe as otherDescribe,(case ss.type when 4 then '部门投诉' when 5 then '服务投诉' end ) as type,ss.emp_name as empName,ss.emp_dept_name as empDeptName,DATE_FORMAT(ss.createtime,'%Y-%m-%d') as createtime,ss.reply_name as replyName,(case ss.status when 0 then '未处理' when 1 then '已处理' end ) as status,ss.is_anonymous as isAnonymous FROM server_suggest ss WHERE 1=1 ";

        if (ToolUtil.isNotEmpty(ids)) {
            String[] unid = ids.split(",");
            sql += " and ss.id in (";
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

        if (ToolUtil.isNotEmpty(type)) {
            sql += " and ss.type = :type ";
        } else if (ToolUtil.isEmpty(type)) {
            sql += " and ss.type in ('4','5') ";
        }
        if (ToolUtil.isNotEmpty(empDeptId)) {
            sql += " and ss.emp_dept_id = :empDeptId ";
        }
        if (ToolUtil.isNotEmpty(status)) {
            sql += " and ss.status = :status ";
        }
        if (ToolUtil.isNotEmpty(isDeliver)) {
            sql += " and ss.is_deliver = :isDeliver ";
        }
        sql += " order by ss.createtime desc ";
        Query query = em.createNativeQuery(sql);
        if (ToolUtil.isNotEmpty(type)) {
            query.setParameter("type", type);
        }
        if (ToolUtil.isNotEmpty(empDeptId)) {
            query.setParameter("empDeptId", empDeptId);
        }
        if (ToolUtil.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ToolUtil.isNotEmpty(isDeliver)) {
            query.setParameter("isDeliver", isDeliver);
        }

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ComplaintModel.class));
        List<ComplaintModel> list = query.getResultList();
        for (ComplaintModel complaintModel : list) {
            if (complaintModel.getIsAnonymous() == 1) {
                complaintModel.setEmpName("匿名");
            }
        }
        return list;
    }
}
