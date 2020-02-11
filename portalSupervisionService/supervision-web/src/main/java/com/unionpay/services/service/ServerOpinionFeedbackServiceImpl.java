package com.unionpay.services.service;

import com.unionpay.common.util.DateUtil;
import com.unionpay.services.model.Server0pinionFeedback;
import com.unionpay.services.model.Server0pinionFeedbackModelDto;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import com.unionpay.services.repository.Server0pinionFeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServerOpinionFeedbackServiceImpl implements ServerOpinionFeedbackService{

    @Autowired
    private Server0pinionFeedbackRepository serverOpinionFeedbackRepository;
    @Autowired
    private EntityManager em;

    /**
     * 意见反馈新增
     * @param id
     * @param content
     * @param empid
     * @return
     *
     * @author lishuang
     * @date 2019-03-18
     */
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public Server0pinionFeedback add(Integer id,String content, Integer empid){
        Server0pinionFeedback feedback = new Server0pinionFeedback();
        feedback.setEmpId(empid);
        feedback.setServerId(id);
        feedback.setContent(content);
        feedback.setCreatetime(new Date());
        feedback.setStatus(1);
        return serverOpinionFeedbackRepository.save(feedback);
    }

    /**
     * 查询意见反馈详情
     * @param id
     * @return
     *
     * @author lishaung
     * @date 2019-03-21
     */
    public Server0pinionFeedbackModelDto getOne(Integer id){
        String sql = "select s.id id,info.ser_id serId,info.ser_name serName,s.content content,user.EMPNAME empName,"
                +"(SELECT o.ORGNAME from om_organization o LEFT JOIN om_user u ON o.ORGID = u.ORGID WHERE u.EMPID = s.emp_id) feedBackOrgName,"
                +"DATE_FORMAT(s.createtime,'%Y-%m-%d %H:%i:%S') createtime,s.`status` `status`,u.empname replyName,s.reply_reason replyReason,"
                +"(SELECT o.ORGNAME from om_organization o LEFT JOIN om_user u ON o.ORGID = u.ORGID WHERE u.EMPID = s.reply_id) replyOrgName,"
                +"DATE_FORMAT(s.reply_time,'%Y-%m-%d %H:%i:%S') replyTime FROM server_opinion_feedback s LEFT JOIN om_user u ON s.reply_id = u.EMPID "
                +"LEFT JOIN om_user user ON s.emp_id = user.EMPID LEFT JOIN server_info info ON info.id = s.server_id where s.id =:id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id",id);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(Server0pinionFeedbackModelDto.class));
        Server0pinionFeedbackModelDto server0pinionFeedbackModelDto = (Server0pinionFeedbackModelDto)query.getSingleResult();
        return server0pinionFeedbackModelDto;
    }

    /**
     * 分页查询意见反馈列表
     * @param page
     * @param size
     * @param orgid
     * @param status
     * @return
     *
     * @author lishuang
     * @date 2019-03-20
     */
    public List<Server0pinionFeedbackModelDto> getAll(Integer page,Integer size,Integer orgid,Integer status){
        String sql = "SELECT s.id id,info.ser_id serId,info.ser_name serName,s.content content,user.EMPNAME empName,(SELECT GROUP_CONCAT(oo.ORGNAME) "
                + "FROM om_organization oo LEFT JOIN server_acceptance_dept sad ON oo.orgid = sad.org_id WHERE sad.server_id = info.id GROUP BY "
                + "sad.server_id) orgName,(SELECT o.ORGNAME from om_organization o LEFT JOIN om_user u ON o.ORGID = u.ORGID WHERE u.EMPID = s.emp_id) "
                +"feedBackOrgName,DATE_FORMAT(s.createtime,'%Y-%m-%d %H:%i:%S') createtime,s.`status` `status`,u.empname replyName,s.reply_reason "
                +"replyReason,(SELECT o.ORGNAME from om_organization o LEFT JOIN om_user u ON o.ORGID = u.ORGID WHERE u.EMPID = s.reply_id) "
                +"replyOrgName,DATE_FORMAT(s.reply_time,'%Y-%m-%d %H:%i:%S') replyTime FROM server_opinion_feedback s LEFT JOIN om_user u "
                +"ON s.reply_id = u.EMPID LEFT JOIN om_user user ON s.emp_id = user.EMPID INNER JOIN server_info info ON info.id = s.server_id where 1=1";
       /* String s = "SELECT s.id id,info.ser_name serName,s.content content,DATE_FORMAT(s.createtime,'%Y-%m-%d %H:%i:%S') createtime,"
                +"(SELECT GROUP_CONCAT(oo.ORGNAME) from om_organization oo LEFT JOIN server_acceptance_dept sad on oo.orgid = sad.org_id "
                +"where sad.server_id = info.id GROUP BY sad.server_id) orgName,ou.EMPNAME empName,om.ORGNAME feedBackOrgName,s.`status` `status` "
                +"from server_opinion_feedback s LEFT JOIN om_user ou ON s.emp_id = ou.EMPID INNER JOIN server_info info ON info.id = "
                +"s.server_id LEFT JOIN om_organization om ON om.orgid = ou.ORGID WHERE 1=1";*/
        if (null != orgid){
            sql += " AND info.id in (SELECT server_id FROM server_acceptance_dept WHERE org_id =:orgid)";
        }
        if (null != status){
            sql += " AND s.status =:status";
        }
        sql += " order by s.createtime desc";
        Query query = em.createNativeQuery(sql);
        if (null != orgid){
            query.setParameter("orgid",orgid);
        }
        if (null != status){
            query.setParameter("status",status);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(Server0pinionFeedbackModelDto.class));
        query.setFirstResult((page-1)*size);
        query.setMaxResults(size);
        List<Server0pinionFeedbackModelDto> list = query.getResultList();
        return list;
    }

    /**
     * 查询意见反馈条数
     * @param page
     * @param size
     * @param orgid
     * @param status
     * @return
     *
     * @author lishuang
     * @date 2019-03-20
     */
    public Integer getCount(Integer page,Integer size,Integer orgid,Integer status){
        int count = 0;
        String sql = "SELECT count(s.id) "
                +"from server_opinion_feedback s LEFT JOIN om_user ou ON s.emp_id = ou.EMPID "
                +"INNER JOIN server_info info ON info.id = s.server_id LEFT JOIN om_organization om ON om.orgid = ou.ORGID WHERE 1=1";
        if (null != orgid){
            sql += " AND info.id in (SELECT server_id FROM server_acceptance_dept WHERE org_id ='"+ orgid +"')";
        }
        if (null != status){
            sql += " AND s.status =  '"+ status +"'";
        }
        Query querycount = em.createNativeQuery(sql);
        List<BigInteger> counts = querycount.getResultList();
        if (counts != null && counts.size() > 0) {
            count = counts.get(0).intValue();
        }
        return count;
    }

    /**
     * 编辑反馈详情
     * @param server0pinionFeedbackModelDto
     * @return
     *
     * @author lishuang
     * @date 2019-03-21
     */
    public Server0pinionFeedback edit(Server0pinionFeedbackModelDto server0pinionFeedbackModelDto,Integer empid){
        Server0pinionFeedback server0pinionFeedback = serverOpinionFeedbackRepository.getOne(server0pinionFeedbackModelDto.getId());
        server0pinionFeedback.setStatus(server0pinionFeedbackModelDto.getStatus());
        server0pinionFeedback.setReplyReason(server0pinionFeedbackModelDto.getReplyReason());
        server0pinionFeedback.setReplyId(empid);
        server0pinionFeedback.setReplyTime(new Date());
        server0pinionFeedback = serverOpinionFeedbackRepository.save(server0pinionFeedback);
        return server0pinionFeedback;
    }

}
