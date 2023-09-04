package com.larksuite.oapi.composite_api.im;

import java.io.File;

public class SendFileRequest {
    private String fileType;
    private String FileName;
    private File file;
    private Integer duration;
    private String receiveIdType;
    private String receiveId;
    private String uuid;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getReceiveIdType() {
        return receiveIdType;
    }

    public void setReceiveIdType(String receiveIdType) {
        this.receiveIdType = receiveIdType;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
