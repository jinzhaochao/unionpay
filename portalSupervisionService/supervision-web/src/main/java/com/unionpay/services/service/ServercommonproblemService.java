package com.unionpay.services.service;

import com.unionpay.services.model.ServerCommonProblem;

import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/11/011 14:06
 * @Description:
 */
public interface ServercommonproblemService {


    /**
     * 查询一个问题的详情
     * @param id
     * @param serverId
     * @return
     */
    public ServerCommonProblem getOneById(Integer id, Integer serverId);

    /**
     * 查询某一项目的所有问题
     * @param serverId
     * @return
     */
    public List<ServerCommonProblem> getSelectAll(Integer serverId);

    /**
     * 根据服务serverId，删除所有相关问题
     * @param serverId
     * @author lishaung
     * @date 2019-04-01
     */
    public void deleteAllByServerId(Integer serverId);

}
