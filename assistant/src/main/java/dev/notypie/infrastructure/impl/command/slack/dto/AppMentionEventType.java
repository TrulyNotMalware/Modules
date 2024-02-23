package dev.notypie.infrastructure.impl.command.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AppMentionEventType {

    /**
     * Required scopes : app_mention:read
     */
    @JsonProperty("client_msg_id")
    private String clientMessageId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("text")
    private String rawText;

    @JsonProperty("user")
    private String userId;

    @JsonProperty("ts")
    private Double ts;

    @JsonProperty("blocks")
    private List<Block> blocks;

    @JsonProperty("team")
    private String team;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("event_ts")
    private Double eventTs;
}
