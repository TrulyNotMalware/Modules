package dev.notypie.aggregate.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EnableAppDto {
    @JsonProperty("appId")
    private String appId;

    @JsonProperty("creatorId")
    private String creatorId;
}
