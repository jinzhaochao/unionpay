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
@Table(name="support_info")
@NamedQuery(name="SupportInfo.findAll", query="SELECT s FROM SupportInfo s")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class SupportInfo implements Serializable{

        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "id", nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        //编号
        private String number;

        //创建时间
        @Column(name="create_time")
        private String createTime;

        //预约人name
        @Column(name="appointment_name")
        private String appointmentName;

        //预约人联系方式
        @Column(name="appointment_connection")
        private String appointmentConnection;

        //问题类别（1网络故障；2硬件故障；3权限开通）
        @Column(name="question_type")
        private Integer questionType;

        //问题描述
        @Column(name="question_category")
        private String questionCategory;

        //支持类型：(1，电话支持；2，现场支持；)
        @Column(name="support_type")
        private Integer supportType;

        //我的地点
        @Column(name="my_place")
        private String myPlace;

        //详细地点
        @Column(name="place_detail")
        private String placeDetail;

        //期望日期
        @Column(name="excepted_day")
        private String exceptedDay;

        //期望时间段
        @Column(name="excepted_time")
        private String exceptedTime;

        //备选日期
        @Column(name="alternative_day")
        private String alternativeDay;

        //备选时间段
        @Column(name="alternative_time")
        private String alternativeTime;

        //提交人id
        @Column(name="emp_id")
        private Integer empId;

        //提交人名字
        @Column(name="emp_name")
        private String empName;

        //提交人部门id
        @Column(name="emp_dept_id")
        private Integer empDeptId;

        //提交人部门名字
        @Column(name="emp_dept_name")
        private String empDeptName;

        //受理人id
        @Column(name="reply_id")
        private Integer replyId;

        //受理人name
        @Column(name="reply_name")
        private String replyName;

        //受理时间
        @Column(name = "reply_time")
        private String replyTime;

        //服务人员id
        @Column(name = "server_userid")
        private Integer serverUserId;

        //服务人名字
        @Column(name = "server_username")
        private String serverUserName;

        //服务日期
        @Column(name = "server_day")
        private String serverDay;

        //服务时间段
        @Column(name = "server_time")
        private String serverTime;

        //回复原因
        @Column(name = "server_reason")
        private String serverReason;

        //评价类型(0满意  1不满意)
        @Column(name = "evaluate_type")
        private Integer evaluateType;

        //受理状态 (0：未处理 1：待服务  2：已完成 3：已撤销)
        private Integer status;

        //评价内容
        @Column(name = "evaluate_content")
        private String evaluateContent;

        //评价时间
        @Column(name = "evaluate_time")
        private String evaluateTime;

    public SupportInfo() {
    }

    public SupportInfo(String number, String createTime, String appointmentName, String appointmentConnection, Integer questionType, String questionCategory, Integer supportType, String myPlace, String placeDetail, String exceptedDay, String exceptedTime, String alternativeDay, String alternativeTime, Integer empId, String empName, Integer empDeptId, String empDeptName, Integer replyId, String replyName, String replyTime, Integer serverUserId, String serverUserName, String serverDay, String serverTime, String serverReason, Integer evaluateType, Integer status, String evaluateContent, String evaluateTime) {
        this.number = number;
        this.createTime = createTime;
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
        this.empId = empId;
        this.empName = empName;
        this.empDeptId = empDeptId;
        this.empDeptName = empDeptName;
        this.replyId = replyId;
        this.replyName = replyName;
        this.replyTime = replyTime;
        this.serverUserId = serverUserId;
        this.serverUserName = serverUserName;
        this.serverDay = serverDay;
        this.serverTime = serverTime;
        this.serverReason = serverReason;
        this.evaluateType = evaluateType;
        this.status = status;
        this.evaluateContent = evaluateContent;
        this.evaluateTime = evaluateTime;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getEmpDeptId() {
        return empDeptId;
    }

    public void setEmpDeptId(Integer empDeptId) {
        this.empDeptId = empDeptId;
    }

    public String getEmpDeptName() {
        return empDeptName;
    }

    public void setEmpDeptName(String empDeptName) {
        this.empDeptName = empDeptName;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
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

    public String getServerDay() {
        return serverDay;
    }

    public void setServerDay(String serverDay) {
        this.serverDay = serverDay;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getServerReason() {
        return serverReason;
    }

    public void setServerReason(String serverReason) {
        this.serverReason = serverReason;
    }

    public Integer getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(Integer evaluateType) {
        this.evaluateType = evaluateType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public String getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    @Override
    public String toString() {
        return "SupportInfo{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", createTime='" + createTime + '\'' +
                ", appointmentName='" + appointmentName + '\'' +
                ", appointmentConnection='" + appointmentConnection + '\'' +
                ", questionType=" + questionType +
                ", questionCategory='" + questionCategory + '\'' +
                ", supportType=" + supportType +
                ", myPlace='" + myPlace + '\'' +
                ", placeDetail='" + placeDetail + '\'' +
                ", exceptedDay='" + exceptedDay + '\'' +
                ", exceptedTime='" + exceptedTime + '\'' +
                ", alternativeDay='" + alternativeDay + '\'' +
                ", alternativeTime='" + alternativeTime + '\'' +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empDeptId=" + empDeptId +
                ", empDeptName='" + empDeptName + '\'' +
                ", replyId=" + replyId +
                ", replyName='" + replyName + '\'' +
                ", replyTime='" + replyTime + '\'' +
                ", serverUserId=" + serverUserId +
                ", serverUserName='" + serverUserName + '\'' +
                ", serverTime='" + serverTime + '\'' +
                ", replyReason='" + serverReason + '\'' +
                ", evaluateType=" + evaluateType +
                ", status=" + status +
                ", evaluateContent='" + evaluateContent + '\'' +
                ", evaluateTime='" + evaluateTime + '\'' +
                '}';
    }
}
