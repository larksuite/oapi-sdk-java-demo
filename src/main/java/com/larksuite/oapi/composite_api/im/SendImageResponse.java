package com.larksuite.oapi.composite_api.im;

import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.service.im.v1.model.CreateFileRespBody;
import com.lark.oapi.service.im.v1.model.CreateImageRespBody;
import com.lark.oapi.service.im.v1.model.CreateMessageRespBody;

public class SendImageResponse extends BaseResponse<Void> {
    private CreateImageRespBody createImageResponse;
    private CreateMessageRespBody createMessageResponse;

    public CreateImageRespBody getCreateImageResponse() {
        return createImageResponse;
    }

    public void setCreateImageResponse(CreateImageRespBody createImageResponse) {
        this.createImageResponse = createImageResponse;
    }

    public CreateMessageRespBody getCreateMessageResponse() {
        return createMessageResponse;
    }

    public void setCreateMessageResponse(CreateMessageRespBody createMessageResponse) {
        this.createMessageResponse = createMessageResponse;
    }
}
