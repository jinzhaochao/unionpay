package com.unionpay.support.model;


/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:分页查询服务人列表条件
 */
public class ServerUserPage {

    //页码
    private Integer page;
    //每页条数
    private Integer size;
    //状态（1在用；2禁用）
    private Integer status;
    //受理人name
    private String serverName;

    public ServerUserPage() {
    }

    public ServerUserPage(Integer page, Integer size, Integer status, String serverName) {
        this.page = page;
        this.size = size;
        this.status = status;
        this.serverName = serverName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
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
}
