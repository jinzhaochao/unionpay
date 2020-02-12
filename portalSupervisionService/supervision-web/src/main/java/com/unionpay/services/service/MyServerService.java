package com.unionpay.services.service;

import com.unionpay.services.entity.ServerApplyCount;
import com.unionpay.services.entity.ServerApplyInfo;
import com.unionpay.services.model.CheckConditionModel;
import com.unionpay.services.model.ServerApplyModel;
import com.unionpay.services.util.Result;

import java.math.BigInteger;
import java.util.List;

/**
 * 我的服务 查询流程列表 service
 * @author lishuang
 * @date 2019/10/10
 */
public interface MyServerService {

    /**
     * 分页查询进行中流程列表（进行中的流程接口）
     * @return
     */
    public Result getUnderWayServerUseGet(String classify, String launchTimeEnd, String launchTimeStart, String userid, String size, String currentPage,String serviceInfoName);

    /**
     * 分页查询已完成服务列表
     * @param checkConditionModel 查询条件
     * @param userid 当前登录人userid
     * @return
     */
    public List<ServerApplyModel> getCompleteServer(CheckConditionModel checkConditionModel,String userid);

    /**
     * 查询已完成服务总数
     * @param checkConditionModel 查询条件
     * @param userid 当前登录人userid
     * @return
     */
    public int count(CheckConditionModel checkConditionModel,String userid);

    /**
     * 查看详情页
     * @param id 流程编号ID
     * @return
     */
    public ServerApplyModel get(BigInteger id);

    /**
     * 评价已完成流程
     * @param id 流程编号ID
     * @param score 评价（1满意；0不满意）
     * @return
     */
    public boolean giveScore(BigInteger id,Integer score);

    /**
     * 根据类型查询统计数据
     * @param type 1本周；2本月；3本季
     * @return
     */
    public ServerApplyCount getDate(Integer type);

    /**
     * 根据类型查询部门满意度排行
     * @param type 1本周；2本月；3本季
     * @return
     */
    public List sort(Integer type);

    /**
     * 获取已完成流程
     * @param endTimeStart
     * @param endTimeEnd
     */
    public List<ServerApplyInfo> getCompleteServer(String endTimeStart, String endTimeEnd);

    /**
     * 获取流程平台服务统计（服务总量统计接口）
     */
    public void getServerCount();
}
