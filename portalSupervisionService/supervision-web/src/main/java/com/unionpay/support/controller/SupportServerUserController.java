package com.unionpay.support.controller;

import com.unionpay.common.resultBean.Pager;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.util.ResultList;
import com.unionpay.supervision.filter.submit.NoRepeatSubmit;
import com.unionpay.support.model.ServerUserModel;
import com.unionpay.support.model.ServerUserPage;
import com.unionpay.support.pojo.SupportServerUser;
import com.unionpay.support.service.SupportServerUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */

@RestController
@RequestMapping("/supportServerUser")
@Api(value = "/supportServerUser",tags = "办公支持服务之服务人信息",description = "办公支持服务之服务人信息")
public class SupportServerUserController {

    @Autowired
    private SupportServerUserService supportServerUserService;


    @ApiOperation(value = "服务人员分页查询", notes = "服务人员分页查询")
    @PostMapping("/selectPage")
    @ResponseBody
    public ResultList selectServerUserPage(@RequestBody ServerUserPage serverUserPage) {
        ResultList resultList = new ResultList();
        try {

            Integer page = serverUserPage.getPage();
            Integer size = serverUserPage.getSize();
            Integer status = serverUserPage.getStatus();
            String serverName = serverUserPage.getServerName();
            //条件分页查询反馈
            List<SupportServerUser> supportServerUserList = supportServerUserService.selectServerUserAll(page, size, status, serverName);
            if (ToolUtil.isNotEmpty(supportServerUserList)) {
                Pager pager = new Pager();
                pager.setCurrentPage(page);
                pager.setSize(size);
                //条件分页查询反馈总条数
                pager.setTotal(supportServerUserService.getTotal(status, serverName));
                resultList.setCode(200);
                resultList.setMessage("查询成功");
                resultList.setData(supportServerUserList);
                resultList.setPager(pager);
            } else if (ToolUtil.isEmpty(supportServerUserList)) {
                resultList.setMessage("查询成功,但没有信息");
                resultList.setData(supportServerUserList);
                resultList.setCode(200);
            } else {
                resultList.setCode(500);
                resultList.setMessage("查询失败");
            }
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("查询失败");
        }
        return resultList;

    }


    /**
     * @param id
     * @param
     * @return
     */
    @ApiOperation(value = "服务人员详情信息查询", notes = "服务人员详情信息查询")
    @PostMapping("/selectOne")
    @ResponseBody
    public ResultList selectServerUserDetails(@RequestParam Integer id) {
        ResultList resultList = new ResultList();
        try {

            SupportServerUser supportServerUser = supportServerUserService.select(id);
            if (ToolUtil.isNotEmpty(supportServerUser)) {
                resultList.setCode(200);
                resultList.setMessage("查询成功");
                resultList.setData(supportServerUser);
            }else if (ToolUtil.isEmpty(supportServerUser)) {
                resultList.setMessage("查询成功,但没有信息");
                resultList.setCode(200);
                resultList.setData(supportServerUser);
            }
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("查询失败");
        }

        return resultList;
    }

    @ApiOperation(value = "保存服务人信息", notes = "保存服务人信息")
    @PostMapping("/saveServerUser")
    @ResponseBody
    @NoRepeatSubmit
    public ResultList saveServerUser(@RequestBody ServerUserModel serverUserModel) {
        ResultList resultList = new ResultList();
        SupportServerUser supportServerUser = new SupportServerUser();
        try {
            if (serverUserModel.getId() > 0){
                supportServerUser = supportServerUserService.select(serverUserModel.getId());
                resultList.setMessage("修改成功");
            }else {
                SupportServerUser supportServerUser1 = supportServerUserService.selectByServerName(serverUserModel.getServerName());
                resultList.setMessage("新增成功");
                if (ToolUtil.isEmpty(supportServerUser1)){
                supportServerUser.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }else {
                    resultList.setCode(500);
                    resultList.setMessage("已有该人名,请重新输入");
                    return resultList;
                }
            }
                supportServerUser.setServerName(serverUserModel.getServerName());
                supportServerUser.setOfficePhone(serverUserModel.getOfficePhone());
                supportServerUser.setPemail(serverUserModel.getPemail());
                supportServerUser.setMobileno(serverUserModel.getMobileno());
                supportServerUser.setStatus(serverUserModel.getStatus());
                supportServerUserService.save(supportServerUser);
            resultList.setCode(200);

        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("保存失败");
            e.printStackTrace();
        }
        return resultList;
    }

    @ApiOperation(value = "在用服务人员查询", notes = "在用服务人员查询")
    @GetMapping("/selectServerUser")
    @ResponseBody
    public ResultList selectServerUser() {
        ResultList resultList = new ResultList();
        try {
            List<SupportServerUser> supportServerUser = supportServerUserService.selectServerUser();
            if (ToolUtil.isNotEmpty(supportServerUser)) {
            resultList.setCode(200);
            resultList.setMessage("查询成功");
            resultList.setData(supportServerUser);
        } else if (ToolUtil.isEmpty(supportServerUser)) {
            resultList.setMessage("查询成功,但没有信息");
            resultList.setData(supportServerUser);
            resultList.setCode(200);
        }
        } catch (Exception e) {
            resultList.setCode(500);
            resultList.setMessage("查询失败");

        }
        return resultList;
    }

//    @ApiOperation(value = "导出服务人员",notes = "导出服务人员")
//    @ResponseBody
//    @PostMapping("/exportServerUser")
//    public void exportServerInfo(@RequestBody String param, HttpServletRequest request, HttpServletResponse response){
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
//        List<SupportServerUser> list = null;
//        if (ToolUtil.isNotEmpty(object)){
//            String[] ids = object.getString("ids").split(",");
//            if (ToolUtil.isNotEmpty(ids)){
//                for (String s:ids){
//                    SupportServerUser supportServerUser = supportServerUserService.select(Integer.parseInt(s));
//                    list.add(supportServerUser);
//                }
//            }else {
////                list = serverSuggestService.getAll(type,1,10000);
//            }
//        }else {
////            list = serverSuggestService.getAll(null,1,10000);
//        }
//        try {
//            String fileName = new String(("服务人员列表" + DateUtil.getDay(new Date())));
//            Sheet sheet = new Sheet(1,0,SupportServerUser.class);
//            sheet.setSheetName("服务人员列表");
//            writer.write(list, sheet);
//            response.setContentType("multipart/form-data");
//            response.setCharacterEncoding("utf-8");
//            fileName = getFileName(request, fileName);
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
//
//    public String getFileName(HttpServletRequest request, String fileName) {
//        try {
//            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
//                fileName = new String(fileName.getBytes(), "iso-8859-1");// firefox浏览器
//            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
//                fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
//            } else {
//                fileName = URLEncoder.encode(fileName, "UTF-8");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fileName;
//    }



}
