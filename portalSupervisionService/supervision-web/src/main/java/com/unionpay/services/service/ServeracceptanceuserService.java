package com.unionpay.services.service;

import com.unionpay.services.model.ServerAcceptanceUser;

import java.util.List;

public interface ServeracceptanceuserService {

    /**
     * 根据id查询受理人
     * @param id
     * @return
     *
     * @author lishuang
     * @date 2019-03-14
     */
    ServerAcceptanceUser getOne(Integer id);

    /**
     * 根据服务id查询所有受理人
     * @param serverId
     * @return
     *
     * @author lishuang
     * @date 2019-03-12
     */
    List<ServerAcceptanceUser> getAllByServerId(Integer serverId);

    /**
     * 根据服务id删除所有受理人
     * @param serverId
     *
     * @author lishuang
     * @data 2019-03-12
     */
    void deleteAllByServerId(Integer serverId);

    /**
     * 新增受理人
     * @param serverAcceptanceUser
     *
     * @author lishuang
     * @data 2019-03-12
     */
    ServerAcceptanceUser add(ServerAcceptanceUser serverAcceptanceUser);

    /**
     * 根据主键id删除受理人
     * @param id
     * @author lishuang
     * @date 2019-04-19
     */
    public void deleteById(Integer id);
}
