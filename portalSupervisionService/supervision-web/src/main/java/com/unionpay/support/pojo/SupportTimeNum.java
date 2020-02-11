package com.unionpay.support.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description:
 */
@Entity
@Table(name="support_time_num")
@NamedQuery(name="SupportTimeNum.findAll", query="SELECT s FROM SupportTimeNum s")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class SupportTimeNum implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //对应sys_dict_entry的DICTID
    @Column(name="place_id")
    private String placeId;

    //地点
    @Column(name="place_name")
    private String placeName;

    //期望时间段
    @Column(name="excepted_time")
    private String exceptedTime;

    //时间段次数
    @Column(name="excepted_num")
    private Integer exceptedNum;

    //排序
    private Integer sort;

    public SupportTimeNum() {
    }

    public SupportTimeNum(String placeId, String placeName, String exceptedTime, Integer exceptedNum, Integer sort) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.exceptedTime = exceptedTime;
        this.exceptedNum = exceptedNum;
        this.sort = sort;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getExceptedTime() {
        return exceptedTime;
    }

    public void setExceptedTime(String exceptedTime) {
        this.exceptedTime = exceptedTime;
    }

    public Integer getExceptedNum() {
        return exceptedNum;
    }

    public void setExceptedNum(Integer exceptedNum) {
        this.exceptedNum = exceptedNum;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SupportTimeNum{" +
                "id=" + id +
                ", placeId='" + placeId + '\'' +
                ", placeName='" + placeName + '\'' +
                ", exceptedTime='" + exceptedTime + '\'' +
                ", exceptedNum=" + exceptedNum +
                ", sort=" + sort +
                '}';
    }
}
