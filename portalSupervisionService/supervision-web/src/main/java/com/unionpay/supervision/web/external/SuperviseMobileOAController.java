package com.unionpay.supervision.web.external;

import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.bussniss.FeedBackOperator;
import com.unionpay.supervision.dao.*;
import com.unionpay.supervision.domain.*;
import com.unionpay.supervision.model.*;
import com.unionpay.supervision.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.unionpay.supervision.supportController.BaseController.verify;

@Api(value = "SuperviseMobileOAController", description = "督办系统与移动OA对接的接口")
@RestController
@RequestMapping("/mobileOaQuery")
public class SuperviseMobileOAController {

    @Autowired
    private SuperSponsorService superSponsorService;
    @Autowired
    private SuperClientService superClientService;
    @Autowired
    private SuperClientRepository superClientRepository;
    @Autowired
    private SuperServiceRepository superServiceRepository;
    @Autowired
    private SuperSponsorRepository superSponsorRepository;
    @Autowired
    private SuperServiceOverseeMappingRepository superServiceOverseeMappingRepository;
    @Autowired
    private SuperSponsorLogRepository superSponsorLogRepository;


//    @ApiOperation(value = "签名生成")
//    @GetMapping("/getSign")
//    public RESTResultBean getSign(String appKey, HttpServletRequest request) {
//        RESTResultBean result = new RESTResultBean(200, "success");
//        SuperClient superClient = superClientRepository.findByAppKey(appKey);
//        String appSecret = superClient.getAppSecret();
//        String requestTime = String.valueOf(superClient.getCreateTime().getTime());
//        String sign = FeedBackOperator.getSign(appKey, appSecret, requestTime);
//        ArrayList<Object> list = new ArrayList<>();
//        list.add(sign);
//        list.add(requestTime);
//        result.setData(list);
//
//        return result;
//    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取督办列表信息
     */
    @ApiOperation(value = "获取督办列表信息")
    @PostMapping("/getPage")
    @ResponseBody
    public RESTResultBean getPage(@RequestHeader("appKey") String appKey,
                                  @RequestHeader("requestTime") String requestTime, @RequestHeader("sign") String sign,
                                  HttpServletRequest request, @Valid @RequestBody SupervisePageModel supervisePageModel, BindingResult bindingResult) {
        RESTResultBean result = new RESTResultBean(200, "success");
        verify(bindingResult, request, supervisePageModel);
        // 时间戳验证
        if (!FeedBackOperator.isTrueTime(requestTime)) {
            result.setCode(500);
            result.setMessage("时间戳失效");
            return result;
        }
        SuperClient client = superClientService.findByAppkey(appKey);
        if (client == null) {
            result.setCode(500);
            result.setMessage("appKey is error");
            return result;
        }
        String appSecret = client.getAppSecret();
        // 签名验证
        if (!sign.equals(FeedBackOperator.getSign(appKey, appSecret, requestTime))) {
            result.setCode(500);
            result.setMessage("签名错误");
            return result;
        }

//        logger.info("++++++++++++++++++++++++++++++supervisePageModel："+supervisePageModel);
        String type = supervisePageModel.getType();
        String deptId1 = supervisePageModel.getDeptId();
        Integer deptId = null;
        if (ToolUtil.isNotEmpty(deptId1)){
            deptId = Integer.parseInt(deptId1);
        }
        String createTimeStart = supervisePageModel.getCreateTimeStart();
        String createTimeEnd = supervisePageModel.getCreateTimeEnd();
        Integer size = supervisePageModel.getSize();
        Integer page = supervisePageModel.getCurrentPage();
        Pager pager = new Pager();
        pager.setCurrentPage(page);
        pager.setSize(size);
        List<SuperSponserPage> list = superSponsorService.selectPage(type,deptId, createTimeStart, createTimeEnd, page, size);
        pager.setTotal(superSponsorService.getCount(type,deptId, createTimeStart, createTimeEnd));
        result.setPager(pager);
        result.setData(list);
        return result;
    }

    /**
     * 获取督办详情信息
     */
    @ApiOperation(value = "获取督办详情信息")
    @PostMapping("/getInfo")
    @ResponseBody
    public RESTResultBean getInfo(@RequestHeader("appKey") String appKey, @RequestHeader("requestTime") String requestTime, @RequestHeader("sign") String sign, @Valid @RequestBody MobileOaInfo mobileOaInfo) {
        RESTResultBean result = new RESTResultBean(200, "success");
//        verify(bindingResult, request, mobileOaInfo);
        // 时间戳验证
        if (!FeedBackOperator.isTrueTime(requestTime)) {
            result.setCode(500);
            result.setMessage("时间戳失效");
            return result;
        }
        SuperClient client = superClientService.findByAppkey(appKey);
        if (client == null) {
            result.setCode(500);
            result.setMessage("appKey is error");
            return result;
        }
        String appSecret = client.getAppSecret();
        // 签名验证
        if (!sign.equals(FeedBackOperator.getSign(appKey, appSecret, requestTime))) {
            result.setCode(500);
            result.setMessage("签名错误");
            return result;
        }

        try {

            String unid = mobileOaInfo.getUnid();
            SuperSponsor sponsor = superSponsorService.findById(unid);
            SuperService superService = superServiceRepository.findByUnid(sponsor.getServiceUnid());
            List<SuperSponsor> superSponsorList = superSponsorRepository.findByServiceUnid(sponsor.getServiceUnid());
            String orgNames = superSponsorRepository.findSuperServiceByUnid(sponsor.getServiceUnid());
//            String orgNames = superSponsorService.findByUnid(sponsor.getServiceUnid());

            //督办事项类型
            List<OverseeTypeList> overseeTypeLists = new ArrayList<>();
            List<SuperServiceOverseeMapping> superServiceOverseeMappingList = superServiceOverseeMappingRepository.findByServiceUnidOrderByIsPrimaryDesc(superService.getUnid());
            for (SuperServiceOverseeMapping superServiceOverseeMapping : superServiceOverseeMappingList) {
                OverseeTypeList overseeTypeList = new OverseeTypeList();
                overseeTypeList.setOverseeUnid(superServiceOverseeMapping.getUnid());
                overseeTypeList.setOverseeName(superServiceOverseeMapping.getOverseeName());
                overseeTypeList.setServiceName(superServiceOverseeMapping.getServiceName());
                overseeTypeList.setCommandSource(superServiceOverseeMapping.getCommandSource());
                overseeTypeList.setCommandLeader(superServiceOverseeMapping.getCommandLeader());
                overseeTypeList.setTaskNote(superServiceOverseeMapping.getTaskNote());
                if (ToolUtil.isNotEmpty(superServiceOverseeMapping.getServiceTime())) {
                    overseeTypeList.setServiceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(superServiceOverseeMapping.getServiceTime())));
                }
//                overseeTypeList.setServiceTime(superServiceOverseeMapping.getServiceTime());
                overseeTypeList.setIsPrimary(superServiceOverseeMapping.getIsPrimary());
                overseeTypeLists.add(overseeTypeList);
            }

            List<SponsorList> sponsorLists = new ArrayList<>();
            for (SuperSponsor superSponsor : superSponsorList) {
                //督办日志
                List<SponsorLogList> sponsorLogLists = new ArrayList<>();
                List<SuperSponsorLog> superSponsorLogList = superSponsorLogRepository.findBySponsorUnidOrderByCreateTimeDesc(superSponsor.getUnid());
                for (SuperSponsorLog superSponsorLog : superSponsorLogList) {
                    SponsorLogList sponsorLogList = new SponsorLogList();
                    sponsorLogList.setUnid(superSponsorLog.getUnid());
                    sponsorLogList.setFeedbackRule(superSponsorLog.getFeedbackRule());
                    if (ToolUtil.isNotEmpty(superSponsorLog.getFeedbackDeadline())) {
                        sponsorLogList.setFeedbackDeadline(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(superSponsorLog.getFeedbackDeadline())));
                    }
//                    sponsorLogList.setFeedbackDeadline(superSponsorLog.getFeedbackDeadline());
                    sponsorLogList.setSponsorId(superSponsorLog.getSponsorId());
                    sponsorLogList.setSponsorName(superSponsorLog.getSponsorName());
                    sponsorLogList.setFeedbackUserid(superSponsorLog.getFeedbackUserid());
                    sponsorLogList.setFeedbackUsername(superSponsorLog.getFeedbackUsername());
                    if (ToolUtil.isNotEmpty(superSponsorLog.getFeedbackTime())) {
                        sponsorLogList.setFeedbackTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(superSponsorLog.getFeedbackTime())));
                    }
//                    sponsorLogList.setFeedbackTime(superSponsorLog.getFeedbackTime());
                    sponsorLogList.setWorkStatus(superSponsorLog.getWorkStatus());
                    sponsorLogList.setProgress(superSponsorLog.getProgress());
                    sponsorLogList.setNote(superSponsorLog.getNote());
                    sponsorLogLists.add(sponsorLogList);
                }
                //督办部门详情

                SponsorList sponsorList = new SponsorList();
                sponsorList.setUnid(superSponsor.getUnid());
                sponsorList.setOrgId(superSponsor.getOrgId());
                sponsorList.setOrgName(superSponsor.getOrgName());
                sponsorList.setIsRead(superSponsor.getIsRead());
                sponsorList.setResultForm(superSponsor.getResultForm());
                sponsorList.setWorkPlan(superSponsor.getWorkPlan());
                if (ToolUtil.isNotEmpty(superSponsor.getProposedClosingTime())) {
                    sponsorList.setProposedClosingTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(superSponsor.getProposedClosingTime())));
                }
//                sponsorList.setProposedClosingTime(superSponsor.getProposedClosingTime());
                sponsorList.setSponsorLogList(sponsorLogLists);
                sponsorLists.add(sponsorList);
            }
            //督办事项详情
            SuperSponserDetail sponserDetail = new SuperSponserDetail();
            sponserDetail.setUnid(unid);
            sponserDetail.setServiceUnid(superService.getUnid());
            sponserDetail.setServiceType(superService.getServiceType());
            sponserDetail.setBranchedLeader(superService.getBranchedLeader());
            if (ToolUtil.isNotEmpty(superService.getCreateTime())) {
                sponserDetail.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(superService.getCreateTime())));
            }
//            sponserDetail.setCreateTime(superService.getCreateTime());
            sponserDetail.setCreateUserid(superService.getCreateUserid());
            sponserDetail.setCreateUsername(superService.getCreateUsername());
            sponserDetail.setOverseeUserid(superService.getOverseeUserid());
            sponserDetail.setOverseeUsername(superService.getOverseeUsername());
            sponserDetail.setOverseeFrequency(superService.getOverseeFrequency());
            sponserDetail.setOrgNames(orgNames);
            sponserDetail.setOverseeTypeList(overseeTypeLists);
            sponserDetail.setSponsorList(sponsorLists);

            result.setData(sponserDetail);

        }catch (Exception e){
             e.printStackTrace();
        }
        return result;
    }




}
