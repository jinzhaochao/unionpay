package com.unionpay.services.service;

import com.unionpay.services.model.ServerAcceptanceDept;

import java.util.List;

public interface ServeracceptancedeptService {
    /**
     * 根据服务id查询所有受理部门
     * @param serverId
     * @return
     *
     * @author lishuang
     * @date 2019-03-12
     */
    List<ServerAcceptanceDept> getAllByServerId(Integer serverId);

    /**
     * 根据服务id删除所有受理部门
     * @param serverId
     *
     * @author lishuang
     * @date 2019-03-12
     */
    void deleteAllByServerId(Integer serverId);

    /**
     * 新增受理部门
     * @param serverAcceptanceDept
     * @return
     *
     * @author lishuang
     * @date 2019-03-12
     */
    ServerAcceptanceDept add(ServerAcceptanceDept serverAcceptanceDept);

    /**
     * 根据主键id删除手里部门
     * @param id
     * @author lishuang
     * @date 2019-04-19
     */
    public void deleteById(Integer id);
}
