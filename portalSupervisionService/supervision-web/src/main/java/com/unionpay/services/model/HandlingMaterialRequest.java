package com.unionpay.services.model;

import java.io.Serializable;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/22/022 15:35
 * @Description:
 */
public class HandlingMaterialRequest implements Serializable {
    private Integer id;
    private Integer serverId;
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
