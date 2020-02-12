package com.unionpay.support.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @Author: jinzhao
 * @Date: 2019/10/30 22:40
 * @Description:  导出预约列表实体类
 */
public class ExcelSupportInfo extends BaseRowModel implements Serializable{
        private static final long serialVersionUID = 1L;


        /*
         * 预约 ID
         */
        //@ExcelProperty(value = { "序号" }, index = 0)
        private Integer id;
        /*
         * 预约编号
         */
        @ExcelProperty(value = {"预约编号"}, index = 1)
        private String number;
        /*
         * 预约人
         */
        @ExcelProperty(value = {"预约人"}, index = 2)
        private String appointmentName;
        /*
         * 状态
         */
        @ExcelProperty(value = {"状态"}, index = 3)
        private String status;
        /*
         * 服务人
         */
        @ExcelProperty(value = {"服务人"}, index = 4)
        private String serverUserName;
        /*
         * 联系电话
         */
        @ExcelProperty(value = {"联系电话"}, index = 5)
        private String officePhone;
        /*
         * 设备类型
         */
        @ExcelProperty(value = {"设备类型"}, index = 6)
        private String questionType;
        /*
         * 预约类型
         */
        @ExcelProperty(value = {"支持类型"}, index = 7)
        private String supportType;

        /*
         * 服务时间
         */
        @ExcelProperty(value = {"服务时间"}, index = 8)
        private String serverDateTime;

        /*
         * 地点
         */
        @ExcelProperty(value = {"地点"}, index = 9)
        private String myPlace;

        /*
         * 详细地点
         */
        @ExcelProperty(value = {"详细地点"}, index = 10)
        private String placeDetail;

        /*
         * 评价类型
         */
        @ExcelProperty(value = {"评价类型"}, index = 11)
        private String evaluateType;

        /*
         * 评价内容
         */
        @ExcelProperty(value = {"评价内容"}, index = 12)
        private String evaluateContent;

        public ExcelSupportInfo() {
        }

        public ExcelSupportInfo(Integer id, String number, String appointmentName, String status, String serverUserName, String officePhone, String questionType, String supportType, String serverDateTime, String myPlace, String placeDetail, String evaluateType, String evaluateContent) {
                this.id = id;
                this.number = number;
                this.appointmentName = appointmentName;
                this.status = status;
                this.serverUserName = serverUserName;
                this.officePhone = officePhone;
                this.questionType = questionType;
                this.supportType = supportType;
                this.serverDateTime = serverDateTime;
                this.myPlace = myPlace;
                this.placeDetail = placeDetail;
                this.evaluateType = evaluateType;
                this.evaluateContent = evaluateContent;
        }

        public static long getSerialVersionUID() {
                return serialVersionUID;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getNumber() {
                return number;
        }

        public void setNumber(String number) {
                this.number = number;
        }

        public String getAppointmentName() {
                return appointmentName;
        }

        public void setAppointmentName(String appointmentName) {
                this.appointmentName = appointmentName;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public String getServerUserName() {
                return serverUserName;
        }

        public void setServerUserName(String serverUserName) {
                this.serverUserName = serverUserName;
        }

        public String getOfficePhone() {
                return officePhone;
        }

        public void setOfficePhone(String officePhone) {
                this.officePhone = officePhone;
        }

        public String getQuestionType() {
                return questionType;
        }

        public void setQuestionType(String questionType) {
                this.questionType = questionType;
        }

        public String getSupportType() {
                return supportType;
        }

        public void setSupportType(String supportType) {
                this.supportType = supportType;
        }

        public String getServerDateTime() {
                return serverDateTime;
        }

        public void setServerDateTime(String serverDateTime) {
                this.serverDateTime = serverDateTime;
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

        public String getEvaluateType() {
                return evaluateType;
        }

        public void setEvaluateType(String evaluateType) {
                this.evaluateType = evaluateType;
        }

        public String getEvaluateContent() {
                return evaluateContent;
        }

        public void setEvaluateContent(String evaluateContent) {
                this.evaluateContent = evaluateContent;
        }

        @Override
        public String toString() {
                return "ExcelSupportInfo{" +
                        "id=" + id +
                        ", number='" + number + '\'' +
                        ", appointmentName='" + appointmentName + '\'' +
                        ", status='" + status + '\'' +
                        ", serverUserName='" + serverUserName + '\'' +
                        ", officePhone='" + officePhone + '\'' +
                        ", questionType='" + questionType + '\'' +
                        ", supportType='" + supportType + '\'' +
                        ", serverDateTime='" + serverDateTime + '\'' +
                        ", myPlace='" + myPlace + '\'' +
                        ", placeDetail='" + placeDetail + '\'' +
                        ", evaluateType='" + evaluateType + '\'' +
                        ", evaluateContent='" + evaluateContent + '\'' +
                        '}';
        }
}
