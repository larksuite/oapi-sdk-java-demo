package com.larksuite.oapi;

import com.lark.oapi.Client;
import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.core.utils.Jsons;
import com.larksuite.oapi.composite_api.sheets.CopyAndPasteByRangeRequest;
import com.larksuite.oapi.composite_api.sheets.Sheets;
import org.junit.Test;

public class SheetsTest {
    private static final Client client = Client.newBuilder(Config.APP_ID, Config.APP_SECRET).build();

    @Test
    public void TestCopyAndPasteByRange() throws Exception {
        CopyAndPasteByRangeRequest req = new CopyAndPasteByRangeRequest();
        req.setSpreadsheetToken("T90VsUqrYhrnGCtBKS3cLCgQnih");
        req.setScrRange("53988e!A1:B5");
        req.setDstRange("53988e!C1:D5");

        BaseResponse<?> resp = Sheets.CopyAndPasteByRange(client, req);
        System.out.println(Jsons.DEFAULT.toJson(resp));
    }
}
