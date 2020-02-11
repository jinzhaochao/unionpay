package com.unionpay.services.model;

import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/22/022 19:08
 * @Description:
 */
public class SugestRequest implements Serializable {
    private Integer page;
    private Integer size;
    private Integer orgId;
    private Integer status;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
