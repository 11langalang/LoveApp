package com.kailangye.langhuaaiagent.tools;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class FileOperationToolTest {

    @Test
    void readFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "测试文件.txt";
        String result = fileOperationTool.readFile(fileName);
        Assertions.assertNotNull(result);
        log.info("Output is: " + result);
    }

    @Test
    void writeFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "测试文件.txt";
        String content = "测试添加内容";
        String result = fileOperationTool.writeFile(fileName, content);
        Assertions.assertNotNull(result);
        log.info("Output is: " + result);
    }
}