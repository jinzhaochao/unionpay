package com.unionpay.services.service;

import com.unionpay.services.entity.ServerBehavior;
import com.unionpay.services.model.BehaviorModel;
import com.unionpay.services.model.ServerDict;

import java.util.List;

/**
 * 行为记录
 */
public interface ServerBehaviorService {
    /**
     * 行为记录下拉框数据
     * @return
     */
    public List listSelect();

    /**
     * 新增行为记录
     * @param id
     * @param userId
     * @return
     */
    public ServerBehavior add(Integer id, String userId);

    /**
     * 根据id查找行为记录
     * @param id
     * @return
     */
    public BehaviorModel getOne(Integer id);

    /**
     * 查询所有行为记录
     * @param id
     * @param page
     * @param size
     * @return
     */
    public List<BehaviorModel> getAll(Integer id, Integer page, Integer size);

    /**
     * 查询所有行为记录总数
     * @param behavior
     * @param page
     * @param size
     * @return
     */
    public Integer count(Integer behavior, Integer page, Integer size);

    List<BehaviorModel> excel(ServerDict serverDict, String ids);
}
