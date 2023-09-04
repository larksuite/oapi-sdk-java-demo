package com.larksuite.oapi.composite_api.base;

import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.service.bitable.v1.model.CreateAppRespBody;
import com.lark.oapi.service.bitable.v1.model.CreateAppTableRespBody;

import java.util.List;

public class CreateAppAndTablesResponse extends BaseResponse<Void> {
    private CreateAppRespBody createAppResponse;
    private List<CreateAppTableRespBody> createAppTableResponse;

    public CreateAppRespBody getCreateAppResponse() {
        return createAppResponse;
    }

    public void setCreateAppResponse(CreateAppRespBody createAppResponse) {
        this.createAppResponse = createAppResponse;
    }

    public List<CreateAppTableRespBody> getCreateAppTableResponse() {
        return createAppTableResponse;
    }

    public void setCreateAppTableResponse(List<CreateAppTableRespBody> createAppTableResponse) {
        this.createAppTableResponse = createAppTableResponse;
    }
}
