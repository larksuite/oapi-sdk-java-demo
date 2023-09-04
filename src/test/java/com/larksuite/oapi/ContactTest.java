package com.larksuite.oapi;

import com.lark.oapi.Client;
import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.core.utils.Jsons;
import com.larksuite.oapi.composite_api.contact.Contact;
import com.larksuite.oapi.composite_api.contact.ListUserByDepartmentRequest;
import org.junit.Test;

public class ContactTest {

    private static final Client client = Client.newBuilder(Config.APP_ID, Config.APP_SECRET).build();

    @Test
    public void TestListUserByDepartment() throws Exception {
        ListUserByDepartmentRequest req = new ListUserByDepartmentRequest();
        req.setDepartmentId("0");

        BaseResponse<?> resp = Contact.ListUserByDepartment(client, req);
        System.out.println(Jsons.DEFAULT.toJson(resp));
    }
}
