package com.larksuite.oapi;

import com.lark.oapi.Client;
import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.core.utils.Jsons;
import com.larksuite.oapi.composite_api.im.Im;
import com.larksuite.oapi.composite_api.im.SendFileRequest;
import com.larksuite.oapi.composite_api.im.SendImageRequest;
import org.junit.Test;

import java.io.File;

public class ImTest {

    private static final Client client = Client.newBuilder(Config.APP_ID, Config.APP_SECRET).build();

    @Test
    public void TestSendFile() throws Exception {
        SendFileRequest req = new SendFileRequest();
        req.setFileType("pdf");
        req.setFileName("demo.pdf");
        req.setFile(new File("/Users/bytedance/Desktop/demo.pdf"));
        req.setReceiveIdType("open_id");
        req.setReceiveId("ou_a79a0f82add14976e3943f4deb17c3fa");

        BaseResponse<?> resp = Im.sendFile(client, req);
        System.out.println(Jsons.DEFAULT.toJson(resp));
    }

    @Test
    public void TestSendImage() throws Exception {
        SendImageRequest req = new SendImageRequest();
        req.setImage(new File("/Users/bytedance/Desktop/demo.png"));
        req.setReceiveIdType("open_id");
        req.setReceiveId("ou_a79a0f82add14976e3943f4deb17c3fa");

        BaseResponse<?> resp = Im.sendImage(client, req);
        System.out.println(Jsons.DEFAULT.toJson(resp));
    }
}
