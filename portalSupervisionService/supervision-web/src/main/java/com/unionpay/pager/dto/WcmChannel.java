package com.unionpay.pager.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
/**
 *wcmchannel表实体类
 * @author jinzhao
 * @date 2019-08-05
 */

public class WcmChannel {
    private Integer id;

    private String name;

    private Integer parentId;

//    private ColumnChildren columnChildren;
    private boolean isParent;

    public WcmChannel() {
    }

    public WcmChannel(Integer id, String name, Integer parentId, boolean isParent) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.isParent = isParent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    @Override
    public String toString() {
        return "WcmChannel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", isParent=" + isParent +
                '}';
    }
}
