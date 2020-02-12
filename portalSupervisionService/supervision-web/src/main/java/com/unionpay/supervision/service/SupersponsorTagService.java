package com.unionpay.supervision.service;


import com.unionpay.supervision.domain.SuperSponsorTag; /**
 * jinzhao
 * 2019-11-20
 * 标签中间表接口
 */
public interface SupersponsorTagService {
    void add(SuperSponsorTag superSponsorTag);

    SuperSponsorTag findByTagIdAndSponsorUnid(Integer tagId, String unid);

    void delTag(String unid);
}
