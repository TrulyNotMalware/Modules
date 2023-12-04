package dev.notypie.aggregate.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.notypie.aggregate.slack.EventHistory;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
public class SlackAppMentionContext extends Contexts {

    @JsonProperty("type")
    @NotNull
    private String type;

    @JsonProperty("event")
    private AppMentionEventType event;


    @Override
    protected EventHistory getHistoryContents() {
        return null;
    }

//    @JsonProperty("token")
//    @NotNull
//    private String token;
//
//    @JsonProperty("team_id")
//    @NotNull
//    private String teamId;
//
//    @JsonProperty("api_app_id")
//    @NotNull
//    private String apiAppId;
//
//    @JsonProperty("event")
//    @NotNull
//    private Event event;
}