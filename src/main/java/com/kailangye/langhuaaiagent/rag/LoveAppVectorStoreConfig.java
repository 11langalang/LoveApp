package com.kailangye.langhuaaiagent.rag;



import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoveAppVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Resource
    MyTokenTextSplitter myTokenTextSplitter;

    @Resource
    MyKeyWordEnricher enricher;
    @Bean
    VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel, MyTokenTextSplitter myTokenTextSplitter) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel)
                .build();
        // 加载文档
        List<Document> documents = loveAppDocumentLoader.loadMarkdowns();
        //数据切分
//        documents = myTokenTextSplitter.splitDocuments(documents);
        //对原文件进行增强
        documents = enricher.enricherDocument(documents);
        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }
}
