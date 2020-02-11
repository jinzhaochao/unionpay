package com.unionpay.services.service;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.services.model.ServerDict;

import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/12/012 9:38
 * @Description:
 */
public interface ServerdictService {

    /**
     * 显示下拉框内容
     * @return
     */
    public List<ServerDict> selectAll();

    /**
     * 服务分类下拉列表
     * @return
     * @author lishuang
     * @date 2019-03-19
     */
    public JSONObject selectType();
}
