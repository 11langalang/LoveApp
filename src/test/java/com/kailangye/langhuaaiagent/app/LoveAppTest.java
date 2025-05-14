package com.kailangye.langhuaaiagent.app;

import dev.langchain4j.rag.query.transformer.CompressingQueryTransformer;
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
        Assertions.assertNotNull(answer);
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


    @Test
    void doChatWithLocalRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
        String answer =  loveApp.doChatWithLocalRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithCloudRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我现在是单身男人，我的爱好是玩游戏，你能帮我推荐以下合适我的对象吗，要求性别女";
        String answer =  loveApp.doChatWithCloudRag(message, chatId);
        Assertions.assertNotNull(answer);
//        CompressingQueryTransformer
    }

    @Test
    void doChatWithOnlineSearch() {
        String chatId = UUID.randomUUID().toString();
        String message = "我现在是单身男人，我的爱好是玩游戏，你能帮我推荐以下合适我的对象吗，要求性别女";
        String answer =  loveApp.doChatWithOnlineSearch(message, chatId);
        Assertions.assertNotNull(answer);

    }
}