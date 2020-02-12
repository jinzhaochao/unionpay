package com.unionpay.services.service;

import com.unionpay.services.model.ServerDeliver;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/12/18/ 15:47
 * @Description:
 */
public interface ServerDeliverService  {
    List<ServerDeliver> findBySuggestId(Integer id);
}
