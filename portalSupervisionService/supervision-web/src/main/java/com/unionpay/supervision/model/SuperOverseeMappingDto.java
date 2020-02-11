package com.unionpay.supervision.model;

/**
 * 督办事项查询，修改事项，督办事项与督办类型关联实体类
 * @author lishuang
 * @date 2019-04-09
 */
public class SuperOverseeMappingDto {
    //督办事项与督办类型关联表,主键
    private String unid;
    // 督办类型名称
    private String overseeName;
    // 事项名称(批示文件名称,出访活动,会议名称)
    private String serviceName;
    //来源时间（创建时间）
    private String createTime;

    public SuperOverseeMappingDto() {
    }

    public SuperOverseeMappingDto(String unid, String overseeName, String serviceName, String createTime) {
        this.unid = unid;
        this.overseeName = overseeName;
        this.serviceName = serviceName;
        this.createTime = createTime;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getOverseeName() {
        return overseeName;
    }

    public void setOverseeName(String overseeName) {
        this.overseeName = overseeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SuperOverseeMappingDto{" +
                "unid='" + unid +
                ", overseeName='" + overseeName +
                ", serviceName='" + serviceName +
                ", createTime='" + createTime +
                '}';
    }
}
