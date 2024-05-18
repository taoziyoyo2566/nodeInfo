package com.taoziyoyo.net.nodeInfo.service;

import com.taoziyoyo.net.nodeInfo.model.NodeInfo;
import com.taoziyoyo.net.nodeInfo.model.Result;

public interface NodeInfoService {
    Result getNodeInfo(String instanceName);

}
