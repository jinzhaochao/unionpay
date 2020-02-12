package com.unionpay.services.service;

import com.unionpay.services.model.FlowChart;
import com.unionpay.services.model.ServerAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/13/013 15:33
 * @Description:
 */
public interface ServerattachmentService {

    /**
     * 上传附件
     * @param file
     * @param savePath
     * @param materId
     * @param type
     * @return
     */
    FlowChart uploadAndSaveAttachment(MultipartFile file, String savePath, Integer materId, Integer type);

    /*
    通过办理材料Id删除该Id下的所有附件
     */
    void deleteByMaterId(Integer materId);

    /**
     * 根据材料materId，查询所有对应的附件
     * @return
     * @author lishuang
     * @date 2019-04-01
     */
    public List<ServerAttachment> getAll(Integer materId);

}
