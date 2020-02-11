package com.unionpay.pager.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.unionpay.common.easyexcel.EasyExcelFactory;
import com.unionpay.common.resultBean.RESTResultBean;
import com.unionpay.common.util.DateUtil;
import com.unionpay.pager.dto.RequireConditionDto;
import com.unionpay.pager.dto.ReturnRequireInfoDto;
import com.unionpay.pager.service.BusinessRequireInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/businessEnterNetExcel")
@Api(value = "商户入网需求Excel管理", tags = "商户入网需求Excel管理", description = "商户入网需求Excel管理")
public class BusinessExcelController {
    @Autowired
    private BusinessRequireInfoService infoService;

    @ResponseBody
    @PostMapping("/importExcel")
    @ApiOperation(value = "数据导入",notes = "用于excel模版数据入库操作")
    public RESTResultBean importExcel(MultipartFile file) throws Exception{
        RESTResultBean result = new RESTResultBean(200,"操作成功");
        infoService.importExcel(file);
        return result;
    }

    @ResponseBody
    @GetMapping("/exportExcel")
    @ApiOperation(value = "数据导出",notes = "按照需求导出数据至excel")
    public void exportExcel(RequireConditionDto conditionDto, HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
        try {
            String fileName = "商户入网要求" + DateUtil.getAllTime(new Date());
            Sheet sheet = new Sheet(1, 0,
                    ReturnRequireInfoDto.class);
            sheet.setSheetName("商户入网要求");
            List<ReturnRequireInfoDto> list = infoService.getPage(conditionDto);
            writer.write(list, sheet);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            fileName = getFileName(request, fileName);
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            out.flush();
        }catch (Exception e){
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
