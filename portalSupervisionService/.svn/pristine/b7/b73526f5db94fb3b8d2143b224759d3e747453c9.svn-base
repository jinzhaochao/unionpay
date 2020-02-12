package com.unionpay.services.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.HostRequest;
import com.unionpay.services.model.ServerHostWords;
import com.unionpay.services.model.SeverHost;
import com.unionpay.services.repository.ServerHostWordsRepository;
import com.unionpay.services.service.ServerHostWordsServiceImpl;
import com.unionpay.services.util.Result;
import com.unionpay.supervision.dao.OMUserRepository;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.filter.submit.NoRepeatSubmit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/20/020 15:48
 * @Description:
 */
@RestController
@RequestMapping("/serverHostWords")
@Api(value = "/serverHostWords", tags = "热词管理", description = "热词管理")
public class ServerHostWordsController {

    @Autowired
    private ServerHostWordsServiceImpl serverHostWordsService;
    @Autowired
    private ServerHostWordsRepository serverHostWordsRepository;
    @Autowired
    private OMUserRepository omUserRepository;

    /**
     * 热词管理查询与分页
     *
     * @return
     */
    @ApiOperation(value = "热词管理查询与分页", notes = "热词管理查询与分页")
    @PostMapping("/SelectAllHostWords")
    @ResponseBody
    public Result SelectAllHostWords(@Valid @RequestBody HostRequest hostRequest) {
        try {
            Integer page = hostRequest.getPage();
            Integer size = hostRequest.getSize();
            String words = hostRequest.getWords();
            Integer status = hostRequest.getStatus();
            List<SeverHost> serviceInfos = serverHostWordsService.SelectAllHostWords(page, size, words, status);
            if(ToolUtil.isNotEmpty(serviceInfos)){
                Pager pager = new Pager();
                pager.setCurrentPage(page);
                pager.setSize(size);
                pager.setTotal(serverHostWordsService.getCount(page,size,words,status));
                return Result.success(serviceInfos,pager);
            }else if (ToolUtil.isEmpty(serviceInfos)){
                return Result.build(200,"查询成功，没有信息",serviceInfos);
            }else {
                return Result.failure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 添加热词，删除热词（物理删除）
     *
     * @param hostRequest
     * @param request
     * @return
     */
    @ApiOperation(value = "添加热词，删除热词", notes = "添加热词，删除热词")
    @PostMapping("/updateHosts")
    @ResponseBody
    @NoRepeatSubmit
    public Result updateHosts(@Valid @RequestBody HostRequest hostRequest, HttpServletRequest request) {
        try {
            List<SeverHost> list = hostRequest.getSeverHosts();
            if (ToolUtil.isNotEmpty(list)) {
                if (hostRequest.getType() == 1) {//把非热词该为热词
                    for (SeverHost severHost : list) {
                        ServerHostWords severHost1 = serverHostWordsRepository.findByWords(severHost.getText());
                        if (ToolUtil.isNotEmpty(severHost1)) {
                            serverHostWordsService.deleteByWords(severHost1.getWords());
                        }
                    }
                    for (SeverHost severHost : list) {
                        String userId = SessionUtils.getUserId(request);
                        OmUser omUser = omUserRepository.findByUserid(userId);
                        ServerHostWords serverHostWords = new ServerHostWords();
                        serverHostWords.setId(0);
                        serverHostWords.setWords(severHost.getText());
                        serverHostWords.setStatus(1);
                        serverHostWords.setFrequency(severHost.getcC().intValue());
                        serverHostWords.setCreatetime(new Date());
                        serverHostWords.setEmp_id(omUser.getEmpid());
                        serverHostWords.setSort(1);
                        serverHostWordsRepository.save(serverHostWords);
                    }
                }
                if (hostRequest.getType() == 0) {//把热词改为非热词
                    for (SeverHost severHost : list) {
                        ServerHostWords severHost1 = serverHostWordsRepository.findByWords(severHost.getText());
                        if (ToolUtil.isNotEmpty(severHost1)) {
                            serverHostWordsService.deleteByWords(severHost.getText());
                        }
                    }
                }
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }


}
