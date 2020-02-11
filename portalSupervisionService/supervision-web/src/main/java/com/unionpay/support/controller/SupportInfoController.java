package com.unionpay.support.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.unionpay.common.easyexcel.EasyExcelFactory;
import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.supervision.dao.OMOrganizationRepository;
import com.unionpay.supervision.dao.OMUserRepository;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.support.model.AppointmentDto;
import com.unionpay.support.model.AppointmentModel;
import com.unionpay.support.model.ExcelSupportInfo;
import com.unionpay.support.model.SupportPage;
import com.unionpay.support.pojo.SupportInfo;
import com.unionpay.support.service.SupportInfoService;
import com.unionpay.support.utils.ResultList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */
@RestController
@RequestMapping("/supportInfo")
@Api(value = "/supportInfo",tags = "办公支持预约服务功能",description = "办公支持预约服务功能")
public class SupportInfoController {

    @Autowired
    private SupportInfoService supportInfoService;
    @Autowired
    private OMUserRepository omUserRepository;
    @Autowired
    private OMOrganizationRepository omOrganizationRepository;
    @Autowired
    private OmUserService omUserService;

    @ApiOperation(value = "预约处理列表分页查询", notes = "预约处理列表分页查询")
    @PostMapping("/selectAppointmentPage")
    @ResponseBody
    public ResultList selectInfoPage(@RequestBody AppointmentModel appointmentModel, HttpServletRequest request) {
        ResultList resultList = new ResultList();
        try {

            String userid = SessionUtils.getUserId(request);
            OmUser user = omUserRepository.findByUserid(userid);
            String userId = user.getEmpid().toString();
            Integer page = appointmentModel.getPage();
            Integer size = appointmentModel.getSize();
            Integer status = appointmentModel.getStatus();
            String appointmentName = appointmentModel.getAppointmentName();
            Integer serverUserId = appointmentModel.getServerUserId();
            String serverUserName = appointmentModel.getServerUserName();
            String timeStart = appointmentModel.getTimeStart();
            String timeEnd = appointmentModel.getTimeEnd();
            Integer tabPage = appointmentModel.getTabPage();
            //条件分页查询反馈
            List<SupportPage> suggestInfosList = supportInfoService.selectSupportInfo(page, size, appointmentName, serverUserId, serverUserName,timeStart, timeEnd, status, userId,tabPage);
            if (ToolUtil.isNotEmpty(suggestInfosList)) {
                Pager pager = new Pager();
                pager.setCurrentPage(page);
                pager.setSize(size);
                //条件分页查询反馈总条数
                pager.setTotal(supportInfoService.selectCount(appointmentName, serverUserId, serverUserName,timeStart, timeEnd, status, userId,tabPage));
                resultList.setCode(200);
                resultList.setMessage("查询成功");
                resultList.setData(suggestInfosList);
                resultList.setPager(pager);
            } else if (ToolUtil.isEmpty(suggestInfosList)) {
                resultList.setCode(200);
                resultList.setMessage("查询成功,但没有信息");
                resultList.setData(suggestInfosList);
            }
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("查询失败");
            e.printStackTrace();
        }
        return resultList;

    }


    @ApiOperation(value = "查询预约详情", notes = "查询预约详情")
    @PostMapping("/findOne")
    @ResponseBody
    public ResultList findOne(@RequestParam Integer id) {
        ResultList resultList = new ResultList();

        try {
            SupportPage info = supportInfoService.selectAll(id);
            resultList.setCode(200);
            resultList.setMessage("查询成功");
            resultList.setData(info);
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("查询失败");
        }

        return resultList;
    }

    @ApiOperation(value = "发起预约时自动显示登录人姓名和联系方式",notes = "发起预约时自动显示登录人姓名和联系方式")
    @GetMapping("/getUserId")
    @ResponseBody
    public ResultList getUserId(HttpServletRequest request){
        ResultList resultList = new ResultList();
        String userId = SessionUtils.getUserId(request);
        OmUser omUser = omUserRepository.findByUserid(userId);
        if (ToolUtil.isEmpty(omUser)){
            resultList.setCode(100);
            resultList.setMessage("未登录");
            return resultList;
        }
        SupportInfo info = new SupportInfo();
        info.setAppointmentName(omUser.getEmpname());
        info.setAppointmentConnection(omUser.getOtel());
        if (ToolUtil.isNotEmpty(info)) {
            resultList.setCode(200);
            resultList.setMessage("查询成功");
            resultList.setData(info);
        }else {
            resultList.setCode(500);
            resultList.setMessage("查询失败");
        }

        return resultList;
    }

    @ApiOperation(value = "保存预约信息", notes = "保存预约信息")
    @PostMapping("/saveAppointment")
    @ResponseBody
    public ResultList addSuggest(@RequestBody AppointmentDto appointmentDto, HttpServletRequest request) {
        ResultList resultList = new ResultList();
        String userId = SessionUtils.getUserId(request);
        OmUser omUser = omUserRepository.findByUserid(userId);

        try {
            supportInfoService.addAppointment(appointmentDto,omUser);
            resultList.setCode(200);
            resultList.setMessage("保存成功");
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("保存失败");
//            e.printStackTrace();
        }
        return resultList;
    }


    @ApiOperation(value = "预约人撤销预约", notes = "预约人撤销预约")
    @GetMapping("/deleteAppointment")
    @ResponseBody
    public ResultList updateStatus(@RequestParam Integer id) {
        ResultList resultList = new ResultList();

        try {

            SupportInfo supportInfo = supportInfoService.updateStatus(id);
            if (ToolUtil.isNotEmpty(supportInfo)) {
                resultList.setCode(200);
                resultList.setMessage("撤销成功");
            }
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("撤销失败");
        }

        return resultList;
    }


    @ApiOperation(value = "预约人评价", notes = "预约人评价")
    @PostMapping("/evaluateAppointment")
    @ResponseBody
    public ResultList update(@RequestBody  SupportInfo supportInfo) {
        ResultList resultList = new ResultList();

        try {
            SupportInfo supportInfo1 = supportInfoService.updateEvaluate(supportInfo);
            if (ToolUtil.isNotEmpty(supportInfo1)) {
                resultList.setCode(200);
                resultList.setMessage("评价成功");
            }
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("评价失败");
            e.printStackTrace();
        }

        return resultList;
    }


    @ApiOperation(value = "接受预约", notes = "接受预约")
    @PostMapping("/acceptAppointment")
    @ResponseBody
    public ResultList acceptAppointment(@RequestBody  SupportInfo supportInfo,HttpServletRequest request) {
        ResultList resultList = new ResultList();
        try {
            //受理人id
            String userId = SessionUtils.getUserId(request);
            OmUser omUser = omUserRepository.findByUserid(userId);
            supportInfoService.add(supportInfo,omUser);
            resultList.setCode(200);
            resultList.setMessage("操作成功");
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("操作失败");
        }

        return resultList;
    }


    @ApiOperation(value = "完成预约", notes = "完成预约")
    @GetMapping("/completeAppointment")
    @ResponseBody
    public ResultList completeAppointment(@RequestParam Integer id) {
        ResultList resultList = new ResultList();

        try {
            supportInfoService.update(id);
            resultList.setCode(200);
            resultList.setMessage("服务成功");
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("服务失败");
        }

        return resultList;
    }

    @ApiOperation(value = "结束预约", notes = "结束预约")
    @PostMapping("/overAppointment")
    @ResponseBody
    public ResultList overAppointment(@RequestBody SupportInfo supportInfo,HttpServletRequest request) {
        ResultList resultList = new ResultList();

        try {
            String userId = SessionUtils.getUserId(request);
            OmUser omUser = omUserRepository.findByUserid(userId);
            SupportInfo supportInfo1 = supportInfoService.overAppointment(supportInfo,omUser);
            if (ToolUtil.isNotEmpty(supportInfo1)) {
                resultList.setCode(200);
                resultList.setMessage("撤销成功");
            }
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("撤销失败");
        }

        return resultList;
    }


    @ApiOperation(value = "导出处理列表",notes = "导出处理列表")
    @GetMapping("/excelSupportInfo")
    public void excelSupportInfo( AppointmentModel appointmentModel,String ids, HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream out = null;
        String userid = SessionUtils.getUserId(request);
        OmUser omUser = omUserService.findByUserid(userid);
        if (ToolUtil.isEmpty(omUser)){
            throw new BussinessException(100,"请登录");
        }
        if (ToolUtil.isEmpty(appointmentModel)){
            throw new BussinessException(500,"参数为空");
        }
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String timeStart = appointmentModel.getTimeStart();
        String timeEnd = appointmentModel.getTimeEnd();
        Integer status = appointmentModel.getStatus();
        Integer serverUserId = appointmentModel.getServerUserId();
        String serverUserName = appointmentModel.getServerUserName();
        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
        try {
            String fileName = new String(("处理预约列表" + DateUtil.getDay(new Date())));
            Sheet sheet = new Sheet(1,0,ExcelSupportInfo.class);
            sheet.setSheetName("处理预约列表");
            List<ExcelSupportInfo> list = supportInfoService.selectInfo(timeStart,timeEnd,status,serverUserId,serverUserName,ids);
            writer.write(list, sheet);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            fileName = getFileName(request, fileName);
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            writer.finish();
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileName(HttpServletRequest request, String fileName) {
        try {
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                fileName = new String(fileName.getBytes(), "iso-8859-1");// firefox浏览器
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

}



