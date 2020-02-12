package com.unionpay.tree.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.sms.service.OmOrganizationService;
import com.unionpay.supervision.domain.OmOrganization;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.tree.service.TreeService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Service
public class TreeServiceImpl implements TreeService {
    @Autowired
    private OmUserService omUserService;
    @Autowired
    private OmOrganizationService omOrganizationService;
    @Autowired
    private EntityManager entityManager;

    /**
     * 根据部门编号，获取该部门下子部门信息（不包含员工信息）
     * @param id
     * @return
     */
    public JSONArray getDept(String id){
        JSONArray array = new JSONArray();
        if ("0".equals(id) || ToolUtil.isEmpty(id)){
            // 初始状态，中国银联下子部门信息（总公司，分公司，子公司）
            List<OmOrganization> omOrganizations = omOrganizationService.fingByParentOrgid(205);
            if (ToolUtil.isNotEmpty(omOrganizations)){
                for (OmOrganization omOrganization:omOrganizations){
                    Map parent = new LinkedHashMap();
                    parent.put("id",omOrganization.getOrgid());
                    parent.put("name",omOrganization.getOrgname());
                    if ("Y".equals(omOrganization.getIsleaf())){
                        parent.put("isParent",false);
                    }else if ("N".equals(omOrganization.getIsleaf())){
                        parent.put("isParent",true);
                    }
                    array.add(parent);
                }
            }
        }else {
            Integer orgid = Integer.parseInt(id);
            // 当前部门下子部门信息
            List<OmOrganization> omOrganizations = omOrganizationService.fingByParentOrgid(orgid);
            if (ToolUtil.isNotEmpty(omOrganizations)){
                for (OmOrganization omOrganization:omOrganizations){
                    Map children = new LinkedHashMap();
                    children.put("id",omOrganization.getOrgid());
                    children.put("name",omOrganization.getOrgname());
                    if ("Y".equals(omOrganization.getIsleaf())){
                        children.put("isParent",false);
                    }else if ("N".equals(omOrganization.getIsleaf())){
                        children.put("isParent",true);
                    }
                    array.add(children);
                }
            }
        }
        return array;
    }

    /**
     * 根据部门编号，获取该部门下子部门及员工信息
     * @param id
     * @return
     */
    public JSONArray getDeptAndUser(String id){
        JSONArray array = new JSONArray();
        if ("0".equals(id) || ToolUtil.isEmpty(id)){
            // 初始状态，中国银联下子部门信息（总公司，分公司，子公司）
            List<OmOrganization> omOrganizations = omOrganizationService.fingByParentOrgid(205);
            if (ToolUtil.isNotEmpty(omOrganizations)){
                for (OmOrganization omOrganization:omOrganizations){
                    Map parent = new LinkedHashMap();
                    parent.put("id",omOrganization.getOrgid());
                    parent.put("name",omOrganization.getOrgname());
                    if ("Y".equals(omOrganization.getIsleaf())){
                        parent.put("isParent",false);
                    }else if ("N".equals(omOrganization.getIsleaf())){
                        parent.put("isParent",true);
                    }
                    array.add(parent);
                }
            }
        }else {
            Integer orgid = Integer.parseInt(id);
            // 当前部门下子部门信息
            List<OmOrganization> omOrganizations = omOrganizationService.fingByParentOrgid(orgid);
            if (ToolUtil.isNotEmpty(omOrganizations)){
                for (OmOrganization omOrganization:omOrganizations){
                    Map children = new LinkedHashMap();
                    children.put("id",omOrganization.getOrgid());
                    children.put("name",omOrganization.getOrgname());
                    if ("Y".equals(omOrganization.getIsleaf())){
                        children.put("isParent",false);
                    }else if ("N".equals(omOrganization.getIsleaf())){
                        children.put("isParent",true);
                    }
                    array.add(children);
                }
            }
            // 当前部门下，员工信息
            List<OmUser> users = getUser(orgid);
            if (ToolUtil.isNotEmpty(users)){
                for (OmUser user:users){
                    Map map = new LinkedHashMap();
                    map.put("id",user.getUserid());
                    map.put("name",user.getEmpname());
                    map.put("isParent",false);
                    array.add(map);
                }
            }
        }
        return array;
    }

    /**
     * 根据部门编号，查询该部门下员工信息
     * @param orgid
     * @return
     */
    private List<OmUser> getUser(Integer orgid){
        String sql = "SELECT empid,userid,empname FROM `om_user_addressbook` "
                   + "WHERE ORGID =:orgid and EMPSTATUS <> '00' and EMPTYPE <> '00' ORDER BY SORTNO";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("orgid",orgid);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(OmUser.class));
        List<OmUser> userList = query.getResultList();
        return userList;
    }
}
