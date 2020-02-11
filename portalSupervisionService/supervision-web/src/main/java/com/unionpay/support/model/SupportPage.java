package com.unionpay.support.model;


import java.math.BigInteger;

/**
 * @Author: jinzhao
 * @Date: 2019/10/31 09:37
 * @Description:分页查询预约处理列表实体类
 */
public class SupportPage {

    private int id;

    //编号
    private String number;

    //创建时间
    private String createTime;

    //预约人name
    private String appointmentName;

    //预约人联系方式
    private String appointmentConnection;

    //问题类别（1网络故障；2硬件故障；3权限开通）
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

    //期望时间段
    private String exceptedTime;

    //备选日期
    private String alternativeDay;

    //备选时间段
    private String alternativeTime;

    //提交人id
    private Integer empId;

    //提交人名字
    private String empName;

    //提交人部门id
    private Integer empDeptId;

    //提交人部门名字
    private String empDeptName;

    //受理人id
    private Integer replyId;

    //受理人name
    private String replyName;

    //受理时间
    private String replyTime;

    //服务人员id
    private Integer serverUserId;

    //服务人名字
    private String serverUserName;

    //服务日期
    private String serverDay;

    //服务时间段
    private String serverTime;

    //回复原因
    private String serverReason;

    //评价类型(0满意  1不满意)
    private Integer evaluateType;

    //受理状态 (0：未处理 1：待服务  2：已完成 3：已撤销)
    private Integer status;

    //评价内容
    private String evaluateContent;

    //评价时间
    private String evaluateTime;

    //服务人办公电话
    private String officePhone;

    //服务时间
    private String serverDateTime;
    //是否可评价
    private Integer isEvaluate;
    private BigInteger isevaluate;
    //是否可撤销
    private Integer isRevoke;
    private BigInteger isrevoke;
    //备选时间的周日期 jinzhao --2019-11-29
    private String exceptedWeek;
    //期望时间周日期
    private String alternativeWeek;
    //设备类型
    private String dictName;

    public SupportPage() {
    }

    public SupportPage(int id, String number, String createTime, String appointmentName, String appointmentConnection, Integer questionType, String questionCategory, Integer supportType, String myPlace, String placeDetail, String exceptedDay, String exceptedTime, String alternativeDay, String alternativeTime, Integer empId, String empName, Integer empDeptId, String empDeptName, Integer replyId, String replyName, String replyTime, Integer serverUserId, String serverUserName, String serverDay, String serverTime, String serverReason, Integer evaluateType, Integer status, String evaluateContent, String evaluateTime, String officePhone, String serverDateTime, Integer isEvaluate, BigInteger isevaluate, Integer isRevoke, BigInteger isrevoke, String exceptedWeek, String alternativeWeek, String dictName) {
        this.id = id;
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
        this.officePhone = officePhone;
        this.serverDateTime = serverDateTime;
        this.isEvaluate = isEvaluate;
        this.isevaluate = isevaluate;
        this.isRevoke = isRevoke;
        this.isrevoke = isrevoke;
        this.exceptedWeek = exceptedWeek;
        this.alternativeWeek = alternativeWeek;
        this.dictName = dictName;
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

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getServerDay() {
        return serverDay;
    }

    public void setServerDay(String serverDay) {
        this.serverDay = serverDay;
    }

    public String getServerDateTime() {
        return serverDateTime;
    }

    public void setServerDateTime(String serverDateTime) {
        this.serverDateTime = serverDateTime;
    }

    public Integer getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(Integer isEvaluate) {
        this.isEvaluate = isEvaluate;
    }

    public Integer getIsRevoke() {
        return isRevoke;
    }

    public void setIsRevoke(Integer isRevoke) {
        this.isRevoke = isRevoke;
    }

    public BigInteger getIsevaluate() {
        return isevaluate;
    }

    public void setIsevaluate(BigInteger isevaluate) {
        this.isevaluate = isevaluate;
    }

    public BigInteger getIsrevoke() {
        return isrevoke;
    }

    public void setIsrevoke(BigInteger isrevoke) {
        this.isrevoke = isrevoke;
    }

    public String getExceptedWeek() {
        return exceptedWeek;
    }

    public void setExceptedWeek(String exceptedWeek) {
        this.exceptedWeek = exceptedWeek;
    }

    public String getAlternativeWeek() {
        return alternativeWeek;
    }

    public void setAlternativeWeek(String alternativeWeek) {
        this.alternativeWeek = alternativeWeek;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    @Override
    public String toString() {
        return "SupportPage{" +
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
                ", serverDay='" + serverDay + '\'' +
                ", serverTime='" + serverTime + '\'' +
                ", serverReason='" + serverReason + '\'' +
                ", evaluateType=" + evaluateType +
                ", status=" + status +
                ", evaluateContent='" + evaluateContent + '\'' +
                ", evaluateTime='" + evaluateTime + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", serverDateTime='" + serverDateTime + '\'' +
                ", isEvaluate=" + isEvaluate +
                ", isevaluate=" + isevaluate +
                ", isRevoke=" + isRevoke +
                ", isrevoke=" + isrevoke +
                ", exceptedWeek='" + exceptedWeek + '\'' +
                ", alternativeWeek='" + alternativeWeek + '\'' +
                ", dictName='" + dictName + '\'' +
                '}';
    }
}
