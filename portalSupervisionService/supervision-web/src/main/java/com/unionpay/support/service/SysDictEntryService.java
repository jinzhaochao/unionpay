package com.unionpay.support.service;

import com.unionpay.support.pojo.SysDictEntry;

import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/11/04 16:59
 * @Description:
 */
public interface SysDictEntryService {
    List<SysDictEntry> select(String str);
}
