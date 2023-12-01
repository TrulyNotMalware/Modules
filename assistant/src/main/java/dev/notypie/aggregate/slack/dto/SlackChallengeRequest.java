package dev.notypie.aggregate.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SlackChallengeRequest {
    @JsonProperty("type")
    @NotNull
    private String type;

    @JsonProperty("token")
    @NotNull
    private String token;

    @JsonProperty("challenge")
    @NotNull
    private String challenge;
}
