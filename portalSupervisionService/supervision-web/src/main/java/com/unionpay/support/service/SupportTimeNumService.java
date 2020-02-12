package com.unionpay.support.service;

import com.unionpay.support.pojo.SupportTimeNum;

import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */
public interface SupportTimeNumService {
    List<SupportTimeNum> selectByPlaceName(String myPlace);

    List<SupportTimeNum> selectByPlaceNameAndExceptedTime(String myPlace);
}
