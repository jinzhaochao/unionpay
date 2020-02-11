package com.unionpay.services.model;

import java.math.BigInteger;

/**
 * 部门满意度排行数据实体
 * @author lishuang
 * @date 2019/10/24
 */
public class CountDeptModel {
    private String orgid;
    private String orgname;
    private BigInteger num;

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public BigInteger getNum() {
        return num;
    }

    public void setNum(BigInteger num) {
        this.num = num;
    }
}
