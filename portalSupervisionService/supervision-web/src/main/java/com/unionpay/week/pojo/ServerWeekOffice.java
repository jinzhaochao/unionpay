package com.unionpay.week.pojo;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: jinzhao
 * @Date: 2019/12/25 17:44
 * @Description:
 */
@Entity
@Table(name="server_week_office")
@NamedQuery(name="ServerWeekOffice.findAll", query="SELECT s FROM ServerWeekOffice s")
public class ServerWeekOffice implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    //工作日
    @Column(name = "office_time")
    private String officeTime;

    //创建时间
    @Column(name = "create_time")
    private String createTime;

    //年份
    private String year;

    public ServerWeekOffice() {
    }

    public ServerWeekOffice(String id, String officeTime, String createTime, String year) {
        this.id = id;
        this.officeTime = officeTime;
        this.createTime = createTime;
        this.year = year;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfficeTime() {
        return officeTime;
    }

    public void setOfficeTime(String officeTime) {
        this.officeTime = officeTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}