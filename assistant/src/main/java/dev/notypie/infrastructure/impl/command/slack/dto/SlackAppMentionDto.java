package dev.notypie.infrastructure.impl.command.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class SlackAppMentionDto {

    @JsonProperty("token")
    private String token;

    @JsonProperty("team_id")
    private String teamId;

    @JsonProperty("api_app_id")
    private String apiAppId;

    @JsonProperty("event")
    private AppMentionEventType event;

    @JsonProperty("type")
    private String type;

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("event_time")
    private int event_time;

    @JsonProperty("authorizations")
    private List<Authorization> authorizations;

    @JsonProperty("is_ext_shared_channel")
    private boolean isExtSharedChannel;

    @JsonProperty("event_context")
    private String eventContext;
}
