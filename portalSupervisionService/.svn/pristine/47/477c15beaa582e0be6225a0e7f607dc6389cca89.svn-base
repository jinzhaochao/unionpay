package com.unionpay.services.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionpay.services.model.*;
import com.unionpay.services.model.OnlineTotal;

import java.util.List;


public interface ServerInfoService {
    /**
     * 进入新增页面，生成服务主键id
     * @author lishuang
     * @date 2019-04-01
     */
    public Integer generateId();

    /**
     * 生成主键id后，保存空数据
     * @author lishuang
     * @date 2019-04-01
     */
    public void save(Integer id);

    /**
     * 服务编码生成策略（部门orgid不足5位，在前面补0）
     * @param orgid
     * @return
     *
     * @author lishaung
     * @data 2019-03-12
     */
    public String getSerId(Integer orgid);

    /**
     * 新增基本信息
     * @param serverInfoModelDto
     * @return
     *
     * @author lishuang
     * @data 2019-03-12
     */
    public ServerInfo add(ServerInfoModelDto serverInfoModelDto,Integer orgid);

    /**
     * 删除基本信息
     * @param id
     *
     * @author lishaung
     * @data 2019-03-12
     */
    public void delete(Integer id);

    /**
     * 修改基本信息（废弃）
     * @param serverInfoModelDto
     * @return
     *
     * @author lishuang
     * @date 2019-03-14
     */
    public ServerInfoModelDto update(ServerInfoModelDto serverInfoModelDto);

    /**
     * 查询基本信息-后台
     * @param id
     * @return
     *
     * @author lishaung
     * @data 2019-03-12
     */
    public JSONObject getById(Integer id);

    /**
     * 服务分页查询列表
     * @param conditionModel
     * @return
     *
     * @author lishuang
     * @date 2019-03-14
     */
    public List<ServiceInfoModelDto> getPage(ConditionModel conditionModel);

    /**
     * 基本信息分页查询总条数
     * @param conditionModel
     * @return
     *
     * @author lishuang
     * @date 2019-03-14
     */
    public int getCount(ConditionModel conditionModel);

    /**
     * 分页查询条件下拉列表
     * @return
     *
     * @author lishuang
     * @date 2019-03-19
     */
    public JSONObject getTypeAndStatus();

    /**
     * 对象转换成基本信息
     * @param serverInfoModelDto
     * @return
     *
     * @author lishuang
     * @data 2019-03-12
     */
    public ServerInfo translateServerInfo(ServerInfoModelDto serverInfoModelDto);

    /**
     * 修改基本信息时，先进行数据库中该服务下受理部门、受理人、流程图的清除操作
     * @param id
     * @return
     *
     * @author lishuang
     * @date 2019-03-14
     */
    public Boolean deleteAll(Integer id);

    /**
     * 热门服务
     * @param top
     *
     * @author lishuang
     * @date 2019-03-18
     */
    public JSONObject getHotService(Integer top);

    /**
     * 查看服务详情-前台
     * @param id
     * @return
     *
     * @author lishuang
     * @date 2019-03-18
     */
    public JSONObject getServerInfo(Integer id);

    /**
     * ServerFrontPage-分页查询基本信息
     * @param orgId
     * @param keyword
     * @return
     *
     */
    public List<ServiceInfoModelDto> ServerFrontPage(Integer page,Integer size, Integer orgId, String keyword,Integer onlineProcessing);
    public Integer getCount(Integer page,Integer size, Integer orgId, String keyword,Integer onlineProcessing);
    /**
     * 服务禁用
     * @param ids
     * @return
     *
     * @author lishuang
     * @date 2019-03-26
     */
    public void serverDisable(String[] ids);

    /**
     * 服务启用
     * @param ids
     * @return
     *
     * @author lishuang
     * @date 2019-03-26
     */
    public void serverEnable(String[] ids);

    public List<ServerFrontDeptAndNum> listFront();

    /**
     * 受理部门、受理人等树信息(递归查询)
     * @return
     *
     * @author lishuang
     * @data 2019-04-17
     */
    public JSONArray findInfos();

    /**
     * 处理部门树信息
     *
     * @date 2019-08-06
     * @return
     */
    public JSONArray findInfos(Integer id);

    /**
     * 根据部门orgid，查询该部门人员
     * @param orgid
     *
     * @date 2019-08-06
     * @return
     */
    public JSONArray getPerson(Integer orgid);

    /**
     * 修改基本信息
     * @param serverInfoModelDto
     * @return
     */
    public ServerInfo updateServerInfo(ServerInfoModelDto serverInfoModelDto);

    OnlineTotal selectOnlineTotal(Integer orgId, String keyword);

    List<ServerModelDto> select(ConditionModel conditionModel, String ids);
}
