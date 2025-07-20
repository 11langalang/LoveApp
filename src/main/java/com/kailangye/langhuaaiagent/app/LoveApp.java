package com.kailangye.langhuaaiagent.app;

import com.kailangye.langhuaaiagent.advisor.MyLoggerAdvisor;
import com.kailangye.langhuaaiagent.chatmemory.FileBasedChatMemory;
import com.kailangye.langhuaaiagent.rag.LoveAppRagCustomAdvisorFactory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class LoveApp {

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "扮演深耕恋爱心理领域的专家。开场向用户表明身份，告知用户可倾诉恋爱难题。" +
            "围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；" +
            "恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。" +
            "引导用户详述事情经过、对方反应及自身想法，以便给出专属解决方案。";



    //用于工具调用
    @Resource
    private ToolCallback[] allTools;

    @Resource
    private VectorStore loveAppVectorStore;


    //用于MCP
    @Resource
    private ToolCallbackProvider toolCallbackProvider;

    public LoveApp(ChatModel dashscopeChatModel) {
        // 初始化基于文件的对话记忆
        String fileDir = System.getProperty("user.dir") + "/temp/chat-memory";
        ChatMemory chatMemory = new FileBasedChatMemory(fileDir);
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        new MyLoggerAdvisor()
                )
                .build();
    }



    public String doChat(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }
//    public LoveReport doChatWithReport(String message, String chatId) {
//        LoveReport loveReport = chatClient
//                .prompt()
//                .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")
//                .user(message)
//                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
//                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
//                .call()
//                .entity(LoveReport.class);
//        log.info("loveReport: {}", loveReport);
//        return loveReport;
//    }


    record LoverReport(String title, List<String> suggestions){

    }
    /**
     * AI恋爱报告功能，演示结构化输出
     * @param message
     * @param chatId
     * @return
     */
    public LoverReport doChatWithReport(String message, String chatId) {
        LoverReport loverReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT +"每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(LoverReport.class);
        log.info("LoverReport: {}", loverReport);
        return loverReport;
    }




    public String doChatWithLocalRag(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                // 应用知识库问答
                .advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
//                自定义的配置知识库检索规则的查询增强方法
//                .advisors(LoveAppRagCustomAdvisorFactory.createLoveAppRagCustomAdvisor(loveAppVectorStore,"单身"))
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    @Resource
    private Advisor loveAppRagCloudAdvisor;
    public String doChatWithCloudRag(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                // 应用知识库问答
                .advisors(loveAppRagCloudAdvisor)
                .call()
                .chatResponse();

        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    /**
     * 模拟调用外部搜索API
     * @param query 用户的查询
     * @return 模拟搜索到的URL列表
     */
    private List<String> callSearchAPI(String query) {
        // TODO: 在此替换为实际的searchAPI调用逻辑
        // 例如: 使用RestTemplate, WebClient, 或其他HTTP客户端库
        log.info("模拟搜索引擎接收查询: {}", query);
        if (query.toLowerCase().contains("新闻") || query.toLowerCase().contains("news")) {
            return java.util.Arrays.asList(
                    "https://example.com/news/article1",
                    "https://example.com/news/article2",
                    "https://example.com/news/article3"
            );
        } else if (query.toLowerCase().contains("天气")) {
            return java.util.Arrays.asList(
                    "https://example.com/weather/" + query.replace(" ", "-")
            );
        }
        // 默认返回一些通用搜索结果
        return java.util.Arrays.asList(
                "https://example.com/search?q=" + query.replace(" ", "+") + "&source=loveapp",
                "https://another-search-engine.com/find?term=" + query.replace(" ", "+")
        );
    }

    /**
     * 结合在线搜索结果与AI进行对话
     * @param message 用户的原始消息/问题
     * @param chatId 对话ID
     * @return AI的回答
     */
    public String doChatWithOnlineSearch(String message, String chatId) {
        log.info("联网搜索查询: {}, chatId: {}", message, chatId);

        // 1. 调用（模拟的）浏览网页API进行搜索
        List<String> searchResultsUrls = callSearchAPI(message);
        log.info("模拟搜索引擎返回的链接: {}", searchResultsUrls);

        // 2. 构建包含搜索结果的提示给AI
        StringBuilder augmentedUserMessage = new StringBuilder(message);
        if (searchResultsUrls != null && !searchResultsUrls.isEmpty()) {
            augmentedUserMessage.append("\n\n以下是相关的网络搜索结果，请参考这些信息进行回答：\n");
            for (String url : searchResultsUrls) {
                augmentedUserMessage.append("- ").append(url).append("\n");
            }
        }
        // 为了保留原LoveApp的人设，我们还是用SYSTEM_PROMPT，将联网信息追加到用户消息中
        ChatResponse response = chatClient
                .prompt()
                // .system() // 可以考虑动态修改system prompt，但此处我们让人设保持不变
                .user(augmentedUserMessage.toString())
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10)) // 保留对话记忆
                .advisors(new MyLoggerAdvisor()) // 保留日志 Advisor
                .call()
                .chatResponse();

        String content = response.getResult().getOutput().getText();
        log.info("AI (联网搜索后) 回答: {}", content);
        return content;
    }



    public String doChatWithTools(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .tools(allTools)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }



    public String doChatWithMcp(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .tools(toolCallbackProvider)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }


    public Flux<String> doChatByStream(String message, String chatId) {
        return chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .stream()
                .content();
    }



}

