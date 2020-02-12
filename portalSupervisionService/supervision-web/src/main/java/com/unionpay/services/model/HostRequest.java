package com.unionpay.services.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/22/022 19:28
 * @Description:
 */
public class HostRequest implements Serializable {
    private Integer page;

    private Integer size;

    private Integer status;

    private String words;

    private List<SeverHost> severHosts;

    public HostRequest(Integer page, Integer size, Integer status, String words, List<SeverHost> severHosts, Integer type) {
        this.page = page;
        this.size = size;
        this.status = status;
        this.words = words;
        this.severHosts = severHosts;
        this.type = type;
    }

    public HostRequest() {
        super();
    }

    /*
            type 1:把非热词该为热词  0:把热词改为非热词
             */
    private Integer type;

    public List<SeverHost> getSeverHosts() {
        return severHosts;
    }

    public void setSeverHosts(List<SeverHost> severHosts) {
        this.severHosts = severHosts;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
