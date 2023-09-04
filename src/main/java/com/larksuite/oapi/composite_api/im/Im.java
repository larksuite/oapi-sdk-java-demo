package com.larksuite.oapi.composite_api.im;

import com.lark.oapi.Client;
import com.lark.oapi.core.response.BaseResponse;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.im.v1.model.*;


public class Im {

    /**
     * 发送文件消息，使用到两个OpenAPI：
     * 1. [上传文件](<a href="https://open.feishu.cn/document/server-docs/im-v1/file/create">...</a>)
     * 2. [发送消息](<a href="https://open.feishu.cn/document/server-docs/im-v1/message/create">...</a>)
     */
    public static BaseResponse<?> sendFile(Client client, SendFileRequest request) throws Exception {
        // 上传文件
        CreateFileReq createFileReq = CreateFileReq.newBuilder()
                .createFileReqBody(CreateFileReqBody.newBuilder()
                        .fileType(request.getFileType())
                        .fileName(request.getFileName())
                        .duration(request.getDuration())
                        .file(request.getFile())
                        .build())
                .build();

        CreateFileResp createFileResp = client.im().file().create(createFileReq);
        if (!createFileResp.success()) {
            System.out.printf("client.im.file.create failed, code: %d, msg: %s, logId: %s%n",
                    createFileResp.getCode(), createFileResp.getMsg(), createFileResp.getRequestId());
            return createFileResp;
        }

        // 发送消息
        CreateMessageReq createMessageReq = CreateMessageReq.newBuilder()
                .receiveIdType(request.getReceiveIdType())
                .createMessageReqBody(CreateMessageReqBody.newBuilder()
                        .receiveId(request.getReceiveId())
                        .msgType("file")
                        .content(Jsons.DEFAULT.toJson(createFileResp.getData()))
                        .uuid(request.getUuid())
                        .build())
                .build();

        CreateMessageResp createMessageResp = client.im().message().create(createMessageReq);
        if (!createMessageResp.success()) {
            System.out.printf("client.im.message.create failed, code: %d, msg: %s, logId: %s%n",
                    createMessageResp.getCode(), createMessageResp.getMsg(), createMessageResp.getRequestId());
            return createMessageResp;
        }

        // 返回结果
        SendFileResponse response = new SendFileResponse();
        response.setCode(0);
        response.setMsg("success");
        response.setCreateFileResponse(createFileResp.getData());
        response.setCreateMessageResponse(createMessageResp.getData());

        return response;
    }

    /**
     * 发送图片消息，使用到两个OpenAPI：
     * 1. [上传图片](<a href="https://open.feishu.cn/document/server-docs/im-v1/image/create">...</a>)
     * 2. [发送消息](<a href="https://open.feishu.cn/document/server-docs/im-v1/message/create">...</a>)
     */
    public static BaseResponse<?> sendImage(Client client, SendImageRequest request) throws Exception {
        // 上传图片
        CreateImageReq req = CreateImageReq.newBuilder()
                .createImageReqBody(CreateImageReqBody.newBuilder()
                        .imageType("message")
                        .image(request.getImage())
                        .build())
                .build();

        CreateImageResp createImageResp = client.im().image().create(req);
        if (!createImageResp.success()) {
            System.out.printf("client.im.image.create failed, code: %d, msg: %s, logId: %s%n",
                    createImageResp.getCode(), createImageResp.getMsg(), createImageResp.getRequestId());
            return createImageResp;
        }

        // 发送消息
        CreateMessageReq createMessageReq = CreateMessageReq.newBuilder()
                .receiveIdType(request.getReceiveIdType())
                .createMessageReqBody(CreateMessageReqBody.newBuilder()
                        .receiveId(request.getReceiveId())
                        .msgType("image")
                        .content(Jsons.DEFAULT.toJson(createImageResp.getData()))
                        .uuid(request.getUuid())
                        .build())
                .build();

        CreateMessageResp createMessageResp = client.im().message().create(createMessageReq);
        if (!createMessageResp.success()) {
            System.out.printf("client.im.message.create failed, code: %d, msg: %s, logId: %s%n",
                    createMessageResp.getCode(), createMessageResp.getMsg(), createMessageResp.getRequestId());
            return createMessageResp;
        }

        // 返回结果
        SendImageResponse response = new SendImageResponse();
        response.setCode(0);
        response.setMsg("success");
        response.setCreateImageResponse(createImageResp.getData());
        response.setCreateMessageResponse(createMessageResp.getData());

        return response;
    }
}
