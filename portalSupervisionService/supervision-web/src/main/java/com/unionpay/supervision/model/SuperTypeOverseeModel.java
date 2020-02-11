package com.unionpay.supervision.model;

/**
 * 我的待办已办、督办事项查询，自定义事项大类下拉框实体类
 * @author lishuang
 * @date 2019-04-11
 */
public class SuperTypeOverseeModel {
    //督办类型表主键unid
    private String unid;
    //督办类型名称
    private String typeName;

    public SuperTypeOverseeModel() {
    }

    public SuperTypeOverseeModel(String unid, String typeName) {
        this.unid = unid;
        this.typeName = typeName;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "SuperTypeOverseeModel{" +
                "unid='" + unid +
                ", typeName='" + typeName +
                '}';
    }
}
