package com.unionpay.pager.service;

import com.unionpay.pager.domain.BusinessRequireInfo;
import com.unionpay.pager.dto.BusinessRequireInfoVo;
import com.unionpay.pager.dto.RequireConditionDto;
import com.unionpay.pager.dto.RequireInfoDto;
import com.unionpay.pager.dto.ReturnRequireInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * @description: 商户入网需求 service
 * @author: lishuang
 * @date: 2019/12/04
 */
public interface BusinessRequireInfoService {
    /**
     * 新增入网需求信息
     * @param infoDto
     * @return
     */
    public BusinessRequireInfo add(RequireInfoDto infoDto);
    /**
     * 修改入网需求信息
     * @param infoDto
     * @return
     */
    public BusinessRequireInfo update(RequireInfoDto infoDto);
    /**
     * 查询入网需求信息
     * @param id
     * @return
     */
    public RequireInfoDto get(Integer id);
    /**
     * 首页查询商户入网要求
     * @param businessType
     * @param productType
     * @param mcc
     * @return
     */
    public List<BusinessRequireInfoVo> getInfos(Integer businessType, Integer productType, Integer mcc);
    /**
     * 分页查询入网需求信息
     * @param conditionDto
     * @return
     */
    public List<ReturnRequireInfoDto> getPage(RequireConditionDto conditionDto);
    /**
     * 查询入网需求信息总数
     * @param conditionDto
     * @return
     */
    public int getTotal(RequireConditionDto conditionDto);
    /**
     * 删除入网需求信息
     * @param ids
     */
    public List<BusinessRequireInfo> delete(List<Integer> ids);
    /**
     * 下拉框
     * @return
     */
    public List<Map> selectMap(Integer id,Integer type,String mcc);
    /**
     * excel模版导入
     * @param file
     * @throws Exception
     */
    public void importExcel(MultipartFile file) throws Exception;
}
