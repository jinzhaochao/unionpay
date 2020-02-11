package com.unionpay.services.service;

import com.unionpay.services.model.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/20/020 14:25
 * @Description:
 */
public interface ServerSuggestService {
    /**
     * 服务反馈信息查询与分页
     * @param orgId
     * @param status
     * @return
     *
     * @author lishuang
     * @date 2019-03-14
     */
    List<ServerSuggestModel> SelectAllSuggest(Integer page, Integer size, Integer orgId, Integer status);

    Integer getCount(Integer page, Integer size, Integer orgId, Integer status);

    ServerSuggest findById(Integer id);

    ServerSuggest edit(ServerSuggest serverSuggest);

    List<ServerSuggestAndGiveLikeModel> selectSuggestAll(Integer page, Integer size, String title, Integer type, Integer empDeptId, String empName, Date startTime, Date endTime, Integer status, Integer isDeliver, Integer tabPage, String userId);

    Integer getTotal(String title, Integer type, Integer empDeptId, String empName, Date startTime, Date endTime, Integer status, Integer isDeliver, Integer tabPage, String userId);

    void update(Integer id, String userId);

    ServerSuggestAndGiveLikeModel select(Integer id, String userId,Integer tabPage);

    ServerSuggestModel selectComplaint(Integer id);

    List<ServerSuggestModel> selectComplaintAll(Integer page, Integer size, String title, Integer type, Integer empDeptId, String empName, Date startTime, Date endTime, Integer status, Integer isDeliver, Integer tabPage, String userId);

    Integer getCountTotal(String title, Integer type, Integer empDeptId, String empName, Date startTime, Date endTime, Integer status, Integer isDeliver, Integer tabPage, String userId);


    /**
     * 查询投诉总数
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public int getComplaintTotal(String beginTime,String endTime);

    /**
     * 新增咨询投诉信息
     * @param addSuggestDto
     * @param userid
     */
    public void addSuggestAndComplain(AddSuggestDto addSuggestDto,String userid);


    /**
     * 处理投诉咨询信息
     * @return
     */
    public ServerSuggest updateSuggestInfo(ServerSuggestDeliver serverSuggestModel,String userid);

    List<SuggestModel> find(Integer empDeptId, String empDeptName, Integer status, Integer type, Integer isDeliver,String ids);

    List<ComplaintModel> getComplaint(Integer empDeptId, String empDeptName, Integer status, Integer type, Integer isDeliver,String ids);

}
