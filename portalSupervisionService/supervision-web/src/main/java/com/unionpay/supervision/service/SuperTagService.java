package com.unionpay.supervision.service;

import com.unionpay.supervision.domain.SuperTag;

import java.util.List;

/**
 *
 * @author jinzhao  2019-11-19
 * @Service
 * 事项标签的接口
 */
public interface SuperTagService {

    SuperTag findByTagContent(String s);

    List<SuperTag> findAll();

    void add(SuperTag superTag);
}
