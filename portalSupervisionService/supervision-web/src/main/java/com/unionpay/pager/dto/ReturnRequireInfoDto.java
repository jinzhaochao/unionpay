package com.unionpay.pager.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
public class ReturnRequireInfoDto extends BaseRowModel {
    private Integer id;
    @ExcelProperty(value = { "业务类型" }, index = 0)
    private String businessType;
    @ExcelProperty(value = { "产品类型" }, index = 1)
    private String productType;
    @ExcelProperty(value = { "MCC可用范围" }, index = 2)
    private String mccInfo;
    @ExcelProperty(value = { "价格类型" }, index = 3)
    private String priceType;
    @ExcelProperty(value = { "入网价格及银联发卡分润算法" }, index = 4)
    private String priceAndAlgorithm;
    @ExcelProperty(value = { "业务材料及要求" }, index = 5)
    private String materialAndRequire;
    @ExcelProperty(value = { "入网发起方式" }, index = 6)
    private String launchMode;
    @ExcelProperty(value = { "入网联系人" }, index = 7)
    private String contactInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getMccInfo() {
        return mccInfo;
    }

    public void setMccInfo(String mccInfo) {
        this.mccInfo = mccInfo;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getPriceAndAlgorithm() {
        return priceAndAlgorithm;
    }

    public void setPriceAndAlgorithm(String priceAndAlgorithm) {
        this.priceAndAlgorithm = priceAndAlgorithm;
    }

    public String getMaterialAndRequire() {
        return materialAndRequire;
    }

    public void setMaterialAndRequire(String materialAndRequire) {
        this.materialAndRequire = materialAndRequire;
    }

    public String getLaunchMode() {
        return launchMode;
    }

    public void setLaunchMode(String launchMode) {
        this.launchMode = launchMode;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "ReturnRequireInfoDto{" +
                "id=" + id +
                ", businessType=" + businessType +
                ", productType=" + productType +
                ", mccInfo=" + mccInfo +
                ", priceType=" + priceType +
                ", priceAndAlgorithm=" + priceAndAlgorithm +
                ", materialAndRequire=" + materialAndRequire +
                ", launchMode=" + launchMode +
                ", contactInfo=" + contactInfo +
                '}';
    }
}
