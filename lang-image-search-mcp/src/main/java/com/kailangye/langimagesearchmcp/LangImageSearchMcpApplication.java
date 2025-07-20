package com.kailangye.langimagesearchmcp;

import com.kailangye.langimagesearchmcp.tools.ImageSearchTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LangImageSearchMcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(LangImageSearchMcpApplication.class, args);
    }


    //Spring Boot项目启动的时候，会自动读取我们的工具，注册为Bean
    @Bean
    public ToolCallbackProvider imageSearchTools(ImageSearchTool imageSearchTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(imageSearchTool)
                .build();
    }
}
