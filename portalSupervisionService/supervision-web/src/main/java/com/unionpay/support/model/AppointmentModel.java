package com.unionpay.support.model;


/**
 * @Author: jinzhao
 * @Date: 2019/10/30 22:40
 * @Description:  条件分页查询预约处理列表实体类
 */
public class AppointmentModel {

    //当前页码
    private Integer page;
    //每页条数
    private Integer size;
    //预约人名字
    private String appointmentName;
    //起始时间
    private String timeStart;
    //截止时间
    private String timeEnd;
    //服务人id
    private Integer serverUserId;
    //服务人名字
    private String serverUserName;
    //受理状态 (0：未处理 1：待服务 2：已完成 3：已撤销)
    private Integer status;
    //tab页 （1我的预约  2处理列表）
    private Integer tabPage;

    public AppointmentModel() {
    }

    public AppointmentModel(Integer page, Integer size, String appointmentName, String timeStart, String timeEnd, Integer serverUserId, String serverUserName, Integer status, Integer tabPage) {
        this.page = page;
        this.size = size;
        this.appointmentName = appointmentName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.serverUserId = serverUserId;
        this.serverUserName = serverUserName;
        this.status = status;
        this.tabPage = tabPage;
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

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getServerUserId() {
        return serverUserId;
    }

    public void setServerUserId(Integer serverUserId) {
        this.serverUserId = serverUserId;
    }

    public String getServerUserName() {
        return serverUserName;
    }

    public void setServerUserName(String serverUserName) {
        this.serverUserName = serverUserName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTabPage() {
        return tabPage;
    }

    public void setTabPage(Integer tabPage) {
        this.tabPage = tabPage;
    }
}
