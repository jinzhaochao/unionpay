package com.unionpay.support.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: jinzhao
 * @Date: 2019/11/04 16:59
 * @Description:
 */
@Entity
@Table(name="support_question")
@NamedQuery(name="SupportQuestion.findAll", query="SELECT s FROM SupportQuestion s")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class SupportQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //对应sys_dict_entry的DICTID
    @Column(name="type_id")
    private String typeId;

    //类型名字
    @Column(name="type_name")
    private String typeName;

    //问题名称
    @Column(name="question_name")
    private String questionName;

    //内容
    private String content;

    //链接路径
    private String url;

    public SupportQuestion() {
    }

    public SupportQuestion(String typeId, String typeName, String questionName, String content, String url) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.questionName = questionName;
        this.content = content;
        this.url = url;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SupportQuestion{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", questionName='" + questionName + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
