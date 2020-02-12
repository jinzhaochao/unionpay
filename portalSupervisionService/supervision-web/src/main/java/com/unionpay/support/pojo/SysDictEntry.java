package com.unionpay.support.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: jinzhao
 * @Date: 2019/11/04 18:28
 * @Description:
 */
@Entity
@Table(name="sys_dict_entry")
@NamedQuery(name="SysDictEntry.findAll", query="SELECT s FROM SysDictEntry s")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class SysDictEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="DICTTYPEID")
    private String dictTypeId;


    @Column(name="DICTID")
    private String dictId;

    @Column(name="DICTNAME")
    private String dictName;

    @Column(name="STATUS")
    private Integer status;

    @Column(name="SORTNO")
    private Integer sortNo;

    @Column(name="RANK")
    private Integer rank;

    @Column(name="SEQNO")
    private String seqNo;

    @Column(name="PARENTID")
    private String parentId;

    public SysDictEntry() {
    }

    public SysDictEntry(String dictTypeId, String dictId, String dictName, Integer status, Integer sortNo, Integer rank, String seqNo, String parentId) {
        this.dictTypeId = dictTypeId;
        this.dictId = dictId;
        this.dictName = dictName;
        this.status = status;
        this.sortNo = sortNo;
        this.rank = rank;
        this.seqNo = seqNo;
        this.parentId = parentId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(String dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SysDictEntry{" +
                "dictTypeId='" + dictTypeId + '\'' +
                ", dictId='" + dictId + '\'' +
                ", dictName='" + dictName + '\'' +
                ", status=" + status +
                ", sortNo=" + sortNo +
                ", rank=" + rank +
                ", seqNo='" + seqNo + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}

