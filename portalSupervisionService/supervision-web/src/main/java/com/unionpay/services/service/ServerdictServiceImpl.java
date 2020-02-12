package com.unionpay.services.service;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.ServerCommonProblem;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unionpay.services.model.ServerDict;
import com.unionpay.services.repository.ServerdictRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class ServerdictServiceImpl implements ServerdictService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ServerdictRepository serverdict_modelRepository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public ServerDict add(ServerDict serverdict_model) {
        return serverdict_modelRepository.save(serverdict_model);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void delete(Integer id) {
        serverdict_modelRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public ServerDict update(ServerDict serverdict_model) {
        return serverdict_modelRepository.saveAndFlush(serverdict_model);
    }

    public ServerDict get(Integer id) {
        return serverdict_modelRepository.getOne(id);
    }

    public List<ServerDict> getAll() {
        return serverdict_modelRepository.findAll();
    }

    public Page<ServerDict> getPage(Integer page, Integer rows) {
        Pageable pageable = new PageRequest(page - 1, rows);
        return serverdict_modelRepository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<ServerDict> selectAll() {
        List<ServerDict> serverDicts = new ArrayList<>();
        //展示下拉框启用与禁用
        String sql = "SELECT sd.`name`,sd.sort,sd.type FROM server_dict sd WHERE sd.type =  \"status\" ";
        String sql1 = "SELECT sd.`name`,sd.sort,sd.type FROM server_dict sd WHERE sd.type =  \"material\" ";
        String sql2 = "SELECT sd.`name`,sd.sort,sd.type FROM server_dict sd WHERE sd.type =  \"necessity\" ";
        Query query = em.createNativeQuery(sql);
        Query query1 = em.createNativeQuery(sql1);
        Query query2 = em.createNativeQuery(sql2);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerDict.class));
        query1.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerDict.class));
        query2.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerDict.class));
        List<ServerDict> liststatus = query.getResultList();
        List<ServerDict> listmaterial = query1.getResultList();
        List<ServerDict> listnecessity = query2.getResultList();
        if (ToolUtil.isNotEmpty(liststatus)) {//状态  0：禁用  1：启用
            serverDicts.addAll(liststatus);
        }
        if (ToolUtil.isNotEmpty(listmaterial)) {//办理材料类型
            serverDicts.addAll(listmaterial);
        }
        if (ToolUtil.isNotEmpty(listnecessity)) {//是否必要
            serverDicts.addAll(listnecessity);
        }
        return serverDicts;
    }

    /**
     * 服务分类、状态下拉列表
     * @return
     * @author lishuang
     * @date 2019-03-19
     */
    public JSONObject selectType(){
        JSONObject jsonObject = new JSONObject();
        //分类
        String sql = "SELECT sd.id,sd.name,sd.sort from server_dict sd WHERE sd.type = 'type' ORDER BY sort";
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerDict.class));
        List<ServerDict> listType = query.getResultList();
        jsonObject.put("type",listType);
        //状态
        String sql2 = "SELECT sd.id,sd.name,sd.sort from server_dict sd WHERE sd.type = 'status'";
        Query query2 = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ServerDict.class));
        List<ServerDict> listStatus = query.getResultList();
        jsonObject.put("status",listStatus);
        return jsonObject;
    }
}