package com.unionpay.services.service;

import com.unionpay.services.model.ServerCommonProblem;
import com.unionpay.services.model.ServerHandlingMaterial;

import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/12/012 14:07
 * @Description:
 */
public interface ServerhandlingmaterialService {

    /**
     * 查询一个办理材料的详情
     * @param id
     * @param serverId
     * @return
     */
    public ServerHandlingMaterial getOneById(Integer id, Integer serverId);

    /**
     * 查询某一项目所有的办理材料
     * @param serverId
     * @return
     */
    public List<ServerHandlingMaterial> getSelectAll(Integer serverId);


    /*
    修改办理材料
     */
    ServerHandlingMaterial edit(ServerHandlingMaterial serverHandlingMaterial);

    /**
     * 根据服务id删除所有受理材料
     * @param id
     * @return
     * @author lishuang
     * @date 2019-03-29
     */
    public void deleteAllByServerId(Integer id);
}
