package com.unionpay.pager.controller;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.dao.PagerTagRepository;
import com.unionpay.pager.domain.PagerTag;
import com.unionpay.pager.dto.PagerTagDto;
import com.unionpay.pager.dto.addTagRequestDto;
import com.unionpay.pager.dto.updateTagDto;
import com.unionpay.pager.service.PagerTagService;
import com.unionpay.services.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zhaijunpeng
 * @create: 2019-07-18 13:52
 **/
@RestController
@RequestMapping("/pagerTag")
@Api(value = "/pagerTag", tags = "模板配置管理-页签管理", description = "模板配置管理-页签管理")
public class PagerTagController {

    @Autowired
    private PagerTagService pagerTagService;
    @Autowired
    private PagerTagRepository pagerTagRepository;


    @ApiOperation(value = "页签展示与筛选",notes = "页签展示与筛选")
    @GetMapping(value = "/selectPagerTags")
    @ResponseBody
    public Map selectPagerTags(@RequestParam(value = "tagName",required = false)String tagName,
                               @RequestParam(defaultValue = "1")Integer page,
                               @RequestParam(defaultValue = "10") Integer size){
        List<PagerTag> pagerTags =  pagerTagService.selectPagerTags(tagName,page,size);
        //分页信息
        Pager pager = new Pager();
        pager.setCurrentPage(page);
        pager.setSize(size);
        //总数量
        pager.setTotal(pagerTagService.findPagerTagsTotle(tagName));
        Map<String, Object> map = new HashMap<>();
        map.put("code", "200");
        map.put("message", "操作成功");
        map.put("data",pagerTags);
        map.put("pager",pager);
        return map;
    }

    @ApiOperation(value = "页签删除",notes = "页签删除")
    @GetMapping(value = "/removePagerTags")
    @ResponseBody
    public RESTResultBean removePagerTags(@RequestParam(value = "tagId")Integer tagId){
        RESTResultBean result = pagerTagService.checkRelation(tagId);
        return result;
    }

    @ApiOperation(value = "页签新增",notes = "页签新增")
    @PostMapping(value = "/addPagerTag")
    @ResponseBody
    public Map addPagerTag(@RequestBody addTagRequestDto addTagRequestDto){
        PagerTag pagerTag = pagerTagService.addPagerTagArticle(addTagRequestDto);
        if (ToolUtil.isNotEmpty(pagerTag)){
            Map<String, Object> map = new HashMap<>();
            map.put("code", "200");
            map.put("message", "添加成功");
            map.put("data",pagerTag);
            return map;
        }else {
            Map<String, Object> map = new HashMap<>();
            map.put("code", "400");
            map.put("message", "添加失败");
            map.put("data",pagerTag);
            return map;
        }
    }

    @ApiOperation(value = "页签修改",notes = "页签修改")
    @PostMapping(value = "/updatePagerTag")
    @Transactional
    @ResponseBody
    public Map updatePagerTag(@RequestBody updateTagDto updateTagDto){
        List<PagerTag> pagerTags = pagerTagService.seleTagById(updateTagDto.getTagId());
        if (pagerTags.size()>0) {
            PagerTag pagerTag = pagerTags.get(0);
            Date createTime = pagerTag.getCreateTime();
            pagerTag.setTagId(updateTagDto.getTagId());
            pagerTag.setTagTitle(updateTagDto.getTagTitle());
            pagerTag.setTagName(updateTagDto.getTagName());
            pagerTag.setFormwork(updateTagDto.getFormwork());
            pagerTag.setStatus(updateTagDto.getStatus());
            pagerTag.setOrgName(updateTagDto.getOrgName());
            pagerTag.setOrgId(updateTagDto.getOrgId());
            pagerTag.setCreateTime(createTime);
            if(updateTagDto.getFormwork() == 3){
                pagerTag.setColumnName(updateTagDto.getColumnName());
                pagerTag.setIsTogether(updateTagDto.getIsTogether());
                pagerTag.setHrefurl(updateTagDto.getHrefurl());
                pagerTag.setColumnId(updateTagDto.getColumnId());
                pagerTag.setChnlId(updateTagDto.getChnlId());
            }
            if (updateTagDto.getFormwork()==4){
                pagerTag.setHrefurl(updateTagDto.getHrefurl());
            }
            pagerTagRepository.save(pagerTag);
            RESTResultBean result = new RESTResultBean(200, "修改成功");
            Map<String, Object> map = new HashMap<>();
            map.put("code", "200");
            map.put("message", "修改成功");
            map.put("data",pagerTag);
            return map;
        }else {
            Map<String, Object> map = new HashMap<>();
            map.put("code", "400");
            map.put("message", "请选择正确的页签进行修改！！！！");
            map.put("data",null);
            return map;
        }
    }

}
