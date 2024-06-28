package dev.notypie.infrastructure.impl.command.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Deprecated( forRemoval = true )
public class SlackUrlVerificationContents extends SlackEventContents{
    @JsonProperty("challenge")
    private String challenge;
}
