package com.larksuite.oapi.composite_api.sheets;

public class DownloadMediaByRangeRequest {
    private String spreadsheetToken;
    private String range;

    public String getSpreadsheetToken() {
        return spreadsheetToken;
    }

    public void setSpreadsheetToken(String spreadsheetToken) {
        this.spreadsheetToken = spreadsheetToken;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
