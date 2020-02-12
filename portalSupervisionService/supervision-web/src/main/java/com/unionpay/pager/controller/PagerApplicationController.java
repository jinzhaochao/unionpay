package com.unionpay.pager.controller;

import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.dao.PagerApplicationRepository;
import com.unionpay.pager.domain.PagerApplication;
import com.unionpay.pager.dto.*;
import com.unionpay.pager.service.PagerApplicationService;
import com.unionpay.voice.domain.VoiceFile;
import com.unionpay.voice.service.VoiceFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-18 13:51
 **/
@RestController
@RequestMapping("/pagerApplication")
@Api(value = "/pagerApplication", tags = "应用管理", description = "应用管理")
public class PagerApplicationController {
    @Autowired
    private VoiceFileService voiceFileService;

    @Autowired
    private PagerApplicationService pagerApplicationService;
    @Autowired
    private PagerApplicationRepository pagerApplicationRepository;

    @ApiOperation(value = "应用筛选与查询",notes = "应用筛选与查询")
    @GetMapping(value = "/selectApplication")
    @ResponseBody
    public Map<String, Object> selectApplication(@RequestParam(value = "status",required = false)Integer status,
                                                 @RequestParam(value = "applicationName",required = false)String applicationName,
                                                 @RequestParam(value = "tagId")Integer tagId,
                                                 @RequestParam(defaultValue = "1")Integer page,
                                                 @RequestParam(defaultValue = "10")Integer size){
        List<PagerApplication> pagerApplicationList = pagerApplicationService.selectApp(status,applicationName,tagId,page,size);
        List<selectAppDto> selectAppDtoList = new ArrayList<>();
        for (PagerApplication pagerapplication : pagerApplicationList){
            List<fileInfo> fileInfos = new ArrayList<>();
            selectAppDto selectAppDto = new selectAppDto();
            String fileId = pagerapplication.getFileId();
            fileInfo fileInfo = new fileInfo();
            VoiceFile voiceFile = voiceFileService.get(fileId);
            if (ToolUtil.isNotEmpty(voiceFile)) {
                fileInfo.setFileId(fileId);
                fileInfo.setFileName(voiceFile.getFileName());
            }
            fileInfos.add(fileInfo);
            selectAppDto.setFiles(fileInfos);
            BeanUtils.copyProperties(pagerapplication,selectAppDto);
            if (pagerapplication.getLevel()!=1||ToolUtil.isNotEmpty(pagerapplication.getParentId())){
                continue;
            }
            if (pagerapplication.getLevel()==1){
                List<PagerApplication> pagerApplications = pagerApplicationRepository.findAllByParentIdAndStatusIsNotAndTagIdOrderBySort(pagerapplication.getApplicationId(),2,tagId);
                if (ToolUtil.isNotEmpty(pagerApplications)) {
                    List<appGroupapp> appGroupapps = new ArrayList<>();
                    for (PagerApplication pagerApplication1 : pagerApplications) {
                        appGroupapp appGroupapp = new appGroupapp();
                        appGroupapp.setApplicationName(pagerApplication1.getApplicationName());
                        appGroupapp.setUrl(pagerApplication1.getUrl());
                        appGroupapps.add(appGroupapp);
                    }
                    selectAppDto.setAppGroup(appGroupapps);
                }
            }
            selectAppDtoList.add(selectAppDto);
        }
        //分页信息
        Pager pager = new Pager();
        pager.setCurrentPage(page);
        pager.setSize(size);
        //总数量
        pager.setTotal(pagerApplicationService.getCount(status, applicationName, tagId, page, size));
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("message", "操作成功");
        map.put("data",selectAppDtoList);
        map.put("pager",pager);
        return map;
    }

    @ApiOperation(value = "应用删除",notes = "应用删除")
    @PostMapping(value = "/delectApplication")
    @Transactional
    @ResponseBody
    public RESTResultBean delectApplication(@RequestBody delectAppRequest delectAppRequest){
        if (delectAppRequest.getApplicationIds().size()>0){
            //pagerApplicationService.deleteApplication(ids);
            List<PagerApplication> pagerApplicationList = pagerApplicationRepository.findAllByApplicationIdIn(delectAppRequest.getApplicationIds());
            if (pagerApplicationList.size()>0){
                RESTResultBean result = new RESTResultBean(200,"删除成功");
                pagerApplicationService.deleteApplication(pagerApplicationList);
                return result;
            }else {
                RESTResultBean result = new RESTResultBean(400,"请选择正确的应用进行删除");
                return result;
            }
        }else {
            RESTResultBean result = new RESTResultBean(400,"请选择正确的应用进行删除");
            return result;
        }

    }

    @ApiOperation(value = "应用禁用与启用",notes = "应用禁用与启用")
    @PostMapping(value = "/changAppStatus")
    @ResponseBody
    public RESTResultBean changAppStatus(@RequestBody appForbedenReq appForbedenReq){
        RESTResultBean result = new RESTResultBean(200,"操作成功");
        List<Integer> applicationIds = appForbedenReq.getApplicationIds();
        if (applicationIds.size()>0){
            for (Integer applicationId : applicationIds) {
                PagerApplication pagerApplication = pagerApplicationRepository.findByApplicationId(applicationId);
                pagerApplication.setStatus(appForbedenReq.getStatus());
                pagerApplicationRepository.save(pagerApplication);
            }
            result.setMessage("操作成功！！");
        }else {
            RESTResultBean restResultBean = new RESTResultBean(400,"请选择至少一条记录！！");
            return restResultBean;
        }
        return result;
    }

    @ApiOperation(value = "应用新增与修改",notes = "应用新增与修改")
    @PostMapping(value = "/addAndUpdateAppNoGroup")
    @Transactional
    @ResponseBody
    public RESTResultBean addAndUpdateAppNoGroup(@RequestBody appGroup appGroup){
        if (ToolUtil.isEmpty(appGroup.getAppGroup())) {
            PagerApplication pagerApplication = new PagerApplication();
            String message = null;
            if (ToolUtil.isEmpty(appGroup.getApplicationId()) || appGroup.getApplicationId() == 0) {
                message = "新增成功";
            } else {
                pagerApplication.setApplicationId(appGroup.getApplicationId());
                message = "修改成功";
            }
            pagerApplication.setApplicationName(appGroup.getApplicationName());
            pagerApplication.setUrl(appGroup.getUrl());
            pagerApplication.setStatus(appGroup.getStatus());
            pagerApplication.setSort(appGroup.getSort());
            pagerApplication.setFileId(appGroup.getFileId());
            pagerApplication.setTagId(appGroup.getTagId());
            pagerApplication.setCreateTime(new Date());
            pagerApplication.setLevel(1);
            pagerApplicationRepository.save(pagerApplication);
            RESTResultBean result = new RESTResultBean(200, message);
            return result;
        }else {
            List<appGroupapp> appGroupapps = appGroup.getAppGroup();
            PagerApplication pagerApplication = new PagerApplication();
            String message = null;
            if (ToolUtil.isEmpty(appGroup.getApplicationId()) || appGroup.getApplicationId()==0){
                message = "新增成功";
            }else {
                pagerApplication.setApplicationId(appGroup.getApplicationId());
                message = "修改成功";
            }
            pagerApplication.setApplicationName(appGroup.getApplicationName());
            pagerApplication.setUrl(appGroup.getUrl());
            pagerApplication.setStatus(appGroup.getStatus());
            pagerApplication.setSort(appGroup.getSort());
            pagerApplication.setFileId(appGroup.getFileId());
            pagerApplication.setTagId(appGroup.getTagId());
            pagerApplication.setCreateTime(new Date());
            pagerApplication.setLevel(1);
            pagerApplicationRepository.saveAndFlush(pagerApplication);
            pagerApplicationRepository.deleteAllByParentId(appGroup.getApplicationId());

            if (appGroupapps.size()>0) {
                for (int i = 0; i < appGroupapps.size(); i++) {
                    PagerApplication pagerApplication1 = new PagerApplication();
                    appGroupapp appGroupapp = appGroupapps.get(i);
                    pagerApplication1.setApplicationName(appGroupapp.getApplicationName());
                    pagerApplication1.setUrl(appGroupapp.getUrl());
                    pagerApplication1.setStatus(appGroup.getStatus());
                    pagerApplication1.setSort(i+1);
                    pagerApplication1.setParentId(pagerApplication.getApplicationId());
                    pagerApplication1.setTagId(appGroup.getTagId());
                    pagerApplication1.setCreateTime(new Date());
                    pagerApplication1.setLevel(2);
                    pagerApplicationRepository.saveAndFlush(pagerApplication1);
                }
            }
            RESTResultBean result = new RESTResultBean(200,message);
            return result;
        }
    }

}
