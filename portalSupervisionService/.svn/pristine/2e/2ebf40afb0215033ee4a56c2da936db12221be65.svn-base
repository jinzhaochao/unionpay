package com.unionpay.pager.controller;

import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.dao.PagerApplicationRepository;
import com.unionpay.pager.dao.PagerManagerRepository;
import com.unionpay.pager.dao.PagerManagerTagRepository;
import com.unionpay.pager.dao.PagerTagRepository;
import com.unionpay.pager.domain.PagerManager;
import com.unionpay.pager.domain.PagerManagerTag;
import com.unionpay.pager.domain.PagerTag;
import com.unionpay.pager.dto.*;
import com.unionpay.pager.service.PagerApplicationService;
import com.unionpay.pager.service.PagerManagerService;
import com.unionpay.pager.service.PagerManagerTagService;
import com.unionpay.pager.service.PagerTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-18 13:51
 **/
@RestController
@RequestMapping("/pagerManager")
@Api(value = "/pagerManager", tags = "页面管理界面", description = "页面管理界面")
public class PagerManagerController {

    @Autowired
    private PagerManagerService pagerManagerService;
    @Autowired
    private PagerManagerRepository pagerManagerRepository;
    @Autowired
    private PagerManagerTagRepository pagerManagerTagRepository;
    @Autowired
    private PagerManagerTagService pagerManagerTagService;
    @Autowired
    private PagerTagRepository pagerTagRepository;
    @Autowired
    private PagerTagService pagerTagService;
    @Autowired
    private PagerApplicationRepository pagerApplicationRepository;
    @Autowired
    private PagerApplicationService pagerApplicationService;


    @ApiOperation(value = "页面下列表分组", notes = "页面下列表分组")
    @PostMapping("/SelectPagerTitile")
    @ResponseBody
    public Map<String, Object> SelectPagerTitile(@RequestParam(value = "pageId") Integer pageId) {
        Map<String, Object> map = new HashMap<>();
        try {
            PagerManager pagerManager = pagerManagerRepository.getOne(pageId);
            if (ToolUtil.isNotEmpty(pagerManager)) {
                //在页面与页签关联表中，查询该页面下的页签
                List<PagerManagerTag> pagerManagerTags = pagerManagerTagService.selectPagerManagerTag(pageId);
                System.err.print("dsd: " + pagerManagerTags.size());
                if (ToolUtil.isNotEmpty(pagerManagerTags)) {
                    Map<Integer, String> map1 = new HashMap<>();
                    //展示该页面下页签的分组
                    for (int i = 0; i < pagerManagerTags.size(); i++) {
                        PagerTag pagerTag = pagerTagService.getPagerTag(pagerManagerTags.get(i).getTagId());
                        if (ToolUtil.isNotEmpty(pagerTag)) {
                            if (pagerTag.getFormwork() == 1) {
                                map1.put(1, "应用列表(无分组)");
                            } else if (pagerTag.getFormwork() == 2) {
                                map1.put(2, "文章列表");
                            } else {
                                map1.put(3, "应用列表(分组)");
                            }
                        }
                    }

                    map.put("code", "200");
                    map.put("message", "展示该页面下页签分组成功");
                    map.put("data", map1);
                    //return Result.build(200, "展示该页面下页签分组成功", map);
                } else {
                    map.put("code", "200");
                    map.put("message", "该页面没有所属页签");
                    map.put("data", null);
                    //return Result.build(200, "该页面没有所属页签", null);
                }
            } else if (ToolUtil.isEmpty(pagerManager)) {
                map.put("code", "200");
                map.put("message", "该页面不存在");
                map.put("data", null);
                //return Result.build(200, "该页面不存在", null);
            } else {
                map.put("code", "400");
                map.put("message", "系统错误");
                map.put("data", null);
                //return Result.failure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "200");
            map.put("message", "系统异常，请联系管理员");
            map.put("data", null);
            //return Result.failure(e.toString());
        }
        return map;
    }

    @ApiOperation(value = "页面下列表分组信息", notes = "页面下列表分组信息")
    @PostMapping("/SelectPagerTitileGrouping")
    @ResponseBody
    public Map<String, Object> SelectPagerTitileGrouping(@RequestParam(value = "pageId") Integer pageId) {
        Map<String, Object> map = new HashMap<>();
        try {
            PagerManager pagerManager = pagerManagerRepository.getOne(pageId);
            if (ToolUtil.isNotEmpty(pagerManager)) {
                //在页面与页签关联表中，查询该页面下的页签
                List<PagerManagerTag> pagerManagerTags = pagerManagerTagService.selectPagerManagerTag(pageId);
                if (ToolUtil.isNotEmpty(pagerManagerTags)) {
                    //定义分组展示的信息（list）
                    List<ApplicationInfoDTO> applicationInfoDTOS = new ArrayList();
                    //查找该页面下的所有页签信息
                    for (int i = 0; i < pagerManagerTags.size(); i++) {
                        //页签信息
                        PagerTag pagerTag = pagerTagRepository.findAllByTagIdAndStatus(pagerManagerTags.get(i).getTagId(), 1);
                        if (ToolUtil.isNotEmpty(pagerTag)) {
                            ApplicationInfoDTO applicationInfoDTO = new ApplicationInfoDTO();
                            applicationInfoDTO.setTagId(pagerTag.getTagId());
                            applicationInfoDTO.setTagName(pagerTag.getTagName());
                            applicationInfoDTO.setTagTitle(pagerTag.getTagTitle());
                            applicationInfoDTO.setFormwork(pagerTag.getFormwork());
                            applicationInfoDTO.setChnlId(pagerTag.getChnlId());
                            applicationInfoDTO.setColumnId(pagerTag.getColumnId());
                            applicationInfoDTO.setColumnName(pagerTag.getColumnName());
                            applicationInfoDTO.setHrefurl(pagerTag.getHrefurl());
                            applicationInfoDTO.setIsTogether(pagerTag.getIsTogether());
                            //查找该页签下的所有应用
                            List<TagApplicationInfoDTO> tagApplicationInfoDTOS = pagerApplicationService.applicationInfos(pagerTag.getTagId(), 1);
                            applicationInfoDTO.setApplications(tagApplicationInfoDTOS);
                            applicationInfoDTOS.add(applicationInfoDTO);
                        }
                    }
                    map.put("code", "200");
                    map.put("message", "展示该页面下页签分组详情");
                    map.put("data", applicationInfoDTOS);
                } else {
                    map.put("code", "200");
                    map.put("message", "该页面没有所属页签");
                    map.put("data", null);
                }
            } else if (ToolUtil.isEmpty(pagerManager)) {
                map.put("code", "200");
                map.put("message", "该页面不存在");
                map.put("data", null);
            } else {
                map.put("code", "400");
                map.put("message", "错误操作");
                map.put("data", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("message", "系统异常，请联系管理员");
            map.put("data", null);
        }
        return map;
    }


    /**
     * 页面信息查询与分页
     *
     * @return
     */
    @ApiOperation(value = "页面信息查询与分页", notes = "页面信息查询与分页")
    @PostMapping("/SelectPagerManager")
    @ResponseBody
    public Map<String, Object> SelectPagerManager(@Valid @RequestBody PagerManagerRequestDTO PagerManagerRequestDTO) {
        //RESTResultBean result = new RESTResultBean();
        Map<String, Object> map = new HashMap<>();
        Integer page = PagerManagerRequestDTO.getPage();
        Integer size = PagerManagerRequestDTO.getSize();
        String pageName = PagerManagerRequestDTO.getPageName();
        List<PagerManagerModel> pagerManagerModels = pagerManagerService.SelectPagerManager(page, size, pageName);
        //添加页签信息
        if (ToolUtil.isNotEmpty(pagerManagerModels)) {
            Pager pager = new Pager();
            pager.setCurrentPage(page);
            pager.setSize(size);
            pager.setTotal(pagerManagerService.getCount(page, size, pageName));
            List<PagerManagerInfo> pagerManagerInfos = new ArrayList<>();
            for (PagerManagerModel pagerManagerModel : pagerManagerModels) {
                PagerManagerInfo PagerManagerInfo = new PagerManagerInfo();
                PagerManagerInfo.setPageId(pagerManagerModel.getPageId());
                PagerManagerInfo.setPageName(pagerManagerModel.getPageName());
                PagerManagerInfo.setOrgName(pagerManagerModel.getOrgName());
                PagerManagerInfo.setStatus(pagerManagerModel.getStatus());
                PagerManagerInfo.setOrgId(pagerManagerModel.getOrgId());
                List<PagerManagerTagInfo> pagerManagerTagInfos = new ArrayList<>();
                List<PagerManagerTag> pagerManagerTags = pagerManagerTagService.selectPagerManagerTag(pagerManagerModel.getPageId());
                //获取页面下所有页签信息
                if (ToolUtil.isNotEmpty(pagerManagerTags)) {
                    for (int i = 0; i < pagerManagerTags.size(); i++) {
                        PagerManagerTagInfo pagerManagerTagInfo = new PagerManagerTagInfo();
                        pagerManagerTagInfo.setPagerManagerTagId(pagerManagerTags.get(i).getId());
                        pagerManagerTagInfo.setTagId(pagerManagerTags.get(i).getTagId());
                        PagerTag pagerTag = pagerTagRepository.findAllByTagIdAndStatus(pagerManagerTags.get(i).getTagId(),1);
                        if (ToolUtil.isNotEmpty(pagerTag)) {
                            pagerManagerTagInfo.setTagName(pagerTag.getTagName());
                            pagerManagerTagInfo.setSort(pagerManagerTags.get(i).getSort());
                            pagerManagerTagInfos.add(pagerManagerTagInfo);
                        }
                    }
                }
                PagerManagerInfo.setPagerTags(pagerManagerTagInfos);
                pagerManagerInfos.add(PagerManagerInfo);
            }

            /*JSONObject data = new JSONObject();
            data.put("pagerManagerModels", pagerManagerModels);
            result.setCode(200);
            result.setMessage("查询成功");
            result.setData(data);
            result.setPager(pager);*/
            map.put("code", "200");
            map.put("message", "查询成功");
            map.put("data", pagerManagerInfos);
            map.put("pager", pager);
            //return Result.success(pagerManagerModels, pager);
        } else if (ToolUtil.isEmpty(pagerManagerModels)) {
            /*result.setCode(200);
            result.setMessage("查询成功，没有信息");
            result.setData(null);*/
            map.put("code", "200");
            map.put("message", "查询成功，没有信息");
            map.put("data", null);
            map.put("pager", null);
            //return Result.build(200, "查询成功，没有信息", pagerManagerModels);
        } else {
            map.put("code", "400");
            map.put("message", "查询失败");
            map.put("data", null);
            map.put("pager", null);
           /* result.setCode(400);
            result.setMessage("查询失败");*/
        }
        return map;
    }

    @ApiOperation(value = "获取页面信息详情", notes = "获取页面信息详情")
    @GetMapping("/GetOnePagerManager")
    @ResponseBody
    public Map<String, Object> GetOnePagerManager(@RequestParam(value = "pageId") Integer pageId) {
        //RESTResultBean result = new RESTResultBean();
        Map<String, Object> map = new HashMap<>();
        try {
            PagerManager pagerManager = pagerManagerRepository.getOne(pageId);
            if (ToolUtil.isNotEmpty(pagerManager)) {
                PagerManagerInfo PagerManagerInfo = new PagerManagerInfo();
                PagerManagerInfo.setPageId(pagerManager.getPageId());
                PagerManagerInfo.setPageName(pagerManager.getPageName());
                PagerManagerInfo.setOrgName(pagerManager.getOrgName());
                PagerManagerInfo.setStatus(pagerManager.getStatus());
                List<PagerManagerTagInfo> pagerManagerTagInfos = new ArrayList<>();
                List<PagerManagerTag> pagerManagerTags = pagerManagerTagService.selectPagerManagerTag(pageId);
                //获取页面下所有页签信息
                if (ToolUtil.isNotEmpty(pagerManagerTags)) {
                    for (int i = 0; i < pagerManagerTags.size(); i++) {
                        PagerManagerTagInfo pagerManagerTagInfo = new PagerManagerTagInfo();
                        pagerManagerTagInfo.setPagerManagerTagId(pagerManagerTags.get(i).getId());
                        pagerManagerTagInfo.setTagId(pagerManagerTags.get(i).getTagId());
                        PagerTag pagerTag = pagerTagRepository.findAllByTagIdAndStatus(pagerManagerTags.get(i).getTagId(),1);
                        if (ToolUtil.isNotEmpty(pagerTag)) {
                            pagerManagerTagInfo.setTagName(pagerTag.getTagName());
                            pagerManagerTagInfo.setSort(pagerManagerTags.get(i).getSort());
                            pagerManagerTagInfos.add(pagerManagerTagInfo);
                        }
                    }
                }
                PagerManagerInfo.setPagerTags(pagerManagerTagInfos);
                map.put("code", "200");
                map.put("message", "获取页面信息详情成功");
                map.put("data", PagerManagerInfo);
                /*JSONObject data = new JSONObject();
                data.put("PagerManagerInfo", PagerManagerInfo);
                result = new RESTResultBean(200, "获取页面信息详情成功", data);*/
                //return Result.build(200, "获取页面信息详情成功", PagerManagerInfo);
            } else {
                map.put("code", "200");
                map.put("message", "该页面信息不存在");
                map.put("data", null);
                //result = new RESTResultBean(200, "该页面信息不存在", null);
                //return Result.build(400, "该页面信息不存在", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("message", "系统异常，请联系管理员");
            map.put("data", null);
            //result = new RESTResultBean(400, e.toString(), null);
            //return Result.failure(e.toString());
        }
        return map;
    }

    @ApiOperation(value = "添加页面信息", notes = "添加页面信息")
    @PostMapping("/addPagerManager")
    @ResponseBody
    @Transactional
    public RESTResultBean addPagerManager(@Valid @RequestBody PagerManagerInfo PagerManagerInfo) {
        RESTResultBean result = new RESTResultBean();
        try {
            PagerManager pagerManager = new PagerManager();
            //pagerManager.setPageId(0);
            pagerManager.setPageName(PagerManagerInfo.getPageName());
            pagerManager.setOrgId(PagerManagerInfo.getOrgId());
            pagerManager.setOrgName(PagerManagerInfo.getOrgName());
            pagerManager.setStatus(PagerManagerInfo.getStatus());
            pagerManager.setCreateTime(new Date());
            //页签为空时，不能添加页面
            if(ToolUtil.isEmpty(PagerManagerInfo.getPagerTags())){
                result = new RESTResultBean(400, "页签不能为空", null);
            }
            //添加页面信息
            PagerManager pagerManager1 = pagerManagerRepository.save(pagerManager);
            List<PagerManagerTagInfo> pagerManagerTagInfos = PagerManagerInfo.getPagerTags();
            //添加页签（页面与页签关联表）
            if (ToolUtil.isNotEmpty(pagerManagerTagInfos)) {
                for (int i = 0; i < pagerManagerTagInfos.size(); i++) {
                    int c = i + 1;
                    PagerManagerTag pagerManagerTag = new PagerManagerTag();
                    pagerManagerTag.setId(null);
                    pagerManagerTag.setPageId(pagerManager1.getPageId());
                    pagerManagerTag.setTagId(pagerManagerTagInfos.get(i).getTagId());
                    pagerManagerTag.setStatus(1);
                    pagerManagerTag.setSort(c);
                    pagerManagerTag.setCreateTime(new Date());
                    pagerManagerTagRepository.save(pagerManagerTag);
                }
            }
            result = new RESTResultBean(200, "添加页面信息成功", null);
            //return Result.build(200, "添加页面信息成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            result = new RESTResultBean(400, "系统异常，请联系管理员", null);
            //return Result.failure(e.toString());
        }
        return result;
    }

    /**
     * 修改页面信息
     *
     * @return
     */
    @ApiOperation(value = "修改页面信息", notes = "修改页面信息")
    @PostMapping("/updatePagerManager")
    @ResponseBody
    public RESTResultBean updatePagerManager(@Valid @RequestBody PagerManagerInfo PagerManagerInfo) {
        RESTResultBean result = new RESTResultBean();
        try {
            PagerManager pagerManager = pagerManagerRepository.getOne(PagerManagerInfo.getPageId());
            if (ToolUtil.isNotEmpty(pagerManager)) {
                pagerManager.setPageName(PagerManagerInfo.getPageName());
                pagerManager.setOrgId(PagerManagerInfo.getOrgId());
                pagerManager.setOrgName(PagerManagerInfo.getOrgName());
                pagerManager.setStatus(PagerManagerInfo.getStatus());
                //修改页面信息
                PagerManager pagerManager2 = pagerManagerRepository.saveAndFlush(pagerManager);
                //查询该页面下的所有页签
                List<PagerManagerTag> pagerManagerTags = pagerManagerTagService.selectPagerManagerTag(PagerManagerInfo.getPageId());
                //把原有该页面下页签删除
                if (ToolUtil.isNotEmpty(pagerManagerTags)) {
                    for (int i = 0; i < pagerManagerTags.size(); i++) {
                        PagerManagerTag pagerManagerTag = pagerManagerTags.get(i);
                        pagerManagerTagRepository.deleteById(pagerManagerTag.getId());
                    }
                }
                //添加页面下页签
                if (ToolUtil.isNotEmpty(PagerManagerInfo.getPagerTags())) {
                    for (int j = 0; j < PagerManagerInfo.getPagerTags().size(); j++) {
                        int c = j + 1;
                        PagerManagerTagInfo pagerManagerTagInfo = PagerManagerInfo.getPagerTags().get(j);
                        PagerManagerTag pagerManagerTag = new PagerManagerTag();
                        pagerManagerTag.setId(null);
                        pagerManagerTag.setPageId(pagerManager.getPageId());
                        pagerManagerTag.setTagId(pagerManagerTagInfo.getTagId());
                        pagerManagerTag.setStatus(1);
                        pagerManagerTag.setSort(c);
                        pagerManagerTag.setCreateTime(new Date());
                        pagerManagerTagRepository.save(pagerManagerTag);
                    }
                }
                result = new RESTResultBean(200, "修改页面信息成功", null);
                //return Result.build(200, "修改页面信息成功", null);
            } else if (ToolUtil.isEmpty(pagerManager)) {
                result = new RESTResultBean(200, "该页面信息不存在", null);
                // return Result.build(200, "该页面信息不存在", null);
            } else {
                result = new RESTResultBean(400, "修改失败", null);
                //return Result.failure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new RESTResultBean(400, "系统异常，请联系管理员", null);
            //return Result.failure(e.toString());
        }
        return result;
    }


    /**
     * 删除页面信息
     *
     * @return
     */
    @ApiOperation(value = "删除页面信息", notes = "删除页面信息")
    @GetMapping("/deletePagerManager")
    public RESTResultBean deletePagerManager(@RequestParam(value = "id") Integer pageId) {
        RESTResultBean result = new RESTResultBean();
        try {
            PagerManager pagerManager = pagerManagerRepository.getOne(pageId);
            if (ToolUtil.isNotEmpty(pagerManager)) {
                pagerManager.setStatus(2);
                PagerManager pagerManager2 = pagerManagerRepository.saveAndFlush(pagerManager);
                List<PagerManagerTag> pagerManagerTags = pagerManagerTagService.selectPagerManagerTag(pageId);
                if (ToolUtil.isNotEmpty(pagerManagerTags)) {
                    for (int i = 0; i < pagerManagerTags.size(); i++) {
                        PagerManagerTag pagerManagerTag = pagerManagerTags.get(i);
                        pagerManagerTag.setStatus(0);
                        PagerManagerTag pagerManagerTag1 = pagerManagerTagService.update(pagerManagerTag);
                        if (ToolUtil.isEmpty(pagerManagerTag1)) {
                            result = new RESTResultBean(400, "删除失败", null);
                            //return Result.build(400, "删除失败", null);
                        }
                    }
                }
                result = new RESTResultBean(200, "删除成功", null);
                //return Result.build(200, "删除成功", null);
            } else if (ToolUtil.isEmpty(pagerManager)) {
                result = new RESTResultBean(200, "该页面信息不存在", null);
                //return Result.build(200, "该页面信息不存在", null);
            } else {
                result = new RESTResultBean(400, "删除失败", null);
                // return Result.failure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new RESTResultBean(400, "系统异常，请联系管理员", null);
            //return Result.failure(e.toString());
        }
        return result;
    }

    @ApiOperation(value = "页签下拉框", notes = "页签下拉框")
    @PostMapping("/dropdownBoxPagerTag")
    public Map<String, Object> dropdownBoxPagerTag(@RequestParam(value = "orgId") Integer orgId) {
        Map<String, Object> map = new HashMap<>();
        List<DropdownPagerTag> dropdownPagerTags = pagerTagService.dropdownBoxPagerTag(orgId);
        if (ToolUtil.isNotEmpty(dropdownPagerTags)) {
            map.put("code", "200");
            map.put("message", "查询成功");
            map.put("data", dropdownPagerTags);
        } else if (ToolUtil.isEmpty(dropdownPagerTags)) {
            map.put("code", "200");
            map.put("message", "无页签标题");
            map.put("data", null);
        } else {
            map.put("code", "400");
            map.put("message", "展示页签标题失败");
            map.put("data", null);
        }

        return map;
    }


}
