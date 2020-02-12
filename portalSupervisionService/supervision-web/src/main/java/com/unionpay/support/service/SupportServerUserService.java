package com.unionpay.support.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unionpay.support.pojo.SupportServerUser;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */

public interface SupportServerUserService {
    List<SupportServerUser> selectServerUserAll(Integer page, Integer size, Integer status, String serverName);

    Integer getTotal( Integer status, String serverName);

    SupportServerUser select(Integer id);

    void save(SupportServerUser supportServerUser);

    SupportServerUser selectByServerName(String serverName);

    List<SupportServerUser> selectServerUser();
}
