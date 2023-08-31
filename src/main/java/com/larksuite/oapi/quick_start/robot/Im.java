package com.larksuite.oapi.quick_start.robot;

import com.lark.oapi.Client;
import com.lark.oapi.service.im.v1.model.*;
import com.larksuite.oapi.Config;

import java.io.File;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Im {

    private static final String[] USER_OPEN_IDS = {"ou_a79a0f82add14976e3943f4deb17c3fa", "ou_33c76a4cbeb76bd66608706edb32508e"};

    private static final Client client = Client.newBuilder(Config.APP_ID, Config.APP_SECRET).build();

    // 获取会话历史消息
    public static void ListChatHistory(String chatId) throws Exception {
        ListMessageReq req = ListMessageReq.newBuilder()
                .containerIdType("chat")
                .containerId(chatId)
                .build();

        ListMessageResp resp = client.im().message().list(req);

        if (!resp.success()) {
            throw new Exception(String.format("client.im.message.list failed, code: %d, msg: %s, logId: %s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId()));
        }

        File file = new File("./src/main/java/com/larksuite/oapi/quick_start/robot/chat_history.txt");
        FileWriter writer = new FileWriter(file);
        for (Message item : resp.getData().getItems()) {
            String senderId = item.getSender().getId();
            String content = item.getBody().getContent();
            String createTime = item.getCreateTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            createTime = sdf.format(new Date(Long.parseLong(createTime)));
            writer.write(String.format("chatter(%s) at (%s) send: %s\n", senderId, createTime, content));
        }
        writer.close();
    }

    // 创建报警群并拉人入群
    public static String CreateAlertChat() throws Exception {
        CreateChatReq req = CreateChatReq.newBuilder()
                .userIdType("open_id")
                .createChatReqBody(CreateChatReqBody.newBuilder()
                        .name("P0: 线上事故处理")
                        .description("线上紧急事故处理")
                        .userIdList(USER_OPEN_IDS)
                        .build())
                .build();

        CreateChatResp resp = client.im().chat().create(req);

        if (!resp.success()) {
            throw new Exception(String.format("client.im.chat.create failed, code: %d, msg: %s, logId: %s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId()));
        }

        return resp.getData().getChatId();
    }

    // 发送报警消息
    public static void SendAlertMessage(String chatId) throws Exception {
        SendMessage(chatId, "interactive", BuildCard("跟进处理"));
    }

    // 发送报警消息
    public static void SendMessage(String chatId, String msgType, String content) throws Exception {
        CreateMessageReq req = CreateMessageReq.newBuilder()
                .receiveIdType("chat_id")
                .createMessageReqBody(CreateMessageReqBody.newBuilder()
                        .receiveId(chatId)
                        .msgType(msgType)
                        .content(content)
                        .build())
                .build();

        CreateMessageResp resp = client.im().message().create(req);

        if (!resp.success()) {
            throw new Exception(String.format("client.im.message.create failed, code: %d, msg: %s, logId: %s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId()));
        }
    }

    private static String UploadImage() throws Exception {
        File file = new File("./src/main/java/com/larksuite/oapi/quick_start/robot/alert.png");
        CreateImageReq req = CreateImageReq.newBuilder()
                .createImageReqBody(CreateImageReqBody.newBuilder()
                        .imageType("message")
                        .image(file)
                        .build())
                .build();

        CreateImageResp resp = client.im().image().create(req);

        if (!resp.success()) {
            throw new Exception(String.format("client.im.image.create failed, code: %d, msg: %s, logId: %s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId()));
        }

        return resp.getData().getImageKey();
    }

    // 构建卡片
    public static String BuildCard(String buttonName) throws Exception {
        // 上传图片
        String imageKey = UploadImage();

        // 获取卡片结构
        File jsonFile = new File("./src/main/java/com/larksuite/oapi/quick_start/robot/card.json");
        FileReader fileReader = new FileReader(jsonFile);
        Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
        int ch = 0;
        StringBuilder sb = new StringBuilder();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        String card = sb.toString();

        card = card.replace("${image_key}", imageKey);
        card = card.replace("${button_name}", buttonName);

        return card;
    }

    // 获取会话信息
    public static GetChatRespBody GetChatInfo(String chatId) throws Exception {
        GetChatReq req = GetChatReq.newBuilder()
                .chatId(chatId)
                .build();

        GetChatResp resp = client.im().chat().get(req);

        if (!resp.success()) {
            throw new Exception(String.format("client.im.chat.get failed, code: %d, msg: %s, logId: %s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId()));
        }

        return resp.getData();
    }

    // 更新会话名称
    public static void UpdateChatName(String chatId, String chatName) throws Exception {
        UpdateChatReq req = UpdateChatReq.newBuilder()
                .chatId(chatId)
                .updateChatReqBody(UpdateChatReqBody.newBuilder()
                        .name(chatName)
                        .build())
                .build();

        UpdateChatResp resp = client.im().chat().update(req);

        if (!resp.success()) {
            throw new Exception(String.format("client.im.chat.update failed, code: %d, msg: %s, logId: %s",
                    resp.getCode(), resp.getMsg(), resp.getRequestId()));
        }
    }

}
