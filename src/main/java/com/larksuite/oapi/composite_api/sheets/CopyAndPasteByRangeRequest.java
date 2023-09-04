package com.larksuite.oapi.composite_api.sheets;

public class CopyAndPasteByRangeRequest {
    private String spreadsheetToken;
    private String scrRange;
    private String dstRange;

    public String getSpreadsheetToken() {
        return spreadsheetToken;
    }

    public void setSpreadsheetToken(String spreadsheetToken) {
        this.spreadsheetToken = spreadsheetToken;
    }

    public String getScrRange() {
        return scrRange;
    }

    public void setScrRange(String scrRange) {
        this.scrRange = scrRange;
    }

    public String getDstRange() {
        return dstRange;
    }

    public void setDstRange(String dstRange) {
        this.dstRange = dstRange;
    }
}
