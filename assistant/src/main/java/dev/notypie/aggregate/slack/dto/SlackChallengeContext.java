package dev.notypie.aggregate.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.notypie.aggregate.slack.EventHistory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class SlackChallengeContext extends Contexts {
    @JsonProperty("type")
    @NotNull
    private String type;

    @JsonProperty("token")
    @NotNull
    private String token;

    @JsonProperty("challenge")
    @NotNull
    private String challenge;

    @Override
    protected EventHistory getHistoryContents() {
        return null;
    }
}
