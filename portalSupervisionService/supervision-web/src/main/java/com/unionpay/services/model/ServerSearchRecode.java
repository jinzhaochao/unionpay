package com.unionpay.services.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 翟俊鹏
 * @Date: 2019/3/20/020 10:31
 * @Description:
 */
@Entity
@Table(name="server_search_recode")
@NamedQuery(name="server_search_recode.findAll", query="SELECT s FROM ServerSearchRecode s")
public class ServerSearchRecode implements Serializable {
    @Id
    private int id;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;

    /**
     * 检索文本
     */
    private String text;

    /**
     * 检索人
     */
    private Integer emp_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }
}
