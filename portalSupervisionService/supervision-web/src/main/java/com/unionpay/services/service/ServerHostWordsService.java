package com.unionpay.services.service;

import com.alibaba.fastjson.JSONObject;
import com.unionpay.services.model.HostWordsModel;
import com.unionpay.services.model.ServerHostWords;
import com.unionpay.services.model.SeverHost;

import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/20/020 18:12
 * @Description:
 */
public interface ServerHostWordsService {

    /**
     * 服务反馈信息查询与分页
     * @param words
     * @param status
     * @return
     *
     * @author lishuang
     * @date 2019-03-14
     */
    List<SeverHost> SelectAllHostWords(Integer page, Integer size, String words, Integer status);
    Integer getCount(Integer page, Integer size, String words, Integer status);

    /*
    删除热词
     */
    void deleteByWords(String wrods);

    List selectHostWords();

    ServerHostWords findByText(String words);

    ServerHostWords add(ServerHostWords serverHostWords);

    List<HostWordsModel> SelectHostWords(String words, Integer status);
}
