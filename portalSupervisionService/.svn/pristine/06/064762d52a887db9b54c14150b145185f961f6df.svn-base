package com.unionpay.pager.domain;
import javax.persistence.*;
import java.io.Serializable;
/**
 * @description: 商户入网需求信息实体类
 * @author: lishuang
 * @date: 2019/12/04
 */
@Entity
@Table(name = "pager_business_require_info")
public class BusinessRequireInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "business_type")
	private Integer businessType;

	@Column(name = "product_type")
	private Integer productType;

	@Column(name = "price_type")
	private Integer priceType;

	@Column(name = "price_and_algorithm")
	private String priceAndAlgorithm;

	@Column(name = "material_and_require")
	private String materialAndRequire;

	@Column(name = "launch_mode")
	private String launchMode;

	@Column(name = "contact_info")
	private String contactInfo;

	@Column(name = "create_time")
	private String createTime;

	@Column(name = "create_userid")
	private String createUserid;

	@Column(name = "modify_time")
	private String modifyTime;

	@Column(name = "modify_userid")
	private String modifyUserid;

	private Integer status;

	private String remark;

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserid() {
		return createUserid;
	}

	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserid() {
		return modifyUserid;
	}

	public void setModifyUserid(String modifyUserid) {
		this.modifyUserid = modifyUserid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "BusinessRequireInfo{" +
				"id=" + id +
				", businessType=" + businessType +
				", productType=" + productType +
				", priceType=" + priceType +
				", priceAndAlgorithm=" + priceAndAlgorithm +
				", materialAndRequire=" + materialAndRequire +
				", launchMode=" + launchMode +
				", contactInfo=" + contactInfo +
				", createTime=" + createTime +
				", createUserid=" + createUserid +
				", modifyTime=" + modifyTime +
				", modifyUserid=" + modifyUserid +
				", status=" + status +
				", remark=" + remark +
				'}';
	}
}
