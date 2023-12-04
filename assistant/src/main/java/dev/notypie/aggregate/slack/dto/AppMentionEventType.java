package dev.notypie.aggregate.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
class AppMentionEventType {

    /**
     * Required scopes : app_mention:read
     */
    @JsonProperty("type")
    private String type;

    //event time stamp
    @JsonProperty("event_ts")
    private String eventTs;

    @JsonProperty("user")
    private String user;

    @JsonProperty("text")
    private String text;

    @JsonProperty("ts")
    private String timeSeconds;

    @JsonProperty("channel")
    private String channel;
}
