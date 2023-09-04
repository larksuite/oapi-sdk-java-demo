package com.larksuite.oapi;

import com.lark.oapi.Client;
import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.core.utils.Lists;
import com.lark.oapi.service.bitable.v1.model.AppTableCreateHeader;
import com.lark.oapi.service.bitable.v1.model.ReqTable;
import com.larksuite.oapi.composite_api.base.Base;
import com.larksuite.oapi.composite_api.base.CreateAppAndTablesRequest;
import org.junit.Test;

public class BaseTest {

    private static final Client client = Client.newBuilder(Config.APP_ID, Config.APP_SECRET).build();

    @Test
    public void TestCreateAppAndTables() throws Exception {
        CreateAppAndTablesRequest req = new CreateAppAndTablesRequest();
        req.setName("这是多维表格1");
        req.setFolderToken("Y9LhfoWNZlKxWcdsf2fcPP0SnXc");
        req.setTables(Lists.newArrayList(
                ReqTable.newBuilder()
                        .name("这是数据表1")
                        .fields(new AppTableCreateHeader[]{AppTableCreateHeader.newBuilder().fieldName("field1").type(1).build()})
                        .build()
        ));

        BaseResponse<?> resp = Base.CreateAppAndTables(client, req);
        System.out.println(Jsons.DEFAULT.toJson(resp));
    }
}
