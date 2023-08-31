package com.larksuite.oapi.quick_start.robot;

import com.lark.oapi.sdk.servlet.ext.ServletAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        // 创建告警群并拉人入群
        String chatId = Im.CreateAlertChat();
        System.out.println("chatId: " + chatId);

        // 发送报警消息
        Im.SendAlertMessage(chatId);

        // 启动服务
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ServletAdapter getServletAdapter() {
        return new ServletAdapter();
    }
}
