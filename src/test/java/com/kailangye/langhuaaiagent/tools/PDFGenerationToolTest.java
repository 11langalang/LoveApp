package com.kailangye.langhuaaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PDFGenerationToolTest {

    @Test
    public void testGeneratePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "测试pdf文件.pdf";
        String content = "pdf文件的测试内容";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}
