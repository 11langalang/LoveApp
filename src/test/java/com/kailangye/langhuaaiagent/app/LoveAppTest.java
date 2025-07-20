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

    @Test
    void doChatWithTools() {
        // 测试联网搜索问题的答案
        testMessage("周末想带女朋友去上海约会，推荐几个适合情侣的小众打卡地？");

        // 测试网页抓取：恋爱案例分析
        testMessage("最近和对象吵架了，看看编程导航网站（codefather.cn）的其他情侣是怎么解决矛盾的？");

        // 测试资源下载：图片下载
        testMessage("直接下载一张适合做手机壁纸的星空情侣图片为文件");

        // 测试终端操作：执行代码
        testMessage("执行 Python3 脚本来生成数据分析报告");

        // 测试文件操作：保存用户档案
        testMessage("保存我的恋爱档案为文件");

        // 测试 PDF 生成
        testMessage("生成一份‘七夕约会计划’PDF，包含餐厅预订、活动流程和礼物清单");
    }

    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = loveApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }


    @Test
    void doChatWithMcp() {
        String chatId = UUID.randomUUID().toString();
        // 测试地图 MCP
//        String message = "我的另一半居住在上海静安区，请帮我找到 5 公里内合适的约会地点";
//        String answer =  loveApp.doChatWithMcp(message, chatId);
        // 测试图片搜索 MCP
        String message = "i want to search image from web which the image is about delicious food";
        String answer =  loveApp.doChatWithMcp(message, chatId);
        Assertions.assertNotNull(answer);
    }

}