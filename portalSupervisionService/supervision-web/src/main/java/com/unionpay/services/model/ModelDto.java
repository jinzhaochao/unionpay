package com.unionpay.services.model;

import java.util.List;

/**
 * 受理部门及受理人-树信息实体类
 * @author lishuang
 * @date 2019-04-17
 */
public class ModelDto {
    private String id;
    private String name;
    private Boolean isOrg;
    private Boolean isParent;
    private String type;
    private List children;

    public ModelDto() {
    }

    public ModelDto(String id, String name, Boolean isOrg, Boolean isParent, String type, List children) {
        this.id = id;
        this.name = name;
        this.isOrg = isOrg;
        this.isParent = isParent;
        this.type = type;
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

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean parent) {
        isParent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                ", isParent=" + isParent +
                ", type='" + type +
                ", children=" + children +
                '}';
    }
}
