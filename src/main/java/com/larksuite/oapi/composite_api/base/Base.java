package com.larksuite.oapi.composite_api.base;

import com.lark.oapi.Client;
import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.service.bitable.v1.model.*;

import java.util.ArrayList;
import java.util.List;


public class Base {

    /**
     * 创建多维表格同时添加数据表，使用到两个OpenAPI：
     * 1. [创建多维表格](<a href="https://open.feishu.cn/document/server-docs/docs/bitable-v1/app/create">...</a>)
     * 2. [新增一个数据表](<a href="https://open.feishu.cn/document/server-docs/docs/bitable-v1/app-table/create">...</a>)
     */
    public static BaseResponse<?> CreateAppAndTables(Client client, CreateAppAndTablesRequest request) throws Exception {
        // 创建多维表格
        CreateAppReq createAppReq = CreateAppReq.newBuilder()
                .reqApp(ReqApp.newBuilder()
                        .name(request.getName())
                        .folderToken(request.getFolderToken())
                        .build())
                .build();

        CreateAppResp createAppResp = client.bitable().app().create(createAppReq);

        if (!createAppResp.success()) {
            System.out.printf("client.bitable.app.create failed, code: %d, msg: %s, logId: %s%n",
                    createAppResp.getCode(), createAppResp.getMsg(), createAppResp.getRequestId());
            return createAppResp;
        }

        // 添加数据表
        List<CreateAppTableRespBody> tables = new ArrayList<>();
        for (ReqTable table : request.getTables()) {
            CreateAppTableReq createAppTableReq = CreateAppTableReq.newBuilder()
                    .appToken(createAppResp.getData().getApp().getAppToken())
                    .createAppTableReqBody(CreateAppTableReqBody.newBuilder()
                            .table(table)
                            .build())
                    .build();

            CreateAppTableResp createAppTableResp = client.bitable().appTable().create(createAppTableReq);

            if (!createAppTableResp.success()) {
                System.out.printf("client.bitable.appTable.create failed, code: %d, msg: %s, logId: %s%n",
                        createAppTableResp.getCode(), createAppTableResp.getMsg(), createAppTableResp.getRequestId());
                return createAppTableResp;
            }

            tables.add(createAppTableResp.getData());
        }

        // 返回结果
        CreateAppAndTablesResponse response = new CreateAppAndTablesResponse();
        response.setCode(0);
        response.setMsg("success");
        response.setCreateAppResponse(createAppResp.getData());
        response.setCreateAppTableResponse(tables);

        return response;
    }
}
