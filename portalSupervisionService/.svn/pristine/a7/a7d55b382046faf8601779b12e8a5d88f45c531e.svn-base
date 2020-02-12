package com.unionpay.services.controller;

import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.FrontPageRequest;
import com.unionpay.services.model.ServerFrontDeptAndNum;
import com.unionpay.services.model.ServerSearchRecode;
import com.unionpay.services.model.ServiceInfoModelDto;
import com.unionpay.services.repository.ServerHostWordsRepository;
import com.unionpay.services.repository.ServerSearchRecodeRepository;
import com.unionpay.services.repository.ServerinfoRepository;
import com.unionpay.services.service.ServerHostWordsService;
import com.unionpay.services.service.ServerInfoService;
import com.unionpay.services.util.Result;
import com.unionpay.services.util.ResultList;
import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.dao.OMUserRepository;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.services.model.OnlineTotal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/18/018 18:40
 * @Description:
 */
@RestController
@RequestMapping("/serverfrontpage")
@Api(value = "/serverfrontpage", tags = "前端页信息接口", description = "前端页信息接口")
public class ServerFrontPageController {

    @Autowired
    private ServerHostWordsRepository serverHostWordsRepository;
    @Autowired
    private OMOrganizationRepository omOrganizationRepository;
    @Autowired
    private ServerInfoService serverInfoService;
    @Autowired
    private ServerSearchRecodeRepository serverSearchRecodeRepository;
    @Autowired
    private OMUserRepository omUserRepository;
    @Autowired
    private ServerInfoService serverinfoService;
    @Autowired
    private ServerHostWordsService serverHostWordsService;
    @Autowired
    private ServerinfoRepository serverinfoRepository;



    /**
     * ServerFrontPage-分页查询基本信息
     * @return
     */
    @ApiOperation(value="ServerFrontPage-分页查询基本信息", notes="分页查询基本信息")
    @PostMapping("/ServerFrontPage")
    @ResponseBody
    public ResultList ServerFrontPage(@Valid @RequestBody FrontPageRequest frontPageRequest, HttpServletRequest request){
        ResultList resultList = new ResultList();
//        try{
             Integer page = frontPageRequest.getPage();
             Integer size = frontPageRequest.getSize();
             Integer orgId = frontPageRequest.getOrgId();
             String keyword = frontPageRequest.getKeyword();
             Integer onlineProcessing = frontPageRequest.getOnlineProcessing();
            if(ToolUtil.isNotEmpty(keyword)){
                 String userId = SessionUtils.getUserId(request);
                 OmUser omUser = omUserRepository.findByUserid(userId);
                 ServerSearchRecode serverSearchRecode = new ServerSearchRecode();
                 serverSearchRecode.setText(keyword);
                 serverSearchRecode.setCreatetime(new Date());
                 serverSearchRecode.setEmp_id(omUser.getEmpid());
                 serverSearchRecodeRepository.save(serverSearchRecode);
             }
            List<ServiceInfoModelDto> serviceInfos = serverInfoService.ServerFrontPage(page,size,orgId,keyword,onlineProcessing);
        OnlineTotal onlineTotal = serverinfoService.selectOnlineTotal(orgId,keyword);
            ArrayList<Object> list = new ArrayList<>();
            list.add(serviceInfos);
            list.add(onlineTotal);
            if(ToolUtil.isNotEmpty(serviceInfos)){
                Pager pager = new Pager();
                pager.setCurrentPage(page);
                pager.setSize(size);
                pager.setTotal(serverInfoService.getCount(page,size,orgId,keyword,onlineProcessing));
                resultList.setCode(200);
                resultList.setMessage("查询成功");
                resultList.setData(list);
                resultList.setPager(pager);
            }else if (ToolUtil.isEmpty(serviceInfos)) {
                resultList.setCode(200);
                resultList.setMessage("查询成功，没有信息");
                resultList.setData(serviceInfos);
//                return Result.success(serviceInfos,pager);
//            }else if (ToolUtil.isEmpty(serviceInfos)){
//                return Result.build(200,"查询成功，没有信息",serviceInfos);
//            }else {
//                return Result.failure();
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            return Result.failure(e.toString());
            }
        return resultList;
    }

    @PostMapping("/selcetHostWords")
    @ApiOperation(value = "展示热词", notes = "展示热词")
    @ResponseBody
    public Result selcetHostWords() {
        try {
            List serverHostWords = serverHostWordsService.selectHostWords();
            if (ToolUtil.isNotEmpty(serverHostWords)) {
                return Result.build(200, "查询成功", serverHostWords);
            } else {
                return Result.build(400, "查询失败", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    @PostMapping("/addSearchRecode")
    @ApiOperation(value = "添加检索记录", notes = "添加检索记录")
    @ResponseBody
    public Result addSearchRecode(@Valid @RequestBody FrontPageRequest frontPageRequest, HttpServletRequest request) {
        try {
            String userId = SessionUtils.getUserId(request);
            OmUser omUser = omUserRepository.findByUserid(userId);
            ServerSearchRecode serverSearchRecode = new ServerSearchRecode();
            serverSearchRecode.setText(frontPageRequest.getText());
            serverSearchRecode.setCreatetime(new Date());
            serverSearchRecode.setEmp_id(omUser.getEmpid());
            ServerSearchRecode serverSearchRecode1 = serverSearchRecodeRepository.save(serverSearchRecode);
            if (ToolUtil.isNotEmpty(serverSearchRecode1)) {
                return Result.build(200, "添加成功", serverSearchRecode1);
            } else {
                return Result.build(400, "添加失败", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }


    @PostMapping("/selcetDeptAndNumber")
    @ApiOperation(value = "每个部门各有几个服务事件", notes = "每个部门各有几个服务事件")
    public Result selcetDeptAndNumber() {
        try {
            //查询每个部门有多少个办理事件
            List<ServerFrontDeptAndNum> serverFrontDeptAndNums = serverinfoService.listFront();
            if (ToolUtil.isNotEmpty(serverFrontDeptAndNums)) {
                return Result.build(200, "查询成功", serverFrontDeptAndNums);
            } else {
                return Result.build(400, "查询失败", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }


}
