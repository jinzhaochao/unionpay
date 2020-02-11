package com.unionpay.pager.dto;


public class fileInfo {
    private String fileName;
    private String fileId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "fileInfo{" +
                "fileName='" + fileName + '\'' +
                ", fileId='" + fileId + '\'' +
                '}';
    }
}
