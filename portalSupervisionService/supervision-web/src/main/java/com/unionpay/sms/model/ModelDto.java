package com.unionpay.sms.model;

import java.util.List;

/**
 * 部门树信息实体类
 * @author lishuang
 * @date 2019-04-17
 */
public class ModelDto {
    private String id;
    private String name;
    private Boolean isOrg;
    private List children;

    public ModelDto() {
    }

    public ModelDto(String id, String name, Boolean isOrg, List children) {
        this.id = id;
        this.name = name;
        this.isOrg = isOrg;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsOrg() {
        return isOrg;
    }

    public void setIsOrg(Boolean org) {
        isOrg = org;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ModelDto{" +
                "id='" + id +
                ", name='" + name +
                ", isOrg=" + isOrg +
                ", children=" + children +
                '}';
    }
}
