package com.unionpay.supervision.model;
/**
 * 推送至流程平台的附件
 *
 */
public class FileTolc {
    //附件id
    private String fileId;
    //附件名称
    private String fileName;
    //附件下载地址
    private String fileUrl;

    public FileTolc() {
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId='" + fileId +
                ", fileName='" + fileName +
                ", fileUrl='" + fileUrl +
                '}';
    }
}
