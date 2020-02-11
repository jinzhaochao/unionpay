package com.unionpay.supervision.domain;

import javax.validation.constraints.NotEmpty;

/**
 * @Author jinzhao
 * @Date 2019-10-10
 * 督办分页请求参数实体
 */
public class SupervisePageModel {
    @NotEmpty
    private String type;
    private String deptId;
    private String branchedLeader;
    private String createTimeStart;
    private String createTimeEnd;
    private Integer size;
    private Integer currentPage;

    public SupervisePageModel() {
    }

    public SupervisePageModel(String type, String deptId, String branchedLeader, String createTimeStart, String createTimeEnd, Integer size, Integer currentPage) {
        this.type = type;
        this.deptId = deptId;
        this.branchedLeader = branchedLeader;
        this.createTimeStart = createTimeStart;
        this.createTimeEnd = createTimeEnd;
        this.size = size;
        this.currentPage = currentPage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getBranchedLeader() {
        return branchedLeader;
    }

    public void setBranchedLeader(String branchedLeader) {
        this.branchedLeader = branchedLeader;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
