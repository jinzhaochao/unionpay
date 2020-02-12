package com.unionpay.services.controller;

import com.unionpay.common.util.ToolUtil;
import com.unionpay.services.model.*;
import com.unionpay.services.repository.ServerattachmentRepository;
import com.unionpay.services.repository.ServerhandlingmaterialRepository;
import com.unionpay.services.service.ServerattachmentService;
import com.unionpay.services.service.ServerhandlingmaterialService;
import com.unionpay.services.service.ServerhandlingmaterialServiceImpl;
import com.unionpay.services.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xiaopengcheng
 * 办理材料
 */
@RestController
@RequestMapping("/serverhandlingmaterial")
@Api(value = "/serverhandlingmaterial", tags = "办理材料接口", description = "办理材料接口")
public class ServerhandlingmaterialController {

    @Autowired
    private ServerhandlingmaterialServiceImpl serverhandlingmaterial_modelService;
    @Autowired
    private ServerattachmentRepository serverattachmentRepository;
    @Autowired
    private ServerhandlingmaterialRepository serverhandlingmaterialRepository;
    @Autowired
    private ServerattachmentService serverattachmentService;
    @Autowired
    private ServerhandlingmaterialService serverhandlingmaterialService;
    @Value("${http.servicecenterfile.download1}")
    private String url;

    /**
     * 添加一个办理材料
     */
    @PostMapping("/addId")
    @ApiOperation(value = "添加一个办理材料的主键ID", notes = "添加一个办理材料的主键ID")
    @ResponseBody
    public Result addId(@Valid @RequestBody ServerHandlingMaterial serverHandlingMaterial) {
        try {
            Date day=new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            serverHandlingMaterial.setName(df.format(day));
            ServerHandlingMaterial serverHandlingMaterial1 = serverhandlingmaterial_modelService.add(serverHandlingMaterial);
            ServerHandlingMaterial serverHandlingMaterial2 = serverhandlingmaterialRepository.findByName(serverHandlingMaterial1.getName());
            if (ToolUtil.isNotEmpty(serverHandlingMaterial2)) {
                return Result.build(200, "添加成功", serverHandlingMaterial2.getId());
            } else {
                return Result.build(400, "添加失败", " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 办理材料--删除一个办理材料
     */
    @PostMapping("/delete")
    @ApiOperation(value = "办理材料--删除一个办理材料", notes = "办理材料--删除一个办理材料")
    @ResponseBody
    public Result delete(@Valid @RequestBody HandlingMaterialRequest handlingMaterialRequest) {
        try {
            ServerHandlingMaterial serverHandlingMaterial =
                    serverhandlingmaterial_modelService.getOneById(handlingMaterialRequest.getId(), handlingMaterialRequest.getServerId());
            if (ToolUtil.isNotEmpty(serverHandlingMaterial)) {
                serverattachmentService.deleteByMaterId(serverHandlingMaterial.getId());
                serverhandlingmaterial_modelService.delete(handlingMaterialRequest.getId());
                return Result.success();
            } else {
                return Result.build(400, "该办理材料不存在", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 办理材料--修改一个办理材料的信息
     */
    @PostMapping("/update")
    @ApiOperation(value = "办理材料--修改一个办理材料的信息", notes = "办理材料--修改一个办理材料的信息")
    @ResponseBody
    public Result update(@Valid @RequestBody ServerHandlingMaterial serverhandlingmaterial_model) {
        try {
            //查询该办理材料
            ServerHandlingMaterial serverHandlingMaterial = serverhandlingmaterial_modelService.get(serverhandlingmaterial_model.getId());
            if (ToolUtil.isNotEmpty(serverHandlingMaterial)) {
                serverHandlingMaterial.setName(serverhandlingmaterial_model.getName());
                serverHandlingMaterial.setServerId(serverhandlingmaterial_model.getServerId());
                serverHandlingMaterial.setCreatetime(new Date());
                serverHandlingMaterial.setNecessary(serverhandlingmaterial_model.getNecessary());
                serverHandlingMaterial.setNumber(serverhandlingmaterial_model.getNumber());
                serverHandlingMaterial.setRemark(serverhandlingmaterial_model.getRemark());
                serverHandlingMaterial.setSort(serverhandlingmaterial_model.getSort());
                serverHandlingMaterial.setType(serverhandlingmaterial_model.getType());
                serverHandlingMaterial.setStatus(serverhandlingmaterial_model.getStatus());
                return Result.success(serverhandlingmaterial_modelService.edit(serverHandlingMaterial));
            } else {
                return Result.build(400, "该办理材料不存在", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }


    /**
     * 修改该问题禁用与启用的状态
     */
    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改该问题禁用与启用的状态", notes = "修改该问题禁用与启用的状态")
    @ResponseBody
    public Result updateStatus(@Valid @RequestBody HandlingMaterialRequest handlingMaterialRequest) {
        try {
            Integer type = handlingMaterialRequest.getType();
            Integer id = handlingMaterialRequest.getId();
            Integer serverId = handlingMaterialRequest.getServerId();
            ServerHandlingMaterial serverHandlingMaterial =
                    serverhandlingmaterial_modelService.getOneById(id, serverId);
            if (ToolUtil.isNotEmpty(serverHandlingMaterial)) {
                if (type == 0) {//启动改为禁用
                    serverHandlingMaterial.setStatus(0);
                }
                if (type == 1) {//禁用改为启动
                    serverHandlingMaterial.setStatus(1);
                }
                return Result.success(serverhandlingmaterial_modelService.update(serverHandlingMaterial));
            } else {
                return Result.build(400, "该问题不存在", " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 查询某一个办理材料的详情
     */
    @GetMapping("/getOne")
    @ApiOperation(value = "查询某一个办理材料的详情", notes = "查询某一个办理材料的详情")
    @ResponseBody
    public Result get(@RequestParam(name = "id") Integer id, @RequestParam(name = "serverId") Integer serverId) {
        try {
            ServerHandlingMaterial serverHandlingMaterial = serverhandlingmaterial_modelService.getOneById(id, serverId);
            List<ServerAttachment> list = serverattachmentRepository.findByMaterId(serverHandlingMaterial.getId());
            serverHandlingMaterial.setServerAttachments(list);
            if (serverHandlingMaterial == null) {
                throw new RuntimeException();
            }
            return Result.success(serverHandlingMaterial);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

    /**
     * 查询某一个项目所有的办理材料
     */
    @PostMapping("/getSelectAll")
    @ApiOperation(value = "查询某一个项目所有的办理材料", notes = "查询某一个项目所有的办理材料")
    @ResponseBody
    public Result getSelectAll(@Valid @RequestBody HandlingMaterialRequest handlingMaterialRequest) {
        try {
            int serverId =handlingMaterialRequest.getServerId();
            //获取页面传入的参数
            List<ServerHandlingMaterial> serverHandlingMaterials = serverhandlingmaterialService.getSelectAll(serverId);

            if (ToolUtil.isNotEmpty(serverHandlingMaterials)) {
                List<ServerHandlingMaterial> handlingMaterials = new ArrayList<>();
                for (ServerHandlingMaterial material :serverHandlingMaterials){
                    List<ServerAttachment> list = serverattachmentRepository.findByMaterId(material.getId());
                    List<ServerAttachment> list1 = new ArrayList<>();
                    if (ToolUtil.isNotEmpty(list)){
                        for (ServerAttachment serverAttachment : list){
                            serverAttachment.setUrl(url);
                            list1.add(serverAttachment);
                        }
                    }
                    material.setServerAttachments(list1);
                    handlingMaterials.add(material);
                }
                Collections.sort(handlingMaterials, new Comparator<ServerHandlingMaterial>() {
                    @Override
                    public int compare(ServerHandlingMaterial o1, ServerHandlingMaterial o2) {
                        if (o1.getSort()>=o2.getSort()){
                            return 1;
                        }
                        return -1;
                    }
                });
                return Result.build(200, "查询成功", handlingMaterials);
            } else if (ToolUtil.isEmpty(serverHandlingMaterials)) {
                return Result.build(200, "查询成功无数据", null);
            } else {
                return Result.build(400, "查询失败", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.toString());
        }
    }

}