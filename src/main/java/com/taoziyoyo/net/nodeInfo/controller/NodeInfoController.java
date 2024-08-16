package com.taoziyoyo.net.nodeInfo.controller;

import com.taoziyoyo.net.nodeInfo.model.Result;
import com.taoziyoyo.net.nodeInfo.service.NodeInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class NodeInfoController {
    private static final Logger logger = LoggerFactory.getLogger(NodeInfoController.class);

    private final NodeInfoService nodeInfoService;
    public NodeInfoController(NodeInfoService nodeInfoService) {
        this.nodeInfoService = nodeInfoService;
    }

    @GetMapping("/healthCheck")
    public Result healthCheck(){

        return Result.success("health check ok");
    }

    @GetMapping("/getNodeInfo")
    public Result getNodeInfo(@RequestParam(value = "instanceName",required = false) String instanceName){
        logger.info("get node info started");
        Result result;
        if(!StringUtils.hasText(instanceName)){
            result = Result.failed("instanceName is empty");
        }else {
            result = nodeInfoService.getNodeInfo(instanceName);
        }
        logger.info("get node info finished");
        return result;
    }

}
