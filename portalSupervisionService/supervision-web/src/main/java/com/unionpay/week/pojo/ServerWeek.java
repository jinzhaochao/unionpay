package com.unionpay.week.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: jinzhao
 * @Date: 2019/12/25 17:44
 * @Description:
 */
@Entity
@Table(name="server_week")
@NamedQuery(name="ServerWeek.findAll", query="SELECT s FROM ServerWeek s")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class ServerWeek implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "UNID")
    private String unid;

    //类型
    @Column(name = "TYPE")
    private String type;

    //年份
    @Column(name = "YEAR")
    private String year;

    //创建时间
    @Column(name = "CREATETIME")
    private String createTime;

    //开始时间
    @Column(name = "STARTDATE")
    private String startDate;

    //结束时间
    @Column(name = "ENDDATE")
    private String endDate;

    //名称   节假日或是加班日
    @Column(name = "MEMO")
    private String memo;

    //天数
    @Column(name = "NUM")
    private Integer num;

    //状态
    @Column(name = "STATE")
    private String state;

    public ServerWeek() {
    }

    public ServerWeek(String unid, String type, String year, String createTime, String startDate, String endDate, String memo, Integer num, String state) {
        this.unid = unid;
        this.type = type;
        this.year = year;
        this.createTime = createTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memo = memo;
        this.num = num;
        this.state = state;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

