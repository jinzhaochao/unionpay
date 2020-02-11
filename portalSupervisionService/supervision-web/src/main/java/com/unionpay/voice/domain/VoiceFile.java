package com.unionpay.voice.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "voice_file")
@NamedQuery(name="VoiceFile.findAll", query="SELECT v FROM VoiceFile v")
public class VoiceFile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "unid")
    private String unid;
    @Column(name = "info_unid")
    private String infoUnid;
    @Column(name = "file_type")
    private Integer fileType;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_store_name")
    private String fileStoreName;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "create_userid")
    private String createUserid;
    @Column(name = "create_time")
    private String createTime;

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getInfoUnid() {
        return infoUnid;
    }

    public void setInfoUnid(String infoUnid) {
        this.infoUnid = infoUnid;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileStoreName() {
        return fileStoreName;
    }

    public void setFileStoreName(String fileStoreName) {
        this.fileStoreName = fileStoreName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "VoiceFile{" +
                "unid=" + unid +
                ", infoUnid=" + infoUnid +
                ", fileType=" + fileType +
                ", fileName=" + fileName +
                ", fileStoreName=" + fileStoreName +
                ", filePath=" + filePath +
                ", createUserid=" + createUserid +
                ", createTime=" + createTime +
                '}';
    }
}
