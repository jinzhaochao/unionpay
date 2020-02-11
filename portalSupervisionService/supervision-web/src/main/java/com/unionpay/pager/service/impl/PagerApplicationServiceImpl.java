package com.unionpay.pager.service.impl;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.dao.PagerApplicationRepository;
import com.unionpay.pager.domain.PagerApplication;
import com.unionpay.pager.dto.PagerAppDto;
import com.unionpay.pager.dto.TagApplicationInfoDTO;
import com.unionpay.pager.dto.children;
import com.unionpay.pager.service.PagerApplicationService;
import com.unionpay.supervision.dao.SuperFileRepository;
import com.unionpay.supervision.domain.SuperFile;
import com.unionpay.voice.dao.VoiceFileRepository;
import com.unionpay.voice.domain.VoiceFile;
import org.apache.poi.ss.formula.functions.Count;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-18 13:45
 **/
@Service
public class PagerApplicationServiceImpl implements PagerApplicationService {

    @Value("${http.pager.doenload}")
    private String url;
    @Autowired
    private PagerApplicationRepository pagerApplicationRepository;
    @Autowired
    private SuperFileRepository superFileRepository;
    @Autowired
    private VoiceFileRepository voiceFileRepository;
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<TagApplicationInfoDTO> applicationInfos(Integer tagId, Integer status) {
        List<TagApplicationInfoDTO> tagApplicationInfoDTOS = new ArrayList<>();
        //查找某个页签下的所有应用
        List<PagerApplication> pagerApplications1 = pagerApplicationRepository.findByTagIdAndStatusOrderBySort(tagId, status);
        List<PagerApplication> pagerApplications = new ArrayList<>();
        for (PagerApplication pagerApplication : pagerApplications1) {
            if (ToolUtil.isEmpty(pagerApplication.getParentId())) {
                pagerApplications.add(pagerApplication);
            }
        }
        if (ToolUtil.isNotEmpty(pagerApplications)) {
            for (PagerApplication pagerApplication : pagerApplications) {
                TagApplicationInfoDTO tagApplicationInfoDTO = new TagApplicationInfoDTO();
                //模板类型：1-应用列表(无分组)，2-应用列表(分组)，3-文章列表
                tagApplicationInfoDTO.setApplicationName(pagerApplication.getApplicationName());
                tagApplicationInfoDTO.setUrl(pagerApplication.getUrl());
                //查询图标
                if (ToolUtil.isNotEmpty(pagerApplication.getFileId())) {
                    //SuperFile superFile = superFileRepository.getOne(pagerApplication.getFileId());
                    VoiceFile voiceFile = voiceFileRepository.findByUnid(pagerApplication.getFileId());
                    if (ToolUtil.isNotEmpty(voiceFile)) {
                        tagApplicationInfoDTO.setFileId(voiceFile.getUnid());
                        tagApplicationInfoDTO.setName(voiceFile.getFileName());
                        tagApplicationInfoDTO.setFileUrl(url + voiceFile.getUnid());
                    }
                }
                //应用下所有的子应用
                if (ToolUtil.isEmpty(pagerApplication.getParentId())) {
                    List<PagerApplication> ziPagerApplications = pagerApplicationRepository.findAllByParentIdAndStatus(pagerApplication.getApplicationId(), 1);
                    List<children> childrens = new ArrayList<>();
                    if (ToolUtil.isNotEmpty(ziPagerApplications)) {
                        for (PagerApplication pagerApplication1 : ziPagerApplications) {
                            children children = new children();
                            children.setApplicationName(pagerApplication1.getApplicationName());
                            children.setUrl(pagerApplication1.getUrl());
                            //查询图标
                            if (ToolUtil.isNotEmpty(pagerApplication1.getFileId())) {
                                //SuperFile superFile = superFileRepository.getOne(pagerApplication.getFileId());
                                VoiceFile voiceFile = voiceFileRepository.findByUnid(pagerApplication.getFileId());
                                if (ToolUtil.isNotEmpty(voiceFile)) {
                                    children.setFileId(voiceFile.getUnid());
                                    children.setName(voiceFile.getFileName());
                                    children.setFileUrl(url + voiceFile.getUnid());
                                }
                            }
                            childrens.add(children);
                        }
                    }
                    tagApplicationInfoDTO.setChildren(childrens);
                }
                tagApplicationInfoDTOS.add(tagApplicationInfoDTO);
            }
        }
        return tagApplicationInfoDTOS;
    }




    @Override
    public List<PagerApplication> selectApp(Integer status, String applicationName, Integer tagId, Integer currentPage, Integer size) {
        String jpqlCondition = "SELECT application_id as applicationId,application_name as applicationName," +
                " create_time as createTime,file_id as fileId,level as level,parent_id as parentId," +
                " sort as sort,tag_id as tagId, url as url,status as status FROM pager_application WHERE status !=2 ";
        //判断状态值是否为空
        if (ToolUtil.isNotEmpty(status)){
            jpqlCondition += " and status =:status ";
        }

        //判断应用名称是否为空
        if (ToolUtil.isNotEmpty(applicationName)){
            jpqlCondition += " and application_name like CONCAT('%',:applicationName,'%')";
        }
        jpqlCondition +="and level = 1 ";

        jpqlCondition +=" and tag_id = :tagId";

        jpqlCondition += " ORDER BY sort ";

        Query query = em.createNativeQuery(jpqlCondition);

        if (ToolUtil.isNotEmpty(status)){
            query.setParameter("status",status);
        }
        if (ToolUtil.isNotEmpty(applicationName)){
            query.setParameter("applicationName",applicationName);
        }
        query.setParameter("tagId",tagId);

        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PagerApplication.class));
        query.setFirstResult((currentPage - 1) * size);
        query.setMaxResults(size);
        List<PagerApplication> pagerApplicationList = query.getResultList();
        return pagerApplicationList;
    }

    @Override
    public Integer selectAppTotal(Integer status, String applicationName, Integer tagId) {
        String jpqlCondition = "SELECT application_id as applicationId,application_name as applicationName,url as url,status as status FROM pager_application WHERE status !=2 ";
        //判断状态值是否为空
        if (ToolUtil.isNotEmpty(status)){
            jpqlCondition += " and status =:status ";
        }
        //判断应用名称是否为空
        if (ToolUtil.isNotEmpty(applicationName)){
            jpqlCondition += " and application_name =:applicationName";
        }
        jpqlCondition += "and tag_id = :tagId";
        jpqlCondition += " ORDER BY sort";
        Query query = em.createNativeQuery(jpqlCondition);
        if (ToolUtil.isNotEmpty(status)){
            query.setParameter("status",status);
        }
        if (ToolUtil.isNotEmpty(applicationName)){
            query.setParameter("applicationName",applicationName);
        }
        query.setParameter("tagId",tagId);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PagerAppDto.class));
        List<PagerAppDto> pagerAppDtos = query.getResultList();
        return pagerAppDtos.size();
    }

    @Override
    public void deleteApplication(List<PagerApplication> pagerApplicationList) {
        for (PagerApplication pagerApplication : pagerApplicationList){
            List<PagerApplication>  childrenPagerApplication =  pagerApplicationRepository.findAllByParentId(pagerApplication.getApplicationId());
            for (PagerApplication childrenApplication : childrenPagerApplication) {
                childrenApplication.setStatus(2);
                pagerApplicationRepository.save(childrenApplication);
            }
            pagerApplication.setStatus(2);
            pagerApplicationRepository.save(pagerApplication);
        }
    }

    @Override
    public Integer getCount(Integer status, String applicationName, Integer tagId, Integer page, Integer size) {
        int count = 0;
        String jpqlCondition = "SELECT application_id as applicationId,application_name as applicationName,url as url,status as status FROM pager_application WHERE status !=2 ";
        //判断状态值是否为空
        if (ToolUtil.isNotEmpty(status)){
            jpqlCondition += " and status =:status ";
        }
        //判断应用名称是否为空
        if (ToolUtil.isNotEmpty(applicationName)){
            jpqlCondition += " and application_name =:applicationName";
        }
        jpqlCondition += "and tag_id = :tagId";
        jpqlCondition += " and LEVEL  = 1 ";
        jpqlCondition += " ORDER BY sort";
        Query query = em.createNativeQuery(jpqlCondition);
        if (ToolUtil.isNotEmpty(status)){
            query.setParameter("status",status);
        }
        if (ToolUtil.isNotEmpty(applicationName)){
            query.setParameter("applicationName",applicationName);
        }
        query.setParameter("tagId",tagId);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(PagerAppDto.class));
        List<PagerAppDto> pagerAppDtos = query.getResultList();
        if (ToolUtil.isNotEmpty(pagerAppDtos)){
            count = pagerAppDtos.size();
        }
        return count;
    }

}
