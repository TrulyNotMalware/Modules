package dev.notypie.infrastructure.impl.command.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UrlVerificationDto {
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
