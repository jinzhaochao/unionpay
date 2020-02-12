package com.unionpay.services.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 行为记录列表实体类
 * @author lishuang
 * @date 2019/08/27
 */
public class BehaviorModel extends BaseRowModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
     * 行为记录 ID
     */
    //@ExcelProperty(value = { "序号" }, index = 0)
    private BigInteger behaviorId;
    /*
     * 类型
     */
    @ExcelProperty(value = { "类型" }, index = 1)
    private String behaviorType;
    /*
     * 时间
     */
    @ExcelProperty(value = { "时间" }, index = 2)
    private String createTime;
    /*
     * 操作人
     */
    @ExcelProperty(value = { "操作人" }, index = 3)
    private String empName;
    /*
     * 部门
     */
    @ExcelProperty(value = { "部门" }, index = 4)
    private String orgName;

    public BigInteger getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(BigInteger behaviorId) {
        this.behaviorId = behaviorId;
    }

    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return "BehaviorModel{" +
                "behaviorId=" + behaviorId +
                ", behaviorType=" + behaviorType +
                ", createTime=" + createTime +
                ", empName=" + empName +
                ", orgName=" + orgName +
                '}';
    }
}
