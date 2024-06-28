package dev.notypie.infrastructure.impl.command.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.notypie.infrastructure.impl.command.slack.dto.bot.BotProfile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Deprecated( forRemoval = true )
public class SlackApiMessage {
    @JsonProperty("bot_id")
    private String botId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("text")
    private String rawText;

    @JsonProperty("user")
    private String user;

    @JsonProperty("ts")
    private Double ts;

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("blocks")
    private List<Block> blocks;

    @JsonProperty("team")
    private String team;

    @JsonProperty("bot_profile")
    private BotProfile botProfile;

}
