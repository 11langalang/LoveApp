package com.kailangye.langhuaaiagent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class SearchService {

    /**
     * 模拟调用外部搜索API
     * @param query 用户的查询
     * @return 模拟搜索到的URL列表
     */

    public List<String> callSearchAPI(String query) {
        // TODO: 在此替换为实际的searchAPI调用逻辑
        // 例如: 使用RestTemplate, WebClient, 或其他HTTP客户端库
        log.info("SearchService: 模拟搜索引擎接收查询: {}", query);
        if (query.toLowerCase().contains("新闻") || query.toLowerCase().contains("news")) {
            return Arrays.asList(
                    "https://example.com/news/article1",
                    "https://example.com/news/article2",
                    "https://example.com/news/article3"
            );
        } else if (query.toLowerCase().contains("天气")) {
            return Arrays.asList(
                    "https://example.com/weather/" + query.replace(" ", "-")
            );
        }
        // 默认返回一些通用搜索结果
        return Arrays.asList(
                "https://example.com/search?q=" + query.replace(" ", "+") + "&source=external_service",
                "https://another-search-engine.com/find?term=" + query.replace(" ", "+") + "&source=external_service"
        );
    }
} 