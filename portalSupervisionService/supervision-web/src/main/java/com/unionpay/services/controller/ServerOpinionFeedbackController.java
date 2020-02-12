package com.unionpay.services.controller;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.Server0pinionFeedback;
import com.unionpay.services.model.Server0pinionFeedbackModelDto;
import com.unionpay.services.service.ServerOpinionFeedbackService;
import com.unionpay.services.util.Result;
import com.unionpay.sms.service.OmOrganizationService;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 意见反馈
 * @author lishuang
 * @date 2019-03-20
 */
@Api(tags = "意见反馈",description = "意见反馈")
@RestController
@RequestMapping("/serverOpinionFeedback")
public class ServerOpinionFeedbackController {
    @Autowired
    private OmUserService omUserService;
    @Autowired
    private OmOrganizationService omOrganizationService;
    @Autowired
    private ServerOpinionFeedbackService serverOpinionFeedbackService;

    /**
     * 意见反馈新增
     * @param json
     * @param request
     * @return
     *
     * @author lishuang
     * @date 2019-03-18
     */
    @ApiOperation(value="新增意见反馈", notes="新增意见反馈")
    @PostMapping("/add")
    @ResponseBody
    public Result addServiceOpinionFeedBack(@RequestBody String json, HttpServletRequest request){
        try {
            //获取页面传入的参数
            JSONObject params = JSONObject.parseObject(json);
            Integer id = params.getInteger("id");
            String content = params.getString("content");
            //获取反馈人信息
            String userid = SessionUtils.getUserId(request);
            OmUser omUser = omUserService.findByUserid(userid);
            if (ToolUtil.isEmpty(omUser)){
                return Result.failure("请登录");
            }
            if (ToolUtil.isEmpty(content)){
                return Result.failure("请填写反馈意见");
            }
            Server0pinionFeedback opinion = serverOpinionFeedbackService.add(id,content,omUser.getEmpid());
            return Result.success(opinion);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 分页查询意见反馈列表
     * @param params
     * @return
     *
     * @author lishuang
     * @date 2019-03-20
     */
    @ApiOperation(value="分页查询意见反馈列表", notes="{\n"+"\"page\":\"1\",\n"+ "\"size\":\"10\",\n"+"\"orgid\":\"235\",\n"+"\"status\":\"1\"\n"+"}")
    @PostMapping("/getPage")
    @ResponseBody
    public Result getPage(@RequestBody String params){
        try {
            JSONObject jsonObject = JSONObject.parseObject(params);
            Integer page = jsonObject.getInteger("page");
            Integer size = jsonObject.getInteger("size");
            Integer orgid = jsonObject.getInteger("orgId");
            Integer status = jsonObject.getInteger("status");
            List<Server0pinionFeedbackModelDto> list = serverOpinionFeedbackService.getAll(page,size,orgid,status);
            if (ToolUtil.isNotEmpty(list)){
                Pager pager = new Pager();
                pager.setCurrentPage(page);
                pager.setSize(size);
                pager.setTotal(serverOpinionFeedbackService.getCount(page,size,orgid,status));
                return Result.success(list,pager);
            }else if (ToolUtil.isEmpty(list)){
                return Result.build(200,"查询成功，没有信息",list);
            }else {
                return Result.failure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 编辑意见反馈详情
     * @param serverOpinionFeedbackModelDto
     * @return
     */
    @ApiOperation(value="编辑意见反馈详情", notes="编辑意见反馈详情")
    @PostMapping("/edit")
    @ResponseBody
    public Result edit(@Valid @RequestBody Server0pinionFeedbackModelDto serverOpinionFeedbackModelDto,HttpServletRequest request){
        try {
            String userid = SessionUtils.getUserId(request);
            OmUser omUser = omUserService.findByUserid(userid);
            if (ToolUtil.isEmpty(omUser)){
                return Result.failure("请先登录再进行反馈处理");
            }
            /*if (ToolUtil.isEmpty(serverOpinionFeedbackModelDto.getReplyReason())){
                return Result.failure("请填写处理说明");
            }*/
            serverOpinionFeedbackService.edit(serverOpinionFeedbackModelDto,omUser.getEmpid());
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }
}
