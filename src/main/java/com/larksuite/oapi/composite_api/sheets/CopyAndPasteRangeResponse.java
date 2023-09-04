package com.larksuite.oapi.composite_api.sheets;

import com.lark.oapi.core.response.BaseResponse;

import java.util.Map;

public class CopyAndPasteRangeResponse extends BaseResponse {
    private SpreadsheetRespBody readResponse;
    private SpreadsheetRespBody writeResponse;

    public SpreadsheetRespBody getReadResponse() {
        return readResponse;
    }

    public void setReadResponse(SpreadsheetRespBody readResponse) {
        this.readResponse = readResponse;
    }

    public SpreadsheetRespBody getWriteResponse() {
        return writeResponse;
    }

    public void setWriteResponse(SpreadsheetRespBody writeResponse) {
        this.writeResponse = writeResponse;
    }
}
