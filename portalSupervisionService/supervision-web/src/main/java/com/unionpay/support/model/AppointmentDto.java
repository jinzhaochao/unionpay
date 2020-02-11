package com.unionpay.support.model;

import javax.persistence.Column;

/**
 * @Author: jinzhao
 * @Date: 2019/10/25 09:31
 * @Description: 预约新增实体类
 */
public class AppointmentDto {

    private Integer id;

    //预约人name
    private String appointmentName;

    //预约人联系方式
    private String appointmentConnection;

    //问题类别
    private Integer questionType;

    //问题描述
    private String questionCategory;

    //支持类型：(1，电话支持；2，现场支持；)
    private Integer supportType;

    //我的地点
    private String myPlace;

    //详细地点
    private String placeDetail;

    //期望日期
    private String exceptedDay;

    //期望时间
    private String exceptedTime;

    //备选日期
    private String alternativeDay;

    //备选时间
    private String alternativeTime;

    public AppointmentDto() {
    }

    public AppointmentDto(Integer id, String appointmentName, String appointmentConnection, Integer questionType, String questionCategory, Integer supportType, String myPlace, String placeDetail, String exceptedDay, String exceptedTime, String alternativeDay, String alternativeTime) {
        this.id = id;
        this.appointmentName = appointmentName;
        this.appointmentConnection = appointmentConnection;
        this.questionType = questionType;
        this.questionCategory = questionCategory;
        this.supportType = supportType;
        this.myPlace = myPlace;
        this.placeDetail = placeDetail;
        this.exceptedDay = exceptedDay;
        this.exceptedTime = exceptedTime;
        this.alternativeDay = alternativeDay;
        this.alternativeTime = alternativeTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getAppointmentConnection() {
        return appointmentConnection;
    }

    public void setAppointmentConnection(String appointmentConnection) {
        this.appointmentConnection = appointmentConnection;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory;
    }

    public Integer getSupportType() {
        return supportType;
    }

    public void setSupportType(Integer supportType) {
        this.supportType = supportType;
    }

    public String getMyPlace() {
        return myPlace;
    }

    public void setMyPlace(String myPlace) {
        this.myPlace = myPlace;
    }

    public String getPlaceDetail() {
        return placeDetail;
    }

    public void setPlaceDetail(String placeDetail) {
        this.placeDetail = placeDetail;
    }

    public String getExceptedDay() {
        return exceptedDay;
    }

    public void setExceptedDay(String exceptedDay) {
        this.exceptedDay = exceptedDay;
    }

    public String getExceptedTime() {
        return exceptedTime;
    }

    public void setExceptedTime(String exceptedTime) {
        this.exceptedTime = exceptedTime;
    }

    public String getAlternativeDay() {
        return alternativeDay;
    }

    public void setAlternativeDay(String alternativeDay) {
        this.alternativeDay = alternativeDay;
    }

    public String getAlternativeTime() {
        return alternativeTime;
    }

    public void setAlternativeTime(String alternativeTime) {
        this.alternativeTime = alternativeTime;
    }
}
