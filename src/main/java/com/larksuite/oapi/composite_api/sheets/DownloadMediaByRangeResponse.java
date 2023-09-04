package com.larksuite.oapi.composite_api.sheets;

import com.lark.oapi.service.drive.v1.model.DownloadMediaResp;

import java.util.List;
import java.util.Map;

public class DownloadMediaByRangeResponse {
    private Map<String, Object> readResponse;
    private List<DownloadMediaResp> downloadMediaResponse;

    public Map<String, Object> getReadResponse() {
        return readResponse;
    }

    public void setReadResponse(Map<String, Object> readResponse) {
        this.readResponse = readResponse;
    }

    public List<DownloadMediaResp> getDownloadMediaResponse() {
        return downloadMediaResponse;
    }

    public void setDownloadMediaResponse(List<DownloadMediaResp> downloadMediaResponse) {
        this.downloadMediaResponse = downloadMediaResponse;
    }
}
