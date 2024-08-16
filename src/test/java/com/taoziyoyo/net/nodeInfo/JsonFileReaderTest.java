package com.taoziyoyo.net.nodeInfo;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoziyoyo.net.nodeInfo.model.NodeInfo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFileReaderTest {
    Logger logger = LoggerFactory.getLogger(JsonFileReaderTest.class);
    @Test
    void test() {
        // JSON 文件路径
        Path currentPath = Paths.get(JsonFileReaderTest.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println("当前类的路径: " + currentPath);
        logger.info("当前类的路径: {}", currentPath);
        Path resourcePath = Paths.get(JsonFileReaderTest.class.getClassLoader().getResource("application.yml").getPath());
        System.out.println("资源文件的路径: " + resourcePath);
        logger.info("资源文件的路径: {}", resourcePath);
        String currentDir = System.getProperty("user.dir");
        System.out.println("当前项目目录: " + currentDir);
        logger.info("当前项目目录: {}",currentDir);

        String jsonFilePath = "/Users/taoziyoyo/IdeaProjects/nodeInfo/config_info.txt"; // 请替换为你的 JSON 文件的实际路径
        // 使用 Path 对象表示当前项目目录
        Path currentDirPath = Paths.get(currentDir);
        System.out.println("当前项目目录(Path对象): " + currentDirPath);
        logger.info("当前项目目录(Path对象): {}",currentDirPath);
//         创建 ObjectMapper 实例
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 读取 JSON 文件并映射到 NodeInfo 实体类
            NodeInfo nodeInfo = objectMapper.readValue(new File("/Users/taoziyoyo/IdeaProjects/nodeInfo/vless_info.json"), NodeInfo.class);

            // 打印 NodeInfo 实体类的字段值
            System.out.println("Instance Name: " + nodeInfo.getInstanceName());
            System.out.println("Node URL: " + nodeInfo.getNodeUrl());
            System.out.println("Create DateTime: " + nodeInfo.getCreateDateTime());
            System.out.println("Region: " + nodeInfo.getRegion());
        } catch (IOException e) {

        }
    }
}
