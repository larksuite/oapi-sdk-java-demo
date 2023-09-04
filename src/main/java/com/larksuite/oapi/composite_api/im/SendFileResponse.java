package com.larksuite.oapi.composite_api.im;

import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.service.im.v1.model.CreateFileResp;
import com.lark.oapi.service.im.v1.model.CreateFileRespBody;
import com.lark.oapi.service.im.v1.model.CreateMessageRespBody;

public class SendFileResponse extends BaseResponse<Void> {
    private CreateFileRespBody createFileResponse;
    private CreateMessageRespBody createMessageResponse;

    public CreateFileRespBody getCreateFileResponse() {
        return createFileResponse;
    }

    public void setCreateFileResponse(CreateFileRespBody createFileResponse) {
        this.createFileResponse = createFileResponse;
    }

    public CreateMessageRespBody getCreateMessageResponse() {
        return createMessageResponse;
    }

    public void setCreateMessageResponse(CreateMessageRespBody createMessageResponse) {
        this.createMessageResponse = createMessageResponse;
    }
}
