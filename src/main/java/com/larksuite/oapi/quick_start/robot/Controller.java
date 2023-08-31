package com.larksuite.oapi.quick_start.robot;

import com.lark.oapi.card.CardActionHandler;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.sdk.servlet.ext.ServletAdapter;
import com.lark.oapi.service.im.v1.ImService;
import com.lark.oapi.service.im.v1.model.EventMessage;
import com.lark.oapi.service.im.v1.model.GetChatRespBody;
import com.lark.oapi.service.im.v1.model.P2MessageReceiveV1;
import com.larksuite.oapi.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Controller {
    @Autowired
    private ServletAdapter servletAdapter;


    private final EventDispatcher EVENT_DISPATCHER = EventDispatcher.newBuilder(Config.VERIFICATION_TOKEN, Config.ENCRYPT_KEY)
            .onP2MessageReceiveV1(new ImService.P2MessageReceiveV1Handler() {
                // 处理消息回调
                @Override
                public void handle(P2MessageReceiveV1 event) throws Exception {
                    EventMessage msg = event.getEvent().getMessage();
                    if (msg.getContent().contains("/solve")) {
                        // 发送消息
                        Im.SendMessage(msg.getChatId(), "text", "{\"text\":\"问题已解决，辛苦了!\"}");

                        // 获取会话信息
                        GetChatRespBody chatInfo = Im.GetChatInfo(msg.getChatId());

                        // 修改会话名称
                        String name = chatInfo.getName();
                        if (name.startsWith("[跟进中]")) {
                            name = "[已解决]" + name.substring(5);
                        } else if (!name.startsWith("[已解决]")) {
                            name = "[已解决]" + name;
                        }
                        Im.UpdateChatName(msg.getChatId(), name);
                    }
                }
            })
            .build();

    private final CardActionHandler CARD_ACTION_HANDLER = CardActionHandler.newBuilder(Config.VERIFICATION_TOKEN, Config.ENCRYPT_KEY,
            cardAction -> {
                // 处理卡片回调
                if ("follow".equals(cardAction.getAction().getValue().get("key"))) {
                    // 获取会话信息
                    GetChatRespBody chatInfo = Im.GetChatInfo(cardAction.getOpenChatId());

                    // 修改会话名称
                    String name = chatInfo.getName();
                    if (!name.startsWith("[跟进中]") && !name.startsWith("[已解决]")) {
                        name = "[跟进中] " + name;
                    }
                    Im.UpdateChatName(cardAction.getOpenChatId(), name);

                    return Im.BuildCard("跟进中");
                }

                return null;
            }).build();

    @RequestMapping("/event")
    public void event(HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        servletAdapter.handleEvent(request, response, EVENT_DISPATCHER);
    }

    @RequestMapping("/card")
    public void card(HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        servletAdapter.handleCardAction(request, response, CARD_ACTION_HANDLER);
    }
}
