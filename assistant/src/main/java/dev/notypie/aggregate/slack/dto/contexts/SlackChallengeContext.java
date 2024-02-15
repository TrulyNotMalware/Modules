package dev.notypie.aggregate.slack.dto.contexts;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.notypie.aggregate.history.domain.EventHistory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    public EventHistory buildEventHistory() {
        return EventHistory.builder().build();
    }
}
