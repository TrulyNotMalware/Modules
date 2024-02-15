package dev.notypie.aggregate.slack.dto.bot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class BotProfile {

    @JsonProperty("id")
    private String botId;

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("name")
    private String botName;

    @JsonProperty("icons")
    private Map<String, String> icons;

    @JsonProperty("deleted")
    private boolean deleted;

    @JsonProperty("updated")
    private Integer updated;

    @JsonProperty("team_id")
    private String teamId;
}
