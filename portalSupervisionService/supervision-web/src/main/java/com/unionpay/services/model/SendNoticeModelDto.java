package com.unionpay.services.model;

import java.util.List;

/**
 * 发送通知实体类
 * @author lishuang
 * @date 2019/11/08
 */
public class SendNoticeModelDto {
    private String title;
    private String url;
    private String account;
    private String password;
    private Integer typeId;
    private List<Integer> receivers;

    public SendNoticeModelDto() {
    }

    public SendNoticeModelDto(String title, String url, String account, String password, Integer typeId, List<Integer> receivers) {
        this.title = title;
        this.url = url;
        this.account = account;
        this.password = password;
        this.typeId = typeId;
        this.receivers = receivers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public List<Integer> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Integer> receivers) {
        this.receivers = receivers;
    }

    @Override
    public String toString() {
        return "SendNoticeModelDto{" +
                "title=" + title +
                ", url=" + url +
                ", account=" + account +
                ", password=" + password +
                ", typeId=" + typeId +
                ", receivers=" + receivers +
                '}';
    }
}
