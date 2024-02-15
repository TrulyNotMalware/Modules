package dev.notypie.aggregate.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlackApiResponse {

    @JsonProperty("ok")
    private boolean ok;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("ts")
    private Double ts;

    @JsonProperty("message")
    private SlackApiMessage message;

}
