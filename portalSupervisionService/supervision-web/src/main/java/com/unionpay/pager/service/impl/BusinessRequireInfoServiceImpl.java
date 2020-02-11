package com.unionpay.pager.service.impl;

import com.unionpay.common.exception.BussinessException;
import com.unionpay.common.seesion.SessionUtils;
import com.unionpay.common.util.DateUtil;
import com.unionpay.common.util.ExcelReadUtil;
import com.unionpay.common.util.ToolUtil;
import com.unionpay.pager.dao.*;
import com.unionpay.pager.domain.*;
import com.unionpay.pager.dto.*;
import com.unionpay.pager.service.BusinessRequireInfoService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;

@Service
public class BusinessRequireInfoServiceImpl implements BusinessRequireInfoService {

    @Autowired
    private BusinessRequireInfoRepository infoRepository;
    @Autowired
    private BusinessRequireRMccRepository requireRMccRepository;
    @Autowired
    private BusinessTypeRepository typeRepository;
    @Autowired
    private BusinessDictRepository dictRepository;
    @Autowired
    private BusinessMccRepository mccRepository;
    @Autowired
    private EntityManager entityManager;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 新增入网需求信息
     * @param infoDto
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public BusinessRequireInfo add(RequireInfoDto infoDto){
        BusinessRequireInfo info = transform(infoDto);
        info = infoRepository.save(info);
        if (ToolUtil.isNotEmpty(info)){
            List<Integer> mccIds = infoDto.getMccIds();
            if (ToolUtil.isNotEmpty(mccIds)&&mccIds.size()>=1){
                List<BusinessRequireRMcc> list = new ArrayList<>();
                for (Integer mccId:mccIds){
                    BusinessRequireRMcc requireRMcc = new BusinessRequireRMcc();
                    requireRMcc.setMccId(mccId);
                    requireRMcc.setRequireId(info.getId());
                    list.add(requireRMcc);
                }
                list = requireRMccRepository.saveAll(list);
            }
        }
        return info;
    }

    /**
     * 修改入网需求信息
     * @param infoDto
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public BusinessRequireInfo update(RequireInfoDto infoDto){
        BusinessRequireInfo info = transform(infoDto);
        info = infoRepository.save(info);
        if (ToolUtil.isNotEmpty(info)){
            // 需求与MCC关系修改
            requireRMccRepository.deleteAllByRequireId(info.getId());
            List<Integer> mccIds = infoDto.getMccIds();
            if (ToolUtil.isNotEmpty(mccIds)&&mccIds.size()>=1){
                List<BusinessRequireRMcc> list = new ArrayList<>();
                for (Integer mccId:mccIds){
                    BusinessRequireRMcc requireRMcc = new BusinessRequireRMcc();
                    requireRMcc.setMccId(mccId);
                    requireRMcc.setRequireId(info.getId());
                    list.add(requireRMcc);
                }
                list = requireRMccRepository.saveAll(list);
            }
        }
        return info;
    }

    /**
     * 删除入网需求信息
     * @param ids
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public List<BusinessRequireInfo> delete(List<Integer> ids){
        List<BusinessRequireInfo> infos = new ArrayList<>();
        if (ToolUtil.isNotEmpty(ids)&&ids.size()>=1){
            for (Integer id:ids){
                BusinessRequireInfo info = infoRepository.getOne(id);
                if (ToolUtil.isNotEmpty(info)){
                    info.setStatus(0);
                    infos.add(info);
                }
                // mcc对应关系删除
                requireRMccRepository.deleteAllByRequireId(id);
            }
            infos = infoRepository.saveAll(infos);
        }
        return infos;
    }

    /**
     * 查询入网需求信息详情
     * @param id
     * @return
     */
    public RequireInfoDto get(Integer id){
        String sql = "SELECT r.id,r.business_type businessType,t1.`name` businessTypeName,r.product_type productType,"
                + "t2.`name` productTypeName,r.price_type priceType,d.`name` priceTypeName,r.price_and_algorithm priceAndAlgorithm,"
                + "r.material_and_require materialAndRequire,r.launch_mode launchMode,r.contact_info contactInfo "
                + "FROM pager_business_require_info r LEFT JOIN pager_business_type t1 ON t1.id = r.business_type "
                + "LEFT JOIN pager_business_type t2 ON t2.id = r.product_type LEFT JOIN pager_business_dict d "
                + "ON r.price_type=d.id WHERE r.id =:id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id",id);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(RequireInfoDto.class));
        RequireInfoDto infoDto = (RequireInfoDto)query.getSingleResult();
        // MCC信息
        List<Integer> mccList = requireRMccRepository.getAllByRequireId(id);
        infoDto.setMccIds(mccList);
        return infoDto;
    }

    /**
     * 首页查询商户入网要求
     * @param businessType
     * @param productType
     * @param mcc
     * @return
     */
    public List<BusinessRequireInfoVo> getInfos(Integer businessType,Integer productType,Integer mcc){
        List<BusinessRequireInfoVo> list = null;
        String sql = "SELECT d.`name` priceType,r.price_and_algorithm priceAndAlgorithm,r.material_and_require "
                + "materialAndRequire,r.launch_mode launchMode,r.contact_info contactInfo FROM pager_business_require_info r "
                + "LEFT JOIN pager_business_require_r_mcc rrm ON r.id = rrm.require_id LEFT JOIN pager_business_dict d "
                + "ON d.id = r.price_type WHERE r.business_type =:businessType AND r.product_type =:productType "
                + " AND rrm.mcc_id =:mccId AND r.status = '1'";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("businessType",businessType);
        query.setParameter("productType",productType);
        query.setParameter("mccId",mcc);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(BusinessRequireInfoVo.class));
        list = query.getResultList();
        return list;
    }

    /**
     * 分页查询入网需求信息
     * @param conditionDto
     * @return
     */
    public List<ReturnRequireInfoDto> getPage(RequireConditionDto conditionDto){
        String sql = "SELECT r.id,t1.`name` businessType,t2.`name` productType,d.`name` priceType,"
                + "GROUP_CONCAT((SELECT CONCAT(m.`value`, '-', m.`name`) FROM pager_business_mcc m "
                + "WHERE m.`id` = rrm.mcc_id)) mccInfo,r.price_and_algorithm priceAndAlgorithm,"
                + "r.material_and_require materialAndRequire,r.launch_mode launchMode,r.contact_info contactInfo "
                + "FROM pager_business_require_info r "
                + "LEFT JOIN pager_business_type t1 ON t1.id = r.business_type "
                + "LEFT JOIN pager_business_type t2 ON t2.id = r.product_type "
                + "LEFT JOIN pager_business_dict d ON (d.`value` = r.price_type AND d.dict_type = 'price_type')"
                + "LEFT JOIN pager_business_require_r_mcc rrm ON r.id = rrm.require_id WHERE 1 = 1 ";
        if (ToolUtil.isNotEmpty(conditionDto.getIds())){
            sql += "and r.id IN (";
            List<Integer> ids = conditionDto.getIds();
            for (int i=0;i<ids.size();i++){
                if (i == ids.size()-1){
                    sql += ids.get(i)+")";
                }else {
                    sql += ids.get(i) +",";
                }
            }
        }
        if (ToolUtil.isNotEmpty(conditionDto.getBusinessType())){
            sql += "AND r.business_type = " + conditionDto.getBusinessType();
        }
        if (ToolUtil.isNotEmpty(conditionDto.getProductType())){
            sql += " AND r.product_type = " + conditionDto.getProductType();
        }
        if (ToolUtil.isNotEmpty(conditionDto.getPriceType())){
            sql += " AND r.price_type = " + conditionDto.getPriceType();
        }
        if (ToolUtil.isNotEmpty(conditionDto.getMccId())){
            sql += " AND rrm.mcc_id = " + conditionDto.getMccId();
        }
        sql += " and r.status = '1' GROUP BY r.id ORDER BY r.create_time DESC";
        Query query = entityManager.createNativeQuery(sql);
        if (ToolUtil.isNotEmpty(conditionDto.getPage()) && ToolUtil.isNotEmpty(conditionDto.getSize())){
            query.setFirstResult((conditionDto.getPage()-1)*conditionDto.getSize());
            query.setMaxResults(conditionDto.getSize());
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(ReturnRequireInfoDto.class));
        return query.getResultList();
    }

    /**
     * 查询入网需求信息总数
     * @param conditionDto
     * @return
     */
    public int getTotal(RequireConditionDto conditionDto){
        int count = 0;
        String sql = "SELECT count(r.id) FROM pager_business_require_info r "
                + "LEFT JOIN pager_business_type t1 ON t1.id = r.business_type "
                + "LEFT JOIN pager_business_type t2 ON t2.id = r.product_type "
                + "LEFT JOIN pager_business_dict d ON (d.`value` = r.price_type AND d.dict_type = 'price_type')"
                + "LEFT JOIN pager_business_require_r_mcc rrm ON r.id = rrm.require_id "
                + "LEFT JOIN pager_business_mcc m ON rrm.mcc_id = m.id WHERE 1 = 1 ";
        if (ToolUtil.isNotEmpty(conditionDto.getBusinessType())){
            sql += "AND r.business_type = " + conditionDto.getBusinessType();
        }
        if (ToolUtil.isNotEmpty(conditionDto.getProductType())){
            sql += " AND r.product_type = " + conditionDto.getProductType();
        }
        if (ToolUtil.isNotEmpty(conditionDto.getPriceType())){
            sql += " AND r.price_type = " + conditionDto.getPriceType();
        }
        if (ToolUtil.isNotEmpty(conditionDto.getMccId())){
            sql += " AND rrm.mcc_id = " + conditionDto.getMccId();
        }
        sql += " and r.status = '1' GROUP BY r.id ORDER BY r.create_time DESC";
        Query query = entityManager.createNativeQuery(sql);
        List<BigInteger> counts = query.getResultList();
        if (ToolUtil.isNotEmpty(counts)){
            count = counts.size();
        }
        return count;
    }

    /**
     * 下拉框
     * @return
     */
    public List<Map> selectMap(Integer id,Integer type,String mcc){
        List<Map> list = new ArrayList<>();
        if (type==0){
            List<BusinessType> businessTypes = typeRepository.getAllByParentId(id);
            if (ToolUtil.isNotEmpty(businessTypes)){
                for (BusinessType businessType:businessTypes){
                    Map<String,String> map = new LinkedHashMap<>();
                    map.put("id",businessType.getId().toString());
                    map.put("name",businessType.getName());
                    list.add(map);
                }
            }
        }
        if (type == 1){
            String sql = "SELECT * FROM (SELECT m.id,CONCAT(m.`value`, '-', m.`name`) `name` FROM pager_business_mcc m WHERE "
                    + "m.`id` IN(SELECT rrm.mcc_id FROM pager_business_require_r_mcc rrm WHERE rrm.require_id "
                    + "IN (SELECT r.id FROM pager_business_require_info r WHERE r.product_type =:productType)) ORDER BY m.id) mcc";
            if (ToolUtil.isNotEmpty(mcc)){
                sql += " WHERE mcc.name LIKE '%"+mcc+"%'";
            }
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("productType",id);
            query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(BusinessMccDto.class));
            list = query.getResultList();
        }
        return list;
    }

    /**
     * excel模版导入
     * @param file
     * @throws Exception
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void importExcel(MultipartFile file) throws Exception{
        String userid = SessionUtils.getUserId();
        Workbook workbook = null;
        InputStream inputStream = null;
        inputStream = file.getInputStream();
        String filename = file.getOriginalFilename();
        if (filename != null && (filename.lastIndexOf(".xlsx") == (filename.length() - 5))) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            workbook = new HSSFWorkbook(inputStream);
        }
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        if (rowCount>=1){
            for (int i = 1;i<=rowCount;i++){
                try {
                    Row row = sheet.getRow(i);
                    if (row.getLastCellNum() > 1){
                        BusinessType type = null;
                        String bType = ExcelReadUtil.convertToString(row.getCell(0));
                        if (ToolUtil.isEmpty(bType)){
                            throw new BussinessException(500,"业务类型不能为空");
                        }
                        int businessType = 0;
                        type = typeRepository.getByParentIdAndName(0,bType);
                        if (ToolUtil.isNotEmpty(type)){
                            businessType = type.getId();
                        }else {
                            throw new BussinessException(500,"不存在["+bType+"]对应的业务类型");
                        }
                        String pType = ExcelReadUtil.convertToString(row.getCell(1));
                        if (ToolUtil.isEmpty(pType)){
                            throw new BussinessException(500,"产品类型不能为空");
                        }
                        int productType = 0;
                        type = typeRepository.getByParentIdAndName(businessType,pType);
                        if (ToolUtil.isNotEmpty(type)){
                            productType = type.getId();
                        }else {
                            throw new BussinessException(500,"不存在["+pType+"]对应的产品类型");
                        }
                        String mccIds = ExcelReadUtil.convertToString(row.getCell(2));
                        if (ToolUtil.isEmpty(mccIds)){
                            throw new BussinessException(500,"MCC可用范围不能为空");
                        }
                        String prType = ExcelReadUtil.convertToString(row.getCell(3));
                        if (ToolUtil.isEmpty(prType)){
                            throw new BussinessException(500,"价格类型不能为空");
                        }
                        int priceType = 0;
                        BusinessDict dict = dictRepository.getByName(prType);
                        if (ToolUtil.isNotEmpty(dict)){
                            priceType = dict.getId();
                        }else {
                            throw new BussinessException(500,"不存在["+prType+"]对应的价格类型");
                        }
                        String priceAndAlgorithm = ExcelReadUtil.convertToString(row.getCell(4));
                        String materialAndRequire = ExcelReadUtil.convertToString(row.getCell(5));
                        String launchMode = ExcelReadUtil.convertToString(row.getCell(6));
                        String contactInfo = ExcelReadUtil.convertToString(row.getCell(7));
                        // 入网需求信息存储
                        BusinessRequireInfo info = new BusinessRequireInfo();
                        info.setBusinessType(businessType);
                        info.setProductType(productType);
                        info.setPriceType(priceType);
                        info.setPriceAndAlgorithm(priceAndAlgorithm);
                        info.setMaterialAndRequire(materialAndRequire);
                        info.setLaunchMode(launchMode);
                        info.setContactInfo(contactInfo);
                        info.setCreateTime(DateUtil.getStrTime(new Date()));
                        info.setCreateUserid(userid);
                        info.setStatus(1);
                        info = infoRepository.save(info);
                        // 入网需求与MCC关系存储
                        if(mccIds.contains("/")){
                            String[] ids = mccIds.split("/");
                            if (ids.length>=1){
                                for (int j=0;j<ids.length;j++) {
                                    if (ToolUtil.isEmpty(ids[j])){
                                        log.error("第"+(i+1)+"行,第"+(j+1)+"个MCC为空");
                                        continue;
                                    }
                                    BusinessRequireRMcc requireRMcc = new BusinessRequireRMcc();
                                    requireRMcc.setRequireId(info.getId());
                                    //查询mccId
                                    BusinessMcc mcc = mccRepository.getByValue(ids[j]);
                                    if (ToolUtil.isNotEmpty(mcc)){
                                        requireRMcc.setMccId(mcc.getId());
                                    }else {
                                        throw new BussinessException(500,"MCC【"+ids[j]+"】 不符合要求");
                                    }
                                    requireRMccRepository.save(requireRMcc);
                                }
                            }
                        }else {
                            BusinessRequireRMcc requireRMcc = new BusinessRequireRMcc();
                            requireRMcc.setRequireId(info.getId());
                            BusinessMcc mcc = mccRepository.getByValue(mccIds);
                            if (ToolUtil.isNotEmpty(mcc)){
                                requireRMcc.setMccId(mcc.getId());
                            }else {
                                throw new BussinessException(500,"MCC【"+mccIds+"】 不符合要求");
                            }
                            requireRMccRepository.save(requireRMcc);
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    String error = e.getMessage();
                    if(!error.contains("不")){
                        error = "";
                    }
                    throw new BussinessException(500,"第" + (i+1) + "行异常:" +error);
                }
            }
        }
    }

    private BusinessRequireInfo transform(RequireInfoDto infoDto){
        BusinessRequireInfo info = null;
        String userid = SessionUtils.getUserId();
        if (ToolUtil.isEmpty(infoDto.getId())){
            // 新增
            info = new BusinessRequireInfo();
            info.setCreateUserid(userid);
            info.setCreateTime(DateUtil.getStrTime(new Date()));
        }else {
            // 修改
            info = infoRepository.getOne(infoDto.getId());
            info.setModifyUserid(userid);
            info.setModifyTime(DateUtil.getStrTime(new Date()));
        }
        info.setBusinessType(infoDto.getBusinessType());
        info.setProductType(infoDto.getProductType());
        info.setPriceType(infoDto.getPriceType());
        info.setPriceAndAlgorithm(infoDto.getPriceAndAlgorithm());
        info.setMaterialAndRequire(infoDto.getMaterialAndRequire());
        info.setLaunchMode(infoDto.getLaunchMode());
        info.setContactInfo(infoDto.getContactInfo());
        info.setStatus(1);
        return info;
    }
}
