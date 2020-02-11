package com.unionpay.services.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 服务管理列表实体类
 * @author jinzhao
 * @date 2019/09/24
 */
public class ServerModelDto extends BaseRowModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
     * 服务管理 ID
     */
    //@ExcelProperty(value = { "序号" }, index = 0)
    private BigInteger suggestId;
    /*
     * 编号
     */
    @ExcelProperty(value = {"编号"}, index = 1)
    private String serId;
    /*
     * 服务名称
     */
    @ExcelProperty(value = {"服务名称"}, index = 2)
    private String serName;
    /*
     * 所属部门
     */
    @ExcelProperty(value = {"所属部门"}, index = 3)
    private String orgName;
    /*
     * 所属分类
     */
    @ExcelProperty(value = {"所属分类"}, index = 4)
    private String type;
    /*
     * 排序
     */
    @ExcelProperty(value = {"排序"}, index = 5)
    private Integer sort;
    /*
     * 状态
     */
    @ExcelProperty(value = {"状态"}, index = 6)
    private String status;
    /*
     * 服务经理
     */
    @ExcelProperty(value = {"服务经理"}, index = 7)
    private String flowUsername;

    private String sortNo;


    public ServerModelDto() {
    }

    public ServerModelDto(BigInteger suggestId, String serId, String serName, String orgName, String type, Integer sort, String status, String flowUsername, String sortNo) {
        this.suggestId = suggestId;
        this.serId = serId;
        this.serName = serName;
        this.orgName = orgName;
        this.type = type;
        this.sort = sort;
        this.status = status;
        this.flowUsername = flowUsername;
        this.sortNo = sortNo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigInteger getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(BigInteger suggestId) {
        this.suggestId = suggestId;
    }

    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlowUsername() {
        return flowUsername;
    }

    public void setFlowUsername(String flowUsername) {
        this.flowUsername = flowUsername;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public String toString() {
        return "ServerModelDto{" +
                "suggestId=" + suggestId +
                ", serId='" + serId + '\'' +
                ", serName='" + serName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", type='" + type + '\'' +
                ", sort='" + sort + '\'' +
                ", status='" + status + '\'' +
                ", flowUsername='" + flowUsername + '\'' +
                '}';
    }
}
