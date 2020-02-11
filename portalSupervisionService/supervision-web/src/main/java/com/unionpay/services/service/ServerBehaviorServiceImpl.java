package com.unionpay.services.service;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.entity.ServerBehavior;
import com.unionpay.services.model.BehaviorModel;
import com.unionpay.services.model.ServerDict;
import com.unionpay.services.repository.ServerBehaviorRepository;
import com.unionpay.services.repository.ServerdictRepository;
import com.unionpay.sms.service.OmOrganizationService;
import com.unionpay.supervision.domain.OmOrganization;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

@Service
public class ServerBehaviorServiceImpl implements ServerBehaviorService{
    @Autowired
    private ServerBehaviorRepository behaviorRepository;
    @Autowired
    private OmUserService omUserService;
    @Autowired
    private OmOrganizationService omOrganizationService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ServerdictRepository serverdictRepository;

    /**
     * 行为记录下拉框数据
     * @return
     */
    public List listSelect(){
        List list = new ArrayList<>();
        String sql = "SELECT `name`,`value` FROM `server_dict` WHERE type = 'behavior'";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerDict.class));
        List<ServerDict> dictList = query.getResultList();
        if (ToolUtil.isNotEmpty(dictList)){
            for (ServerDict dict:dictList){
                JSONObject map = new JSONObject();
                map.put("value",dict.getValue());
                map.put("name",dict.getName());
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 新增行为记录
     * @param id
     * @param userId
     * @return
     */
    @Override
    public ServerBehavior add(Integer id,String userId) {
        ServerBehavior behavior = new ServerBehavior();
        OmUser omUser = omUserService.findByUserid(userId);
        if (ToolUtil.isNotEmpty(omUser)){
            OmOrganization omOrganization = omOrganizationService.findByOrgid(omUser.getOrgid());
            if (ToolUtil.isNotEmpty(omOrganization)){
                behavior.setDictId(id);
                behavior.setUserId(userId);
                behavior.setEmpName(omUser.getEmpname());
                behavior.setOrgId(omOrganization.getOrgid());
                behavior.setOrgName(omOrganization.getOrgname());
                behavior.setStatus(1);
                behavior.setCreateTime(DateUtil.getStrTime(new Date()));
                behaviorRepository.save(behavior);
            }
        }
        return behavior;
    }

    /**
     * 根据id查找行为记录
     * @param id
     * @return
     */
    public BehaviorModel getOne(Integer id){
        String sql = "SELECT s.behavior_id behaviorId,sd.`name` behaviorType,s.emp_name empName,s.org_name orgName,"
                + "DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') createTime FROM server_behavior s "
                + "LEFT JOIN server_dict sd ON (sd.type = 'behavior' AND sd.`value` = s.dict_id) WHERE s.behavior_id =:id";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(BehaviorModel.class));
        if (ToolUtil.isNotEmpty(id)){
            query.setParameter("id",id);
        }
        BehaviorModel model = (BehaviorModel)query.getSingleResult();
        return model;
    }


    /**
     * 查询所有行为记录
     *
     * @param id
     * @return
     */
    @Override
    public List<BehaviorModel> getAll(Integer id,Integer page,Integer size) {
        String sql = "SELECT s.behavior_id behaviorId,sd.`name` behaviorType,s.emp_name empName,s.org_name orgName,"
                + "DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') createTime FROM server_behavior s "
                + "LEFT JOIN server_dict sd ON (sd.type = 'behavior' AND sd.`value` = s.dict_id) WHERE 1=1";
        if (ToolUtil.isNotEmpty(id)){
            sql += " AND s.dict_id =:id";
        }
        sql += " ORDER BY s.create_time DESC";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(BehaviorModel.class));
        if (ToolUtil.isNotEmpty(id)){
            query.setParameter("id",id);
        }
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<BehaviorModel> modelList = query.getResultList();
        return modelList;
    }

    /**
     * 查询所有行为记录总数
     *
     * @param behavior
     * @return
     */
    @Override
    public Integer count(Integer behavior,Integer page,Integer size) {
        int count = 0;
        String sql = "SELECT count(s.behavior_id) FROM server_behavior s "
                + "LEFT JOIN server_dict sd ON (sd.type = 'behavior' AND sd.`value` = s.dict_id) WHERE 1=1";
        if (ToolUtil.isNotEmpty(behavior)){
            sql += " AND s.dict_id =:id";
        }
        Query query = entityManager.createNativeQuery(sql);
        if (ToolUtil.isNotEmpty(behavior)){
            query.setParameter("id",behavior);
        }
        List<BigInteger> counts = query.getResultList();
        if (ToolUtil.isNotEmpty(counts)){
            count = counts.get(0).intValue();
        }
        return count;
    }

    @Override
    public List<BehaviorModel> excel(ServerDict serverDict, String ids) {
        String sql = "SELECT s.behavior_id behaviorId,sd.`name` behaviorType,s.emp_name empName,s.org_name orgName,"
                + "DATE_FORMAT(s.create_time,'%Y-%m-%d %H:%i:%S') createTime FROM server_behavior s "
                + "LEFT JOIN server_dict sd ON (sd.type = 'behavior' AND sd.`value` = s.dict_id) WHERE 1=1";
        if (ToolUtil.isNotEmpty(serverDict.getValue())){
            sql += " AND s.dict_id =:id";
        }
        if (ToolUtil.isNotEmpty(ids)) {
            String[] unid = ids.split(",");
            sql += " and s.behavior_id in (";
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
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(BehaviorModel.class));
        if (ToolUtil.isNotEmpty(serverDict.getValue())){
            query.setParameter("id",serverDict.getValue());
        }
        List<BehaviorModel> modelList = query.getResultList();
        return modelList;
    }
}
