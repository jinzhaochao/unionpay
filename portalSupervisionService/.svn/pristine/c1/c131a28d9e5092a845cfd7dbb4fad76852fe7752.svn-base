package com.unionpay.pager.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.dao.PagerApplicationRepository;
import com.unionpay.pager.domain.JDBCUtil;
import com.unionpay.pager.domain.PagerApplication;
import com.unionpay.pager.domain.PagerTag;
import com.unionpay.pager.dto.*;
import com.unionpay.pager.service.PagerApplicationService;
import com.unionpay.pager.service.PagerTagService;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-18 13:46
 **/
@Service
public class PagerTagServiceImpl implements PagerTagService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private com.unionpay.pager.dao.PagerTagRepository PagerTagRepository;
    @Autowired
    private PagerTagService pagerTagService;


    @Override
    public List<DropdownPagerTag> dropdownBoxPagerTag(Integer orgId) {
        String jpql = "SELECT pt.tag_id as tagId, pt.tag_name as tagName, pt.tag_title as tagTitle " +
                "    FROM pager_tag pt WHERE pt.`status` = 1   ";
        if (ToolUtil.isNotEmpty(orgId)) {
            jpql += "  and pt.org_id = :orgId ";
        }
        jpql += " Order By pt.create_time  Desc";
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(orgId)) {
            query.setParameter("orgId", orgId);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(DropdownPagerTag.class));
        List<DropdownPagerTag> dropdownPagerTags = query.getResultList();
        return dropdownPagerTags;
    }

    @Override
    public PagerTag getPagerTag(Integer tagId) {
        String jpql = "SELECT pt.tag_id as tagId, pt.tag_name as tagName,pt.tag_title as tagTitle,pt.org_name as orgName," +
                "   pt.formwork as formwork,pt.column_id as columnId,pt.column_name as columnName,pt.hrefurl as hrefurl,pt.is_together as isTogether" +
                "    FROM pager_tag pt WHERE  pt.`status` = 1 ";
        if (ToolUtil.isNotEmpty(tagId)) {
            jpql += "  and pt.tag_id = :tagId ";
        }
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(tagId)) {
            query.setParameter("tagId", tagId);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PagerTag.class));
        PagerTag pagerTag = (PagerTag) query.getSingleResult();
        return pagerTag;
    }

    @Override
    public PagerTag getPagerTagByIdAndFormwork(Integer tagId, Integer formwork) {
        String jpql = "SELECT pt.tag_id as tagId, pt.tag_name as tagName,pt.tag_title as tagTitle,pt.org_name as orgName," +
                "   pt.formwork as formwork,pt.column_id as columnId,pt.column_name as columnName,pt.hrefurl as hrefurl,pt.is_together as isTogether" +
                "    FROM pager_tag pt WHERE  pt.`status` = 1 ";
        if (ToolUtil.isNotEmpty(tagId)) {
            jpql += "  and pt.tag_id = :tagId ";
        }
        if (ToolUtil.isNotEmpty(formwork)) {
            jpql += "  and pt.formwork = :formwork ";
        }
        Query query = em.createNativeQuery(jpql);
        if (ToolUtil.isNotEmpty(tagId)) {
            query.setParameter("tagId", tagId);
        }
        if (ToolUtil.isNotEmpty(formwork)) {
            query.setParameter("formwork", formwork);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PagerTag.class));
        PagerTag pagerTag = (PagerTag) query.getSingleResult();
        return pagerTag;
    }


    //查询页签列表
    @Override
    public List<PagerTag> selectPagerTags(String tagName, Integer currentPage, Integer size) {
        String jpqlCondition = "SELECT tag.tag_title as tagTitle,tag.tag_id as tagId,tag.tag_name as tagName," +
                " tag.org_name as orgName,tag.status as status ,tag.column_id as columnId," +
                " tag.column_name as columnName, tag.create_time as createTime, tag.formwork as formwork," +
                " tag.is_together as isTogether, tag.org_id as orgId,tag.hrefurl as hrefurl, tag.chnl_Id as chnlId" +
                " FROM pager_tag tag WHERE 1=1 AND status !=2";
        //判断标题是否为空
        if (ToolUtil.isNotEmpty(tagName)) {
            jpqlCondition += " AND tag.tag_name like CONCAT('%',:tagName,'%') ";
        }
        jpqlCondition += " ORDER BY tag.create_time desc ";
        Query query = em.createNativeQuery(jpqlCondition);
        if (ToolUtil.isNotEmpty(tagName)) {
            query.setParameter("tagName", tagName);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PagerTag.class));
        query.setFirstResult((currentPage - 1) * size);
        query.setMaxResults(size);
        List<PagerTag> list = query.getResultList();
        return list;
    }

    //查询页签总数
    @Override
    public Integer findPagerTagsTotle(String tagName) {
            int count = 0;
        String jpqlCondition = "SELECT count(tag.tag_id) FROM pager_tag tag WHERE 1=1 AND status !=2";
        //判断标题是否为空
        if (ToolUtil.isNotEmpty(tagName)) {
            jpqlCondition += " AND tag.tag_name like CONCAT('%',:tagName,'%') ";
        }
        Query query = em.createNativeQuery(jpqlCondition);
        if (ToolUtil.isNotEmpty(tagName)) {
            query.setParameter("tagName", tagName);
        }
        List<BigInteger> counts = query.getResultList();
        if (ToolUtil.isNotEmpty(counts)) {
            count = counts.get(0).intValue();
        }
        return count;
    }


    @Autowired
    private PagerApplicationService pagerApplicationService;
    @Autowired
    private com.unionpay.pager.dao.PagerApplicationRepository pagerApplicationRepository;

    @Override
    public RESTResultBean checkRelation(Integer tagId) {
        String jpqlCondition = "SELECT tag_id FROM pager_manager_tag WHERE tag_id =:tagId  and status != 0";
        Query query = em.createNativeQuery(jpqlCondition);
        query.setParameter("tagId", tagId);
        List count = query.getResultList();
        if (count.size() > 0) {
            String message = "本条页签已被页面应用，无法删除";
            RESTResultBean result = new RESTResultBean(400, message);
            return result;
        }
        String jpql = "SELECT application_id FROM pager_application WHERE tag_id =:tagId and status != 2";
        Query query1 = em.createNativeQuery(jpql);
        query1.setParameter("tagId", tagId);
        List<PagerApplication> pagerApplicationList = pagerApplicationRepository.findAllByTagId(tagId);
        pagerApplicationService.deleteApplication(pagerApplicationList);
//        List count1 = query1.getResultList();
//        if (count1.size() > 0) {
//            String message = "本条页签下有应用，无法删除";
//            RESTResultBean result = new RESTResultBean(400, message);
//            return result;
//        }
        List<PagerTag> pagerTag = pagerTagService.seleTagById(tagId);
        if (ToolUtil.isNotEmpty(pagerTag)) {
//            PagerTag pagerTag1 = new PagerTag();
//            pagerTag1.setTagId(tagid);
            PagerTag pagerTag1 = pagerTag.get(0);
            pagerTag1.setStatus(2);
            PagerTagRepository.saveAndFlush(pagerTag1);
            List<PagerTag> pagerTags = pagerTagService.seleTagById(tagId);
            if (ToolUtil.isNotEmpty(pagerTags)) {
                String message = "删除失败";
                RESTResultBean result = new RESTResultBean(400, message);
                return result;
            } else {
                String message = "删除成功";
                RESTResultBean result = new RESTResultBean(200, message);
                return result;
            }
        } else {
            String message = "本条页签信息不存在";
            RESTResultBean result = new RESTResultBean(400, message);
            return result;
        }
    }

    @Override
    public List<PagerTag> seleTagById(Integer tagId) {
        String jpqlCondition = "SELECT tag_id as tagId,tag_name as tagName,tag_title as tagTitle,org_id as orgId,org_name as orgName,status as status," +
                "formwork as formwork,column_id as columnId,column_name as columnName,is_together as isTogether," +
                "create_time as createTime FROM pager_tag WHERE tag_id = :tagId  AND status !=2";
        Query query = em.createNativeQuery(jpqlCondition);
        query.setParameter("tagId", tagId);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PagerTag.class));
        List<PagerTag> pagerTags = query.getResultList();
        return pagerTags;
    }

    @Override
    public PagerTag addPagerTagArticle(addTagRequestDto addTagRequestDto) {
        PagerTag pagerTag = new PagerTag();
        pagerTag.setTagName(addTagRequestDto.getTagName());
        pagerTag.setTagTitle(addTagRequestDto.getTagTitle());
        pagerTag.setOrgId(addTagRequestDto.getOrgId());
        pagerTag.setOrgName(addTagRequestDto.getOrgName());
        pagerTag.setFormwork(addTagRequestDto.getFormwork());
        pagerTag.setStatus(addTagRequestDto.getStatus());
        pagerTag.setCreateTime(new Date());
        if (addTagRequestDto.getFormwork() == 3) {
            pagerTag.setColumnName(addTagRequestDto.getColumnName());
            pagerTag.setIsTogether(addTagRequestDto.getIsTogether());
            pagerTag.setHrefurl(addTagRequestDto.getHrefurl());
            pagerTag.setColumnId(addTagRequestDto.getColumnId());
            pagerTag.setChnlId(addTagRequestDto.getChnlId());
        }
        if (addTagRequestDto.getFormwork() == 4){
            pagerTag.setHrefurl(addTagRequestDto.getHrefurl());
        }
        PagerTagRepository.saveAndFlush(pagerTag);
        return pagerTag;
    }



}
