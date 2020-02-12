package com.unionpay.services.controller;

import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.*;
import com.unionpay.services.repository.ServerSuggestRepository;
import com.unionpay.services.service.ServerDeliverService;
import com.unionpay.services.service.ServerSuggestService;
import com.unionpay.services.util.Result;
import com.unionpay.services.util.ResultList;
import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.dao.OMUserRepository;
import com.unionpay.supervision.domain.OmOrganization;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.filter.submit.NoRepeatSubmit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/20/020 14:05
 * @Description:
 */
@RestController
@RequestMapping("/serverSuggest")
@Api(value = "/serverSuggest", tags = "服务反馈", description = "服务反馈")
public class ServerSuggestController {
    @Autowired
    private OMUserRepository omUserRepository;
    @Autowired
    private ServerSuggestRepository serverSuggestRepository;
    @Autowired
    private OMOrganizationRepository omOrganizationRepository;
    @Autowired
    private ServerSuggestService serverSuggestService;
    @Autowired
    private ServerDeliverService serverDeliverService;


    @ApiOperation(value = "添加服务反馈信息", notes = "添加服务反馈信息")
    @PostMapping("/addServerSuggest")
    @ResponseBody
    public Result addServerSuggest(@Valid @RequestBody ServerSuggest serverSuggest, HttpServletRequest request) {
        try {
            serverSuggest.setCreatetime(new Date());
            serverSuggest.setStatus(0);
            String userId = SessionUtils.getUserId(request);
            OmUser omUser = omUserRepository.findByUserid(userId);
            if (ToolUtil.isNotEmpty(omUser)) {
                serverSuggest.setEmpName(omUser.getEmpname());//修改为反馈人的姓名（中文）---lishuang 2019-07-25
                serverSuggest.setEmpId(omUser.getEmpid());
                serverSuggest.setEmpDeptId(omUser.getDeptorgid());
                serverSuggest.setEmpOrgId(omUser.getOrgid());
                OmOrganization omOrganization = omOrganizationRepository.findByOrgid(omUser.getDeptorgid());
                serverSuggest.setEmpDeptName(omOrganization.getOrgname());
            } else {
                return Result.build(400, "该用户未登陆", "");
            }
            ServerSuggest suggest = serverSuggestRepository.save(serverSuggest);
            if (ToolUtil.isNotEmpty(suggest)) {
                return Result.build(200, "添加成功", suggest);
            } else {
                return Result.build(400, "添加失败", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 查询某一个问题的详情
     */
    @GetMapping("/getOne")
    @ApiOperation(value = "查询某一个服务反馈信息详情", notes = "查询某一个服务反馈信息详情")
    public Result get(@RequestParam(value = "id") Integer id) {
        try {
            ServerSuggest suggest = serverSuggestService.findById(id);
            if (suggest == null) {
                throw new RuntimeException();
            }
            return Result.build(200, "查询成功", suggest);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 处理提交的服务反馈信息
     */
    @PostMapping("/update")
    @ApiOperation(value = "处理提交的服务反馈信息", notes = "处理提交的服务反馈信息")
    @ResponseBody
    public Result update(@Valid @RequestBody ServerSuggest serverSuggest, HttpServletRequest request) {
        try {
            ServerSuggest suggest = serverSuggestRepository.getOne(serverSuggest.getId());
            if (ToolUtil.isNotEmpty(suggest)) {
                String userId = SessionUtils.getUserId(request);
                OmUser omUser = omUserRepository.findByUserid(userId);
                suggest.setReplyId(omUser.getEmpid());
                suggest.setReplyTime(new Date());
                suggest.setReplyReason(serverSuggest.getReplyReason());
                suggest.setStatus(serverSuggest.getStatus());
                ServerSuggest suggest1 = serverSuggestService.edit(suggest);
                return Result.success(suggest1);
            } else {
                return Result.build(400, "该问题不存在", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }


    /**
     * 服务反馈信息查询与分页
     *
     * @return
     */
    @ApiOperation(value = "服务反馈信息查询与分页", notes = "服务反馈信息查询与分页")
    @PostMapping("/SelectAllSuggest")
    @ResponseBody
    public Result SelectAllSuggest(@Valid @RequestBody SugestRequest sugestRequest) {
        try {
            Integer page = sugestRequest.getPage();
            Integer size = sugestRequest.getSize();
            Integer orgId = sugestRequest.getOrgId();
            Integer status = sugestRequest.getStatus();
            List<ServerSuggestModel> serviceInfos = serverSuggestService.SelectAllSuggest(page, size, orgId, status);
            if (ToolUtil.isNotEmpty(serviceInfos)) {
                Pager pager = new Pager();
                pager.setCurrentPage(page);
                pager.setSize(size);
                pager.setTotal(serverSuggestService.getCount(page, size, orgId, status));
                return Result.success(serviceInfos, pager);
            } else if (ToolUtil.isEmpty(serviceInfos)) {
                return Result.build(200, "查询成功，没有信息", serviceInfos);
            } else {
                return Result.failure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * @Author jinzhao
     * @Date
     * @Description
     */
    @ApiOperation(value = "咨询条件分页查询", notes = "咨询条件分页查询")
    @PostMapping("/selectSuggestPage")
    @ResponseBody
    public ResultList selectSuggestPage(@RequestBody SuggestDto suggestDto, HttpServletRequest request) {
        ResultList resultList = new ResultList();
        try {

            String userid = SessionUtils.getUserId(request);
            OmUser user = omUserRepository.findByUserid(userid);
            String userId = user.getEmpid().toString();
            Integer page = suggestDto.getPage();
            Integer size = suggestDto.getSize();
            Integer type = suggestDto.getType();
            Integer status = suggestDto.getStatus();
            String title = suggestDto.getTitle();
            String empName = suggestDto.getEmpName();
            Integer empDeptId = suggestDto.getOrgId();
            Date startTime = null;
            Date endTime = null;
            if (ToolUtil.isNotEmpty(suggestDto.getStartTime())) {
                startTime = DateUtil.parse(DateUtil.format(suggestDto.getStartTime(),"yyyy-MM-dd")+ " 00:00:00","yyyy-MM-dd HH:mm:ss");
            }
            if (ToolUtil.isNotEmpty(suggestDto.getEndTime())) {
                endTime = DateUtil.parse(DateUtil.format(suggestDto.getEndTime(),"yyyy-MM-dd") + " 23:59:59","yyyy-MM-dd HH:mm:ss");
            }
            Integer isDeliver = suggestDto.getIsDeliver();
            Integer tabPage = null;
            if (ToolUtil.isNotEmpty(suggestDto.getTabPage())) {
                 tabPage = suggestDto.getTabPage();
            }
            //条件分页查询反馈
            List<ServerSuggestAndGiveLikeModel> suggestInfosList = serverSuggestService.selectSuggestAll(page, size, title, type, empDeptId,empName, startTime, endTime, status, isDeliver, tabPage, userId);
            if (ToolUtil.isNotEmpty(suggestInfosList)) {
                Pager pager = new Pager();
                pager.setCurrentPage(page);
                pager.setSize(size);
                //条件分页查询反馈总条数
                pager.setTotal(serverSuggestService.getTotal(title, type, empDeptId,empName, startTime, endTime, status, isDeliver, tabPage, userId));
                resultList.setCode(200);
                resultList.setMessage("查询成功");
                resultList.setData(suggestInfosList);
                resultList.setPager(pager);
            } else if (ToolUtil.isEmpty(suggestInfosList)) {
                resultList.setCode(200);
                resultList.setMessage("查询成功,但没有信息");
                resultList.setData(suggestInfosList);
            } else {
                resultList.setCode(500);
                resultList.setMessage("查询失败");
            }
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("查询失败");
            e.printStackTrace();
        }
        return resultList;

    }


    /**
     * @param id
     * @param request
     * @return
     */
    @ApiOperation(value = "咨询详情信息查询", notes = "咨询详情信息查询")
    @GetMapping("/selectSuggestDetails")
    @ResponseBody
    public ResultList selectSuggestDetails(@RequestParam Integer id, @RequestParam Integer tabPage, HttpServletRequest request) {
        ResultList resultList = new ResultList();
        try {

            String userid = SessionUtils.getUserId(request);
            OmUser user = omUserRepository.findByUserid(userid);
            String userId = user.getEmpid().toString();
            ServerSuggestAndGiveLikeModel serverSuggestAndGiveLikeModel = serverSuggestService.select(id, userId,tabPage);
            List<ServerDeliver> deliverList = serverDeliverService.findBySuggestId(id);
            if (ToolUtil.isNotEmpty(deliverList)){
                serverSuggestAndGiveLikeModel.setDeliverList(deliverList);
            }
            if (ToolUtil.isNotEmpty(serverSuggestAndGiveLikeModel)) {
                if (ToolUtil.isNotEmpty(serverSuggestAndGiveLikeModel.getReplyId())) {
                    serverSuggestAndGiveLikeModel.setReplyUserid(omUserRepository.findByEmpid(serverSuggestAndGiveLikeModel.getReplyId()).getUserid());
                }
                resultList.setCode(200);
                resultList.setMessage("查询成功");
                resultList.setData(serverSuggestAndGiveLikeModel);
            }else if (ToolUtil.isEmpty(serverSuggestAndGiveLikeModel)) {
                resultList.setCode(200);
                resultList.setMessage("查询成功,但没有信息");
                resultList.setData(serverSuggestAndGiveLikeModel);
            }
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("查询失败");
            e.printStackTrace();
        }

        return resultList;
    }

    @ApiOperation(value = "投诉详情信息查询", notes = "投诉详情信息查询")
    @GetMapping("/selectComplaintDetails")
    @ResponseBody
    public ResultList selectComplaintDetails(@RequestParam Integer id) {
        ResultList resultList = new ResultList();
        try {

            ServerSuggestModel serverSuggestModel = serverSuggestService.selectComplaint(id);
            List<ServerDeliver> deliverList = serverDeliverService.findBySuggestId(id);
            if (ToolUtil.isNotEmpty(deliverList)){
                serverSuggestModel.setDeliverList(deliverList);
            }
            if (ToolUtil.isNotEmpty(serverSuggestModel)) {
                if (ToolUtil.isNotEmpty(serverSuggestModel.getReplyId())) {
                    serverSuggestModel.setReplyUserid(omUserRepository.findByEmpid(serverSuggestModel.getReplyId()).getUserid());
                }
                resultList.setMessage("查询成功");
                resultList.setCode(200);
                resultList.setData(serverSuggestModel);
            }else if (ToolUtil.isEmpty(serverSuggestModel)) {
                resultList.setMessage("查询成功,但没有信息");
                resultList.setCode(200);
                resultList.setData(serverSuggestModel);
            }
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("查询失败");
        }

        return resultList;
    }


    @ApiOperation(value = "点赞更新", notes = "点赞更新")
    @PostMapping("/updateGaveLikeStatus")
    @ResponseBody
    public ResultList updateGaveLikeStatus(@RequestParam Integer id, HttpServletRequest request) {
        ResultList resultList = new ResultList();

        try {
            String userid = SessionUtils.getUserId(request);
            OmUser omUser = omUserRepository.findByUserid(userid);
            String userId = omUser.getEmpid().toString();
            serverSuggestService.update(id, userId);
            resultList.setCode(200);
            resultList.setMessage("点赞成功");

        } catch (Exception e) {
            e.printStackTrace();
            resultList.setCode(500);
            resultList.setMessage("点赞失败");
        }

        return resultList;
    }

    @ApiOperation(value = "新增咨询投诉信息", notes = "新增咨询投诉信息")
    @PostMapping("/addSuggest")
    @ResponseBody
    @NoRepeatSubmit
    public ResultList addSuggest(@RequestBody AddSuggestDto addSuggestDto, HttpServletRequest request) {
        ResultList resultList = new ResultList();
        //提交人id
        String userid = SessionUtils.getUserId(request);
        try {
            serverSuggestService.addSuggestAndComplain(addSuggestDto,userid);
           resultList.setCode(200);
            resultList.setMessage("提交成功");
        } catch (Exception e) {
            e.printStackTrace();
//            resultList.setCode(500);
//            resultList.setMessage("提交失败");
        }
        return resultList;
    }


    @ApiOperation(value = "处理咨询投诉", notes = "处理咨询投诉")
    @PostMapping("/updateSuggestInfo")
    @ResponseBody
    public ResultList updateSuggestInfo(@RequestBody ServerSuggestDeliver serverSuggestModel, HttpServletRequest request) {
        ResultList resultList = new ResultList();
        String userid = SessionUtils.getUserId(request);
        try {
            if (ToolUtil.isNotEmpty(serverSuggestModel.getReplyUserid())) {
                serverSuggestModel.setReplyId(omUserRepository.findByUserid(serverSuggestModel.getReplyUserid()).getEmpid());
            }

            ServerSuggest serverSuggest = serverSuggestService.updateSuggestInfo(serverSuggestModel,userid);
            if (ToolUtil.isNotEmpty(serverSuggest)){
                resultList.setCode(200);
                resultList.setMessage("提交成功");
            }else {
                resultList.setCode(500);
                resultList.setMessage("提交失败");
            }
        } catch (Exception e) {
            resultList.setCode(500);
//            e.printStackTrace();
            resultList.setMessage(e.getMessage());
        }
        return resultList;
    }

    @ApiOperation(value = "投诉条件分页查询", notes = "投诉条件分页查询")
    @PostMapping("/selectComplaintPage")
    @ResponseBody
    public ResultList selectComplaintPage(@RequestBody SuggestDto suggestDto, HttpServletRequest request) {
        ResultList resultList = new ResultList();
        try {

            String userid = SessionUtils.getUserId(request);
            OmUser user = omUserRepository.findByUserid(userid);
            String userId = user.getEmpid().toString();
            Integer page = suggestDto.getPage();
            Integer size = suggestDto.getSize();
            Integer type = suggestDto.getType();
            Integer status = suggestDto.getStatus();
            String title = suggestDto.getTitle();
            Integer empDeptId = suggestDto.getOrgId();
            String empName = suggestDto.getEmpName();
            Date startTime = null;
            Date endTime = null;
            if (ToolUtil.isNotEmpty(suggestDto.getStartTime())) {
                startTime = DateUtil.parse(DateUtil.format(suggestDto.getStartTime(),"yyyy-MM-dd") + " 00:00:00","yyyy-MM-dd HH:mm:ss");
            }
            if (ToolUtil.isNotEmpty(suggestDto.getEndTime())) {
                endTime = DateUtil.parse(DateUtil.format(suggestDto.getEndTime(),"yyyy-MM-dd") + " 23:59:59","yyyy-MM-dd HH:mm:ss");
            }
            Integer isDeliver = suggestDto.getIsDeliver();
            Integer tabPage = null;
            if (ToolUtil.isNotEmpty(suggestDto.getTabPage())){
                tabPage = suggestDto.getTabPage();
            }
            //条件分页查询反馈
            List<ServerSuggestModel> complaintInfosList = serverSuggestService.selectComplaintAll(page, size, title, type, empDeptId,empName, startTime, endTime, status, isDeliver, tabPage, userId);
            if (ToolUtil.isNotEmpty(complaintInfosList)) {
                Pager pager = new Pager();
                pager.setCurrentPage(page);
                pager.setSize(size);
                //条件分页查询反馈总条数
                pager.setTotal(serverSuggestService.getCountTotal(title, type, empDeptId,empName, startTime, endTime, status, isDeliver, tabPage, userId));
                resultList.setMessage("查询成功");
                resultList.setCode(200);
                resultList.setData(complaintInfosList);
                resultList.setPager(pager);
            } else if (ToolUtil.isEmpty(complaintInfosList)) {
                resultList.setMessage("查询成功,但没有信息");
                resultList.setCode(200);
                resultList.setData(complaintInfosList);
            } else {
                resultList.setCode(500);
                resultList.setMessage("查询失败");
            }
        } catch (Exception e) {
            resultList.setMessage("查询失败");
            resultList.setCode(500);
        }
        return resultList;

    }

}
