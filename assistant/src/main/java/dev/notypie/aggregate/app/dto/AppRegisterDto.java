package dev.notypie.aggregate.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AppRegisterDto {
    @JsonProperty("appId")
    private String appId;

    @JsonProperty("creatorId")
    private String creatorId;

    @JsonProperty("appName")
    private String appName;

    @JsonProperty("appType")
    private String appType;
}
