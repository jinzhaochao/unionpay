package com.unionpay.services.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.common.easyexcel.EasyExcelFactory;
import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.*;
import com.unionpay.services.service.ServerBehaviorService;
import com.unionpay.services.service.ServerHostWordsService;
import com.unionpay.services.service.ServerInfoService;
import com.unionpay.services.service.ServerSuggestService;
import com.unionpay.supervision.domain.OmUser;
import com.unionpay.supervision.service.OmUserService;
import com.unionpay.supervision.web.major.ExcelController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "导出数据接口",value = "导出数据接口",description = "服务管理页数据导出")
@CrossOrigin(maxAge = 3600)
@RequestMapping("/export")
public class ExportExcelController {
    @Autowired
    private OmUserService omUserService;
    @Autowired
    private ServerBehaviorService behaviorService;
    @Autowired
    private ExcelController excelController;

    @Autowired
    private ServerHostWordsService serverHostWordsService;
    @Autowired
    private ServerInfoService serverInfoService;
    @Autowired
    private ServerSuggestService serverSuggestService;

//    @ApiOperation(value = "导出服务管理",notes = "导出服务管理")
//    @ResponseBody
//    @PostMapping("/exportServerInfo")
//    public void exportServerInfo(@RequestBody String param,HttpServletRequest request, HttpServletResponse response){
//        ServletOutputStream out = null;
//        String userid = SessionUtils.getUserId(request);
//        OmUser omUser = omUserService.findByUserid(userid);
//        if (ToolUtil.isEmpty(omUser)){
//            throw new BussinessException(100,"请登录");
//        }
//        if (ToolUtil.isEmpty(param)){
//            throw new BussinessException(500,"参数为空");
//        }
//        try {
//            out = response.getOutputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
//        JSONObject object = JSONObject.parseObject(param);
//        List<ServerModelDto> list = null;
//        if (ToolUtil.isNotEmpty(object)) {
//            Integer type = object.getInteger("type");
//            String[] ids = object.getString("ids").split(",");
//            if (ToolUtil.isNotEmpty(ids)) {
//                list = serverinfoRepository.findServerByIdIn(Arrays.asList(ids));
////                for (String s:ids){
////                    SuggestModel suggestModel = serverSuggestService.getOne(Integer.parseInt(s));
////                    list.add(suggestModel);
////                }
//            }
//        }
//        try {
//            String fileName = new String(("服务管理" + DateUtil.getDay(new Date())));
//            Sheet sheet = new Sheet(1,0,ServerModelDto.class);
//            sheet.setSheetName("服务管理");
//            writer.write(list, sheet);
//            response.setContentType("multipart/form-data");
//            response.setCharacterEncoding("utf-8");
//            fileName = excelController.getFileName(request, fileName);
//            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            writer.finish();
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @ApiOperation(value = "导出服务咨询",notes = "导出服务咨询")
//    @ResponseBody
//    @PostMapping("/exportServerSuggest")
//    public void exportServerSuggest(@RequestBody SuggestDto suggestDto,HttpServletRequest request, HttpServletResponse response){
//        ServletOutputStream out = null;
//        String userid = SessionUtils.getUserId(request);
//        OmUser omUser = omUserService.findByUserid(userid);
//        if (ToolUtil.isEmpty(omUser)){
//            throw new BussinessException(100,"请登录");
//        }
//        if (ToolUtil.isEmpty(suggestDto)){
//            throw new BussinessException(500,"参数为空");
//        }
//        try {
//            out = response.getOutputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
//        JSONObject object = JSONObject.parseObject(param);
//        List<SuggestModel> list = null;
//        if (ToolUtil.isNotEmpty(object)){
//            Integer type = object.getInteger("type");
//            String[] ids = object.getString("ids").split(",");
//            if (ToolUtil.isNotEmpty(ids)){
//                list = serverSuggestRepository.findByIdIn(Arrays.asList(ids));
////                for (String s:ids){
////                    SuggestModel suggestModel = serverSuggestService.getOne(Integer.parseInt(s));
////                    list.add(suggestModel);
////                }
//            }else {
////                list = serverSuggestService.getAll(type,1,10000);
//            }
//        }else {
////            list = serverSuggestService.getAll(null,1,10000);
//        }
//        try {
//            String fileName = new String(("服务咨询" + DateUtil.getDay(new Date())));
//            Sheet sheet = new Sheet(1,0,SuggestModel.class);
//            sheet.setSheetName("服务咨询");
//            writer.write(list, sheet);
//            response.setContentType("multipart/form-data");
//            response.setCharacterEncoding("utf-8");
//            fileName = excelController.getFileName(request, fileName);
//            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            writer.finish();
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @ApiOperation(value = "导出服务投诉",notes = "导出服务投诉")
//    @GetMapping("/exportServerComplaint")
//    public void exportServerComplaint(@RequestBody String param,HttpServletRequest request, HttpServletResponse response){
//        ServletOutputStream out = null;
//        String userid = SessionUtils.getUserId(request);
//        OmUser omUser = omUserService.findByUserid(userid);
//        if (ToolUtil.isEmpty(omUser)){
//            throw new BussinessException(100,"请登录");
//        }
//        if (ToolUtil.isEmpty(param)){
//            throw new BussinessException(500,"参数为空");
//        }
//        try {
//            out = response.getOutputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
//        JSONObject object = JSONObject.parseObject(param);
//        List<ComplaintModel> list = null;
//        if (ToolUtil.isNotEmpty(object)){
//            Integer type = object.getInteger("type");
//            String[] ids = object.getString("ids").split(",");
//            if (ToolUtil.isNotEmpty(ids)){
//                list = new ArrayList<>();
//                for (String s:ids){
//                    ComplaintModel complaintModel = serverSuggestRepository.getComplaintById(Integer.parseInt(s));
//                    if (complaintModel.getIsAnonymous() == 1){
//                        complaintModel.setEmpName("匿名");
//                    }
//                    list.add(complaintModel);
//                }
//            }
////            else {
////                list = serverSuggestService.getAll(type,1,10000);
////            }
////        }else {
////            list = serverSuggestService.getAll(null,1,10000);
//        }
//        try {
//            String fileName = new String(("服务投诉" + DateUtil.getDay(new Date())));
//            Sheet sheet = new Sheet(1,0,ComplaintModel.class);
//            sheet.setSheetName("服务投诉");
//            writer.write(list, sheet);
//            response.setContentType("multipart/form-data");
//            response.setCharacterEncoding("utf-8");
//            fileName = excelController.getFileName(request, fileName);
//            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            writer.finish();
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @ApiOperation(value = "导出热词管理",notes = "导出热词管理")
//    @GetMapping("/exportHostWords")
//    public void exportHostWords(@RequestBody String param,HttpServletRequest request, HttpServletResponse response){
//        ServletOutputStream out = null;
//        String userid = SessionUtils.getUserId(request);
//        OmUser omUser = omUserService.findByUserid(userid);
//        if (ToolUtil.isEmpty(omUser)){
//            throw new BussinessException(100,"请登录");
//        }
//        if (ToolUtil.isEmpty(param)){
//            throw new BussinessException(500,"参数为空");
//        }
//        try {
//            out = response.getOutputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
//        JSONObject object = JSONObject.parseObject(param);
//        List<HostWordsModel> list = null;
//        if (ToolUtil.isNotEmpty(object)){
//            Integer type = object.getInteger("type");
//            String[] ids = object.getString("ids").split(",");
//            if (ToolUtil.isNotEmpty(ids)){
//                list = serverHostWordsRepository.findByIdIn(Arrays.asList(ids));
////                for (String s:ids){
////                    SuggestModel suggestModel = serverSuggestService.getOne(Integer.parseInt(s));
////                    list.add(suggestModel);
////                }
//            }else {
////                list = serverSuggestService.getAll(type,1,10000);
//            }
//        }else {
////            list = serverSuggestService.getAll(null,1,10000);
//        }
//        try {
//            String fileName = new String(("热词管理" + DateUtil.getDay(new Date())));
//            Sheet sheet = new Sheet(1,0,HostWordsModel.class);
//            sheet.setSheetName("热词管理");
//            writer.write(list, sheet);
//            response.setContentType("multipart/form-data");
//            response.setCharacterEncoding("utf-8");
//            fileName = excelController.getFileName(request, fileName);
//            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            writer.finish();
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @ApiOperation(value = "导出行为记录",notes = "导出行为记录")
    @GetMapping("/exportServerBehavior")
    public void exportServerBehavior( ServerDict serverDict,String ids,HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream out = null;
        String userid = SessionUtils.getUserId(request);
        OmUser omUser = omUserService.findByUserid(userid);
        if (ToolUtil.isEmpty(omUser)){
            throw new BussinessException(100,"请登录");
        }
//        if (ToolUtil.isEmpty(param)){
//            throw new BussinessException(500,"参数为空");
//        }
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
//        JSONObject object = JSONObject.parseObject(param);
//        List<BehaviorModel> list = null;
//        if (ToolUtil.isNotEmpty(object)){
//            Integer type = object.getInteger("type");
//            if (ToolUtil.isNotEmpty(type)){
//                list = behaviorService.getAll(type,ids,1,10000);
//            }
//        }else {
//            list = behaviorService.getAll(null,ids,1,10000);
//        }
        try {
            String fileName = new String(("行为记录" + DateUtil.getDay(new Date())));
            Sheet sheet = new Sheet(1,0,BehaviorModel.class);
            sheet.setSheetName("行为记录");
            List<BehaviorModel> list = behaviorService.excel(serverDict,ids);
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




    @ApiOperation(value = "导出服务管理",notes = "导出服务管理")
    @GetMapping("/exportServerInfo")
    public void exportServerInfo(ConditionModel conditionModel, String ids,HttpServletRequest request, HttpServletResponse response){

        ServletOutputStream out = null;
        String userid = SessionUtils.getUserId(request);
        OmUser omUser = omUserService.findByUserid(userid);
        if (ToolUtil.isEmpty(omUser)){
            throw new BussinessException(100,"请登录");
        }

//        if (ToolUtil.isEmpty(conditionModel)){
//            throw new BussinessException(500,"参数为空");
//
//        }
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
        try {
            String fileName = new String(("服务管理" + DateUtil.getDay(new Date())));
            Sheet sheet = new Sheet(1,0,ServerModelDto.class);
            sheet.setSheetName("服务管理");
            List<ServerModelDto> list = serverInfoService.select(conditionModel,ids);
            writer.write(list, sheet);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            fileName = excelController.getFileName(request, fileName);
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

    @ApiOperation(value = "导出服务咨询",notes = "导出服务咨询")
    @GetMapping("/exportServerSuggest")
    public void exportServerSuggest( SuggestDto suggestDto, String ids,HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream out = null;
        String userid = SessionUtils.getUserId(request);
        OmUser omUser = omUserService.findByUserid(userid);
        if (ToolUtil.isEmpty(omUser)){
            throw new BussinessException(100,"请登录");
        }
//        if (ToolUtil.isEmpty(suggestDto)){
//            throw new BussinessException(500,"参数为空");
//        }
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Integer empDeptId = suggestDto.getEmpDeptId();
        String empDeptName = suggestDto.getEmpDeptName();
        Integer status = suggestDto.getStatus();
        Integer type = suggestDto.getType();
        Integer isDeliver = suggestDto.getIsDeliver();
        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
        try {
            String fileName = new String(("服务咨询" + DateUtil.getDay(new Date())));
            Sheet sheet = new Sheet(1,0,SuggestModel.class);
            sheet.setSheetName("服务咨询");
            List<SuggestModel> list = serverSuggestService.find(empDeptId,empDeptName,status,type,isDeliver,ids);
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

    @ApiOperation(value = "导出服务投诉",notes = "导出服务投诉")
    @GetMapping("/exportServerComplaint")
    public void exportServerComplaint( SuggestDto suggestDto, String ids,HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream out = null;
        String userid = SessionUtils.getUserId(request);
        OmUser omUser = omUserService.findByUserid(userid);
        if (ToolUtil.isEmpty(omUser)){
            throw new BussinessException(100,"请登录");
        }
//        if (ToolUtil.isEmpty(suggestDto)){
//            throw new BussinessException(500,"参数为空");
//        }
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer empDeptId = suggestDto.getEmpDeptId();
        String empDeptName = suggestDto.getEmpDeptName();
        Integer status = suggestDto.getStatus();
        Integer type = suggestDto.getType();
        Integer isDeliver = suggestDto.getIsDeliver();
        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);


        try {
            String fileName = new String(("服务投诉" + DateUtil.getDay(new Date())));
            Sheet sheet = new Sheet(1,0,ComplaintModel.class);
            sheet.setSheetName("服务投诉");
            List<ComplaintModel> list = serverSuggestService.getComplaint(empDeptId,empDeptName,status,type,isDeliver,ids);
            writer.write(list, sheet);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            fileName = excelController.getFileName(request, fileName);
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

    @ApiOperation(value = "导出热词管理",notes = "导出热词管理")
    @GetMapping("/exportHostWords")
    public void exportHostWords( HostRequest hostRequest, HttpServletRequest request, HttpServletResponse response) {

        ServletOutputStream out = null;
        String userid = SessionUtils.getUserId(request);
        OmUser omUser = omUserService.findByUserid(userid);
        if (ToolUtil.isEmpty(omUser)){
            throw new BussinessException(100,"请登录");
        }

        try {
            out = response.getOutputStream();
        }catch (IOException e) {
            e.printStackTrace();
        }
        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);

            String words = hostRequest.getWords();
            Integer status = hostRequest.getStatus();
        try {
            String fileName = new String(("热词管理" + DateUtil.getDay(new Date())));
            Sheet sheet = new Sheet(1,0,HostWordsModel.class);
            sheet.setSheetName("热词管理");
               List<HostWordsModel> severHostList = serverHostWordsService.SelectHostWords(words, status);
            writer.write(severHostList, sheet);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            fileName = excelController.getFileName(request, fileName);
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
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
