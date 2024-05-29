package com.taoziyoyo.net.nodeInfo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonProperty("URL_ID")
    private String instanceName;

    @JsonProperty("URL_IPV4")
    private String nodeUrl;

    @JsonProperty("CREATE_DATETIME")
    private String createDateTime;

    @JsonProperty("EXPIRE_DATETIME")
    private String expireDateTime;

    @JsonProperty("REGION")
    private String region;

}

