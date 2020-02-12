package com.unionpay.voice.model;

/**
 * 页面显示附件信息实体类
 * @author lishuang
 * @date 2019-05-09
 */
public class FileModel {
    private String unid;
    private String fileName;

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public String toString() {
        return "FileModel{" +
                "unid=" + unid + 
                ", fileName=" + fileName +
                '}';
    }
}
