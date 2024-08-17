package com.taoziyoyo.net.nodeInfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.taoziyoyo.net.nodeInfo.model.NodeInfo;
import com.taoziyoyo.net.nodeInfo.model.Result;
import com.taoziyoyo.net.nodeInfo.utils.DockerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NodeInfoServiceImpl implements NodeInfoService {
    private static final Logger logger = LoggerFactory.getLogger(NodeInfoServiceImpl.class);

    private final DockerUtils dockerUtils = new DockerUtils();

    /**
     * @param instanceName String
     * @return Result
     */
    @Override
    public Result getNodeInfo(String instanceName) {

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
            } catch (InterruptedException e) {
                logger.error("docker exec InterruptedException: {}", e.getLocalizedMessage());
            }

            reader.close();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                if (exitCode == 0) {
                    logger.info("Command executed successfully");
                    nodeInfo = objectMapper.readValue(jsonContent.toString(), NodeInfo.class);
                    logger.info("nodeInfo: {}", nodeInfo.getNodeUrl());
                    result = Result.success();
                    result.put("nodeInfo",nodeInfo);
                } else {
                    String message = jsonContent.substring(jsonContent.indexOf(":") + 1).trim();
                    logger.error("error message: {}", message);
                    result = Result.failed(message);
                    logger.error("Command execution failed with instanceName: {}", instanceName);
                }
            } catch (JsonProcessingException e) {
                logger.error("Json processing exception: {}", e.getLocalizedMessage());
            }
        } catch (IOException e) {
            logger.error("json file read exception: {}", e.getLocalizedMessage());
        }

        return result;
    }
}
