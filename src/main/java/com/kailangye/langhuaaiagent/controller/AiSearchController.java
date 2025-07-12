//package com.kailangye.langhuaaiagent.controller;
//
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/ai")
//public class AiSearchController {
//
//
//    @PostMapping("/onlineSearch")
//    public AIQueryResponse searchWithAI(@RequestBody AIQueryRequest request) {
//        String userQuery = request.getQuery();
//
//        // 1. 调用浏览网页的API，如searchAPI，将每日新闻进行搜索
//        //    (实际实现中，这里会调用外部的搜索服务)
//        System.out.println("收到的查询: " + userQuery);
//        List<String> searchResultsUrls = callSearchAPI(userQuery);
//        System.out.println("模拟搜索引擎返回的链接: " + searchResultsUrls);
//
//        // 2. 直接将问题与搜出来的链接喂给AI
//        //    (实际实现中，这里会调用外部的AI服务)
//        String aiAnswer = callAIService(userQuery, searchResultsUrls);
//        System.out.println("模拟AI服务返回的答案: " + aiAnswer);
//
//        return new AIQueryResponse(aiAnswer, searchResultsUrls);
//    }
//}
