package com.larksuite.oapi.composite_api.im;

import java.io.File;

public class SendImageRequest {
    private File image;
    private String receiveIdType;
    private String receiveId;
    private String uuid;

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
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
