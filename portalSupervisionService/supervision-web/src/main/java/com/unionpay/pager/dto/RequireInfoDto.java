package com.unionpay.pager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class RequireInfoDto {
    private Integer id;
    @NotNull
    private Integer businessType;
    private String businessTypeName;
    @NotNull
    private Integer productType;
    private String productTypeName;
    @NotNull
    private Integer priceType;
    private String priceTypeName;
    @NotBlank
    private String priceAndAlgorithm;
    @NotBlank
    private String materialAndRequire;
    @NotBlank
    private String launchMode;
    @NotBlank
    private String contactInfo;
    @NotNull
    private List<Integer> mccIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
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

    public List<Integer> getMccIds() {
        return mccIds;
    }

    public void setMccIds(List<Integer> mccIds) {
        this.mccIds = mccIds;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }

    @Override
    public String toString() {
        return "RequireInfoDto{" +
                "id=" + id +
                ", businessType=" + businessType +
                ", businessTypeName=" + businessTypeName +
                ", productType=" + productType +
                ", productTypeName=" + productTypeName +
                ", priceType=" + priceType +
                ", priceTypeName=" + priceTypeName +
                ", priceAndAlgorithm=" + priceAndAlgorithm +
                ", materialAndRequire=" + materialAndRequire +
                ", launchMode=" + launchMode +
                ", contactInfo=" + contactInfo +
                ", mccIds=" + mccIds +
                '}';
    }
}
