package com.unionpay.services.model;


import java.math.BigInteger;
import java.util.List;


/**
 * @Author: jinzhao
 * @Date: 2019/08/30 16:59
 * @Description:
 */
public class ServerSuggestAndGiveLikeModel  {
    //private static final long serialVersionUID = 1L;
    private Integer id;

    /**
     * 关联服务id
     */
    private Integer serverId;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 服务标题
     */
    private String title;

    /**
     * 意见（服务描述，咨询描述，投诉描述）
     */
    private String otherDescribe;

    /**
     * 提交人id
     */
    private Integer empId;
    /**
     提交人名字
     */
    private String empName;

    /**
     提交人的部门
     */
    private String empDeptName;

    /**
     提交人部门Id
     */
    private Integer empDeptId;

    /**
     提交人的科室Id
     */
    private Integer empOrgId;

    /**
     * 处理状态  0：待处理   2：已处理
     */
    private Integer status;

    /**
     * 处理人empid
     */
    private Integer replyId;

    /**
     * 处理人名称
     */
    private String replyName;

    /**
     * 回复时间
     */
    private String replyTime;

    /**
     * 回复原因
     */
    private String replyReason;

    /**
        反馈途径类型：1服务反馈，2服务建议，3意见反馈
     */
    private Integer type;

    /**
        是否转交：0否，1是
     */
    private Integer isDeliver;

    /**
       办理时长
     */
    private String timeLength;

//    /**
//      点赞id
//     */
//    private long likeId;
//
//    /**
//      点赞人id
//     */
//    private String likeUserId;

    /**
      点赞状态  0:取消点赞,1:点赞
     */
    private BigInteger likeStatus;
    /**
     点赞状态  0:取消点赞,1:点赞
     */
    private Integer giveLikeStatus;

    /**
       点赞数
     */
    private BigInteger giveLikeCount;

    /**
       是否可以点赞  0:不可点赞,1:可点赞
     */
    private Integer isGiveLike;
    /**
     是否可以点赞  0:不可点赞,1:可点赞
     */
    private BigInteger isLike;

    /**
     * replyUserid 转递人userid
     */
    private String replyUserid;

    //转递日志
    private List<ServerDeliver> deliverList;


    public ServerSuggestAndGiveLikeModel() {
    }

    public ServerSuggestAndGiveLikeModel(Integer id, Integer serverId, String createtime, String title, String otherDescribe, Integer empId, String empName, String empDeptName, Integer empDeptId, Integer empOrgId, Integer status, Integer replyId, String replyName, String replyTime, String replyReason, Integer type, Integer isDeliver, String timeLength, BigInteger likeStatus, Integer giveLikeStatus, BigInteger giveLikeCount, Integer isGiveLike, BigInteger isLike, String replyUserid, List<ServerDeliver> deliverList) {
        this.id = id;
        this.serverId = serverId;
        this.createtime = createtime;
        this.title = title;
        this.otherDescribe = otherDescribe;
        this.empId = empId;
        this.empName = empName;
        this.empDeptName = empDeptName;
        this.empDeptId = empDeptId;
        this.empOrgId = empOrgId;
        this.status = status;
        this.replyId = replyId;
        this.replyName = replyName;
        this.replyTime = replyTime;
        this.replyReason = replyReason;
        this.type = type;
        this.isDeliver = isDeliver;
        this.timeLength = timeLength;
        this.likeStatus = likeStatus;
        this.giveLikeStatus = giveLikeStatus;
        this.giveLikeCount = giveLikeCount;
        this.isGiveLike = isGiveLike;
        this.isLike = isLike;
        this.replyUserid = replyUserid;
        this.deliverList = deliverList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOtherDescribe() {
        return otherDescribe;
    }

    public void setOtherDescribe(String otherDescribe) {
        this.otherDescribe = otherDescribe;
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

    public String getEmpDeptName() {
        return empDeptName;
    }

    public void setEmpDeptName(String empDeptName) {
        this.empDeptName = empDeptName;
    }

    public Integer getEmpDeptId() {
        return empDeptId;
    }

    public void setEmpDeptId(Integer empDeptId) {
        this.empDeptId = empDeptId;
    }

    public Integer getEmpOrgId() {
        return empOrgId;
    }

    public void setEmpOrgId(Integer empOrgId) {
        this.empOrgId = empOrgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getReplyReason() {
        return replyReason;
    }

    public void setReplyReason(String replyReason) {
        this.replyReason = replyReason;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(Integer isDeliver) {
        this.isDeliver = isDeliver;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    public BigInteger getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(BigInteger likeStatus) {
        this.likeStatus = likeStatus;
    }

    public BigInteger getGiveLikeCount() {
        return giveLikeCount;
    }

    public void setGiveLikeCount(BigInteger giveLikeCount) {
        this.giveLikeCount = giveLikeCount;
    }

    public Integer getIsGiveLike() {
        return isGiveLike;
    }

    public void setIsGiveLike(Integer isGiveLike) {
        this.isGiveLike = isGiveLike;
    }

    public BigInteger getIsLike() {
        return isLike;
    }

    public void setIsLike(BigInteger isLike) {
        this.isLike = isLike;
    }

    public Integer getGiveLikeStatus() {
        return giveLikeStatus;
    }

    public void setGiveLikeStatus(Integer giveLikeStatus) {
        this.giveLikeStatus = giveLikeStatus;
    }

    public String getReplyUserid() {
        return replyUserid;
    }

    public void setReplyUserid(String replyUserid) {
        this.replyUserid = replyUserid;
    }

    public List<ServerDeliver> getDeliverList() {
        return deliverList;
    }

    public void setDeliverList(List<ServerDeliver> deliverList) {
        this.deliverList = deliverList;
    }

    @Override
    public String toString() {
        return "ServerSuggestAndGiveLikeModel{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", createtime='" + createtime + '\'' +
                ", title='" + title + '\'' +
                ", otherDescribe='" + otherDescribe + '\'' +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empDeptName='" + empDeptName + '\'' +
                ", empDeptId=" + empDeptId +
                ", empOrgId=" + empOrgId +
                ", status=" + status +
                ", replyId=" + replyId +
                ", replyName='" + replyName + '\'' +
                ", replyTime='" + replyTime + '\'' +
                ", replyReason='" + replyReason + '\'' +
                ", type=" + type +
                ", isDeliver=" + isDeliver +
                ", timeLength='" + timeLength + '\'' +
                ", likeStatus=" + likeStatus +
                ", giveLikeStatus=" + giveLikeStatus +
                ", giveLikeCount=" + giveLikeCount +
                ", isGiveLike=" + isGiveLike +
                ", isLike=" + isLike +
                ", replyUserid='" + replyUserid + '\'' +
                ", deliverList=" + deliverList +
                '}';
    }
}
