package com.unionpay.services.service;

import com.unionpay.services.model.FlowChart;
import com.unionpay.services.model.ServerAttachmentFlowChart;
import com.unionpay.supervision.domain.SuperFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServerattachmentflowchartService {
    /**
     * 上传流程图，并保存至数据库
     * @param file
     * @param savePath
     * @param userId
     * @return
     *
     * @author lishuang
     * @data 2019-03-11
     */
    FlowChart uploadAndSaveFlowChart(MultipartFile file, String savePath, String userId);

    /**
     * 根据id删除流程图
     * @param id
     *
     * @author lishuang
     * @data 2019-03-11
     */
    void delete(Integer id);

    /**
     * 根据服务id删除所有流程图
     * @param serverId
     *
     * @author lishuang
     * @data 2019-03-12
     */
    void deleteAllByServerId(Integer serverId);

    /**
     * 根据流程图id查看
     * @param id
     * @return
     *
     * @author lishuang
     * @data 2019-03-11
     */
    ServerAttachmentFlowChart get(Integer id);

    /**
     * 根据服务id查询所有流程图
     * @param serverId
     * @return
     *
     * @author lishuang
     * @date 2019-03-12
     */
    List<ServerAttachmentFlowChart> getAllByServerId(Integer serverId);

    /**
     * 新增流程图
     * @param serverAttachmentFlowChart
     * @return
     *
     * @author lishuang
     * @date 2019-03-12
     */
    public ServerAttachmentFlowChart add(ServerAttachmentFlowChart serverAttachmentFlowChart);

    /**
     * 下载流程图
     * @param id
     * @return
     *
     * @author lishuang
     * @date 2019-03-27
     */
    public ServerAttachmentFlowChart downLoadFlowChar(Integer id);
}
