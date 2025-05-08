package com.kailangye.langhuaaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        String chatId2 = UUID.randomUUID().toString();

        //第一轮会话
        String message = "你好，我是小红";
        String answer = loveApp.doChat(message,chatId);
        //第二轮会话
        message = "我想让我的另一半(小黄)更爱我";
        answer = loveApp.doChat(message,chatId);
        Assertions.assertNotNull(answer);
        //第三轮会话
        message = "我的另一半叫什么？";
        answer = loveApp.doChat(message,chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
//        String chatId2 = UUID.randomUUID().toString();

        //第一轮会话
        String message = "你好，我是小红,我想让对象更爱我，请告诉我怎么做";
        LoveApp.LoverReport answer = loveApp.doChatWithReport(message,chatId);
        Assertions.assertNotNull(answer);
//        //第二轮会话
//        message = "我想让我的另一半(小黄)更爱我";
//        answer = loveApp.doChatWithReport(message,chatId);
//        Assertions.assertNotNull(answer);
//        //第三轮会话
//        message = "我的另一半叫什么？";
//        answer = loveApp.doChatWithReport(message,chatId);
//        Assertions.assertNotNull(answer);
    }
}