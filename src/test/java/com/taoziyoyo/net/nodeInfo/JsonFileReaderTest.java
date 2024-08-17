package com.taoziyoyo.net.nodeInfo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoziyoyo.net.nodeInfo.model.NodeInfo;
import com.taoziyoyo.net.nodeInfo.model.Result;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.hierarchical.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
        logger.info("当前项目目录: {}", currentDir);


        String jsonFilePath = "/Users/taoziyoyo/IdeaProjects/nodeInfo/config_info.txt"; // 请替换为你的 JSON 文件的实际路径
        jsonFilePath = "/opt/docker/reality/nodeInfo/reality_ZGOUS_9df3641e/vless_info.json";
        // 使用 Path 对象表示当前项目目录
        Path currentDirPath = Paths.get(currentDir);
        System.out.println("当前项目目录(Path对象): " + currentDirPath);
        logger.info("当前项目目录(Path对象)log: {}", currentDirPath);
//         创建 ObjectMapper 实例
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "{\n" +
                "  \"URL_ID\": \"9df3641e\",\n" +
                "  \"REGION\": \"ZGOUS\",\n" +
                "  \"IPV4\": \"154.23.243.155\",\n" +
                "  \"UUID\": \"630ba768-b090-4d17-a5a1-f7d0e27b7514\",\n" +
                "  \"DEST\": \"www.apple.com:443\",\n" +
                "  \"PORT\": \"24560\",\n" +
                "  \"NETWORK\": \"tcp\",\n" +
                "  \"URL_IPV4\": \"vless://630ba768-b090-4d17-a5a1-f7d0e27b7514@154.23.243.155:24560?encryption=none&security=reality&type=tcp&sni=www.apple.com&fp=chrome&pbk=N28MayohO2lQlFPk2uSMDRAvo1-joj5JNxpy-OZdxH4&flow=xtls-rprx-vision#vless_reality_ZGOUS_9df3641e\",\n" +
                "  \"CREATE_DATETIME\": \"2024-08-13 00:08:20\",\n" +
                "  \"EXPIRE_DATETIME\": \"NA\",\n" +
                "  \"MONTH_COUNT\": \"\",\n" +
                "  \"DAY_COUNT\": \"\"\n" +
                "}";
        try {
            NodeInfo nodeInfo = objectMapper.readValue(jsonString, NodeInfo.class);
            logger.info("nodeInfo json deser: {}", nodeInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            // 读取 JSON 文件并映射到 NodeInfo 实体类
            NodeInfo nodeInfo = objectMapper.readValue(new File("jsonFilePath"), NodeInfo.class);
            logger.info("nodeInfo: {}", nodeInfo.toString());
            // 打印 NodeInfo 实体类的字段值
            System.out.println("Instance Name: " + nodeInfo
                    .getInstanceName());
            System.out.println("Node URL: " + nodeInfo.getNodeUrl());
            System.out.println("Create DateTime: " + nodeInfo.getCreateDateTime());
            System.out.println("Region: " + nodeInfo.getRegion());
        } catch (IOException e) {
            logger.error("exception");
        }
    }

    @Test
    public void dockerJson() {
        String instanceName = "reality_ZGOUS_9df3641e";
        String filePath = "vless_info.json";

        try {

            NodeInfo myObject = deserializeJson(instanceName, filePath);

            logger.info("nodeInfo docker json: {}", myObject.toString());
            logger.info("nodeInfo docker column: {}", myObject.getNodeUrl());
        } catch (Exception e) {
            logger.error("Docker json exception");
        }
    }

    public NodeInfo deserializeJson(String instanceName, String filePath) {
        String jsonContent = null;
        try {
            jsonContent = readJsonFromDocker(instanceName, filePath);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonContent, NodeInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public String readJsonFromDocker(String instanceName, String filePath) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("docker", "exec", instanceName, "cat", filePath);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder jsonContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonContent.append(line);
        }

        process.waitFor();
        reader.close();

        return jsonContent.toString();
    }

    @Test
    public void dockerTest() {
        String instanceName = "reality_ZGOUS_9df3641e";
        String filePath = "vless_info.json";
        Result result = new Result();
        List<String> commands = Arrays.asList("docker", "exec", instanceName, "cat", filePath);
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.redirectErrorStream(true);
        Process process = null;
        NodeInfo nodeInfo = null;
        StringBuilder jsonContent = null;
        try {
            process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            int exitCode = 100;
            try {
                exitCode = process.waitFor();
                ProcessHandle.Info info = process.info();

            } catch (InterruptedException e) {
                logger.error("docker exec exception: {}", e.getLocalizedMessage());
            }
            reader.close();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                if (exitCode == 0) {
                    logger.info("Command executed successfully");
                    nodeInfo = objectMapper.readValue(jsonContent.toString(), NodeInfo.class);
                    logger.info("nodeInfo: {}", nodeInfo.toString());
                    logger.info("nodeInfo docker column: {}", nodeInfo.getNodeUrl());
                } else {

                    logger.error("Command execution failed with instanceName: {}", instanceName);
                    logger.error("error message: {}", jsonContent.substring(jsonContent.indexOf(":") + 1).trim());
                }

            } catch (JsonProcessingException e) {
                logger.error("Json processing exception: {}", e.getLocalizedMessage());
            }

        } catch (IOException e) {
            logger.error("json file read exception: {}", e.getLocalizedMessage());
        }
    }

}
