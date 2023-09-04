package com.larksuite.oapi.composite_api.sheets;

import com.lark.oapi.Client;
import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.core.response.RawResponse;
import com.lark.oapi.core.token.AccessTokenType;
import com.lark.oapi.core.utils.Jsons;

import java.util.HashMap;
import java.util.Map;


public class Sheets {

    public static BaseResponse<?> CopyAndPasteByRange(Client client, CopyAndPasteByRangeRequest request) throws Exception {
        // 读取单个范围
        RawResponse readResp = client.get(
                String.format("/open-apis/sheets/v2/spreadsheets/%s/values/%s", request.getSpreadsheetToken(), request.getScrRange()),
                null,
                AccessTokenType.Tenant);

        SpreadsheetResp readSpreadsheetResp = Jsons.DEFAULT.fromJson(new String(readResp.getBody()), SpreadsheetResp.class);
        if (!readSpreadsheetResp.success()) {
            System.out.printf("read spreadsheet failed, code: %d, msg: %s, logId: %s%n",
                    readSpreadsheetResp.getCode(), readSpreadsheetResp.getMsg(), readSpreadsheetResp.getRequestId());
            return readSpreadsheetResp;
        }

        // 向单个范围写入数据
        Map<String, Object> valueRange = new HashMap<>();
        valueRange.put("range", request.getDstRange());
        valueRange.put("values", readSpreadsheetResp.getData().getValueRange().getValues());
        Map<String, Object> body = new HashMap<>();
        body.put("valueRange", valueRange);

        RawResponse writeResp = client.put(
                String.format("/open-apis/sheets/v2/spreadsheets/%s/values", request.getSpreadsheetToken()),
                body,
                AccessTokenType.Tenant);

        SpreadsheetResp writeSpreadsheetResp = Jsons.DEFAULT.fromJson(new String(writeResp.getBody()), SpreadsheetResp.class);
        if (!writeSpreadsheetResp.success()) {
            System.out.printf("write spreadsheet failed, code: %d, msg: %s, logId: %s%n",
                    writeSpreadsheetResp.getCode(), writeSpreadsheetResp.getMsg(), writeSpreadsheetResp.getRequestId());
            return writeSpreadsheetResp;
        }

        // 返回结果
        CopyAndPasteRangeResponse response = new CopyAndPasteRangeResponse();
        response.setCode(0);
        response.setMsg("success");
        response.setReadResponse(readSpreadsheetResp.getData());
        response.setWriteResponse(writeSpreadsheetResp.getData());

        return response;
    }
}
