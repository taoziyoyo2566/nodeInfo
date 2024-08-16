package com.taoziyoyo.net.nodeInfo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoziyoyo.net.nodeInfo.model.NodeInfo;
import com.taoziyoyo.net.nodeInfo.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class NodeInfoServiceImpl implements NodeInfoService {
    private static final Logger logger = LoggerFactory.getLogger(NodeInfoServiceImpl.class);
    /**
     * @param instanceName String
     * @return Result
     */
    @Override
    public Result getNodeInfo(String instanceName) {

        NodeInfo nodeInfo = null;
        Result returnResult = Result.failed("uncompleted process");
        try {
            List<String> command = new ArrayList<>();
            command.add("docker");
            command.add("exec");
            command.add(instanceName);
            command.add("cat");
            command.add("vless_info.json");
            logger.info("Executing command: {}", command);
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // 读取命令执行的输出内容
            StringBuilder jsonOutput = new StringBuilder();
            try (InputStream inputStream = process.getInputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    jsonOutput.append(line);
                }
            } catch (IOException e) {
                logger.error(e.toString());
            }
            logger.info("json file content: {}", jsonOutput);

            // 等待命令执行完成
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                returnResult = Result.success();
                if(StringUtils.hasText(jsonOutput)){
                    // 解析JSON内容
                    ObjectMapper objectMapper = new ObjectMapper();
                    nodeInfo = objectMapper.readValue(jsonOutput.toString(), NodeInfo.class);
                    logger.info("Instance Name: {}",nodeInfo.getInstanceName());
                    logger.info("Node URL: {}",nodeInfo.getNodeUrl());
                    logger.info("Create DateTime: {}", nodeInfo.getCreateDateTime());
                    logger.info("Region: {}", nodeInfo.getRegion());
                    logger.info("Command executed successfully");
                }
            } else {
                logger.error("Command execution failed with instanceName: {}", instanceName);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        returnResult.put("nodeInfo", nodeInfo);


        return returnResult;
    }
}
