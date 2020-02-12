package com.unionpay.supervision.model;

import javax.validation.constraints.NotEmpty;

public class MobileOaInfo {
    @NotEmpty
    private String unid;

    public MobileOaInfo() {
    }

    public MobileOaInfo(@NotEmpty String unid) {
        this.unid = unid;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }
}
