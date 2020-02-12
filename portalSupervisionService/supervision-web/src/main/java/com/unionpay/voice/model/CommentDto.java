package com.unionpay.voice.model;

/**
 * 评论分页查询数据实体类
 * @author lishuang
 * @date 2019-05-10
 */
public class CommentDto {
    private String unid;
    private String title;
    private String commentContent;
    private Integer incognito;
    private String commentEmpname;
    private String createTime;
    private Integer commentStatus;

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getIncognito() {
        return incognito;
    }

    public void setIncognito(Integer incognito) {
        this.incognito = incognito;
    }

    public String getCommentEmpname() {
        return commentEmpname;
    }

    public void setCommentEmpname(String commentEmpname) {
        this.commentEmpname = commentEmpname;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "unid=" + unid +
                ", title=" + title +
                ", commentContent=" + commentContent +
                ", incognito=" + incognito +
                ", commentEmpname=" + commentEmpname +
                ", createTime=" + createTime +
                ", commentStatus=" + commentStatus +
                '}';
    }
}
