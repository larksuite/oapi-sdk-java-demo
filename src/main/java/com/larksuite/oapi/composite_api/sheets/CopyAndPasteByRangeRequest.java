package com.larksuite.oapi.composite_api.sheets;

public class CopyAndPasteByRangeRequest {
    private String spreadsheetToken;
    private String srcRange;
    private String dstRange;

    public String getSpreadsheetToken() {
        return spreadsheetToken;
    }

    public void setSpreadsheetToken(String spreadsheetToken) {
        this.spreadsheetToken = spreadsheetToken;
    }

    public String getSrcRange() {
        return srcRange;
    }

    public void setSrcRange(String srcRange) {
        this.srcRange = srcRange;
    }

    public String getDstRange() {
        return dstRange;
    }

    public void setDstRange(String dstRange) {
        this.dstRange = dstRange;
    }
}
