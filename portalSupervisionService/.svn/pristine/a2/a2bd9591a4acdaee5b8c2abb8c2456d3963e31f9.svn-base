package com.unionpay.services.service;

import com.unionpay.services.model.Server0pinionFeedback;
import com.unionpay.services.model.Server0pinionFeedbackModelDto;

import java.util.List;

public interface ServerOpinionFeedbackService {

    /**
     * 意见反馈新增
     * @param id
     * @param content
     * @param empid
     * @return
     *
     * @author lishuang
     * @date 2019-03-18
     */
    public Server0pinionFeedback add(Integer id,String content,Integer empid);

    /**
     * 查询意见反馈详情
     * @param id
     * @return
     *
     * @author lishaung
     * @date 2019-03-21
     */
    public Server0pinionFeedbackModelDto getOne(Integer id);

    /**
     * 分页查询意见反馈列表
     * @param page
     * @param size
     * @param orgid
     * @param status
     * @return
     *
     * @author lishuang
     * @date 2019-03-20
     */
    public List<Server0pinionFeedbackModelDto> getAll(Integer page,Integer size,Integer orgid,Integer status);

    /**
     * 查询意见反馈条数
     * @param page
     * @param size
     * @param orgid
     * @param status
     * @return
     *
     * @author lishuang
     * @date 2019-03-20
     */
    public Integer getCount(Integer page,Integer size,Integer orgid,Integer status);

    /**
     * 编辑意见反馈详情
     * @param server0pinionFeedbackModelDto
     * @return
     *
     * @author lishuang
     * @date 2019-03-21
     */
    public Server0pinionFeedback edit(Server0pinionFeedbackModelDto server0pinionFeedbackModelDto,Integer empid);
}
