package dev.notypie.aggregate.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SlackUrlVerificationContents extends SlackEventContents{
    @JsonProperty("challenge")
    private String challenge;
}
