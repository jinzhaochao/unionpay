package com.unionpay.support.serviceImpl;

import com.unionpay.support.dao.SupportTimeNumRepository;
import com.unionpay.support.pojo.SupportTimeNum;
import com.unionpay.support.service.SupportTimeNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */
@Service
@Transactional
public class SupportTimeNumServiceImpl implements SupportTimeNumService{

    @Autowired
    private SupportTimeNumRepository supportTimeNumRepository;

    @Override
    public List<SupportTimeNum> selectByPlaceName(String myPlace) {
        List<SupportTimeNum> list = supportTimeNumRepository.findByPlaceName(myPlace);
        return list;
    }

    @Override
    public List<SupportTimeNum> selectByPlaceNameAndExceptedTime(String myPlace) {
        List<SupportTimeNum> list = supportTimeNumRepository.findByPlaceNameAndExceptedTime(myPlace);
        return list;
    }
}
